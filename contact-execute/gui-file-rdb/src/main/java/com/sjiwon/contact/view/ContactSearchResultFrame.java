package com.sjiwon.contact.view;

import com.sjiwon.contact.frame.AbstractFrame;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Getter
@Component
@RequiredArgsConstructor
public class ContactSearchResultFrame extends AbstractFrame {
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
