CREATE TABLE IF NOT EXISTS contacts
(
    id         BIGINT AUTO_INCREMENT,
    name       VARCHAR(200) NOT NULL,
    age        INT          NOT NULL,
    phone      VARCHAR(30)  NOT NULL,
    created_at DATETIME     NOT NULL,

    PRIMARY KEY (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO contacts(name, age, phone, created_at)
VALUES ('가나다', 20, '010-1111-2222', '2023-11-27 13:48:23'),
       ('라라라', 25, '010-2222-3333', '2023-11-27 15:30:38'),
       ('홍길동', 30, '010-1111-4444', '2023-11-27 18:30:38')
