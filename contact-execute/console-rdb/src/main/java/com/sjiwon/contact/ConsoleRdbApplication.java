package com.sjiwon.contact;

import com.sjiwon.contact.controller.MainController;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsoleRdbApplication {
    public static void main(final String[] args) {
        System.setProperty("RDB_PORT", "13306");
        SpringApplication.run(ConsoleRdbApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(final ConfigurableApplicationContext context) {
        return args -> {
            final MainController controller = context.getBean(MainController.class);
            controller.invoke();
        };
    }
}
