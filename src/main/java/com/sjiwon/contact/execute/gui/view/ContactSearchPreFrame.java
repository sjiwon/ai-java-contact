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
public class ContactSearchPreFrame extends AbstractFrame {
    private JTextField mediumText;
    private JTextField lastText;
    private JButton processButton;

    @Override
    protected void setFrameUp() {
        setLayout(new GridLayout(4, 1, 30, 0));
        setSize(500, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        defaultSetUp();
    }

    @Override
    protected void initComponents() {
        // 설명
        final JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descriptionPanel.add(new JLabel("가운데 자리 또는 끝 자리 번호를 입력해주세요."));

        // 가운데 번호 조회
        final JPanel mediumPanel = new JPanel(new BorderLayout(5, 5));
        mediumText = new JTextField(10);
        mediumPanel.add(new JLabel("가운데 번호"), BorderLayout.WEST);
        mediumPanel.add(mediumText, BorderLayout.CENTER);

        // 마지막 번호 조회
        final JPanel lastPanel = new JPanel(new BorderLayout(5, 5));
        lastText = new JTextField(10);
        lastPanel.add(new JLabel("끝 번호"), BorderLayout.WEST);
        lastPanel.add(lastText, BorderLayout.CENTER);

        // 등록
        processButton = new JButton("조회");

        add(descriptionPanel);
        add(mediumPanel);
        add(lastPanel);
        add(processButton);
    }

    public void clear() {
        mediumText.setText("");
        lastText.setText("");
    }

    public String getMedium() {
        return mediumText.getText();
    }

    public String getLast() {
        return lastText.getText();
    }
}
