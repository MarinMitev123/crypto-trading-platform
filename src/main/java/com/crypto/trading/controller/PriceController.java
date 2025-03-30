package com.crypto.trading.controller;

import com.crypto.trading.service.KrakenWebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final KrakenWebSocketClient krakenWebSocketClient;

    @Autowired
    public PriceController(KrakenWebSocketClient krakenWebSocketClient) {
        this.krakenWebSocketClient = krakenWebSocketClient;
    }

    @GetMapping("/{crypto}")
    public Double getCryptoPrice(@PathVariable String crypto) {
        return krakenWebSocketClient.getCryptoPrice(crypto);
    }

    @GetMapping
    public Map<String, Double> getAllCryptoPrices() {
        return krakenWebSocketClient.getAllPrices();
    }
}