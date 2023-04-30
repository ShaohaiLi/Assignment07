//Shaohai Li (sli34@toromail.csudh.edu)
import java.io.*;
import java.util.*;

public class Bank {
    static String choiceCurrency = "USD";
    static boolean DEBUG = false;
    private static Map<Integer, Account> accounts ;
    static Transform transform = new Transform();
     static int accountNumbers = 100;
    // File name for account list
    final static String ACCOUNT_FILE = "accounts.csv";
    //load account list from file
    static Map<Integer, Account> loadAccounts() {
        Map<Integer, Account> accountMap = new HashMap<>();
        File file = new File(ACCOUNT_FILE);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    /*
                                String info = type;
                                info += ", " + accountNumber;
                                info += ", " +accountHolder;
                                info += ", " + balance;
                                info += ", " + currency;
                                info += ", " + currencyBalance;

                                if (type.equals("Checking")) {
                                    info += ", " + overdraftLimit;
                                }

                     */
                    String array[] = line.split(",");
                    String type = array[0].trim();
                    int accountNumber = Integer.parseInt(array[1].trim());
                    if(accountNumbers <= accountNumber) {
                        accountNumbers = accountNumber + 1;
                    }
                    String person_str = array[2];

                    String person_array[] = person_str.split(":");
                    Person person = new Person(Integer.valueOf(person_array[0].trim()), person_array[1], person_array[2], person_array[3], person_array[4]);
                    double balance = Double.parseDouble(array[3].trim());
                    String currency = array[4].trim();
                    double currencyBalance = Double.parseDouble(array[5].trim());

                    if(type.equals("Checking")) {
                        double overdraftLimit = Double.parseDouble(array[3].trim());
                        Account account = new Account(accountNumber, type, person, overdraftLimit);
                        accountMap.put(accountNumber, account);

                    } else {
                        Account account = new Account(accountNumber, type, person);
                        accountMap.put(accountNumber, account);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
        if(DEBUG){
            System.out.println("Loaded " + accountMap.size() + " accounts");
            for(Integer num : accountMap.keySet()) {
                System.out.println("Account loaded: " + accountMap.get(num).formatInfo());
            }
        }

        return accountMap;
    }
    public void saveAccounts() {
        Writer writer = null;
        try {
            writer = new FileWriter(ACCOUNT_FILE);
            for (Account account : accounts.values()) {
                writer.write(account.formatInfo() + "\n");
            }
        }catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }

    }
    public Bank() {
        accounts = loadAccounts();

        if(transform.getCurrencyList().size() > 0) {
            Scanner scanner = new Scanner(System.in);

           while (true){
               System.out.print("Please select a currency to use:");
                choiceCurrency = scanner.nextLine();
               boolean found = false;
               for(Currency currency : transform.getCurrencyList()) {
                   if(currency.getISO().equals(choiceCurrency)) {
                       found = true;
                       break;
                   }
               }
               if(!found) {
                   System.out.println("Invalid currency");
               }
                else {
                     break;
                }
           }

        }
    }

    public static Account openAccount(int id,String firstName, String lastName, String email, String SSN, String accountType) {
        Person customer = new Person(id,firstName, lastName,email,SSN);
        Account account = new Account(accountNumbers++, accountType, customer);
        accounts.put(account.getAccountNumber(), account);
        System.out.println("Account created: " + account.formatInfo());
        return account;
    }

    public static Account openAccount(int id,String firstName, String lastName, String email, String SSN, String accountType, double overdraftLimit) {
        Person customer = new Person(id,firstName, lastName, email, SSN);
        Account account = new Account(accountNumbers++, accountType, customer, overdraftLimit);
        accounts.put(account.getAccountNumber(), account);
        System.out.println("Account created: " + account.formatInfo());
        return account;
    }

    public static Account getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }

    public static Account findAccount(int accountNumber) {
        for (Account account : accounts.values()) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }


    public static class AccountClosedException extends Exception {
        public AccountClosedException(String message) {
            super(message);
        }
    }

    public static class InsufficientBalanceException extends Exception {
        public InsufficientBalanceException(String message) {
            super(message);
        }
    }

    public static class NoSuchAccountException extends Exception {
        public NoSuchAccountException(String message) {
            super(message);
        }
    }

    public static boolean deposit(int accountNumber, double amount) throws AccountClosedException, InsufficientBalanceException,NoSuchAccountException {
        Account account = findAccount(accountNumber);
        if (account == null) {
            throw new NoSuchAccountException("Account does not exist");
        }
        if (!account.isOpen()) {
            throw new AccountClosedException("Cannot deposit to a closed account");
        }
        if (((account.getType().equals("Saving")) || (account.getType().equals("Checking")) ) &&  (account.getBalance() < 0 || amount < 0)) {
            throw new InsufficientBalanceException("Cannot deposit a negative amount or deposit to an account with negative balance");
        }
        account.deposit(amount);
        return true;
    }

    public static boolean withdrawal(int accountNumber, double amount) throws AccountClosedException, InsufficientBalanceException,NoSuchAccountException{
        Account account = findAccount(accountNumber);
        if (account == null) {
            throw new NoSuchAccountException("Account does not exist");
        }
        if (!account.isOpen()) {
            throw new AccountClosedException("Cannot withdraw from a closed account");
        }
        if (((account.getType().equals("Saving")) || (account.getType().equals("Checking")) ) && ((account.getBalance() > amount) || (account.getBalance() - amount < 0)) ){
            throw new InsufficientBalanceException("Cannot withdraw more than the available balance or withdraw from an account with negative balance");
        }
        account.withdrawal(amount);
        return true;
    }

    public static boolean closeAccount(int accountNumber) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return false;
        }
        accounts.remove(accountNumber);
        return true;
    }

    public static double getBalance(int accountNumber) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return 0.0;
        }
        return account.getBalance();

        // Methods

        /**
         * Handles the currency conversion.
         */



    }

}