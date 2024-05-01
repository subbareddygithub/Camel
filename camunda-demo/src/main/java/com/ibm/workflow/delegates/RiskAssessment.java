package com.ibm.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibm.util.Constants;
import com.ibm.util.WorkflowLogger;

@Component("RiskAssessment")
public class RiskAssessment implements JavaDelegate{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		int age = 0;
		if(execution.hasVariable(Constants.AGE)) {
			age =  Integer.parseInt(execution.getVariable(Constants.AGE).toString());
		}

		if (execution.hasVariable(Constants.HEART) && (boolean) execution.getVariable(Constants.HEART) ||
			execution.hasVariable(Constants.DIABETIC) && (boolean) execution.getVariable(Constants.DIABETIC) ||
			execution.hasVariable(Constants.KIDNEY) && (boolean) execution.getVariable(Constants.KIDNEY) ||
			execution.hasVariable(Constants.LIVER) && (boolean) execution.getVariable(Constants.LIVER) &&
			age >=50
			) {
			execution.setVariable(Constants.HIGH, Constants.HIGH);
			WorkflowLogger.info(logger, Constants.HIGH, "Risk Identifed. ");
		}else if(execution.hasVariable(Constants.BP) && (boolean) execution.getVariable(Constants.BP) ||
				execution.hasVariable(Constants.SMOKING) && (boolean) execution.getVariable(Constants.SMOKING) ||
				execution.hasVariable(Constants.ALCOHOL) && (boolean) execution.getVariable(Constants.ALCOHOL) &&
				age >30 && age < 50
				) {
			execution.setVariable(Constants.MEDIUM, Constants.MEDIUM);
			WorkflowLogger.info(logger, Constants.MEDIUM, "Risk Identifed. ");
		}else {
			
			WorkflowLogger.info(logger, Constants.LOW, "Risk Identifed. ");
		}
		
	}

}
