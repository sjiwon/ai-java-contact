package com.sjiwon.contact.common.frame;

import jakarta.annotation.PostConstruct;

import javax.swing.*;

public abstract class AbstractFrame extends JFrame {
    private static final String TITLE = "KGU 연락처 관리 프로그램";

    @PostConstruct
    protected void prepareFrame() {
        setFrameUp();
        initComponents();
    }

    protected abstract void setFrameUp();

    protected abstract void initComponents();

    protected void defaultSetUp() {
        getRootPane().setBorder(Borders.createEmptyBorder());
        setTitle(TITLE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
