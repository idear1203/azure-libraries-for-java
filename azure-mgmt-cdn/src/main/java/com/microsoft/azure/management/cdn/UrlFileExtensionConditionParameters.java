/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the parameters for the URL file extension condition.
 */
public class UrlFileExtensionConditionParameters {
    /**
     * The odatatype property.
     */
    @JsonProperty(value = "@odata\\.type", required = true)
    private String odatatype;

    /**
     * A list of extensions for the condition of the delivery rule.
     */
    @JsonProperty(value = "extensions", required = true)
    private List<String> extensions;

    /**
     * Creates an instance of UrlFileExtensionConditionParameters class.
     */
    public UrlFileExtensionConditionParameters() {
        odatatype = "Microsoft.Azure.Cdn.Models.DeliveryRuleUrlFileExtensionConditionParameters";
    }

    /**
     * Get the odatatype value.
     *
     * @return the odatatype value
     */
    public String odatatype() {
        return this.odatatype;
    }

    /**
     * Set the odatatype value.
     *
     * @param odatatype the odatatype value to set
     * @return the UrlFileExtensionConditionParameters object itself.
     */
    public UrlFileExtensionConditionParameters withOdatatype(String odatatype) {
        this.odatatype = odatatype;
        return this;
    }

    /**
     * Get the extensions value.
     *
     * @return the extensions value
     */
    public List<String> extensions() {
        return this.extensions;
    }

    /**
     * Set the extensions value.
     *
     * @param extensions the extensions value to set
     * @return the UrlFileExtensionConditionParameters object itself.
     */
    public UrlFileExtensionConditionParameters withExtensions(List<String> extensions) {
        this.extensions = extensions;
        return this;
    }

}
