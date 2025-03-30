package com.crypto.trading.controller;

import com.crypto.trading.service.KrakenWebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/websocket")
public class WebSocketController {

    private final KrakenWebSocketClient krakenWebSocketClient;

    @Autowired
    public WebSocketController(KrakenWebSocketClient krakenWebSocketClient) {
        this.krakenWebSocketClient = krakenWebSocketClient;
    }

    @GetMapping("/prices")
    public Map<String, Double> getCryptoPrices() {
        return krakenWebSocketClient.getAllPrices();
    }
}
