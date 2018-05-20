package pl.agh.edu.ethereumreservations.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;

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

    @GetMapping("/accounts/{userName}")
    public String getAccount(@PathVariable("userName") String userName) {
        //e.g. localhost:8080/accounts/bob
        return ethereumService.getAccountDesc(userName);
    }

    @PutMapping("/accounts/new/{userName}")
    public void addAccount(@PathVariable("userName") String userName) {
        //e.g. localhost:8080/accounts/new/tom
        ethereumService.addAccount(userName);
    }

}
