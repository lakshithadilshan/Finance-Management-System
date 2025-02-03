package user.Util;

import user.DTO.ResponseDTO;

public class InputValidatorSysUser {
    public static ResponseDTO validateRegistrationInputs(String username, String email, String password, String confirmPassword) {
        if (username == null || username.trim().isEmpty()) {
            return new ResponseDTO(false, "Username is required.");
        }

        if (!(username.length() >= 6)) {
            return new ResponseDTO(false, "Username is should be at least 6 characters");
        }

        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            return new ResponseDTO(false, "Invalid email address.");
        }

        if (password == null || password.length() < 6) {
            return new ResponseDTO(false, "Password must be at least 6 characters long.");
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).+$")) {
            return new ResponseDTO(false, "Required at least one Uppercase and one Lowercase (A-Z or a-z),at least one number (0-9 and Special Character(!$)");
        }

        if (!password.equals(confirmPassword)) {
            return new ResponseDTO(false, "Passwords do not match.");
        }

        // If all validations pass
        return new ResponseDTO(true, "Validation successful.");
    }
}
