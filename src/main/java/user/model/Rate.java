package user.model;

public class Rate {
    private int rate_id;
    private String account_name;
    private float rate;

    public Rate(int rate_id, String account_name, float rate) {
        this.rate_id = rate_id;
        this.account_name = account_name;
        this.rate = rate;
    }

    public int getRate_id() {
        return rate_id;
    }

    public void setRate_id(int rate_id) {
        this.rate_id = rate_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
