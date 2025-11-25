INSERT INTO transaction (date, amount, currency_code, payment_method, description, category_id) values
(
    '2000-01-01',
    '10.0',
    'PLN',
    'CARD',
    'Income test 1',
    (SELECT id FROM category WHERE name='PAYCHECK')
),
(
    '2000-01-02',
    '20.0',
    'NOK',
    'CASH',
    'Income test 2',
    (SELECT id FROM category WHERE name='BONUS')
);

INSERT INTO transaction (date, amount, currency_code, payment_method, description, category_id) values
(
    '2000-01-03',
    '5.0',
    'EUR',
    'CARD',
    'Expense test 1',
    (SELECT id FROM category WHERE name='GROCERIES')
),
(
    '2000-01-04',
    '10.0',
    'USD',
    'CASH',
    'Expense test 2',
    (SELECT id FROM category WHERE name='OTHER' AND type='EXPENSE')
);