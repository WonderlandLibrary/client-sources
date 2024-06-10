/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ import net.minecraft.world.WorldProvider;
/*  7:   */ 
/*  8:   */ public class WorldGenFlowers
/*  9:   */   extends WorldGenerator
/* 10:   */ {
/* 11:   */   private Block field_150552_a;
/* 12:   */   private int field_150551_b;
/* 13:   */   private static final String __OBFID = "CL_00000410";
/* 14:   */   
/* 15:   */   public WorldGenFlowers(Block p_i45452_1_)
/* 16:   */   {
/* 17:15 */     this.field_150552_a = p_i45452_1_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void func_150550_a(Block p_150550_1_, int p_150550_2_)
/* 21:   */   {
/* 22:20 */     this.field_150552_a = p_150550_1_;
/* 23:21 */     this.field_150551_b = p_150550_2_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 27:   */   {
/* 28:26 */     for (int var6 = 0; var6 < 64; var6++)
/* 29:   */     {
/* 30:28 */       int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 31:29 */       int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 32:30 */       int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 33:32 */       if ((par1World.isAirBlock(var7, var8, var9)) && ((!par1World.provider.hasNoSky) || (var8 < 255)) && (this.field_150552_a.canBlockStay(par1World, var7, var8, var9))) {
/* 34:34 */         par1World.setBlock(var7, var8, var9, this.field_150552_a, this.field_150551_b, 2);
/* 35:   */       }
/* 36:   */     }
/* 37:38 */     return true;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenFlowers
 * JD-Core Version:    0.7.0.1
 */