CREATE TABLE IF NOT EXISTS income (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date date,
    amount double,
    type varchar(20) CHECK (type IN ('CASH', 'CARD')),
    description varchar(255),
    category varchar(20) CHECK (category IN ('PAYCHECK', 'BONUS', 'GIFT', 'SELLING', 'OTHER'))
);

CREATE TABLE IF NOT EXISTS expense (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date date,
    amount double,
    type varchar(20) CHECK (type IN ('CASH', 'CARD')),
    description varchar(255),
    category varchar(20) CHECK (category IN ('GROCERIES', 'RESTAURANTS', 'TAKEAWAY', 'COFFEE', 'CATERING', 'DROGERIE', 'MEDICAL', 'CLOTHES', 'APARTMENT', 'TRANSPORT', 'SUBSCRIPTIONS', 'PLEASURES', 'TOYS', 'TRAVELING', 'BILLS', 'OTHER', 'INVESTING'))
);
