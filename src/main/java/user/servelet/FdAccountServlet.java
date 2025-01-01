package user.servelet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import user.DTO.FdAccounDTO;
import user.DTO.ResponseDTO;
import user.Dao.accountDao;
import user.Services.FixedDepositAccount;
import user.Util.VarList;
import user.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/fdAccount")
public class FdAccountServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);
    FixedDepositAccount fixedDepositAccount = new FixedDepositAccount();
    accountDao accountDao = new accountDao();
    //check fixed deposit details
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FdAccounDTO fdDTO = new FdAccounDTO();
        fdDTO.setAccNumber(req.getParameter("accNum"));
        fdDTO.setOperation(req.getParameter("operation"));
        fdDTO.setAccType(VarList.FD_ACC);
        String maturityDate = "";
        float InterestEarn = 0.0F;
        HttpSession session = req.getSession();
        //accountOwner is logged in user who is bank employee
        int accountOwner = 0;
        if (session != null) {
            // Retrieve the User object
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser != null) {
                // Extract user_id from the User object
                accountOwner = loggedInUser.getUser_id();
                fdDTO.setAccOwner(accountOwner);
            } else {
                resp.sendRedirect("systemuser/login.jsp"); // Redirect to login if User object is null
            }
        } else {
            resp.sendRedirect("systemuser/login.jsp"); // Redirect if session does not exist
        }
        if (fdDTO.getAccNumber() == null || fdDTO.getAccNumber().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Account number is required.");
            return;
        }
        //check account exist
        Integer checkClosedAc = accountDao.getAccStatus(fdDTO.getAccNumber());
        //check account closed or not.. time duration return 0 is account is closed
        if (checkClosedAc != null ) {
            if (checkClosedAc > 0) {
                switch (fdDTO.getOperation()) {
                    //withdraw money
                    //when someone withdraw the fd money bank deduct the 10% tax from interest
                    case "fdWithdraw":
                        logger.info("Initiate FD Withdraw ..");
                        ResponseDTO isWithdraw = fixedDepositAccount.fdWithdraw(fdDTO);
                        if (isWithdraw.isStatus()) {
                            req.setAttribute("successMsg", "Withdraw Success! ");
                            req.setAttribute("successMsgFD", "Withdraw Success! Note:bank keep 10% of interest as a Tax");
                            req.getRequestDispatcher("/user/fdAccount.jsp").forward(req, resp);

                        } else {
                            req.setAttribute("errorMessage", "Withdraw Failed! ");
                            req.setAttribute("successMsgFD", "Withdraw Failed! " + isWithdraw.getResponseMsg());
                            req.getRequestDispatcher("/user/fdAccount.jsp").forward(req, resp);

                        }
                        break;
                    case "fdDetails":
                        logger.info("Initiate FD details retrieve process ..");
                        try {
                                // Retrieve maturity date and interest earned
                                maturityDate = fixedDepositAccount.getMaturityDate(fdDTO.getAccNumber());
                                InterestEarn = fixedDepositAccount.MakeInterest(fdDTO.getAccNumber());
                                String InterestEarnStr = InterestEarn >= 0 ? String.valueOf(InterestEarn) : "Invalid interest calculation";
                                req.setAttribute("mDate", maturityDate != null && !maturityDate.isEmpty() ? maturityDate : "No maturity date available");
                                req.setAttribute("interestEarned", InterestEarnStr);
                                req.getRequestDispatcher("/user/fdAccount.jsp").forward(req, resp);


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                }
            } else {

                req.setAttribute("errorMessage", "Account is closed");
                req.getRequestDispatcher("/user/fdAccount.jsp").forward(req, resp);
                logger.info("Account is closed");
            }
        }else {
            req.setAttribute("errorMessage", "Not Available Account");
            req.getRequestDispatcher("/user/fdAccount.jsp").forward(req, resp);
            logger.info("Not Available Account");
        }
    }
}
