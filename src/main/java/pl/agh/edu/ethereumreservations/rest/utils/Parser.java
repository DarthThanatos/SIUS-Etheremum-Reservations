package pl.agh.edu.ethereumreservations.rest.utils;


import pl.agh.edu.ethereumreservations.rest.pojo.Account;
import pl.agh.edu.ethereumreservations.rest.pojo.CustomCurrency;
import pl.agh.edu.ethereumreservations.rest.pojo.EtherumCurrency;

public class Parser {

    private Parser() {

    }

    public static Account parseAccount(String description) {
        String[] accountInfo = description.split(" ");
        Account account = null;
        if (accountInfo.length == 6) {
            account = new Account();
            account.setName(accountInfo[0].substring(0, accountInfo[0].length() - 2));
            account.setPublicKey(accountInfo[2]);
            account.setPrivateKey(accountInfo[5]);
        }
        return account;
    }

    public static EtherumCurrency parseEtherum(String description) {
        String[] etherumInfo = description.split(" ");
        EtherumCurrency etherumCurrency = null;
        if (etherumInfo.length == 8) {
            etherumCurrency = new EtherumCurrency();
            etherumCurrency.setHolder(etherumInfo[2]);
            etherumCurrency.setEther(etherumInfo[5].split("\n")[0]);
            etherumCurrency.setWei(etherumInfo[7]);
        }
        return etherumCurrency;
    }

    public static CustomCurrency parseCustom(String description) {
        String[] customInfo = description.split(" ");
        CustomCurrency customCurrency = null;
        if (customInfo.length == 7) {
            customCurrency = new CustomCurrency();
            customCurrency.setHolder(customInfo[2]);
            customCurrency.setValue(customInfo[6]);
        }
        return customCurrency;
    }
}