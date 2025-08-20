package com.arjun.stock.service.impl;

import com.arjun.stock.model.StockPrice;
import com.arjun.stock.service.StockPriceService;

import java.time.LocalDateTime;

public class StockPriceServiceImpl implements StockPriceService {
    @Override
    public StockPrice getStockPrice(String symbol) {
        // Mocked response for now
        return new StockPrice(symbol, Math.round(Math.random()*1000*100.0)*100.0,// random price up to 1000 with 2 decimals
                LocalDateTime.now() // current time as LocalDateTime
        );
    }
}
