package user.DTO;

public class LoanBalanceDTO {
    private float fullLoanAmount ;
    private float fullInterest;
    private int timeDuration;
    private float balance;

    public LoanBalanceDTO() {
    }

    public LoanBalanceDTO(float fullLoanAmount, float fullInterest, int timeDuration, float balance) {
        this.fullLoanAmount = fullLoanAmount;
        this.fullInterest = fullInterest;
        this.timeDuration = timeDuration;
        this.balance = balance;
    }

    public float getFullLoanAmount() {
        return fullLoanAmount;
    }

    public void setFullLoanAmount(float fullLoanAmount) {
        this.fullLoanAmount = fullLoanAmount;
    }

    public float getFullInterest() {
        return fullInterest;
    }

    public void setFullInterest(float fullInterest) {
        this.fullInterest = fullInterest;
    }

    public int getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(int timeDuration) {
        this.timeDuration = timeDuration;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
