package com.ibm.controller;


import com.ibm.util.Constants;
import com.ibm.util.WorkflowLogger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PolicyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RuntimeService runtimeService;
    
    @PostMapping("/policy/ready/{process-instance-id}")
    @Operation(summary = "life policy generation", tags = {"policy"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Policy done", content = {@Content(schema = @Schema(hidden = true))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(hidden = true))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Unexpected Error", content = {@Content(schema = @Schema(hidden = true))})})
    public ResponseEntity<String> riskAssessment(
            @PathVariable(name = "process-instance-id") String processInstanceId
    ) {

       
        try {
            if (StringUtils.isEmpty(processInstanceId)) {
                WorkflowLogger.error(logger, "Approved", "Process Instance Id cannot be null or empty");
               return ResponseEntity.badRequest().body("Process Instance Id cannot be null or empty");
               
            }
            runtimeService
            .createMessageCorrelation(Constants.PREMIUM_GENERATED)
            .processInstanceId(processInstanceId)
            .correlate();

    return ResponseEntity.ok().body(processInstanceId + " is ready to eat.");
        } catch (Exception e) {
            WorkflowLogger.error(logger, "Premium Ready", "Unknown Exception", e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown Exception. Message: " + e.getMessage());
        }

    }

}
