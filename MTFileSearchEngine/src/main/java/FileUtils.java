import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public static List<File> loadTxtFilesFrmFolder(String folderPath){
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()){
            throw new IllegalArgumentException("Invalid folder path: " + folderPath);
        }

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        return files!=null? Arrays.asList(files):new ArrayList<>();
    }
}
