package pl.agh.edu.ethereumreservations.rest.pojo;

@SuppressWarnings("unused")
public class EtherumCurrency {

    private String holder;
    private String ether;
    private String wei;

    public String getEther() {
        return ether;
    }

    public void setEther(String ether) {
        this.ether = ether;
    }

    public String getWei() {
        return wei;
    }

    public void setWei(String wei) {
        this.wei = wei;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }
}
