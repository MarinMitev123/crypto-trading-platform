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

    @PostMapping
    public void createTransaction(@RequestBody TransactionHistory transactionHistory) {
        transactionHistoryService.saveTransaction(transactionHistory);
    }

    @GetMapping("/{userId}")
    public List<TransactionHistory> getUserTransactions(@PathVariable Long userId) {
        return transactionHistoryService.getTransactionHistory(userId); // Викаме метода от Service
    }
}
