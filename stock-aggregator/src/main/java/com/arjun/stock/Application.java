package com.arjun.stock;

import com.arjun.stock.api.MockStockApiClient;
import com.arjun.stock.api.StockApiClient;
import com.arjun.stock.model.StockPrice;
import com.arjun.stock.service.StockPriceService;
import com.arjun.stock.service.impl.StockPriceServiceImpl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class Application
{
    public static void main( String[] args )
    {

        StockApiClient apiClient = new MockStockApiClient();

        StockPriceService stockPriceService = new StockPriceServiceImpl(apiClient);

        // Test with a few sample stock symbols
        String[] symbols = {"AAPL","GOOGL","AMZN"};

        // Create a scheduler
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable fetchTask = () -> {
            System.out.println("===== Fetching stock prices ====");
            for (String symbol: symbols){
                StockPrice stockPrice = stockPriceService.getStockPrice(symbol);
                System.out.println(stockPrice);
            }
            System.out.println("===========================================================");
        };

        scheduler.scheduleAtFixedRate(fetchTask,0,5, TimeUnit.SECONDS);

        scheduler.schedule(()->{
            System.out.println("Stopping scheduler....");
            scheduler.shutdown();
        },30,TimeUnit.SECONDS);

    }
}
