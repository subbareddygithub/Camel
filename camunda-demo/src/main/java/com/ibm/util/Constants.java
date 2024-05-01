package com.ibm.util;

/**
 * @implSpec Constants class used to maintain constants used in project
 */
public class Constants {

    public static final String PREMIUM_APPROVED = "premium approved";
    public static final String PREMIUM_GENERATED = "premium generated";
    public static final String SMOKING = "smoke";
    public static final String LIVER = "liver";
    public static final String HEART = "heart";
    public static final String DIABETIC = "diabetes";
    public static final String KIDNEY = "kidney";
    public static final String BP = "bp";
    public static final String ALCOHOL = "alcohol";
    public static final String AGE = "age";
    public static final String HIGH = "High";
    public static final String MEDIUM = "Medium";
    public static final String LOW = "Low";
    public static final int BASE_PREMIUM = 10000;
    public static final int HIGH_RISK_PREMIUM = 25;
    public static final int MEDIUM_RISK_PREMIUM = 15;

    public static final String TOTAL_PREMIUM_ANNUAL = "totalpremiumannual";
    public static final String RISK_IDENTIFIED = "riskidentified";
    

    private Constants() {
        throw new IllegalStateException("Constants Utility Class. Cannot be instantiated.");
    }

}
