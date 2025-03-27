//package com.crypto.trading.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.Getter;
//
//@Entity
//@Table(name = "trades")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Trade {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @Column(nullable = false)
//    private String crypto;
//
//    @Column(nullable = false)
//    private double quantity;
//
//    @Column(nullable = false)
//    private double price;
//
//    @Column(nullable = false)
//    private boolean buy; // ❗ Променено от isBuy -> buy
//
//    @Column
//    private Double profitLoss;
//
//    // Конструктори без Lombok за удобство
//    public Trade(User user, String crypto, double quantity, double price, boolean buy) {
//        this.user = user;
//        this.crypto = crypto;
//        this.quantity = quantity;
//        this.price = price;
//        this.buy = buy;
//    }
//
//    public Trade(User user, String crypto, double quantity, double price, boolean buy, Double profitLoss) {
//        this.user = user;
//        this.crypto = crypto;
//        this.quantity = quantity;
//        this.price = price;
//        this.buy = buy;
//        this.profitLoss = profitLoss;
//    }
//}

package com.crypto.trading.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String crypto;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean buy;

    @Column
    private Double profitLoss;

    public Trade() {}

    public Trade(User user, String crypto, double quantity, double price, boolean buy) {
        this.user = user;
        this.crypto = crypto;
        this.quantity = quantity;
        this.price = price;
        this.buy = buy;
    }

    public Trade(User user, String crypto, double quantity, double price, boolean buy, Double profitLoss) {
        this.user = user;
        this.crypto = crypto;
        this.quantity = quantity;
        this.price = price;
        this.buy = buy;
        this.profitLoss = profitLoss;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCrypto() {
        return crypto;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBuy() {
        return buy;
    }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }
}
