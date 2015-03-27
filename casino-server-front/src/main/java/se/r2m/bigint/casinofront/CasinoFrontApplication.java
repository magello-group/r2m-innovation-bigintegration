package se.r2m.bigint.casinofront;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import se.r2m.bigint.casinofront.game.GameEngine;
import se.r2m.bigint.casinofront.game.KafkaGameEngine;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class CasinoFrontApplication {

    private static final Logger LOG = LoggerFactory.getLogger(CasinoFrontApplication.class);

    public CasinoFrontApplication() {
        LOG.info("Starting CasinoFrontApplication...");

    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CasinoFrontApplication.class, args);

        // System.out.println("Let's inspect the beans provided by Spring Boot:");
        //
        //
        // String[] beanNames = ctx.getBeanDefinitionNames();
        // Arrays.sort(beanNames);
        // for (String beanName : beanNames) {
        // System.out.println(beanName);
        // }
    }

    @Bean
    public GameEngine getGameEngine() {
        return new KafkaGameEngine();
    }

}