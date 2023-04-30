import java.util.List;

//Shaohai Li (sli34@toromail.csudh.edu)
public class Account {

        //Fields

        private int accountNumber;
        private String type;
        private boolean accountOpen;
        private double balance;
        private Person accountHolder;
        private double overdraftLimit;
        private String currency;
        private double currencyBalance;

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAccountOpen() {
        return accountOpen;
    }

    public void setAccountOpen(boolean accountOpen) {
        this.accountOpen = accountOpen;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Person getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Person accountHolder) {
        this.accountHolder = accountHolder;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getCurrencyBalance() {
        return currencyBalance;
    }

    public void setCurrencyBalance(double currencyBalance) {
        this.currencyBalance = currencyBalance;
    }

    //Constructor
        public Account(int accountNumber, String type, Person accountHolder, Double overdraftLimit) {
            this.accountNumber = accountNumber;
            this.type = type;
            this.accountHolder = accountHolder;
            accountOpen=true;
            currency="USD";
            currencyBalance=0;

            this.overdraftLimit=overdraftLimit;
        }

        public Account(int accountNumber, String type, Person accountHolder) {
            this.accountNumber = accountNumber;
            this.type = type;
            this.accountHolder = accountHolder;
            accountOpen=true;
            currency="USD";
            currencyBalance=0;
        }
    public String formatInfo() {
        String info = type;
        info += ", " + accountNumber;
        info += ", " +accountHolder;
        info += ", " + balance;
        info += ", " + currency;
        info += ", " + currencyBalance;

        if (type.equals("Checking")) {
            info += ", " + overdraftLimit;
        }

        return info;
        }
    public boolean withdrawal(double amount) throws Bank.AccountClosedException {
        if (!isOpen()) {
            throw new Bank.AccountClosedException("Cannot withdraw from a closed account");
        }
        if (this.balance - amount < 0) {
            throw new Bank.AccountClosedException("Cannot withdraw from a closed account");

        }
        this.balance = this.balance - amount;
        return true;
    }
    public boolean deposit(double amount) throws Bank.AccountClosedException {
        if (!isOpen()) {
            throw new Bank.AccountClosedException("Cannot deposit to a closed account");
        }
        if (amount < 0) {
            throw new Bank.AccountClosedException("Cannot deposit to a closed account");
        }
        this.balance = this.balance + amount;
        return true;
    }

        /*public boolean deposit(double amount)throws AccountClosedException {
            if (amount < 0 || !isOpen()) {
                throw new AccountClosedException("Insufficient balance to withdraw");
            }

            this.balance=this.balance+amount;

            return true;
        }*/

        public boolean isOpen() {
            return this.accountOpen;
        }

        private double overdraftLimit(){
            return this.overdraftLimit;
        }
        public double getBalance() {
            return this.balance;
        }

        public String toString() {
            return this.accountNumber+":"+type+":"+this.balance+":["+this.accountHolder.toString()+"]";
        }
        public int getAccountNumber() {return this.accountNumber;}

        // Implement:
        public String getType() {return this.type;}
        public  String getFirstName() {
            return accountHolder.getFirstName();
        }
        public String getLastName() {
            return accountHolder.getLastName();
        }
        public String getSSN() {
            return accountHolder.getSSN();
        }
        public String getAccountStatus() {
            return this.accountOpen ? "Account Open" : "Account Closed";
        }
        public List<Transaction> getTransactions() {
            return this.accountHolder.getTransactionList();
        }
    }

