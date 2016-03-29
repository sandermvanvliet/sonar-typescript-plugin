package codenizer.sonarqube.typescript;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;

/**
 * Replacement for org.sonar.plugins.squid.SonarWayProfile
 */
public class TypeScriptSonarWayProfile extends ProfileDefinition {

  private final RuleFinder ruleFinder;

  public TypeScriptSonarWayProfile(RuleFinder ruleFinder) {
    this.ruleFinder = ruleFinder;
  }

  @Override
  public RulesProfile createProfile(ValidationMessages messages) {
      return RulesProfile.create("Sonar way", TypeScript.KEY);
  }
}