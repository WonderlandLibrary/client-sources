/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.world.gen.feature.WorldGenSpikes;
/*    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*    */ 
/*    */ public class BiomeEndDecorator extends BiomeDecorator {
/* 10 */   protected WorldGenerator spikeGen = (WorldGenerator)new WorldGenSpikes(Blocks.end_stone);
/*    */ 
/*    */   
/*    */   protected void genDecorations(BiomeGenBase biomeGenBaseIn) {
/* 14 */     generateOres();
/*    */     
/* 16 */     if (this.randomGenerator.nextInt(5) == 0) {
/*    */       
/* 18 */       int i = this.randomGenerator.nextInt(16) + 8;
/* 19 */       int j = this.randomGenerator.nextInt(16) + 8;
/* 20 */       this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(i, 0, j)));
/*    */     } 
/*    */     
/* 23 */     if (this.field_180294_c.getX() == 0 && this.field_180294_c.getZ() == 0) {
/*    */       
/* 25 */       EntityDragon entitydragon = new EntityDragon(this.currentWorld);
/* 26 */       entitydragon.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
/* 27 */       this.currentWorld.spawnEntityInWorld((Entity)entitydragon);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\biome\BiomeEndDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */