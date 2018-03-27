/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for ListRoutesTable associated with the Express Route Circuits API.
 */
public class ExpressRouteCircuitsRoutesTableSummaryListResult {
    /**
     * A list of the routes table.
     */
    @JsonProperty(value = "value")
    private List<ExpressRouteCircuitRoutesTableSummary> value;

    /**
     * The URL to get the next set of results.
     */
    @JsonProperty(value = "nextLink")
    private String nextLink;

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public List<ExpressRouteCircuitRoutesTableSummary> value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the ExpressRouteCircuitsRoutesTableSummaryListResult object itself.
     */
    public ExpressRouteCircuitsRoutesTableSummaryListResult withValue(List<ExpressRouteCircuitRoutesTableSummary> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink value.
     *
     * @return the nextLink value
     */
    public String nextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink value.
     *
     * @param nextLink the nextLink value to set
     * @return the ExpressRouteCircuitsRoutesTableSummaryListResult object itself.
     */
    public ExpressRouteCircuitsRoutesTableSummaryListResult withNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }

}