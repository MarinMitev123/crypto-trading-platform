package com.crypto.trading.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Map<String, Double> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Map<String, Double> portfolio) {
        this.portfolio = portfolio;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    // Баланс на потребителя
    @Getter
    @Column(nullable = false)
    private double balance = 10000.0;  // Примерен начален баланс от 10,000

    // Портфейл на потребителя за криптовалутите
    @Getter
    @ElementCollection
    @CollectionTable(name = "user_portfolio", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "crypto_pair")
    @Column(name = "quantity")
    private Map<String, Double> portfolio = new HashMap<>();// Пример: BTC/USD -> 0.5, ETH/USD -> 2.0
    public User(Long id, String username, String password, String email, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = balance;
    }
}