CREATE SCHEMA IF NOT EXISTS TRANSACTION;
SET SCHEMA TRANSACTION;
DROP TABLE IF EXISTS TRANSACTION.TRANSACTIONS_CLIENT;
CREATE TABLE TRANSACTION.TRANSACTIONS_CLIENT
(
    id_transaction              INT AUTO_INCREMENT,
    transaction_identifier      VARCHAR(50) PRIMARY KEY,
    account_number_source       VARCHAR(50) NOT NULL,
    account_number_target       VARCHAR(50) NOT NULL,
    customer_account_identifier VARCHAR(50) NOT NULL,
    transaction_amount          NUMERIC(28, 14),
    creation_date               TIMESTAMP,
    update_date                 TIMESTAMP
);
COMMIT;


