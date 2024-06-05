package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenCanopyTree extends WorldGenAbstractTree {
   private static final IBlockState field_181640_a = Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks$EnumType.DARK_OAK);
   private static final IBlockState field_181641_b = Blocks.leaves2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks$EnumType.DARK_OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

   public WorldGenCanopyTree(boolean var1) {
      super(var1);
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + var2.nextInt(2) + 6;
      int var5 = var3.getX();
      int var6 = var3.getY();
      int var7 = var3.getZ();
      if(var6 >= 1 && var6 + var4 + 1 < 256) {
         BlockPos var8 = var3.down();
         Block var9 = var1.getBlockState(var8).getBlock();
         if(var9 != Blocks.grass && var9 != Blocks.dirt) {
            return false;
         } else {
            if(this.func_181638_a(var1, var3, var4)) {
               this.func_175921_a(var1, var8);
               this.func_175921_a(var1, var8.east());
               this.func_175921_a(var1, var8.south());
               this.func_175921_a(var1, var8.south().east());
               EnumFacing var10 = EnumFacing$Plane.HORIZONTAL.random(var2);
               int var11 = var4 - var2.nextInt(4);
               int var12 = 2 - var2.nextInt(3);
               int var13 = var5;
               int var14 = var7;
               int var15 = var6 + var4 - 1;

               for(int var16 = 0; var16 < var4; ++var16) {
                  if(var16 >= var11) {
                     var13 += var10.getFrontOffsetX();
                     var14 += var10.getFrontOffsetZ();
                     --var12;
                  }

                  int var17 = var6 + var16;
                  BlockPos var18 = new BlockPos(var13, var17, var14);
                  Material var19 = var1.getBlockState(var18).getBlock().getMaterial();
                  if(var19 == Material.air || var19 == Material.leaves) {
                     this.func_181639_b(var1, var18);
                     this.func_181639_b(var1, var18.east());
                     this.func_181639_b(var1, var18.south());
                     this.func_181639_b(var1, var18.east().south());
                  }
               }

               byte var20 = -2;
               int var21 = -2;

               while(true) {
                  byte var22 = -1;
                  this.func_150526_a(var1, var13 + var20, var15 + var22, var14 + var21);
                  this.func_150526_a(var1, 1 + var13 - var20, var15 + var22, var14 + var21);
                  this.func_150526_a(var1, var13 + var20, var15 + var22, 1 + var14 - var21);
                  this.func_150526_a(var1, 1 + var13 - var20, var15 + var22, 1 + var14 - var21);
                  if((var20 > -2 || var21 > -1) && (var20 != -1 || var21 != -2)) {
                     var22 = 1;
                     this.func_150526_a(var1, var13 + var20, var15 + var22, var14 + var21);
                     this.func_150526_a(var1, 1 + var13 - var20, var15 + var22, var14 + var21);
                     this.func_150526_a(var1, var13 + var20, var15 + var22, 1 + var14 - var21);
                     this.func_150526_a(var1, 1 + var13 - var20, var15 + var22, 1 + var14 - var21);
                  }

                  ++var21;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean func_181638_a(World var1, BlockPos var2, int var3) {
      int var4 = var2.getX();
      int var5 = var2.getY();
      int var6 = var2.getZ();
      BlockPos$MutableBlockPos var7 = new BlockPos$MutableBlockPos();

      for(int var8 = 0; var8 <= var3 + 1; ++var8) {
         byte var9 = 1;
         var9 = 0;
         if(var8 >= var3 - 1) {
            var9 = 2;
         }

         for(int var10 = -var9; var10 <= var9; ++var10) {
            for(int var11 = -var9; var11 <= var9; ++var11) {
               if(!this.func_150523_a(var1.getBlockState(var7.func_181079_c(var4 + var10, var5 + var8, var6 + var11)).getBlock())) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private void func_181639_b(World var1, BlockPos var2) {
      if(this.func_150523_a(var1.getBlockState(var2).getBlock())) {
         this.setBlockAndNotifyAdequately(var1, var2, field_181640_a);
      }

   }

   private void func_150526_a(World var1, int var2, int var3, int var4) {
      BlockPos var5 = new BlockPos(var2, var3, var4);
      Block var6 = var1.getBlockState(var5).getBlock();
      if(var6.getMaterial() == Material.air) {
         this.setBlockAndNotifyAdequately(var1, var5, field_181641_b);
      }

   }
}
