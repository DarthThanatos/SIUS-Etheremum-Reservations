package pl.agh.edu.ethereumreservations.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.ReservationManager;

import java.util.List;

@RestController
public class DiagnosticController {

    private final IEthereumService ethereumService;

    @Autowired
    public DiagnosticController(IEthereumService ethereumService){
        this.ethereumService = ethereumService;
    }


    @GetMapping("/debug/accounts")
    public List<String> listAllAccounts(){
        return ethereumService.getAccountsDescList();
    }

    @GetMapping("/estates/{userName}")
    public List<ReservationManager.Estate> getAllEstates(@PathVariable("userName") String userName){
        //e.g. localhost:8080/estates/main
        return ethereumService.getAllEstates(userName);
    }
}
