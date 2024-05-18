/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenFire
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 12 */     for (int i = 0; i < 64; i++) {
/*    */       
/* 14 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 16 */       if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.netherrack)
/*    */       {
/* 18 */         worldIn.setBlockState(blockpos, Blocks.fire.getDefaultState(), 2);
/*    */       }
/*    */     } 
/*    */     
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\gen\feature\WorldGenFire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */