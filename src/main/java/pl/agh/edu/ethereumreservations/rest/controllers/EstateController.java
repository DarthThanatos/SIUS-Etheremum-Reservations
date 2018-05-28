package pl.agh.edu.ethereumreservations.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.ethereumreservations.rest.pojo.Account;
import pl.agh.edu.ethereumreservations.rest.utils.Parser;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.ReservationManager;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

@RestController
public class EstateController {

    private final IEthereumService ethereumService;

    @Autowired
    public EstateController(IEthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    //e.g. localhost:8080/estates/main
    @GetMapping("/estates/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ReservationManager.Estate> getAllUserEstates(@PathVariable("userName") String userName) {
        return ethereumService.getAllEstates(userName);
    }

    //e.g. localhost:8080/estates/main/bob
    @PostMapping("/estates/{userName}/{ownerName}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ReservationManager.Estate> getAllUserOwnerEstates(@PathVariable("userName") String userName, @PathVariable("ownerName") String ownerName) {
        return ethereumService.getAllUserEstates(userName, ownerName);
    }

    //e.g. localhost:8080/estates/main/1
    @PostMapping("/estates/{userName}/{index}")
    @Produces({MediaType.APPLICATION_JSON})
    public ReservationManager.Estate getEstateAt(@PathVariable("userName") String userName, @PathVariable("index") int index) {
        return ethereumService.getEstateAt(userName, index);
    }

    //e.g. localhost:8080/estates/main/bob/1
    @PostMapping("/estates/{userName}/{ownerName}/{index}")
    @Produces({MediaType.APPLICATION_JSON})
    public ReservationManager.Estate getEstateAt(@PathVariable("userName") String userName, @PathVariable("ownerName") String ownerName, @PathVariable("index") int index) {
        return ethereumService.getEstateDescription(userName, ownerName, index);
    }
}
