package com.sjiwon.contact;

import com.sjiwon.contact.consoledb.application.ContactProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ContactProcessor contactProcessor;

    public void run() {
        contactProcessor.invoke();
    }
}
