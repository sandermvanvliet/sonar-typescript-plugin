package nl.codenizer.plugins.typescript;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AnalysisRunnerImpl implements AnalysisRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public AnalysisResult[] Execute(String projectRootDirectory) {
        AnalysisResult[] result = new AnalysisResult[0];
        
       try {
           // 1) Set working directory to projectRootDirectory
           // 2) Call analyzer.js
           // 3) Parse result file
           
           log.info("Initializing commands");
           
           String[] commands = new String[] {
               "tsa",
               "**/*.ts"    /* we can specify this glob because we're already in the source directory */
           };
           
           log.info("Starting tsa...");
           
           Process p = Runtime.getRuntime().exec(commands, null /* inherit env */, new File(projectRootDirectory));
           
           int exitCode = p.waitFor();
           
           log.info("exitCode: " + exitCode);
           
           if(exitCode != 0) {
               log.error("Running analyzer failed with exit code " + exitCode);
           }
           
           // Look for file in current directory
           String pathToResultFile = "ts-analysis-results.json";
           
           result = AnalysisResultParser.FromFile(pathToResultFile);
       }
       catch(Exception ex) {
           log.error(ex.getMessage());
       }
    
        return result;
    }
}