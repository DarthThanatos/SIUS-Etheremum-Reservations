package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import org.adridadou.ethereum.values.EthAccount;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

abstract class Command {



    @SuppressWarnings("SameParameterValue")
    String getName(AccountsManager accountsManager, String targetName ){
        EthAccount targetAccount = accountsManager.getAccount(targetName);
        if(targetAccount == null) {
            System.out.println("Error: target does not exist!");
            return null;
        }
        return targetName;
    }

    @SuppressWarnings("SameParameterValue")
    EthAccount getAccount(AccountsManager accountsManager, String estateOwnerName){
        EthAccount account = accountsManager.getAccount(estateOwnerName);
        if(account == null){
            System.out.println("There is no user with such a name");
        }
        return  account;
    }

    int getDay(int day){
        if(day < 0 || day > 6){
            System.out.println("Day needs to be in range [0,6]");
            return -1;
        }
        return day;
    }
}
