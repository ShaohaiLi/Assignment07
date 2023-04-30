public class Currency {
    private String ISO;
    private String name;
    private double rate;

    public Currency(String ISO, String name, double rate) {
        this.ISO = ISO;
        this.name = name;
        this.rate = rate;
    }

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
