package user.servelet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import user.DTO.ClientUserDTO;
import user.DTO.ResponseDTO;
import user.Dao.userDao;
import user.Util.InputValidatorClientUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/client")
public class ClientUserServlet extends HttpServlet {
    // client user account creation process
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);
    private userDao userDao = new userDao();
    private final InputValidatorClientUser validator =  new InputValidatorClientUser();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientUserDTO input = new ClientUserDTO();
        input.setcNameinFull(req.getParameter("cNameFull"));
        input.setNamewithInitials(req.getParameter("nameWithInitials"));
        input.setCity(req.getParameter("city"));
        input.setCemail(req.getParameter("cEmail"));
        input.setCnic(req.getParameter("cNic"));
        input.setCmartial_status(req.getParameter("cMartialStatus"));
        input.setCpasswordsign(req.getParameter("cPasswordDesign"));
        input.setAddress(req.getParameter("address"));
        try {
            // Validate input
            ResponseDTO responseDTO = validator.validateParameters(input);
            if (responseDTO.isStatus()) {
                try {
                    //check previously registered or email or id
                    boolean userAlreadyExist = userDao.userAlreadyExist(input);
                    if (!userAlreadyExist) {
                        boolean isRegistered = userDao.registerClientUser(input);
                        if (isRegistered) {
                            logger.info("Registration successful");
                            // Set success message in the request
                            req.setAttribute("successMessage", "Registration successful! You can now create account");
                        } else {
                            logger.info("Registration failed");
                            // Set error message in the request
                            req.setAttribute("errorMessage", "Registration failed. Please try again.");
                        }
                        // Forward to the registration page (or another page as necessary)
                        req.getRequestDispatcher("/user/clientUserRegister.jsp").forward(req, resp);
                    } else {
                        logger.warn("already Register this user ");
                        req.setAttribute("errorMessage", "Already Register this User ,please enter new NIC or email");
                        req.getRequestDispatcher("/user/clientUserRegister.jsp").forward(req, resp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                logger.warn(responseDTO.getResponseMsg());
                req.setAttribute("successMessage", responseDTO.getResponseMsg());
                req.getRequestDispatcher("/user/clientUserRegister.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
