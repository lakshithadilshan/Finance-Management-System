package systemuser.model;

public class Rate {

        private int rateId;
        private String accountName;
        private double rate;

        public Rate(int rateId, String accountName, double rate) {
            this.rateId = rateId;
            this.accountName = accountName;
            this.rate = rate;
        }

        public int getRateId() {
            return rateId;
        }

        public void setRateId(int rateId) {
            this.rateId = rateId;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }


}
