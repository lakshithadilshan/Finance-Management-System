package user.servelet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import user.DTO.ResponseDTO;
import user.Dao.accountDao;
import user.Services.LoanAccountBusiness;
import user.Util.VarList;
import user.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loan")
public class LoanAccountServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        accountDao accDao = new accountDao();
        LoanAccountBusiness loanAccountBusiness = new LoanAccountBusiness();
        HttpSession session = req.getSession();
        //accountOwner is logged in user who is bank employee
        int accountOwner = 0;
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
        float balance = 0.0F;
        String accNum = req.getParameter("accnumber");
        String operation = req.getParameter("operation");
        String repayAmount = req.getParameter("repaymentAmount");
        //validation
        if (accNum.length() != 8 ||accNum == null || accNum.isEmpty() && operation == null || operation.isEmpty() ) {
            logger.warn("account number empty ,null or not completed 8 numbers");
            req.setAttribute("errorMessage", "Account number is Empty or Incorrect.");
            req.getRequestDispatcher("/user/loanAccount.jsp").forward(req, resp);
        }else {
            try {
                //check account exists or not
                boolean checkAccExist = accDao.checkAccount(accNum, VarList.LOAN_ACC);
                if (!checkAccExist) {
                    req.setAttribute("errorMessage", VarList.ACC_NOT_AVAILABLE);
                    req.getRequestDispatcher("/user/loanAccount.jsp").forward(req, resp);
                    logger.warn(VarList.ACC_NOT_AVAILABLE);
                    return;
                }
                String sucessMsg = "";
                switch (operation) {
                    case "repay":
                        float fRepayAmount = Float.parseFloat(repayAmount);
                        if (fRepayAmount < 0 ||repayAmount == null || repayAmount.isEmpty()) {
                            sucessMsg = "Amount must be grater than 0 and not be empty";
                            session.setAttribute("LoansucessMsg", sucessMsg);
                            resp.sendRedirect("/FMS/user/loanAccount.jsp");
                            logger.info("Amount must be grater than 0 and not be empty");
                        } else {
                            ResponseDTO status = loanAccountBusiness.RepayLoan(accNum, repayAmount, accountOwner);
                            if (status.isStatus()) {
                                req.setAttribute("successMsg", "Payment Successfully");
                                logger.info("Payment Successfully");
                            } else {
                                req.setAttribute("errorMessage", "Payment Unsuccessful! :" + status.getResponseMsg());
                                logger.warn("Payment Unsuccessful! ");
                            }
                            req.getRequestDispatcher("/user/loanAccount.jsp").forward(req, resp);

                        }
                        break;
                    case "checkbalance":
                        try {
                            logger.info("Initiate Loan Acc Check Balance..");
                            //return the balance and monthly charge
                            ResponseDTO balMoncharge = loanAccountBusiness.checkBalanceandMonthlyCharge(accNum);
                            if (balMoncharge.isStatus()) {
                                req.setAttribute("remainBalance", balMoncharge.getResponseMsg());
                            } else {
                                req.setAttribute("errorMessage", balMoncharge.getResponseMsg());
                            }
                            req.getRequestDispatcher("/user/loanAccount.jsp").forward(req, resp);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("error Loan Acc Check Balance..");
                            req.setAttribute("errorMessage", "error Loan Acc Check Balance..");
                            req.getRequestDispatcher("/user/loanAccount.jsp").forward(req, resp);
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
