package com.crypto.trading.service;

import com.crypto.trading.model.Trade;
import com.crypto.trading.model.Wallet;
import com.crypto.trading.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final KrakenWebSocketClient krakenWebSocketClient;
    private final TradeService tradeService;

    // Метод за извличане на портфейл на потребител по userId
    public List<Wallet> getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId); // Връща портфейл или null ако не намери
    }

    // Метод за създаване на нов портфейл
    public Wallet createWallet(Long userId, String currency) {
        Optional<Wallet> existingWallet = walletRepository.findByUserIdAndCurrency(userId, currency);
        if (existingWallet.isPresent()) {
            return existingWallet.get(); // Ако портфейлът съществува, връщаме го
        }

        // Ако портфейлът не съществува, създаваме нов
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setCurrency(currency);
        wallet.setBalance(10000.0);

        return walletRepository.save(wallet); // Записва новия портфейл в базата
    }
    public void deleteWalletById(Long walletId) {
        walletRepository.deleteWalletById(walletId);
    }
    public Wallet update(Wallet wallet) {
        return walletRepository.update(wallet);
    }
    public void resetWallets(Long userId) {
        // Изтриваме всички портфейли за потребителя
        walletRepository.deleteWalletById(userId);

        // Създаваме нов USD портфейл с баланс 10 000
        Wallet usdWallet = new Wallet();
        usdWallet.setUserId(userId);
        usdWallet.setCurrency("USD");
        usdWallet.setBalance(10000.0);

        // Записваме новия портфейл
        walletRepository.save(usdWallet);
    }

    // Метод за нулиране на всички портфейли на потребител
//    public void resetWallets(Long userId) {
//        walletRepository.deleteAllByUserId(userId); // Изтрива всички портфейли за потребителя
//        Wallet usdWallet = new Wallet();
//        usdWallet.setUserId(userId);
//        usdWallet.setCurrency("USD");
//        usdWallet.setBalance(10000.0);
//
//        walletRepository.save(usdWallet); // Записваме новия USD портфейл
//    }

//    public double getTotalBalanceInUsd(Long userId) {
//        List<Wallet> wallets = walletRepository.findByUserId(userId); // Взимаме портфейлите от базата
//        double total = 0.0;
//
//        for (Wallet wallet : wallets) {
//            String currency = wallet.getCurrency();
//            double balance = wallet.getBalance();
//
//            // Ако е USD – няма нужда от конвертиране
//            if (currency.equalsIgnoreCase("USD")) {
//                total += balance;
//            } else {
//                // Иначе търсим цената от Kraken
//                String pair = currency + "/USD"; // Пример: BTC/USD
//                Double price = krakenWebSocketClient.getCryptoPrice(pair);
//
//                if (price == null || price == 0.0) {
//                    System.err.println("⚠ Няма цена за: " + pair + " → пропускаме");
//                    continue; // пропускаме ако няма актуална цена
//                }
//
//                total += balance * price; // Добавяме в долари
//            }
//
//        }
//
//        return total;
//    }

//    public void buyCrypto(Long userId, String currency, double amount) {
//        List<Wallet> wallets = walletRepository.findByUserId(userId);
//        Wallet usdWallet = wallets.stream()
//                .filter(wallet -> wallet.getCurrency().equalsIgnoreCase("USD"))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Няма USD портфейл"));
//
//        double usdBalance = usdWallet.getBalance();
//        double price = krakenWebSocketClient.getCryptoPrice(currency + "/USD");
//
//        if (price == 0.0) {
//            throw new IllegalArgumentException("Няма цена за: " + currency);
//        }
//
//        double cost = amount * price;
//        if (usdBalance < cost) {
//            throw new IllegalArgumentException("Недостатъчно USD за покупка");
//        }
//
//        Wallet cryptoWallet = wallets.stream()
//                .filter(wallet -> wallet.getCurrency().equalsIgnoreCase(currency))
//                .findFirst()
//                .orElseGet(() -> createWallet(userId, currency));
//
//        usdWallet.setBalance(usdBalance - cost);
//        cryptoWallet.setBalance(cryptoWallet.getBalance() + amount);
//
//        walletRepository.save(usdWallet);
//        walletRepository.save(cryptoWallet);
//        tradeService.saveTrade(new Trade(userId, currency, amount, price, true));
//    }
    public void buyCrypto(Long userId, String currency, double amount) {
        List<Wallet> wallets = walletRepository.findByUserId(userId);
        Wallet usdWallet = wallets.stream()
                .filter(wallet -> wallet.getCurrency().equalsIgnoreCase("USD"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Няма USD портфейл"));

        double usdBalance = usdWallet.getBalance();
        double price = krakenWebSocketClient.getCryptoPrice(currency + "/USD");

        if (price == 0.0) {
            throw new IllegalArgumentException("Няма цена за: " + currency);
        }

        double cost = amount * price;
        if (usdBalance < cost) {
            throw new IllegalArgumentException("Недостатъчно USD за покупка");
        }

        Wallet cryptoWallet = wallets.stream()
                .filter(wallet -> wallet.getCurrency().equalsIgnoreCase(currency))
                .findFirst()
                .orElseGet(() -> createWallet(userId, currency));

        usdWallet.setBalance(usdBalance - cost);
        cryptoWallet.setBalance(cryptoWallet.getBalance() + amount);

        walletRepository.update(usdWallet);
        walletRepository.update(cryptoWallet);
        tradeService.saveTrade(new Trade(userId, currency, amount, price, true));
    }
    public void saleCrypto(Long userId, String currency, double amount) {
        List<Wallet> wallets = walletRepository.findByUserId(userId);
        Wallet cryptoWallet = wallets.stream()
                .filter(wallet -> wallet.getCurrency().equalsIgnoreCase(currency))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Няма портфейл за: " + currency));

        double cryptoBalance = cryptoWallet.getBalance();
        if (cryptoBalance < amount) {
            throw new IllegalArgumentException("Недостатъчно " + currency + " за продажба");
        }

        double price = krakenWebSocketClient.getCryptoPrice(currency + "/USD");
        if (price == 0.0) {
            throw new IllegalArgumentException("Няма цена за: " + currency);
        }

        Wallet usdWallet = wallets.stream()
                .filter(wallet -> wallet.getCurrency().equalsIgnoreCase("USD"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Няма USD портфейл"));

        double income = amount * price;
        usdWallet.setBalance(usdWallet.getBalance() + income);
        cryptoWallet.setBalance(cryptoBalance - amount);

        walletRepository.update(usdWallet);
        walletRepository.update(cryptoWallet);
        tradeService.saveTrade(new Trade(userId, currency, amount, price, false));
    }
}
