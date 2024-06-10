/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class WorldGenBlockBlob
/*  9:   */   extends WorldGenerator
/* 10:   */ {
/* 11:   */   private Block field_150545_a;
/* 12:   */   private int field_150544_b;
/* 13:   */   private static final String __OBFID = "CL_00000402";
/* 14:   */   
/* 15:   */   public WorldGenBlockBlob(Block p_i45450_1_, int p_i45450_2_)
/* 16:   */   {
/* 17:16 */     super(false);
/* 18:17 */     this.field_150545_a = p_i45450_1_;
/* 19:18 */     this.field_150544_b = p_i45450_2_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 23:   */   {
/* 24:25 */     while (par4 > 3)
/* 25:   */     {
/* 26:29 */       if (!par1World.isAirBlock(par3, par4 - 1, par5))
/* 27:   */       {
/* 28:31 */         Block var6 = par1World.getBlock(par3, par4 - 1, par5);
/* 29:33 */         if ((var6 == Blocks.grass) || (var6 == Blocks.dirt) || (var6 == Blocks.stone)) {
/* 30:   */           break;
/* 31:   */         }
/* 32:   */       }
/* 33:39 */       par4--;
/* 34:   */     }
/* 35:44 */     if (par4 <= 3) {
/* 36:46 */       return false;
/* 37:   */     }
/* 38:49 */     int var18 = this.field_150544_b;
/* 39:51 */     for (int var7 = 0; (var18 >= 0) && (var7 < 3); var7++)
/* 40:   */     {
/* 41:53 */       int var8 = var18 + par2Random.nextInt(2);
/* 42:54 */       int var9 = var18 + par2Random.nextInt(2);
/* 43:55 */       int var10 = var18 + par2Random.nextInt(2);
/* 44:56 */       float var11 = (var8 + var9 + var10) * 0.333F + 0.5F;
/* 45:58 */       for (int var12 = par3 - var8; var12 <= par3 + var8; var12++) {
/* 46:60 */         for (int var13 = par5 - var10; var13 <= par5 + var10; var13++) {
/* 47:62 */           for (int var14 = par4 - var9; var14 <= par4 + var9; var14++)
/* 48:   */           {
/* 49:64 */             float var15 = var12 - par3;
/* 50:65 */             float var16 = var13 - par5;
/* 51:66 */             float var17 = var14 - par4;
/* 52:68 */             if (var15 * var15 + var16 * var16 + var17 * var17 <= var11 * var11) {
/* 53:70 */               par1World.setBlock(var12, var14, var13, this.field_150545_a, 0, 4);
/* 54:   */             }
/* 55:   */           }
/* 56:   */         }
/* 57:   */       }
/* 58:76 */       par3 += -(var18 + 1) + par2Random.nextInt(2 + var18 * 2);
/* 59:77 */       par5 += -(var18 + 1) + par2Random.nextInt(2 + var18 * 2);
/* 60:78 */       par4 += 0 - par2Random.nextInt(2);
/* 61:   */     }
/* 62:81 */     return true;
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenBlockBlob
 * JD-Core Version:    0.7.0.1
 */