package net;

import java.util.Set;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundRegistry;

public class Q6 {
   public static void b(SoundRegistry var0) {
      var0.clearMap();
   }

   public static boolean b(SoundRegistry var0, Object var1) {
      return var0.containsKey(var1);
   }

   public static Object a(SoundRegistry var0, Object var1) {
      return var0.getObject(var1);
   }

   public static void a(SoundRegistry var0, SoundEventAccessorComposite var1) {
      var0.registerSound(var1);
   }

   public static Set a(SoundRegistry var0) {
      return var0.getKeys();
   }
}
