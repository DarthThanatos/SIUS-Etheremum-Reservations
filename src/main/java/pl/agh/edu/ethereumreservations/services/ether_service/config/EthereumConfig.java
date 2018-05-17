package pl.agh.edu.ethereumreservations.services.ether_service.config;

import org.adridadou.ethereum.values.config.ChainId;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ethereum")
public class EthereumConfig {

    private String genesisFile = "custom_genesis.json";
    private boolean isDevelPhase = true;
    private ChainId customNetworkId = ChainId.id(3765);

    public ChainId getCustomNetworkId() {
        return customNetworkId;
    }

    public void setCustomNetworkId(ChainId customNetworkId) {
        this.customNetworkId = customNetworkId;
    }

    public String getGenesisFile() {
        return genesisFile;
    }

    public void setGenesisFile(String genesisFile) {
        this.genesisFile = genesisFile;
    }

    public boolean isDevelPhase() {
        return isDevelPhase;
    }

    public void setDevelPhase(boolean develPhase) {
        isDevelPhase = develPhase;
    }
}
