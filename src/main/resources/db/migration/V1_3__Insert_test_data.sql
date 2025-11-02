INSERT INTO transaction (date, amount, payment_method, description, category_id) values
(
    '1900-01-01',
    '10.0',
    'CARD',
    'Income test 1',
    (SELECT id FROM category WHERE name='PAYCHECK')
),
(
    '1900-01-02',
    '20.0',
    'CASH',
    'Income test 2',
    (SELECT id FROM category WHERE name='BONUS')
);

INSERT INTO transaction (date, amount, payment_method, description, category_id) values
(
    '1900-01-03',
    '5.0',
    'CARD',
    'Expense test 1',
    (SELECT id FROM category WHERE name='GROCERIES')
),
(
    '1900-01-04',
    '15.0',
    'CASH',
    'Expense test 2',
    (SELECT id FROM category WHERE name='OTHER_EXPENSE')
);