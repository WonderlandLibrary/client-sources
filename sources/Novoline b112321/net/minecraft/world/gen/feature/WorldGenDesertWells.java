package net.minecraft.world.gen.feature;

import com.google.common.base.Predicates;
import java.util.Random;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSand$EnumType;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab$EnumBlockHalf;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlab$EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDesertWells extends WorldGenerator {
   private static final BlockStateHelper field_175913_a = BlockStateHelper.forBlock(Blocks.sand).where(BlockSand.VARIANT, Predicates.equalTo(BlockSand$EnumType.SAND));
   private final IBlockState e = Blocks.stone_slab.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab$EnumType.SAND).withProperty(BlockSlab.HALF, BlockSlab$EnumBlockHalf.BOTTOM);
   private final IBlockState field_175912_c = Blocks.sandstone.getDefaultState();
   private final IBlockState c = Blocks.flowing_water.getDefaultState();

   public boolean generate(World var1, Random var2, BlockPos var3) {
      while(var1.isAirBlock(var3) && var3.getY() > 2) {
         var3 = var3.down();
      }

      if(!field_175913_a.apply(var1.getBlockState(var3))) {
         return false;
      } else {
         for(int var4 = -2; var4 <= 2; ++var4) {
            for(int var5 = -2; var5 <= 2; ++var5) {
               if(var1.isAirBlock(var3.a(var4, -1, var5)) && var1.isAirBlock(var3.a(var4, -2, var5))) {
                  return false;
               }
            }
         }

         int var7 = -1;

         while(true) {
            for(int var8 = -2; var8 <= 2; ++var8) {
               for(int var6 = -2; var6 <= 2; ++var6) {
                  var1.setBlockState(var3.a(var8, var7, var6), this.field_175912_c, 2);
               }
            }

            ++var7;
         }
      }
   }
}
