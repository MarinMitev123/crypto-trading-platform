package com.crypto.trading.service;

import com.crypto.trading.model.TransactionHistory;
import com.crypto.trading.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    // В TransactionHistoryService.java


    @Autowired
    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    public void saveTransaction(TransactionHistory transactionHistory) {
        transactionHistoryRepository.save(transactionHistory);
    }

    public List<TransactionHistory> getTransactionHistory(Long userId) {
        return transactionHistoryRepository.getTransactionHistory(userId);
    }
}
