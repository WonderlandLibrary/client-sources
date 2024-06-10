/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class WorldGenShrub
/* 10:   */   extends WorldGenTrees
/* 11:   */ {
/* 12:   */   private int field_150528_a;
/* 13:   */   private int field_150527_b;
/* 14:   */   private static final String __OBFID = "CL_00000411";
/* 15:   */   
/* 16:   */   public WorldGenShrub(int par1, int par2)
/* 17:   */   {
/* 18:17 */     super(false);
/* 19:18 */     this.field_150527_b = par1;
/* 20:19 */     this.field_150528_a = par2;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 24:   */   {
/* 25:   */     Block var6;
/* 26:26 */     while ((((var6 = par1World.getBlock(par3, par4, par5)).getMaterial() == Material.air) || (var6.getMaterial() == Material.leaves)) && (par4 > 0))
/* 27:   */     {
/* 28:   */       Block var6;
/* 29:28 */       par4--;
/* 30:   */     }
/* 31:31 */     Block var7 = par1World.getBlock(par3, par4, par5);
/* 32:33 */     if ((var7 == Blocks.dirt) || (var7 == Blocks.grass))
/* 33:   */     {
/* 34:35 */       par4++;
/* 35:36 */       func_150516_a(par1World, par3, par4, par5, Blocks.log, this.field_150527_b);
/* 36:38 */       for (int var8 = par4; var8 <= par4 + 2; var8++)
/* 37:   */       {
/* 38:40 */         int var9 = var8 - par4;
/* 39:41 */         int var10 = 2 - var9;
/* 40:43 */         for (int var11 = par3 - var10; var11 <= par3 + var10; var11++)
/* 41:   */         {
/* 42:45 */           int var12 = var11 - par3;
/* 43:47 */           for (int var13 = par5 - var10; var13 <= par5 + var10; var13++)
/* 44:   */           {
/* 45:49 */             int var14 = var13 - par5;
/* 46:51 */             if (((Math.abs(var12) != var10) || (Math.abs(var14) != var10) || (par2Random.nextInt(2) != 0)) && (!par1World.getBlock(var11, var8, var13).func_149730_j())) {
/* 47:53 */               func_150516_a(par1World, var11, var8, var13, Blocks.leaves, this.field_150528_a);
/* 48:   */             }
/* 49:   */           }
/* 50:   */         }
/* 51:   */       }
/* 52:   */     }
/* 53:60 */     return true;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenShrub
 * JD-Core Version:    0.7.0.1
 */