//package com.crypto.trading;
//
//import com.crypto.trading.model.User;
//import com.crypto.trading.repository.UserRepository;
//import com.crypto.trading.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    public void testGetBalance() {
//        // Arrange
//        Long userId = 1L;
//        User user = new User();
//        user.setId(userId);
//        user.setBalance(1000.0);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        // Act
//        double balance = userService.getBalance(userId);
//
//        // Assert
//        assertEquals(1000.0, balance);
//    }
//
//    @Test
//    public void testDecreaseBalance() {
//        // Arrange
//        Long userId = 1L;
//        User user = new User();
//        user.setId(userId);
//        user.setBalance(1000.0);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        // Act
//        userService.decreaseBalance(userId, 100.0);
//
//        // Assert
//        verify(userRepository, times(1)).save(user);
//        assertEquals(900.0, user.getBalance());
//    }
//
//    @Test
//    public void testIncreaseBalance() {
//        // Arrange
//        Long userId = 1L;
//        User user = new User();
//        user.setId(userId);
//        user.setBalance(1000.0);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        // Act
//        userService.increaseBalance(userId, 500.0);
//
//        // Assert
//        verify(userRepository, times(1)).save(user);
//        assertEquals(1500.0, user.getBalance());
//    }
//}
package com.crypto.trading;

import com.crypto.trading.model.User;
import com.crypto.trading.repository.UserRepository;
import com.crypto.trading.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetBalance() {
        Long userId = 1L;
        double balance = 10000.0;

        // Мока на поведението на репозитория
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(new User(userId, "testuser", "password", "test@crypto.com", balance)));

        // Извикваме метода
        double result = userService.getBalance(userId);

        // Проверяваме дали баланса е коректен
        assertEquals(balance, result);
    }

    @Test
    void testDecreaseBalance() {
        Long userId = 1L;
        double initialBalance = 10000.0;
        double amountToDeduct = 2000.0;
        double expectedBalance = 8000.0;

        // Мока на потребителя
        User user = new User(userId, "testuser", "password", "test@crypto.com", initialBalance);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Извикваме метода
        userService.decreaseBalance(userId, amountToDeduct);

        // Проверяваме дали баланса е намален правилно
        assertEquals(expectedBalance, user.getBalance());
    }
}
