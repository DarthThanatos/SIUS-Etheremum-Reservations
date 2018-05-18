package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import org.adridadou.ethereum.values.EthAccount;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Coin;

import java.util.concurrent.ExecutionException;

public class PayForReservationCommand extends Command {

    private AccountsManager accountsManager;
    private String userName;

    public PayForReservationCommand(AccountsManager accountsManager, String userName){
        this.accountsManager = accountsManager;
        this.userName = userName;
    }

    public void execute(String ownerName, int estateIndex, int amount, int day) {
        EthAccount account;
        if((userName = getName(accountsManager, userName)) != null && (account = getAccount(accountsManager, ownerName)) != null && (estateIndex >= 0) && (amount >= 0) && (day = getDay(day))!= -1){
            payForReservation(account, estateIndex, amount, day);
        }
    }

    private void payForReservation(EthAccount account, int estateIndex, int amount, int day){
        Coin coin = accountsManager.getCoinForName(userName);
        try {
            coin.payForReservation(account, estateIndex, amount, day, accountsManager.getReservationsAddr()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }




}
