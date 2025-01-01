package user.DTO;

public class ResponseDTO {
    private String responseCode;
    private String responseMsg;
    private boolean status;

    public ResponseDTO(String responseCode, String responseMsg, boolean status) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.status = status;
    }

    public ResponseDTO(boolean status, String responseMsg) {
        this.status = status;
        this.responseMsg = responseMsg;
    }

    public ResponseDTO() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
