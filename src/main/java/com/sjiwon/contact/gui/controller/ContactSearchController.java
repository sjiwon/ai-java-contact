package com.sjiwon.contact.gui.controller;

import com.sjiwon.contact.common.AbstractFrameController;
import com.sjiwon.contact.common.Constants;
import com.sjiwon.contact.consoledb.application.ContactGuiRdbInteractProcessor;
import com.sjiwon.contact.domain.Contact;
import com.sjiwon.contact.gui.ui.ContactSearchPreFrame;
import com.sjiwon.contact.gui.ui.ContactSearchResultFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContactSearchController extends AbstractFrameController {
    private static final String[] TABLE_COLUMN = new String[]{"이름", "나이", "연락처", "등록 날짜"};

    private final ContactSearchPreFrame contactSearchPreFrame;
    private final ContactSearchResultFrame contactSearchResultFrame;
    private final ContactGuiRdbInteractProcessor contactGuiRdbInteractProcessor;

    @Override
    public void setUpAndOpen() {
        registerButton(contactSearchPreFrame.getProcessButton(), (e) -> process());
        contactSearchPreFrame.setVisible(true);
    }

    private void process() {
        if (init()) {
            contactSearchPreFrame.clear();
            contactSearchPreFrame.setVisible(false);
            contactSearchResultFrame.setVisible(true);
        }
    }

    private boolean init() {
        try {
            final String medium = contactSearchPreFrame.getMedium();
            final String last = contactSearchPreFrame.getLast();

            if (isEmpty(medium) && isEmpty(last)) {
                JOptionPane.showMessageDialog(
                        null,
                        "가운데 또는 끝 자리 번호를 반드시 입력해야 합니다.",
                        "연락처 등록",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }

            if (!medium.isBlank() && !last.isBlank()) {
                JOptionPane.showMessageDialog(
                        null,
                        "가운데 또는 끝 자리 번호 중 하나만 입력해야 합니다.",
                        "연락처 등록",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }

            final JTable contactTable = contactSearchResultFrame.getContactTable();
            final String[][] contactArray = medium.isBlank() ? getContactArray(Type.LAST, last) : getContactArray(Type.MEDIUM, medium);

            contactTable.setModel(new DefaultTableModel(contactArray, TABLE_COLUMN));
            contactTable.getTableHeader().setReorderingAllowed(false);

            final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(SwingConstants.CENTER);

            final TableColumnModel columnModel = contactTable.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setCellRenderer(renderer);
            }

            return true;
        } catch (final Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "연락처 조회",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return false;
    }

    private boolean isEmpty(final String value) {
        return value == null || value.isEmpty();
    }

    private String[][] getContactArray(final Type type, final String value) {
        final List<Contact> contactRecords = getRecords(type, value);
        final String[][] contactArray = new String[contactRecords.size()][4];

        for (int i = 0; i < contactRecords.size(); i++) {
            contactArray[i][0] = contactRecords.get(i).name();
            contactArray[i][1] = String.valueOf(contactRecords.get(i).age());
            contactArray[i][2] = contactRecords.get(i).phone();
            contactArray[i][3] = contactRecords.get(i).createdAt().format(Constants.DATE_FORMAT);
        }

        return contactArray;
    }

    private List<Contact> getRecords(final Type type, final String value) {
        if (type == Type.MEDIUM) {
            return contactGuiRdbInteractProcessor.findByMedium(value);
        }
        return contactGuiRdbInteractProcessor.findByLast(value);
    }

    private enum Type {
        MEDIUM, LAST
    }
}
