package pl.agh.edu.ethereumreservations.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.ethereumreservations.rest.pojo.Account;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
public class AccountController {

    private final IEthereumService ethereumService;

    @Autowired
    public AccountController(IEthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    @GetMapping("/accounts")
    public List<String> listAllAccounts() {
        //e.g. localhost:8080/accounts
        return ethereumService.getAccountsDescList();
    }

    //e.g. localhost:8080/accounts/bob
    @GetMapping("/accounts/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    public Account getAccount(@PathVariable("userName") String userName) {
        Account account = new Account();
        String[] accountInfo = ethereumService.getAccountDesc(userName).split(" ");
        if (accountInfo.length == 6) {
            account.setName(userName);
            account.setPublicKey(accountInfo[2]);
            account.setPrivateKey(accountInfo[5]);
        }
        return account;
    }

    @PutMapping("/accounts/new/{userName}")
    public void addAccount(@PathVariable("userName") String userName) {
        //e.g. localhost:8080/accounts/new/tom
        ethereumService.addAccount(userName);
    }

}
