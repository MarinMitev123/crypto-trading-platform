package com.crypto.trading.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "transaction_history")
@Getter
@Setter
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "crypto_pair", nullable = false)
    private String cryptoPair;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;  // Buy or Sell

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double total;

    @Column(name = "profit_loss", nullable = false)
    private double profitLoss;

    @Column(nullable = false)
    private String timestamp;

    public TransactionHistory(Long userId, String cryptoPair, String transactionType, double amount, double price, double total, double profitLoss) {
        this.userId = userId;
        this.cryptoPair = cryptoPair;
        this.transactionType = transactionType;
        this.amount = amount;
        this.price = price;
        this.total = total;
        this.profitLoss = profitLoss;
        this.timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());
    }

    // Празен конструктор за JPA
    public TransactionHistory() {
    }
}
