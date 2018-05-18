package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

public class PrintUsersCommand extends PrintCommand {

    public PrintUsersCommand(AccountsManager accountsManager) {
        super(accountsManager);
        getterOfAll = AccountsManager::getAccountsDescList;
        getterOfOne = AccountsManager::getAccountDesc;
    }

}
