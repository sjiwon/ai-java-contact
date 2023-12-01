package com.sjiwon.contact.view;

import com.sjiwon.contact.frame.AbstractFrame;
import com.sjiwon.contact.frame.ImageManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Getter
@Component
@RequiredArgsConstructor
public class ContactMainFrame extends AbstractFrame {
    private JButton showButton;
    private JButton registerButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JButton finishButton;

    @Override
    protected void setFrameUp() {
        setLayout(new GridLayout(1, 5, 30, 0));
        setSize(1280, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        defaultSetUp();
    }

    @Override
    protected void initComponents() {
        // 1. 연락처 출력
        final JPanel showPanel = new JPanel(new BorderLayout());
        showButton = new JButton(ImageManager.getStaticImage("show.png", 200, 200));
        showButton.setBackground(Color.white);
        showButton.setBorderPainted(false);
        showButton.setContentAreaFilled(false);
        showButton.setFocusPainted(false);

        final JLabel showLabel = new JLabel("연락처 출력");
        showLabel.setHorizontalAlignment(JLabel.CENTER);
        showLabel.setFont(new Font("휴먼굴림체", Font.BOLD, 20));

        showPanel.add(showButton, BorderLayout.CENTER);
        showPanel.add(showLabel, BorderLayout.SOUTH);

        // 2. 연락처 등록
        final JPanel registerPanel = new JPanel(new BorderLayout());
        registerButton = new JButton(ImageManager.getStaticImage("register.png", 200, 200));
        registerButton.setBackground(Color.white);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);

        final JLabel registerLabel = new JLabel("연락처 등록");
        registerLabel.setHorizontalAlignment(JLabel.CENTER);
        registerLabel.setFont(new Font("휴먼굴림체", Font.BOLD, 20));

        registerPanel.add(registerButton, BorderLayout.CENTER);
        registerPanel.add(registerLabel, BorderLayout.SOUTH);

        // 3. 연락처 검색
        final JPanel searchPanel = new JPanel(new BorderLayout());
        searchButton = new JButton(ImageManager.getStaticImage("search.png", 200, 200));
        searchButton.setBackground(Color.white);
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setFocusPainted(false);

        final JLabel searchLabel = new JLabel("연락처 검색");
        searchLabel.setHorizontalAlignment(JLabel.CENTER);
        searchLabel.setFont(new Font("휴먼굴림체", Font.BOLD, 20));

        searchPanel.add(searchButton, BorderLayout.CENTER);
        searchPanel.add(searchLabel, BorderLayout.SOUTH);

        // 4. 연락처 삭제
        final JPanel deletePanel = new JPanel(new BorderLayout());
        deleteButton = new JButton(ImageManager.getStaticImage("delete.png", 200, 200));
        deleteButton.setBackground(Color.white);
        deleteButton.setBorderPainted(false);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setFocusPainted(false);

        final JLabel deleteLabel = new JLabel("연락처 삭제");
        deleteLabel.setHorizontalAlignment(JLabel.CENTER);
        deleteLabel.setFont(new Font("휴먼굴림체", Font.BOLD, 20));

        deletePanel.add(deleteButton, BorderLayout.CENTER);
        deletePanel.add(deleteLabel, BorderLayout.SOUTH);

        // 5. 끝내기
        final JPanel finishPanel = new JPanel(new BorderLayout());
        finishButton = new JButton(ImageManager.getStaticImage("logout.png", 200, 200));
        finishButton.setBackground(Color.white);
        finishButton.setBorderPainted(false);
        finishButton.setContentAreaFilled(false);
        finishButton.setFocusPainted(false);

        final JLabel finishLabel = new JLabel("종료");
        finishLabel.setHorizontalAlignment(JLabel.CENTER);
        finishLabel.setFont(new Font("휴먼굴림체", Font.BOLD, 20));

        finishPanel.add(finishButton, BorderLayout.CENTER);
        finishPanel.add(finishLabel, BorderLayout.SOUTH);

        add(showPanel);
        add(registerPanel);
        add(searchPanel);
        add(deletePanel);
        add(finishPanel);
    }
}
