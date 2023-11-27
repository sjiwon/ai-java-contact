package com.sjiwon.contact.common;

import java.util.regex.Pattern;

public interface Constants {
    String PROJECT_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\com\\sjiwon\\contact";
    String FILE_PATH = PROJECT_PATH + "\\consolefile\\store\\phone.txt";

    /**
     * xxx-xxx-xxxx or xxx-xxxx-xxxx
     */
    Pattern PHONE_PATTERN = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
    Pattern NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z]*$");
}
