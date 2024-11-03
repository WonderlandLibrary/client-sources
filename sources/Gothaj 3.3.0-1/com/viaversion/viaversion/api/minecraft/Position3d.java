package com.viaversion.viaversion.api.minecraft;

public class Position3d {
   private final double x;
   private final double y;
   private final double z;

   public Position3d(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public double x() {
      return this.x;
   }

   public double y() {
      return this.y;
   }

   public double z() {
      return this.z;
   }
}
