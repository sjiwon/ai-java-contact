package com.sjiwon.contact;

import com.sjiwon.contact.consoledb.application.ContactConsoleProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ContactConsoleProcessor contactConsoleProcessor;

    public void run() {
        contactConsoleProcessor.invoke();
    }
}
