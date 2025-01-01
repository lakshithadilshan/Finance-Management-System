package user.Util;

public class VarList {
    //transaction types
    public static final String TXNTYPE_DEPOSIT = "1";
    public static final String TXNTYPE_WITHDRAW = "2";
    public static final String TXNTYPE_REPAYLOAN = "3";

    //status
    public static final boolean STATUS_TRUE = true;
    public static final boolean STATUS_FALSE = false;

    //table
    public static final String SAVING_ACC = "saving_account";
    public static final String LOAN_ACC = "loan_account";
    public static final String FD_ACC = "fd_account";

    //RATE ID
    public static final Integer SAVING_ACC_RATE = 2;
    public static final Integer LOAN_ACC_RATE = 3;
    public static final Integer FD_ACC_RATE = 1;

    //failed messages
    public static final String ACC_CREATION_FAILED = "Account creation failed";
    public static final String ACC_NOT_AVAILABLE = "Not available Account";


    //tax
    public static final float FD_ACC_TAX = 10;

    //responsecode
    public static final String RESPONSE_CODE_SUCESS = "00";
    public static final String RESPONSE_CODE_FALSE = "01";








}
