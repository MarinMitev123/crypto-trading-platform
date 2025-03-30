package com.crypto.trading.service;

import com.crypto.trading.model.User;
import com.crypto.trading.model.Wallet;
import com.crypto.trading.repository.UserRepository;
import com.crypto.trading.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final KrakenWebSocketClient krakenWebSocketClient;

    @Autowired
    public UserService(WalletRepository walletRepository, KrakenWebSocketClient krakenWebSocketClient, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.krakenWebSocketClient = krakenWebSocketClient;
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Потребител не намерен"));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public double getBalance(Long userId) {
        return userRepository.getBalance(userId);
    }

    public void decreaseBalance(Long userId, double amount) {
        userRepository.decreaseBalance(userId, amount);
    }

    public void increaseBalance(Long userId, double amount) {
        userRepository.increaseBalance(userId, amount);
    }

    public double getTotalBalanceInUsd(Long userId) {
        List<Wallet> wallets = walletRepository.findByUserId(userId); // Вземаме всички портфейли на потребителя
        double totalInUsd = 0.0;

        for (Wallet wallet : wallets) {
            String currency = wallet.getCurrency();
            double balance = wallet.getBalance();

            // Ако е USD – няма нужда от конвертиране
            if (currency.equalsIgnoreCase("USD")) {
                totalInUsd += balance;
            } else {
                // Ако не е USD, търсим цената от Kraken
                String pair = currency + "/USD"; // Пример: BTC/USD
                double price = krakenWebSocketClient.getCryptoPrice(pair);

                // Ако няма цена, пропускаме валутата
                if (price == 0.0) {
                    continue;
                }

                totalInUsd += balance * price; // Добавяме баланса, умножен по цената в USD
            }
        }

        return totalInUsd;
    }

}
