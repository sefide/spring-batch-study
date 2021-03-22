CREATE TABLE sales(
    id         BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_no   VARCHAR(255),
    amount     BIGINT NOT NULL,
    order_date DATE,
    constraint SALES_INST_UN unique (order_no)
) ENGINE = InnoDB;

SELECT *
FROM sales;

DROP TABLE sales;
