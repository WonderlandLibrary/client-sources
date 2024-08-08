package com.example.editme.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class LogoutSpot {
   public String serverIp;
   public double z;
   public double y;
   public double x;
   public Entity entity;
   public String name;

   public float getDistanceTo(double var1, double var3, double var5) {
      float var7 = (float)(this.x - var1);
      float var8 = (float)(this.y - var3);
      float var9 = (float)(this.z - var5);
      return MathHelper.func_76129_c(var7 * var7 + var8 * var8 + var9 * var9);
   }

   public LogoutSpot(Entity var1, double var2, double var4, double var6, String var8) {
      this.entity = var1;
      this.x = var2;
      this.y = var4;
      this.z = var6;
      this.name = var8;
      this.serverIp = Minecraft.func_71410_x().func_147104_D().field_78845_b;
   }
}
