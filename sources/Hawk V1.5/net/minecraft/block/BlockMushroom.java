package net.minecraft.block;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

public class BlockMushroom extends BlockBush implements IGrowable {
   private static final String __OBFID = "CL_00000272";

   protected BlockMushroom() {
      float var1 = 0.2F;
      this.setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var1 * 2.0F, 0.5F + var1);
      this.setTickRandomly(true);
   }

   public boolean isStillGrowing(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   public boolean func_176485_d(World var1, BlockPos var2, IBlockState var3, Random var4) {
      var1.setBlockToAir(var2);
      WorldGenBigMushroom var5 = null;
      if (this == Blocks.brown_mushroom) {
         var5 = new WorldGenBigMushroom(0);
      } else if (this == Blocks.red_mushroom) {
         var5 = new WorldGenBigMushroom(1);
      }

      if (var5 != null && var5.generate(var1, var4, var2)) {
         return true;
      } else {
         var1.setBlockState(var2, var3, 3);
         return false;
      }
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.func_176485_d(var1, var3, var4, var2);
   }

   protected boolean canPlaceBlockOn(Block var1) {
      return var1.isFullBlock();
   }

   public boolean canBlockStay(World var1, BlockPos var2, IBlockState var3) {
      if (var2.getY() >= 0 && var2.getY() < 256) {
         IBlockState var4 = var1.getBlockState(var2.offsetDown());
         return var4.getBlock() == Blocks.mycelium ? true : (var4.getBlock() == Blocks.dirt && var4.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL ? true : var1.getLight(var2) < 13 && this.canPlaceBlockOn(var4.getBlock()));
      } else {
         return false;
      }
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return super.canPlaceBlockAt(var1, var2) && this.canBlockStay(var1, var2, this.getDefaultState());
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return (double)var2.nextFloat() < 0.4D;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var4.nextInt(25) == 0) {
         int var5 = 5;
         boolean var6 = true;
         Iterator var7 = BlockPos.getAllInBoxMutable(var2.add(-4, -1, -4), var2.add(4, 1, 4)).iterator();

         BlockPos var8;
         while(var7.hasNext()) {
            var8 = (BlockPos)var7.next();
            if (var1.getBlockState(var8).getBlock() == this) {
               --var5;
               if (var5 <= 0) {
                  return;
               }
            }
         }

         var8 = var2.add(var4.nextInt(3) - 1, var4.nextInt(2) - var4.nextInt(2), var4.nextInt(3) - 1);

         for(int var9 = 0; var9 < 4; ++var9) {
            if (var1.isAirBlock(var8) && this.canBlockStay(var1, var8, this.getDefaultState())) {
               var2 = var8;
            }

            var8 = var2.add(var4.nextInt(3) - 1, var4.nextInt(2) - var4.nextInt(2), var4.nextInt(3) - 1);
         }

         if (var1.isAirBlock(var8) && this.canBlockStay(var1, var8, this.getDefaultState())) {
            var1.setBlockState(var8, this.getDefaultState(), 2);
         }
      }

   }
}
