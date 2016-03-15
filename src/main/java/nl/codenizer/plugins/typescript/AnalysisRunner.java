package codenizer.sonarqube.typescript;

public interface AnalysisRunner {
    AnalysisResult[] Execute(String projectRootDirectory);
}