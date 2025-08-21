package com.arjun.stock.util;

import com.arjun.stock.model.StockPrice;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ReportWriter {
    private final String filePath;

    public ReportWriter(String filePath) {
        this.filePath = filePath;
    }

    public void writeReport(List<StockPrice> stockPrices){
        try(FileWriter writer = new FileWriter(filePath, true)) {
            writer.write("Stock Report - "+ LocalDateTime.now()+"\n");
            for (StockPrice sp: stockPrices){
                writer.write(sp.toString()+"\n");
            }
            writer.write("\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
