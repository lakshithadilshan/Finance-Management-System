package user.model;

import java.util.Date;

public class Transaction {
    private Integer account_number;
    private float amount;
    private int txn_type;
    private Date txn_date_time;
    private Integer createdby;

    public Transaction(Integer account_number, float amount, int txn_type, Date txn_date_time, Integer createdby) {
        this.account_number = account_number;
        this.amount = amount;
        this.txn_type = txn_type;
        this.txn_date_time = txn_date_time;
        this.createdby = createdby;
    }

    public Integer getAccount_number() {
        return account_number;
    }

    public void setAccount_number(Integer account_number) {
        this.account_number = account_number;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getTxn_type() {
        return txn_type;
    }

    public void setTxn_type(int txn_type) {
        this.txn_type = txn_type;
    }

    public Date getTxn_date_time() {
        return txn_date_time;
    }

    public void setTxn_date_time(Date txn_date_time) {
        this.txn_date_time = txn_date_time;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }
}
