package com.crypto.trading.repository;

import com.crypto.trading.model.User;
import com.crypto.trading.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser_Id(Long userId);
    Optional<Wallet> findByUser_IdAndCurrency(Long userId, String currency);
    void deleteAllByUser_Id(Long userId);

}
