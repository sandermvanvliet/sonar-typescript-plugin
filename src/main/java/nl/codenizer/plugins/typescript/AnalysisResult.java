package nl.codenizer.plugins.typescript;

public class AnalysisResult {
    private String FileName = null;
    private int NumberOfClasses = 0;
    private int NumberOfMethods = 0;
    private int NumberOfLines = 0;
    private int LinesOfCode = 0;
    private int LinesOfComments = 0;

    public int getNumberOfClasses() {
        return this.NumberOfClasses;
    }

    public int getNumberOfMethods() {
        return this.NumberOfMethods;
    }

    public int getNumberOfLines() {
        return this.NumberOfLines;
    }

    public String getFileName() {
        return this.FileName;
    }

    public int getLinesOfCode() {
        return this.LinesOfCode;
    }

    public int getLinesOfComments() { return this.LinesOfComments; }
}