package user.model;

import java.util.Date;

public class SavingAcc {
    private Integer account_number;
    private Integer account_owner;
    private float balance;
    private Integer rate;
    private Date createdby;

    public SavingAcc(Integer account_number, Integer account_owner, float balance, Integer rate, Date createdby) {
        this.account_number = account_number;
        this.account_owner = account_owner;
        this.balance = balance;
        this.rate = rate;
        this.createdby = createdby;
    }

    public Integer getAccount_number() {
        return account_number;
    }

    public void setAccount_number(Integer account_number) {
        this.account_number = account_number;
    }

    public Integer getAccount_owner() {
        return account_owner;
    }

    public void setAccount_owner(Integer account_owner) {
        this.account_owner = account_owner;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Date getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Date createdby) {
        this.createdby = createdby;
    }
}
