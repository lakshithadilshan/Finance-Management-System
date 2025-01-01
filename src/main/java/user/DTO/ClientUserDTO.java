package user.DTO;

public class ClientUserDTO {
    private String cNameinFull;
    private String NamewithInitials;
    private String city;
    private String cemail;
    private String cnic;
    private String cmartial_status;
    private String cpasswordsign;
    private String address;

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
