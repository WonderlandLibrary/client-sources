/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.entity.item.EntityEnderCrystal;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class WorldGenSpikes
/* 10:   */   extends WorldGenerator
/* 11:   */ {
/* 12:   */   private Block field_150520_a;
/* 13:   */   private static final String __OBFID = "CL_00000433";
/* 14:   */   
/* 15:   */   public WorldGenSpikes(Block p_i45464_1_)
/* 16:   */   {
/* 17:16 */     this.field_150520_a = p_i45464_1_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 21:   */   {
/* 22:21 */     if ((par1World.isAirBlock(par3, par4, par5)) && (par1World.getBlock(par3, par4 - 1, par5) == this.field_150520_a))
/* 23:   */     {
/* 24:23 */       int var6 = par2Random.nextInt(32) + 6;
/* 25:24 */       int var7 = par2Random.nextInt(4) + 1;
/* 26:30 */       for (int var8 = par3 - var7; var8 <= par3 + var7; var8++) {
/* 27:32 */         for (int var9 = par5 - var7; var9 <= par5 + var7; var9++)
/* 28:   */         {
/* 29:34 */           int var10 = var8 - par3;
/* 30:35 */           int var11 = var9 - par5;
/* 31:37 */           if ((var10 * var10 + var11 * var11 <= var7 * var7 + 1) && (par1World.getBlock(var8, par4 - 1, var9) != this.field_150520_a)) {
/* 32:39 */             return false;
/* 33:   */           }
/* 34:   */         }
/* 35:   */       }
/* 36:44 */       for (var8 = par4; (var8 < par4 + var6) && (var8 < 256); var8++) {
/* 37:46 */         for (int var9 = par3 - var7; var9 <= par3 + var7; var9++) {
/* 38:48 */           for (int var10 = par5 - var7; var10 <= par5 + var7; var10++)
/* 39:   */           {
/* 40:50 */             int var11 = var9 - par3;
/* 41:51 */             int var12 = var10 - par5;
/* 42:53 */             if (var11 * var11 + var12 * var12 <= var7 * var7 + 1) {
/* 43:55 */               par1World.setBlock(var9, var8, var10, Blocks.obsidian, 0, 2);
/* 44:   */             }
/* 45:   */           }
/* 46:   */         }
/* 47:   */       }
/* 48:61 */       EntityEnderCrystal var13 = new EntityEnderCrystal(par1World);
/* 49:62 */       var13.setLocationAndAngles(par3 + 0.5F, par4 + var6, par5 + 0.5F, par2Random.nextFloat() * 360.0F, 0.0F);
/* 50:63 */       par1World.spawnEntityInWorld(var13);
/* 51:64 */       par1World.setBlock(par3, par4 + var6, par5, Blocks.bedrock, 0, 2);
/* 52:65 */       return true;
/* 53:   */     }
/* 54:69 */     return false;
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenSpikes
 * JD-Core Version:    0.7.0.1
 */