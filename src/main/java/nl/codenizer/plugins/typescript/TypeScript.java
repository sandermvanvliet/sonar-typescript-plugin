package codenizer.sonarqube.typescript;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

public class TypeScript extends AbstractLanguage {
    
  /**
   * TypeScript key
   */
  public static final String KEY = "typescript";

  /**
   * TypeScript name
   */
  public static final String NAME = "TypeScript";


  /**
   * Key of the file suffix parameter
   */
  public static final String FILE_SUFFIXES_KEY = "sonar.typescript.file.suffixes";

  /**
   * Default TypeScript files knows suffixes
   */
  public static final String DEFAULT_FILE_SUFFIXES = ".ts";

  /**
   * Key of the TypeScript version used for sources
   */
  public static final String SOURCE_VERSION = "sonar.typescript.source";

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

  /**
   * {@inheritDoc}
   *
   * @see org.sonar.api.resources.AbstractLanguage#getFileSuffixes()
   */
  @Override
  public String[] getFileSuffixes() {
    String[] suffixes = filterEmptyStrings(settings.getStringArray(TypeScript.FILE_SUFFIXES_KEY));
    if (suffixes.length == 0) {
      suffixes = StringUtils.split(DEFAULT_FILE_SUFFIXES, ",");
    }
    return suffixes;
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
}