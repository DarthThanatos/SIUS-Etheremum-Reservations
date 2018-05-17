package pl.agh.edu.ethereumreservations.services.ether_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.ethereumreservations.EthereumApplication;

import java.util.List;

@Service
public class EthereumServiceImpl implements IEthereumService {

    private final EthereumApplication ethereumApplication;

    @Autowired
    public EthereumServiceImpl(EthereumApplication ethereumApplication){
        this.ethereumApplication = ethereumApplication;
    }

    @Override
    public List<String> getAccountsDescList() {
        return ethereumApplication.getAccountsManager().getAccountsDescList();
    }
}
