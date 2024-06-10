/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public abstract class WorldGenerator
/*  8:   */ {
/*  9:   */   private final boolean doBlockNotify;
/* 10:   */   private static final String __OBFID = "CL_00000409";
/* 11:   */   
/* 12:   */   public WorldGenerator()
/* 13:   */   {
/* 14:18 */     this.doBlockNotify = false;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public WorldGenerator(boolean par1)
/* 18:   */   {
/* 19:23 */     this.doBlockNotify = par1;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public abstract boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3);
/* 23:   */   
/* 24:   */   public void setScale(double par1, double par3, double par5) {}
/* 25:   */   
/* 26:   */   protected void func_150515_a(World p_150515_1_, int p_150515_2_, int p_150515_3_, int p_150515_4_, Block p_150515_5_)
/* 27:   */   {
/* 28:35 */     func_150516_a(p_150515_1_, p_150515_2_, p_150515_3_, p_150515_4_, p_150515_5_, 0);
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected void func_150516_a(World p_150516_1_, int p_150516_2_, int p_150516_3_, int p_150516_4_, Block p_150516_5_, int p_150516_6_)
/* 32:   */   {
/* 33:40 */     if (this.doBlockNotify) {
/* 34:42 */       p_150516_1_.setBlock(p_150516_2_, p_150516_3_, p_150516_4_, p_150516_5_, p_150516_6_, 3);
/* 35:   */     } else {
/* 36:46 */       p_150516_1_.setBlock(p_150516_2_, p_150516_3_, p_150516_4_, p_150516_5_, p_150516_6_, 2);
/* 37:   */     }
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenerator
 * JD-Core Version:    0.7.0.1
 */