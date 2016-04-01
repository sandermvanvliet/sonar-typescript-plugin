package nl.codenizer.plugins.typescript;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

import java.util.List;

class TypeScript extends AbstractLanguage {

    /**
     * TypeScript key
     */
    public static final String KEY = "typescript";
    /**
     * Key of the TypeScript version used for sources
     */
    public static final String SOURCE_VERSION = "sonar.typescript.source";
    /**
     * TypeScript name
     */
    private static final String NAME = "TypeScript";
    /**
     * Key of the file suffix parameter
     */
    private static final String FILE_SUFFIXES_KEY = "sonar.typescript.file.suffixes";
    /**
     * Default TypeScript files knows suffixes
     */
    private static final String DEFAULT_FILE_SUFFIXES = ".ts";
    /**
     * Settings of the plugin.
     */
    private final Settings settings;

    /**
     * Default constructor
     */
    public TypeScript(Settings settings) {
        super(KEY, NAME);
        this.settings = settings;
    }

    private static String[] filterEmptyStrings(String[] stringArray) {
        List<String> nonEmptyStrings = Lists.newArrayList();
        for (String string : stringArray) {
            if (StringUtils.isNotBlank(string.trim())) {
                nonEmptyStrings.add(string.trim());
            }
        }
        return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.sonar.api.resources.AbstractLanguage#getFileSuffixes()
     */
    public String[] getFileSuffixes() {
        String[] suffixes = filterEmptyStrings(settings.getStringArray(TypeScript.FILE_SUFFIXES_KEY));
        if (suffixes.length == 0) {
            suffixes = StringUtils.split(DEFAULT_FILE_SUFFIXES, ",");
        }
        return suffixes;
    }
}