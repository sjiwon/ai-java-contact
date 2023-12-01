//package com.sjiwon.contact.controller;
//
//import com.sjiwon.contact.frame.AbstractFrameController;
//import com.sjiwon.contact.view.ContactRegisterFrame;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//
//import javax.swing.*;
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class ContactRegisterController extends AbstractFrameController {
//    private final ContactRegisterFrame contactRegisterFrame;
//    private final ContactGuiRdbInteractProcessor contactGuiRdbInteractProcessor;
//    private final ContactGuiFileInteractProcessor contactGuiFileInteractProcessor;
//
//    @Override
//    public void setUpAndOpen() {
//        registerButton(contactRegisterFrame.getProcessButton(), (e) -> process());
//        contactRegisterFrame.setVisible(true);
//    }
//
//    private void process() {
//        try {
//            final String name = contactRegisterFrame.getName();
//            final String age = contactRegisterFrame.getAge();
//            final String phone = contactRegisterFrame.getPhone();
//
//            if (isEmpty(name) || isEmpty(age) || isEmpty(phone)) {
//                return;
//            }
//
//            if (invalidNamePattern(name)) {
//                JOptionPane.showMessageDialog(
//                        null,
//                        "이름 형식이 잘못되었습니다.",
//                        "연락처 등록",
//                        JOptionPane.ERROR_MESSAGE
//                );
//                return;
//            }
//
//            if (invalidAge(age)) {
//                JOptionPane.showMessageDialog(
//                        null,
//                        "나이는 0보다 커야합니다.",
//                        "연락처 등록",
//                        JOptionPane.ERROR_MESSAGE
//                );
//                return;
//            }
//
//            if (invalidPhonePattern(phone)) {
//                JOptionPane.showMessageDialog(
//                        null,
//                        "전화번호는 xxx-xxx-xxxx 또는 xxx-xxxx-xxxx로 입력해주세요.",
//                        "연락처 등록",
//                        JOptionPane.ERROR_MESSAGE
//                );
//                return;
//            }
//
//            if (isPhoneExists(phone)) {
//                JOptionPane.showMessageDialog(
//                        null,
//                        "연락처에 이미 존재하는 번호입니다.",
//                        "연락처 등록",
//                        JOptionPane.ERROR_MESSAGE
//                );
//                return;
//            }
//
//            final Long contactId = contactGuiRdbInteractProcessor.create(name, Integer.parseInt(age), phone);
//            contactGuiFileInteractProcessor.create(contactId, name, Integer.parseInt(age), phone);
//            JOptionPane.showMessageDialog(
//                    null,
//                    "연락처가 등록되었습니다.",
//                    "연락처 등록",
//                    JOptionPane.INFORMATION_MESSAGE
//            );
//
//            contactRegisterFrame.clear();
//            contactRegisterFrame.setVisible(false);
//        } catch (final NumberFormatException e) {
//            JOptionPane.showMessageDialog(
//                    null,
//                    "나이는 숫자를 입력해주세요.",
//                    "연락처 등록",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        } catch (final Exception e) {
//            e.printStackTrace();
//
//            JOptionPane.showMessageDialog(
//                    null,
//                    "연락처 등록에 실패하였습니다." + e.getMessage(),
//                    "연락처 등록",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }
//
//    private boolean isEmpty(final String value) {
//        if (value == null || value.isEmpty()) {
//            JOptionPane.showMessageDialog(
//                    null,
//                    "빈 값이 존재합니다.",
//                    "연락처 등록",
//                    JOptionPane.ERROR_MESSAGE
//            );
//            return true;
//        }
//        return false;
//    }
//
//    private boolean invalidNamePattern(final String value) {
//        return !NAME_PATTERN.matcher(value).matches();
//    }
//
//    private boolean invalidAge(final String value) {
//        final int age = Integer.parseInt(value);
//        return age <= 0;
//    }
//
//    private boolean invalidPhonePattern(final String value) {
//        return !PHONE_PATTERN.matcher(value).matches();
//    }
//
//    private boolean isPhoneExists(final String value) {
//        final List<String> filterWithName = contactGuiRdbInteractProcessor.findAll()
//                .stream()
//                .map(Contact::phone)
//                .toList();
//
//        return filterWithName.contains(value);
//    }
//}
