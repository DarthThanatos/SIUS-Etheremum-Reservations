package pl.agh.edu.ethereumreservations.services.ether_service.event;

import org.springframework.beans.factory.annotation.Autowired;
import pl.agh.edu.ethereumreservations.services.ether_service.config.EthereumConfig;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

import java.util.stream.Collectors;

public class EventHandler <T extends SolEvent>{

    private int i = 0;
    private AccountsManager accountsManager;

    private EthereumConfig config;

    public EventHandler(AccountsManager accountsManager){
        this.accountsManager = accountsManager;
    }

    public void handle(T event, NonDefaultEventHandler<T> nonDefaultEventHandler){
        Object[] translatedAdresses =
                event.addressesToTranslate().stream()
                        .map(s -> accountsManager.getReadableNameFromHexForm(s)).collect(Collectors.toList()).toArray();
        if(config.isDevelPhase()){
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

        System.out.println(
                String.format(
                        event.toString(),
                        translatedAdresses
                )
        );
        if(nonDefaultEventHandler != null) nonDefaultEventHandler.handle(event);
    }

    @Autowired
    public void setConfig(EthereumConfig config) {
        System.out.println("Setting config in ecent handler");
        this.config = config;
    }

    public interface NonDefaultEventHandler<T extends SolEvent>{
        void handle(T event);
    }
}