package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSpikes extends WorldGenerator {
   private Block baseBlockRequired;

   public WorldGenSpikes(Block var1) {
      this.baseBlockRequired = var1;
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      if(var1.isAirBlock(var3) && var1.getBlockState(var3.down()).getBlock() == this.baseBlockRequired) {
         int var4 = var2.nextInt(32) + 6;
         int var5 = var2.nextInt(4) + 1;
         BlockPos$MutableBlockPos var6 = new BlockPos$MutableBlockPos();

         for(int var7 = var3.getX() - var5; var7 <= var3.getX() + var5; ++var7) {
            for(int var8 = var3.getZ() - var5; var8 <= var3.getZ() + var5; ++var8) {
               int var9 = var7 - var3.getX();
               int var10 = var8 - var3.getZ();
               if(var9 * var9 + var10 * var10 <= var5 * var5 + 1 && var1.getBlockState(var6.func_181079_c(var7, var3.getY() - 1, var8)).getBlock() != this.baseBlockRequired) {
                  return false;
               }
            }
         }

         for(int var12 = var3.getY(); var12 < var3.getY() + var4 && var12 < 256; ++var12) {
            for(int var14 = var3.getX() - var5; var14 <= var3.getX() + var5; ++var14) {
               for(int var15 = var3.getZ() - var5; var15 <= var3.getZ() + var5; ++var15) {
                  int var16 = var14 - var3.getX();
                  int var11 = var15 - var3.getZ();
                  if(var16 * var16 + var11 * var11 <= var5 * var5 + 1) {
                     var1.setBlockState(new BlockPos(var14, var12, var15), Blocks.obsidian.getDefaultState(), 2);
                  }
               }
            }
         }

         EntityEnderCrystal var13 = new EntityEnderCrystal(var1);
         var13.setLocationAndAngles((double)((float)var3.getX() + 0.5F), (double)(var3.getY() + var4), (double)((float)var3.getZ() + 0.5F), var2.nextFloat() * 360.0F, 0.0F);
         var1.spawnEntityInWorld(var13);
         var1.setBlockState(var3.up(var4), Blocks.bedrock.getDefaultState(), 2);
         return true;
      } else {
         return false;
      }
   }
}
