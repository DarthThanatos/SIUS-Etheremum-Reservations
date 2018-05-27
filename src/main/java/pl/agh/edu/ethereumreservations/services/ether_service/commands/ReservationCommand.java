package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import org.adridadou.ethereum.values.EthAccount;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Reservations;


public abstract class ReservationCommand extends Command {

    AccountsManager accountsManager;
    private String userName;

    ReservationCommand(AccountsManager accountsManager, String userName) {
        this.accountsManager = accountsManager;
        this.userName = userName;
    }

    abstract void makeReservationAction(EthAccount account, int index, int day, Reservations reservationsForName) throws Exception;

    public void execute(String estateOwnerName, int index, int day) {

        EthAccount account;

        if ((account = getAccount(accountsManager, estateOwnerName)) != null && (index >= 0) && (day = getDay(day)) != -1) {
            onArgumentsCorrect(account, index, day);
        }
    }

    private void onArgumentsCorrect(EthAccount account, int index, int day) {
        Reservations reservationForName = accountsManager.getReservationsForName(userName);
        try {
            makeReservationAction(account, index, day, reservationForName);
        } catch (Exception e) {
            System.out.println("No estate having given properties");
        }
    }

}
