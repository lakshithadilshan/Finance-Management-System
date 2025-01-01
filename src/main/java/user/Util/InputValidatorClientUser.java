package user.Util;

import user.DTO.ClientUserDTO;
import user.DTO.ResponseDTO;

public class InputValidatorClientUser {
    public ResponseDTO validateParameters(ClientUserDTO input) {
            ResponseDTO response = new ResponseDTO();

            // Validate Full Name
            if (!validateFullName(input.getcNameinFull())) {
                response.setResponseCode("400");
                response.setResponseMsg("Invalid full name. It must not be empty.");
                response.setStatus(VarList.STATUS_FALSE);
                return response;
            }

            // Validate Name with Initials
            if (!validateNameWithInitials(input.getNamewithInitials())) {
                response.setResponseCode("400");
                response.setResponseMsg("Invalid name with initials. It must not be empty.");
                response.setStatus(VarList.STATUS_FALSE);
                return response;
            }

            // Validate City
            if (!validateCity(input.getCity())) {
                response.setResponseCode("400");
                response.setResponseMsg("Invalid city. It must not be empty.");
                response.setStatus(VarList.STATUS_FALSE);
                return response;
            }

            // Validate Email
            if (!validateEmail(input.getCemail())) {
                response.setResponseCode("400");
                response.setResponseMsg("Invalid email format.");
                response.setStatus(VarList.STATUS_FALSE);
                return response;
            }

            // Validate NIC
            if (!validateNIC(input.getCnic())) {
                response.setResponseCode("400");
                response.setResponseMsg("Invalid NIC format. Expected 9 digits followed by 'V' or 'v'.");
                response.setStatus(VarList.STATUS_FALSE);
                return response;
            }

            // Validate Martial Status
            if (!validateMartialStatus(input.getCmartial_status())) {
                response.setResponseCode("400");
                response.setResponseMsg("Invalid martial status. Allowed values: Single, Married.");
                response.setStatus(VarList.STATUS_FALSE);
                return response;
            }

            // Validate Password
            if (!validatePassword(input.getCpasswordsign())) {
                response.setResponseCode("400");
                response.setResponseMsg("Please Ensure Password : Must be at least 6 characters ,at least one letter (A-Z or a-z),at least one number (0-9");
                response.setStatus(VarList.STATUS_FALSE);
                return response;
            }

            // Validate Address
            if (!validateAddress(input.getAddress())) {
                response.setResponseCode("400");
                response.setResponseMsg("Invalid address. It must not be empty.");
                response.setStatus(VarList.STATUS_FALSE);
                return response;
            }

            // All validations passed
            response.setResponseCode("200");
            response.setResponseMsg("Validation successful.");
            response.setStatus(VarList.STATUS_TRUE);
            return response;
        }

        private boolean validateFullName(String fullName) {
            return fullName != null && !fullName.trim().isEmpty();
        }

        private boolean validateNameWithInitials(String nameWithInitials) {
            return nameWithInitials != null && !nameWithInitials.trim().isEmpty();
        }

        private boolean validateCity(String city) {
            return city != null && !city.trim().isEmpty();
        }

        private boolean validateEmail(String email) {
            return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
        }

        private boolean validateNIC(String nic) {
            return nic != null && nic.matches("\\d{9}[Vv]");
        }

        private boolean validateMartialStatus(String status) {
            return status != null && (status.equalsIgnoreCase("Single") || status.equalsIgnoreCase("Married") || status.equalsIgnoreCase("Divorced") || status.equalsIgnoreCase("Widowed"));
        }

        private boolean validatePassword(String password) {
            return password != null && password.length() >= 6 && password.matches("^(?=.*[A-Za-z])(?=.*\\d).+$");
//            return password != null && !password.trim().isEmpty();
        }

        private boolean validateAddress(String address) {
            return address != null && !address.trim().isEmpty();
        }

}
