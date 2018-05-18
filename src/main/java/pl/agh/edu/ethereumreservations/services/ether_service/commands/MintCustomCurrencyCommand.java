package pl.agh.edu.ethereumreservations.services.ether_service.commands;


import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Coin;

import java.util.concurrent.ExecutionException;

public class MintCustomCurrencyCommand extends TargetedCommandWithAmount {


    public MintCustomCurrencyCommand(AccountsManager accountsManager, String userName){
        super(accountsManager, userName);
    }

    @Override
    public void execute(int amount, String targetName) {
        warnNonMainUser();
        super.execute(amount, targetName);

    }

    @Override
    void onArgumentsCorrect(String targetName, int amount) {
        Coin coin = accountsManager.getCoinForName(userName);
        mintCoins(coin, targetName, amount);
    }

    private void warnNonMainUser(){
        if(!userName.equals("main")){
            System.out.println("Warning to " + userName + ": you are not the owner of the contract. Your request will be ignored by the contract code.");
        }
    }

    private void mintCoins(Coin coin, String targetName, int amount){
        try {
            coin.mint(accountsManager.getAccount(targetName), amount).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}