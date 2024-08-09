package dev.excellent.api.interfaces.client;

import dev.excellent.Excellent;
import dev.excellent.impl.client.theme.Themes;

public interface ITheme {
   default Themes getTheme() {
      return Excellent.getInst().getThemeManager().getTheme();
   }

   default void setTheme(Themes theme) {
      Excellent.getInst().getThemeManager().setTheme(theme);
   }
}
