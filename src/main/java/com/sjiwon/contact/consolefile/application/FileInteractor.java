package com.sjiwon.contact.consolefile.application;

import com.sjiwon.contact.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileInteractor {
    private static final String PROJECT_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\com\\sjiwon\\contact";
    private static final String PHONE_FILE_PATH = PROJECT_PATH + "\\common\\file\\phone.txt";
    private static final String LAST_ID_FILE_PATH = PROJECT_PATH + "\\common\\file\\lastId.txt";

    public List<Contact> read() {
        final List<Contact> list = new ArrayList<>();

        try (final BufferedReader phoneReader = new BufferedReader(new InputStreamReader(new FileInputStream(PHONE_FILE_PATH)))) {
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
    public long getLastId() {
        try (final BufferedReader idReader = new BufferedReader(new InputStreamReader(new FileInputStream(LAST_ID_FILE_PATH)))) {
            return Long.parseLong(idReader.readLine());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLastId(final Long id) {
        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LAST_ID_FILE_PATH, false)))) {
            bw.write(id + "\n");
            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeWithKeepAdd(final Contact contact) {
        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PHONE_FILE_PATH, true)))) {
            final String text = contact.toForm();
            bw.write(text + "\n");
            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeWithInit(final List<Contact> contacts) {
        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PHONE_FILE_PATH, false)))) {
            for (final Contact contact : contacts) {
                bw.write(contact.toForm() + "\n");
            }
            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
