package pl.agh.edu.ethereumreservations.services.ether_service;

import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.ReservationManager;

import java.util.List;

public interface IEthereumService {

    List<String> getAccountsDescList();

    String getAccountDesc(String userName);

    void addAccount(String userName);

    void makeReservation(String userName, String ownerName, int estateIndex, int weekDay);

    void cancelReservation(String userName, String ownerName, int estateIndex, int weekDay);

    void changeAvailabilityStatus(String ownerName, int estateLocalIndex, int weekDay, boolean available);

    List<ReservationManager.Estate> getAllEstates(String userName);

    List<ReservationManager.Estate> getAllUserEstates(String userName, String ownerName);

    ReservationManager.Estate getEstateAt(String userName, int index);

    void mintCustomCurrency(String issuer, String targetName, int amount);

    void sendCustomCurrency(String issuer, String targetName, int amount);

    void payForReservation(String clientName, String estateOwnerName, int estateLocalIndex, int amount, int weekDay);

    void sendEther(String issuer, String targetName, int amount);

    List<String> getCustomCurrencyBalances();

    List<String> getEthereumBalances();

    String getEthereumBalance(String userName);

    String getCustomCurrencyBalance(String userName);

    ReservationManager.Estate getEstateDescription(String userName, String owner, int index);

    void publishEstate(String owner, String estateName, int estatePrice);

    String getNameByHexString(String hexName);

}
