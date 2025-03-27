-- Таблица с потребители
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     username VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) NOT NULL,
                                     password VARCHAR(255) NOT NULL
);

-- Таблица с портфейли (wallets)
CREATE TABLE IF NOT EXISTS wallets (
                                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       user_id BIGINT NOT NULL,
                                       currency VARCHAR(10) NOT NULL,
                                       balance DOUBLE NOT NULL,
                                       CONSTRAINT fk_wallet_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Таблица със сделки (trades)
CREATE TABLE IF NOT EXISTS trades (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      user_id BIGINT NOT NULL,
                                      crypto VARCHAR(10) NOT NULL,
                                      quantity DOUBLE NOT NULL,
                                      price DOUBLE NOT NULL,
                                      is_buy BOOLEAN NOT NULL,
                                      profit_loss DOUBLE,
                                      timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      CONSTRAINT fk_trade_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
