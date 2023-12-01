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
