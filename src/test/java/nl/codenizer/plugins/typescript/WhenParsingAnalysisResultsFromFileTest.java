package nl.codenizer.plugins.typescript;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static org.fest.assertions.Assertions.assertThat;

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
        AnalysisResult result = ParseFromFile();
        
        assertThat(result.getNumberOfClasses()).isEqualTo(10);
    }
    
    @Test
    public void GivenAValidFileThenTheNumberOfMethodsIsSet() {
        AnalysisResult result = ParseFromFile();
        
        assertThat(result.getNumberOfMethods()).isEqualTo(123);
    }
    
    @Test
    public void GivenAValidFileThenTheNumberOfLinesIsSet() {
        AnalysisResult result = ParseFromFile();
        
        assertThat(result.getNumberOfLines()).isEqualTo(4567);
    }
    
    @Test
    public void GivenAValidFileThenTheLinesOfCodeIsSet() {
        AnalysisResult result = ParseFromFile();
        
        assertThat(result.getLinesOfCode()).isEqualTo(4560);
    }

    @Test
    public void GivenAValidFileThenTheLinesOfCommentsIsSet() {
        AnalysisResult result = ParseFromFile();

        assertThat(result.getLinesOfComments()).isEqualTo(789);
    }
    
    @Test
    public void GivenAValidFileThenTheFileNameIsSet() {
        AnalysisResult result = ParseFromFile();
        
        assertThat(result.getFileName()).isEqualTo("tests/file_a.ts");
    }
    
    @Test
    public void GivenAFileWithMultipleResultsThenAllResultsAreReturned() {
        AnalysisResult[] results = ParseAllFromFile();
        
        assertThat(results.length).isEqualTo(10);
    }
    
    private AnalysisResult ParseFromFile() {
        AnalysisResult result = null;
        
        try {
            result = AnalysisResultParser.FromFile("src/test/resources/valid_result.json")[0];
        } catch(Exception ignored) {
            
        }
        
        return result;
    }
    
    private AnalysisResult[] ParseAllFromFile() {
        AnalysisResult[] result = null;
        
        try {
            result = AnalysisResultParser.FromFile("src/test/resources/many_results.json");
        } catch(Exception ignored) {
            
        }
        
        return result;
    }
}