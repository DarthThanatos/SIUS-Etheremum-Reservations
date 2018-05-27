package pl.agh.edu.ethereumreservations.services.ether_service.commands;


import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;
import pl.agh.edu.ethereumreservations.services.ether_service.interfaces.Coin;

import java.util.concurrent.ExecutionException;

public class PayCustomCurrencyCommand extends TargetedCommandWithAmount {


    public PayCustomCurrencyCommand(AccountsManager accountsManager, String userName) {
        super(accountsManager, userName);
    }

    @Override
    void onArgumentsCorrect(String targetName, int amount) {
        Coin coin = accountsManager.getCoinForName(userName);
        sendAmount(coin, targetName, amount);
    }

    private void sendAmount(Coin coin, String targetName, int amount) {
        try {
            coin.send(accountsManager.getAccount(targetName), amount).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
