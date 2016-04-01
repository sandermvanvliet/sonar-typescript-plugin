package nl.codenizer.plugins.typescript;

class AnalysisResult {
    private String FileName;
    private int NumberOfClasses;
    private int NumberOfMethods;
    private int NumberOfLines;
    private int LinesOfCode;
    
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
}