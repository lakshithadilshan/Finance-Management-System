package systemuser.Dao;

import systemuser.model.Rate;
import systemuser.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rateDao {
    // Get all rates from the database
    public List<Rate>getAllrates(){
        String sql = "SELECT rate_id,account_name, rate FROM rate";
        List<Rate> rates = new ArrayList<>();


        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int rateId = resultSet.getInt("rate_id");
                String accountName = resultSet.getString("account_name");
                double rate = resultSet.getDouble("rate");
                rates.add(new Rate(rateId, accountName, rate));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rates;

    }

    // Update a rate
    public boolean updateRate( String rateId,String newRate) {
        String sql = "UPDATE rate SET rate = ? WHERE rate_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newRate);
            preparedStatement.setString(2,rateId);

            return preparedStatement.executeUpdate() > 0; // Returns true if successful

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Rate getRateById(int rateId) {
        Rate rate = null;
        String sql = "SELECT * FROM rate WHERE rate_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, rateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rate = new Rate(
                        resultSet.getInt("rate_id"),
                        resultSet.getString("account_name"),
                        resultSet.getDouble("rate")
                );
             
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching rate by ID.", e);
        }
        return rate;
    }

}
