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
    
    @Test
    public void GivenAValidFileThenTheNumberOfClassesIsSet() {
        AnalysisResult result = null;
        
        try {
            result = AnalysisResultParser.FromFile("src/test/resources/valid_result.json");
        } catch(Exception ex) {
            
        }
        
        assert(result.getNumberOfClasses() == 10);
    }
    
    @Test
    public void GivenAValidFileThenTheNumberOfMethodsIsSet() {
        AnalysisResult result = null;
        
        try {
            result = AnalysisResultParser.FromFile("src/test/resources/valid_result.json");
        } catch(Exception ex) {
            
        }
        
        assert(result.getNumberOfMethods() == 123);
    }
    
    @Test
    public void GivenAValidFileThenTheNumberOfLinesIsSet() {
        AnalysisResult result = null;
        
        try {
            result = AnalysisResultParser.FromFile("src/test/resources/valid_result.json");
        } catch(Exception ex) {
            
        }
        
        assert(result.getNumberOfLines() == 4567);
    }
}