import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchEngineApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Text Search Engine ===");
        System.out.println("Current Working Directory: " + new File(".").getAbsolutePath());
        System.out.print("Enter folder path containing .txt files: ");
        String folderPath = sc.nextLine();

        System.out.print("Enter keyword to search: ");
        String keyword = sc.nextLine();

//        System.out.print("Do you want a case-sensitive search? (yes/no): ");
//        boolean caseSensitive=sc.nextLine().trim().equalsIgnoreCase("yes");

        try {
            List<File> textFiles = FileUtils.loadTxtFilesFrmFolder(folderPath);
            System.out.println("Found " + textFiles.size() + " .txt files.");

            if (textFiles.isEmpty()){
                System.out.println("No text files to scan. Exiting.");
                return;
            }

            // Step 2: Create ExecutorService and submit tasks
            ExecutorService executor = Executors.newFixedThreadPool(4);
            List<Future<List<SearchResult>>> futures = new ArrayList<>();

            for (File file: textFiles){
                futures.add(executor.submit(new FileScanner(file,keyword)));
            }

            // Step 3: Collect and display results
            List<SearchResult> allResults = new ArrayList<>();
            for (Future<List<SearchResult>> future : futures){
                allResults.addAll(future.get());
            }

            executor.shutdown();

            int totalHits = allResults.size();
            System.out.println("\nSearch Results:");
            for (SearchResult result:allResults){
                System.out.println(result);
            }
            System.out.println("\nTotal Hits across all files: " + totalHits);

            // Step 4: Export results to file
            System.out.print("\nDo you want to export results to file? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")){
                File outputFile = new File("./MTFileSearchEngine/src/files","search_results.txt");
                saveResultsToFile(allResults,totalHits,outputFile);
            }
        }catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void saveResultsToFile(List<SearchResult> resultList, int totalHits, File fileName ){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (SearchResult result: resultList){
                bw.write(result.toString());
                bw.newLine();
            }

            bw.write("Total Hits across all files: " + totalHits);
            bw.newLine();
            System.out.println("Results exported to: " + fileName);
            System.out.println(System.getProperty("user.dir"));
        }catch (Exception e){
            System.out.println("Failed to write to file: " + e.getMessage());
        }
    }
}
