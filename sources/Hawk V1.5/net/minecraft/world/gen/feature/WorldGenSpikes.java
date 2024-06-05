package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenSpikes extends WorldGenerator {
   private Block field_150520_a;
   private static final String __OBFID = "CL_00000433";

   public WorldGenSpikes(Block var1) {
      this.field_150520_a = var1;
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      if (var1.isAirBlock(var3) && var1.getBlockState(var3.offsetDown()).getBlock() == this.field_150520_a) {
         int var4 = var2.nextInt(32) + 6;
         int var5 = var2.nextInt(4) + 1;

         int var6;
         int var7;
         int var8;
         int var9;
         for(var6 = var3.getX() - var5; var6 <= var3.getX() + var5; ++var6) {
            for(var7 = var3.getZ() - var5; var7 <= var3.getZ() + var5; ++var7) {
               var8 = var6 - var3.getX();
               var9 = var7 - var3.getZ();
               if (var8 * var8 + var9 * var9 <= var5 * var5 + 1 && var1.getBlockState(new BlockPos(var6, var3.getY() - 1, var7)).getBlock() != this.field_150520_a) {
                  return false;
               }
            }
         }

         for(var6 = var3.getY(); var6 < var3.getY() + var4 && var6 < 256; ++var6) {
            for(var7 = var3.getX() - var5; var7 <= var3.getX() + var5; ++var7) {
               for(var8 = var3.getZ() - var5; var8 <= var3.getZ() + var5; ++var8) {
                  var9 = var7 - var3.getX();
                  int var10 = var8 - var3.getZ();
                  if (var9 * var9 + var10 * var10 <= var5 * var5 + 1) {
                     var1.setBlockState(new BlockPos(var7, var6, var8), Blocks.obsidian.getDefaultState(), 2);
                  }
               }
            }
         }

         EntityEnderCrystal var11 = new EntityEnderCrystal(var1);
         var11.setLocationAndAngles((double)((float)var3.getX() + 0.5F), (double)(var3.getY() + var4), (double)((float)var3.getZ() + 0.5F), var2.nextFloat() * 360.0F, 0.0F);
         var1.spawnEntityInWorld(var11);
         var1.setBlockState(var3.offsetUp(var4), Blocks.bedrock.getDefaultState(), 2);
         return true;
      } else {
         return false;
      }
   }
}
