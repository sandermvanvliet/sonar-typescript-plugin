package nl.codenizer.plugins.typescript;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
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

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

class TypeScriptSensor implements Sensor {
    private final FileSystem fileSystem;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private AnalysisRunner analysisRunner;

    public TypeScriptSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        this.analysisRunner = new AnalysisRunnerImpl();
    }

    private static Iterable<File> toFile(Iterable<InputFile> inputFiles) {
        List<File> files = Lists.newArrayList();
        for (InputFile inputFile : inputFiles) {
            files.add(inputFile.file());
        }
        return files;
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

        File rootDir = fileSystem.baseDir();

        try {
            AnalysisResult[] result = this.analysisRunner.Execute(rootDir.getAbsolutePath());

            log.debug("Analysis done, saving metrics");

            for (AnalysisResult r : result) {
                File match = Iterables.find(filesToAnalyze, GetPredicateFor(r.getFileName()));

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

    private Predicate<File> GetPredicateFor(final String fileName) {
        return new Predicate<File>() {
            public boolean apply(@Nullable File file) {
                return file.getName() == fileName;
            }
        };
    }

    private void saveCoreMetrics(SensorContext sensorContext, AnalysisResult analysisResult, File match) {
        InputFile file = new DefaultInputFile("someModule", match.getName());

        log.debug("saving metrics for file " + analysisResult.getFileName());

        Resource resource = sensorContext.getResource(file);

        log.debug("trying to save CoreMetrics.CLASSES");
        sensorContext.saveMeasure(resource, CoreMetrics.CLASSES, (double) analysisResult.getNumberOfClasses());

        log.debug("trying to save CoreMetrics.FUNCTIONS");
        sensorContext.saveMeasure(resource, CoreMetrics.FUNCTIONS, (double) analysisResult.getNumberOfMethods());

        log.debug("trying to save CoreMetrics.LINES");
        sensorContext.saveMeasure(resource, CoreMetrics.LINES, (double) analysisResult.getNumberOfLines());

        log.debug("trying to save CoreMetrics.NCLOC with value " + analysisResult.getLinesOfCode());
        sensorContext.saveMeasure(resource, CoreMetrics.NCLOC, (double) analysisResult.getLinesOfCode());

        log.debug("measures saved");
    }

    private Iterable<File> GetFilesToAnalyze(FileSystem fs) {
        Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasLanguage(TypeScript.KEY));

        return toFile(files);
    }

    public String toString() {
        return "TypeScriptSensor";
    }
}