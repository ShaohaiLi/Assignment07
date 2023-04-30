//Shaohai Li (sli34@toromail.csudh.edu)
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Transform {
    private static List<Currency> currencyList =new  ArrayList<>();
    final static String EXCHANGE_RATE_FILE = "exchange-rate.csv";
    public Transform(){
        currencyList = loadExchangeRate();
    }
    List<Currency> loadExchangeRate(){
        List<Currency>currencies = new ArrayList<>();
        try {
            Scanner s = new Scanner(new FileReader(EXCHANGE_RATE_FILE));
            while (s.hasNext()){
                String line = s.nextLine();
                String[] tokens = line.split(",");
                Currency currency = new Currency(tokens[0],tokens[1],Double.parseDouble(tokens[2]));
                currencies.add(currency);

            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }

    public static List<Currency> getCurrencyList() {
        return currencyList;
    }

    public static void setCurrencyList(List<Currency> currencyList) {
        Transform.currencyList = currencyList;
    }
    public static double convertToUSD(double amount, String currencyISO){
        double result = 0;
        for(Currency c : currencyList){
            if(c.getISO().equals(currencyISO)){
                result = amount * c.getRate();
            }
        }
        return result;
    }
    public static double convertFromUSD(double amount, String currencyISO){
        double result = 0;
        for(Currency c : currencyList){
            if(c.getISO().equals(currencyISO)){
                result = amount / c.getRate();
            }
        }
        return result;
    }
}
