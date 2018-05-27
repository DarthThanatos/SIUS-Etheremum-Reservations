package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Reservations;

import java.util.concurrent.ExecutionException;

public class PublishEstateCommand extends Command {

    private AccountsManager accountsManager;
    private String userName;

    public PublishEstateCommand(AccountsManager accountsManager, String userName) {
        this.accountsManager = accountsManager;
        this.userName = userName;
    }

    public void execute(int price, String estateName) {
        if ((userName = getName(accountsManager, userName)) != null && (price >= 0)) {
            publishEstate(estateName, price);
        }
    }

    private void publishEstate(String name, int price) {
        Reservations reservations = accountsManager.getReservationsForName(userName);
        try {
            reservations.publishEstate(name, price, booleanArrayOf(true), booleanArrayOf(false)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Boolean[] booleanArrayOf(Boolean val) {
        Boolean[] res = new Boolean[7];
        for (int i = 0; i < 7; i++) {
            res[i] = val;
        }
        return res;
    }

}
