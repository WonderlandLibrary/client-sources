/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockDoublePlant;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenDoublePlant
/*    */   extends WorldGenerator
/*    */ {
/*    */   private BlockDoublePlant.EnumPlantType field_150549_a;
/*    */   
/*    */   public void setPlantType(BlockDoublePlant.EnumPlantType p_180710_1_) {
/* 15 */     this.field_150549_a = p_180710_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 20 */     boolean flag = false;
/*    */     
/* 22 */     for (int i = 0; i < 64; i++) {
/*    */       
/* 24 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 26 */       if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.getHasNoSky() || blockpos.getY() < 254) && Blocks.double_plant.canPlaceBlockAt(worldIn, blockpos)) {
/*    */         
/* 28 */         Blocks.double_plant.placeAt(worldIn, blockpos, this.field_150549_a, 2);
/* 29 */         flag = true;
/*    */       } 
/*    */     } 
/*    */     
/* 33 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\gen\feature\WorldGenDoublePlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */