package pl.agh.edu.ethereumreservations.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.ethereumreservations.rest.pojo.Account;
import pl.agh.edu.ethereumreservations.rest.utils.Parser;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

@RestController
public class AccountController {

    private final IEthereumService ethereumService;

    @Autowired
    public AccountController(IEthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    //e.g. localhost:8080/accounts
    @GetMapping("/accounts")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Account> listAllAccounts() {
        List<Account> accounts = new LinkedList<>();
        for (String accountString : ethereumService.getAccountsDescList()) {
            accounts.add(Parser.parseAccount(accountString));
        }
        return accounts;
    }

    //e.g. localhost:8080/accounts/bob
    @GetMapping("/accounts/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    public Account getAccount(@PathVariable("userName") String userName) {
        String accountString = ethereumService.getAccountDesc(userName);
        return Parser.parseAccount(accountString);
    }

    //e.g. localhost:8080/accounts/hex/janusz
    @GetMapping("/accounts/hex/{hexName}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getNameByHexString(@PathVariable("hexName") String hexName) {
        return ethereumService.getNameByHexString(hexName);
    }

    //e.g. localhost:8080/accounts/new/tom
    @PutMapping("/accounts/new/{userName}")
    public void addAccount(@PathVariable("userName") String userName) {
        ethereumService.addAccount(userName);
    }

}
