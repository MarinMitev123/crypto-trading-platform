package com.crypto.trading.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "transaction_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String cryptoPair;

    @Column(nullable = false)
    private String transactionType;  // Buy or Sell

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
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
}
