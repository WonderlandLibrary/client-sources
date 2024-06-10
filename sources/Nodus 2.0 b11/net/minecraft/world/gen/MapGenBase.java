/*  1:   */ package net.minecraft.world.gen;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ import net.minecraft.world.chunk.IChunkProvider;
/*  7:   */ 
/*  8:   */ public class MapGenBase
/*  9:   */ {
/* 10:11 */   protected int range = 8;
/* 11:14 */   protected Random rand = new Random();
/* 12:   */   protected World worldObj;
/* 13:   */   private static final String __OBFID = "CL_00000394";
/* 14:   */   
/* 15:   */   public void func_151539_a(IChunkProvider p_151539_1_, World p_151539_2_, int p_151539_3_, int p_151539_4_, Block[] p_151539_5_)
/* 16:   */   {
/* 17:22 */     int var6 = this.range;
/* 18:23 */     this.worldObj = p_151539_2_;
/* 19:24 */     this.rand.setSeed(p_151539_2_.getSeed());
/* 20:25 */     long var7 = this.rand.nextLong();
/* 21:26 */     long var9 = this.rand.nextLong();
/* 22:28 */     for (int var11 = p_151539_3_ - var6; var11 <= p_151539_3_ + var6; var11++) {
/* 23:30 */       for (int var12 = p_151539_4_ - var6; var12 <= p_151539_4_ + var6; var12++)
/* 24:   */       {
/* 25:32 */         long var13 = var11 * var7;
/* 26:33 */         long var15 = var12 * var9;
/* 27:34 */         this.rand.setSeed(var13 ^ var15 ^ p_151539_2_.getSeed());
/* 28:35 */         func_151538_a(p_151539_2_, var11, var12, p_151539_3_, p_151539_4_, p_151539_5_);
/* 29:   */       }
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void func_151538_a(World p_151538_1_, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_) {}
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.MapGenBase
 * JD-Core Version:    0.7.0.1
 */