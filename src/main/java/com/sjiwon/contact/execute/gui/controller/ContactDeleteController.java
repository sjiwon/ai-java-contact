package com.sjiwon.contact.execute.gui.controller;

import com.sjiwon.contact.common.Constants;
import com.sjiwon.contact.common.frame.AbstractFrameController;
import com.sjiwon.contact.domain.application.ContactGuiFileInteractProcessor;
import com.sjiwon.contact.domain.application.ContactGuiRdbInteractProcessor;
import com.sjiwon.contact.domain.model.Contact;
import com.sjiwon.contact.execute.gui.view.ContactDeleteFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContactDeleteController extends AbstractFrameController {
    private static final String[] TABLE_COLUMN = new String[]{"ID", "이름", "나이", "연락처", "등록 날짜", "삭제"};

    private final ContactDeleteFrame contactDeleteFrame;
    private final ContactGuiRdbInteractProcessor contactGuiRdbInteractProcessor;
    private final ContactGuiFileInteractProcessor contactGuiFileInteractProcessor;

    @Override
    public void setUpAndOpen() {
        init();
        contactDeleteFrame.setVisible(true);
    }

    private void init() {
        final JTable contactTable = contactDeleteFrame.getContactTable();
        final String[][] contactArray = getMovieArray();

        contactTable.setModel(new DefaultTableModel(contactArray, TABLE_COLUMN));
        contactTable.getTableHeader().setReorderingAllowed(false);

        final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        final TableColumnModel columnModel = contactTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(renderer);
        }

        final TableModel model = contactTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            final TableColumn deleteColumn = columnModel.getColumn(5);
            deleteColumn.setCellRenderer(new PhoneDeleteCell());
            deleteColumn.setCellEditor(new PhoneDeleteCell());
        }
    }

    private String[][] getMovieArray() {
        final List<Contact> contactRecords = contactGuiRdbInteractProcessor.findAll();
        final String[][] contactArray = new String[contactRecords.size()][6];

        for (int i = 0; i < contactRecords.size(); i++) {
            contactArray[i][0] = String.valueOf(contactRecords.get(i).id());
            contactArray[i][1] = contactRecords.get(i).name();
            contactArray[i][2] = String.valueOf(contactRecords.get(i).age());
            contactArray[i][3] = contactRecords.get(i).phone();
            contactArray[i][4] = contactRecords.get(i).createdAt().format(Constants.DATE_FORMAT);
            contactArray[i][5] = "Delete";
        }

        return contactArray;
    }

    class PhoneDeleteCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        private final JButton deleteButton;
        private Long contactId;
        private String contactPhone;

        public PhoneDeleteCell() {
            deleteButton = new JButton("삭제하기");
            deleteButton.addActionListener(e -> {
                contactGuiFileInteractProcessor.delete(contactId);
                contactGuiRdbInteractProcessor.delete(contactId);
                JOptionPane.showMessageDialog(
                        null,
                        String.format("[%s] 연락처가 삭제되었습니다.", contactPhone),
                        "연락처 삭제",
                        JOptionPane.INFORMATION_MESSAGE
                );
                contactDeleteFrame.setVisible(false);
            });
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(
                final JTable table,
                final Object value,
                final boolean isSelected,
                final boolean hasFocus,
                final int row,
                final int column
        ) {
            contactId = Long.valueOf((String) table.getValueAt(row, 0));
            contactPhone = (String) table.getValueAt(row, 1);
            return deleteButton;
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(
                final JTable table,
                final Object value,
                final boolean isSelected,
                final int row,
                final int column
        ) {
            contactId = Long.valueOf((String) table.getValueAt(row, 0));
            contactPhone = (String) table.getValueAt(row, 1);
            return deleteButton;
        }
    }
}
