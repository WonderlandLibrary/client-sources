package net.minecraft.client.renderer;

import net.minecraft.util.BlockPos;

public class DestroyBlockProgress {
   private int partialBlockProgress;
   private final int miningPlayerEntId;
   private int createdAtCloudUpdateTick;
   private final BlockPos field_180247_b;
   private static final String __OBFID = "CL_00001427";

   public int getCreationCloudUpdateTick() {
      return this.createdAtCloudUpdateTick;
   }

   public void setPartialBlockDamage(int var1) {
      if (var1 > 10) {
         var1 = 10;
      }

      this.partialBlockProgress = var1;
   }

   public void setCloudUpdateTick(int var1) {
      this.createdAtCloudUpdateTick = var1;
   }

   public DestroyBlockProgress(int var1, BlockPos var2) {
      this.miningPlayerEntId = var1;
      this.field_180247_b = var2;
   }

   public BlockPos func_180246_b() {
      return this.field_180247_b;
   }

   public int getPartialBlockDamage() {
      return this.partialBlockProgress;
   }
}
