//package com.sjiwon.contact.domain.application;
//
//import com.sjiwon.contact.common.file.FileInteractType;
//import com.sjiwon.contact.common.file.FileInteractor;
//import com.sjiwon.contact.domain.model.Contact;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.IntStream;
//
//
//@Component
//@RequiredArgsConstructor
//public class ContactGuiFileInteractProcessor {
//    private final FileInteractor fileInteractor;
//
//    public List<Contact> findAll() {
//        return fileInteractor.read(FileInteractType.GUI);
//    }
//
//    public List<Contact> findByMedium(final String value) {
//        return fileInteractor.read(FileInteractType.GUI)
//                .stream()
//                .filter(contact -> contact.getMediumPartOfPhone().contains(value))
//                .toList();
//    }
//
//    public List<Contact> findByLast(final String value) {
//        return fileInteractor.read(FileInteractType.GUI)
//                .stream()
//                .filter(contact -> contact.getLastPartOfPhone().contains(value))
//                .toList();
//    }
//
//    /**
//     * RDB에 저장 후 id 받아서 invoke
//     */
//    public void create(final Long id, final String name, final int age, final String phone) {
//        final Contact contact = new Contact(id, name, age, phone);
//
//        fileInteractor.updateLastId(FileInteractType.GUI, id);
//        fileInteractor.writeWithKeepAdd(FileInteractType.GUI, contact);
//    }
//
//    public void delete(final Long id) {
//        final List<Contact> contacts = fileInteractor.read(FileInteractType.GUI);
//        if (contacts.isEmpty()) {
//            return;
//        }
//
//        final List<Long> contactIds = contacts.stream()
//                .map(Contact::id)
//                .toList();
//        if (!contactIds.contains(id)) {
//            return;
//        }
//
//        final int removeIdx = getRemoveIdx(contactIds, id);
//        if (removeIdx == -1) {
//            return;
//        }
//
//        contacts.remove(removeIdx);
//        fileInteractor.writeWithInit(FileInteractType.GUI, contacts);
//    }
//
//    private int getRemoveIdx(final List<Long> contactIds, final Long id) {
//        return IntStream.range(0, contactIds.size())
//                .filter(i -> contactIds.get(i).equals(id))
//                .findFirst()
//                .orElse(-1);
//    }
//}
