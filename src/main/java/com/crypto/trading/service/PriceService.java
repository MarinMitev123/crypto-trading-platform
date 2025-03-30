package com.crypto.trading.service;

import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Сервиз за извличане на текущи цени от Kraken API
 */
@Service
public class PriceService {

    // Кешираме последно взетите цени, за да не спамим Kraken API постоянно
    private final Map<String, Double> priceCache = new java.util.concurrent.ConcurrentHashMap<>();

    /**
     * Връща текущата цена на криптовалута спрямо USD (например BTC/USD)
     */
    public double getCurrentPrice(String symbol) {
        // Преобразуваме символа във формат на Kraken – напр. BTC/USD → XBTUSD
        String krakenSymbol = convertToKrakenSymbol(symbol);

        try {
            String urlStr = "https://api.kraken.com/0/public/Ticker?pair=" + krakenSymbol;
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

            JsonObject result = json.getAsJsonObject("result");
            JsonObject pairData = result.entrySet().iterator().next().getValue().getAsJsonObject();
            String priceStr = pairData.getAsJsonArray("c").get(0).getAsString();
            double price = Double.parseDouble(priceStr);

            // Запазваме в кеш
            priceCache.put(symbol, price);
            return price;

        } catch (Exception e) {
            e.printStackTrace();
            // Ако има проблем с API-то, връщаме кеширана цена или 0
            return priceCache.getOrDefault(symbol, 0.0);
        }
    }

    /**
     * Преобразува символа във формат на Kraken (BTC → XBT)
     */
    private String convertToKrakenSymbol(String symbol) {
        return symbol.replace("BTC", "XBT")
                .replace("/", "")
                .toUpperCase();
    }
}
