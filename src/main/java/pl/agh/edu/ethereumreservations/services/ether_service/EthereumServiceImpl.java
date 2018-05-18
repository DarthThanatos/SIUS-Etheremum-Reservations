package pl.agh.edu.ethereumreservations.services.ether_service;

import org.adridadou.ethereum.EthereumFacade;
import org.springframework.stereotype.Service;
import pl.agh.edu.ethereumreservations.services.ether_service.builder.BlockChainBuilder;
import pl.agh.edu.ethereumreservations.services.ether_service.config.EthereumConfig;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.ReservationManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Reservations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@Service
public class EthereumServiceImpl implements IEthereumService {

    private final AccountsManager accountsManager;
    private final EthereumFacade ethereum;

    public EthereumServiceImpl(){
        accountsManager = new AccountsManager();
        ethereum = new BlockChainBuilder(accountsManager, new EthereumConfig()).mountEthereum();
    }

    @Override
    public List<String> getAccountsDescList() {
        return accountsManager.getAccountsDescList();
    }

    @Override
    public String getAccountDesc(String userName) {
        return null;
    }

    @Override
    public void addAccount(String userName) {

    }

    @Override
    public void makeReservation(String ownerName, int estateIndex, int weekDay) {

    }

    @Override
    public void cancelReservation(String ownerName, int estateIndex, int weekDay) {

    }

    @Override
    public void changeAvailabilityStatus(String ownerName, int estateLocalIndex, int weekDay, boolean available) {

    }




    @Override
    public List<ReservationManager.Estate> getAllEstates(String userName) {
        Reservations reservationsForName = accountsManager.getReservationsForName(userName);
        int maxEstates = getMaxEstates(reservationsForName);
        List<ReservationManager.Estate> res = new ArrayList<>();
        for(int i = 0; i < maxEstates; i++) res.add(getEstate(reservationsForName, i));
        return res;
    }

    @SuppressWarnings("EmptyCatchBlock")
    private ReservationManager.Estate getEstate(Reservations reservationsForName, int i){
        try {
            return reservationsForName.getEstateByIndex(i);
        }catch(Exception e){ }
        return null;
    }

    @SuppressWarnings("EmptyCatchBlock")
    private int getMaxEstates(Reservations reservationsForName){
        int maxEstates = 0;
        try {
            maxEstates = reservationsForName.getAllEstatesAmount();
        }catch(Exception e){}
        return maxEstates;
    }



    @Override
    public List<ReservationManager.Estate> getAllUserEstates(String ownerName) {
        return null;
    }

    @Override
    public void mintCustomCurrency(String issuer, String targetName, int amount) {

    }

    @Override
    public void sendCustomCurrency(String issuer, String targetName, int amount) {

    }

    @Override
    public void payForReservation(String clientName, String estateOwnerName, int estateLocalIndex, int amount, int weekDay) {

    }

    @Override
    public void sendEther(String issuer, String targetName, int amount) {

    }

    @Override
    public List<String> getCustomCurrencyBalances() {
        return null;
    }

    @Override
    public List<String> getEthereumBalances() {
        return null;
    }

    @Override
    public String getEthereumBalance(String userName) {
        return null;
    }

    @Override
    public String getCustomCurrencyBalance(String userName) {
        return null;
    }

    @Override
    public ReservationManager.Estate getEstateDescription(String owner, int index) {
        return null;
    }

    @Override
    public void publishEstate(String owner, String estateName, int estatePrice) {
        Reservations reservations = accountsManager.getReservationsForName(owner);
        try {
            reservations.publishEstate(estateName, estatePrice, booleanArrayOf(true), booleanArrayOf(false)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Boolean[] booleanArrayOf(Boolean val){
        Boolean[] res = new Boolean[7];
        for (int i = 0; i < 7; i++){
            res[i] = val;
        }
        return res;
    }

}
