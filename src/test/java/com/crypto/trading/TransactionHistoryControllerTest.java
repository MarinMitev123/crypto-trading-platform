package com.crypto.trading;

import com.crypto.trading.controller.TransactionHistoryController;
import com.crypto.trading.model.TransactionHistory;
import com.crypto.trading.service.TransactionHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransactionHistoryControllerTest {

    @Mock
    private TransactionHistoryService transactionHistoryService;

    @InjectMocks
    private TransactionHistoryController transactionHistoryController;

    @Test
    public void testGetTransactions() {
        Long userId = 1L;

        // Примерна транзакция
        TransactionHistory mockTransaction = new TransactionHistory(userId, "BTC/USD", "buy", 0.5, 20000.0, 10000.0, 0.0);

        // Мок на транзакциите
        when(transactionHistoryService.getAllTransactions(userId)).thenReturn(Collections.singletonList(mockTransaction));

        // Извикване на контролера
        var transactions = transactionHistoryController.getTransactions(userId);

        // Проверка дали резултатът е правилен
        assertEquals(1, transactions.size());
        assertEquals("BTC/USD", transactions.get(0).getCryptoPair());
    }
}
