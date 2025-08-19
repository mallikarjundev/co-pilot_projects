package com.arjun.stock.api;

public final class ApiPaths {
    public ApiPaths() {
    }

    // NOTE: placeholder for now; we’ll externalize to config/env later.
    public static final String BASE_URL = "https://api.example.com";

    // Contracted endpoints (provider-agnostic)
    public static final String REALTIME_PRICE = "/stock/realtime"; // e.g., ?symbol=AAPL
    public static final String HISTORICAL = "/stock/history"; // e.g., ?symbol=AAPL&range=1m
    public static final String AGGREGATE = "/stock/aggregate"; // e.g., ?symbols=AAPL,GOOG,TSLA
}
