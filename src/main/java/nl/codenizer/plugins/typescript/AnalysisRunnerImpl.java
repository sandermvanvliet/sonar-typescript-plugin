package codenizer.sonarqube.typescript;

public class AnalysisRunnerImpl implements AnalysisRunner {    
    public AnalysisResult[] Execute(String projectRootDirectory) {
        AnalysisResult[] result = new AnalysisResult[0];
        
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