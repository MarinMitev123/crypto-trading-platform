package com.crypto.trading.model;

public class Wallet {
    private Long id;
    private Long userId;
    private String currency;  // Валутата (например USD, BTC)
    private Double balance;   // Балансът на потребителя в дадената валута

    public Wallet() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
