/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WorldGenCanopyTree
/*  10:    */   extends WorldGenAbstractTree
/*  11:    */ {
/*  12:    */   private static final String __OBFID = "CL_00000430";
/*  13:    */   
/*  14:    */   public WorldGenCanopyTree(boolean p_i45461_1_)
/*  15:    */   {
/*  16: 16 */     super(p_i45461_1_);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/*  20:    */   {
/*  21: 21 */     int var6 = par2Random.nextInt(3) + par2Random.nextInt(2) + 6;
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
/*  53: 70 */       Block var20 = par1World.getBlock(par3, par4 - 1, par5);
/*  54: 72 */       if (((var20 == Blocks.grass) || (var20 == Blocks.dirt)) && (par4 < 256 - var6 - 1))
/*  55:    */       {
/*  56: 74 */         func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
/*  57: 75 */         func_150515_a(par1World, par3 + 1, par4 - 1, par5, Blocks.dirt);
/*  58: 76 */         func_150515_a(par1World, par3 + 1, par4 - 1, par5 + 1, Blocks.dirt);
/*  59: 77 */         func_150515_a(par1World, par3, par4 - 1, par5 + 1, Blocks.dirt);
/*  60: 78 */         int var21 = par2Random.nextInt(4);
/*  61: 79 */         int var10 = var6 - par2Random.nextInt(4);
/*  62: 80 */         int var11 = 2 - par2Random.nextInt(3);
/*  63: 81 */         int var22 = par3;
/*  64: 82 */         int var13 = par5;
/*  65: 83 */         int var14 = 0;
/*  66: 87 */         for (int var15 = 0; var15 < var6; var15++)
/*  67:    */         {
/*  68: 89 */           int var16 = par4 + var15;
/*  69: 91 */           if ((var15 >= var10) && (var11 > 0))
/*  70:    */           {
/*  71: 93 */             var22 += net.minecraft.util.Direction.offsetX[var21];
/*  72: 94 */             var13 += net.minecraft.util.Direction.offsetZ[var21];
/*  73: 95 */             var11--;
/*  74:    */           }
/*  75: 98 */           Block var17 = par1World.getBlock(var22, var16, var13);
/*  76:100 */           if ((var17.getMaterial() == Material.air) || (var17.getMaterial() == Material.leaves))
/*  77:    */           {
/*  78:102 */             func_150516_a(par1World, var22, var16, var13, Blocks.log2, 1);
/*  79:103 */             func_150516_a(par1World, var22 + 1, var16, var13, Blocks.log2, 1);
/*  80:104 */             func_150516_a(par1World, var22, var16, var13 + 1, Blocks.log2, 1);
/*  81:105 */             func_150516_a(par1World, var22 + 1, var16, var13 + 1, Blocks.log2, 1);
/*  82:106 */             var14 = var16;
/*  83:    */           }
/*  84:    */         }
/*  85:110 */         for (var15 = -2; var15 <= 0; var15++) {
/*  86:112 */           for (int var16 = -2; var16 <= 0; var16++)
/*  87:    */           {
/*  88:114 */             byte var25 = -1;
/*  89:115 */             func_150526_a(par1World, var22 + var15, var14 + var25, var13 + var16);
/*  90:116 */             func_150526_a(par1World, 1 + var22 - var15, var14 + var25, var13 + var16);
/*  91:117 */             func_150526_a(par1World, var22 + var15, var14 + var25, 1 + var13 - var16);
/*  92:118 */             func_150526_a(par1World, 1 + var22 - var15, var14 + var25, 1 + var13 - var16);
/*  93:120 */             if (((var15 > -2) || (var16 > -1)) && ((var15 != -1) || (var16 != -2)))
/*  94:    */             {
/*  95:122 */               byte var24 = 1;
/*  96:123 */               func_150526_a(par1World, var22 + var15, var14 + var24, var13 + var16);
/*  97:124 */               func_150526_a(par1World, 1 + var22 - var15, var14 + var24, var13 + var16);
/*  98:125 */               func_150526_a(par1World, var22 + var15, var14 + var24, 1 + var13 - var16);
/*  99:126 */               func_150526_a(par1World, 1 + var22 - var15, var14 + var24, 1 + var13 - var16);
/* 100:    */             }
/* 101:    */           }
/* 102:    */         }
/* 103:131 */         if (par2Random.nextBoolean())
/* 104:    */         {
/* 105:133 */           func_150526_a(par1World, var22, var14 + 2, var13);
/* 106:134 */           func_150526_a(par1World, var22 + 1, var14 + 2, var13);
/* 107:135 */           func_150526_a(par1World, var22 + 1, var14 + 2, var13 + 1);
/* 108:136 */           func_150526_a(par1World, var22, var14 + 2, var13 + 1);
/* 109:    */         }
/* 110:139 */         for (var15 = -3; var15 <= 4; var15++) {
/* 111:141 */           for (int var16 = -3; var16 <= 4; var16++) {
/* 112:143 */             if (((var15 != -3) || (var16 != -3)) && ((var15 != -3) || (var16 != 4)) && ((var15 != 4) || (var16 != -3)) && ((var15 != 4) || (var16 != 4)) && ((Math.abs(var15) < 3) || (Math.abs(var16) < 3))) {
/* 113:145 */               func_150526_a(par1World, var22 + var15, var14, var13 + var16);
/* 114:    */             }
/* 115:    */           }
/* 116:    */         }
/* 117:150 */         for (var15 = -1; var15 <= 2; var15++) {
/* 118:152 */           for (int var16 = -1; var16 <= 2; var16++) {
/* 119:154 */             if (((var15 < 0) || (var15 > 1) || (var16 < 0) || (var16 > 1)) && (par2Random.nextInt(3) <= 0))
/* 120:    */             {
/* 121:156 */               int var23 = par2Random.nextInt(3) + 2;
/* 122:159 */               for (int var18 = 0; var18 < var23; var18++) {
/* 123:161 */                 func_150516_a(par1World, par3 + var15, var14 - var18 - 1, par5 + var16, Blocks.log2, 1);
/* 124:    */               }
/* 125:166 */               for (var18 = -1; var18 <= 1; var18++) {
/* 126:168 */                 for (int var19 = -1; var19 <= 1; var19++) {
/* 127:170 */                   func_150526_a(par1World, var22 + var15 + var18, var14 - 0, var13 + var16 + var19);
/* 128:    */                 }
/* 129:    */               }
/* 130:174 */               for (var18 = -2; var18 <= 2; var18++) {
/* 131:176 */                 for (int var19 = -2; var19 <= 2; var19++) {
/* 132:178 */                   if ((Math.abs(var18) != 2) || (Math.abs(var19) != 2)) {
/* 133:180 */                     func_150526_a(par1World, var22 + var15 + var18, var14 - 1, var13 + var16 + var19);
/* 134:    */                   }
/* 135:    */                 }
/* 136:    */               }
/* 137:    */             }
/* 138:    */           }
/* 139:    */         }
/* 140:188 */         return true;
/* 141:    */       }
/* 142:192 */       return false;
/* 143:    */     }
/* 144:198 */     return false;
/* 145:    */   }
/* 146:    */   
/* 147:    */   private void func_150526_a(World p_150526_1_, int p_150526_2_, int p_150526_3_, int p_150526_4_)
/* 148:    */   {
/* 149:204 */     Block var5 = p_150526_1_.getBlock(p_150526_2_, p_150526_3_, p_150526_4_);
/* 150:206 */     if (var5.getMaterial() == Material.air) {
/* 151:208 */       func_150516_a(p_150526_1_, p_150526_2_, p_150526_3_, p_150526_4_, Blocks.leaves2, 1);
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenCanopyTree
 * JD-Core Version:    0.7.0.1
 */