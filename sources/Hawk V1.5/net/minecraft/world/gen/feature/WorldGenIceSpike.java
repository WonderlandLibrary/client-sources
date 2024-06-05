package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenIceSpike extends WorldGenerator {
   private static final String __OBFID = "CL_00000417";

   public boolean generate(World var1, Random var2, BlockPos var3) {
      while(var1.isAirBlock(var3) && var3.getY() > 2) {
         var3 = var3.offsetDown();
      }

      if (var1.getBlockState(var3).getBlock() != Blocks.snow) {
         return false;
      } else {
         var3 = var3.offsetUp(var2.nextInt(4));
         int var4 = var2.nextInt(4) + 7;
         int var5 = var4 / 4 + var2.nextInt(2);
         if (var5 > 1 && var2.nextInt(60) == 0) {
            var3 = var3.offsetUp(10 + var2.nextInt(30));
         }

         int var6;
         int var7;
         for(var6 = 0; var6 < var4; ++var6) {
            float var8 = (1.0F - (float)var6 / (float)var4) * (float)var5;
            var7 = MathHelper.ceiling_float_int(var8);

            for(int var9 = -var7; var9 <= var7; ++var9) {
               float var10 = (float)MathHelper.abs_int(var9) - 0.25F;

               for(int var11 = -var7; var11 <= var7; ++var11) {
                  float var12 = (float)MathHelper.abs_int(var11) - 0.25F;
                  if ((var9 == 0 && var11 == 0 || var10 * var10 + var12 * var12 <= var8 * var8) && (var9 != -var7 && var9 != var7 && var11 != -var7 && var11 != var7 || var2.nextFloat() <= 0.75F)) {
                     Block var13 = var1.getBlockState(var3.add(var9, var6, var11)).getBlock();
                     if (var13.getMaterial() == Material.air || var13 == Blocks.dirt || var13 == Blocks.snow || var13 == Blocks.ice) {
                        this.func_175906_a(var1, var3.add(var9, var6, var11), Blocks.packed_ice);
                     }

                     if (var6 != 0 && var7 > 1) {
                        var13 = var1.getBlockState(var3.add(var9, -var6, var11)).getBlock();
                        if (var13.getMaterial() == Material.air || var13 == Blocks.dirt || var13 == Blocks.snow || var13 == Blocks.ice) {
                           this.func_175906_a(var1, var3.add(var9, -var6, var11), Blocks.packed_ice);
                        }
                     }
                  }
               }
            }
         }

         var6 = var5 - 1;
         if (var6 < 0) {
            var6 = 0;
         } else if (var6 > 1) {
            var6 = 1;
         }

         for(int var14 = -var6; var14 <= var6; ++var14) {
            for(var7 = -var6; var7 <= var6; ++var7) {
               BlockPos var15 = var3.add(var14, -1, var7);
               int var16 = 50;
               if (Math.abs(var14) == 1 && Math.abs(var7) == 1) {
                  var16 = var2.nextInt(5);
               }

               while(var15.getY() > 50) {
                  Block var17 = var1.getBlockState(var15).getBlock();
                  if (var17.getMaterial() != Material.air && var17 != Blocks.dirt && var17 != Blocks.snow && var17 != Blocks.ice && var17 != Blocks.packed_ice) {
                     break;
                  }

                  this.func_175906_a(var1, var15, Blocks.packed_ice);
                  var15 = var15.offsetDown();
                  --var16;
                  if (var16 <= 0) {
                     var15 = var15.offsetDown(var2.nextInt(5) + 1);
                     var16 = var2.nextInt(5);
                  }
               }
            }
         }

         return true;
      }
   }
}
