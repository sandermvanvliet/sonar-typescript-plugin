package nl.codenizer.plugins.typescript;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.resources.Project;

import java.io.File;
import java.util.Iterator;

import static org.mockito.Mockito.*;

public class WhenStoringAnalysisResultsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    //@Test
    public void GivenASingleResultThenTheClassesMetricIsStored() {
        SensorContext sensorContext = GivenASensorContext();
        WhenRunningTheSensor(sensorContext);
        
        verify(sensorContext, Mockito.times(1)).saveMeasure(any(InputFile.class), eq(CoreMetrics.CLASSES), eq(10.0));
    }
    
    //@Test
    public void GivenASingleResultThenTheFunctionsMetricIsStored() {
        SensorContext sensorContext = GivenASensorContext();
        WhenRunningTheSensor(sensorContext);
        
        verify(sensorContext, Mockito.times(1)).saveMeasure(any(InputFile.class), eq(CoreMetrics.FUNCTIONS), eq(123.0));
    }
    
    //@Test
    public void GivenASingleResultThenTheLinesMetricIsStored() {
        SensorContext sensorContext = GivenASensorContext();
        WhenRunningTheSensor(sensorContext);
                
        verify(sensorContext, Mockito.times(1)).saveMeasure(any(InputFile.class), eq(CoreMetrics.LINES), eq(4567.0));
    }
    
    //@Test
    public void GivenManyResultsThenAllResultMetricsAreStored() {
        SensorContext sensorContext = GivenASensorContext();
        WhenRunningTheSensorForMultipleFiles(sensorContext);
        
        verify(sensorContext, Mockito.times(10)).saveMeasure(any(InputFile.class), eq(CoreMetrics.CLASSES), eq(10.0));
    }
    
    private void WhenRunningTheSensor(SensorContext sensorContext) {
        FileSystem mockedFileSystem = GivenAFileSystem();
        AnalysisRunner analysisRunner = mock(AnalysisRunner.class);
        when(analysisRunner.Execute(any(String.class))).thenReturn(GivenAnArrayWithOneResult());
        TypeScriptSensor sensor = new TypeScriptSensor(mockedFileSystem);
        sensor.setAnalysisRunner(analysisRunner);
        Project project = new Project("TypeScriptProject");
        
        sensor.analyse(project, sensorContext);
    }
    
    private void WhenRunningTheSensorForMultipleFiles(SensorContext sensorContext) {
        FileSystem mockedFileSystem = GivenAFileSystem();
        AnalysisRunner analysisRunner = mock(AnalysisRunner.class);
        when(analysisRunner.Execute(any(String.class))).thenReturn(GivenAnArrayWithMultipleResults());
        TypeScriptSensor sensor = new TypeScriptSensor(mockedFileSystem);
        sensor.setAnalysisRunner(analysisRunner);
        Project project = new Project("TypeScriptProject");
        
        sensor.analyse(project, sensorContext);
    }
    
    private SensorContext GivenASensorContext() {
        return mock(SensorContext.class);
    }
    
    private FileSystem GivenAFileSystem() {
        FileSystem mocked = mock(FileSystem.class);

        when(mocked.inputFiles(Matchers.any(FilePredicate.class))).thenReturn(new Iterable<InputFile>() {
            public Iterator<InputFile> iterator() {
                return new Iterator<InputFile>() {
                    public boolean hasNext() {
                        return true;
                    }

                    public InputFile next() {
                        return new DefaultInputFile("someModule", "src/test/resources/valid_result.json");
                    }

                    public void remove() {

                    }
                };
            }
        });
        FilePredicates mockedPredicates = mock(FilePredicates.class);
        FilePredicate mockedFilePredicate = mock(FilePredicate.class);
        when(mockedFilePredicate.apply(Matchers.any(InputFile.class))).thenReturn(true);
        when(mockedPredicates.hasLanguage(Matchers.anyString())).thenReturn(mockedFilePredicate);
        when(mocked.predicates()).thenReturn(mockedPredicates);
        when(mocked.baseDir()).thenReturn(new File("./"));
        
        return mocked;
    }
    
    private AnalysisResult[] GivenAnArrayWithOneResult() {
        try {
            return AnalysisResultParser.FromFile("src/test/resources/valid_result.json");
        } catch(Exception ex) {
            return null;
        }
    }
    
    private AnalysisResult[] GivenAnArrayWithMultipleResults() {
        try {
            return AnalysisResultParser.FromFile("src/test/resources/many_results.json");
        } catch(Exception ex) {
            return null;
        }
    }
}