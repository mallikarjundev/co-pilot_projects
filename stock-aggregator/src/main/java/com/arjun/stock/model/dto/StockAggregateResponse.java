package com.arjun.stock.model.dto;

import java.util.List;

public class StockAggregateResponse {

    private List<StockPriceResponse> stocks;

    public StockAggregateResponse() {
    }

    public StockAggregateResponse(List<StockPriceResponse> stocks) {
        this.stocks = stocks;
    }

    public List<StockPriceResponse> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockPriceResponse> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "StockAggregateResponse{" +
                "stocks=" + stocks +
                '}';
    }
}
