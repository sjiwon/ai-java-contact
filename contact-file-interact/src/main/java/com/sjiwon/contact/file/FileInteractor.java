package com.sjiwon.contact.file;

import com.sjiwon.contact.domain.Contact;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileInteractor {
    private static final String PROJECT_PATH = System.getProperty("user.dir") + "\\contact-file-interact\\src\\main\\java\\com\\sjiwon\\contact";
    private static final String PHONE_FILE_PATH_WITH_CONSOLE = PROJECT_PATH + "\\store\\console\\phone.txt";
    private static final String LAST_ID_FILE_PATH_WITH_CONSOLE = PROJECT_PATH + "\\store\\console\\lastId.txt";
    private static final String PHONE_FILE_PATH_WITH_GUI = PROJECT_PATH + "\\store\\gui\\phone.txt";
    private static final String LAST_ID_FILE_PATH_WITH_GUI = PROJECT_PATH + "\\store\\gui\\lastId.txt";

    /**
     * Singleton Instance
     */
    private FileInteractor() {
    }

    private static class SingletonHolder {
        private static final FileInteractor INSTANCE = new FileInteractor();
    }

    public static FileInteractor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 파일 없으면 세팅
     */
    public void initialize() {
        final List<String> phoneFiles = List.of(
                PHONE_FILE_PATH_WITH_CONSOLE,
                PHONE_FILE_PATH_WITH_GUI
        );

        for (final String phoneFilePath : phoneFiles) {
            final File file = new File(phoneFilePath);
            if (!file.exists()) {
                System.out.println(file.getAbsolutePath() + " 존재 X");
            } else {
                System.out.println(file.getAbsolutePath() + " 존재 O");
            }
        }

        final List<String> lastIdFile = List.of(
                LAST_ID_FILE_PATH_WITH_CONSOLE,
                LAST_ID_FILE_PATH_WITH_GUI
        );

        for (final String lastIdFilePath : lastIdFile) {
            final File file = new File(lastIdFilePath);
            if (!file.exists()) {
                System.out.println(file.getAbsolutePath() + " 존재 X");
            } else {
                System.out.println(file.getAbsolutePath() + " 존재 O");
            }
        }
    }

    public List<Contact> read(final FileInteractType type) {
        final List<Contact> list = new ArrayList<>();

        try (final BufferedReader phoneReader = new BufferedReader(new InputStreamReader(new FileInputStream(getPhoneFilePath(type))))) {
            String line;
            while ((line = phoneReader.readLine()) != null) {
                final String[] token = line.split(Contact.FORM_DELIMITER);
                list.add(new Contact(
                        Long.valueOf(token[0]),
                        token[1],
                        Integer.parseInt(token[2]),
                        token[3],
                        LocalDateTime.parse(token[4])
                ));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    /**
     * lastId.txt에는 반드시 text 존재 <br>
     * -> 초기값 = 0
     */
    public long getLastId(final FileInteractType type) {
        try (final BufferedReader idReader = new BufferedReader(new InputStreamReader(new FileInputStream(getLastIdPath(type))))) {
            return Long.parseLong(idReader.readLine());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLastId(final FileInteractType type, final Long id) {
        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getLastIdPath(type), false)))) {
            bw.write(id + "\n");
            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeWithKeepAdd(final FileInteractType type, final Contact contact) {
        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getPhoneFilePath(type), true)))) {
            final String text = contact.toForm();
            bw.write(text + "\n");
            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeWithInit(final FileInteractType type, final List<Contact> contacts) {
        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getPhoneFilePath(type), false)))) {
            for (final Contact contact : contacts) {
                bw.write(contact.toForm() + "\n");
            }
            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPhoneFilePath(final FileInteractType type) {
        if (type == FileInteractType.CONSOLE) {
            return PHONE_FILE_PATH_WITH_CONSOLE;
        }
        return PHONE_FILE_PATH_WITH_GUI;
    }

    private String getLastIdPath(final FileInteractType type) {
        if (type == FileInteractType.CONSOLE) {
            return LAST_ID_FILE_PATH_WITH_CONSOLE;
        }
        return LAST_ID_FILE_PATH_WITH_GUI;
    }
}
