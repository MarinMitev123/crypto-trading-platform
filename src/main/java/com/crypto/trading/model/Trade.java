package com.crypto.trading.model;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Trade {
    private Wallet wallet;

    private Long id;
    private Long userId;
    private String crypto;
    private double quantity;
    private double price;
    private boolean buy;  // Ако е true, значи е покупка, ако е false - продажба
    private double profitLoss; // Печалба или загуба от сделката

    public Trade() {}

    public Trade(Long userId, String crypto, double quantity, double price, boolean buy) {
        this.userId = userId;
        this.crypto = crypto;
        this.quantity = quantity;
        this.price = price;
        this.buy = buy;
    }

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

    public String getCrypto() {
        return crypto;
    }

    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(double profitLoss) {
        this.profitLoss = profitLoss;
    }
    private double getCurrentPrice() {
        // Имплементация за получаване на текущата цена
        return 0.0; // Примерна стойност
    }
    public void calculateProfitLoss() {
        double currentPrice = getCurrentPrice();
        if (buy) {
            profitLoss = (currentPrice - price) * quantity;
        } else {
            profitLoss = (price - currentPrice) * quantity;
        }
    }
//    public void processTransaction() {
//        double amount = price * quantity;
//        wallet.updateBalance(amount, buy);
//        calculateProfitLoss();
//    }
//
//    // Метод за получаване на текущата цена на криптовалутата от Kraken API
//    private double getCurrentPrice() {
//        try {
//            String urlStr = "https://api.kraken.com/0/public/Ticker?pair=" + crypto + "USD";
//            URL url = new URL(urlStr);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(5000);
//            conn.setReadTimeout(5000);
//
//            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
//            JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
//            JsonObject result = jsonResponse.getAsJsonObject("result");
//            JsonObject pair = result.getAsJsonObject(crypto + "USD");
//            double currentPrice = pair.getAsJsonObject("c").get(0).getAsDouble();
//
//            return currentPrice;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0.0;
//        }
//    }
}
