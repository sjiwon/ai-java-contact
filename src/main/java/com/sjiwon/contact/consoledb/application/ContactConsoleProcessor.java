package com.sjiwon.contact.consoledb.application;

import com.sjiwon.contact.consolefile.exception.DuplicateResourceException;
import com.sjiwon.contact.consolefile.exception.InvalidAgeException;
import com.sjiwon.contact.consolefile.exception.InvalidDeleteOperationException;
import com.sjiwon.contact.consolefile.exception.InvalidMenuException;
import com.sjiwon.contact.consolefile.exception.InvalidNameException;
import com.sjiwon.contact.consolefile.exception.InvalidPhoneException;
import com.sjiwon.contact.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

import static com.sjiwon.contact.common.Constants.DATE_FORMAT;
import static com.sjiwon.contact.common.Constants.NAME_PATTERN;
import static com.sjiwon.contact.common.Constants.PHONE_PATTERN;

@Component
@RequiredArgsConstructor
public class ContactConsoleProcessor {
    private static final Scanner sc = new Scanner(System.in);

    private final ContactWriter contactWriter;
    private final ContactReader contactReader;

    public void invoke() {
        System.out.println("### 친구 연락처 관리 ###");
        while (true) {
            try {
                System.out.println("====================================");
                System.out.println("1. 연락처 출력");
                System.out.println("2. 연락처 등록");
                System.out.println("3. 연락처 검색");
                System.out.println("4. 연락처 삭제");
                System.out.println("5. 끝내기");
                System.out.println("====================================\n");

                System.out.print("메뉴를 입력해주세요 >> ");
                final int menu = Integer.parseInt(sc.nextLine());

                switch (menu) {
                    case 1 -> show();
                    case 2 -> add();
                    case 3 -> search();
                    case 4 -> delete();
                    case 5 -> {
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

    private void show() {
        final List<Contact> contacts = contactReader.findAll();
        System.out.println(generate(contacts));
    }

    private String generate(final List<Contact> contacts) {
        if (contacts.isEmpty()) {
            return "검색 결과가 존재하지 않습니다.";
        }

        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < contacts.size(); i++) {
            final Contact contact = contacts.get(i);
            result.append(String.format(
                    "%d:\t%s\t%d\t%s\t%s\n",
                    (i + 1),
                    contact.name(),
                    contact.age(),
                    contact.phone(),
                    contact.createdAt().format(DATE_FORMAT)
            ));
        }

        return result.toString();
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
                System.out.print("이름을 입력하세요 (exit=종료) >> ");
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
                System.out.print("나이를 입력하세요 (exit=종료) >> ");
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
                System.out.print("전화번호를 입력하세요 (exit=종료) >> ");
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
        if (contactReader.isPhoneUsedByOther(value)) {
            throw new DuplicateResourceException("연락처에 이미 존재하는 번호입니다.");
        }
    }

    private boolean isExit(final String value) {
        return "exit".equalsIgnoreCase(value);
    }

    private void register(final Contact contact) {
        contactWriter.create(contact);
    }

    private void search() {
        System.out.println("연락처를 검색해보세요.");

        while (true) {
            try {
                System.out.print("가운데 번호 검색(1), 끝자리 번호 검색(2), 종료(3) >> ");
                final int select = Integer.parseInt(sc.nextLine());

                switch (select) {
                    case 1 -> {
                        System.out.print("번호를 입력해주세요 >> ");
                        final String number = sc.nextLine();

                        if (invalidSearchLength(number)) {
                            throw new IllegalArgumentException("검색 번호는 3자리 또는 4자리로 입력해주세요.");
                        }

                        final List<Contact> contacts = contactReader.findAll()
                                .stream()
                                .filter(contact -> contact.getMediumPartOfPhone().contains(number))
                                .toList();
                        System.out.println(generate(contacts));
                    }
                    case 2 -> {
                        System.out.print("번호를 입력해주세요 >> ");
                        final String number = sc.nextLine();

                        final List<Contact> contacts = contactReader.findAll()
                                .stream()
                                .filter(contact -> contact.getLastPartOfPhone().contains(number))
                                .toList();
                        System.out.println(generate(contacts));
                    }
                    case 3 -> {
                        return;
                    }
                    default -> throw new IllegalArgumentException("[1, 2, 3]중 하나를 입력해주세요.");
                }

            } catch (final NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            } catch (final IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean invalidSearchLength(final String number) {
        Integer.parseInt(number); // number check

        final int length = number.length();
        return length < 3 || length > 4;
    }

    private void delete() {
        while (true) {
            try {
                System.out.print("삭제할 행의 번호는? (exit=종료) >> ");
                final String command = sc.nextLine();
                if (isExit(command)) {
                    return;
                }

                final int row = Integer.parseInt(sc.nextLine());
                if (row == 0) {
                    throw new InvalidDeleteOperationException("행은 1부터 입력해주세요.");
                }

                final List<Contact> contacts = contactReader.findAll();
                if (contacts.size() < row) {
                    throw new InvalidDeleteOperationException("행에 해당하는 레코드가 존재하지 않습니다.");
                }

                contactWriter.delete(contacts.get(row - 1));
                System.out.printf("%d 행이 삭제되었습니다.\n\n", row);
                break;
            } catch (final InvalidDeleteOperationException e) {
                System.out.println(e.getMessage());
            } catch (final NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }
}
