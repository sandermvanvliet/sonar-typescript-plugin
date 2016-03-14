package codenizer.sonarqube.typescript.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileNotFoundException;

import codenizer.sonarqube.typescript.*;

public class WhenParsingAnalysisResultsFromFileTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
  
    @Test
    public void GivenAnEmptyPathThenExceptionIsThrown() {
        try {
            AnalysisResultParser.FromFile(null);
        } catch(Exception ex) {
            assert(ex instanceof IllegalArgumentException);
        }
    }
    
    @Test
    public void GivenTheFileDoesNotExistThenAFileNotFoundExceptionIsThrown() {
        try {
            AnalysisResultParser.FromFile("src/test/resources/empty_result_file.json");
        } catch(Exception ex){
            assert(ex instanceof FileNotFoundException);
        }
    }
}