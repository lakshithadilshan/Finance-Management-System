package systemuser.Dao;

import systemuser.model.Rate;
import systemuser.model.TransactionType;
import systemuser.util.DatabaseUtil;
import user.Util.VarList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class transactionTypeDao {
    public List<TransactionType> getAllTransactionTypes() {
        String sql = "SELECT txn_id,txn_type,status FROM transaction_type";
        List<TransactionType> txntype = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int txn_id = resultSet.getInt("txn_id");
                String txn_type = resultSet.getString("txn_type");
                int status = resultSet.getInt("status");
                txntype.add(new TransactionType(txn_id, txn_type,status));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return txntype;
    }

    public boolean isExistTransactionType(String txnId) {
        boolean status = VarList.STATUS_FALSE;
        String sql = "SELECT txn_id FROM transaction_type";

        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                status = VarList.STATUS_TRUE;
               }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public boolean updateStatusTxnType(String txnId, String updateStatus) {
        String sql = "UPDATE transaction_type SET status = ? WHERE txn_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, updateStatus);
            preparedStatement.setString(2,txnId);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
