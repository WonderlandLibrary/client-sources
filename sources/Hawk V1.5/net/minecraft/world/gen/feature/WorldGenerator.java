package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenerator {
   private static final String __OBFID = "CL_00000409";
   private final boolean doBlockNotify;

   public abstract boolean generate(World var1, Random var2, BlockPos var3);

   public WorldGenerator(boolean var1) {
      this.doBlockNotify = var1;
   }

   protected void func_175903_a(World var1, BlockPos var2, IBlockState var3) {
      if (this.doBlockNotify) {
         var1.setBlockState(var2, var3, 3);
      } else {
         var1.setBlockState(var2, var3, 2);
      }

   }

   public WorldGenerator() {
      this(false);
   }

   protected void func_175905_a(World var1, BlockPos var2, Block var3, int var4) {
      this.func_175903_a(var1, var2, var3.getStateFromMeta(var4));
   }

   public void func_175904_e() {
   }

   protected void func_175906_a(World var1, BlockPos var2, Block var3) {
      this.func_175905_a(var1, var2, var3, 0);
   }
}
