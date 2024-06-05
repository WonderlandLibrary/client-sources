package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenLiquids extends WorldGenerator {
   private Block field_150521_a;
   private static final String __OBFID = "CL_00000434";

   public boolean generate(World var1, Random var2, BlockPos var3) {
      if (var1.getBlockState(var3.offsetUp()).getBlock() != Blocks.stone) {
         return false;
      } else if (var1.getBlockState(var3.offsetDown()).getBlock() != Blocks.stone) {
         return false;
      } else if (var1.getBlockState(var3).getBlock().getMaterial() != Material.air && var1.getBlockState(var3).getBlock() != Blocks.stone) {
         return false;
      } else {
         int var4 = 0;
         if (var1.getBlockState(var3.offsetWest()).getBlock() == Blocks.stone) {
            ++var4;
         }

         if (var1.getBlockState(var3.offsetEast()).getBlock() == Blocks.stone) {
            ++var4;
         }

         if (var1.getBlockState(var3.offsetNorth()).getBlock() == Blocks.stone) {
            ++var4;
         }

         if (var1.getBlockState(var3.offsetSouth()).getBlock() == Blocks.stone) {
            ++var4;
         }

         int var5 = 0;
         if (var1.isAirBlock(var3.offsetWest())) {
            ++var5;
         }

         if (var1.isAirBlock(var3.offsetEast())) {
            ++var5;
         }

         if (var1.isAirBlock(var3.offsetNorth())) {
            ++var5;
         }

         if (var1.isAirBlock(var3.offsetSouth())) {
            ++var5;
         }

         if (var4 == 3 && var5 == 1) {
            var1.setBlockState(var3, this.field_150521_a.getDefaultState(), 2);
            var1.func_175637_a(this.field_150521_a, var3, var2);
         }

         return true;
      }
   }

   public WorldGenLiquids(Block var1) {
      this.field_150521_a = var1;
   }
}
