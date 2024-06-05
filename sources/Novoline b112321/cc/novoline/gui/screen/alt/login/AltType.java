package cc.novoline.gui.screen.alt.login;

import org.apache.commons.lang3.StringUtils;

public enum AltType {
   CRACKED,
   PREMIUM;

   private final String capitalized = StringUtils.capitalize(this.name().toLowerCase());

   public String getCapitalized() {
      return this.capitalized;
   }
}
