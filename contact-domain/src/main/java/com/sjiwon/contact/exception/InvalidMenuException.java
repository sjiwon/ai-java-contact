package com.sjiwon.contact.exception;

public class InvalidMenuException extends RuntimeException {
    public InvalidMenuException() {
        super("잘못된 메뉴를 선택하였습니다.\n다시 입력해주세요\n");
    }
}
