package com.arjun.stock.model.dto;

import java.util.List;

public class StockHistoryResponse {

    private String symbol;
    private String range; // "1d", "1w", "1m", "6m", "1y"
    private List<StockHistoryEntry> history;

    public StockHistoryResponse() {
    }

    public StockHistoryResponse(String symbol, String range, List<StockHistoryEntry> history) {
        this.symbol = symbol;
        this.range = range;
        this.history = history;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<StockHistoryEntry> getHistory() {
        return history;
    }

    public void setHistory(List<StockHistoryEntry> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "StockHistoryResponse{" +
                "symbol='" + symbol + '\'' +
                ", range='" + range + '\'' +
                ", history=" + history +
                '}';
    }
}
