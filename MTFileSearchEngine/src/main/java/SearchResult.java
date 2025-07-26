public class SearchResult {
    private final String fileName;
    private final int lineNumber;
    private final String lineContent;

    public SearchResult(String fileName, int lineNumber, String lineContent) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.lineContent = lineContent;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLineContent() {
        return lineContent;
    }

    @Override
    public String toString() {
        return String.format("File: %-20s Line %d: %s", fileName, lineNumber, lineContent);
    }
}
