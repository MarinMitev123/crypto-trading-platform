package com.crypto.trading.service;

import com.crypto.trading.model.TransactionHistory;
import com.crypto.trading.model.Trade;
import com.crypto.trading.repository.TransactionHistoryRepository;
import com.crypto.trading.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final TradeRepository tradeRepository; // Добавяме TradeRepository

    @Autowired
    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository, TradeRepository tradeRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.tradeRepository = tradeRepository; // Инициализираме зависимостта
    }

    // Записва нова транзакция
    public void saveTransaction(Long userId, String cryptoPair, String transactionType, double amount, double price) {
        double total = amount * price;
        double profitLoss = 0.0;

        // Ако е продажба, изчисляваме печалба/загуба
        if ("Sell".equalsIgnoreCase(transactionType)) {
            // Намираме всички покупки за същата криптовалута на потребителя
            List<Trade> previousBuys = tradeRepository.findByUser_Id(userId).stream()
                    .filter(t -> t.getCrypto().equals(cryptoPair.split("/")[0]) && t.isBuy())
                    .toList();

            double totalBoughtQty = previousBuys.stream().mapToDouble(Trade::getQuantity).sum();
            double totalBoughtValue = previousBuys.stream().mapToDouble(t -> t.getQuantity() * t.getPrice()).sum();
            double averageBuyPrice = totalBoughtQty > 0 ? totalBoughtValue / totalBoughtQty : price;

            // Изчисляваме печалба/загуба
            profitLoss = (price - averageBuyPrice) * amount;
        }

        // Записваме транзакцията
        TransactionHistory transactionHistory = new TransactionHistory(userId, cryptoPair, transactionType, amount, price, total, profitLoss);
        transactionHistoryRepository.save(transactionHistory);
    }

    // Връща всички транзакции за даден потребител
    public List<TransactionHistory> getAllTransactions(Long userId) {
        return transactionHistoryRepository.findByUserId(userId); // Намира транзакции по потребителски id
    }
}
