package com.sjiwon.contact.controller;

import com.sjiwon.contact.frame.AbstractFrameController;
import com.sjiwon.contact.view.ContactMainFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.swing.*;

@Controller
@RequiredArgsConstructor
public class ContactMainController extends AbstractFrameController {
    private final ContactMainFrame contactMainFrame;
    private final ContactShowController contactShowController;
    private final ContactRegisterController contactRegisterController;
    private final ContactSearchController contactSearchController;
    private final ContactDeleteController contactDeleteController;

    @Override
    public void setUpAndOpen() {
        registerButton(contactMainFrame.getShowButton(), (e) -> openShowWindow());
        registerButton(contactMainFrame.getRegisterButton(), (e) -> openRegisterWindow());
        registerButton(contactMainFrame.getSearchButton(), (e) -> openSearchWindow());
        registerButton(contactMainFrame.getDeleteButton(), (e) -> openDeleteWindow());
        registerButton(contactMainFrame.getFinishButton(), (e) -> finish());
        contactMainFrame.setVisible(true);
    }

    private void openShowWindow() {
        contactShowController.setUpAndOpen();
    }

    private void openRegisterWindow() {
        contactRegisterController.setUpAndOpen();
    }

    private void openSearchWindow() {
        contactSearchController.setUpAndOpen();
    }

    private void openDeleteWindow() {
        contactDeleteController.setUpAndOpen();
    }

    private void finish() {
        final int result = JOptionPane.showConfirmDialog(
                null,
                "종료하시겠습니까?",
                "연락처 관리 프로그램",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            contactMainFrame.setVisible(false);
            try {
                Thread.sleep(500);
                System.exit(0);
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
