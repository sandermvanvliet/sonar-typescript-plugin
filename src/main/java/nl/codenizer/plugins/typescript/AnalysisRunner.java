package nl.codenizer.plugins.typescript;

public interface AnalysisRunner {
    AnalysisResult[] Execute(String projectRootDirectory);
}