package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenMegaPineTree extends WorldGenHugeTrees {
   private boolean field_150542_e;
   private static final String __OBFID = "CL_00000421";

   public WorldGenMegaPineTree(boolean var1, boolean var2) {
      super(var1, 13, 15, BlockPlanks.EnumType.SPRUCE.func_176839_a(), BlockPlanks.EnumType.SPRUCE.func_176839_a());
      this.field_150542_e = var2;
   }

   private void func_175933_b(World var1, BlockPos var2) {
      for(int var3 = -2; var3 <= 2; ++var3) {
         for(int var4 = -2; var4 <= 2; ++var4) {
            if (Math.abs(var3) != 2 || Math.abs(var4) != 2) {
               this.func_175934_c(var1, var2.add(var3, 0, var4));
            }
         }
      }

   }

   private void func_150541_c(World var1, int var2, int var3, int var4, int var5, Random var6) {
      int var7 = var6.nextInt(5) + (this.field_150542_e ? this.baseHeight : 3);
      int var8 = 0;

      for(int var9 = var4 - var7; var9 <= var4; ++var9) {
         int var10 = var4 - var9;
         int var11 = var5 + MathHelper.floor_float((float)var10 / (float)var7 * 3.5F);
         this.func_175925_a(var1, new BlockPos(var2, var9, var3), var11 + (var10 > 0 && var11 == var8 && (var9 & 1) == 0 ? 1 : 0));
         var8 = var11;
      }

   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = this.func_150533_a(var2);
      if (!this.func_175929_a(var1, var2, var3, var4)) {
         return false;
      } else {
         this.func_150541_c(var1, var3.getX(), var3.getZ(), var3.getY() + var4, 0, var2);

         for(int var5 = 0; var5 < var4; ++var5) {
            Block var6 = var1.getBlockState(var3.offsetUp(var5)).getBlock();
            if (var6.getMaterial() == Material.air || var6.getMaterial() == Material.leaves) {
               this.func_175905_a(var1, var3.offsetUp(var5), Blocks.log, this.woodMetadata);
            }

            if (var5 < var4 - 1) {
               var6 = var1.getBlockState(var3.add(1, var5, 0)).getBlock();
               if (var6.getMaterial() == Material.air || var6.getMaterial() == Material.leaves) {
                  this.func_175905_a(var1, var3.add(1, var5, 0), Blocks.log, this.woodMetadata);
               }

               var6 = var1.getBlockState(var3.add(1, var5, 1)).getBlock();
               if (var6.getMaterial() == Material.air || var6.getMaterial() == Material.leaves) {
                  this.func_175905_a(var1, var3.add(1, var5, 1), Blocks.log, this.woodMetadata);
               }

               var6 = var1.getBlockState(var3.add(0, var5, 1)).getBlock();
               if (var6.getMaterial() == Material.air || var6.getMaterial() == Material.leaves) {
                  this.func_175905_a(var1, var3.add(0, var5, 1), Blocks.log, this.woodMetadata);
               }
            }
         }

         return true;
      }
   }

   private void func_175934_c(World var1, BlockPos var2) {
      for(int var3 = 2; var3 >= -3; --var3) {
         BlockPos var4 = var2.offsetUp(var3);
         Block var5 = var1.getBlockState(var4).getBlock();
         if (var5 == Blocks.grass || var5 == Blocks.dirt) {
            this.func_175905_a(var1, var4, Blocks.dirt, BlockDirt.DirtType.PODZOL.getMetadata());
            break;
         }

         if (var5.getMaterial() != Material.air && var3 < 0) {
            break;
         }
      }

   }

   public void func_180711_a(World var1, Random var2, BlockPos var3) {
      this.func_175933_b(var1, var3.offsetWest().offsetNorth());
      this.func_175933_b(var1, var3.offsetEast(2).offsetNorth());
      this.func_175933_b(var1, var3.offsetWest().offsetSouth(2));
      this.func_175933_b(var1, var3.offsetEast(2).offsetSouth(2));

      for(int var4 = 0; var4 < 5; ++var4) {
         int var5 = var2.nextInt(64);
         int var6 = var5 % 8;
         int var7 = var5 / 8;
         if (var6 == 0 || var6 == 7 || var7 == 0 || var7 == 7) {
            this.func_175933_b(var1, var3.add(-3 + var6, 0, -3 + var7));
         }
      }

   }
}
