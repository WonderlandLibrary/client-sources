package net;

import cc.novoline.modules.visual.Trail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

class xf {
   private float a;
   private float b;
   private Vec3 e;
   private EntityPlayer c;
   final Trail d;

   public xf(Trail var1, EntityPlayer var2) {
      this.d = var1;
      this.e = new Vec3(var2.prevPosX, var2.prevPosY, var2.prevPosZ);
      this.b = 0.0F;
      this.a = 125.0F;
      this.c = var2;
   }

   static Vec3 b(xf var0) {
      return var0.e;
   }

   static float a(xf var0) {
      return var0.a;
   }

   static float a(xf var0, float var1) {
      return var0.a = var1;
   }
}
