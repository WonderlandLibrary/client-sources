package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBigMushroom extends WorldGenerator {
   private Block mushroomType;

   public WorldGenBigMushroom(Block var1) {
      super(true);
      this.mushroomType = var1;
   }

   public WorldGenBigMushroom() {
      super(false);
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      if(this.mushroomType == null) {
         this.mushroomType = var2.nextBoolean()?Blocks.brown_mushroom_block:Blocks.red_mushroom_block;
      }

      int var4 = var2.nextInt(3) + 4;
      boolean var5 = true;
      if(var3.getY() >= 1 && var3.getY() + var4 + 1 < 256) {
         for(int var6 = var3.getY(); var6 <= var3.getY() + 1 + var4; ++var6) {
            byte var7 = 3;
            if(var6 <= var3.getY() + 3) {
               var7 = 0;
            }

            BlockPos$MutableBlockPos var8 = new BlockPos$MutableBlockPos();

            for(int var9 = var3.getX() - var7; var9 <= var3.getX() + var7; ++var9) {
               for(int var10 = var3.getZ() - var7; var10 <= var3.getZ() + var7; ++var10) {
                  if(var6 < 256) {
                     Block var11 = var1.getBlockState(var8.func_181079_c(var9, var6, var10)).getBlock();
                     if(var11.getMaterial() != Material.air && var11.getMaterial() != Material.leaves) {
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
