package net.minecraft.world.gen.feature;

import net.minecraft.util.BlockPos;

class WorldGenBigTree$FoliageCoordinates extends BlockPos {
   private final int field_178000_b;

   public WorldGenBigTree$FoliageCoordinates(BlockPos var1, int var2) {
      super(var1.getX(), var1.getY(), var1.getZ());
      this.field_178000_b = var2;
   }

   public int func_177999_q() {
      return this.field_178000_b;
   }
}
