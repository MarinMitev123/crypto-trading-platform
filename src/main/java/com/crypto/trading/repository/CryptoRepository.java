package com.crypto.trading.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CryptoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CryptoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getAllCryptoPairs() {
        String sql = "SELECT DISTINCT crypto_pair FROM crypto_prices";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
