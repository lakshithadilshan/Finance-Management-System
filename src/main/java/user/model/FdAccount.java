package user.model;

import java.util.Date;

public class FdAccount extends Account{
    private Date createddate;
    private Integer timeduration;


    public FdAccount(String accountNumber, String accountOwner, float balance, float interestRate, Integer createdBy, Date createddate, Integer timeduration) {
        super(accountNumber, accountOwner, balance, interestRate, createdBy);
        this.createddate = createddate;
        this.timeduration = timeduration;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Integer getTimeduration() {
        return timeduration;
    }

    public void setTimeduration(Integer timeduration) {
        this.timeduration = timeduration;
    }
}
