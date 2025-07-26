import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FileScanner implements Callable<List<SearchResult>> {

    private final File file;
    private final String keyword;

    public FileScanner(File file, String keyword) {
        this.file = file;
        this.keyword = keyword;
    }

    @Override
    public List<SearchResult> call() throws Exception {
        List<SearchResult> result = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNo = 1;
            while ((line= br.readLine())!=null){
                if (line.toLowerCase().contains(keyword.toLowerCase())){
                    result.add(new SearchResult(file.getName(), lineNo,line.trim()));
                }
                lineNo++;
            }
        }

        return result;
    }
}
