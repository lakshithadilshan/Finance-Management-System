package user.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import user.Dao.accountDao;
import user.Util.VarList;

public class AccountMain {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);
    LoanAccountBusiness loanAccountBusiness = new LoanAccountBusiness();
    Integer newAccNum = 0;
    Integer rate = null;
    Integer latestAccNum = null;
    String UserId = "";
    private accountDao accountDao = new accountDao();

    //check the Account balance
    public float checkBalance(String acctype, String accountNumber, Integer accountOwner) {
        float balance = 0.0F;
        try {
            balance = accountDao.checkAccountBalance(acctype, accountNumber);
            logger.info("Check Balance");
        } catch (Exception e) {
            logger.warn("Check Balance Failed");
            e.printStackTrace();
        }
        return balance;
    }

    //create saving account
    public String accountCreation(String accountType, float initialDeposit, int accountOwner, String nic) {
        logger.info("Initiate Saving account creation!");
        boolean checkReady = initAccountCreate(accountType, nic);
        if (checkReady) {
            boolean iscreated = accountDao.createSavingAccount(newAccNum, UserId, initialDeposit, rate, accountOwner);
            if (iscreated) {
                String StrnewAccNum = String.valueOf(newAccNum.toString());
                logger.info("Acc created Success");
                return StrnewAccNum;
            } else {
                logger.warn("failed account creation!");
            }
        }
        return VarList.ACC_CREATION_FAILED;
    }

    //create loan account
    public String accountCreation(String accountType, float initialDeposit, int accountOwner, String nic, String loantime) {
        logger.info("Initiate Loan account creation!");
        float fullInterest = loanAccountBusiness.makeInterestLoan(initialDeposit, loantime);
        float loanAndInterest = initialDeposit + fullInterest;
        int loantimeInt = Integer.parseInt(loantime);
        int timeDurationMonth = loantimeInt * 12;
        boolean checkReady = initAccountCreate(accountType, nic);
        if (checkReady) {
            boolean isCreated = accountDao.createLoanAccount(newAccNum, UserId, initialDeposit, rate, accountOwner, fullInterest, loanAndInterest, loantime ,timeDurationMonth);
            if (isCreated) {
                String StrnewAccNum = String.valueOf(newAccNum);
                logger.info("Loan Acc created Success");
                return StrnewAccNum;
            } else {
                logger.warn("failed Loan account creation!");
            }
        }
        return VarList.ACC_CREATION_FAILED;
    }

    //create FD account
    public String accountCreation(float initialDeposit, String accountType, int accountOwner, String fdtime, String nic) {
        logger.info("Initiate FD account creation!");
        boolean checkReady = initAccountCreate(accountType, nic);
        if (checkReady) {
            boolean isCreated = accountDao.createFDAccount(newAccNum, UserId, initialDeposit, rate, accountOwner, fdtime);
            if (isCreated) {
                String StrnewAccNum = String.valueOf(newAccNum);
                logger.info("FD Acc created Success");
                return StrnewAccNum;
            } else {
                logger.warn("failed FD account creation!");
            }
        }
        return VarList.ACC_CREATION_FAILED;
    }

    //making requirement for account creation
    public boolean initAccountCreate(String accounttype, String nic) {
        boolean status = VarList.STATUS_FALSE;
        //retrieve rate
        Integer rateId = 0;
        if (accounttype.equals("saving_account")) {
            rateId = VarList.SAVING_ACC_RATE;
        } else if (accounttype.equals("loan_account")) {
            rateId = VarList.LOAN_ACC_RATE;
        } else if (accounttype.equals("fd_account")) {
            rateId = VarList.FD_ACC_RATE;
        }
        try {
            //generate new acc number
            newAccNum = accountDao.generateNewAccNumber(accounttype);
            if (newAccNum == 0){
                return status ;
            }
            //check if exist that rate
            rate = accountDao.getRate(rateId);
            //get user id using nic number
            UserId = accountDao.getUserIdByNic(nic);
            //check already account available or not
            boolean isExist = accountDao.checkAccountAlreadyExist(UserId, accounttype);
            if (newAccNum != null && rate != null && !UserId.isEmpty() && !isExist) {
                status = VarList.STATUS_TRUE;
            } else {
                logger.warn("Already Account Exist or Rate not available or User not Registered");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


}
