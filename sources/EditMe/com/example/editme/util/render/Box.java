package com.example.editme.util.render;

public final class Box {
   public final double minZ;
   public final double maxY;
   public final double minY;
   public final double maxX;
   public final double maxZ;
   public final double minX;

   public Box(double var1, double var3, double var5, double var7, double var9, double var11) {
      this.minX = var1;
      this.minY = var3;
      this.minZ = var5;
      this.maxX = var7;
      this.maxY = var9;
      this.maxZ = var11;
   }
}
