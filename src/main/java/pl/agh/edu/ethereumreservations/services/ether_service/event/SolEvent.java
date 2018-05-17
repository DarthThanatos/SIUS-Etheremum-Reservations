package pl.agh.edu.ethereumreservations.services.ether_service.event;

import java.util.List;

public abstract class SolEvent {
    public abstract List<String> addressesToTranslate();
}
