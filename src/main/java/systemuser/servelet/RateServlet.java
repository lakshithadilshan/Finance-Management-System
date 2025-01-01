package systemuser.servelet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.Dao.rateDao;
import systemuser.model.Rate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/rates")
public class RateServlet extends HttpServlet {
    private rateDao rateDao = new rateDao();
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);

    //get all rates
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            List<Rate> rates = rateDao.getAllrates();
            req.setAttribute("rates",rates);
            req.getRequestDispatcher("../FMS/systemuser/rate.jsp").forward(req,resp);
        }catch (Exception e){
            e.printStackTrace();
            req.setAttribute("errorMessage", "Error loading rates.");
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }

    }

    //update rates
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        logger.info("Initiate Update Rate...");
        try {
            //get param from form
            String rateid = req.getParameter("rateId");
            String newRate = req.getParameter("rate");
            if (!rateid.isEmpty() || !newRate.isEmpty()){
                //update
                boolean isUpdatedd = rateDao.updateRate(rateid,newRate);
                if (isUpdatedd){
                    logger.info("Successfully Updated Rate: {}", rateid);
                    session.setAttribute("updateRMsg","Successfully Updated Rate: " + rateid);
                    resp.sendRedirect("admin");
                }else {
                    logger.warn("Updated Failed Rate: {}", rateid);
                    session.setAttribute("errorMessage","Warning: Updated Failed Rate: "+ rateid);
                    resp.sendRedirect("admin");
                }
            }else {
                logger.warn("Input Pramaters empty...");
                session.setAttribute("errorMessage","Update failed:Input Pramaters empty");
                resp.sendRedirect("admin");
            }
        }catch (Exception e){
            logger.error("Error updating rate.");
            e.printStackTrace();
            req.setAttribute("errorMessage", "Error updating rate.");
            resp.sendRedirect("admin");
        }
    }
}