package codenizer.sonarqube.typescript.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileNotFoundException;

import static org.fest.assertions.Assertions.assertThat;
import codenizer.sonarqube.typescript.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

public class WhenStoringAnalysisResultsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void GivenASingleResultThenTheClassesMetricIsStored() {
        SensorContext sensorContext = GivenASensorContext();
        WhenRunningTheSensor(sensorContext);
        
        verify(sensorContext, Mockito.times(1)).saveMeasure(CoreMetrics.CLASSES, 10.0);
    }
    
    @Test
    public void GivenASingleResultThenTheFunctionsMetricIsStored() {
        SensorContext sensorContext = GivenASensorContext();
        WhenRunningTheSensor(sensorContext);
        
        verify(sensorContext, Mockito.times(1)).saveMeasure(CoreMetrics.FUNCTIONS, 123.0);
    }
    
    @Test
    public void GivenASingleResultThenTheLinesMetricIsStored() {
        SensorContext sensorContext = GivenASensorContext();
        WhenRunningTheSensor(sensorContext);
                
        verify(sensorContext, Mockito.times(1)).saveMeasure(CoreMetrics.LINES, 4567.0);
    }
    
    private void WhenRunningTheSensor(SensorContext sensorContext) {
        FileSystem mockedFileSystem = GivenAFileSystem();
        AnalysisRunner analysisRunner = mock(AnalysisRunner.class);
        when(analysisRunner.Execute(any(String.class))).thenReturn(GivenAnArrayWithOneResult());
        TypeScriptSensor sensor = new TypeScriptSensor(mockedFileSystem, analysisRunner);
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
}