package com.arjun.stock;

import com.arjun.stock.model.StockPrice;
import com.arjun.stock.service.StockPriceService;
import com.arjun.stock.service.impl.StockPriceServiceImpl;

/**
 * Hello world!
 *
 */
public class Application
{
    public static void main( String[] args )
    {
        StockPriceService stockPriceService = new StockPriceServiceImpl();

        // Test with a few sample stock symbols
        StockPrice apple = stockPriceService.getStockPrice("AAPL");
        StockPrice google = stockPriceService.getStockPrice("GOOGL");
        StockPrice amazon = stockPriceService.getStockPrice("AMZN");

        System.out.println(apple);
        System.out.println(google);
        System.out.println(amazon);
    }
}
