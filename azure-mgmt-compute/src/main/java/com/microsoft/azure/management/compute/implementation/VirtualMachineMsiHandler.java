/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.compute.ResourceIdentityType;
import com.microsoft.azure.management.compute.VirtualMachineIdentity;
import com.microsoft.azure.management.compute.VirtualMachineIdentityUserAssignedIdentitiesValue;
import com.microsoft.azure.management.compute.VirtualMachineUpdate;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.graphrbac.implementation.RoleAssignmentHelper;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Utility class to set Managed Service Identity (MSI) property on a virtual machine,
 * install or update MSI extension and create role assignments for the service principal
 * associated with the virtual machine.
 */
@LangDefinition
class VirtualMachineMsiHandler extends RoleAssignmentHelper {
    private final VirtualMachineImpl virtualMachine;

    private List<String> creatableIdentityKeys;
    private Map<String, VirtualMachineIdentityUserAssignedIdentitiesValue> userAssignedIdentities;

    /**
     * Creates VirtualMachineMsiHandler.
     *
     * @param rbacManager the graph rbac manager
     * @param virtualMachine the virtual machine to which MSI extension needs to be installed and
     *                       for which role assignments needs to be created
     */
    VirtualMachineMsiHandler(final GraphRbacManager rbacManager,
                             VirtualMachineImpl virtualMachine) {
        super(rbacManager, virtualMachine.taskGroup(), virtualMachine.idProvider());
        this.virtualMachine = virtualMachine;
        this.creatableIdentityKeys = new ArrayList<>();
        this.userAssignedIdentities = new HashMap<>();
    }

    /**
     * Specifies that Local Managed Service Identity needs to be enabled in the virtual machine.
     * If MSI extension is not already installed then it will be installed with access token
     * port as 50342.
     *
     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withLocalManagedServiceIdentity() {
        this.initVMIdentity(ResourceIdentityType.SYSTEM_ASSIGNED);
        return this;
    }

    /**
     * Specifies that Local Managed Service Identity needs to be disabled in the virtual machine.
     *
     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withoutLocalManagedServiceIdentity() {
        if (this.virtualMachine.inner().identity() == null
                || this.virtualMachine.inner().identity().type() == null
                || this.virtualMachine.inner().identity().type().equals(ResourceIdentityType.NONE)
                || this.virtualMachine.inner().identity().type().equals(ResourceIdentityType.USER_ASSIGNED)) {
            return this;
        } else if (this.virtualMachine.inner().identity().type().equals(ResourceIdentityType.SYSTEM_ASSIGNED)) {
            this.virtualMachine.inner().identity().withType(ResourceIdentityType.NONE);
        } else if (this.virtualMachine.inner().identity().type().equals(ResourceIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED)) {
            this.virtualMachine.inner().identity().withType(ResourceIdentityType.USER_ASSIGNED);
        }
        return this;
    }

    /**
     * Specifies that given identity should be set as one of the External Managed Service Identity
     * of the virtual machine.
     *
     * @param creatableIdentity yet-to-be-created identity to be associated with the virtual machine
     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withNewExternalManagedServiceIdentity(Creatable<Identity> creatableIdentity) {
        this.initVMIdentity(ResourceIdentityType.USER_ASSIGNED);

        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) creatableIdentity;
        Objects.requireNonNull(dependency);

        this.virtualMachine.taskGroup().addDependency(dependency);
        this.creatableIdentityKeys.add(creatableIdentity.key());

        return this;
    }

    /**
     * Specifies that given identity should be set as one of the External Managed Service Identity
     * of the virtual machine.
     *
     * @param identity an identity to associate
     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withExistingExternalManagedServiceIdentity(Identity identity) {
        this.initVMIdentity(ResourceIdentityType.USER_ASSIGNED);
        this.userAssignedIdentities.put(identity.id(), new VirtualMachineIdentityUserAssignedIdentitiesValue());
        return this;
    }

    /**
     * Specifies that given identity should be removed from the list of External Managed Service Identity
     * associated with the virtual machine machine.
     *
     * @param identityId resource id of the identity
     * @return VirtualMachineMsiHandler
     */
    VirtualMachineMsiHandler withoutExternalManagedServiceIdentity(String identityId) {
        this.userAssignedIdentities.put(identityId, null);
        return this;
    }

    void processCreatedExternalIdentities() {
        for (String key : this.creatableIdentityKeys) {
            Identity identity = (Identity) this.virtualMachine.taskGroup().taskResult(key);
            Objects.requireNonNull(identity);
            this.userAssignedIdentities.put(identity.id(), new VirtualMachineIdentityUserAssignedIdentitiesValue());
        }
        this.creatableIdentityKeys.clear();
    }

    void handleExternalIdentities() {
        if (!this.userAssignedIdentities.isEmpty()) {
            this.virtualMachine.inner().identity().withUserAssignedIdentities(this.userAssignedIdentities);
        }
    }

    void handleExternalIdentities(VirtualMachineUpdate vmUpdate) {
        if (this.handleRemoveAllExternalIdentitiesCase(vmUpdate)) {
            return;
        } else {
            // At this point one of the following condition is met:
            //
            // 1. User don't want touch the 'VM.Identity.userAssignedIdentities' property
            //      [this.userAssignedIdentities.empty() == true]
            // 2. User want to add some identities to 'VM.Identity.userAssignedIdentities'
            //      [this.userAssignedIdentities.empty() == false and this.virtualMachine.inner().identity() != null]
            // 3. User want to remove some (not all) identities in 'VM.Identity.userAssignedIdentities'
            //      [this.userAssignedIdentities.empty() == false and this.virtualMachine.inner().identity() != null]
            //      Note: The scenario where this.virtualMachine.inner().identity() is null in #3 is already handled in
            //      handleRemoveAllExternalIdentitiesCase method
            // 4. User want to add and remove (all or subset) some identities in 'VM.Identity.userAssignedIdentities'
            //      [this.userAssignedIdentities.empty() == false and this.virtualMachine.inner().identity() != null]
            //
            VirtualMachineIdentity currentIdentity = this.virtualMachine.inner().identity();
            vmUpdate.withIdentity(currentIdentity);
            if (!this.userAssignedIdentities.isEmpty()) {
                // At this point its guaranteed that 'currentIdentity' is not null so vmUpdate.identity() is.
                vmUpdate.identity().withUserAssignedIdentities(this.userAssignedIdentities);
            } else {
                // User don't want to touch 'VM.Identity.userAssignedIdentities' property
                if (currentIdentity != null) {
                    // and currently there is identity exists or user want to manipulate some other properties of
                    // identity, set identities to null so that it won't send over wire.
                    currentIdentity.withUserAssignedIdentities(null);
                }
            }
        }
    }

    /**
     * Clear VirtualMachineMsiHandler post-run specific internal state.
     */
    void clear() {
        this.userAssignedIdentities = new HashMap<>();
    }

    /**
     * Method that handle the case where user request indicates all it want to do is remove all identities associated
     * with the virtual machine.
     *
     * @param vmUpdate the vm update payload model
     * @return true if user indented to remove all the identities.
     */
    private boolean handleRemoveAllExternalIdentitiesCase(VirtualMachineUpdate vmUpdate) {
        if (!this.userAssignedIdentities.isEmpty()) {
            int rmCount = 0;
            for (VirtualMachineIdentityUserAssignedIdentitiesValue v : this.userAssignedIdentities.values()) {
                if (v == null) {
                    rmCount++;
                } else {
                    break;
                }
            }
            boolean containsRemoveOnly = rmCount > 0 && rmCount == this.userAssignedIdentities.size();
            // Check if user request contains only request for removal of identities.
            if (containsRemoveOnly) {
                Set<String> currentIds = new HashSet<>();
                VirtualMachineIdentity currentIdentity = this.virtualMachine.inner().identity();
                if (currentIdentity != null && currentIdentity.userAssignedIdentities() != null) {
                    for (String id : currentIdentity.userAssignedIdentities().keySet()) {
                        currentIds.add(id.toLowerCase());
                    }
                }
                Set<String> removeIds = new HashSet<>();
                for (Map.Entry<String, VirtualMachineIdentityUserAssignedIdentitiesValue> entrySet : this.userAssignedIdentities.entrySet()) {
                    if (entrySet.getValue() == null) {
                        removeIds.add(entrySet.getKey().toLowerCase());
                    }
                }
                // If so check user want to remove all the identities
                boolean removeAllCurrentIds = currentIds.size() == removeIds.size() && currentIds.containsAll(removeIds);
                if (removeAllCurrentIds) {
                    // If so adjust  the identity type [Setting type to SYSTEM_ASSIGNED orNONE will remove all the identities]
                    if (currentIdentity == null || currentIdentity.type() == null) {
                        vmUpdate.withIdentity(new VirtualMachineIdentity().withType(ResourceIdentityType.NONE));
                    } else if (currentIdentity.type().equals(ResourceIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED)) {
                        vmUpdate.withIdentity(currentIdentity);
                        vmUpdate.identity().withType(ResourceIdentityType.SYSTEM_ASSIGNED);
                    } else if (currentIdentity.type().equals(ResourceIdentityType.USER_ASSIGNED)) {
                        vmUpdate.withIdentity(currentIdentity);
                        vmUpdate.identity().withType(ResourceIdentityType.NONE);
                    }
                    // and set identities property in the payload model to null so that it won't be sent
                    vmUpdate.identity().withUserAssignedIdentities(null);
                    return true;
                } else {
                    // Check user is asking to remove identities though there is no identities currently associated
                    if (currentIds.size() == 0
                            && removeIds.size() != 0
                            && currentIdentity == null) {
                        // If so we are in a invalid state but we want to send user input to service and let service
                        // handle it (ignore or error).
                        vmUpdate.withIdentity(new VirtualMachineIdentity().withType(ResourceIdentityType.NONE));
                        vmUpdate.identity().withUserAssignedIdentities(null);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Initialize VM's identity property.
     *
     * @param identityType the identity type to set
     */
    private void initVMIdentity(ResourceIdentityType identityType) {
        if (!identityType.equals(ResourceIdentityType.USER_ASSIGNED)
                && !identityType.equals(ResourceIdentityType.SYSTEM_ASSIGNED)) {
            throw new IllegalArgumentException("Invalid argument: " + identityType);
        }

        VirtualMachineInner virtualMachineInner = this.virtualMachine.inner();
        if (virtualMachineInner.identity() == null) {
            virtualMachineInner.withIdentity(new VirtualMachineIdentity());
        }
        if (virtualMachineInner.identity().type() == null
                || virtualMachineInner.identity().type().equals(ResourceIdentityType.NONE)
                || virtualMachineInner.identity().type().equals(identityType)) {
            virtualMachineInner.identity().withType(identityType);
        } else {
            virtualMachineInner.identity().withType(ResourceIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED);
        }
    }
}