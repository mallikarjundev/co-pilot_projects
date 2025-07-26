import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebPageDownloader {
    private static final int MAX_RETRIES = 2;
    public static void main(String[] args) {
        List<String> urls = List.of(
                "https://example.com",
                "https://www.wikipedia.org",
                "https://www.openai.com"
        );

        long startTime = System.currentTimeMillis();
        for (String url: urls){
            boolean success =downloadWithRetry(url);
            if (!success) {
                System.out.println("Failed after retries: " + url);
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken (single-threaded): "+(endTime-startTime)+"ms");
    }

    private static boolean downloadWithRetry(String url){
        for (int i=1;i<=MAX_RETRIES;i++){
            try {
                downloadToFile(url);
//                String content = downloadWebPage(url);
//                saveToFile(url,content);
//                System.out.println("Downloaded: " + url);
                return true;
            }catch (Exception e){
                System.out.println("Attempt " + i + " failed for " + url + ": " + e.getMessage());
            }
        }
        return false;
    }

    private static void downloadToFile(String urlStr) throws Exception{
//        StringBuilder content = new StringBuilder();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Java Downloader)");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int code = conn.getResponseCode();
        if (code!=200){
            throw new RuntimeException("HTTP " + code);
        }

        String fileName = url.getHost().replaceAll("\\W+","_")+".html";
        File outputFile = new File("./WebPageDownloader/src",fileName);
        try(InputStream in = conn.getInputStream();
        FileOutputStream out = new FileOutputStream(outputFile)){
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead=in.read(buffer))!=-1){
                out.write(buffer,0,bytesRead);
            }
        }
        System.out.println("Downloaded & saved: " + outputFile.getAbsolutePath());
    }

//    private static void saveToFile(String urlStr, String content) throws Exception{
//        File fileName = new File("./WebPageDownloader/src",urlStr.replaceAll("[^A-Za-z0-9]","_")+".html");
//        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
//            bw.write(content);
//        }
//    }
}
