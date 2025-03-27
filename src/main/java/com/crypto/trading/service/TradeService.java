package com.crypto.trading.service;

import com.crypto.trading.model.Trade;
import com.crypto.trading.model.Wallet;
import com.crypto.trading.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;
    private final WalletService walletService;
    private final KrakenWebSocketClient krakenClient;

    @Autowired
    public TradeService(TradeRepository tradeRepository, WalletService walletService, KrakenWebSocketClient krakenClient) {
        this.tradeRepository = tradeRepository;
        this.walletService = walletService;
        this.krakenClient = krakenClient;
    }

    public List<Trade> getTradesByUserId(Long userId) {
        return tradeRepository.findByUser_Id(userId);
    }

    public Trade executeTrade(Long userId, String crypto, double quantity, boolean isBuy) {
        double price = krakenClient.getCryptoPrice(crypto + "/USD");
        double totalAmount = quantity * price;

        Wallet fiatWallet = walletService.getWalletByUserIdAndCurrency(userId, "USD")
                .orElseThrow(() -> new RuntimeException("USD портфейл не е намерен"));

        Wallet cryptoWallet = walletService.getWalletByUserIdAndCurrency(userId, crypto)
                .orElse(walletService.createWallet(userId, crypto));

        if (isBuy) {
            if (fiatWallet.getBalance() < totalAmount) {
                throw new RuntimeException("Недостатъчен USD баланс");
            }
            fiatWallet.setBalance(fiatWallet.getBalance() - totalAmount);
            cryptoWallet.setBalance(cryptoWallet.getBalance() + quantity);

            walletService.saveWallet(fiatWallet);
            walletService.saveWallet(cryptoWallet);

            Trade trade = new Trade(fiatWallet.getUser(), crypto, quantity, price, true);
            return tradeRepository.save(trade);
        } else {
            if (cryptoWallet.getBalance() < quantity) {
                throw new RuntimeException("Недостатъчно количество " + crypto);
            }

            fiatWallet.setBalance(fiatWallet.getBalance() + totalAmount);
            cryptoWallet.setBalance(cryptoWallet.getBalance() - quantity);

            walletService.saveWallet(fiatWallet);
            walletService.saveWallet(cryptoWallet);

            // Изчисляване на печалба/загуба спрямо средна покупна цена
            List<Trade> previousBuys = tradeRepository.findByUser_Id(userId).stream()
                    .filter(t -> t.getCrypto().equals(crypto) && t.isBuy())
                    .toList();

            double totalBoughtQty = previousBuys.stream().mapToDouble(Trade::getQuantity).sum();
            double totalBoughtValue = previousBuys.stream().mapToDouble(t -> t.getQuantity() * t.getPrice()).sum();
            double averageBuyPrice = totalBoughtQty > 0 ? totalBoughtValue / totalBoughtQty : price;

            double profitLoss = (price - averageBuyPrice) * quantity;

            Trade trade = new Trade(fiatWallet.getUser(), crypto, quantity, price, false, profitLoss);
            return tradeRepository.save(trade);
        }
    }
}
