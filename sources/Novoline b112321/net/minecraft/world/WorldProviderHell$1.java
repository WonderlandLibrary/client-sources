package net.minecraft.world;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.border.WorldBorder;

class WorldProviderHell$1 extends WorldBorder {
   final WorldProviderHell this$0;

   WorldProviderHell$1(WorldProviderHell var1) {
      this.this$0 = var1;
   }

   public double getCenterX() {
      return super.getCenterX() / 8.0D;
   }

   public double getCenterZ() {
      return super.getCenterZ() / 8.0D;
   }
}
