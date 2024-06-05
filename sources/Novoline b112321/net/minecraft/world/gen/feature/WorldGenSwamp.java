package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenSwamp extends WorldGenAbstractTree {
   private static final IBlockState c = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks$EnumType.OAK);
   private static final IBlockState b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks$EnumType.OAK).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.FALSE);

   public WorldGenSwamp() {
      super(false);
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4;
      for(var4 = var2.nextInt(4) + 5; var1.getBlockState(var3.down()).getBlock().getMaterial() == Material.water; var3 = var3.down()) {
         ;
      }

      boolean var5 = true;
      if(var3.getY() >= 1 && var3.getY() + var4 + 1 <= 256) {
         for(int var6 = var3.getY(); var6 <= var3.getY() + 1 + var4; ++var6) {
            byte var7 = 1;
            if(var6 == var3.getY()) {
               var7 = 0;
            }

            if(var6 >= var3.getY() + 1 + var4 - 2) {
               var7 = 3;
            }

            BlockPos$MutableBlockPos var8 = new BlockPos$MutableBlockPos();

            for(int var9 = var3.getX() - var7; var9 <= var3.getX() + var7; ++var9) {
               for(int var10 = var3.getZ() - var7; var10 <= var3.getZ() + var7; ++var10) {
                  if(var6 < 256) {
                     Block var11 = var1.getBlockState(var8.func_181079_c(var9, var6, var10)).getBlock();
                     if(var11.getMaterial() != Material.air && var11.getMaterial() != Material.leaves) {
                        if(var11 != Blocks.water && var11 != Blocks.flowing_water) {
                           var5 = false;
                        } else if(var6 > var3.getY()) {
                           var5 = false;
                        }
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

   private void func_181647_a(World var1, BlockPos var2, PropertyBool var3) {
      IBlockState var4 = Blocks.vine.getDefaultState().withProperty(var3, Boolean.TRUE);
      this.setBlockAndNotifyAdequately(var1, var2, var4);
      int var5 = 4;

      for(var2 = var2.down(); var1.getBlockState(var2).getBlock().getMaterial() == Material.air; --var5) {
         this.setBlockAndNotifyAdequately(var1, var2, var4);
         var2 = var2.down();
      }

   }
}
