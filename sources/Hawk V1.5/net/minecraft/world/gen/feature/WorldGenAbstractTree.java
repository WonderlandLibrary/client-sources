package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenAbstractTree extends WorldGenerator {
   private static final String __OBFID = "CL_00000399";

   protected void func_175921_a(World var1, BlockPos var2) {
      if (var1.getBlockState(var2).getBlock() != Blocks.dirt) {
         this.func_175903_a(var1, var2, Blocks.dirt.getDefaultState());
      }

   }

   public WorldGenAbstractTree(boolean var1) {
      super(var1);
   }

   public void func_180711_a(World var1, Random var2, BlockPos var3) {
   }

   protected boolean func_150523_a(Block var1) {
      return var1.getMaterial() == Material.air || var1.getMaterial() == Material.leaves || var1 == Blocks.grass || var1 == Blocks.dirt || var1 == Blocks.log || var1 == Blocks.log2 || var1 == Blocks.sapling || var1 == Blocks.vine;
   }
}
