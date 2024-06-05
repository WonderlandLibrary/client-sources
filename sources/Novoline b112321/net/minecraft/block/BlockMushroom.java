package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt$DirtType;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

public class BlockMushroom extends BlockBush implements IGrowable {
   protected BlockMushroom() {
      float var1 = 0.2F;
      this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
      this.setTickRandomly(true);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(var4.nextInt(25) == 0) {
         int var5 = 5;
         boolean var6 = true;

         for(BlockPos var8 : BlockPos.getAllInBoxMutable(var2.a(-4, -1, -4), var2.a(4, 1, 4))) {
            if(var1.getBlockState(var8).getBlock() == this) {
               --var5;
               return;
            }
         }

         BlockPos var10 = var2.a(var4.nextInt(3) - 1, var4.nextInt(2) - var4.nextInt(2), var4.nextInt(3) - 1);

         for(int var11 = 0; var11 < 4; ++var11) {
            if(var1.isAirBlock(var10) && this.canBlockStay(var1, var10, this.getDefaultState())) {
               var2 = var10;
            }

            var10 = var2.a(var4.nextInt(3) - 1, var4.nextInt(2) - var4.nextInt(2), var4.nextInt(3) - 1);
         }

         if(var1.isAirBlock(var10) && this.canBlockStay(var1, var10, this.getDefaultState())) {
            var1.setBlockState(var10, this.getDefaultState(), 2);
         }
      }

   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return super.canPlaceBlockAt(var1, var2) && this.canBlockStay(var1, var2, this.getDefaultState());
   }

   protected boolean canPlaceBlockOn(Block var1) {
      return var1.isFullBlock();
   }

   public boolean canBlockStay(World var1, BlockPos var2, IBlockState var3) {
      if(var2.getY() >= 0 && var2.getY() < 256) {
         IBlockState var4 = var1.getBlockState(var2.down());
         return var4.getBlock() == Blocks.mycelium || var4.getBlock() == Blocks.dirt && var4.getValue(BlockDirt.VARIANT) == BlockDirt$DirtType.PODZOL || var1.getLight(var2) < 13 && this.canPlaceBlockOn(var4.getBlock());
      } else {
         return false;
      }
   }

   public boolean generateBigMushroom(World var1, BlockPos var2, IBlockState var3, Random var4) {
      var1.setBlockToAir(var2);
      WorldGenBigMushroom var5 = null;
      if(this == Blocks.brown_mushroom) {
         var5 = new WorldGenBigMushroom(Blocks.brown_mushroom_block);
      } else if(this == Blocks.red_mushroom) {
         var5 = new WorldGenBigMushroom(Blocks.red_mushroom_block);
      }

      if(var5.generate(var1, var4, var2)) {
         return true;
      } else {
         var1.setBlockState(var2, var3, 3);
         return false;
      }
   }

   public boolean canGrow(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return (double)var2.nextFloat() < 0.4D;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.generateBigMushroom(var1, var3, var4, var2);
   }
}
