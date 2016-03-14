package codenizer.sonarqube.typescript.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import codenizer.sonarqube.typescript.*;

public class WhenParsingAnalysisResultsFromFileTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
  
    @Test
    public void GivenAnEmptyPathThenExceptionIsThrown() {
        thrown.expect(IllegalArgumentException.class);
        AnalysisResultParser.FromFile(null);
    }
}