package com.sjiwon.contact;

import com.sjiwon.contact.domain.Contact;
import com.sjiwon.contact.domain.ContactValidator;
import com.sjiwon.contact.domain.SearchType;
import com.sjiwon.contact.exception.DuplicateResourceException;
import com.sjiwon.contact.exception.InvalidAgeException;
import com.sjiwon.contact.exception.InvalidDeleteOperationException;
import com.sjiwon.contact.exception.InvalidMenuException;
import com.sjiwon.contact.exception.InvalidNameException;
import com.sjiwon.contact.exception.InvalidPhoneException;
import com.sjiwon.contact.file.FileInteractType;
import com.sjiwon.contact.file.FileInteractor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleFileProcessor {
    private static final Scanner sc = new Scanner(System.in);
    private final FileInteractor fileInteractor = FileInteractor.getInstance();
    private List<Contact> list = new ArrayList<>();

    public void run() {
        init();

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
                System.out.println(e.getMessage());
            }
        }
    }

    private void init() {
        list = fileInteractor.read(FileInteractType.CONSOLE);
    }

    private void show() {
        System.out.println(organizeContact(list));
    }

    private String organizeContact(final List<Contact> contacts) {
        if (contacts.isEmpty()) {
            return "검색 결과가 존재하지 않습니다.\n";
        }

        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < contacts.size(); i++) {
            final Contact contact = contacts.get(i);
            result.append(String.format(
                    "%-5d:\t%-5s\t%-3d\t%-13s\t%-20s\n",
                    (i + 1),
                    contact.name(),
                    contact.age(),
                    contact.phone(),
                    contact.organizeCreatedAt()
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

        final long lastId = fileInteractor.getLastId(FileInteractType.CONSOLE);
        final Contact contact = new Contact(
                lastId + 1,
                name,
                Integer.parseInt(age),
                phone,
                LocalDateTime.now()
        );
        register(contact, lastId);

        printNewLine();
    }

    private String readName() {
        while (true) {
            try {
                System.out.print("이름을 입력하세요 (exit=종료) >> ");
                final String name = sc.nextLine();

                if (isExit(name)) {
                    return null;
                }

                if (!ContactValidator.isValidNamePattern(name)) {
                    throw new InvalidNameException("이름 형식이 잘못되었습니다.");
                }

                return name;
            } catch (final InvalidNameException e) {
                System.out.println(e.getMessage());
            }
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

                if (!ContactValidator.isValidAge(age)) {
                    throw new InvalidAgeException("나이는 0보다 커야합니다.");
                }

                return age;
            } catch (final InvalidAgeException e) {
                System.out.println(e.getMessage());
            } catch (final NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
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

                if (!ContactValidator.isValidPhonePattern(phone)) {
                    throw new InvalidPhoneException("전화번호는 xxx-xxx-xxxx 또는 xxx-xxxx-xxxx로 입력해주세요.");
                }

                validatePhoneIsExists(phone);
                return phone;
            } catch (final InvalidPhoneException | DuplicateResourceException e) {
                System.out.println(e.getMessage());
            }
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

    private void register(final Contact contact, final long lastId) {
        fileInteractor.writeWithKeepAdd(FileInteractType.CONSOLE, contact);
        fileInteractor.updateLastId(FileInteractType.CONSOLE, lastId);
        init();
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
                        proceedSearch(SearchType.MIDDLE, number);
                    }
                    case 2 -> {
                        System.out.print("번호를 입력해주세요 >> ");
                        final String number = sc.nextLine();
                        proceedSearch(SearchType.END, number);
                    }
                    case 3 -> {
                        printNewLine();
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

    private void proceedSearch(final SearchType type, final String value) {
        if (invalidSearchLength(value)) {
            throw new IllegalArgumentException("검색 번호는 3자리 또는 4자리로 입력해주세요.");
        }

        final List<Contact> contacts = list.stream()
                .filter(contact -> type == SearchType.MIDDLE ? contact.getMediumPartOfPhone().contains(value) : contact.getLastPartOfPhone().contains(value))
                .toList();
        System.out.println(organizeContact(contacts));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
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

                final int row = Integer.parseInt(command);
                if (row == 0) {
                    throw new InvalidDeleteOperationException("행은 1부터 입력해주세요.");
                }
                if (list.size() < row) {
                    throw new InvalidDeleteOperationException("행에 해당하는 레코드가 존재하지 않습니다.");
                }

                list.remove(row - 1);
                fileInteractor.writeWithInit(FileInteractType.CONSOLE, list);

                System.out.printf("%d 행이 삭제되었습니다.\n\n", row);
                break;
            } catch (final InvalidDeleteOperationException e) {
                System.out.println(e.getMessage());
            } catch (final NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    private void printNewLine() {
        System.out.println();
    }
}
