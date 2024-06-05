package net;

import net.minecraft.world.EnumDifficulty;

public class G0 {
   public static EnumDifficulty a(int var0) {
      return EnumDifficulty.getDifficultyEnum(var0);
   }

   public static int b(EnumDifficulty var0) {
      return var0.getDifficultyId();
   }

   public static String a(EnumDifficulty var0) {
      return var0.getDifficultyResourceKey();
   }
}
