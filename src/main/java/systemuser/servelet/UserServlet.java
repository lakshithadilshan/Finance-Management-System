package systemuser.servelet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import user.DTO.ResponseDTO;
import user.Dao.userDao;
import user.Util.InputValidatorSysUser;
import user.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sysuser")
public class UserServlet extends HttpServlet {
    private userDao userDao = new userDao();
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);

    //system user register
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            logger.info("Initiate Employee Register...");
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirmPassword");
            // Validate inputs
            ResponseDTO response = InputValidatorSysUser.validateRegistrationInputs(username, email, password, confirmPassword);
            if (!response.isStatus()){
                // Validation failed
                logger.warn(response.getResponseMsg());
                req.setAttribute("errorMessage", response.getResponseMsg());
                req.getRequestDispatcher("/systemuser/register.jsp").forward(req, resp);
                return;
            }
        try{
                boolean userExist = userDao.getUserByUsernameOrEmail(username,email);
                if (userExist){
                    logger.warn("Username or Email Already Registered");
                    req.setAttribute("errorMessage", "Username or Email Already Registered");
                    req.getRequestDispatcher("/systemuser/register.jsp").forward(req, resp);
                    return;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
                try{
                    //String EncryptedPassword = UserService.PasswordEncryption(password);
                    User user = new User(username,email,password);
                    boolean isRegistered = userDao.userRegister(user);
                    if (isRegistered) {
                        //register success
                        logger.info("Successfully Created!");
                        req.setAttribute("successMessage", "Successfully Created!");
                        req.getRequestDispatcher("/systemuser/register.jsp").forward(req, resp);

                    } else {
                        logger.warn("Fail Registration");
                        req.setAttribute("errorMessage", "Fail Registration! Try again");
                        req.getRequestDispatcher("systemuser/register.jsp").forward(req, resp);

                    }
                }catch (Exception e){
                    logger.warn("Error during registration.");
                    e.printStackTrace();
                    req.setAttribute("errorMessage", "Error during registration.");
                    req.getRequestDispatcher("user/register.jsp").forward(req, resp);
                }
    }
}
