/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class WorldGenClay
/* 10:   */   extends WorldGenerator
/* 11:   */ {
/* 12:   */   private Block field_150546_a;
/* 13:   */   private int numberOfBlocks;
/* 14:   */   private static final String __OBFID = "CL_00000405";
/* 15:   */   
/* 16:   */   public WorldGenClay(int par1)
/* 17:   */   {
/* 18:19 */     this.field_150546_a = Blocks.clay;
/* 19:20 */     this.numberOfBlocks = par1;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 23:   */   {
/* 24:25 */     if (par1World.getBlock(par3, par4, par5).getMaterial() != Material.water) {
/* 25:27 */       return false;
/* 26:   */     }
/* 27:31 */     int var6 = par2Random.nextInt(this.numberOfBlocks - 2) + 2;
/* 28:32 */     byte var7 = 1;
/* 29:34 */     for (int var8 = par3 - var6; var8 <= par3 + var6; var8++) {
/* 30:36 */       for (int var9 = par5 - var6; var9 <= par5 + var6; var9++)
/* 31:   */       {
/* 32:38 */         int var10 = var8 - par3;
/* 33:39 */         int var11 = var9 - par5;
/* 34:41 */         if (var10 * var10 + var11 * var11 <= var6 * var6) {
/* 35:43 */           for (int var12 = par4 - var7; var12 <= par4 + var7; var12++)
/* 36:   */           {
/* 37:45 */             Block var13 = par1World.getBlock(var8, var12, var9);
/* 38:47 */             if ((var13 == Blocks.dirt) || (var13 == Blocks.clay)) {
/* 39:49 */               par1World.setBlock(var8, var12, var9, this.field_150546_a, 0, 2);
/* 40:   */             }
/* 41:   */           }
/* 42:   */         }
/* 43:   */       }
/* 44:   */     }
/* 45:56 */     return true;
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenClay
 * JD-Core Version:    0.7.0.1
 */