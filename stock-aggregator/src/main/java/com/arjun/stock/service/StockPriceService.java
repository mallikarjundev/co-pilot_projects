package com.arjun.stock.service;

import com.arjun.stock.model.StockPrice;

public interface StockPriceService {
    StockPrice getStockPrice(String symbol);
}
