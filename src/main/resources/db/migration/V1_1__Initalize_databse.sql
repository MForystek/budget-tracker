CREATE TABLE IF NOT EXISTS category (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    type VARCHAR(20) CHECK (type IN ('INCOME', 'EXPENSE')) NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(20) CHECK (payment_method IN ('CASH', 'CARD')) NOT NULL,
    description VARCHAR(255),
    category_id INT,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id)
);