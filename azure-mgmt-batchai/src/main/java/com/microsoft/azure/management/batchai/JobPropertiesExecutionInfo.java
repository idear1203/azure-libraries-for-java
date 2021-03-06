/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batchai;

import org.joda.time.DateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information about the execution of a job.
 */
public class JobPropertiesExecutionInfo {
    /**
     * Start time.
     * The time at which the job started running. 'Running' corresponds to the
     * running state. If the job has been restarted or retried, this is the
     * most recent time at which the job started running. This property is
     * present only for job that are in the running or completed state.
     */
    @JsonProperty(value = "startTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime startTime;

    /**
     * End time.
     * The time at which the job completed. This property is only returned if
     * the job is in completed state.
     */
    @JsonProperty(value = "endTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime endTime;

    /**
     * Exit code.
     * The exit code of the job. This property is only returned if the job is
     * in completed state.
     */
    @JsonProperty(value = "exitCode", access = JsonProperty.Access.WRITE_ONLY)
    private Integer exitCode;

    /**
     * Errors.
     * A collection of errors encountered by the service during job execution.
     */
    @JsonProperty(value = "errors", access = JsonProperty.Access.WRITE_ONLY)
    private List<BatchAIError> errors;

    /**
     * Get the time at which the job started running. 'Running' corresponds to the running state. If the job has been restarted or retried, this is the most recent time at which the job started running. This property is present only for job that are in the running or completed state.
     *
     * @return the startTime value
     */
    public DateTime startTime() {
        return this.startTime;
    }

    /**
     * Get the time at which the job completed. This property is only returned if the job is in completed state.
     *
     * @return the endTime value
     */
    public DateTime endTime() {
        return this.endTime;
    }

    /**
     * Get the exit code of the job. This property is only returned if the job is in completed state.
     *
     * @return the exitCode value
     */
    public Integer exitCode() {
        return this.exitCode;
    }

    /**
     * Get a collection of errors encountered by the service during job execution.
     *
     * @return the errors value
     */
    public List<BatchAIError> errors() {
        return this.errors;
    }

}
