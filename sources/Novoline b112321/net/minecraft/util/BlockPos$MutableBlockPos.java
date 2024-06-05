package net.minecraft.util;

import net.minecraft.util.BlockPos;

public final class BlockPos$MutableBlockPos extends BlockPos {
   private int x;
   private int y;
   private int z;

   public BlockPos$MutableBlockPos() {
      this(0, 0, 0);
   }

   public BlockPos$MutableBlockPos(int var1, int var2, int var3) {
      super(0, 0, 0);
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public BlockPos$MutableBlockPos func_181079_c(int var1, int var2, int var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
      return this;
   }

   static int access$002(BlockPos$MutableBlockPos var0, int var1) {
      return var0.x = var1;
   }

   static int access$102(BlockPos$MutableBlockPos var0, int var1) {
      return var0.y = var1;
   }

   static int access$202(BlockPos$MutableBlockPos var0, int var1) {
      return var0.z = var1;
   }
}
