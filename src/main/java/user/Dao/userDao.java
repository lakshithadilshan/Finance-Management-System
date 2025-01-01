package user.Dao;

import systemuser.util.DatabaseUtil;
import user.DTO.ClientUserDTO;
import user.Util.VarList;
import user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userDao {

    public boolean userRegister(User user) {
        String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword()); // Ideally, hash the password
            preparedStatement.setString(3, user.getEmail());

            return preparedStatement.executeUpdate() > 0; // Returns true if registration is successful
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch a user by username (optional for login or validation)
    public User getUserByUsername(String username) {
        String sql = "SELECT user_id, username, password, email FROM user WHERE username = ?";
        User user = null;


        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean userAlreadyExist(ClientUserDTO clientUserDTO) {
        boolean exist = VarList.STATUS_FALSE;
        //String sql = "Select cnic from client_user where cnic=?";
        String sql = "select cemail from client_user where cemail = ? or cnic =?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, clientUserDTO.getCemail());
            preparedStatement.setString(2, clientUserDTO.getCnic());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exist = VarList.STATUS_TRUE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exist;
    }

    public boolean registerClientUser(ClientUserDTO clientUserDTO) {
        boolean isRegister = false;
        String sql = "INSERT INTO client_user (cNameinFull, NamewithInitials, city, cemail, cnic, cmartial_status, cpasswordsign, address) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Setting the values for the PreparedStatement
            preparedStatement.setString(1, clientUserDTO.getcNameinFull());
            preparedStatement.setString(2, clientUserDTO.getNamewithInitials());
            preparedStatement.setString(3, clientUserDTO.getCity());
            preparedStatement.setString(4, clientUserDTO.getCemail());
            preparedStatement.setString(5, clientUserDTO.getCnic());
            preparedStatement.setString(6, clientUserDTO.getCmartial_status());
            preparedStatement.setString(7, clientUserDTO.getCpasswordsign());
            preparedStatement.setString(8, clientUserDTO.getAddress());

            // Execute the insert operation
            int rowsAffected = preparedStatement.executeUpdate();
            // Return true if the insert was successful, false otherwise
            if (rowsAffected > 0){
                isRegister = VarList.STATUS_TRUE;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  isRegister;
    }

    public boolean getUserByUsernameOrEmail(String username, String email) {
        boolean exist = false;
        String sql = "select username from user where username = ? or email = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exist;
    }
}
