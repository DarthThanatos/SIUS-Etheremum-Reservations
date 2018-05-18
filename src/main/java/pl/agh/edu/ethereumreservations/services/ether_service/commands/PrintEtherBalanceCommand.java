package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;


public class PrintEtherBalanceCommand extends PrintCommand {

    public PrintEtherBalanceCommand(AccountsManager accountsManager) {
        super(accountsManager);
        getterOfAll = AccountsManager::getEthereumBalances;
        getterOfOne = AccountsManager::getUserEthereumBalance;
    }

}
