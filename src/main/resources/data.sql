-- Добавяме потребител с уникално id (поставяме само username и email)
INSERT IGNORE INTO users (id, username, email, password) VALUES (1, 'testuser', 'test@crypto.com', 'test');

-- -- Добавяме портфейл с USD за същия потребител
INSERT IGNORE INTO wallets (id, user_id, currency, balance) VALUES (1,1, 'USD', 10000.00);
INSERT IGNORE INTO wallets (id, user_id, currency, balance) VALUES (2,1, 'BTC', 10.00);

-- Примерна сделка за този потребител
-- INSERT INTO trades (user_id, crypto, quantity, price, is_buy, profit_loss)
-- VALUES (LAST_INSERT_ID(), 'BTC', 0.1, 30000.00, true, NULL);
