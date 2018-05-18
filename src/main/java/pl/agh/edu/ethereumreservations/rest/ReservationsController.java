package pl.agh.edu.ethereumreservations.rest;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("reservations/reserve/{userName}")
    public void reserveDay(@PathVariable("userName") String userName, @RequestParam("ownerName") String ownerName, @RequestParam("index") int index, @RequestParam("day") int day){
        //e.g. put in postman: localhost:8080/reservations/reserve/bob?ownerName=main&index=0&day=0
        ethereumService.makeReservation(userName, ownerName, index, day);
    }
}
