package user.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import user.DTO.FdAccounDTO;
import user.DTO.ResponseDTO;
import user.Dao.accountDao;
import user.Util.VarList;
import user.model.FdAccount;
import user.model.Rate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FixedDepositAccount {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);
    accountDao accountDao = new accountDao();
    Common common = new Common();
    float balance = 0.0F;
    public String getMaturityDate(String accNum) {
        //get created date and time duration
        FdAccount fdAccount = null;
        String formattedDate = "";
        try {
            fdAccount = accountDao.getFdAccountDetailByAccNum(accNum);
            //check time duration 0 or not then return msg closed account

            // Your date string from the database
            if (fdAccount != null) {
                String createdDate = String.valueOf(fdAccount.getCreateddate());
                LocalDate date = LocalDate.parse(createdDate);
                LocalDate newDate = null;
                // add months for make maturity date
                switch (fdAccount.getTimeduration()){
                    case 1:
                         newDate = date.plusMonths(1);
                        break;
                    case 3:
                         newDate = date.plusMonths(3);
                        break;
                    case 12:
                         newDate = date.plusMonths(12);
                        break;
                    case 24:
                         newDate = date.plusMonths(24);
                        break;
                }

                // Format the new date back to a string if needed
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formattedDate = newDate.format(formatter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public float MakeInterest(String accNum) {
        float Interest = 0.0F;
        Rate rate = null;
        balance = 0.0F;
        float rateValue = 0.0F;
        float onemonthrateValue = 0.0F;
        Integer timeduration = 0;
        // check exist rate and getRate
        try {
            rate = accountDao.getRateDetail(VarList.FD_ACC_RATE);
            if (rate != null) {
                rateValue = rate.getRate();
            } else {
                logger.warn("Rate not available");
            }
            balance = accountDao.checkAccountBalance(VarList.FD_ACC, accNum);
            timeduration = accountDao.getTimeDuration(accNum, VarList.FD_ACC);
            if (balance > 0 && timeduration != null) {
                onemonthrateValue = rateValue / 12;
                Interest = balance * (onemonthrateValue / 100) * timeduration;
            } else {
                logger.warn("balance LKR : 0 or timeduration nullable");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Interest;
    }

    public ResponseDTO fdWithdraw(FdAccounDTO fd) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(VarList.STATUS_FALSE);
        String matDate = getMaturityDate(fd.getAccNumber());
        float Interest = MakeInterest(fd.getAccNumber());
        float tax = Interest * (VarList.FD_ACC_TAX/100);
        if (!matDate.isEmpty() && Interest > 0 ){
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Parse matDate into LocalDate
            LocalDate maturityDate = LocalDate.parse(matDate, formatter);
            // Check if today is after maturityDate
            if (today.isAfter(maturityDate)) {
                    boolean isExistTxnType = accountDao.checkTxnTypeExist(VarList.TXNTYPE_WITHDRAW);
                    if (isExistTxnType){
                        boolean iswithdraw = accountDao.withdrawFDMoney(fd.getAccNumber(), balance,tax);
                        if (iswithdraw){
                            //insert to transaction table
                            boolean isTxncompplete = common.makeTransaction(fd.getAccNumber(), (int) balance, VarList.TXNTYPE_WITHDRAW,fd.getAccOwner());
                            if (isTxncompplete){
                                responseDTO.setStatus(VarList.STATUS_TRUE);
                            }else {
                                responseDTO.setResponseMsg("Transaction Failed");
                                logger.warn("Transaction Failed");
                            }
                        }else {
                            responseDTO.setResponseMsg("Withdraw failed");
                            logger.warn("Withdraw failed");
                        }
                    }else {
                        responseDTO.setResponseMsg("Transaction Type Disabled");
                        logger.warn("Transaction Type Disabled");
                    }
            }else{
                responseDTO.setResponseMsg("Not meet maturity date");
                logger.info("Not meet maturity date");
                }
        }else {
            responseDTO.setResponseMsg("Maturity date or Interest failed");
            logger.info("Maturity date or Interest failed");
        }
        return responseDTO;
    }

}
