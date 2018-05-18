package pl.agh.edu.ethereumreservations.services.ether_service.commands;

import org.adridadou.ethereum.EthereumFacade;
import org.adridadou.ethereum.values.EthAccount;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

import java.util.concurrent.ExecutionException;

import static org.adridadou.ethereum.values.EthValue.ether;

public class SendEtherCommand extends TargetedCommandWithAmount {

    private EthereumFacade ethereum;

    public SendEtherCommand(AccountsManager accountsManager, EthereumFacade ethereum, String userName) {
        super(accountsManager, userName);
        this.ethereum = ethereum;
    }

    @Override
    void onArgumentsCorrect(String targetName, int amount) {
        EthAccount srcAccount = accountsManager.getAccount(userName);
        EthAccount targetAccount = accountsManager.getAccount(targetName);
        sendEtherFromTo(srcAccount, targetAccount, amount);
    }

    private void sendEtherFromTo(EthAccount srcAccount, EthAccount targetAccount, int amount){
        try {
            ethereum.sendEther(srcAccount, targetAccount.getAddress(), ether(getCorrectAmountToSend(amount))).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private int getCorrectAmountToSend(int amount){
        int MAX_ETHER_AMOUNT = 10;
        if(amount > MAX_ETHER_AMOUNT) {
            System.out.println("Warning: sending only " + MAX_ETHER_AMOUNT + " as this is the maximum allowed amount to send");
            amount = MAX_ETHER_AMOUNT;
        }
        return amount;
    }

}
