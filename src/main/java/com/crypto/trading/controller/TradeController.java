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

    // Извлича всички сделки за даден потребител по ID
    @GetMapping("/{userId}")
    public List<Trade> getTradesByUserId(@PathVariable Long userId) {
        return tradeService.getTradesByUserId(userId);  // Извиква метода на TradeService за получаване на сделки
    }


    // Изпълнява търговия (покупка или продажба на криптовалута)
    @PostMapping("/execute")
    public String executeTrade(@RequestParam Long userId,
                               @RequestParam String crypto,
                               @RequestParam double quantity,
                               @RequestParam boolean isBuy) {
        // Създаване на нова търговия (съхраняване на сделки)
        Trade trade = new Trade(userId, crypto, quantity, 1000.0, isBuy);  // Цената на криптовалутата може да идва от API или друго място
        tradeService.saveTrade(trade);

        // Възможно е да добавиш логика за актуализиране на баланса на потребителя и портфейлите му.
        return "Търговията с " + crypto + " беше успешно извършена!";
    }
}
