package user.Services;

import user.Dao.accountDao;
import user.Util.VarList;

public class Common {
    private accountDao accountDao = new accountDao();
    //make transactions
    protected boolean makeTransaction(String accountNumber, Integer Amount,String txntype,Integer accountOwner){
         boolean status = VarList.STATUS_FALSE;
         status = accountDao.makeTransacation(accountNumber,Amount,txntype,accountOwner);
         if (status){
             return status;
         }
        return status;
    }
}
