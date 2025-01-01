package user.servelet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import user.DTO.ResponseDTO;
import user.Services.AccountMain;
import user.Util.InputValidatorAcc;
import user.Util.VarList;
import user.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);
    private final InputValidatorAcc inputValidatorAcc = new InputValidatorAcc();
//account creation process)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //vaidation input param
        ResponseDTO responseDTO = inputValidatorAcc.validateParameters(req);
        if (responseDTO.isStatus()) {
            String nic = req.getParameter("nic");
            String accountType = req.getParameter("accountType");
            String loanTime = req.getParameter("loanTimeDuration");
            float initialDeposit = Float.parseFloat(req.getParameter("initialDeposit"));
            String fdtime = req.getParameter("fdTimeDuration");
            AccountMain accountMain = new AccountMain();
            String newAccNum = "";
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
                    logger.info("Initiate Account Creation..");
                    try {
                        switch (accountType) {
                            //saving account creation
                            case VarList.SAVING_ACC:
                                //main acc saving
                                newAccNum = accountMain.accountCreation(accountType, initialDeposit, accountOwner, nic);
                                break;
                            case VarList.LOAN_ACC:
                                //main acc loan
                                newAccNum = accountMain.accountCreation(accountType, initialDeposit, accountOwner, nic, loanTime);
                                break;
                            //fd account creation
                            case VarList.FD_ACC:
                                newAccNum = accountMain.accountCreation(initialDeposit, accountType, accountOwner, fdtime, nic);
                                break;
                        }
                        if (!newAccNum.isEmpty() && newAccNum != null || newAccNum.length() >= 4) {
                            logger.info("Generated new account Number");
                            // Set the balance as a request attribute
                            if(!resp.isCommitted()) {
                                req.setAttribute("newAccNum", newAccNum);
                                // Forward the request to the JSP page
                                req.getRequestDispatcher("/user/successCreatedSavingAcc.jsp").forward(req, resp);
                            }
                        } else {
                            logger.info("Does not generated new account Number");
                            resp.sendRedirect("/FMS/user/accountCreation.jsp"); // Redirect to account create page
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!resp.isCommitted()) {
                            req.setAttribute("newAccNum", "Error during Account Creation.");
                            req.getRequestDispatcher("/user/successCreatedSavingAcc.jsp").forward(req, resp);
                        }
                    }
        }else {
//            req.setAttribute("newAccNum", responseDTO.getResponseMsg());
//            req.getRequestDispatcher("/user/successCreatedSavingAcc.jsp").forward(req, resp);
            req.setAttribute("errorMessage", responseDTO.getResponseMsg());
            req.getRequestDispatcher("/user/accountCreation.jsp").forward(req, resp);
            logger.warn("Invalid Input param:Redirect back");
        }
    }
}
