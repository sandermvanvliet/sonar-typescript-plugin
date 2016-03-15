package codenizer.sonarqube.typescript;

import java.util.Arrays;
import java.util.List;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

public class TypeScriptSensor implements Sensor {
    private final FileSystem fileSystem;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TypeScriptSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }
    
    public boolean shouldExecuteOnProject(Project project) {
        // this sensor is executed on any type of project
        return true;
    }
    
    public void analyse(Project project, SensorContext sensorContext) {
        File rootDir = fileSystem.baseDir();

        log.info("Analysing project root in search for TypeScript files: " + rootDir.getAbsolutePath());

        AnalysisRunner runner = new AnalysisRunner(rootDir.getAbsolutePath());
        
        try {
            AnalysisResult result = runner.Execute();

            log.info("Analysis done");
            
            saveMainInfo(sensorContext, result);
        } catch (Exception ae) {
            log.error("Error while running Analyzer", ae);
        }
    }
    
    private void saveMainInfo(SensorContext sensorContext, AnalysisResult analysisResult) {
        sensorContext.saveMeasure(CoreMetrics.CLASSES, (double)analysisResult.getNumberOfClasses());
        sensorContext.saveMeasure(CoreMetrics.FUNCTIONS, (double)analysisResult.getNumberOfMethods());
        sensorContext.saveMeasure(CoreMetrics.LINES, (double)analysisResult.getNumberOfLines());
        
        log.debug("measures saved");
    }
    
    public String toString() {
        return "TypeScriptSensor";
    }
}