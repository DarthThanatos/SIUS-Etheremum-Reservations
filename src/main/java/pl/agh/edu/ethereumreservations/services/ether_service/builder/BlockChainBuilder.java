package pl.agh.edu.ethereumreservations.services.ether_service.builder;

import org.adridadou.ethereum.EthereumFacade;
import org.adridadou.ethereum.blockchain.BlockchainConfig;
import org.adridadou.ethereum.blockchain.IncompatibleDatabaseBehavior;
import org.adridadou.ethereum.provider.EthereumFacadeProvider;
import org.adridadou.ethereum.provider.EthereumJConfigs;
import org.adridadou.ethereum.values.config.GenesisPath;
import org.adridadou.ethereum.values.config.NodeIp;
import pl.agh.edu.ethereumreservations.services.ether_service.config.EthereumConfig;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

public class BlockChainBuilder{

        private final  AccountsManager accountsManager;
        private final EthereumConfig config;

        public BlockChainBuilder(AccountsManager accountsManager, EthereumConfig config){
            this.accountsManager = accountsManager;
            this.config = config;
        }

        public EthereumFacade mountEthereum(){
            return config.isDevelPhase() ? forTest() : forNetwork();
        }

        private EthereumFacade forTest(){
            System.out.println("For test");
            accountsManager.createStartingAccounts(config.getGenesisFile());
            EthereumFacade ethereum = EthereumFacadeProvider.forTest(accountsManager.createTestAccountsConfig());
            accountsManager.setEthereum(ethereum);
            return ethereum;
        }


        private  EthereumFacade forNetwork(){
            accountsManager.createStartingAccounts(config.getGenesisFile());
            EthereumFacade ethereum =
                    EthereumFacadeProvider.forNetwork( privateNetwork() ).create();
            accountsManager.setEthereum(ethereum);
            return ethereum;
        }

    private BlockchainConfig.Builder networkCOnfig() {
        return EthereumJConfigs.privateMiner().genesis(GenesisPath.path(config.getGenesisFile())).peerDiscovery(true);
    }


    private  BlockchainConfig.Builder privateNetwork() {
            return BlockchainConfig.builder()
                    .addIp(NodeIp.ip("192.168.0.100:30301"))
                    .addIp(NodeIp.ip("192.168.0.100:30302"))
                    .addIp(NodeIp.ip("192.168.0.100:30303"))
                    .addIp(NodeIp.ip("192.168.0.101:30301"))
                    .addIp(NodeIp.ip("192.168.0.101:30302"))
                    .addIp(NodeIp.ip("192.168.0.101:30303"))
                    .peerActiveUrl("enode://15e76f568c37cbc1d85eff477b15a8e8a9db728dc4f4bcdb44aabcdd4912903203978bb6ddeb76095fb34080bfa50f0bc4c251967f030f56adc72e891bb9cecf@192.168.0.100:30303")
                    .peerActiveUrl("enode://85ed62ffce2ba244af6b499b6494cef00db661121b0f7a040f684ba2c5bd9441157e11e81087e1ba6213e15b533d891179f356822b2622a751d4701942eeb658@192.168.0.101:30302")
                    .networkId(config.getCustomNetworkId())
                    .genesis(GenesisPath.path("genesis.json"))
                    .incompatibleDatabaseBehavior(IncompatibleDatabaseBehavior.IGNORE)
                    .peerDiscovery(false);
        }
}
