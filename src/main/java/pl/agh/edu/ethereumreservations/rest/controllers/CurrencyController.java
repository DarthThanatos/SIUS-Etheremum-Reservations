package pl.agh.edu.ethereumreservations.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.ethereumreservations.rest.pojo.CustomCurrency;
import pl.agh.edu.ethereumreservations.rest.pojo.EtherumCurrency;
import pl.agh.edu.ethereumreservations.rest.utils.Parser;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

@RestController
public class CurrencyController {

    private final IEthereumService ethereumService;

    @Autowired
    public CurrencyController(IEthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    //e.g. localhost:8080/currency/mint/bob?targetName=main&amount=20
    @PostMapping("/currency/mint/{issuer}")
    public void mintCustomCurrency(@PathVariable("issuer") String issuer, @RequestParam("targetName") String targetName, @RequestParam("amount") int amount) {
        ethereumService.mintCustomCurrency(issuer, targetName, amount);
    }

    //e.g. localhost:8080/currency/send/custom/bob?targetName=main&amount=20
    @PostMapping("/currency/send/custom/{issuer}")
    public void sendCustomCurrency(@PathVariable("issuer") String issuer, @RequestParam("targetName") String targetName, @RequestParam("amount") int amount) {
        ethereumService.sendCustomCurrency(issuer, targetName, amount);
    }

    //e.g. localhost:8080/currency/pay/bob?ownerName=main&index=1&amount=5&weekDay=3
    @PostMapping("/currency/pay/{clientName}")
    public void payForReservation(@PathVariable("clientName") String clientName, @RequestParam("ownerName") String ownerName, @RequestParam("index") int index, @RequestParam("amount") int amount, @RequestParam("weekDay") int weekDay) {
        ethereumService.payForReservation(clientName, ownerName, index, amount, weekDay);
    }

    //e.g. localhost:8080/currency/send/ether/bob?targetName=main&amount=20
    @PostMapping("/currency/send/ether/{issuer}")
    public void sendEtherCurrency(@PathVariable("issuer") String issuer, @RequestParam("targetName") String targetName, @RequestParam("amount") int amount) {
        ethereumService.sendEther(issuer, targetName, amount);
    }

    //e.g. localhost:8080/currency/balance/custom/all
    @GetMapping("/currency/balance/custom/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CustomCurrency> getCustomBalances() {
        List<CustomCurrency> customCurrencies = new LinkedList<>();
        for (String description : ethereumService.getCustomCurrencyBalances()) {
            customCurrencies.add(Parser.parseCustom(description));
        }
        return customCurrencies;
    }

    //e.g. localhost:8080/currency/balance/ether/all
    @GetMapping("/currency/balance/ether/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<EtherumCurrency> getEtherBalances() {
        List<EtherumCurrency> etherumCurrencies = new LinkedList<>();
        for (String description : ethereumService.getEthereumBalances()) {
            etherumCurrencies.add(Parser.parseEtherum(description));
        }
        return etherumCurrencies;
    }

    //e.g. localhost:8080/currency/balance/custom/bob
    @GetMapping("/currency/balance/custom/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    public CustomCurrency getCustomBalance(@PathVariable("userName") String userName) {
        String description = ethereumService.getCustomCurrencyBalance(userName);
        return Parser.parseCustom(description);
    }

    //e.g. localhost:8080/currency/balance/ether/bob
    @GetMapping("/currency/balance/ether/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    public EtherumCurrency getEtherBalance(@PathVariable("userName") String userName) {
        String description = ethereumService.getEthereumBalance(userName);
        return Parser.parseEtherum(description);
    }

}