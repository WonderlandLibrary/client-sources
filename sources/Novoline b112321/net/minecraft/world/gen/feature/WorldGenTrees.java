package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenTrees extends WorldGenAbstractTree {
   private static final IBlockState field_181653_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks$EnumType.OAK);
   private static final IBlockState field_181654_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks$EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);
   private final int minTreeHeight;
   private final boolean d;
   private final IBlockState g;
   private final IBlockState b;

   public WorldGenTrees(boolean var1) {
      this(var1, 4, field_181653_a, field_181654_b, false);
   }

   public WorldGenTrees(boolean var1, int var2, IBlockState var3, IBlockState var4, boolean var5) {
      super(var1);
      this.minTreeHeight = var2;
      this.g = var3;
      this.b = var4;
      this.d = var5;
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + this.minTreeHeight;
      boolean var5 = true;
      if(var3.getY() >= 1 && var3.getY() + var4 + 1 <= 256) {
         for(int var6 = var3.getY(); var6 <= var3.getY() + 1 + var4; ++var6) {
            byte var7 = 1;
            if(var6 == var3.getY()) {
               var7 = 0;
            }

            if(var6 >= var3.getY() + 1 + var4 - 2) {
               var7 = 2;
            }

            BlockPos$MutableBlockPos var8 = new BlockPos$MutableBlockPos();

            for(int var9 = var3.getX() - var7; var9 <= var3.getX() + var7; ++var9) {
               for(int var10 = var3.getZ() - var7; var10 <= var3.getZ() + var7; ++var10) {
                  if(var6 < 256) {
                     if(!this.func_150523_a(var1.getBlockState(var8.func_181079_c(var9, var6, var10)).getBlock())) {
                        var5 = false;
                     }
                  } else {
                     var5 = false;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void func_181652_a(World var1, int var2, BlockPos var3, EnumFacing var4) {
      this.setBlockAndNotifyAdequately(var1, var3, Blocks.cocoa.getDefaultState().withProperty(BlockCocoa.Q, Integer.valueOf(var2)).withProperty(BlockCocoa.FACING, var4));
   }

   private void func_181651_a(World var1, BlockPos var2, PropertyBool var3) {
      this.setBlockAndNotifyAdequately(var1, var2, Blocks.vine.getDefaultState().withProperty(var3, Boolean.TRUE));
   }

   private void func_181650_b(World var1, BlockPos var2, PropertyBool var3) {
      this.func_181651_a(var1, var2, var3);
      int var4 = 4;

      for(var2 = var2.down(); var1.getBlockState(var2).getBlock().getMaterial() == Material.air; --var4) {
         this.func_181651_a(var1, var2, var3);
         var2 = var2.down();
      }

   }
}
