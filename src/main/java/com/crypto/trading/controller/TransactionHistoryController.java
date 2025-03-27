package com.crypto.trading.controller;

import com.crypto.trading.model.TransactionHistory;
import com.crypto.trading.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @Autowired
    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    // Получаване на транзакции по потребителски ID
    @GetMapping("/{userId}")
    public List<TransactionHistory> getTransactions(@PathVariable Long userId) {
        return transactionHistoryService.getAllTransactions(userId);
    }
}
