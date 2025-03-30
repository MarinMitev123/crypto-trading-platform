package com.crypto.trading.service;

import com.crypto.trading.model.Trade;
import com.crypto.trading.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    // Извлича всички сделки на потребител
    public List<Trade> getTradesByUserId(Long userId) {
        return tradeRepository.findByUserId(userId);
    }

    // Записва търговия
    public void saveTrade(Trade trade) {
        tradeRepository.save(trade);
    }

}
