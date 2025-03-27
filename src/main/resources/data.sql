-- Премахваме всички записи от таблицата users, за да избегнем конфликт на id
DELETE FROM users;

-- Добавяме потребител с уникално id (поставяме само username и email)
INSERT INTO users (username, email) VALUES ('testuser', 'test@crypto.com');

-- Добавяме портфейл с USD за същия потребител
INSERT INTO wallets (user_id, currency, balance) VALUES (LAST_INSERT_ID(), 'USD', 10000.00);

-- Примерна сделка за този потребител
INSERT INTO trades (user_id, crypto, quantity, price, is_buy, profit_loss)
VALUES (LAST_INSERT_ID(), 'BTC', 0.1, 30000.00, true, NULL);
