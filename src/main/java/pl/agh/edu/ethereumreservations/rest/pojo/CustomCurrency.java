package pl.agh.edu.ethereumreservations.rest.pojo;

public class CustomCurrency {

    String holder;
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }
}
