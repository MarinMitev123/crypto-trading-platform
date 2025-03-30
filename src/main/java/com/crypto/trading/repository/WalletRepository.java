package com.crypto.trading.repository;

import com.crypto.trading.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class WalletRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Извлича портфейл на потребител по userId
    public List<Wallet> findByUserId(Long userId) {
        String sql = "SELECT * FROM wallets WHERE user_id = ?";
        List<Wallet> wallets = jdbcTemplate.query(sql, new Object[]{userId}, new WalletRowMapper());
//        return jdbcTemplate.queryForList(sql, new Object[]{userId}, (rs, rowNum) -> {
//            List<Wallet> wallets = new ArrayList<>();
//            Wallet wallet = new Wallet();
//            wallet.setId(rs.getLong("id"));
//            wallet.setCurrency(rs.getString("currency"));
//            wallet.setBalance(rs.getDouble("balance"));
//            wallet.setUserId(rs.getLong("user_id"));
//            return wallet;
//        });
        return wallets;
    }

    // Извлича портфейл на потребител по userId и валута
//    public Wallet findByUserIdAndCurrency(Long userId, String currency) {
//        String sql = "SELECT * FROM wallets WHERE user_id = ? AND currency = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{userId, currency}, (rs, rowNum) -> {
//            Wallet wallet = new Wallet();
//            wallet.setId(rs.getLong("id"));
//            wallet.setCurrency(rs.getString("currency"));
//            wallet.setBalance(rs.getDouble("balance"));
//            wallet.setUserId(rs.getLong("user_id"));
//            return wallet;
//        });
//    }
    public Optional<Wallet> findByUserIdAndCurrency(Long userId, String currency) {
        String sql = "SELECT * FROM wallets WHERE user_id = ? AND currency = ?";
        return jdbcTemplate.query(sql, new Object[]{userId, currency}, new WalletRowMapper())
                .stream().findFirst(); // Ако има, вземи първия; иначе -> empty
    }




    // Метод за изтриване на всички портфейли на даден потребител
    public void deleteWalletById(Long walletId) {
        String sql = "DELETE FROM wallets WHERE id = ?";
        jdbcTemplate.update(sql, walletId);
    }


    // Метод за записване на нов портфейл
    public Wallet save(Wallet wallet) {
        String sql = "INSERT INTO wallets (user_id, currency, balance) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, wallet.getUserId(), wallet.getCurrency(), wallet.getBalance());
        return wallet;
    }

    public Wallet update(Wallet wallet) {
        String sql = "UPDATE wallets SET balance = ? WHERE id = ?";
        jdbcTemplate.update(sql, wallet.getBalance(), wallet.getId());
        return wallet;
    }


}
