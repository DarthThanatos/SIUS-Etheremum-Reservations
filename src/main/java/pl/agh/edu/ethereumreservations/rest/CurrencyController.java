package pl.agh.edu.ethereumreservations.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;

import java.util.List;

@RestController
public class CurrencyController {

    private final IEthereumService ethereumService;

    @Autowired
    public CurrencyController(IEthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    @PostMapping("/currency/mint/{issuer}")
    public void mintCustomCurrency(@PathVariable("issuer") String issuer, @RequestParam("targetName") String targetName, @RequestParam("amount") int amount) {
        //e.g. localhost:8080/currency/mint/bob?targetName=main&amount=20
        ethereumService.mintCustomCurrency(issuer, targetName, amount);
    }

    @PostMapping("/currency/send/custom/{issuer}")
    public void sendCustomCurrency(@PathVariable("issuer") String issuer, @RequestParam("targetName") String targetName, @RequestParam("amount") int amount) {
        //e.g. localhost:8080/currency/send/custom/bob?targetName=main&amount=20
        ethereumService.sendCustomCurrency(issuer, targetName, amount);
    }

    @PostMapping("/currency/pay/{clientName}")
    public void payForReservation(@PathVariable("clientName") String clientName, @RequestParam("ownerName") String ownerName, @RequestParam("index") int index, @RequestParam("amount") int amount, @RequestParam("weekDay") int weekDay) {
        //e.g. localhost:8080/currency/pay/bob?ownerName=main&index=1&amount=5&weekDay=3
        ethereumService.payForReservation(clientName, ownerName, index, amount, weekDay);
    }

    @PostMapping("/currency/send/ether/{issuer}")
    public void sendEtherCurrency(@PathVariable("issuer") String issuer, @RequestParam("targetName") String targetName, @RequestParam("amount") int amount) {
        //e.g. localhost:8080/currency/send/ether/bob?targetName=main&amount=20
        ethereumService.sendEther(issuer, targetName, amount);
    }

    @GetMapping("/currency/balance/custom/all")
    public List<String> getCustomBalances() {
        //e.g. localhost:8080/currency/balance/custom/all
        return ethereumService.getCustomCurrencyBalances();
    }

    @GetMapping("/currency/balance/ether/all")
    public List<String> getEtherBalances() {
        //e.g. localhost:8080/currency/balance/ether/all
        return ethereumService.getEthereumBalances();
    }

    @GetMapping("/currency/balance/custom/{userName}")
    public String getCustomBalance(@PathVariable("userName") String userName) {
        //e.g. localhost:8080/currency/balance/custom/bob
        return ethereumService.getCustomCurrencyBalance(userName);
    }

    @GetMapping("/currency/balance/ether/{userName}")
    public String getEtherBalance(@PathVariable("userName") String userName) {
        //e.g. localhost:8080/currency/balance/ether/bob
        return ethereumService.getEthereumBalance(userName);
    }

}