package systemuser.servelet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Get the session, if it exists
        String side = (String) session.getAttribute("side");
                if (session != null) {
            session.invalidate(); // Destroy the session
            logger.info("logout");

        }
                //redirect to settings side login
        if (side.equals("adminPanel")){
            resp.sendRedirect("/FMS/systemuser/settingLogin.jsp"); // Redirect to login page
            //redirect to client services login
        }else {
            resp.sendRedirect("/FMS/systemuser/login.jsp"); // Redirect to login page
        }
    }
}
