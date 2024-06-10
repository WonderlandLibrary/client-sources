/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.boss.EntityDragon;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ import net.minecraft.world.gen.feature.WorldGenSpikes;
/*  8:   */ import net.minecraft.world.gen.feature.WorldGenerator;
/*  9:   */ 
/* 10:   */ public class BiomeEndDecorator
/* 11:   */   extends BiomeDecorator
/* 12:   */ {
/* 13:   */   protected WorldGenerator spikeGen;
/* 14:   */   private static final String __OBFID = "CL_00000188";
/* 15:   */   
/* 16:   */   public BiomeEndDecorator()
/* 17:   */   {
/* 18:15 */     this.spikeGen = new WorldGenSpikes(Blocks.end_stone);
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void func_150513_a(BiomeGenBase p_150513_1_)
/* 22:   */   {
/* 23:20 */     generateOres();
/* 24:22 */     if (this.randomGenerator.nextInt(5) == 0)
/* 25:   */     {
/* 26:24 */       int var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
/* 27:25 */       int var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
/* 28:26 */       int var4 = this.currentWorld.getTopSolidOrLiquidBlock(var2, var3);
/* 29:27 */       this.spikeGen.generate(this.currentWorld, this.randomGenerator, var2, var4, var3);
/* 30:   */     }
/* 31:30 */     if ((this.chunk_X == 0) && (this.chunk_Z == 0))
/* 32:   */     {
/* 33:32 */       EntityDragon var5 = new EntityDragon(this.currentWorld);
/* 34:33 */       var5.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
/* 35:34 */       this.currentWorld.spawnEntityInWorld(var5);
/* 36:   */     }
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeEndDecorator
 * JD-Core Version:    0.7.0.1
 */