package com.sjiwon.contact.frame;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class AbstractFrameController {
    public abstract void setUpAndOpen();

    protected void registerButton(final JButton button, final ActionListener listener) {
        for (final ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
        button.addActionListener(listener);
    }
}
