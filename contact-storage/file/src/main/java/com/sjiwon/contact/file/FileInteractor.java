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
import java.util.List;

public class FileInteractor {
    private static final String PROJECT_PATH = System.getProperty("user.dir") + "\\contact-storage\\file\\src\\main\\resources";
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
        final FileInteractor instance = SingletonHolder.INSTANCE;
        instance.initialize();
        return instance;
    }

    /**
     * 파일 없으면 세팅
     */
    private void initialize() {
        final List<String> phoneFiles = List.of(
                PHONE_FILE_PATH_WITH_CONSOLE,
                PHONE_FILE_PATH_WITH_GUI
        );

        for (final String phoneFilePath : phoneFiles) {
            final File file = new File(phoneFilePath);
            if (!file.exists()) {
                try {
                    if (!file.createNewFile()) {
                        throw new RuntimeException("file is not created...");
                    }
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        final List<String> lastIdFile = List.of(
                LAST_ID_FILE_PATH_WITH_CONSOLE,
                LAST_ID_FILE_PATH_WITH_GUI
        );

        for (final String lastIdFilePath : lastIdFile) {
            final File file = new File(lastIdFilePath);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)))) {
                            bw.write("0");
                            bw.flush();
                        } catch (final IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public List<Contact> read(final FileInteractType type) {
        try (final BufferedReader phoneReader = new BufferedReader(new InputStreamReader(new FileInputStream(getPhoneFilePath(type))))) {
            return phoneReader.lines()
                    .map(line -> line.split(Contact.FORM_DELIMITER))
                    .map(token -> new Contact(
                            Long.valueOf(token[0]),
                            token[1],
                            Integer.parseInt(token[2]),
                            token[3],
                            LocalDateTime.parse(token[4])
                    )).toList();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
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
            bw.write(String.valueOf(id));
            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeWithKeepAdd(final FileInteractType type, final Contact contact) {
        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getPhoneFilePath(type), true)))) {
            bw.write(contact.toForm() + "\n");
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
