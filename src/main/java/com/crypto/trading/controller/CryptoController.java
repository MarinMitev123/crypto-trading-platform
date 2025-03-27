package com.crypto.trading.controller;

import com.crypto.trading.service.UserService;
import com.crypto.trading.service.KrakenWebSocketClient;
import com.crypto.trading.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    @Autowired
    private KrakenWebSocketClient krakenWebSocketClient;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    private static final Logger logger = LoggerFactory.getLogger(CryptoController.class);

    // Покупка на криптовалута
    @PostMapping("/buy")
    public String buyCrypto(@RequestParam String userId, @RequestParam String cryptoPair, @RequestParam double amount) {
        Long userIdLong = Long.valueOf(userId);

        // Проверка дали потребителят има достатъчно средства
        Double currentBalance = userService.getBalance(userIdLong);
        if (currentBalance == null) {
            return "User not found.";
        }

        Double currentPrice = krakenWebSocketClient.getCryptoPrice(cryptoPair);
        if (currentPrice == 0.0) {
            return "Invalid cryptocurrency pair.";
        }

        double totalCost = currentPrice * amount;

        // Проверка за достатъчност на средства
        if (currentBalance < totalCost) {
            return "Not enough balance to complete the purchase.";
        }

        // Извършваме покупката
        userService.decreaseBalance(userIdLong, totalCost);
        userService.addCryptoToPortfolio(userIdLong, cryptoPair, amount);

        // Записваме транзакцията
        transactionHistoryService.saveTransaction(userIdLong, cryptoPair, "BUY", amount, currentPrice);

        logger.info("User {} bought {} {} for {} USD", userId, amount, cryptoPair, totalCost);
        return "Successfully bought " + amount + " " + cryptoPair + " for " + totalCost + " USD.";
    }

    // Продажба на криптовалута
    @PostMapping("/sell")
    public String sellCrypto(@RequestParam String userId, @RequestParam String cryptoPair, @RequestParam double amount) {
        Long userIdLong = Long.valueOf(userId);

        // Проверка дали потребителят има достатъчно криптовалута за продажба
        double currentAmount = userService.getCryptoAmount(userIdLong, cryptoPair);
        if (currentAmount < amount) {
            return "Not enough crypto to sell.";
        }

        double currentPrice = krakenWebSocketClient.getCryptoPrice(cryptoPair);
        if (currentPrice == 0.0) {
            return "Invalid cryptocurrency pair.";
        }

        double totalRevenue = currentPrice * amount;

        // Извършваме продажбата
        userService.removeCryptoFromPortfolio(userIdLong, cryptoPair, amount);
        userService.increaseBalance(userIdLong, totalRevenue);

        // Записваме транзакцията
        transactionHistoryService.saveTransaction(userIdLong, cryptoPair, "SELL", amount, currentPrice);

        logger.info("User {} sold {} {} for {} USD", userId, amount, cryptoPair, totalRevenue);
        return "Successfully sold " + amount + " " + cryptoPair + " for " + totalRevenue + " USD.";
    }
}
