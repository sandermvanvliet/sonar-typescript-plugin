package nl.codenizer.plugins.typescript;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.resources.Project;

import java.io.File;
import java.util.NoSuchElementException;

public class TypeScriptSensor implements Sensor {
    private final FileSystem fileSystem;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private AnalysisRunner analysisRunner;

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
        File rootDir = fileSystem.baseDir();

        try {
            AnalysisResult[] result = this.analysisRunner.Execute(rootDir.getAbsolutePath());

            log.debug("Analysis done, saving metrics");

            for (AnalysisResult r : result) {
                log.debug("Attempting to find " + r.getFileName());
                InputFile match;

                try {
                    match = this.fileSystem.inputFile(fileSystem.predicates().and(
                            fileSystem.predicates().hasRelativePath(r.getFileName()),
                            fileSystem.predicates().hasType(InputFile.Type.MAIN)));
                }
                catch(NoSuchElementException nsx) {
                    log.info("Skipping " + r.getFileName() + " because it is not included in the analysis for this project");
                    continue;
                }

                if(match != null) {
                    saveCoreMetrics(sensorContext, r, match);
                } else {
                    log.info("Skipping " + r.getFileName() + " because it is not included in the analysis for this project");
                }
            }

            log.debug("Metrics saved");
        } catch (Exception ae) {
            log.error("Error while running Analyzer:", ae);
            ae.printStackTrace(System.out);
        }
    }

    private void saveCoreMetrics(SensorContext sensorContext, AnalysisResult analysisResult, InputFile file) {
        log.debug("saving metrics for file " + analysisResult.getFileName());

        sensorContext.saveMeasure(file, CoreMetrics.CLASSES, (double) analysisResult.getNumberOfClasses());

        sensorContext.saveMeasure(file, CoreMetrics.FUNCTIONS, (double) analysisResult.getNumberOfMethods());

        sensorContext.saveMeasure(file, CoreMetrics.NCLOC, (double) analysisResult.getLinesOfCode());

        sensorContext.saveMeasure(file, CoreMetrics.COMMENT_LINES, (double) analysisResult.getLinesOfComments());

        log.debug("Metrics saved");
    }

    public String toString() {
        return "TypeScriptSensor";
    }
}