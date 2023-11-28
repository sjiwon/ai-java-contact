package com.sjiwon.contact.gui.ui;

import com.sjiwon.contact.common.AbstractFrame;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Getter
@Component
@RequiredArgsConstructor
public class ContactShowFrame extends AbstractFrame {
    private JTable contactTable;
    private JScrollPane tableScrollPane;

    @Override
    protected void setFrameUp() {
        setLayout(new GridLayout(1, 1, 0, 0));
        setSize(840, 500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        defaultSetUp();
    }

    @Override
    protected void initComponents() {
        contactTable = new JTable();
        tableScrollPane = new JScrollPane(contactTable);
        add(tableScrollPane);
    }
}
