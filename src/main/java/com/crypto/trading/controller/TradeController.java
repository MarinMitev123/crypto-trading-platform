package com.crypto.trading.controller;

import com.crypto.trading.model.Trade;
import com.crypto.trading.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/{userId}")
    public List<Trade> getTradesByUserId(@PathVariable Long userId) {
        return tradeService.getTradesByUserId(userId);
    }

    @PostMapping("/execute")
    public Trade executeTrade(@RequestParam Long userId,
                              @RequestParam String crypto,
                              @RequestParam double quantity,
                              @RequestParam boolean isBuy) {
        return tradeService.executeTrade(userId, crypto, quantity, isBuy);
    }
}