CREATE TABLE pay (
    id         bigint not null auto_increment,
    amount     bigint,
    tx_name     varchar(255),
    tx_date_time datetime,
    primary key (id)
) engine = InnoDB;

INSERT INTO pay (amount, tx_name, tx_date_time) VALUES (1000, 'trade1', '2021-03-17 00:00:00');
INSERT INTO pay (amount, tx_name, tx_date_time) VALUES (2000, 'trade2', '2021-03-17 00:00:00');
INSERT INTO pay (amount, tx_name, tx_date_time) VALUES (3000, 'trade3', '2021-03-17 00:00:00');
INSERT INTO pay (amount, tx_name, tx_date_time) VALUES (4000, 'trade4', '2021-03-17 00:00:00');

SELECT id, amount, tx_name, tx_date_time FROM pay;