/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The parameters used to update a firewall rule while updating a Data Lake
 * Analytics account.
 */
@JsonFlatten
public class UpdateFirewallRuleWithAccountParameters {
    /**
     * The unique name of the firewall rule to update.
     */
    @JsonProperty(value = "name", required = true)
    private String name;

    /**
     * The start IP address for the firewall rule. This can be either ipv4 or
     * ipv6. Start and End should be in the same protocol.
     */
    @JsonProperty(value = "properties.startIpAddress")
    private String startIpAddress;

    /**
     * The end IP address for the firewall rule. This can be either ipv4 or
     * ipv6. Start and End should be in the same protocol.
     */
    @JsonProperty(value = "properties.endIpAddress")
    private String endIpAddress;

    /**
     * Get the unique name of the firewall rule to update.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the unique name of the firewall rule to update.
     *
     * @param name the name value to set
     * @return the UpdateFirewallRuleWithAccountParameters object itself.
     */
    public UpdateFirewallRuleWithAccountParameters withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the start IP address for the firewall rule. This can be either ipv4 or ipv6. Start and End should be in the same protocol.
     *
     * @return the startIpAddress value
     */
    public String startIpAddress() {
        return this.startIpAddress;
    }

    /**
     * Set the start IP address for the firewall rule. This can be either ipv4 or ipv6. Start and End should be in the same protocol.
     *
     * @param startIpAddress the startIpAddress value to set
     * @return the UpdateFirewallRuleWithAccountParameters object itself.
     */
    public UpdateFirewallRuleWithAccountParameters withStartIpAddress(String startIpAddress) {
        this.startIpAddress = startIpAddress;
        return this;
    }

    /**
     * Get the end IP address for the firewall rule. This can be either ipv4 or ipv6. Start and End should be in the same protocol.
     *
     * @return the endIpAddress value
     */
    public String endIpAddress() {
        return this.endIpAddress;
    }

    /**
     * Set the end IP address for the firewall rule. This can be either ipv4 or ipv6. Start and End should be in the same protocol.
     *
     * @param endIpAddress the endIpAddress value to set
     * @return the UpdateFirewallRuleWithAccountParameters object itself.
     */
    public UpdateFirewallRuleWithAccountParameters withEndIpAddress(String endIpAddress) {
        this.endIpAddress = endIpAddress;
        return this;
    }

}
