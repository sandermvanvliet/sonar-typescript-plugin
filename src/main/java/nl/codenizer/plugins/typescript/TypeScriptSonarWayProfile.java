package nl.codenizer.plugins.typescript;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;

/**
 * Replacement for org.sonar.plugins.squid.SonarWayProfile
 */
class TypeScriptSonarWayProfile extends ProfileDefinition {

  public TypeScriptSonarWayProfile() {
  }

  @Override
  public RulesProfile createProfile(ValidationMessages messages) {
      return RulesProfile.create("Sonar way", TypeScript.KEY);
  }
}