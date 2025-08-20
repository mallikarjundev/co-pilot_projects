package com.arjun.stock.service.impl;

import com.arjun.stock.api.StockApiClient;
import com.arjun.stock.model.StockPrice;
import com.arjun.stock.service.StockPriceService;

import java.time.LocalDateTime;

public class StockPriceServiceImpl implements StockPriceService {
    private final StockApiClient apiClient;
    public StockPriceServiceImpl(StockApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public StockPrice getStockPrice(String symbol) {
        // Mocked response for now
        return apiClient.getStockPrice(symbol);
    }
}
