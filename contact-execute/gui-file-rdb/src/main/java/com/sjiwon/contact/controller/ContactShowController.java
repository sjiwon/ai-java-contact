package com.sjiwon.contact.controller;

import com.sjiwon.contact.application.ContactGuiRdbInteractProcessor;
import com.sjiwon.contact.domain.Contact;
import com.sjiwon.contact.frame.AbstractFrameController;
import com.sjiwon.contact.view.ContactShowFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContactShowController extends AbstractFrameController {
    private static final String[] TABLE_COLUMN = new String[]{"이름", "나이", "연락처", "등록 날짜"};

    private final ContactShowFrame contactShowFrame;
    private final ContactGuiRdbInteractProcessor contactGuiRdbInteractProcessor;

    @Override
    public void setUpAndOpen() {
        init();
        contactShowFrame.setVisible(true);
    }

    private void init() {
        final JTable contactTable = contactShowFrame.getContactTable();
        final String[][] contactArray = getContactArray();

        contactTable.setModel(new DefaultTableModel(contactArray, TABLE_COLUMN));
        contactTable.getTableHeader().setReorderingAllowed(false);

        final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        final TableColumnModel columnModel = contactTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(renderer);
        }
    }

    private String[][] getContactArray() {
        final List<Contact> contactRecords = contactGuiRdbInteractProcessor.findAll();
        final String[][] contactArray = new String[contactRecords.size()][4];

        for (int i = 0; i < contactRecords.size(); i++) {
            contactArray[i][0] = contactRecords.get(i).name();
            contactArray[i][1] = String.valueOf(contactRecords.get(i).age());
            contactArray[i][2] = contactRecords.get(i).phone();
            contactArray[i][3] = contactRecords.get(i).organizeCreatedAt();
        }

        return contactArray;
    }
}
