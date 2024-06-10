/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.init.Blocks;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class WorldGenDesertWells
/*  8:   */   extends WorldGenerator
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000407";
/* 11:   */   
/* 12:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 13:   */   {
/* 14:13 */     while ((par1World.isAirBlock(par3, par4, par5)) && (par4 > 2)) {
/* 15:15 */       par4--;
/* 16:   */     }
/* 17:18 */     if (par1World.getBlock(par3, par4, par5) != Blocks.sand) {
/* 18:20 */       return false;
/* 19:   */     }
/* 20:27 */     for (int var6 = -2; var6 <= 2; var6++) {
/* 21:29 */       for (int var7 = -2; var7 <= 2; var7++) {
/* 22:31 */         if ((par1World.isAirBlock(par3 + var6, par4 - 1, par5 + var7)) && (par1World.isAirBlock(par3 + var6, par4 - 2, par5 + var7))) {
/* 23:33 */           return false;
/* 24:   */         }
/* 25:   */       }
/* 26:   */     }
/* 27:38 */     for (var6 = -1; var6 <= 0; var6++) {
/* 28:40 */       for (int var7 = -2; var7 <= 2; var7++) {
/* 29:42 */         for (int var8 = -2; var8 <= 2; var8++) {
/* 30:44 */           par1World.setBlock(par3 + var7, par4 + var6, par5 + var8, Blocks.sandstone, 0, 2);
/* 31:   */         }
/* 32:   */       }
/* 33:   */     }
/* 34:49 */     par1World.setBlock(par3, par4, par5, Blocks.flowing_water, 0, 2);
/* 35:50 */     par1World.setBlock(par3 - 1, par4, par5, Blocks.flowing_water, 0, 2);
/* 36:51 */     par1World.setBlock(par3 + 1, par4, par5, Blocks.flowing_water, 0, 2);
/* 37:52 */     par1World.setBlock(par3, par4, par5 - 1, Blocks.flowing_water, 0, 2);
/* 38:53 */     par1World.setBlock(par3, par4, par5 + 1, Blocks.flowing_water, 0, 2);
/* 39:55 */     for (var6 = -2; var6 <= 2; var6++) {
/* 40:57 */       for (int var7 = -2; var7 <= 2; var7++) {
/* 41:59 */         if ((var6 == -2) || (var6 == 2) || (var7 == -2) || (var7 == 2)) {
/* 42:61 */           par1World.setBlock(par3 + var6, par4 + 1, par5 + var7, Blocks.sandstone, 0, 2);
/* 43:   */         }
/* 44:   */       }
/* 45:   */     }
/* 46:66 */     par1World.setBlock(par3 + 2, par4 + 1, par5, Blocks.stone_slab, 1, 2);
/* 47:67 */     par1World.setBlock(par3 - 2, par4 + 1, par5, Blocks.stone_slab, 1, 2);
/* 48:68 */     par1World.setBlock(par3, par4 + 1, par5 + 2, Blocks.stone_slab, 1, 2);
/* 49:69 */     par1World.setBlock(par3, par4 + 1, par5 - 2, Blocks.stone_slab, 1, 2);
/* 50:71 */     for (var6 = -1; var6 <= 1; var6++) {
/* 51:73 */       for (int var7 = -1; var7 <= 1; var7++) {
/* 52:75 */         if ((var6 == 0) && (var7 == 0)) {
/* 53:77 */           par1World.setBlock(par3 + var6, par4 + 4, par5 + var7, Blocks.sandstone, 0, 2);
/* 54:   */         } else {
/* 55:81 */           par1World.setBlock(par3 + var6, par4 + 4, par5 + var7, Blocks.stone_slab, 1, 2);
/* 56:   */         }
/* 57:   */       }
/* 58:   */     }
/* 59:86 */     for (var6 = 1; var6 <= 3; var6++)
/* 60:   */     {
/* 61:88 */       par1World.setBlock(par3 - 1, par4 + var6, par5 - 1, Blocks.sandstone, 0, 2);
/* 62:89 */       par1World.setBlock(par3 - 1, par4 + var6, par5 + 1, Blocks.sandstone, 0, 2);
/* 63:90 */       par1World.setBlock(par3 + 1, par4 + var6, par5 - 1, Blocks.sandstone, 0, 2);
/* 64:91 */       par1World.setBlock(par3 + 1, par4 + var6, par5 + 1, Blocks.sandstone, 0, 2);
/* 65:   */     }
/* 66:94 */     return true;
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenDesertWells
 * JD-Core Version:    0.7.0.1
 */