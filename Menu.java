//Shaohai Li (sli34@toromail.csudh.edu)
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    // Implement:
    public static void main(String[] args) {
        Bank bank = new Bank();


        Scanner scanner = new Scanner(System.in);
        // Read config file
        Config Config = readConfigFile("config.txt");


        //When program loaded, acquires foreign
        //exchange information from the following URL:
        //http://www.usman.cloud/banking/exchange-rate.csv
        List<Currency> currencyList = Transform.getCurrencyList();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://www.usman.cloud/banking/exchange-rate.csv"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String csvData = response.body();

            String[] lines = csvData.split("\n");
            for (String line : lines) {
                String[] arr = line.split(",");
                currencyList.add(new Currency(arr[0], arr[1], Double.parseDouble(arr[2])));
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error retrieving exchange rates: " + e.getMessage());
        }


        boolean exit = false;
        while (!exit) {
            // Display menu of options
            System.out.println("1 - Open a Checking account");
            System.out.println("2 - Open Saving Account");
            System.out.println("3 - List Accounts");
            System.out.println("4 - Account Statement");
            //Newly added 04/05/2023
            System.out.println("5 - Show Account Information");
            System.out.println("6 - Deposit funds");
            System.out.println("7 - Withdraw funds");
            //Newly added 04/05/2023
            System.out.println("8 - Currency Conversion");
            System.out.println("9 - Close an account");
            System.out.println("10 - Exit");
            System.out.println("");
            System.out.print("Enter Choice: [1-10]: ");

            // Get user input
            int choice = scanner.nextInt();

            switch (choice) {
                case 8:
                    //Currency Conversion
                    Scanner sc = new Scanner(System.in);

                    System.out.print("Enter the currency you are selling: ");
                    String sellingCurrency = sc.next().toUpperCase();

                    System.out.print("Enter the amount you are selling: ");
                    double sellingAmount = sc.nextDouble();

                    System.out.print("Enter the currency you are buying: ");
                    String buyingCurrency = sc.next().toUpperCase();

                    /*
                    The currency you are selling : CAD The amount you are selling : xxx The currency you are buying : USD
                        Output:
                    The exchange rate is xxx and you will get USD xxx
                     */
                    double sellingExchangeRate = 0;
                    double buyingExchangeRate = 0;
                    for (Currency c : currencyList) {
                        if (c.getISO().equals(sellingCurrency)) {
                            sellingExchangeRate = c.getRate();
                        }
                        if (c.getISO().equals(buyingCurrency)) {
                            buyingExchangeRate = c.getRate();
                        }
                    }
                    double exchangeRate = buyingExchangeRate / sellingExchangeRate;
                    double buyingAmount = sellingAmount * exchangeRate;
                    System.out.println("The exchange rate is " + exchangeRate + " and you will get " + buyingCurrency + " " + buyingAmount);


                    break;

                case 1:
//                    System.out.println("Curr"+bank.accountNumbers);
                    // Open a checking account
                    System.out.print("Enter the id:");
                    int id = scanner.nextInt();
                    System.out.print("Enter first name:");
                    String firstName = scanner.next();
                    System.out.print("Enter last name:");
                    String lastName = scanner.next();
                    System.out.print("Enter social security number:");
                    String ssn = scanner.next();
                    System.out.print("Enter overdraft limit:");
                    double overdraftLimit = scanner.nextDouble();
                    scanner.nextLine();

                    Account checkingAccount = bank.openAccount(id, firstName, lastName, "", ssn, "Checking", overdraftLimit);
                    System.out.println("Thank you, the account number is " + checkingAccount.getAccountNumber());
                    System.out.println("");
                    break;

                case 2:// Open a saving account
                    System.out.print("Enter the id:");
                    id = scanner.nextInt();
                    System.out.print("Enter first name:");
                    String firstNameS = scanner.next();
                    System.out.print("Enter last name:");
                    String lastNameS = scanner.next();
                    System.out.print("Enter email address:");
                    String emailS = scanner.next();
                    System.out.print("Enter social security number:");
                    String ssnS = scanner.next();


                    Account savingAccount = bank.openAccount(id, firstNameS, lastNameS, emailS, ssnS, "saving");
                    System.out.println("Thank you, the account number is " + savingAccount.getAccountNumber());
                    System.out.println("");
                    break;

                case 3:
                    // List account information
                    System.out.print("Enter account number:");
                    int accNum = scanner.nextInt();

                    Account acc = bank.getAccount(accNum);
                    if (acc == null) {
                        System.out.println("Account not found");
                    } else {
                        /*
                        <Account Number> (<Account Name>) : <First Name> : <Last Name> : <SSN> : <Account Currency> : <Balance> : <USD Balance>: <Account Status>

                        1001(Checking) : Joe : Blogs : 999-999-9999 :CAD: 17.0: 14.00: Account Open 1002(Saving) : Minnie : Mouse : 111-999-9999 :CAD: 20.0:
                         15.00 : Account Open
                         */
                        System.out.println(String.format("%d(%s) : %s : %s : %s : %s : %.2f : %.2f : %s", acc.getAccountNumber(), acc.getType(), acc.getFirstName(), acc.getLastName(), acc.getSSN(), acc.getCurrency(), acc.getCurrencyBalance(), acc.getBalance(), acc.getAccountStatus()));
                    }
                    System.out.println("");
                    break;

                case 4:
                    // Print account statement
                    System.out.print("Enter account number:");
                    int accountNumber = scanner.nextInt();
                    Account account = bank.findAccount(accountNumber);
                    if (account != null) {
                        for (Transaction transaction : account.getTransactions()) {
                            String type = transaction.getType().equals("Credit") ? "Credit" : "Debit";
                            System.out.println(transaction.getId() + " : " + type + " : " + transaction.getAmount());
                        }
                        System.out.println("Balance: " + account.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                    System.out.println("");
                    break;

                case 5:
                    //Show Account Information
                    /*
                    Input:

                        Enter account number: 1001(this is an example value)
                        Output:

                        Account Number: 1001 Name: Joe Blog

                        SSN: 999 Currency: CAD Currency Balance: CAD 20.00 USD Balance: USD 15.00
                     */
                    System.out.print("Enter account number:");
                    int accountNumberI = scanner.nextInt();
                    Account accountI = bank.findAccount(accountNumberI);
                    if (accountI != null) {
                        System.out.println("Account Number: " + accountI.getAccountNumber() + " Name: " + accountI.getFirstName() + " " + accountI.getLastName());
                        System.out.println("SSN: " + accountI.getSSN() + " Currency: " + accountI.getCurrency() + " Currency Balance: " + accountI.getCurrency() + " " + accountI.getBalance());
                        System.out.println("USD Balance: USD " + accountI.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }

                    break;

                case 6:
                    // Deposit funds
                    System.out.print("Enter account number:");
                    int accountNumberD = scanner.nextInt();
                    System.out.println("Enter amount to deposit:");
                    double amountD = scanner.nextDouble();
                    try {
                        boolean depositSuccess = bank.deposit(accountNumberD, amountD);
                        if (depositSuccess) {
                            System.out.println("Deposit successful. New balance: " + bank.getBalance(accountNumberD));
                        }
                    } catch (Bank.AccountClosedException e) {
                        System.out.println("Deposit failed. Account is closed.");
                    } catch (Bank.NoSuchAccountException e) {
                        System.out.println("Deposit failed. Account does not exist.");
                    } catch (Bank.InsufficientBalanceException e) {
                        System.out.println("Deposit failed. Insufficient funds");
                    }

                    System.out.println("");
                    break;


                case 7:
                    // Withdrawal funds

                    System.out.print("Enter account number:");
                    int accountNumberW = scanner.nextInt();
                    System.out.println("Enter amount to withdrawal:");
                    double amountW = scanner.nextDouble();
                    try {
                        boolean withdrawalSuccess = bank.withdrawal(accountNumberW, amountW);
                        if (withdrawalSuccess) {
                            System.out.println("Withdrawal successful. New balance: " + bank.getBalance(accountNumberW));
                        }
                    } catch (Bank.AccountClosedException e) {
                        System.out.println("Withdrawal failed. Account is closed.");
                    } catch (Bank.NoSuchAccountException e) {
                        System.out.println("Withdrawal failed. Account does not exist.");
                    } catch (Bank.InsufficientBalanceException e) {
                        System.out.println("Withdrawal failed. Insufficient funds");
                    }

                    System.out.println("");
                    break;


                case 9:
                    //Close an account
                    System.out.print("Enter account number:");
                    accountNumber = scanner.nextInt();
                    boolean closed = bank.closeAccount(accountNumber);
                    if (closed) {
                        System.out.println("Account closed successfully.");
                    } else {
                        System.out.println("Account not found.");
                    }
                    System.out.println("");
                    break;

                   /* case 8:
                        // Save transactions to file
                        try {
                            PrintWriter writer = new PrintWriter("transactions.txt");
                            System.out.println("Enter account number:");
                            accountNumber = scanner.nextInt();

                            account = bank.getAccount(accountNumber);
                            if (account != null) {
                                for (Transaction t : account.getTransactions()) {
                                    String type = t.getType().equals("Credit") ? "Credit" : "Debit";
                                    writer.println(t.getId() + " : " + type + " : " + t.getAmount());
                                }
                                writer.println("Balance: " + account.getBalance());
                                System.out.println("Transactions saved to file.");
                            } else {
                                System.out.println("Account not found.");
                            }
                            writer.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("File not found.");
                        }
                        break;
*/

                case 10:
                    // If option 10 is selected, the program will exit
                    System.out.println("Exiting ! Have a Great Day");
                    bank.saveAccounts();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static Config readConfigFile(String filename) {
        boolean supportCurrencies = false;
        String currenciesSource = null;
        String webserviceUrl = null;
        String currencyFile = null;

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    switch (key) {
                        case "support.currencies":
                            supportCurrencies = Boolean.parseBoolean(value);
                            break;
                        case "currencies.source":
                            currenciesSource = value;
                            break;
                        case "webservice.url":
                            webserviceUrl = value;
                            break;
                        case "currency.file":
                            currencyFile = value;
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found: " + e.getMessage());
        }

        return new Config(true, "webservice", "http://www.usman.cloud/banking/exchange-rate.csv", "exchange-rate.csv");
    }
}
