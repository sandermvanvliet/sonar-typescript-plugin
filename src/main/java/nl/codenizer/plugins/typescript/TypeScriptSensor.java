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
    /**
     * The file system object for the project being analysed.
     */
    private final FileSystem fileSystem;

    /**
     * The logger object for the sensor.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TypeScriptSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Determines whether the sensor should run or not for the given project.
     *
     * @param project the project being analysed
     * @return always true
     */
    public boolean shouldExecuteOnProject(Project project) {
        // this sensor is executed on any type of project
        return true;
    }
    
    /**
     * Analyses project directory in search for IDE metadata configuration files
     * and extracts relevant information.
     *
     * @param project       the project being analysed
     * @param sensorContext the sensor context
     */
    public void analyse(Project project, SensorContext sensorContext) {

        File rootDir = fileSystem.baseDir();

        log.info("Analysing project root in search for TypeScript files: " + rootDir.getAbsolutePath());

        //EclipseAnalyzer analyzer = new EclipseAnalyzer(rootDir);
        //ProjectInfo projectInfo;

        try {
            //projectInfo = analyzer.analyze();

            log.info("Analysis done");
            
            saveMainInfo(sensorContext, null);
        } catch (Exception ae) {
            log.error("Error while running Analyzer", ae);
        }
    }
    
    private void saveMainInfo(SensorContext sensorContext, AnalysisResult analysisResult) {
        sensorContext.saveMeasure(CoreMetrics.CLASSES, (double)analysisResult.getNumberOfClasses());
        
        log.debug("measures saved");
    }

    /**
     * Returns the name of the sensor as it will be used in logs during analysis.
     *
     * @return the name of the sensor
     */
    public String toString() {
        return "TypeScriptSensor";
    }
}