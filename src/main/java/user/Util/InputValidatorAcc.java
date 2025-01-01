package user.Util;

import user.DTO.ResponseDTO;

import javax.servlet.http.HttpServletRequest;

public class InputValidatorAcc {
    public ResponseDTO validateParameters(HttpServletRequest req) {
        ResponseDTO response = new ResponseDTO();

        String nic = req.getParameter("nic");
        String accountType = req.getParameter("accountType");
        String loantime = req.getParameter("loanTimeDuration");
        String initialDepositStr = req.getParameter("initialDeposit");
        String fdtime = req.getParameter("fdTimeDuration");


        if (!validateNIC(nic)) {
            response.setResponseCode("400");
            response.setResponseMsg("Invalid NIC format. Expected 9 digits followed by 'V' or 'v'.");
            response.setStatus(VarList.STATUS_FALSE);
            return response;
        }


        if (!validateAccountType(accountType)) {
            response.setResponseCode("400");
            response.setResponseMsg("Invalid account type. Allowed values: loan, fixedDeposit, saving.");
            response.setStatus(VarList.STATUS_FALSE);
            return response;
        }

        if (!validateLoanTime(loantime)) {
            response.setResponseCode("400");
            response.setResponseMsg("Invalid loan time duration. Must be a positive integer.");
            response.setStatus(VarList.STATUS_FALSE);
            return response;
        }
        // Validate Initial Deposit
        if (!validateInitialDeposit(initialDepositStr)) {
            response.setResponseCode("400");
            response.setResponseMsg("Invalid initial deposit. Must be a positive number.");
            response.setStatus(VarList.STATUS_FALSE);
            return response;
        }

        // Validate Fixed Deposit Time
        if (!validateFDTime(fdtime)) {
            response.setResponseCode("400");
            response.setResponseMsg("Invalid fixed deposit time duration. Must be a positive integer.");
            response.setStatus(VarList.STATUS_FALSE);
            return response;
        }

        // All validations passed
        response.setResponseMsg("Validation successful.");
        response.setStatus(VarList.STATUS_TRUE);
        return response;
    }

    // Validate NIC (e.g., 9 digits + V/v format)
    private boolean validateNIC(String nic) {
        return nic != null && nic.matches("\\d{9}[Vv]");
    }

    // Validate account type (must be one of the predefined types)
    private boolean validateAccountType(String accountType) {
        return accountType != null && (accountType.equals(VarList.LOAN_ACC) || accountType.equals(VarList.FD_ACC) || accountType.equals(VarList.SAVING_ACC));
    }

    // Validate loan time duration (e.g., positive integer or empty)
    private boolean validateLoanTime(String loantime) {
        return loantime == null || loantime.isEmpty() || loantime.matches("\\d+");
    }
    // Validate Initial Deposit (positive float value)
    private boolean validateInitialDeposit(String initialDepositStr) {
        try {
            float initialDeposit = Float.parseFloat(initialDepositStr);
            return initialDeposit > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validate Fixed Deposit Time (positive integer or optional)
    private boolean validateFDTime(String fdtime) {
        return fdtime == null || fdtime.isEmpty() || fdtime.matches("\\d+");
    }
}
