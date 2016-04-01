package nl.codenizer.plugins.typescript;

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
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

import com.google.common.collect.Lists;

class TypeScriptSensor implements Sensor {
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
        // Only run if TypeScript files have been detected
        return this.fileSystem.hasFiles(this.fileSystem.predicates().hasLanguage(TypeScript.KEY));
    }
    
    public void analyse(Project project, SensorContext sensorContext) {
        Iterable<File> filesToAnalyze = GetFilesToAnalyze(this.fileSystem);
        
        for(File f: filesToAnalyze) {
            log.info("Will analyze " + f.getName());
        }        
        
        File rootDir = fileSystem.baseDir();

        log.info("Analysing project root in search for TypeScript files: " + rootDir.getAbsolutePath());
        
        try {
            AnalysisResult[] result = this.analysisRunner.Execute(rootDir.getAbsolutePath());

            log.info("Analysis done, saving metrics");
            log.info("Got " + result.length + " metrics");
            
            for(AnalysisResult r: result) {
                saveCoreMetrics(sensorContext, r);
            }
            
            log.info("Metrics saved");
        } catch (Exception ae) {
            log.error("Error while running Analyzer:", ae);
            ae.printStackTrace(System.out);
        }
    }
    
    private void saveCoreMetrics(SensorContext sensorContext, AnalysisResult analysisResult) {
        InputFile file = new DefaultInputFile("someModule", analysisResult.getFileName());
         //log.info("found file: " + file.absolutePath());
         log.info("saving metrics for file " + analysisResult.getFileName());
         
         Resource resource = sensorContext.getResource(file);
         
         if(resource != null) {
             log.info("Found resource with language: " + resource.getLanguage());
         }
         
         log.debug("trying to save CoreMetrics.CLASSES");
        sensorContext.saveMeasure(resource, CoreMetrics.CLASSES, (double)analysisResult.getNumberOfClasses());
        
         log.debug("trying to save CoreMetrics.FUNCTIONS");
        sensorContext.saveMeasure(resource, CoreMetrics.FUNCTIONS, (double)analysisResult.getNumberOfMethods());
        
         log.debug("trying to save CoreMetrics.LINES");
        sensorContext.saveMeasure(resource, CoreMetrics.LINES, (double)analysisResult.getNumberOfLines());
        
        log.debug("trying to save CoreMetrics.NCLOC with value " + analysisResult.getLinesOfCode());
        sensorContext.saveMeasure(resource, CoreMetrics.NCLOC, (double)analysisResult.getLinesOfCode());
        
        log.debug("measures saved");
    }
    
    private Iterable<File> GetFilesToAnalyze(FileSystem fs) {
         Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasLanguage(TypeScript.KEY));
         
         return toFile(files);
    }
    
    private static Iterable<File> toFile(Iterable<InputFile> inputFiles) {
        List<File> files = Lists.newArrayList();
        for (InputFile inputFile : inputFiles) {
        files.add(inputFile.file());
        }
        return files;
    }
    
    public String toString() {
        return "TypeScriptSensor";
    }
}