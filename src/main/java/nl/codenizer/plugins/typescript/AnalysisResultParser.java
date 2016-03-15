package codenizer.sonarqube.typescript;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import com.google.gson.Gson;

public class AnalysisResultParser {
    public static AnalysisResult[] FromFile(String path) throws IllegalArgumentException, java.io.FileNotFoundException, java.io.IOException {
        if(path == null) {
            throw new IllegalArgumentException();
        }
        
        File file = new File(path);
        if(!file.exists()) {
            throw new FileNotFoundException();
        }

        String fileContents = readFile(path, Charset.defaultCharset());
        
        Gson gson = new Gson();
        
        return gson.fromJson(fileContents, AnalysisResult[].class);
    }
    
    static String readFile(String path, Charset encoding) throws java.io.IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}