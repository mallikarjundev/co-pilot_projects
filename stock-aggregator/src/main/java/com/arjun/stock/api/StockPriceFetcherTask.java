package com.arjun.stock.api;

import com.arjun.stock.model.StockPrice;
import com.arjun.stock.service.StockPriceService;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class StockPriceFetcherTask implements Callable<StockPrice> {
    private static final Logger logger = Logger.getLogger(StockPriceFetcherTask.class.getName());

    private final String symbol;
    private final StockPriceService stockPriceService;

    public StockPriceFetcherTask(String symbol, StockPriceService stockPriceService) {
        this.symbol = symbol;
        this.stockPriceService = stockPriceService;
    }

    @Override
    public StockPrice call() throws Exception {
        StockPrice stockPrice = stockPriceService.getStockPrice(symbol);
        // Log the fetched Price
        logger.info("Fetched price for "+symbol+": "+stockPrice.getPrice());
        return stockPrice;
    }
}
