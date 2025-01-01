package user.model;

public class ClientUser {
    private int cuser_id;  // Primary Key, auto-increment
    private String cNameinFull;
    private String NamewithInitials;
    private String city;
    private String cemail;
    private String cnic;
    private String cmartial_status;
    private String cpasswordsign;
    private String address;

    public ClientUser(int cuser_id, String cNameinFull, String namewithInitials, String city, String cemail, String cnic, String cmartial_status, String cpasswordsign, String address) {
        this.cuser_id = cuser_id;
        this.cNameinFull = cNameinFull;
        NamewithInitials = namewithInitials;
        this.city = city;
        this.cemail = cemail;
        this.cnic = cnic;
        this.cmartial_status = cmartial_status;
        this.cpasswordsign = cpasswordsign;
        this.address = address;
    }

    public int getCuser_id() {
        return cuser_id;
    }

    public void setCuser_id(int cuser_id) {
        this.cuser_id = cuser_id;
    }

    public String getcNameinFull() {
        return cNameinFull;
    }

    public void setcNameinFull(String cNameinFull) {
        this.cNameinFull = cNameinFull;
    }

    public String getNamewithInitials() {
        return NamewithInitials;
    }

    public void setNamewithInitials(String namewithInitials) {
        NamewithInitials = namewithInitials;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getCmartial_status() {
        return cmartial_status;
    }

    public void setCmartial_status(String cmartial_status) {
        this.cmartial_status = cmartial_status;
    }

    public String getCpasswordsign() {
        return cpasswordsign;
    }

    public void setCpasswordsign(String cpasswordsign) {
        this.cpasswordsign = cpasswordsign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
