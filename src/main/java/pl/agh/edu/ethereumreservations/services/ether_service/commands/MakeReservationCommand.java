package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import org.adridadou.ethereum.values.EthAccount;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Reservations;

public class MakeReservationCommand extends ReservationCommand {

    public MakeReservationCommand(AccountsManager accountsManager, String userName) {
        super(accountsManager, userName);
    }

    @Override
    void makeReservationAction(EthAccount estateOwnerAccount, int estateIndex, int day, Reservations reservationForName) throws Exception {
        reservationForName.tryToReserve(estateOwnerAccount, estateIndex, day).get();
    }


}
