package user.DTO;

public class AccountCreationDTO {
    private String accountType ;
    private float initialDeposit;
    private Integer accountOwmer;
    private String nic;
    private String fdTime;

    public AccountCreationDTO(String accountType, float initialDeposit, Integer accountOwmer, String nic, String fdTime) {
        this.accountType = accountType;
        this.initialDeposit = initialDeposit;
        this.accountOwmer = accountOwmer;
        this.nic = nic;
        this.fdTime = fdTime;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public float getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(float initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public Integer getAccountOwmer() {
        return accountOwmer;
    }

    public void setAccountOwmer(Integer accountOwmer) {
        this.accountOwmer = accountOwmer;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFdTime() {
        return fdTime;
    }

    public void setFdTime(String fdTime) {
        this.fdTime = fdTime;
    }
}
