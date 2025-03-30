package com.crypto.trading.repository;

import com.crypto.trading.model.Wallet;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletRowMapper implements RowMapper<Wallet> {
    @Override
    public Wallet mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wallet wallet = new Wallet();
        wallet.setId(rs.getLong("id"));
        wallet.setCurrency(rs.getString("currency"));
        wallet.setBalance(rs.getDouble("balance"));
        wallet.setUserId(rs.getLong("user_id"));
        return wallet;
    }
}
