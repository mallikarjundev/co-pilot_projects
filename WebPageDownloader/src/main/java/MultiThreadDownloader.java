import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadDownloader {
    private static final int MAX_RETRIES = 2;
    private static final String OUTPUT_FOLDER = "./WebPageDownloader/src";

    public static void main(String[] args) {
        List<String> urls = List.of("https://example.com",
                "https://www.wikipedia.org",
                "https://www.openai.com");

        new File(OUTPUT_FOLDER).mkdirs();

        ExecutorService service = Executors.newFixedThreadPool(4);

        long start = System.currentTimeMillis();
        List<Future<Boolean>> futures = urls.stream().map(url->service.submit(()->downloadWithRetry(url))).toList();

        for (Future<Boolean> future: futures){
            try {
                future.get();
            }catch (Exception e){
                System.out.println("Download task error: " + e.getMessage());
            }
        }

        service.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("Time taken (multi-threaded with retry): " + (end - start) + "ms");
    }

    private static boolean downloadWithRetry(String url){
        long urlStart = System.currentTimeMillis();
        for (int attempt = 1;attempt<=MAX_RETRIES;attempt++){
            try {
                downloadToFile(url);
                long urlEnd = System.currentTimeMillis();
                System.out.println("Time taken for " + url + ": " + (urlEnd - urlStart) + "ms");
                return true;
            }catch (Exception e){
                System.out.println("Attempt " + attempt + " failed for " + url + ": " + e.getMessage());
            }
        }
        return false;
    }

    private static void downloadToFile(String urlStr) throws Exception{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Java Downloader)");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP " + responseCode);
        }

        String fileName = url.getHost().replaceAll("\\W+","_")+".html";
        File outputFile = new File(OUTPUT_FOLDER,fileName);

        try(InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(outputFile)
        ) {
            byte[] buffer = new byte[4096];
            int byteRead;
            while ((byteRead=(in.read(buffer)))!=-1){
                out.write(buffer,0,byteRead);
            }
        }
        System.out.println("Downloaded & saved (thread: " + Thread.currentThread().getName() + "): "
                + outputFile.getAbsolutePath());
    }
}
