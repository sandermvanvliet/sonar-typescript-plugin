package codenizer.sonarqube.typescript;

public class AnalysisResultParser {
    public static AnalysisResult FromFile(String path) {
        if(path == null) {
            throw new IllegalArgumentException();
        }
        
        return new AnalysisResult();
    }
}