package pl.agh.edu.ethereumreservations.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;

@RestController
public class ReservationsController {

    private final IEthereumService ethereumService;

    @Autowired
    public ReservationsController(IEthereumService ethereumService){
        this.ethereumService = ethereumService;
    }

    @PostMapping("/reservations/publish/{ownerName}")
    public void publishEstate(@PathVariable("ownerName") String ownerName, @RequestParam String estateName, @RequestParam int estatePrice){
        //e.g. post in postman: localhost:8080/reservations/publish/main?estateName=est_main_0&estatePrice=20
        ethereumService.publishEstate(ownerName, estateName, estatePrice);
    }

}
