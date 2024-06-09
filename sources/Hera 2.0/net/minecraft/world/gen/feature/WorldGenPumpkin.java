/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockPumpkin;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenPumpkin
/*    */   extends WorldGenerator {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 14 */     for (int i = 0; i < 64; i++) {
/*    */       
/* 16 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 18 */       if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.grass && Blocks.pumpkin.canPlaceBlockAt(worldIn, blockpos))
/*    */       {
/* 20 */         worldIn.setBlockState(blockpos, Blocks.pumpkin.getDefaultState().withProperty((IProperty)BlockPumpkin.FACING, (Comparable)EnumFacing.Plane.HORIZONTAL.random(rand)), 2);
/*    */       }
/*    */     } 
/*    */     
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\gen\feature\WorldGenPumpkin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */