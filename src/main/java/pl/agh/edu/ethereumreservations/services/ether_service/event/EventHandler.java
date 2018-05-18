package pl.agh.edu.ethereumreservations.services.ether_service.event;

import pl.agh.edu.ethereumreservations.rest.NotificationController;
import pl.agh.edu.ethereumreservations.services.ether_service.config.EthereumConfig;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

import java.util.stream.Collectors;

public class EventHandler <T extends SolEvent>{

    private int i = 0;
    private AccountsManager accountsManager;

    public EventHandler(AccountsManager accountsManager){
        this.accountsManager = accountsManager;
    }

    public void handle(T event, NonDefaultEventHandler<T> nonDefaultEventHandler){
        Object[] translatedAdresses =
                event.addressesToTranslate().stream()
                        .map(s -> accountsManager.getReadableNameFromHexForm(s)).collect(Collectors.toList()).toArray();
        if(new EthereumConfig().isDevelPhase()){
            if(i == 0) {
                act(event, nonDefaultEventHandler,translatedAdresses);
                i = 1;
            }
            else i = 0;

        }
        else act(event, nonDefaultEventHandler,translatedAdresses);
    }


    public void handle(T event){
        handle(event, null);
    }


    private void act(T event, NonDefaultEventHandler<T> nonDefaultEventHandler, Object[] translatedAdresses){
        String res =
                String.format(
                        event.toString(),
                        translatedAdresses
                );
        System.out.println( res );
        System.out.println("Sending to /events");
        NotificationController.template.convertAndSend("/events", res);
        if(nonDefaultEventHandler != null) nonDefaultEventHandler.handle(event);
    }



    public interface NonDefaultEventHandler<T extends SolEvent>{
        void handle(T event);
    }
}