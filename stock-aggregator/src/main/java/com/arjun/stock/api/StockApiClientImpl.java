package com.arjun.stock.api;

import com.arjun.stock.model.StockPrice;
import com.arjun.stock.service.StockPriceService;

import java.time.LocalDateTime;
import java.util.Random;

public class StockApiClientImpl implements StockPriceService {

    private final StockApiClient stockApiClient;

    // Constructor injection
    public StockApiClientImpl(StockApiClient stockApiClient) {
        this.stockApiClient = stockApiClient;
    }

    @Override
    public StockPrice getStockPrice(String symbol) {
        return stockApiClient.getStockPrice(symbol);
    }
}
