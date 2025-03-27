package com.crypto.trading.controller;

import com.crypto.trading.model.Wallet;
import com.crypto.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}")
    public Optional<Wallet> getWalletByUserId(@PathVariable Long userId) {
        return walletService.getWalletByUserId(userId);
    }

    @PostMapping("/reset/{userId}")
    public void resetWallet(@PathVariable Long userId) {
        walletService.resetWallet(userId);
    }
}

