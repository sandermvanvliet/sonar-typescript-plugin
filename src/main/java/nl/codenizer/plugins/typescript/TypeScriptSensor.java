package codenizer.sonarqube.typescript;

import java.util.Arrays;
import java.util.List;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

public class TypeScriptSensor implements Sensor {
    private final FileSystem fileSystem;
    private AnalysisRunner analysisRunner;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TypeScriptSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        this.analysisRunner = new AnalysisRunnerImpl();
    }
    
    public void setAnalysisRunner(AnalysisRunner runner) {
        this.analysisRunner = runner;
    }
    
    public boolean shouldExecuteOnProject(Project project) {
        // this sensor is executed on any type of project
        return true;
    }
    
    public void analyse(Project project, SensorContext sensorContext) {
        File rootDir = fileSystem.baseDir();

        log.info("Analysing project root in search for TypeScript files: " + rootDir.getAbsolutePath());
        
        try {
            AnalysisResult[] result = this.analysisRunner.Execute(rootDir.getAbsolutePath());

            log.info("Analysis done, saving metrics");
            log.info("Got " + result.length + " metrics");
            
            for(AnalysisResult r: result) {
                saveCoreMetrics(sensorContext, r, rootDir.getAbsolutePath());
            }
            
            log.info("Metrics saved");
        } catch (Exception ae) {
            log.error("Error while running Analyzer: " + ae.getMessage(), ae);
        }
    }
    
    private void saveCoreMetrics(SensorContext sensorContext, AnalysisResult analysisResult, String rootDir) {
        InputFile file = new DefaultInputFile("someModule", analysisResult.getFileName());
         //log.info("found file: " + file.absolutePath());
         log.info("saving metrics for file " + analysisResult.getFileName());
         
         Resource resource = sensorContext.getResource(file);
         
         if(resource != null) {
             log.info("Found resource with language: " + resource.getLanguage());
         }
         
         log.info("trying to save CoreMetrics.CLASSES");
        sensorContext.saveMeasure(resource, CoreMetrics.CLASSES, (double)analysisResult.getNumberOfClasses());
        
         log.info("trying to save CoreMetrics.FUNCTIONS");
        sensorContext.saveMeasure(resource, CoreMetrics.FUNCTIONS, (double)analysisResult.getNumberOfMethods());
        
         log.info("trying to save CoreMetrics.LINES");
        sensorContext.saveMeasure(resource, CoreMetrics.LINES, (double)analysisResult.getNumberOfLines());
        
        log.info("trying to save CoreMetrics.NCLOC");
        sensorContext.saveMeasure(resource, CoreMetrics.NCLOC, (double)analysisResult.getNumberOfLines());
        
        log.debug("measures saved");
    }
    
    public String toString() {
        return "TypeScriptSensor";
    }
}