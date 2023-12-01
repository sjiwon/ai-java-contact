package com.sjiwon.contact.execute.gui.view;

import com.sjiwon.contact.common.frame.AbstractFrame;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Getter
@Component
@RequiredArgsConstructor
public class ContactRegisterFrame extends AbstractFrame {
    private JTextField nameText;
    private JTextField ageText;
    private JTextField phoneText;
    private JButton processButton;

    @Override
    protected void setFrameUp() {
        setLayout(new GridLayout(4, 1, 10, 10));
        setSize(500, 280);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        defaultSetUp();
    }

    @Override
    protected void initComponents() {
        // 이름
        final JPanel namePanel = new JPanel(new BorderLayout(5, 5));
        nameText = new JTextField(10);
        namePanel.add(new JLabel("이름"), BorderLayout.WEST);
        namePanel.add(nameText, BorderLayout.CENTER);

        // 나이
        final JPanel agePanel = new JPanel(new BorderLayout(5, 5));
        ageText = new JTextField(10);
        agePanel.add(new JLabel("나이"), BorderLayout.WEST);
        agePanel.add(ageText, BorderLayout.CENTER);

        // 전화번호
        final JPanel phonePanel = new JPanel(new BorderLayout(5, 5));
        phoneText = new JTextField(10);
        phonePanel.add(new JLabel("전화번호"), BorderLayout.WEST);
        phonePanel.add(phoneText, BorderLayout.CENTER);

        // 등록
        processButton = new JButton("등록");

        add(namePanel);
        add(agePanel);
        add(phonePanel);
        add(processButton);
    }

    public void clear() {
        nameText.setText("");
        ageText.setText("");
        phoneText.setText("");
    }

    public String getName() {
        final String text = nameText.getText();
        return text.isBlank() ? null : text;
    }

    public String getAge() {
        final String text = ageText.getText();
        return text.isBlank() ? null : text;
    }

    public String getPhone() {
        final String text = phoneText.getText();
        return text.isBlank() ? null : text;
    }
}
