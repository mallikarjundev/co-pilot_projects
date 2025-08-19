import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class WebDownloaderWithProgress {
    private static final AtomicLong totalBytesDownloaded = new AtomicLong();
    public static void main(String[] args) throws InterruptedException {
        List<String> urls = List.of(
                "https://www.example.com",
                "https://www.wikipedia.org",
                "https://www.openai.com"
        );

        ExecutorService executor = Executors.newFixedThreadPool(urls.size());

        long startTime = System.currentTimeMillis();

        for (String url : urls) {
            executor.submit(() -> downloadWithProgress(url));
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();

        System.out.println("\nTotal size downloaded: " + formatSize(totalBytesDownloaded.get()));
        System.out.println("\nTime taken (multi-threaded with progress): " + (endTime - startTime) + "ms");
    }

    private static void downloadWithProgress(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            int totalSize = conn.getContentLength();

            String fileName = url.getHost() + ".html";
            try (InputStream in = conn.getInputStream();
                 FileOutputStream out = new FileOutputStream(fileName)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                int downloaded = 0;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    downloaded += bytesRead;
                    if (totalSize > 0) {
                        int progress = (int) (((double) downloaded / totalSize) * 100);
                        printProgress(fileName, progress);
                    }
                }
            }

            File downloadedFile = new File(fileName);
            long fileSize = downloadedFile.length();
            totalBytesDownloaded.addAndGet(fileSize);

            System.out.println("\nDownloaded: " + fileName + " (" + formatSize(fileSize) + ")");


        } catch (IOException e) {
            System.out.println("\nFailed to download " + urlStr + ": " + e.getMessage());
        }
    }

    private static void printProgress(String fileName, int progress) {
        int totalBars = 20;
        int filledBars = (progress * totalBars) / 100;
        String bar = "[" + "#".repeat(filledBars) + "-".repeat(totalBars - filledBars) + "]";
        System.out.print("\r" + fileName + " " + bar + " " + progress + "%");
    }

    private static String formatSize(long bytes) {
        if (bytes<1024) return bytes+"B";
        int exp = (int) (Math.log(bytes)/Math.log(1024));
        char unit = "KMGTPE".charAt(exp-1);
        return String.format("%.2f %sB",bytes/Math.pow(1024,exp),unit);
    }
}
