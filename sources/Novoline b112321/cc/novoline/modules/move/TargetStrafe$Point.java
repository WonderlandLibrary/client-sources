package cc.novoline.modules.move;

import net.minecraft.client.Minecraft;

public class TargetStrafe$Point {
   public double x;
   public double z;
   public int poscount;

   public TargetStrafe$Point(double var1, double var3, int var5) {
      this.x = var1;
      this.z = var3;
      this.poscount = var5;
   }

   public double getDistanceToPlayer() {
      double var1 = Math.abs(Minecraft.getInstance().player.posX - this.x);
      double var3 = Math.abs(Minecraft.getInstance().player.posZ - this.z);
      return Math.sqrt(var1 * var1 + var3 * var3);
   }
}
