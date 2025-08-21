package com.arjun.stock;

import com.arjun.stock.api.MockStockApiClient;
import com.arjun.stock.api.StockApiClient;
import com.arjun.stock.api.StockPriceFetcherTask;
import com.arjun.stock.model.StockPrice;
import com.arjun.stock.service.StockPriceService;
import com.arjun.stock.service.impl.StockPriceServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class Application
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {

        StockPriceService stockPriceService = new StockPriceServiceImpl(new MockStockApiClient());

        // Test with a few sample stock symbols
        List<String> symbols = Arrays.asList("AAPL","GOOGL","AMZN");

        // Create a scheduler
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<StockPrice>> futures = new ArrayList<>();

            for (String symbol: symbols){
                StockPriceFetcherTask task = new StockPriceFetcherTask(symbol,stockPriceService);
                futures.add(executor.submit(task));
            }

            for (Future<StockPrice> future:futures){
                StockPrice stockPrice = future.get();
                System.out.println("Fetched stock: "+stockPrice);
            }


        executor.shutdown();

    }
}
