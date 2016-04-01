package nl.codenizer.plugins.typescript;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.SonarPlugin;

class TypeScriptPlugin extends SonarPlugin {
    public TypeScriptPlugin() {
        super();
    }
    
    public List getExtensions() {
        return Arrays.asList(TypeScriptSensor.class, TypeScript.class, TypeScriptSonarWayProfile.class);
    }
}
