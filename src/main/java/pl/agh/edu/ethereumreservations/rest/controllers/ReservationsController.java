package pl.agh.edu.ethereumreservations.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.ethereumreservations.services.ether_service.IEthereumService;

@RestController
public class ReservationsController {

    private final IEthereumService ethereumService;

    @Autowired
    public ReservationsController(IEthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    //e.g. localhost:8080/reservations/publish/main?estateName=est_main_0&estatePrice=20
    @PostMapping("/reservations/publish/{ownerName}")
    public void publishEstate(@PathVariable("ownerName") String ownerName, @RequestParam("estateName") String estateName, @RequestParam("estatePrice") int estatePrice) {
        ethereumService.publishEstate(ownerName, estateName, estatePrice);
    }

    //e.g. localhost:8080/reservations/reserve/bob?ownerName=main&index=0&day=0
    @PutMapping("reservations/reserve/{userName}")
    public void reserveDay(@PathVariable("userName") String userName, @RequestParam("ownerName") String ownerName, @RequestParam("index") int index, @RequestParam("day") int day) {
        ethereumService.makeReservation(userName, ownerName, index, day);
    }

    //e.g. localhost:8080/reservations/cancel/bob?ownerName=main&index=0&day=0
    @PostMapping("reservations/cancel/{userName}")
    public void cancelReservation(@PathVariable("userName") String userName, @RequestParam("ownerName") String ownerName, @RequestParam("index") int index, @RequestParam("day") int day) {
        ethereumService.cancelReservation(userName, ownerName, index, day);
    }

    //e.g. localhost:8080/reservations/status/bob?index=0&day=0&status=true
    @PostMapping("reservations/status/{ownerName}")
    public void changeStatus(@PathVariable("ownerName") String ownerName, @RequestParam("index") int index, @RequestParam("day") int day, @RequestParam("status") boolean status) {
        ethereumService.changeAvailabilityStatus(ownerName, index, day, status);
    }
}
