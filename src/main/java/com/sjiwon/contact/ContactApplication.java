package com.sjiwon.contact;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ContactApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(final ConfigurableApplicationContext context) {
        return args -> {
            final MainController controller = context.getBean(MainController.class);
            controller.run();
        };
    }
}
