package com.crypto.trading.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KrakenWebSocketClient {

    private static final String KRAKEN_WS_URL = "wss://ws.kraken.com/";
    private WebSocketClient webSocketClient;
    private final Map<String, Double> cryptoPrices = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // В клас KrakenWebSocketClient
    private final List<String> tradingPairs = List.of(
            "XBT/USD", "ETH/USD", "XRP/USD", "LTC/USD", "ADA/USD",
            "BCH/USD", "DOGE/USD", "DOT/USD", "SOL/USD", "AVAX/USD",
            "LINK/USD", "MATIC/USD", "ALGO/USD", "LUNA/USD", "UNI/USD",
            "FTM/USD", "AAVE/USD", "XLM/USD", "SHIB/USD", "FIL/USD"
    );


    @PostConstruct
    public void connect() {
        try {
            webSocketClient = new WebSocketClient(new URI(KRAKEN_WS_URL)) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("✅ Connected to Kraken WebSocket API");
                    subscribeToChannels();
                }

                @Override
                public void onMessage(String message) {
                    processMessage(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("⚠️ WebSocket closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("❌ WebSocket error: " + ex.getMessage());
                }
            };
            webSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Изпраща валидно съобщение за абонамент към Kraken
    private void subscribeToChannels() {
        try {
            Map<String, Object> message = Map.of(
                    "event", "subscribe",
                    "pair", tradingPairs,
                    "subscription", Map.of("name", "ticker") // Правилният формат
            );

            String json = objectMapper.writeValueAsString(message);
            send(json);
            System.out.println("📨 Sent subscribe message to Kraken: " + json);
        } catch (Exception e) {
            System.err.println("❌ Failed to build subscribe message: " + e.getMessage());
        }
    }


    // Изпраща съобщение към Kraken WebSocket
    private void send(String message) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.send(message);
        } else {
            System.err.println("❗ WebSocket connection is not open.");
        }
    }

    // Обработка на съобщения от Kraken
    private void processMessage(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);

            // Игнорираме heartbeat съобщенията
            if (jsonNode.has("event") && "heartbeat".equals(jsonNode.get("event").asText())) {
                return;
            }

            // Проверяваме дали съобщението съдържа търговска информация
            if (jsonNode.isArray() && jsonNode.size() > 3 && "ticker".equals(jsonNode.get(2).asText())) {
                String pair = jsonNode.get(3).asText(); // Например "XBT/USD"
                JsonNode dataNode = jsonNode.get(1);

                if (dataNode.has("c")) { // c -> последна цена
                    double price = dataNode.get("c").get(0).asDouble();
                    cryptoPrices.put(pair, price);
                    System.out.println("💰 Updated price: " + pair + " = " + price);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to parse message: " + message);
        }
    }


    public Double getCryptoPrice(String cryptoPair) {
        return cryptoPrices.getOrDefault(cryptoPair, 0.0);
    }

    public Map<String, Double> getAllPrices() {
        return cryptoPrices;
    }
}
