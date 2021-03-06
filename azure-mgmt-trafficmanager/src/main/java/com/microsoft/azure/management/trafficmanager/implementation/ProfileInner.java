/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.trafficmanager.implementation;

import com.microsoft.azure.management.trafficmanager.ProfileStatus;
import com.microsoft.azure.management.trafficmanager.TrafficRoutingMethod;
import com.microsoft.azure.management.trafficmanager.DnsConfig;
import com.microsoft.azure.management.trafficmanager.MonitorConfig;
import java.util.List;
import com.microsoft.azure.management.trafficmanager.TrafficViewEnrollmentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.rest.SkipParentValidation;
import com.microsoft.azure.Resource;

/**
 * Class representing a Traffic Manager profile.
 */
@JsonFlatten
@SkipParentValidation
public class ProfileInner extends Resource {
    /**
     * The status of the Traffic Manager profile. Possible values include:
     * 'Enabled', 'Disabled'.
     */
    @JsonProperty(value = "properties.profileStatus")
    private ProfileStatus profileStatus;

    /**
     * The traffic routing method of the Traffic Manager profile. Possible
     * values include: 'Performance', 'Priority', 'Weighted', 'Geographic',
     * 'MultiValue', 'Subnet'.
     */
    @JsonProperty(value = "properties.trafficRoutingMethod")
    private TrafficRoutingMethod trafficRoutingMethod;

    /**
     * The DNS settings of the Traffic Manager profile.
     */
    @JsonProperty(value = "properties.dnsConfig")
    private DnsConfig dnsConfig;

    /**
     * The endpoint monitoring settings of the Traffic Manager profile.
     */
    @JsonProperty(value = "properties.monitorConfig")
    private MonitorConfig monitorConfig;

    /**
     * The list of endpoints in the Traffic Manager profile.
     */
    @JsonProperty(value = "properties.endpoints")
    private List<EndpointInner> endpoints;

    /**
     * Indicates whether Traffic View is 'Enabled' or 'Disabled' for the
     * Traffic Manager profile. Null, indicates 'Disabled'. Enabling this
     * feature will increase the cost of the Traffic Manage profile. Possible
     * values include: 'Enabled', 'Disabled'.
     */
    @JsonProperty(value = "properties.trafficViewEnrollmentStatus")
    private TrafficViewEnrollmentStatus trafficViewEnrollmentStatus;

    /**
     * Maximum number of endpoints to be returned for MultiValue routing type.
     */
    @JsonProperty(value = "properties.maxReturn")
    private Long maxReturn;

    /**
     * Get the status of the Traffic Manager profile. Possible values include: 'Enabled', 'Disabled'.
     *
     * @return the profileStatus value
     */
    public ProfileStatus profileStatus() {
        return this.profileStatus;
    }

    /**
     * Set the status of the Traffic Manager profile. Possible values include: 'Enabled', 'Disabled'.
     *
     * @param profileStatus the profileStatus value to set
     * @return the ProfileInner object itself.
     */
    public ProfileInner withProfileStatus(ProfileStatus profileStatus) {
        this.profileStatus = profileStatus;
        return this;
    }

    /**
     * Get the traffic routing method of the Traffic Manager profile. Possible values include: 'Performance', 'Priority', 'Weighted', 'Geographic', 'MultiValue', 'Subnet'.
     *
     * @return the trafficRoutingMethod value
     */
    public TrafficRoutingMethod trafficRoutingMethod() {
        return this.trafficRoutingMethod;
    }

    /**
     * Set the traffic routing method of the Traffic Manager profile. Possible values include: 'Performance', 'Priority', 'Weighted', 'Geographic', 'MultiValue', 'Subnet'.
     *
     * @param trafficRoutingMethod the trafficRoutingMethod value to set
     * @return the ProfileInner object itself.
     */
    public ProfileInner withTrafficRoutingMethod(TrafficRoutingMethod trafficRoutingMethod) {
        this.trafficRoutingMethod = trafficRoutingMethod;
        return this;
    }

    /**
     * Get the DNS settings of the Traffic Manager profile.
     *
     * @return the dnsConfig value
     */
    public DnsConfig dnsConfig() {
        return this.dnsConfig;
    }

    /**
     * Set the DNS settings of the Traffic Manager profile.
     *
     * @param dnsConfig the dnsConfig value to set
     * @return the ProfileInner object itself.
     */
    public ProfileInner withDnsConfig(DnsConfig dnsConfig) {
        this.dnsConfig = dnsConfig;
        return this;
    }

    /**
     * Get the endpoint monitoring settings of the Traffic Manager profile.
     *
     * @return the monitorConfig value
     */
    public MonitorConfig monitorConfig() {
        return this.monitorConfig;
    }

    /**
     * Set the endpoint monitoring settings of the Traffic Manager profile.
     *
     * @param monitorConfig the monitorConfig value to set
     * @return the ProfileInner object itself.
     */
    public ProfileInner withMonitorConfig(MonitorConfig monitorConfig) {
        this.monitorConfig = monitorConfig;
        return this;
    }

    /**
     * Get the list of endpoints in the Traffic Manager profile.
     *
     * @return the endpoints value
     */
    public List<EndpointInner> endpoints() {
        return this.endpoints;
    }

    /**
     * Set the list of endpoints in the Traffic Manager profile.
     *
     * @param endpoints the endpoints value to set
     * @return the ProfileInner object itself.
     */
    public ProfileInner withEndpoints(List<EndpointInner> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    /**
     * Get indicates whether Traffic View is 'Enabled' or 'Disabled' for the Traffic Manager profile. Null, indicates 'Disabled'. Enabling this feature will increase the cost of the Traffic Manage profile. Possible values include: 'Enabled', 'Disabled'.
     *
     * @return the trafficViewEnrollmentStatus value
     */
    public TrafficViewEnrollmentStatus trafficViewEnrollmentStatus() {
        return this.trafficViewEnrollmentStatus;
    }

    /**
     * Set indicates whether Traffic View is 'Enabled' or 'Disabled' for the Traffic Manager profile. Null, indicates 'Disabled'. Enabling this feature will increase the cost of the Traffic Manage profile. Possible values include: 'Enabled', 'Disabled'.
     *
     * @param trafficViewEnrollmentStatus the trafficViewEnrollmentStatus value to set
     * @return the ProfileInner object itself.
     */
    public ProfileInner withTrafficViewEnrollmentStatus(TrafficViewEnrollmentStatus trafficViewEnrollmentStatus) {
        this.trafficViewEnrollmentStatus = trafficViewEnrollmentStatus;
        return this;
    }

    /**
     * Get maximum number of endpoints to be returned for MultiValue routing type.
     *
     * @return the maxReturn value
     */
    public Long maxReturn() {
        return this.maxReturn;
    }

    /**
     * Set maximum number of endpoints to be returned for MultiValue routing type.
     *
     * @param maxReturn the maxReturn value to set
     * @return the ProfileInner object itself.
     */
    public ProfileInner withMaxReturn(Long maxReturn) {
        this.maxReturn = maxReturn;
        return this;
    }

}
