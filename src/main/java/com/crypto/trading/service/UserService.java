package com.crypto.trading.service;

import com.crypto.trading.model.User;
import com.crypto.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public double getBalance(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(User::getBalance).orElse(0.0); // Връща баланса на потребителя или 0, ако потребителят не е намерен
    }

    public void decreaseBalance(Long userId, double amount) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getBalance() >= amount) {
                user.setBalance(user.getBalance() - amount);
                userRepository.save(user); // Записваме новия баланс в базата
            } else {
                throw new IllegalArgumentException("Няма достатъчно средства");
            }
        } else {
            throw new IllegalArgumentException("Потребителят не е намерен");
        }
    }

    public void increaseBalance(Long userId, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }

    public void addCryptoToPortfolio(Long userId, String cryptoPair, double amount) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, Double> portfolio = user.getPortfolio();
            portfolio.put(cryptoPair, portfolio.getOrDefault(cryptoPair, 0.0) + amount);
            user.setPortfolio(portfolio);
            userRepository.save(user); // Записваме обновеното портфолио в базата
        } else {
            throw new IllegalArgumentException("Потребителят не е намерен");
        }
    }

    public void removeCryptoFromPortfolio(Long userId, String cryptoPair, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getPortfolio().containsKey(cryptoPair)) {
            double currentAmount = user.getPortfolio().get(cryptoPair);
            if (currentAmount >= amount) {
                user.getPortfolio().put(cryptoPair, currentAmount - amount);
            } else {
                throw new IllegalArgumentException("Not enough crypto to remove");
            }
        }
        userRepository.save(user);
    }

    public double getCryptoAmount(Long userId, String cryptoPair) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getPortfolio().getOrDefault(cryptoPair, 0.0);
    }
}
