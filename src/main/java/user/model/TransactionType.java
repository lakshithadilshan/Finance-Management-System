package user.model;

public class TransactionType {
    private Integer txnId;
    private String txnType;

    public TransactionType(Integer txnId, String txnType) {
        this.txnId = txnId;
        this.txnType = txnType;
    }

    public Integer getTxnId() {
        return txnId;
    }

    public void setTxnId(Integer txnId) {
        this.txnId = txnId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
}
