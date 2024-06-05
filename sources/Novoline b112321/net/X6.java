package net;

import net.minecraft.entity.item.EntityXPOrb;

public class X6 {
   public static int a(int var0) {
      return EntityXPOrb.getXPSplit(var0);
   }

   public static int b(EntityXPOrb var0) {
      return var0.getXpValue();
   }

   public static int a(EntityXPOrb var0) {
      return var0.getTextureByXP();
   }

   public static int a(EntityXPOrb var0, float var1) {
      return var0.getBrightnessForRender(var1);
   }
}
