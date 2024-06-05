package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenMegaJungle extends WorldGenHugeTrees {
   private static final String __OBFID = "CL_00000420";

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = this.func_150533_a(var2);
      if (!this.func_175929_a(var1, var2, var3, var4)) {
         return false;
      } else {
         this.func_175930_c(var1, var3.offsetUp(var4), 2);

         int var5;
         for(var5 = var3.getY() + var4 - 2 - var2.nextInt(4); var5 > var3.getY() + var4 / 2; var5 -= 2 + var2.nextInt(4)) {
            float var6 = var2.nextFloat() * 3.1415927F * 2.0F;
            int var7 = var3.getX() + (int)(0.5F + MathHelper.cos(var6) * 4.0F);
            int var8 = var3.getZ() + (int)(0.5F + MathHelper.sin(var6) * 4.0F);

            int var9;
            for(var9 = 0; var9 < 5; ++var9) {
               var7 = var3.getX() + (int)(1.5F + MathHelper.cos(var6) * (float)var9);
               var8 = var3.getZ() + (int)(1.5F + MathHelper.sin(var6) * (float)var9);
               this.func_175905_a(var1, new BlockPos(var7, var5 - 3 + var9 / 2, var8), Blocks.log, this.woodMetadata);
            }

            var9 = 1 + var2.nextInt(2);
            int var10 = var5;

            for(int var11 = var5 - var9; var11 <= var10; ++var11) {
               int var12 = var11 - var10;
               this.func_175928_b(var1, new BlockPos(var7, var11, var8), 1 - var12);
            }
         }

         for(var5 = 0; var5 < var4; ++var5) {
            BlockPos var13 = var3.offsetUp(var5);
            if (this.func_175931_a(var1.getBlockState(var13).getBlock().getMaterial())) {
               this.func_175905_a(var1, var13, Blocks.log, this.woodMetadata);
               if (var5 > 0) {
                  this.func_175932_b(var1, var2, var13.offsetWest(), BlockVine.field_176275_S);
                  this.func_175932_b(var1, var2, var13.offsetNorth(), BlockVine.field_176272_Q);
               }
            }

            if (var5 < var4 - 1) {
               BlockPos var14 = var13.offsetEast();
               if (this.func_175931_a(var1.getBlockState(var14).getBlock().getMaterial())) {
                  this.func_175905_a(var1, var14, Blocks.log, this.woodMetadata);
                  if (var5 > 0) {
                     this.func_175932_b(var1, var2, var14.offsetEast(), BlockVine.field_176271_T);
                     this.func_175932_b(var1, var2, var14.offsetNorth(), BlockVine.field_176272_Q);
                  }
               }

               BlockPos var15 = var13.offsetSouth().offsetEast();
               if (this.func_175931_a(var1.getBlockState(var15).getBlock().getMaterial())) {
                  this.func_175905_a(var1, var15, Blocks.log, this.woodMetadata);
                  if (var5 > 0) {
                     this.func_175932_b(var1, var2, var15.offsetEast(), BlockVine.field_176271_T);
                     this.func_175932_b(var1, var2, var15.offsetSouth(), BlockVine.field_176276_R);
                  }
               }

               BlockPos var16 = var13.offsetSouth();
               if (this.func_175931_a(var1.getBlockState(var16).getBlock().getMaterial())) {
                  this.func_175905_a(var1, var16, Blocks.log, this.woodMetadata);
                  if (var5 > 0) {
                     this.func_175932_b(var1, var2, var16.offsetWest(), BlockVine.field_176275_S);
                     this.func_175932_b(var1, var2, var16.offsetSouth(), BlockVine.field_176276_R);
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean func_175931_a(Material var1) {
      return var1 == Material.air || var1 == Material.leaves;
   }

   public WorldGenMegaJungle(boolean var1, int var2, int var3, int var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   private void func_175930_c(World var1, BlockPos var2, int var3) {
      byte var4 = 2;

      for(int var5 = -var4; var5 <= 0; ++var5) {
         this.func_175925_a(var1, var2.offsetUp(var5), var3 + 1 - var5);
      }

   }

   private void func_175932_b(World var1, Random var2, BlockPos var3, int var4) {
      if (var2.nextInt(3) > 0 && var1.isAirBlock(var3)) {
         this.func_175905_a(var1, var3, Blocks.vine, var4);
      }

   }
}
