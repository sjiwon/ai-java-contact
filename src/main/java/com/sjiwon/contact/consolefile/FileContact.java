package com.sjiwon.contact.consolefile;

import com.sjiwon.contact.consolefile.exception.DuplicateResourceException;
import com.sjiwon.contact.consolefile.exception.InvalidAgeException;
import com.sjiwon.contact.consolefile.exception.InvalidDeleteOperationException;
import com.sjiwon.contact.consolefile.exception.InvalidMenuException;
import com.sjiwon.contact.consolefile.exception.InvalidNameException;
import com.sjiwon.contact.consolefile.exception.InvalidPhoneException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.sjiwon.contact.common.Constants.DELIMITER;
import static com.sjiwon.contact.common.Constants.FILE_PATH;
import static com.sjiwon.contact.common.Constants.NAME_PATTERN;
import static com.sjiwon.contact.common.Constants.PHONE_PATTERN;

public class FileContact {
    private static final Scanner sc = new Scanner(System.in);

    record Contact(
            String name,
            int age,
            String phone
    ) {
    }

    private final List<Contact> list = new ArrayList<>();

    private void run() {
        init();

        System.out.println("### 친구 연락처 관리 ###");
        while (true) {
            try {
                System.out.println("====================================");
                System.out.println("1. 연락처 출력");
                System.out.println("2. 연락처 등록");
                System.out.println("3. 연락처 삭제");
                System.out.println("4. 끝내기");
                System.out.println("====================================\n");

                System.out.print("메뉴를 입력해주세요 >> ");
                final int menu = Integer.parseInt(sc.nextLine());

                switch (menu) {
                    case 1 -> show();
                    case 2 -> add();
                    case 3 -> delete();
                    case 4 -> {
                        System.out.println("연락처 관리 프로그램을 종료하겠습니다...");
                        System.exit(0);
                    }
                    default -> throw new InvalidMenuException();
                }
            } catch (final InvalidMenuException e) {
                System.out.println("잘못된 메뉴를 선택하였습니다.\n다시 입력해주세요\n");
            }
        }
    }

    private void init() {
        list.clear();

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH)))) {
            String line;

            while ((line = br.readLine()) != null) {
                final String[] token = line.split(DELIMITER);
                list.add(new Contact(
                        token[0],
                        Integer.parseInt(token[1]),
                        token[2]
                ));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void show() {
        for (int i = 0; i < list.size(); i++) {
            final Contact contact = list.get(i);
            System.out.printf(
                    "%d:\t%s\t%d\t%s\n",
                    (i + 1),
                    contact.name(),
                    contact.age(),
                    contact.phone()
            );
        }
        System.out.println();
    }

    private void add() {
        final String name = readName();
        if (name == null) {
            return;
        }

        final String age = readAge();
        if (age == null) {
            return;
        }

        final String phone = readPhone();
        if (phone == null) {
            return;
        }

        final Contact contact = new Contact(name, Integer.parseInt(age), phone);
        register(contact);

        System.out.println();
    }

    private String readName() {
        while (true) {
            try {
                System.out.print("이름을 입력하세요 (exit 입력 시 메뉴 종료) >> ");
                final String name = sc.nextLine();

                if (isExit(name)) {
                    return null;
                }

                validateNamePattern(name);
                return name;
            } catch (final InvalidNameException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateNamePattern(final String value) {
        if (!NAME_PATTERN.matcher(value).matches()) {
            throw new InvalidNameException("이름 형식이 잘못되었습니다.");
        }
    }

    private String readAge() {
        while (true) {
            try {
                System.out.print("나이를 입력하세요 (exit 입력 시 메뉴 종료) >> ");
                final String age = sc.nextLine();

                if (isExit(age)) {
                    return null;
                }

                validateAge(age);
                return age;
            } catch (final InvalidAgeException e) {
                System.out.println(e.getMessage());
            } catch (final NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    private void validateAge(final String value) {
        final int age = Integer.parseInt(value);

        if (age <= 0) {
            throw new InvalidAgeException("나이는 0보다 커야합니다.");
        }
    }

    private String readPhone() {
        while (true) {
            try {
                System.out.print("전화번호를 입력하세요 (exit 입력 시 메뉴 종료) >> ");
                final String phone = sc.nextLine();

                if (isExit(phone)) {
                    return null;
                }

                validatePhonePattern(phone);
                validatePhoneIsExists(phone);
                return phone;
            } catch (final InvalidPhoneException | DuplicateResourceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validatePhonePattern(final String value) {
        if (!PHONE_PATTERN.matcher(value).matches()) {
            throw new InvalidPhoneException("전화번호는 xxx-xxx-xxxx 또는 xxx-xxxx-xxxx로 입력해주세요.");
        }
    }

    private void validatePhoneIsExists(final String value) {
        final List<String> filterWithName = list.stream()
                .map(Contact::phone)
                .toList();

        if (filterWithName.contains(value)) {
            throw new DuplicateResourceException("연락처에 이미 존재하는 번호입니다.");
        }
    }

    private boolean isExit(final String value) {
        return "exit".equalsIgnoreCase(value);
    }

    private void register(final Contact contact) {
        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, true)))) {
            final String text = contact.name() + DELIMITER + contact.age() + DELIMITER + contact.phone();
            bw.write(text + "\n");
            bw.flush();

            init();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete() {
        while (true) {
            try {
                System.out.print("삭제할 행의 번호는? >> ");
                final int row = Integer.parseInt(sc.nextLine());

                if (list.size() < row) {
                    throw new InvalidDeleteOperationException();
                }

                list.remove(row - 1);
                reboot();

                System.out.printf("%d 행이 삭제되었습니다.\n\n", row);
                break;
            } catch (final InvalidDeleteOperationException e) {
                System.out.println("행에 해당하는 레코드가 존재하지 않습니다.");
            } catch (final NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    private void reboot() {
        try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, false)))) {
            for (final Contact contact : list) {
                final String text = contact.name() + DELIMITER + contact.age() + DELIMITER + contact.phone();
                bw.write(text + "\n");
            }

            bw.flush();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(final String[] args) {
        new FileContact().run();
    }
}
