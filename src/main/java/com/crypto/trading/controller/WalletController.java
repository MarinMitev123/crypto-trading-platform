package com.crypto.trading.controller;

import com.crypto.trading.model.Wallet;
import com.crypto.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // Връща портфейла на потребителя по userId
    @GetMapping("/{userId}")
    public List<Wallet> getWalletByUserId(@PathVariable Long userId) {
        return walletService.getWalletByUserId(userId); // Извиква метод от WalletService
    }
//    @GetMapping("/{userId}/total-balance")
//    public double getTotalBalance(@PathVariable Long userId) {
//        return walletService.getTotalBalanceInUsd(userId);
//    }
    // Нулира портфейла на потребителя
//    @PostMapping("/reset/{userId}")
//    public void resetWallet(@PathVariable Long userId) {
//        walletService.resetWallets(userId); // Нулираме портфейлите
//    }
    @PutMapping("/update")
    public Wallet updateWallet(@RequestBody Wallet wallet) {
        return walletService.update(wallet);
    }
    @DeleteMapping("/{walletId}")
    public ResponseEntity<String> deleteWallet(@PathVariable Long walletId) {
        walletService.deleteWalletById(walletId);
        return ResponseEntity.ok("Портфейлът беше изтрит успешно.");
    }


    // Създава нов портфейл
    @PostMapping("/create")
    public Wallet createWallet(@RequestParam Long userId, @RequestParam String currency) {
        return walletService.createWallet(userId, currency); // Създаваме нов портфейл
    }
//    @GetMapping("/{userId}/total-balance")
//    public double getTotalBalance(@PathVariable Long userId) {
//        return walletService.getTotalBalanceInUsd(userId);
//    }
}
