package pl.agh.edu.ethereumreservations.services.ether_service;

import org.adridadou.ethereum.EthereumFacade;
import org.springframework.stereotype.Service;
import pl.agh.edu.ethereumreservations.services.ether_service.builder.BlockChainBuilder;
import pl.agh.edu.ethereumreservations.services.ether_service.commands.*;
import pl.agh.edu.ethereumreservations.services.ether_service.config.EthereumConfig;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.ReservationManager;
import java.util.List;

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
        return new PrintUsersCommand(accountsManager).getAll();
    }

    @Override
    public String getAccountDesc(String userName) {
        return new PrintUsersCommand(accountsManager).getOne(userName);
    }

    @Override
    public void addAccount(String userName) {
        new AddAccount(accountsManager).tryToAddAccount(userName);
    }

    @Override
    public void makeReservation(String userName, String ownerName, int estateIndex, int weekDay) {
        new MakeReservationCommand(accountsManager, userName).execute(ownerName, estateIndex, weekDay);
    }

    @Override
    public void cancelReservation(String userName, String ownerName, int estateIndex, int weekDay) {
        new CancelReservationCommand(accountsManager, userName).execute(ownerName, estateIndex, weekDay);
    }

    @Override
    public void changeAvailabilityStatus(String ownerName, int estateLocalIndex, int weekDay, boolean available) {
        new ChangeAvailabilityStatusCommand(accountsManager, ownerName).execute(weekDay, estateLocalIndex, available);
    }

    @Override
    public List<ReservationManager.Estate> getAllEstates(String userName) {
        return new ListAllEstatesCommand(accountsManager, userName).getAllEstates();
    }

    @Override
    public List<ReservationManager.Estate> getAllUserEstates(String userName, String ownerName) {
        return new ListAllEstatesCommand(accountsManager, userName).getAllEstatesOfOwner(ownerName);
    }

    @Override
    public ReservationManager.Estate getEstateAt(String userName, int index) {
        return new ListAllEstatesCommand(accountsManager, userName).getEstate(index);
    }

    @Override
    public void mintCustomCurrency(String issuer, String targetName, int amount) {
        new MintCustomCurrencyCommand(accountsManager, issuer).execute(amount, targetName);
    }

    @Override
    public void sendCustomCurrency(String issuer, String targetName, int amount) {
        new PayCustomCurrencyCommand(accountsManager, issuer).execute(amount, targetName);
    }

    @Override
    public void payForReservation(String clientName, String estateOwnerName, int estateLocalIndex, int amount, int weekDay) {
        new PayForReservationCommand(accountsManager, clientName).execute(estateOwnerName, estateLocalIndex, amount, weekDay);
    }

    @Override
    public void sendEther(String issuer, String targetName, int amount) {
        new SendEtherCommand(accountsManager, ethereum, issuer).execute(amount, targetName);
    }

    @Override
    public List<String> getCustomCurrencyBalances() {
        return new PrintCustomCurrencyBalanceCommand(accountsManager).getAll();
    }

    @Override
    public List<String> getEthereumBalances() {
        return new PrintEtherBalanceCommand(accountsManager).getAll();
    }

    @Override
    public String getEthereumBalance(String userName) {
        return new PrintEtherBalanceCommand(accountsManager).getOne(userName);
    }

    @Override
    public String getCustomCurrencyBalance(String userName) {
        return new PrintCustomCurrencyBalanceCommand(accountsManager).getOne(userName);
    }

    @Override
    public ReservationManager.Estate getEstateDescription(String userName, String owner, int index) {
        return new ListAllEstatesCommand(accountsManager, userName).getEstateOfOwner(owner, index);
    }

    @Override
    public void publishEstate(String owner, String estateName, int estatePrice) {
        new PublishEstateCommand(accountsManager, owner).execute(estatePrice, estateName);
    }


}
