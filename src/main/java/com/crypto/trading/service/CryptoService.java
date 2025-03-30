package com.crypto.trading.service;

import com.crypto.trading.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoService {

    private final CryptoRepository cryptoRepository;

    @Autowired
    public CryptoService(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    public List<String> getAllCryptoPairs() {
        return cryptoRepository.getAllCryptoPairs();
    }
}
