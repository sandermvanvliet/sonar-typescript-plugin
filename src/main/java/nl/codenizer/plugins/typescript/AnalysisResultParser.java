package codenizer.sonarqube.typescript;

import java.io.File;
import java.io.FileNotFoundException;

public class AnalysisResultParser {
    public static AnalysisResult FromFile(String path) throws IllegalArgumentException, java.io.FileNotFoundException {
        if(path == null) {
            throw new IllegalArgumentException();
        }
        
        File file = new File(path);
        if(!file.exists()) {
            throw new FileNotFoundException();
        }
        
        return new AnalysisResult();
    }
}