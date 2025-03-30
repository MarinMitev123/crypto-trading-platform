package com.crypto.trading.repository;

import com.crypto.trading.model.Trade;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Repository
public class TradeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TradeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Извлича всички сделки на потребителя
    public List<Trade> findByUserId(Long userId) {
        String sql = "SELECT * FROM trades WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            Trade trade = new Trade();
            trade.setId(rs.getLong("id"));
            trade.setUserId(rs.getLong("user_id"));
            trade.setCrypto(rs.getString("crypto"));
            trade.setQuantity(rs.getDouble("quantity"));
            trade.setPrice(rs.getDouble("price"));
            trade.setBuy(rs.getBoolean("is_buy"));
            trade.setProfitLoss(rs.getDouble("profit_loss"));
            return trade;
        });
    }

    // Записва търговия
    public void save(Trade trade) {
        String sql = "INSERT INTO trades (user_id, crypto, quantity, price, is_buy, profit_loss) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, trade.getUserId(), trade.getCrypto(), trade.getQuantity(), trade.getPrice(),
                trade.isBuy(), trade.getProfitLoss());
    }
}
