/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class WorldGenGlowStone1
/* 10:   */   extends WorldGenerator
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000419";
/* 13:   */   
/* 14:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 15:   */   {
/* 16:15 */     if (!par1World.isAirBlock(par3, par4, par5)) {
/* 17:17 */       return false;
/* 18:   */     }
/* 19:19 */     if (par1World.getBlock(par3, par4 + 1, par5) != Blocks.netherrack) {
/* 20:21 */       return false;
/* 21:   */     }
/* 22:25 */     par1World.setBlock(par3, par4, par5, Blocks.glowstone, 0, 2);
/* 23:27 */     for (int var6 = 0; var6 < 1500; var6++)
/* 24:   */     {
/* 25:29 */       int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 26:30 */       int var8 = par4 - par2Random.nextInt(12);
/* 27:31 */       int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 28:33 */       if (par1World.getBlock(var7, var8, var9).getMaterial() == Material.air)
/* 29:   */       {
/* 30:35 */         int var10 = 0;
/* 31:37 */         for (int var11 = 0; var11 < 6; var11++)
/* 32:   */         {
/* 33:39 */           Block var12 = null;
/* 34:41 */           if (var11 == 0) {
/* 35:43 */             var12 = par1World.getBlock(var7 - 1, var8, var9);
/* 36:   */           }
/* 37:46 */           if (var11 == 1) {
/* 38:48 */             var12 = par1World.getBlock(var7 + 1, var8, var9);
/* 39:   */           }
/* 40:51 */           if (var11 == 2) {
/* 41:53 */             var12 = par1World.getBlock(var7, var8 - 1, var9);
/* 42:   */           }
/* 43:56 */           if (var11 == 3) {
/* 44:58 */             var12 = par1World.getBlock(var7, var8 + 1, var9);
/* 45:   */           }
/* 46:61 */           if (var11 == 4) {
/* 47:63 */             var12 = par1World.getBlock(var7, var8, var9 - 1);
/* 48:   */           }
/* 49:66 */           if (var11 == 5) {
/* 50:68 */             var12 = par1World.getBlock(var7, var8, var9 + 1);
/* 51:   */           }
/* 52:71 */           if (var12 == Blocks.glowstone) {
/* 53:73 */             var10++;
/* 54:   */           }
/* 55:   */         }
/* 56:77 */         if (var10 == 1) {
/* 57:79 */           par1World.setBlock(var7, var8, var9, Blocks.glowstone, 0, 2);
/* 58:   */         }
/* 59:   */       }
/* 60:   */     }
/* 61:84 */     return true;
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenGlowStone1
 * JD-Core Version:    0.7.0.1
 */