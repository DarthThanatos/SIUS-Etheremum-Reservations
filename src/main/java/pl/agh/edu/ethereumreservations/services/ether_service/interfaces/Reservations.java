package pl.agh.edu.ethereumreservations.services.ether_service.interfaces;

import org.adridadou.ethereum.values.EthAccount;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.ReservationManager;

import java.util.concurrent.CompletableFuture;

public interface Reservations {

    CompletableFuture<Void> tryToReserve(EthAccount estateOwner, int estateIndex, int day);

    CompletableFuture<Void> tryToCancel(EthAccount estateOwner, int estateIndex, int day);

    CompletableFuture<Void> publishEstate(String name, int price, Boolean[] daysAvailabilityStates, Boolean[] daysReservationStates);

    ReservationManager.Estate getEstateOfOwnerByIndex(EthAccount estatesOwner, int index);

    ReservationManager.Estate getEstateByIndex(int index);

    int getOwnerEstatesAmount(EthAccount estatesOwner);

    int getAllEstatesAmount();

    String getTenantOfOwner(EthAccount estateOwner, int estateIndex, int day);

    String getTenant(int estateIndex, int day);

    CompletableFuture<Void> tryToChangeAvailableDay(int estateLocalIndex, int day, boolean available);

    CompletableFuture<Boolean> checkPaidForReservation(EthAccount estateOwner, int estateIndex, int day);

    CompletableFuture<Integer> getRemainingQuote(EthAccount estateOwner, int estateLocalIndex, int day);

    CompletableFuture<Integer> getRemainingQuoteGlobal(int globalIndex, int day);
}
