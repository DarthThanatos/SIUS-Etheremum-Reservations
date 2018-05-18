package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import org.adridadou.ethereum.values.EthAccount;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Reservations;

public class CancelReservationCommand extends ReservationCommand {

    public CancelReservationCommand(AccountsManager accountsManager, String userName) {
        super(accountsManager, userName);
    }


    @Override
    void makeReservationAction(EthAccount account, int index, int day, Reservations reservationsForName) throws Exception {
        reservationsForName.tryToCancel(account, index, day).get();
    }
}
