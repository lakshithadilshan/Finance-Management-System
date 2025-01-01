package user.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import user.DTO.LoanBalanceDTO;
import user.DTO.ResponseDTO;
import user.Dao.accountDao;
import user.Util.VarList;
import user.model.LoanAccount;
import user.model.Rate;

public class LoanAccountBusiness {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);
    float Interest = 0.0F;
    float rateValue = 0.0F;
    Rate rate = null;
    accountDao accountDao = new accountDao();
    Common common = new Common();


//calculate interest of loan
    public float makeInterestLoan(Float amount, String timeduration) {
        try{
            rate = accountDao.getRateDetail(VarList.LOAN_ACC_RATE);
            if (rate != null){
                rateValue = rate.getRate();
            }else {
                logger.warn("Rate not available");
            }
            switch (timeduration) {
                case "1":
                    Interest = amount * (rateValue / 100);
                    break;
                case "2":
                    Interest = amount * ((rateValue / 100) * 2);
                    break;
                case "3":
                    Interest = amount * ((rateValue / 100) * 5);
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();}
        return Interest;
    }

    public ResponseDTO RepayLoan(String accNum, String repayAmount,Integer accountOwner) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(VarList.STATUS_FALSE);
        int time ;
        float rePayAmount = Float.parseFloat(repayAmount);
        float fullInterest =0.0F;
        float monthlyInterest = 0.0F;
        float fullLoanAmount,monthlyFeeAndInterest =0.0F;
        float monthlyAmount = 0.0F;
        float balance = 0.0F;
        float payedAmount = 0.0F;


        try {
            LoanAccount account = accountDao.getLoanAccDetails(accNum);
            fullLoanAmount = account.getFullLoanAmount();
            fullInterest = account.getFullInterest();
            balance = account.getBalance();
            payedAmount = account.getPayedamount();
            time = account.getTimeDuration();
            switch (time){
                case 1:
                    monthlyInterest = fullInterest/12;
                    monthlyAmount = fullLoanAmount/12;
                    break;
                case 2:
                    monthlyInterest = fullInterest/24;
                    monthlyAmount = fullLoanAmount/24;

                    break;
                case 3:
                    monthlyInterest = fullInterest/36;
                    monthlyAmount = fullLoanAmount/36;
                    break;
            }
            if (balance != fullLoanAmount){
                monthlyFeeAndInterest = monthlyInterest + monthlyAmount;
                if (rePayAmount >= monthlyFeeAndInterest || balance == rePayAmount && !(balance > rePayAmount)){
                    if (payedAmount != fullLoanAmount + fullInterest){
                        payedAmount = payedAmount + rePayAmount ;
                    }else if(payedAmount == fullLoanAmount + fullInterest){
                        responseDTO.setStatus(VarList.STATUS_FALSE);
                        responseDTO.setResponseMsg("Loan already payed done");
                        logger.info("Loan already payed done");
                        return responseDTO;
                    }
                    //decrease the balance
                    balance = balance - rePayAmount;
                    //insert to transaction
                        boolean isExistTxnType = accountDao.checkTxnTypeExist(VarList.TXNTYPE_REPAYLOAN);
                        if (isExistTxnType){
                            Integer repayA = Integer.valueOf(repayAmount);
                            boolean addAmountInterest = accountDao.InsertPayedDetails(payedAmount,balance,accNum);
                            if (addAmountInterest){
                                boolean statusTxn = common.makeTransaction(accNum,repayA,VarList.TXNTYPE_REPAYLOAN,accountOwner);
                                if (statusTxn){
                                    responseDTO.setStatus(VarList.STATUS_TRUE);
                                }else {
                                    logger.warn("Txn table update failed");
                                }
                            }
                        }else{
                            responseDTO.setResponseMsg("This Transaction Type is Disable this time");
                            logger.info("This Transaction Type is Disable this time");
                        }
                }else {
                    //able to pay one time and able to pay low payment than monthly payment
                    responseDTO.setStatus(VarList.STATUS_FALSE);
                    responseDTO.setResponseMsg("Not enough money for this month payment OR if  you pay last payment or remain full payment pay only remain balance..");
                    logger.info("Not enough money for this month payment OR if  you pay last payment or remain full payment pay only remain balance..");
                }
            }else {
                logger.info("loan Already payed anc closed account");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTO;
    }

    public ResponseDTO checkBalanceandMonthlyCharge(String accNum) {
        ResponseDTO respDTO = new ResponseDTO();
        float balance = 0.0F;
        try{
            LoanBalanceDTO loanBalanceDTO = accountDao.checkBalanceandMonthlyCharge(accNum);
            if (loanBalanceDTO == null){
                respDTO.setStatus(VarList.STATUS_FALSE);
                respDTO.setResponseMsg("Not Found Account");
            }else {
                if (loanBalanceDTO.getBalance() > 0){
                    balance = loanBalanceDTO.getBalance();
                    float fullLoan = (loanBalanceDTO.getFullInterest() + loanBalanceDTO.getFullLoanAmount());
                    int time = loanBalanceDTO.getTimeDuration();
                    float monthltCharge = 0.0F;
                    switch (time) {
                        case 1:
                            monthltCharge = fullLoan / 12;
                            break;
                        case 2:
                            monthltCharge = fullLoan / 24;
                            break;
                        case 5:
                            monthltCharge = fullLoan / 60;
                            break;
                        default:
                            logger.warn("cant found time duration");
                    }
                    respDTO.setStatus(VarList.STATUS_TRUE);
                    respDTO.setResponseMsg("LKR " + balance + " / " + "Your Monthly Payment:" +"LKR "+ monthltCharge);
                }else {
                    respDTO.setResponseMsg(" Account Closed");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return respDTO;
    }
}
