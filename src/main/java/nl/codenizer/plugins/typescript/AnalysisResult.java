package codenizer.sonarqube.typescript;

public class AnalysisResult {
    private int numberOfClasses;
    private int numberOfInterfaces;
    private int numberOfMethods;
    private int numberOfLines;
    
    public int getNumberOfClasses() {
        return this.numberOfClasses;
    }
    
    public int getNumberOfInterfaces() {
        return this.numberOfInterfaces;
    }
    
    public int getNumberOfMethods() {
        return this.numberOfMethods;
    }
    
    public int getNumberOfLines() {
        return this.numberOfLines;
    }
}