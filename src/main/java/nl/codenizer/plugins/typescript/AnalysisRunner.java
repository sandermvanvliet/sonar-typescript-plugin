package nl.codenizer.plugins.typescript;

interface AnalysisRunner {
    AnalysisResult[] Execute(String projectRootDirectory);
}