package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenIceSpike extends WorldGenerator {
   public boolean generate(World var1, Random var2, BlockPos var3) {
      while(var1.isAirBlock(var3) && var3.getY() > 2) {
         var3 = var3.down();
      }

      if(var1.getBlockState(var3).getBlock() != Blocks.snow) {
         return false;
      } else {
         var3 = var3.up(var2.nextInt(4));
         int var4 = var2.nextInt(4) + 7;
         int var5 = var4 / 4 + var2.nextInt(2);
         if(var5 > 1 && var2.nextInt(60) == 0) {
            var3 = var3.up(10 + var2.nextInt(30));
         }

         for(int var6 = 0; var6 < var4; ++var6) {
            float var7 = (1.0F - (float)var6 / (float)var4) * (float)var5;
            int var8 = MathHelper.ceiling_float_int(var7);

            for(int var9 = -var8; var9 <= var8; ++var9) {
               float var10 = (float)MathHelper.abs_int(var9) - 0.25F;

               for(int var11 = -var8; var11 <= var8; ++var11) {
                  float var12 = (float)MathHelper.abs_int(var11) - 0.25F;
                  if(var10 * var10 + var12 * var12 <= var7 * var7 && (var9 != -var8 && var9 != var8 && var11 != -var8 && var11 != var8 || var2.nextFloat() <= 0.75F)) {
                     Block var13 = var1.getBlockState(var3.a(var9, var6, var11)).getBlock();
                     if(var13.getMaterial() == Material.air || var13 == Blocks.dirt || var13 == Blocks.snow || var13 == Blocks.ice) {
                        this.setBlockAndNotifyAdequately(var1, var3.a(var9, var6, var11), Blocks.packed_ice.getDefaultState());
                     }

                     if(var8 > 1) {
                        var13 = var1.getBlockState(var3.a(var9, -var6, var11)).getBlock();
                        if(var13.getMaterial() == Material.air || var13 == Blocks.dirt || var13 == Blocks.snow || var13 == Blocks.ice) {
                           this.setBlockAndNotifyAdequately(var1, var3.a(var9, -var6, var11), Blocks.packed_ice.getDefaultState());
                        }
                     }
                  }
               }
            }
         }

         int var15 = var5 - 1;
         var15 = 0;

         for(int var17 = -var15; var17 <= var15; ++var17) {
            for(int var18 = -var15; var18 <= var15; ++var18) {
               BlockPos var19 = var3.a(var17, -1, var18);
               int var21 = 50;
               if(Math.abs(var17) == 1 && Math.abs(var18) == 1) {
                  var21 = var2.nextInt(5);
               }

               while(var19.getY() > 50) {
                  Block var23 = var1.getBlockState(var19).getBlock();
                  if(var23.getMaterial() != Material.air && var23 != Blocks.dirt && var23 != Blocks.snow && var23 != Blocks.ice && var23 != Blocks.packed_ice) {
                     break;
                  }

                  this.setBlockAndNotifyAdequately(var1, var19, Blocks.packed_ice.getDefaultState());
                  var19 = var19.down();
                  --var21;
                  var19 = var19.down(var2.nextInt(5) + 1);
                  var21 = var2.nextInt(5);
               }
            }
         }

         return true;
      }
   }
}
