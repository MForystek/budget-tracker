CREATE TABLE IF NOT EXISTS currency (
    code CHAR(3) NOT NULL UNIQUE,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS category (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    type VARCHAR(7) CHECK (type IN ('INCOME', 'EXPENSE')) NOT NULL,
    CONSTRAINT uq_category UNIQUE (name, type)
);

CREATE TABLE IF NOT EXISTS transaction (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency_code CHAR(3) NOT NULL,
    payment_method CHAR(4) CHECK (payment_method IN ('CASH', 'CARD')) NOT NULL,
    description VARCHAR(255) NOT NULL,
    category_id INT,
    CONSTRAINT fk_currency FOREIGN KEY (currency_code) REFERENCES currency(code),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id)
);