package com.arjun.stock.api;

import com.arjun.stock.model.StockPrice;

public interface StockApiClient {
    StockPrice getStockPrice(String symbol);
}
