package com.arjun.stock;

import com.arjun.stock.api.MockStockApiClient;
import com.arjun.stock.api.StockPriceFetcherTask;
import com.arjun.stock.model.StockPrice;
import com.arjun.stock.service.StockPriceService;
import com.arjun.stock.service.impl.StockPriceServiceImpl;
import com.arjun.stock.util.ReportWriter;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) throws ExecutionException, InterruptedException, URISyntaxException {

        StockPriceService stockPriceService = new StockPriceServiceImpl(new MockStockApiClient());

        // Test with a few sample stock symbols
        List<String> symbols = Arrays.asList("AAPL", "GOOGL", "AMZN");

        // Create a scheduler
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<StockPrice>> futures = new ArrayList<>();

        for (String symbol : symbols) {
            StockPriceFetcherTask task = new StockPriceFetcherTask(symbol, stockPriceService);
            futures.add(executor.submit(task));
        }

        List<StockPrice> stockPrices = new ArrayList<>();
        for (Future<StockPrice> future : futures) {
            stockPrices.add(future.get());
        }


        executor.shutdown();

        ReportWriter reportWriter = new ReportWriter("./stock-aggregator/src/main/java/stock_report.txt");
        reportWriter.writeReport(stockPrices);

        System.out.println("Stock report generated in stock_report.txt");
    }
}
