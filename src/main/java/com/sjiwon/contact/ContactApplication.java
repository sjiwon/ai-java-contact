//package com.sjiwon.contact;
//
//import com.sjiwon.contact.execute.gui.controller.ContactMainController;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ConfigurableApplicationContext;
//
//import javax.swing.*;
//
//@SpringBootApplication
//public class ContactApplication {
//    // GUI
//    public static void main(final String[] args) {
//        final ConfigurableApplicationContext context = createApplicationContext(args);
//        displayMainFrame(context);
//    }
//
//    private static ConfigurableApplicationContext createApplicationContext(final String[] args) {
//        return new SpringApplicationBuilder(ContactApplication.class)
//                .headless(false)
//                .run(args);
//    }
//
//    private static void displayMainFrame(final ConfigurableApplicationContext context) {
//        SwingUtilities.invokeLater(() -> {
//            final ContactMainController mainController = context.getBean(ContactMainController.class);
//            mainController.setUpAndOpen();
//        });
//    }
//}
