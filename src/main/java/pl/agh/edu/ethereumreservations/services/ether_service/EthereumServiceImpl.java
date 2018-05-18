package pl.agh.edu.ethereumreservations.services.ether_service;

import org.adridadou.ethereum.EthereumFacade;
import org.springframework.stereotype.Service;
import pl.agh.edu.ethereumreservations.services.ether_service.builder.BlockChainBuilder;
import pl.agh.edu.ethereumreservations.services.ether_service.config.EthereumConfig;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

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
        return accountsManager.getAccountsDescList();
    }
}
