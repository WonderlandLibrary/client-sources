package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenTaiga1 extends WorldGenAbstractTree {
   private static final IBlockState b = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks$EnumType.SPRUCE);
   private static final IBlockState c = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks$EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

   public WorldGenTaiga1() {
      super(false);
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(5) + 7;
      int var5 = var4 - var2.nextInt(2) - 3;
      int var6 = var4 - var5;
      int var7 = 1 + var2.nextInt(var6 + 1);
      boolean var8 = true;
      if(var3.getY() >= 1 && var3.getY() + var4 + 1 <= 256) {
         for(int var9 = var3.getY(); var9 <= var3.getY() + 1 + var4; ++var9) {
            int var10 = 1;
            if(var9 - var3.getY() < var5) {
               var10 = 0;
            } else {
               var10 = var7;
            }

            BlockPos$MutableBlockPos var11 = new BlockPos$MutableBlockPos();

            for(int var12 = var3.getX() - var10; var12 <= var3.getX() + var10; ++var12) {
               for(int var13 = var3.getZ() - var10; var13 <= var3.getZ() + var10; ++var13) {
                  if(var9 < 256) {
                     if(!this.func_150523_a(var1.getBlockState(var11.func_181079_c(var12, var9, var13)).getBlock())) {
                        var8 = false;
                     }
                  } else {
                     var8 = false;
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
