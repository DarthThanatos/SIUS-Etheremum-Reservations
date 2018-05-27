package pl.agh.edu.ethereumreservations.services.ether_service.commands;


import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

public class AddAccount extends Command {

    private AccountsManager accountsManager;

    public AddAccount(AccountsManager accountsManager) {
        this.accountsManager = accountsManager;
    }


    public void tryToAddAccount(String name) {
        if (getName(accountsManager, name) != null) return;
        try {
            accountsManager.addToAccounts(name);
            System.out.println("Warning: this is a brand new account with 0 ether balance. Inform the main account to sent this new user some resources.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
