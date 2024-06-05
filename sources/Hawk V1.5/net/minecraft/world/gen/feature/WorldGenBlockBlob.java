package net.minecraft.world.gen.feature;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenBlockBlob extends WorldGenerator {
   private final int field_150544_b;
   private static final String __OBFID = "CL_00000402";
   private final Block field_150545_a;

   public boolean generate(World var1, Random var2, BlockPos var3) {
      for(; var3.getY() > 3; var3 = var3.offsetDown()) {
         if (!var1.isAirBlock(var3.offsetDown())) {
            Block var4 = var1.getBlockState(var3.offsetDown()).getBlock();
            if (var4 == Blocks.grass || var4 == Blocks.dirt || var4 == Blocks.stone) {
               break;
            }
         }
      }

      if (var3.getY() <= 3) {
         return false;
      } else {
         int var12 = this.field_150544_b;

         for(int var5 = 0; var12 >= 0 && var5 < 3; ++var5) {
            int var6 = var12 + var2.nextInt(2);
            int var7 = var12 + var2.nextInt(2);
            int var8 = var12 + var2.nextInt(2);
            float var9 = (float)(var6 + var7 + var8) * 0.333F + 0.5F;
            Iterator var10 = BlockPos.getAllInBox(var3.add(-var6, -var7, -var8), var3.add(var6, var7, var8)).iterator();

            while(var10.hasNext()) {
               BlockPos var11 = (BlockPos)var10.next();
               if (var11.distanceSq(var3) <= (double)(var9 * var9)) {
                  var1.setBlockState(var11, this.field_150545_a.getDefaultState(), 4);
               }
            }

            var3 = var3.add(-(var12 + 1) + var2.nextInt(2 + var12 * 2), 0 - var2.nextInt(2), -(var12 + 1) + var2.nextInt(2 + var12 * 2));
         }

         return true;
      }
   }

   public WorldGenBlockBlob(Block var1, int var2) {
      super(false);
      this.field_150545_a = var1;
      this.field_150544_b = var2;
   }
}
