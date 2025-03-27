//package com.crypto.trading;
//
//import com.crypto.trading.controller.CryptoController;
//import com.crypto.trading.service.UserService;
//import com.crypto.trading.service.KrakenWebSocketClient;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CryptoControllerTest {
//
//    @Mock
//    private KrakenWebSocketClient krakenWebSocketClient;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private CryptoController cryptoController;
//
//    @Test
//    public void testBuyCryptoSuccess() {
//        Long userId = 1L;
//        String cryptoPair = "BTC/USD";
//        double amount = 0.5;
//        double price = 20000.0;
//        double currentBalance = 10000.0;
//
//        // Мока на цената
//        when(krakenWebSocketClient.getCryptoPrice(cryptoPair)).thenReturn(price);
//
//        // Мока на баланса на потребителя
//        when(userService.getBalance(userId)).thenReturn(currentBalance);
//
//        String result = cryptoController.buyCrypto(userId.toString(), cryptoPair, amount);
//
//        assertEquals("Successfully bought 0.5 BTC/USD for 10000.0 USD.", result);
//    }
//
//    @Test
//    public void testBuyCryptoInsufficientBalance() {
//        Long userId = 1L;
//        String cryptoPair = "BTC/USD";
//        double amount = 0.5;
//        double price = 20000.0;
//        double currentBalance = 5000.0;
//
//        // Мока на цената
//        when(krakenWebSocketClient.getCryptoPrice(cryptoPair)).thenReturn(price);
//
//        // Мока на баланса на потребителя
//        when(userService.getBalance(userId)).thenReturn(currentBalance);
//
//        String result = cryptoController.buyCrypto(userId.toString(), cryptoPair, amount);
//
//        assertEquals("Not enough balance to complete the purchase.", result);
//    }
//}
package com.crypto.trading;

import com.crypto.trading.controller.CryptoController;
import com.crypto.trading.service.UserService;
import com.crypto.trading.service.KrakenWebSocketClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)  // Добавя Mockito поддръжка
public class CryptoControllerTest {

    @Mock
    private KrakenWebSocketClient krakenWebSocketClient;

    @Mock
    private UserService userService;

    @InjectMocks
    private CryptoController cryptoController;

    @Test
    public void testBuyCryptoSuccess() {
        Long userId = 1L;
        String cryptoPair = "BTC/USD";
        double amount = 0.5;
        double price = 20000.0;
        double currentBalance = 10000.0;

        when(krakenWebSocketClient.getCryptoPrice(cryptoPair)).thenReturn(price);
        when(userService.getBalance(userId)).thenReturn(currentBalance);

        String result = cryptoController.buyCrypto(userId.toString(), cryptoPair, amount);

        assertEquals("Successfully bought 0.5 BTC/USD for 10000.0 USD.", result);
    }

    @Test
    public void testBuyCryptoInsufficientBalance() {
        Long userId = 1L;
        String cryptoPair = "BTC/USD";
        double amount = 0.5;
        double price = 20000.0;
        double currentBalance = 5000.0;

        when(krakenWebSocketClient.getCryptoPrice(cryptoPair)).thenReturn(price);
        when(userService.getBalance(userId)).thenReturn(currentBalance);

        String result = cryptoController.buyCrypto(userId.toString(), cryptoPair, amount);

        assertEquals("Not enough balance to complete the purchase.", result);
    }
}
