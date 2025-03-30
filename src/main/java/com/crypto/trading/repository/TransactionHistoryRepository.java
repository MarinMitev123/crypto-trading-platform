package com.crypto.trading.repository;

import com.crypto.trading.model.TransactionHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@RequiredArgsConstructor
@Repository
public class TransactionHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<TransactionHistory> getTransactionHistory(Long userId) {
        String sql = "SELECT * FROM transaction_history WHERE user_id = ?";

        // Изпълняваме SQL заявката и връщаме резултатите като списък от обекти TransactionHistory
        return jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(TransactionHistory.class));
    }
    public List<TransactionHistory> findAll() {
        String sql = "SELECT * FROM transaction_history";
        return jdbcTemplate.query(sql, new TransactionHistoryRowMapper());
    }



    public void save(TransactionHistory transactionHistory) {
        String sql = "INSERT INTO transaction_history (user_id, crypto_pair, transaction_type, amount, price, total, profit_loss, timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                transactionHistory.getUserId(),
                transactionHistory.getCryptoPair(),
                transactionHistory.getTransactionType(),
                transactionHistory.getAmount(),
                transactionHistory.getPrice(),
                transactionHistory.getTotal(),
                transactionHistory.getProfitLoss(),
                transactionHistory.getTimestamp());
    }

    public List<TransactionHistory> findByUserId(Long userId) {
        String sql = "SELECT * FROM transaction_history WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new TransactionHistoryRowMapper());
    }

    public static class TransactionHistoryRowMapper implements RowMapper<TransactionHistory> {
        @Override
        public TransactionHistory mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            return new TransactionHistory(
                    rs.getLong("user_id"),
                    rs.getString("crypto_pair"),
                    rs.getString("transaction_type"),
                    rs.getDouble("amount"),
                    rs.getDouble("price"),
                    rs.getDouble("total"),
                    rs.getDouble("profit_loss")
            );
        }
    }
}
