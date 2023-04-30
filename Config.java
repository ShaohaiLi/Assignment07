
public class Config {
    private boolean supportCurrencies;
    private String currenciesSource;
    private String webserviceUrl;
    private String currencyFile;

    public Config(boolean supportCurrencies, String currenciesSource, String webserviceUrl, String currencyFile) {
        this.supportCurrencies = supportCurrencies;
        this.currenciesSource = currenciesSource;
        this.webserviceUrl = webserviceUrl;
        this.currencyFile = currencyFile;
    }

    public boolean isSupportCurrencies() {
        return supportCurrencies;
    }

    public String getCurrenciesSource() {
        return currenciesSource;
    }

    public String getWebserviceUrl() {
        return webserviceUrl;
    }

    public String getCurrencyFile() {
        return currencyFile;
    }
}
