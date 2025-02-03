package systemuser.servelet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.util.LoginValidator;
import user.DTO.ResponseDTO;
import user.Dao.userDao;
import user.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private userDao userDao = new userDao();
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String side = req.getParameter("side");
        logger.info("Iniitiate Login...");

        ResponseDTO responseDTO = LoginValidator.validateLoginCredentials(username,password,side);
        if (!responseDTO.isStatus()){
            logger.warn("login credentials error "+responseDTO.getResponseMsg());
            String direction = "";
            if (side.equals("adminPanel")){
                direction = "settingLogin.jsp";
            }else{
                direction = "login.jsp";
            }
            req.setAttribute("errorMessage", responseDTO.getResponseMsg());
            req.getRequestDispatcher("systemuser/"+direction).forward(req, resp);
        } else {
            try {
                User user = userDao.getUserByUsername(username);
                if (user != null){
                    HttpSession session = req.getSession();
                    session.setAttribute("loggedInUser", user);
                    switch (side) {
                        case "adminPanel":
                            if (userDao.getUserByUsername(username) != null && user.getPassword().equals(password)) {
                                // Successful login admin Panel
                                logger.info("Successfully login to admin Panel");
                                session.setAttribute("side", "adminPanel");
                                resp.sendRedirect("admin");
                            } else {
                                // Invalid credentials
                                logger.warn("Invalid username or password");
                                req.setAttribute("errorMessage", "Invalid username or password");
                                req.getRequestDispatcher("systemuser/settingLogin.jsp").forward(req, resp);
                            }
                            break;
                        case "clientSupportPanel":
                            session.setAttribute("side", "clientSupportPanel");

                            if (userDao.getUserByUsername(username) != null && user.getPassword().equals(password)) {
                                logger.info("Successfully login to Customer Service Panel");
                                // Successful login
                                resp.sendRedirect("/FMS/user/accountsMenu.jsp"); // Redirect to account menu page

                            } else {
                                // Invalid credentials
                                logger.warn("Invalid username or password");
                                req.setAttribute("errorMessage", "Invalid username or password");
                                req.getRequestDispatcher("systemuser/login.jsp").forward(req, resp);
                            }
                            break;
                        default:
                            req.setAttribute("errorMessage", "not found panel side");
                            req.getRequestDispatcher("systemuser/login.jsp").forward(req, resp);
                            logger.warn("not found panel side");
                    }
                }else {
                    req.setAttribute("errorMessage", "Invalid username or password");
                    req.getRequestDispatcher("systemuser/login.jsp").forward(req, resp);
                    logger.warn("Invalid Credentials");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
