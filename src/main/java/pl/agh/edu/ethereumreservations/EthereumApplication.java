package pl.agh.edu.ethereumreservations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.agh.edu.ethereumreservations.services.ether_service.config.EthereumConfig;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(EthereumConfig.class)
public class EthereumApplication {


    public static void main(String[] args){
        SpringApplication.run(EthereumApplication.class, args);
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
