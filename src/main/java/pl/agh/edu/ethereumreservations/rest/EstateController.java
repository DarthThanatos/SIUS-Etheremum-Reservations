package pl.agh.edu.ethereumreservations.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.ReservationManager;

import java.util.List;

@RestController
public class EstateController {

    private final IEthereumService ethereumService;

    @Autowired
    public EstateController(IEthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    @GetMapping("/estates/{userName}")
    public List<ReservationManager.Estate> getAllEstates(@PathVariable("userName") String userName) {
        //e.g. localhost:8080/estates/main
        return ethereumService.getAllEstates(userName);
    }

    @PostMapping("/estates/{userName}/{ownerName}")
    public List<ReservationManager.Estate> getAllUserEstates(@PathVariable("userName") String userName, @PathVariable("ownerName") String ownerName) {
        //e.g. localhost:8080/estates/main/bob
        return ethereumService.getAllUserEstates(userName, ownerName);
    }

    @PostMapping("/estates/{userName}/{index}")
    public ReservationManager.Estate getEstateAt(@PathVariable("userName") String userName, @PathVariable("index") int index) {
        //e.g. localhost:8080/estates/main/1
        return ethereumService.getEstateAt(userName, index);
    }

    @PostMapping("/estates/{userName}/{ownerName}/{index}")
    public ReservationManager.Estate getEstateAt(@PathVariable("userName") String userName, @PathVariable("ownerName") String ownerName, @PathVariable("index") int index) {
        //e.g. localhost:8080/estates/main/bob/1
        return ethereumService.getEstateDescription(userName, ownerName, index);
    }
}
