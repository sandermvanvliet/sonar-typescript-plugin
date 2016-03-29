package codenizer.sonarqube.typescript;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;

public class TypeScriptPlugin extends SonarPlugin {
    public TypeScriptPlugin() {
        super();
    }
    
    public List getExtensions() {
        return Arrays.asList(TypeScriptSensor.class);
    }
}
