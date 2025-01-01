package systemuser.model;

public class TransactionType {
    private int txnId;
    private String txnType;
    private int status;

    public TransactionType(int txnId, String txnType,int status) {
        this.txnId = txnId;
        this.txnType = txnType;
        this.status = status;
    }

    public Integer getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
