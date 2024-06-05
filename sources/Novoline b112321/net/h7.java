package net;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;

public class h7 {
   public static void a(DumperOptions var0, FlowStyle var1) {
      var0.setDefaultFlowStyle(var1);
   }

   public static void a(DumperOptions var0, boolean var1) {
      var0.setPrettyFlow(var1);
   }

   public static void a(DumperOptions var0, int var1) {
      var0.setIndent(var1);
   }
}
