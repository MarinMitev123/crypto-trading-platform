package com.crypto.trading.controller;

import com.crypto.trading.model.User;
import com.crypto.trading.model.Wallet;
import com.crypto.trading.service.UserService;
import com.crypto.trading.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final WalletService walletService;


    @PostMapping("/buyCrypto")
    public List<Wallet> buyCrypto(@RequestParam Long userId, @RequestParam String cryptoPair, @RequestParam double amount) {
//        User user = userService.getUserById(userId);
//        user.addCryptoToPortfolio(cryptoPair, amount);
//        userService.saveUser(user);
        walletService.buyCrypto(userId, cryptoPair, amount);
        return walletService.getWalletByUserId(userId);
    }

    @PostMapping("/sellCrypto")
    public List<Wallet> sellCrypto(@RequestParam Long userId, @RequestParam String cryptoPair, @RequestParam double amount) {
        walletService.saleCrypto(userId, cryptoPair, amount);
        return walletService.getWalletByUserId(userId);

    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.saveUser(user);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/balance/{userId}")
    public double getBalance(@PathVariable Long userId) {
        return userService.getBalance(userId);
    }
    @GetMapping("/total-balance/{userId}")
    public double getTotalBalance(@PathVariable Long userId) {
        return userService.getTotalBalanceInUsd(userId);
    }


    @PostMapping("/decreaseBalance")
    public void decreaseBalance(@RequestParam Long userId, @RequestParam double amount) {
        userService.decreaseBalance(userId, amount);
    }

    @PostMapping("/increaseBalance")
    public void increaseBalance(@RequestParam Long userId, @RequestParam double amount) {
        userService.increaseBalance(userId, amount);
    }
    @PostMapping("/reset-account/{userId}")
    public ResponseEntity<String> resetAccount(@PathVariable Long userId) {
        try {
            walletService.resetWallets(userId);
            return ResponseEntity.ok("✅ Акаунтът беше успешно нулиран.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("⚠️ Възникна грешка при нулиране на акаунта.");
        }
    }
}
