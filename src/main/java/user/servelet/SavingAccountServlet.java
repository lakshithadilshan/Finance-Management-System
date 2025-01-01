package user.servelet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import user.DTO.ResponseDTO;
import user.Dao.accountDao;
import user.Services.SavingAccount;
import user.Util.VarList;
import user.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/savingacc")
public class SavingAccountServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);
    private SavingAccount account = new SavingAccount();
    accountDao accountDao = new accountDao();
    //saving account deposit ,withdraw,check balance)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer Amount = 0;
        String errorMSG ="";
        boolean status = VarList.STATUS_TRUE;
            //accountOwner is logged in user who is bank employee
            int accountOwner = 0;
            // Get the session
            HttpSession session = req.getSession(false);
            if (session != null) {
                // Retrieve the User object
                User loggedInUser = (User) session.getAttribute("loggedInUser");
                if (loggedInUser != null) {
                    // Extract user_id from the User object
                    accountOwner = loggedInUser.getUser_id();
                } else {
                    resp.sendRedirect("systemuser/login.jsp"); // Redirect to login if User object is null
                }
            } else {
                resp.sendRedirect("systemuser/login.jsp"); // Redirect if session does not exist
            }
        //validation input param
        String accountNumber = req.getParameter("accountNumber");
        String operation = req.getParameter("operation");
        if (operation.equals("withdraw") || operation.equals("deposit")){
            Amount = Integer.valueOf(req.getParameter("amount"));
        }
        if (accountNumber == null || accountNumber.isEmpty() || operation == null || operation.isEmpty() ) {
            errorMSG = "Account number is required.";
            status = VarList.STATUS_FALSE;
            return;
        } else if ((operation.equals("withdraw") || operation.equals("deposit")) && (Amount < 0)) {
            errorMSG = "Amount should be more than 0";
            status = VarList.STATUS_FALSE;
            return;
        }
        if (status) {
            switch (operation) {
                case "deposit":
                    try {
                        ResponseDTO isDeposited = account.depositSavingAcc(accountNumber, Amount, accountOwner);

                        if (isDeposited.isStatus()) {
                            logger.info("Deposit Success");
                            req.setAttribute("successMsg", isDeposited.getResponseMsg());
                        } else {
                            logger.warn("Deposit Failed!");
                            req.setAttribute("errorMessage", isDeposited.getResponseMsg());
                        }
                        req.getRequestDispatcher("/user/savingAccount.jsp").forward(req, resp);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case "withdraw":
                    try {
                        ResponseDTO isWithdrawed = account.withdrawSavingAcc(accountNumber, Amount, accountOwner);

                        if (isWithdrawed.isStatus()) {
                            logger.info("Withdrawal Success");
                            req.setAttribute("successMsg", isWithdrawed.getResponseMsg());
                        } else {
                            logger.warn("Withdrawal Unsuccessful");
                            req.setAttribute("errorMessage", isWithdrawed.getResponseMsg());
                        }
                        req.getRequestDispatcher("/user/savingAccount.jsp").forward(req, resp);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "checkBalance":
                    try {
                        logger.info("Initiate check saving acc balance process..");
                        boolean checkAccExist = accountDao.checkAccount(accountNumber, VarList.SAVING_ACC);
                        if (checkAccExist) {
                            float balance = account.checkBalance(VarList.SAVING_ACC, accountNumber, accountOwner);
                            req.setAttribute("balance", balance);
                            logger.warn("Check balance");
                        } else {
                            logger.warn("Does not exist Account");
                            req.setAttribute("errorMessage", "Does not exist Account");
                        }
                        req.getRequestDispatcher("/user/savingAccount.jsp").forward(req, resp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
        }else {
            req.setAttribute("errorMessage",  errorMSG);
            req.getRequestDispatcher("/user/savingAccount.jsp").forward(req, resp);
            logger.warn("Invalid Input param:Redirect back");
        }
    }
}
