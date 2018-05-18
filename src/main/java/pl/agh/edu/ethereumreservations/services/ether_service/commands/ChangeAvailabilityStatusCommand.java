package pl.agh.edu.ethereumreservations.services.ether_service.commands;


import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Reservations;


public class ChangeAvailabilityStatusCommand extends Command {

    private AccountsManager accountsManager;
    private String userName;

    public ChangeAvailabilityStatusCommand(AccountsManager accountsManager, String userName){
        this.accountsManager = accountsManager;
        this.userName = userName;
    }

    public void execute(int day, int estateIndex, boolean isAvailable) {
        if((estateIndex >= 0) &&(day = getDay(day)) != -1){
            Reservations reservations = accountsManager.getReservationsForName(userName);
            tryToChangeAvailabilityStatus(reservations, estateIndex, day, isAvailable);
        }
    }

    private void tryToChangeAvailabilityStatus(Reservations reservations, int estateIndex, int day, boolean isAvailable){
        try{
            reservations.tryToChangeAvailableDay(estateIndex, day, isAvailable).get();
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("No estate with the given properties");
        }
    }


}
