package user.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import user.DTO.ResponseDTO;
import user.Dao.accountDao;
import user.Util.VarList;

public class SavingAccount extends AccountMain{
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);
    private accountDao accountDao = new accountDao();
    private Common common = new Common();

    @Override
    public float checkBalance(String accountType, String accountNumber,Integer accountOwner) {
        float balance = 0.0F;
        try{
            //commented code is use for login user  able to only check own balance
            if (accountType != null || !accountType.isEmpty()  || !accountNumber.isEmpty()){
                balance = accountDao.checkAccountBalance(accountType,accountNumber);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return balance;
    }

    public ResponseDTO depositSavingAcc(String accountNumber, Integer depositAmount, Integer accountOwner) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            boolean checkAccount = accountDao.checkAccount(accountNumber,VarList.SAVING_ACC);
            if (checkAccount){
                boolean isExistTxnType = accountDao.checkTxnTypeExist(VarList.TXNTYPE_DEPOSIT);
                if (isExistTxnType){
                    boolean isdeposited = accountDao.depositSavings(accountNumber,depositAmount);
                    if (isdeposited){
                        //insert to transaction table
                        try{
                            boolean isTxncompplete = common.makeTransaction(accountNumber,depositAmount, VarList.TXNTYPE_DEPOSIT,accountOwner);
                            if (isTxncompplete){
                                responseDTO.setStatus(VarList.STATUS_TRUE);
                                responseDTO.setResponseMsg("Deposit Success!");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }else {
                    responseDTO.setStatus(VarList.STATUS_FALSE);
                    responseDTO.setResponseMsg("Deposit Failed ! Transaction Type Disabled");
                    logger.warn("Transaction Type Disabled");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTO;
    }

    public ResponseDTO withdrawSavingAcc(String accNumber, Integer withdrawAmount, int accountOwner) {
        ResponseDTO responseDTO = new ResponseDTO();
        boolean status = VarList.STATUS_FALSE;
        try{
            boolean isExistTxnType = accountDao.checkTxnTypeExist(VarList.TXNTYPE_WITHDRAW);
            if (isExistTxnType) {
                boolean iswithdraw = accountDao.withdrawMoney(accNumber, withdrawAmount, accountOwner);
                if (iswithdraw) {
                    //insert to transaction table
                    boolean isTxncompplete = common.makeTransaction(accNumber, withdrawAmount, VarList.TXNTYPE_WITHDRAW, accountOwner);
                    if (isTxncompplete) {
                        status = VarList.STATUS_TRUE;
                        responseDTO.setStatus(VarList.STATUS_TRUE);
                        responseDTO.setResponseMsg("Withdraw Success !");
                    }
                }
            }else {
                responseDTO.setStatus(VarList.STATUS_FALSE);
                responseDTO.setResponseMsg("Withdraw Failed ! Transaction Type Disabled");
                logger.warn("Transaction Type Disabled");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseDTO;
    }
}
