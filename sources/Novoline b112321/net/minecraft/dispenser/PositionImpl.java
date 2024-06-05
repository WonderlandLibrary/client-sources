package net.minecraft.dispenser;

import net.minecraft.dispenser.IPosition;

public class PositionImpl implements IPosition {
   protected final double x;
   protected final double y;
   protected final double z;

   public PositionImpl(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }
}
