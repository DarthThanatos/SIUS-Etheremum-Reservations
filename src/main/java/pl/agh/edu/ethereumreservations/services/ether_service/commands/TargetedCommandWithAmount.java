package pl.agh.edu.ethereumreservations.services.ether_service.commands;


import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

public abstract class TargetedCommandWithAmount extends Command {

    AccountsManager accountsManager;
    String userName;

    TargetedCommandWithAmount(AccountsManager accountsManager, String userName){
        this.accountsManager = accountsManager;
        this.userName = userName;
    }

    public void execute(int amount, String targetName) {
        if((userName = getName(accountsManager, userName)) != null && (targetName = getName(accountsManager, targetName)) != null && (amount > 0))
            onArgumentsCorrect(targetName, amount);
    }


    abstract void onArgumentsCorrect(String targetName, int amount);


}
