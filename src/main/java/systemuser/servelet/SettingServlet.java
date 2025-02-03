package systemuser.servelet;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.Dao.rateDao;
import systemuser.Dao.transactionTypeDao;
import systemuser.model.Rate;
import systemuser.model.TransactionType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class SettingServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);
    //get transaction types and rates
    rateDao rateDao = new rateDao();
    transactionTypeDao typeDao = new transactionTypeDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            logger.info("Loading transaction types and rates...");
            // Load transaction types and rates
            List<TransactionType> transactionTypes = typeDao.getAllTransactionTypes();
            List<Rate> rates = rateDao.getAllrates();
            if (!transactionTypes.isEmpty()  || !rates.isEmpty()){
                req.setAttribute("transactionTypes", transactionTypes);
                req.setAttribute("rates", rates);
            }else {
                logger.warn("No data Found Transaction Type or Rates...");
                req.setAttribute("errorMessage", "Error loading data.");
            }
            // Forward to the JSP
            req.getRequestDispatcher("/systemuser/setting.jsp").forward(req, resp);


        } catch (Exception e) {
            logger.error("Error loading data");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String updateStatus = req.getParameter("updateStatus");
        String txnId = req.getParameter("txnId");
        logger.info("Initiate Update Transaction Type...");
        if (!updateStatus.isEmpty() || !txnId.isEmpty()){
            try{
                boolean status = typeDao.isExistTransactionType(txnId);
                if (status){
                    boolean isUpdated = typeDao.updateStatusTxnType(txnId,updateStatus);
                    if (isUpdated){
                        //make a response msg
                        String  updateMsg = "";
                        if (updateStatus.equals("0")){
                            logger.info("Successfully Enabled!");
                            req.setAttribute("updateRMsg", "Successfully Enabled!");
                        }else {
                            logger.info("Successfully Disabled!");
                            req.setAttribute("updateRMsg", "Successfully Disabled!");
                        }
                        // call doGet method
                        doGet(req, resp);
                    }else {
                        logger.warn("Update Failed!");
                        req.setAttribute("errorMessage", "Update Failed!");
                        doGet(req, resp);
                    }
                }else {
                    logger.warn("Does not exist Transaction type");
                    req.setAttribute("errorMessage", "Does not exist Transaction type");
                    doGet(req, resp);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            logger.warn("Empty Input Parameters");
        }
    }
}
