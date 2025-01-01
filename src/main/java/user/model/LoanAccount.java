package user.model;

public class LoanAccount extends Account{
    private float fullLoanAmount ;
    private float payedamount;
    private float fullInterest;
    private float payedInterest;
    private int timeDuration;



    public LoanAccount(String accountNumber, String accountOwner, float balance, float interestRate, Integer createdBy, float fullLoanAmount, float payedamount, float fullInterest, float payedInterest) {
        super(accountNumber, accountOwner, balance, interestRate, createdBy);
        this.fullLoanAmount = fullLoanAmount;
        this.payedamount = payedamount;
        this.fullInterest = fullInterest;
        this.payedInterest = payedInterest;

    }
    public LoanAccount(){}

    public LoanAccount (float fullLoanAmount, float payedamount, float fullInterest, float payedInterest,float balance,int timeDuration){
        this.fullLoanAmount = fullLoanAmount;
        this.payedamount = payedamount;
        this.fullInterest = fullInterest;
        this.payedInterest = payedInterest;
        this.setBalance(balance);
        this.setTimeDuration(timeDuration);
    }



    public float getFullInterest() {
        return fullInterest;
    }

    public void setFullInterest(float fullInterest) {
        this.fullInterest = fullInterest;
    }

    public float getPayedInterest() {
        return payedInterest;
    }

    public void setPayedInterest(float payedInterest) {
        this.payedInterest = payedInterest;
    }

    public float getFullLoanAmount() {
        return fullLoanAmount;
    }

    public void setFullLoanAmount(float fullLoanAmount) {
        this.fullLoanAmount = fullLoanAmount;
    }

    public float getPayedamount() {
        return payedamount;
    }

    public void setPayedamount(float payedamount) {
        this.payedamount = payedamount;
    }

    public int getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(int timeDuration) {
        this.timeDuration = timeDuration;
    }
}
