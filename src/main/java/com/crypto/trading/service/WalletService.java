package com.crypto.trading.service;

import com.crypto.trading.model.User;
import com.crypto.trading.model.Wallet;
import com.crypto.trading.repository.UserRepository;
import com.crypto.trading.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    public void resetWallets(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Изтриване на всички портфейли
        walletRepository.deleteAllByUser_Id(userId);

        // Създаване на нов USD портфейл с $10 000
        Wallet usdWallet = new Wallet();
        usdWallet.setUser(user);
        usdWallet.setCurrency("USD");
        usdWallet.setBalance(10000.0);
        walletRepository.save(usdWallet);
    }

    @Autowired
    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public Optional<Wallet> getWalletByUserId(Long userId) {
        return walletRepository.findByUser_Id(userId);
    }

    public Optional<Wallet> getWalletByUserIdAndCurrency(Long userId, String currency) {
        return walletRepository.findByUser_IdAndCurrency(userId, currency);
    }

    public Wallet createWallet(Long userId, String currency) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setCurrency(currency);
        wallet.setBalance(0.0);
        return walletRepository.save(wallet);
    }

    public Wallet saveWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public void resetWallet(Long userId) {
        walletRepository.findByUser_Id(userId).ifPresent(wallet -> {
            wallet.setBalance(10000.0);
            walletRepository.save(wallet);
        });
    }
}
