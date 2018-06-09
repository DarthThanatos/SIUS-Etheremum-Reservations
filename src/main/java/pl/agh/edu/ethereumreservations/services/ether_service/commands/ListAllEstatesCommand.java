package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import org.adridadou.ethereum.values.EthAccount;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.ReservationManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Reservations;

import java.util.ArrayList;
import java.util.List;

public class ListAllEstatesCommand extends Command {

    private AccountsManager accountsManager;
    private String userName;

    public ListAllEstatesCommand(AccountsManager accountsManager, String userName) {
        this.accountsManager = accountsManager;
        this.userName = userName;

    }

    public List<ReservationManager.Estate> getAllEstates() {
        if ((userName = getName(accountsManager, userName)) != null)
            return getAllEstates(accountsManager.getReservationsForName(userName));
        return null;
    }

    private List<ReservationManager.Estate> getAllEstates(Reservations reservationsForName) {
        List<ReservationManager.Estate> res = new ArrayList<>();
        int maxEstates = getMaxEstates(reservationsForName);
        for (int i = 0; i < maxEstates; i++) res.add(getEstate(reservationsForName, i));
        return res;
    }

    public ReservationManager.Estate getEstate(int i) {
        if (i >= 0 && (userName = getName(accountsManager, userName)) != null) {
            return getEstate(accountsManager.getReservationsForName(userName), i);
        }
        return null;
    }


    @SuppressWarnings("EmptyCatchBlock")
    private int getMaxEstates(Reservations reservationsForName) {
        int maxEstates = 0;
        try {
            maxEstates = reservationsForName.getAllEstatesAmount();
        } catch (Exception e) {
        }
        return maxEstates;
    }

    public List<ReservationManager.Estate> getAllEstatesOfOwner(String owner) {
        EthAccount account;
        List<ReservationManager.Estate> res = new ArrayList<>();
        if ((userName = getName(accountsManager, userName)) != null && (account = getAccount(accountsManager, owner)) != null) {
            Reservations reservationsForName = accountsManager.getReservationsForName(userName);
            int maxEstates = getMaxEstatesOfAccount(reservationsForName, account);
            for (int i = 0; i < maxEstates; i++) res.add(getEstateOfOwner(reservationsForName, account, i));
        }
        return res;
    }

    public ReservationManager.Estate getEstateOfOwner(String owner, int i) {
        EthAccount account;
        if ((userName = getName(accountsManager, userName)) != null && (account = getAccount(accountsManager, owner)) != null) {
            return getEstateOfOwner(accountsManager.getReservationsForName(userName), account, i);
        }
        return null;
    }

    @SuppressWarnings("EmptyCatchBlock")
    private ReservationManager.Estate getEstateOfOwner(Reservations reservationsForName, EthAccount account, int i) {
        try {
            ReservationManager.Estate estate = reservationsForName.getEstateOfOwnerByIndex(account, i);
            return withTenantNames(estate, account, i, reservationsForName);
        } catch (Exception e) {
        }
        return null;
    }

    @SuppressWarnings("EmptyCatchBlock")
    private ReservationManager.Estate getEstate(Reservations reservationsForName, int i) {
        try {
            ReservationManager.Estate estate = reservationsForName.getEstateByIndex(i);
            return withTenantNames(estate, i, reservationsForName);
        } catch (Exception e) {
        }
        return null;
    }

    private ReservationManager.Estate withTenantNames(ReservationManager.Estate estate, int globalIndex, Reservations reservationsForName){
        String[] names = new String[7];
        for (int day = 0; day < 7; day++){
            names[day] = accountsManager.getReadableNameFromHexForm(reservationsForName.getTenant(globalIndex, day));
        }
        estate.tenantsNames = names;
        return estate;
    }

    private ReservationManager.Estate withTenantNames(ReservationManager.Estate estate, EthAccount estateOwner, int localIndex, Reservations reservationsForName){
        String[] names = new String[7];
        for (int day = 0; day < 7; day++){
            names[day] = accountsManager.getReadableNameFromHexForm(reservationsForName.getTenantOfOwner(estateOwner, localIndex, day));
        }
        estate.tenantsNames = names;
        return estate;
    }

    @SuppressWarnings("EmptyCatchBlock")
    private int getMaxEstatesOfAccount(Reservations reservationsForName, EthAccount account) {
        int maxEstates = 0;
        try {
            maxEstates = reservationsForName.getOwnerEstatesAmount(account);
        } catch (Exception e) {
        }
        return maxEstates;
    }

}