/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.util.MathHelper;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class WorldGenMegaJungle
/*  11:    */   extends WorldGenHugeTrees
/*  12:    */ {
/*  13:    */   private static final String __OBFID = "CL_00000420";
/*  14:    */   
/*  15:    */   public WorldGenMegaJungle(boolean p_i45456_1_, int p_i45456_2_, int p_i45456_3_, int p_i45456_4_, int p_i45456_5_)
/*  16:    */   {
/*  17: 16 */     super(p_i45456_1_, p_i45456_2_, p_i45456_3_, p_i45456_4_, p_i45456_5_);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  21:    */   {
/*  22: 21 */     int var6 = func_150533_a(par2Random);
/*  23: 23 */     if (!func_150537_a(par1World, par2Random, par3, par4, par5, var6)) {
/*  24: 25 */       return false;
/*  25:    */     }
/*  26: 29 */     func_150543_c(par1World, par3, par5, par4 + var6, 2, par2Random);
/*  27: 31 */     for (int var7 = par4 + var6 - 2 - par2Random.nextInt(4); var7 > par4 + var6 / 2; var7 -= 2 + par2Random.nextInt(4))
/*  28:    */     {
/*  29: 33 */       float var8 = par2Random.nextFloat() * 3.141593F * 2.0F;
/*  30: 34 */       int var9 = par3 + (int)(0.5F + MathHelper.cos(var8) * 4.0F);
/*  31: 35 */       int var10 = par5 + (int)(0.5F + MathHelper.sin(var8) * 4.0F);
/*  32: 38 */       for (int var11 = 0; var11 < 5; var11++)
/*  33:    */       {
/*  34: 40 */         var9 = par3 + (int)(1.5F + MathHelper.cos(var8) * var11);
/*  35: 41 */         var10 = par5 + (int)(1.5F + MathHelper.sin(var8) * var11);
/*  36: 42 */         func_150516_a(par1World, var9, var7 - 3 + var11 / 2, var10, Blocks.log, this.woodMetadata);
/*  37:    */       }
/*  38: 45 */       var11 = 1 + par2Random.nextInt(2);
/*  39: 46 */       int var12 = var7;
/*  40: 48 */       for (int var13 = var7 - var11; var13 <= var12; var13++)
/*  41:    */       {
/*  42: 50 */         int var14 = var13 - var12;
/*  43: 51 */         func_150534_b(par1World, var9, var13, var10, 1 - var14, par2Random);
/*  44:    */       }
/*  45:    */     }
/*  46: 55 */     for (int var15 = 0; var15 < var6; var15++)
/*  47:    */     {
/*  48: 57 */       Block var16 = par1World.getBlock(par3, par4 + var15, par5);
/*  49: 59 */       if ((var16.getMaterial() == Material.air) || (var16.getMaterial() == Material.leaves))
/*  50:    */       {
/*  51: 61 */         func_150516_a(par1World, par3, par4 + var15, par5, Blocks.log, this.woodMetadata);
/*  52: 63 */         if (var15 > 0)
/*  53:    */         {
/*  54: 65 */           if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3 - 1, par4 + var15, par5))) {
/*  55: 67 */             func_150516_a(par1World, par3 - 1, par4 + var15, par5, Blocks.vine, 8);
/*  56:    */           }
/*  57: 70 */           if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3, par4 + var15, par5 - 1))) {
/*  58: 72 */             func_150516_a(par1World, par3, par4 + var15, par5 - 1, Blocks.vine, 1);
/*  59:    */           }
/*  60:    */         }
/*  61:    */       }
/*  62: 77 */       if (var15 < var6 - 1)
/*  63:    */       {
/*  64: 79 */         var16 = par1World.getBlock(par3 + 1, par4 + var15, par5);
/*  65: 81 */         if ((var16.getMaterial() == Material.air) || (var16.getMaterial() == Material.leaves))
/*  66:    */         {
/*  67: 83 */           func_150516_a(par1World, par3 + 1, par4 + var15, par5, Blocks.log, this.woodMetadata);
/*  68: 85 */           if (var15 > 0)
/*  69:    */           {
/*  70: 87 */             if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3 + 2, par4 + var15, par5))) {
/*  71: 89 */               func_150516_a(par1World, par3 + 2, par4 + var15, par5, Blocks.vine, 2);
/*  72:    */             }
/*  73: 92 */             if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3 + 1, par4 + var15, par5 - 1))) {
/*  74: 94 */               func_150516_a(par1World, par3 + 1, par4 + var15, par5 - 1, Blocks.vine, 1);
/*  75:    */             }
/*  76:    */           }
/*  77:    */         }
/*  78: 99 */         var16 = par1World.getBlock(par3 + 1, par4 + var15, par5 + 1);
/*  79:101 */         if ((var16.getMaterial() == Material.air) || (var16.getMaterial() == Material.leaves))
/*  80:    */         {
/*  81:103 */           func_150516_a(par1World, par3 + 1, par4 + var15, par5 + 1, Blocks.log, this.woodMetadata);
/*  82:105 */           if (var15 > 0)
/*  83:    */           {
/*  84:107 */             if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3 + 2, par4 + var15, par5 + 1))) {
/*  85:109 */               func_150516_a(par1World, par3 + 2, par4 + var15, par5 + 1, Blocks.vine, 2);
/*  86:    */             }
/*  87:112 */             if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3 + 1, par4 + var15, par5 + 2))) {
/*  88:114 */               func_150516_a(par1World, par3 + 1, par4 + var15, par5 + 2, Blocks.vine, 4);
/*  89:    */             }
/*  90:    */           }
/*  91:    */         }
/*  92:119 */         var16 = par1World.getBlock(par3, par4 + var15, par5 + 1);
/*  93:121 */         if ((var16.getMaterial() == Material.air) || (var16.getMaterial() == Material.leaves))
/*  94:    */         {
/*  95:123 */           func_150516_a(par1World, par3, par4 + var15, par5 + 1, Blocks.log, this.woodMetadata);
/*  96:125 */           if (var15 > 0)
/*  97:    */           {
/*  98:127 */             if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3 - 1, par4 + var15, par5 + 1))) {
/*  99:129 */               func_150516_a(par1World, par3 - 1, par4 + var15, par5 + 1, Blocks.vine, 8);
/* 100:    */             }
/* 101:132 */             if ((par2Random.nextInt(3) > 0) && (par1World.isAirBlock(par3, par4 + var15, par5 + 2))) {
/* 102:134 */               func_150516_a(par1World, par3, par4 + var15, par5 + 2, Blocks.vine, 4);
/* 103:    */             }
/* 104:    */           }
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:141 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void func_150543_c(World p_150543_1_, int p_150543_2_, int p_150543_3_, int p_150543_4_, int p_150543_5_, Random p_150543_6_)
/* 112:    */   {
/* 113:147 */     byte var7 = 2;
/* 114:149 */     for (int var8 = p_150543_4_ - var7; var8 <= p_150543_4_; var8++)
/* 115:    */     {
/* 116:151 */       int var9 = var8 - p_150543_4_;
/* 117:152 */       func_150535_a(p_150543_1_, p_150543_2_, var8, p_150543_3_, p_150543_5_ + 1 - var9, p_150543_6_);
/* 118:    */     }
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenMegaJungle
 * JD-Core Version:    0.7.0.1
 */