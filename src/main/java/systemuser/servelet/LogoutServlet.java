package systemuser.servelet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Get the session, if it exists
        String side = (String) session.getAttribute("side");
                if (session != null) {
            session.invalidate(); // Destroy the session
            System.out.println("logout");
        }
                //redirect to settings side login
        if (side.equals("adminPanel")){
            resp.sendRedirect("/FMS/systemuser/settingLogin.jsp"); // Redirect to login page
            //redirect to clinet services login
        }else {
            resp.sendRedirect("/FMS/systemuser/login.jsp"); // Redirect to login page
        }
    }
}
