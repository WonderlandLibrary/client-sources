/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class WorldGenIcePath
/*  9:   */   extends WorldGenerator
/* 10:   */ {
/* 11:   */   private Block field_150555_a;
/* 12:   */   private int field_150554_b;
/* 13:   */   private static final String __OBFID = "CL_00000416";
/* 14:   */   
/* 15:   */   public WorldGenIcePath(int p_i45454_1_)
/* 16:   */   {
/* 17:16 */     this.field_150555_a = Blocks.packed_ice;
/* 18:17 */     this.field_150554_b = p_i45454_1_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 22:   */   {
/* 23:22 */     while ((par1World.isAirBlock(par3, par4, par5)) && (par4 > 2)) {
/* 24:24 */       par4--;
/* 25:   */     }
/* 26:27 */     if (par1World.getBlock(par3, par4, par5) != Blocks.snow) {
/* 27:29 */       return false;
/* 28:   */     }
/* 29:33 */     int var6 = par2Random.nextInt(this.field_150554_b - 2) + 2;
/* 30:34 */     byte var7 = 1;
/* 31:36 */     for (int var8 = par3 - var6; var8 <= par3 + var6; var8++) {
/* 32:38 */       for (int var9 = par5 - var6; var9 <= par5 + var6; var9++)
/* 33:   */       {
/* 34:40 */         int var10 = var8 - par3;
/* 35:41 */         int var11 = var9 - par5;
/* 36:43 */         if (var10 * var10 + var11 * var11 <= var6 * var6) {
/* 37:45 */           for (int var12 = par4 - var7; var12 <= par4 + var7; var12++)
/* 38:   */           {
/* 39:47 */             Block var13 = par1World.getBlock(var8, var12, var9);
/* 40:49 */             if ((var13 == Blocks.dirt) || (var13 == Blocks.snow) || (var13 == Blocks.ice)) {
/* 41:51 */               par1World.setBlock(var8, var12, var9, this.field_150555_a, 0, 2);
/* 42:   */             }
/* 43:   */           }
/* 44:   */         }
/* 45:   */       }
/* 46:   */     }
/* 47:58 */     return true;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenIcePath
 * JD-Core Version:    0.7.0.1
 */