package net;

import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.visual.Camera;
import com.google.gson.JsonObject;

public class Mg {
   public static JsonObject c(Camera var0) {
      return var0.getJsonObject();
   }

   public static boolean b(Camera var0) {
      return var0.isEnabled();
   }

   public static BooleanProperty e(Camera var0) {
      return var0.getNoHurtCam();
   }

   public static BooleanProperty a(Camera var0) {
      return var0.getViewClip();
   }

   public static boolean d(Camera var0) {
      return var0.f();
   }
}
