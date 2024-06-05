package net;

import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.Atmosphere;

public class EF {
   public static boolean b(Atmosphere var0) {
      return var0.isEnabled();
   }

   public static StringProperty a(Atmosphere var0) {
      return var0.getWeather_mode();
   }
}
