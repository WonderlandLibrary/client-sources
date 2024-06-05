package net;

import net.minecraft.client.audio.SoundCategory;

public class ye {
   public static String b(SoundCategory var0) {
      return var0.getCategoryName();
   }

   public static int a(SoundCategory var0) {
      return var0.getCategoryId();
   }

   public static SoundCategory a(String var0) {
      return SoundCategory.getCategory(var0);
   }
}
