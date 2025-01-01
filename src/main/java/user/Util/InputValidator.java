package user.Util;

import user.DTO.ResponseDTO;

public class InputValidator {

    // Method to validate all input fields at once
    public static ResponseDTO validateInputs(String cNameinFull, String NamewithInitials, String city,
                                             String cemail, String cnic, String cmartial_status,
                                             String cpasswordsign, String address) {
        ResponseDTO responseDTO = new ResponseDTO();
        boolean isValid = true;

        // Full Name Validation
        if (!validateFullName(cNameinFull)) {
            isValid = false;
            responseDTO.setResponseMsg("Please Ensure Full Name");
        }

        // Name with Initials Validation
        if (!validateNameWithInitials(NamewithInitials)) {
            isValid = false;
            responseDTO.setResponseMsg("Please Ensure Name with Initials");
        }

        // City Validation
        if (!validateCity(city)) {
            isValid = false;
            responseDTO.setResponseMsg("Please Ensure City");
        }

        // Email Validation
        if (!validateEmail(cemail)) {
            isValid = false;
            responseDTO.setResponseMsg("Please Ensure Email");
        }

        // NIC Validation
        if (!validateNIC(cnic)) {
            isValid = false;
            responseDTO.setResponseMsg("Please Ensure NIC");
        }

        // Marital Status Validation
        if (!validateMaritalStatus(cmartial_status)) {
            isValid = false;
            responseDTO.setResponseMsg("Please Ensure Marital Status");
        }

        // Password Validation
        if (!validatePassword(cpasswordsign)) {
            isValid = false;
            responseDTO.setResponseMsg("Please Ensure Password : Must be at least 6 characters ,at least one letter (A-Z or a-z),at least one number (0-9");
        }

        // Address Validation
        if (!validateAddress(address)) {
            isValid = false;
            responseDTO.setResponseMsg("Please Ensure Address");
        }
        responseDTO.setStatus(isValid);
        return responseDTO;

    }

    // Full Name Validation
    private static boolean validateFullName(String fullName) {
        return fullName != null && fullName.matches("^[A-Za-z ]+$") && !fullName.trim().isEmpty();
    }

    // Name with Initials Validation
    private static boolean validateNameWithInitials(String nameWithInitials) {
        return nameWithInitials != null && nameWithInitials.matches("^[A-Za-z. ]+$") && !nameWithInitials.trim().isEmpty();
    }

    // City Validation
    private static boolean validateCity(String city) {
        return city != null && city.matches("^[A-Za-z ]+$") && !city.trim().isEmpty();
    }

    // Email Validation
    private static boolean validateEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // NIC Validation
    private static boolean validateNIC(String nic) {
        return nic != null && (nic.matches("\\d{9}[A-Za-z]") || nic.matches("\\d{12}"));
    }

    // Marital Status Validation
    private static boolean validateMaritalStatus(String maritalStatus) {
        return maritalStatus != null && (maritalStatus.equalsIgnoreCase("Single") || maritalStatus.equalsIgnoreCase("Married") || maritalStatus.equalsIgnoreCase("Divorced") || maritalStatus.equalsIgnoreCase("Widowed"));
    }

    // Password Validation
    private static boolean validatePassword(String password) {
        return password != null && password.length() >= 6 && password.matches("^(?=.*[A-Za-z])(?=.*\\d).+$");
    }

    // Address Validation
    private static boolean validateAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }
}

