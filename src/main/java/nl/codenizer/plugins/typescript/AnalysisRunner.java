package codenizer.sonarqube.typescript;

public class AnalysisRunner {
    private final String projectRootDirectory;
    
    public AnalysisRunner(String projectRootDirectory) {
        this.projectRootDirectory = projectRootDirectory;
    }
    
    public AnalysisResult Execute() {
        AnalysisResult result = new AnalysisResult();
        
       try {
           // 1) Set working directory to projectRootDirectory
           // 2) Call analyzer.js
           // 3) Parse result file
           Process p = Runtime.getRuntime().exec("node analyzer.js");
           
           p.waitFor();           
       }
       catch(Exception ex) {
           
       }
    
        return result;
    }
}