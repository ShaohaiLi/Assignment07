import java.util.ArrayList;
import java.util.List;

//Shaohai Li (sli34@toromail.csudh.edu)
public class Transaction {
        private int id;
        private String type;
        private double amount;

        public Transaction(int id, String type, double amount) {
            this.id = id;
            this.type = type;
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

    public String getType() {
            return type;

        }

        public double getAmount() {
            return amount;
        }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}

