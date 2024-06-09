/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockFlower;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenFlowers
/*    */   extends WorldGenerator
/*    */ {
/*    */   private BlockFlower flower;
/*    */   private IBlockState field_175915_b;
/*    */   
/*    */   public WorldGenFlowers(BlockFlower p_i45632_1_, BlockFlower.EnumFlowerType p_i45632_2_) {
/* 16 */     setGeneratedBlock(p_i45632_1_, p_i45632_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setGeneratedBlock(BlockFlower p_175914_1_, BlockFlower.EnumFlowerType p_175914_2_) {
/* 21 */     this.flower = p_175914_1_;
/* 22 */     this.field_175915_b = p_175914_1_.getDefaultState().withProperty(p_175914_1_.getTypeProperty(), (Comparable)p_175914_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 27 */     for (int i = 0; i < 64; i++) {
/*    */       
/* 29 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 31 */       if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.getHasNoSky() || blockpos.getY() < 255) && this.flower.canBlockStay(worldIn, blockpos, this.field_175915_b))
/*    */       {
/* 33 */         worldIn.setBlockState(blockpos, this.field_175915_b, 2);
/*    */       }
/*    */     } 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\gen\feature\WorldGenFlowers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */