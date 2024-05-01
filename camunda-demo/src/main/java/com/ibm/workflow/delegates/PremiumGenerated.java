package com.ibm.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibm.util.Constants;
import com.ibm.util.WorkflowLogger;

@Component("PremiumGenerated")
public class PremiumGenerated implements JavaDelegate {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		double basePremium = Constants.BASE_PREMIUM;
		double premium;
		String risk;
		if (execution.hasVariable(Constants.HIGH)) {
			execution.setVariable(Constants.RISK_IDENTIFIED, Constants.HIGH);
			premium = basePremium + Constants.HIGH_RISK_PREMIUM * basePremium;
		} else if (execution.hasVariable(Constants.MEDIUM)) {
			execution.setVariable(Constants.RISK_IDENTIFIED, Constants.MEDIUM);
			premium = basePremium + Constants.MEDIUM_RISK_PREMIUM * basePremium;
		} else {
			execution.setVariable(Constants.RISK_IDENTIFIED, Constants.LOW);
			premium = basePremium;
		}
		execution.setVariable(Constants.TOTAL_PREMIUM_ANNUAL, premium);
		WorkflowLogger.info(logger, "" + premium, "Premium Generated. ");

	}

}
