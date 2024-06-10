/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WorldGenSavannaTree
/*  10:    */   extends WorldGenAbstractTree
/*  11:    */ {
/*  12:    */   private static final String __OBFID = "CL_00000432";
/*  13:    */   
/*  14:    */   public WorldGenSavannaTree(boolean p_i45463_1_)
/*  15:    */   {
/*  16: 16 */     super(p_i45463_1_);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  20:    */   {
/*  21: 21 */     int var6 = par2Random.nextInt(3) + par2Random.nextInt(3) + 5;
/*  22: 22 */     boolean var7 = true;
/*  23: 24 */     if ((par4 >= 1) && (par4 + var6 + 1 <= 256))
/*  24:    */     {
/*  25: 29 */       for (int var8 = par4; var8 <= par4 + 1 + var6; var8++)
/*  26:    */       {
/*  27: 31 */         byte var9 = 1;
/*  28: 33 */         if (var8 == par4) {
/*  29: 35 */           var9 = 0;
/*  30:    */         }
/*  31: 38 */         if (var8 >= par4 + 1 + var6 - 2) {
/*  32: 40 */           var9 = 2;
/*  33:    */         }
/*  34: 43 */         for (int var10 = par3 - var9; (var10 <= par3 + var9) && (var7); var10++) {
/*  35: 45 */           for (int var11 = par5 - var9; (var11 <= par5 + var9) && (var7); var11++) {
/*  36: 47 */             if ((var8 >= 0) && (var8 < 256))
/*  37:    */             {
/*  38: 49 */               Block var12 = par1World.getBlock(var10, var8, var11);
/*  39: 51 */               if (!func_150523_a(var12)) {
/*  40: 53 */                 var7 = false;
/*  41:    */               }
/*  42:    */             }
/*  43:    */             else
/*  44:    */             {
/*  45: 58 */               var7 = false;
/*  46:    */             }
/*  47:    */           }
/*  48:    */         }
/*  49:    */       }
/*  50: 64 */       if (!var7) {
/*  51: 66 */         return false;
/*  52:    */       }
/*  53: 70 */       Block var21 = par1World.getBlock(par3, par4 - 1, par5);
/*  54: 72 */       if (((var21 == Blocks.grass) || (var21 == Blocks.dirt)) && (par4 < 256 - var6 - 1))
/*  55:    */       {
/*  56: 74 */         func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
/*  57: 75 */         int var22 = par2Random.nextInt(4);
/*  58: 76 */         int var10 = var6 - par2Random.nextInt(4) - 1;
/*  59: 77 */         int var11 = 3 - par2Random.nextInt(3);
/*  60: 78 */         int var23 = par3;
/*  61: 79 */         int var13 = par5;
/*  62: 80 */         int var14 = 0;
/*  63: 84 */         for (int var15 = 0; var15 < var6; var15++)
/*  64:    */         {
/*  65: 86 */           int var16 = par4 + var15;
/*  66: 88 */           if ((var15 >= var10) && (var11 > 0))
/*  67:    */           {
/*  68: 90 */             var23 += net.minecraft.util.Direction.offsetX[var22];
/*  69: 91 */             var13 += net.minecraft.util.Direction.offsetZ[var22];
/*  70: 92 */             var11--;
/*  71:    */           }
/*  72: 95 */           Block var17 = par1World.getBlock(var23, var16, var13);
/*  73: 97 */           if ((var17.getMaterial() == Material.air) || (var17.getMaterial() == Material.leaves))
/*  74:    */           {
/*  75: 99 */             func_150516_a(par1World, var23, var16, var13, Blocks.log2, 0);
/*  76:100 */             var14 = var16;
/*  77:    */           }
/*  78:    */         }
/*  79:104 */         for (var15 = -1; var15 <= 1; var15++) {
/*  80:106 */           for (int var16 = -1; var16 <= 1; var16++) {
/*  81:108 */             func_150525_a(par1World, var23 + var15, var14 + 1, var13 + var16);
/*  82:    */           }
/*  83:    */         }
/*  84:112 */         func_150525_a(par1World, var23 + 2, var14 + 1, var13);
/*  85:113 */         func_150525_a(par1World, var23 - 2, var14 + 1, var13);
/*  86:114 */         func_150525_a(par1World, var23, var14 + 1, var13 + 2);
/*  87:115 */         func_150525_a(par1World, var23, var14 + 1, var13 - 2);
/*  88:117 */         for (var15 = -3; var15 <= 3; var15++) {
/*  89:119 */           for (int var16 = -3; var16 <= 3; var16++) {
/*  90:121 */             if ((Math.abs(var15) != 3) || (Math.abs(var16) != 3)) {
/*  91:123 */               func_150525_a(par1World, var23 + var15, var14, var13 + var16);
/*  92:    */             }
/*  93:    */           }
/*  94:    */         }
/*  95:128 */         var23 = par3;
/*  96:129 */         var13 = par5;
/*  97:130 */         var15 = par2Random.nextInt(4);
/*  98:132 */         if (var15 != var22)
/*  99:    */         {
/* 100:134 */           int var16 = var10 - par2Random.nextInt(2) - 1;
/* 101:135 */           int var24 = 1 + par2Random.nextInt(3);
/* 102:136 */           var14 = 0;
/* 103:140 */           for (int var18 = var16; (var18 < var6) && (var24 > 0); var24--)
/* 104:    */           {
/* 105:142 */             if (var18 >= 1)
/* 106:    */             {
/* 107:144 */               int var19 = par4 + var18;
/* 108:145 */               var23 += net.minecraft.util.Direction.offsetX[var15];
/* 109:146 */               var13 += net.minecraft.util.Direction.offsetZ[var15];
/* 110:147 */               Block var20 = par1World.getBlock(var23, var19, var13);
/* 111:149 */               if ((var20.getMaterial() == Material.air) || (var20.getMaterial() == Material.leaves))
/* 112:    */               {
/* 113:151 */                 func_150516_a(par1World, var23, var19, var13, Blocks.log2, 0);
/* 114:152 */                 var14 = var19;
/* 115:    */               }
/* 116:    */             }
/* 117:156 */             var18++;
/* 118:    */           }
/* 119:159 */           if (var14 > 0)
/* 120:    */           {
/* 121:161 */             for (var18 = -1; var18 <= 1; var18++) {
/* 122:163 */               for (int var19 = -1; var19 <= 1; var19++) {
/* 123:165 */                 func_150525_a(par1World, var23 + var18, var14 + 1, var13 + var19);
/* 124:    */               }
/* 125:    */             }
/* 126:169 */             for (var18 = -2; var18 <= 2; var18++) {
/* 127:171 */               for (int var19 = -2; var19 <= 2; var19++) {
/* 128:173 */                 if ((Math.abs(var18) != 2) || (Math.abs(var19) != 2)) {
/* 129:175 */                   func_150525_a(par1World, var23 + var18, var14, var13 + var19);
/* 130:    */                 }
/* 131:    */               }
/* 132:    */             }
/* 133:    */           }
/* 134:    */         }
/* 135:182 */         return true;
/* 136:    */       }
/* 137:186 */       return false;
/* 138:    */     }
/* 139:192 */     return false;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void func_150525_a(World p_150525_1_, int p_150525_2_, int p_150525_3_, int p_150525_4_)
/* 143:    */   {
/* 144:198 */     Block var5 = p_150525_1_.getBlock(p_150525_2_, p_150525_3_, p_150525_4_);
/* 145:200 */     if ((var5.getMaterial() == Material.air) || (var5.getMaterial() == Material.leaves)) {
/* 146:202 */       func_150516_a(p_150525_1_, p_150525_2_, p_150525_3_, p_150525_4_, Blocks.leaves2, 0);
/* 147:    */     }
/* 148:    */   }
/* 149:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenSavannaTree
 * JD-Core Version:    0.7.0.1
 */