package nl.codenizer.plugins.typescript;

import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

public class TypeScriptPlugin extends SonarPlugin {
    public TypeScriptPlugin() {
        super();
    }

    public List getExtensions() {
        return Arrays.asList(TypeScriptSensor.class, TypeScript.class, TypeScriptSonarWayProfile.class);
    }
}
