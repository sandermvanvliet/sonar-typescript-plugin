package nl.codenizer.plugins.typescript;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import org.mockito.Mockito;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.resources.Project;

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