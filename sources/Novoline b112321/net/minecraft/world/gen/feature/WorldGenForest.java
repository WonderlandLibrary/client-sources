package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenForest extends WorldGenAbstractTree {
   private static final IBlockState d = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks$EnumType.BIRCH);
   private static final IBlockState c = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks$EnumType.BIRCH).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.FALSE);
   private boolean useExtraRandomHeight;

   public WorldGenForest(boolean var1, boolean var2) {
      super(var1);
      this.useExtraRandomHeight = var2;
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + 5;
      if(this.useExtraRandomHeight) {
         var4 += var2.nextInt(7);
      }

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
}
