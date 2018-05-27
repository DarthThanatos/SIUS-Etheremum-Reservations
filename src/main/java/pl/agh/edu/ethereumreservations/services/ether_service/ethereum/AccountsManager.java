package pl.agh.edu.ethereumreservations.services.ether_service.ethereum;

import org.adridadou.ethereum.EthereumFacade;
import org.adridadou.ethereum.blockchain.TestConfig;
import org.adridadou.ethereum.keystore.AccountProvider;
import org.adridadou.ethereum.values.EthAccount;
import org.adridadou.ethereum.values.EthAddress;
import org.adridadou.ethereum.values.EthValue;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Coin;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Reservations;

import java.util.*;
import java.util.stream.Collectors;

import static org.adridadou.ethereum.values.EthValue.ether;

public class AccountsManager {

    private AccountsMappings accounts = new AccountsMappings();

    private EthereumFacade ethereum;
    private CoinManager coinManager;
    private ReservationManager reservationManager;

    public void setEthereum(EthereumFacade ethereum) {
        this.ethereum = ethereum;
        onInitAfterEthereumAssigned();
    }

    private void onInitAfterEthereumAssigned() {
        ContractPublisher contractPublisher = new ContractPublisher(ethereum);
        EthAccount mainAccount = accounts.get("main");
        initCoinManager(contractPublisher, mainAccount);
        initReservationManager(contractPublisher, mainAccount);
    }

    private void initCoinManager(ContractPublisher contractPublisher, EthAccount mainAccount) {
        try {
            System.err.println("Publishing coin contract");
            ContractPublisher.Contract<Coin> mainContract = contractPublisher.compileAndPublish("coin.sol", "Coin", mainAccount, Coin.class);
            System.err.println("Published coin contract");
            coinManager = new CoinManager(ethereum, mainContract, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initReservationManager(ContractPublisher contractPublisher, EthAccount mainAccount) {
        try {
            System.err.println("Publishing pl.agh.edu.reservations contract");
            ContractPublisher.Contract<Reservations> mainContract = contractPublisher.compileAndPublish("reservations.sol", "Reservations", mainAccount, Reservations.class);
            System.err.println("Published pl.agh.edu.reservations contract");
            reservationManager = new ReservationManager(ethereum, mainContract, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EthAddress getReservationsAddr() {
        return reservationManager.getReservationsContractAddr();
    }

    public void createStartingAccounts(String configPath) {
        accounts.put("bob", AccountProvider.from("bob"));
        accounts.put("alice", AccountProvider.from("alice"));
        accounts.put("main", AccountProvider.from("main"));
        new GenesisCreator(accounts.accounts, configPath).createJSONGenesis();
    }

    public TestConfig createTestAccountsConfig() {
        return addEtherToMain(addEtherToAll(TestConfig.builder(), ether(100000)), ether(500000)).build();
    }

    public EthAccount addToAccounts(String name) throws Exception {
        if (accounts.containsKey(name)) throw new Exception("Account with the specified username already exists!");
        EthAccount account = AccountProvider.from(name);
        accounts.put(name, account);
        return account;
    }

    private TestConfig.Builder addEtherToMain(TestConfig.Builder configBuilder, EthValue ethValue) {
        EthAccount mainAccount = accounts.get("main");
        configBuilder.balance(mainAccount, ethValue);
        return configBuilder;
    }

    private TestConfig.Builder addEtherToAll(TestConfig.Builder configBuilder, EthValue etherValue) {
        for (EthAccount account : accounts.values()) {
            configBuilder.balance(account, etherValue);
        }
        return configBuilder;
    }


    public List<String> getEthereumBalances() {
        List<String> res = new ArrayList<>();
        for (String name : accounts.keySet()) {
            res.add(getUserEthereumBalance(name));
        }
        return res;
    }

    public String getUserEthereumBalance(String name) {
        EthAccount account = accounts.get(name);
        if (account == null) {
            System.out.println("There is no account with name: " + name);
            return null;
        }
        EthValue accountBalance = ethereum.getBalance(account);
        return "Balance of " + name + " \n\tin Ether: " + accountBalance.inEth() + "\n\tin Wei: " + accountBalance.inWei();
    }


    public List<String> getCustomCurrencyBalances() {
        List<String> res = new ArrayList<>();
        for (String name : accounts.keySet()) {
            res.add(getUserCustomCurrencyBalance(name));
        }
        return res;
    }

    public String getUserCustomCurrencyBalance(String name) {
        EthAccount account = accounts.get(name);
        if (account == null) {
            System.out.println("There is no account with name: " + name);
            return null;
        }
        return coinManager.getUserCustomCurrencyBalance(account, name);
    }

    public Coin getCoinForName(String name) {
        EthAccount account = accounts.get(name);
        if (account == null) {
            System.out.println("There is no account with name: " + name);
            return null;
        }
        return coinManager.getCoinForName(account, name);
    }

    public Reservations getReservationsForName(String name) {
        EthAccount account = accounts.get(name);
        if (account == null) {
            System.out.println("There is no account with name: " + name);
            return null;
        }
        return reservationManager.getReservationForName(account, name);
    }

    public EthAccount getAccount(String name) {
        return accounts.get(name);
    }

    public List<String> getAccountsDescList() {
        return accounts.keySet().stream().map(this::getAccountDesc).collect(Collectors.toList());
    }

    public String getAccountDesc(String name) {
        EthAccount account = accounts.get(name);
        if (account == null) {
            return "There is no account with name: " + name;
        }
        return name + "'s account: " + account.getAddress().toString() + " private key: " + account.key.getPrivKey().toString(16);
    }


    public String getReadableNameFromHexForm(String hexForm) {
        return accounts.getNameByHexString(hexForm);
    }

    EthAccount getAcountFromHex(String hexForm) {
        return accounts.getAccountByHexString(hexForm);
    }

    private class AccountsMappings {

        private HashMap<String, EthAccount> accounts = new HashMap<>();
        private HashMap<String, EthAccount> hexToAccountMap = new HashMap<>();
        private HashMap<String, String> hexToNameMap = new HashMap<>();

        void put(String name, EthAccount account) {
            accounts.put(name, account);
            hexToAccountMap.put(account.getAddress().toString(), account);
            hexToNameMap.put(account.getAddress().toString(), name);
        }

        boolean containsKey(String name) {
            return accounts.containsKey(name);
        }

        EthAccount get(String name) {
            return accounts.get(name);
        }

        Set<String> keySet() {
            return accounts.keySet();
        }

        Collection<EthAccount> values() {
            return accounts.values();
        }

        EthAccount getAccountByHexString(String hexString) {
            return hexToAccountMap.get(hexString);
        }

        String getNameByHexString(String hexString) {
            return hexToNameMap.get(hexString);
        }
    }

}
