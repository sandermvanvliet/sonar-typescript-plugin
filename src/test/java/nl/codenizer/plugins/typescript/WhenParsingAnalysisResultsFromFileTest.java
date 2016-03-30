package codenizer.sonarqube.typescript.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileNotFoundException;

import static org.fest.assertions.Assertions.assertThat;
import codenizer.sonarqube.typescript.*;

public class WhenParsingAnalysisResultsFromFileTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
  
    @Test
    public void GivenAnEmptyPathThenExceptionIsThrown() {
        try {
            AnalysisResultParser.FromFile(null);
        } catch(Exception ex) {
            assertThat(ex).isInstanceOf(IllegalArgumentException.class);
        }
    }
    
    @Test
    public void GivenTheFileDoesNotExistThenAFileNotFoundExceptionIsThrown() {
        try {
            AnalysisResultParser.FromFile("src/test/resources/empty_result_file.json");
        } catch(Exception ex){
            assertThat(ex).isInstanceOf(FileNotFoundException.class);
        }
    }
    
    @Test
    public void GivenAValidFileThenTheNumberOfClassesIsSet() {
        AnalysisResult result = ParseFromFile("src/test/resources/valid_result.json");
        
        assertThat(result.getNumberOfClasses()).isEqualTo(10);
    }
    
    @Test
    public void GivenAValidFileThenTheNumberOfMethodsIsSet() {
        AnalysisResult result = ParseFromFile("src/test/resources/valid_result.json");
        
        assertThat(result.getNumberOfMethods()).isEqualTo(123);
    }
    
    @Test
    public void GivenAValidFileThenTheNumberOfLinesIsSet() {
        AnalysisResult result = ParseFromFile("src/test/resources/valid_result.json");
        
        assertThat(result.getNumberOfLines()).isEqualTo(4567);
    }
    
    @Test
    public void GivenAValidFileThenTheLinesOfCodeIsSet() {
        AnalysisResult result = ParseFromFile("src/test/resources/valid_result.json");
        
        assertThat(result.getLinesOfCode()).isEqualTo(4560);
    }
    
    @Test
    public void GivenAValidFileThenTheFileNameIsSet() {
        AnalysisResult result = ParseFromFile("src/test/resources/valid_result.json");
        
        assertThat(result.getFileName()).isEqualTo("tests/file_a.ts");
    }
    
    @Test
    public void GivenAFileWithMultipleResultsThenAllResultsAreReturned() {
        AnalysisResult[] results = ParseAllFromFile("src/test/resources/many_results.json");
        
        assertThat(results.length).isEqualTo(10);
    }
    
    private AnalysisResult ParseFromFile(String path) {
        AnalysisResult result = null;
        
        try {
            result = AnalysisResultParser.FromFile(path)[0];
        } catch(Exception ex) {
            
        }
        
        return result;
    }
    
    private AnalysisResult[] ParseAllFromFile(String path) {
        AnalysisResult[] result = null;
        
        try {
            result = AnalysisResultParser.FromFile(path);
        } catch(Exception ex) {
            
        }
        
        return result;
    }
}