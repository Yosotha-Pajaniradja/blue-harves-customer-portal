CREATE SCHEMA IF NOT EXISTS CUSTOMER_KYC;
SET SCHEMA CUSTOMER_KYC;
DROP TABLE IF EXISTS CUSTOMER_KYC.CUSTOMER_INFO;
CREATE TABLE CUSTOMER_KYC.CUSTOMER_INFO
(
    id_customer         INT AUTO_INCREMENT,
    customer_identifier VARCHAR(50) PRIMARY KEY,
    first_name          VARCHAR(75) NOT NULL,
    last_name           VARCHAR(75) NOT NULL,
    no_of_valid_account INT         NOT NULL,
    country             VARCHAR(50),
    tax_profile         VARCHAR(12),
    operating_currency  VARCHAR(3),
    creation_date       TIMESTAMP,
    update_date         TIMESTAMP
);
COMMIT;

INSERT INTO TESTDB.CUSTOMER_KYC.CUSTOMER_INFO (FIRST_NAME, LAST_NAME, CUSTOMER_IDENTIFIER,
                                               NO_OF_VALID_ACCOUNT,
                                               OPERATING_CURRENCY, TAX_PROFILE, COUNTRY, CREATION_DATE, UPDATE_DATE)
VALUES ('John', 'Frank', '3456 3232 5677 3345', 1, 'EUR', 'RETAIL', 'FRANCE', now(), now());

INSERT INTO CUSTOMER_KYC.CUSTOMER_INFO (FIRST_NAME, LAST_NAME, CUSTOMER_IDENTIFIER,
                                        NO_OF_VALID_ACCOUNT,
                                        OPERATING_CURRENCY, TAX_PROFILE, COUNTRY, CREATION_DATE, UPDATE_DATE)
VALUES ('Frank', 'POUPLIN','4655 2343 6888 3344', 1, 'EUR', 'RETAIL', 'FRANCE', now(), now());

COMMIT;

