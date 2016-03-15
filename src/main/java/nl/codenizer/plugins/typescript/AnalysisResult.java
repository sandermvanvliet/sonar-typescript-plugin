package codenizer.sonarqube.typescript;

public class AnalysisResult {
    private int numberOfClasses;
    private int numberOfMethods;
    private int numberOfLines;
    private String fileName;
    
    public int getNumberOfClasses() {
        return this.numberOfClasses;
    }
    
    public int getNumberOfMethods() {
        return this.numberOfMethods;
    }
    
    public int getNumberOfLines() {
        return this.numberOfLines;
    }
    
    public String getFileName() {
        return this.fileName;
    }
}