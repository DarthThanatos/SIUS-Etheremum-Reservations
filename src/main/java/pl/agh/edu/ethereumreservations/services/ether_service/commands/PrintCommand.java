package pl.agh.edu.ethereumreservations.services.ether_service.commands;


import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

import java.util.List;

public abstract class PrintCommand extends Command {

    private AccountsManager accountsManager;

    interface GetterOfAll {
        List<String> getAll(AccountsManager accountsManager);
    }

    interface GetterOfOne {
        String getOne(AccountsManager accountsManager, String name);
    }

    GetterOfAll getterOfAll;
    GetterOfOne getterOfOne;

    PrintCommand(AccountsManager accountsManager){
        this.accountsManager = accountsManager;
    }

    public String getOne(String name){
        return getterOfOne.getOne(accountsManager, name);
    }

    public List<String> getAll(){
        return getterOfAll.getAll(accountsManager);
    }

}
