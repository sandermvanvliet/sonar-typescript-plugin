package codenizer.sonarqube.typescript;

public class AnalysisRunner {
    private final String projectRootDirectory;
    
    public AnalysisRunner(String projectRootDirectory) {
        this.projectRootDirectory = projectRootDirectory;
    }
    
    public AnalysisResult Execute() {
        AnalysisResult result = new AnalysisResult();
        
        return result;
    }
}