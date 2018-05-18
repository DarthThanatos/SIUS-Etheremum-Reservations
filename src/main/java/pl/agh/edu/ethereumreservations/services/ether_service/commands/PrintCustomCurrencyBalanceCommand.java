package pl.agh.edu.ethereumreservations.services.ether_service.commands;


import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

public class PrintCustomCurrencyBalanceCommand extends PrintCommand {

    public PrintCustomCurrencyBalanceCommand(AccountsManager accountsManager) {
        super(accountsManager);
        getterOfAll = AccountsManager::getCustomCurrencyBalances;
        getterOfOne = AccountsManager::getUserCustomCurrencyBalance;
    }
}
