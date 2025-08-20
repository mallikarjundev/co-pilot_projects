package com.arjun.stock.api;

import com.arjun.stock.model.StockPrice;

import java.time.LocalDateTime;
import java.util.Random;

public class MockStockApiClient implements StockApiClient{
    private final Random random = new Random();
    @Override
    public StockPrice getStockPrice(String symbol) {
        // Generate dummy stock price between 100 - 500
        double price = 100 +(400* random.nextDouble());
        return new StockPrice(symbol,price, LocalDateTime.now());
    }
}
