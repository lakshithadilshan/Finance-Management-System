package user.Dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import systemuser.servelet.SettingServlet;
import systemuser.util.DatabaseUtil;
import user.DTO.LoanBalanceDTO;
import user.Util.VarList;
import user.model.Account;
import user.model.FdAccount;
import user.model.LoanAccount;
import user.model.Rate;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class accountDao {
    private static final Logger logger = (Logger) LogManager.getLogger(SettingServlet.class);

    public float checkAccountBalance(String accType, String accountNumber) {
        float balance = 0.0F;
        String sql = "SELECT balance FROM " + accType + " WHERE account_number = ?";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                balance = resultSet.getFloat("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching account balance.", e);
        }

        return balance;
    }

    public Integer getLatestAccNum(String accounttype) {
        String sql = "select account_number from " + accounttype + " order by account_number desc limit 1 ";
        Integer LastAccNum = 0;
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Retrieve the last account num
                LastAccNum = resultSet.getInt("account_number");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching Last account number.", e);
        }
        return LastAccNum;
    }

    public Integer getRate(Integer rateId) {
        Integer rate_id = 0;
        String sql = "select rate_id from rate where rate_id =?;";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, rateId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Retrieve the rate
                rate_id = resultSet.getInt("rate_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching rate.", e);
        }
        return rate_id;
    }

    //create Saving Account
    public boolean createSavingAccount(Integer newAccNum, String UserId, float initialdeposit, Integer rate, int accountOwner) {
        String sql = "INSERT INTO saving_account (account_number, account_owner, balance, rate,createdby) VALUES (?, ?, ?, ?,?)";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, newAccNum);
            preparedStatement.setInt(2, Integer.parseInt(UserId));
            preparedStatement.setFloat(3, initialdeposit);
            preparedStatement.setInt(4, rate);
            preparedStatement.setInt(5, accountOwner);


            return preparedStatement.executeUpdate() > 0; // Returns true if registration is successful

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkAccountAlreadyExist(String UserId, String accounttype) {
        String sql = "select * from " + accounttype + " where account_owner = ?";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, UserId);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching saving acc.", e);
        }

    }

    public Account checkAccOwnership(String accountNumber, String acctype) {
        String sql = "SELECT * FROM " + acctype + " WHERE account_number = ?";
        Account account = null;
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account = new Account(resultSet.getString("account_number"), resultSet.getString("account_owner"), resultSet.getFloat("balance"), resultSet.getFloat("rate"), resultSet.getInt("createdby"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public boolean depositSavings(String accountNumber, Integer depositAmount) throws SQLException {
        boolean sucess = false;
        try (Connection connection = DatabaseUtil.getConnection(); CallableStatement callstmt = connection.prepareCall("{CALL DepositMoney(?, ?,?)}")) {
            callstmt.setString(1, accountNumber);
            callstmt.setInt(2, depositAmount);
            callstmt.registerOutParameter(3, java.sql.Types.BOOLEAN);
            callstmt.execute();
            boolean success = callstmt.getBoolean(3);
            if (success) {
                sucess = VarList.STATUS_TRUE;
            } else {
                sucess = VarList.STATUS_FALSE;
                logger.warn("Deposit failed. Account not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sucess;
    }

    //check account exists or not
    public boolean checkAccount(String accountNumber, String accType) {
        String sql = "SELECT account_number FROM " + accType + "  WHERE  account_number=?";
        boolean account_exists = VarList.STATUS_FALSE;
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check if the account exists by checking if there are any results
            if (resultSet.next()) {
                account_exists = VarList.STATUS_TRUE;  // Account exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account_exists;
    }

    public String getUserIdByNic(String nic) {
        String sql = "select user_id from client_user where cnic=?";
        String userid_exists = "";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nic);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check if the account exists by checking if there are any results
            if (resultSet.next()) {
                userid_exists = String.valueOf(resultSet.getInt("user_id"));
                return userid_exists;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return userid_exists;
    }

    public boolean makeTransacation(String accountNumber, Integer Amount, String txntype, Integer accountOwner) {
        boolean status = VarList.STATUS_FALSE;
        String sql = "INSERT INTO transaction (account_number, amount, txn_type, txn_date_time,createdby) VALUES (?, ?,?,?,?)";
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setInt(2, Amount);
            preparedStatement.setString(3, txntype);
            preparedStatement.setTimestamp(4, timestamp);
            preparedStatement.setInt(5, accountOwner);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                status = VarList.STATUS_TRUE;
                logger.info("Transaction Success");
            } else {
                logger.warn("Transaction insert failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;

    }

    public boolean withdrawMoney(String accNumber, Integer withdrawtAmount, int accountOwner) {
        boolean sucess = VarList.STATUS_FALSE;
        try (Connection connection = DatabaseUtil.getConnection(); CallableStatement callstmt = connection.prepareCall("{CALL WithdrawMoney(?,?,?)}")) {
            callstmt.setString(1, accNumber);
            callstmt.setInt(2, withdrawtAmount);
            callstmt.registerOutParameter(3, java.sql.Types.BOOLEAN);
            callstmt.execute();
            boolean success = callstmt.getBoolean(3);
            if (success) {
                sucess = VarList.STATUS_TRUE;
            } else {
                sucess = VarList.STATUS_FALSE;
                logger.warn("Withdraw failed. Account not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sucess;
    }

    public boolean createFDAccount(Integer newAccNum, String UserId, float initialdeposit, Integer rate, int accountOwner, String fdtime) {
        boolean status = VarList.STATUS_FALSE;
        String sql = "INSERT INTO fd_account (account_number, account_owner, balance, rate,created_date,createdby,time_duration) VALUES (?, ?, ?, ?,?,?,?)";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            LocalDate today = LocalDate.now(); // Current date
            Date sqlDate = Date.valueOf(today);

            preparedStatement.setInt(1, newAccNum);
            preparedStatement.setInt(2, Integer.parseInt(UserId));
            preparedStatement.setFloat(3, initialdeposit);
            preparedStatement.setInt(4, rate);
            preparedStatement.setDate(5, sqlDate);
            preparedStatement.setInt(6, accountOwner);
            preparedStatement.setString(7, fdtime);


            return preparedStatement.executeUpdate() > 0; // Returns true if registration is successful

        } catch (Exception e) {
            e.printStackTrace();
            return status;
        }
    }

    public FdAccount getFdAccountDetailByAccNum(String accNum) {
        String sql = "SELECT * FROM fd_account WHERE account_number = ?";
        FdAccount fdAccount = null;
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, accNum);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    fdAccount = new FdAccount(resultSet.getString("account_number"), resultSet.getString("account_owner"), resultSet.getFloat("balance"), resultSet.getInt("rate"), resultSet.getInt("createdby"), resultSet.getDate("created_date"), resultSet.getInt("time_duration"));
                } else {
                    logger.warn("No data found fd account");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fdAccount;
    }

    public Rate getRateDetail(Integer rate) {
        Rate rate1 = null;

        String sql = "select * from rate where rate_id =?;";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, rate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    rate1 = new Rate(resultSet.getInt("rate_id"), resultSet.getString("account_name"), resultSet.getFloat("rate"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching rate.", e);
        }
        return rate1;
    }

    public Integer getTimeDuration(String accNum, String acctype) {
        Integer time = 0;
        String sql = "SELECT time_duration FROM " + acctype + " WHERE account_number = ?";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, accNum);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                time = resultSet.getInt("time_duration");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching fd account .", e);
        }

        return time;
    }

    public boolean createLoanAccount(Integer newAccNum, String UserId, float initialDeposit, Integer rate, int accountOwner, float fullinterest, float loanAndInterest, String timeDuration,int timeDurationMonth) {

        String sql = "INSERT INTO loan_account (account_number, account_owner,rate,balance,  full_loan_amount, createdby, created_date, full_interest,time_duration,timeDurationMonth) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            LocalDate today = LocalDate.now(); // Current date
            Date sqlDate = Date.valueOf(today);

            preparedStatement.setInt(1, newAccNum);
            preparedStatement.setInt(2, Integer.parseInt(UserId));
            preparedStatement.setInt(3, rate);
            preparedStatement.setFloat(4, loanAndInterest);
            preparedStatement.setFloat(5, initialDeposit);
            preparedStatement.setInt(6, accountOwner);
            preparedStatement.setDate(7, sqlDate);
            preparedStatement.setString(8, String.valueOf(fullinterest));
            preparedStatement.setString(9, timeDuration);
            preparedStatement.setInt(10, timeDurationMonth);
            return preparedStatement.executeUpdate() > 0; // Returns true if registration is successful

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public LoanAccount getLoanAccDetails(String accNum) {
        LoanAccount account = new LoanAccount();
        String sql = "SELECT full_loan_amount,payed_amount,full_interest,payed_interest,balance,time_duration FROM loan_account WHERE account_number = ?";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, accNum);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    account.setFullLoanAmount(resultSet.getFloat("full_loan_amount"));
                    account.setPayedamount(resultSet.getFloat("payed_amount"));
                    account.setFullInterest(resultSet.getFloat("full_interest"));
                    account.setPayedInterest(resultSet.getFloat("payed_interest"));
                    account.setBalance(resultSet.getFloat("balance"));
                    account.setTimeDuration(resultSet.getInt("time_duration"));


                } else {
                    logger.info("No data found for account number");
                    //System.out.println("No data found for account number: " + accNum);
                }
            }
//            System.out.println("Executing query: " + sql + " with account number: " + accNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public boolean InsertPayedDetails(float payedAmount, float balance, String accNum) {
        boolean status = VarList.STATUS_FALSE;
        String sql = "UPDATE loan_account SET balance = ?, payed_amount = ? WHERE account_number = ?";
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            preparedStatement.setFloat(1, balance);
            preparedStatement.setFloat(2, payedAmount);
            preparedStatement.setString(3, accNum);
            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                logger.info("Repay done");
                status = VarList.STATUS_TRUE;
            } else {
                logger.warn("Repay fail");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public boolean checkTxnTypeExist(String txntypeRepayloan) {
        boolean status = VarList.STATUS_FALSE;
        String sql = "SELECT txn_id,status FROM transaction_type WHERE txn_id = ?";

        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, txntypeRepayloan);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("status") == 0) {
                    status = VarList.STATUS_TRUE;
                }
            } else {
                System.out.println("This Transaction not available in this time");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching Transaction Type account .", e);
        }
        return status;
    }

    //withdraaw fd money
    public boolean withdrawFDMoney(String accNum, float withdrawAmount, float tax) {
        boolean sucess = VarList.STATUS_FALSE;
        try (Connection connection = DatabaseUtil.getConnection(); CallableStatement callstmt = connection.prepareCall("{CALL WithdrawFixedDeposit(?,?,?,?)}")) {
            callstmt.setString(1, accNum);
            callstmt.setFloat(2, withdrawAmount);
            callstmt.setFloat(3, tax);
            callstmt.registerOutParameter(4, java.sql.Types.BOOLEAN);
            callstmt.execute();
            boolean success = callstmt.getBoolean(4);

            if (success) {
                sucess = VarList.STATUS_TRUE;
            } else {
                sucess = VarList.STATUS_FALSE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sucess;
    }

    public Integer getAccStatus(String accNum) {
        String sql = "SELECT time_duration FROM fd_account WHERE account_number = ?";
        Integer status = null;
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, accNum);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                status = resultSet.getInt("time_duration");
            } else {
                System.out.println("Fetching error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching Transaction Type account .", e);
        }
        return status;
    }

    public LoanBalanceDTO checkBalanceandMonthlyCharge(String accNum) {
        LoanBalanceDTO loanBalanceDTO = new LoanBalanceDTO();
        String sql = "SELECT time_duration,full_interest,full_loan_amount,balance FROM loan_account WHERE account_number = ?";
        Integer status = null;
        try (Connection connection = DatabaseUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, accNum);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                loanBalanceDTO.setTimeDuration(resultSet.getInt("time_duration"));
                loanBalanceDTO.setFullInterest(resultSet.getInt("full_interest"));
                loanBalanceDTO.setFullLoanAmount(resultSet.getInt("full_loan_amount"));
                loanBalanceDTO.setBalance(resultSet.getFloat("balance"));
            } else {
                logger.warn("Fetching error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching Transaction Type account .", e);
        }
        return loanBalanceDTO;

    }

    public Integer generateNewAccNumber(String accounttype) {
        Integer accNum = null;
        try (Connection connection = DatabaseUtil.getConnection();
             CallableStatement callstmt = connection.prepareCall("{CALL GenerateAccNumber(?,?)}")) {
                callstmt.setString(1, accounttype);
                callstmt.registerOutParameter(2, Types.INTEGER);
                callstmt.execute();
                accNum = callstmt.getInt(2);
                if (accNum != 0) {
                    return accNum;
                } else {
                    logger.warn("Number Generation failed");
                }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while generating account number");

        }
        return accNum;
    }
}
