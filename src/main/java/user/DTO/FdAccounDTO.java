package user.DTO;

public class FdAccounDTO {
    private String accNumber;
    private String operation;
    private int accOwner;
    private String accType;
    private float balance;
    private float tax;


    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getAccOwner() {
        return accOwner;
    }

    public void setAccOwner(int accOwner) {
        this.accOwner = accOwner;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }
}
