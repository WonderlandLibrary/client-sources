package net;

import java.util.Set;
import net.minecraft.client.renderer.chunk.SetVisibility;

public class ahm {
   public static void a(SetVisibility var0, boolean var1) {
      var0.setAllVisible(var1);
   }

   public static void a(SetVisibility var0, Set var1) {
      var0.setManyVisible(var1);
   }
}
