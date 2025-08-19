package com.arjun.stock.model.dto;

public class StockPriceResponse {
    private String symbol;
    private double price;
    private String currency; // e.g., "USD"
    private String timestamp; // ISO-8601 string, e.g., "2025-08-18T19:42:00Z"

    public StockPriceResponse() {
    }

    public StockPriceResponse(String symbol, double price, String currency, String timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "StockPriceResponse{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
