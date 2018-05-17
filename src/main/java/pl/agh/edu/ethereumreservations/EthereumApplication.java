package pl.agh.edu.ethereumreservations;

import org.adridadou.ethereum.EthereumFacade;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.agh.edu.ethereumreservations.services.ether_service.config.EthereumConfig;
import pl.agh.edu.ethereumreservations.services.ether_service.ethereum.AccountsManager;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(EthereumConfig.class)
public class EthereumApplication {

    private static AccountsManager accountsManager;
    private static EthereumFacade ethereum;

    public static void main(String[] args){
        accountsManager = new AccountsManager();
        ethereum = new BlockChainBuilder(accountsManager, new EthereumConfig()).mountEthereum();
        SpringApplication.run(EthereumApplication.class, args);
    }

    public AccountsManager getAccountsManager() {
        return accountsManager;
    }

    public EthereumFacade getEthereum() {
        return ethereum;
    }

    @Bean
    CommandLineRunner init() {
        return (args) -> {

        };
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}
