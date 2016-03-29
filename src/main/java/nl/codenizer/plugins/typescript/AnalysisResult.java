package codenizer.sonarqube.typescript;

public class AnalysisResult {
    private String FileName;
    private int NumberOfClasses;
    private int NumberOfMethods;
    private int NumberOfLines;
    
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
}