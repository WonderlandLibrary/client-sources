/*   1:    */ package net.minecraft.world.gen.layer;
/*   2:    */ 
/*   3:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   4:    */ import net.minecraft.world.biome.BiomeGenJungle;
/*   5:    */ import net.minecraft.world.biome.BiomeGenMesa;
/*   6:    */ 
/*   7:    */ public class GenLayerShore
/*   8:    */   extends GenLayer
/*   9:    */ {
/*  10:    */   private static final String __OBFID = "CL_00000568";
/*  11:    */   
/*  12:    */   public GenLayerShore(long par1, GenLayer par3GenLayer)
/*  13:    */   {
/*  14: 13 */     super(par1);
/*  15: 14 */     this.parent = par3GenLayer;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public int[] getInts(int par1, int par2, int par3, int par4)
/*  19:    */   {
/*  20: 23 */     int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
/*  21: 24 */     int[] var6 = IntCache.getIntCache(par3 * par4);
/*  22: 26 */     for (int var7 = 0; var7 < par4; var7++) {
/*  23: 28 */       for (int var8 = 0; var8 < par3; var8++)
/*  24:    */       {
/*  25: 30 */         initChunkSeed(var8 + par1, var7 + par2);
/*  26: 31 */         int var9 = var5[(var8 + 1 + (var7 + 1) * (par3 + 2))];
/*  27: 32 */         BiomeGenBase var10 = BiomeGenBase.func_150568_d(var9);
/*  28: 38 */         if (var9 == BiomeGenBase.mushroomIsland.biomeID)
/*  29:    */         {
/*  30: 40 */           int var11 = var5[(var8 + 1 + (var7 + 1 - 1) * (par3 + 2))];
/*  31: 41 */           int var12 = var5[(var8 + 1 + 1 + (var7 + 1) * (par3 + 2))];
/*  32: 42 */           int var13 = var5[(var8 + 1 - 1 + (var7 + 1) * (par3 + 2))];
/*  33: 43 */           int var14 = var5[(var8 + 1 + (var7 + 1 + 1) * (par3 + 2))];
/*  34: 45 */           if ((var11 != BiomeGenBase.ocean.biomeID) && (var12 != BiomeGenBase.ocean.biomeID) && (var13 != BiomeGenBase.ocean.biomeID) && (var14 != BiomeGenBase.ocean.biomeID)) {
/*  35: 47 */             var6[(var8 + var7 * par3)] = var9;
/*  36:    */           } else {
/*  37: 51 */             var6[(var8 + var7 * par3)] = BiomeGenBase.mushroomIslandShore.biomeID;
/*  38:    */           }
/*  39:    */         }
/*  40: 54 */         else if ((var10 != null) && (var10.func_150562_l() == BiomeGenJungle.class))
/*  41:    */         {
/*  42: 56 */           int var11 = var5[(var8 + 1 + (var7 + 1 - 1) * (par3 + 2))];
/*  43: 57 */           int var12 = var5[(var8 + 1 + 1 + (var7 + 1) * (par3 + 2))];
/*  44: 58 */           int var13 = var5[(var8 + 1 - 1 + (var7 + 1) * (par3 + 2))];
/*  45: 59 */           int var14 = var5[(var8 + 1 + (var7 + 1 + 1) * (par3 + 2))];
/*  46: 61 */           if ((func_151631_c(var11)) && (func_151631_c(var12)) && (func_151631_c(var13)) && (func_151631_c(var14)))
/*  47:    */           {
/*  48: 63 */             if ((!func_151618_b(var11)) && (!func_151618_b(var12)) && (!func_151618_b(var13)) && (!func_151618_b(var14))) {
/*  49: 65 */               var6[(var8 + var7 * par3)] = var9;
/*  50:    */             } else {
/*  51: 69 */               var6[(var8 + var7 * par3)] = BiomeGenBase.beach.biomeID;
/*  52:    */             }
/*  53:    */           }
/*  54:    */           else {
/*  55: 74 */             var6[(var8 + var7 * par3)] = BiomeGenBase.field_150574_L.biomeID;
/*  56:    */           }
/*  57:    */         }
/*  58: 77 */         else if ((var9 != BiomeGenBase.extremeHills.biomeID) && (var9 != BiomeGenBase.field_150580_W.biomeID) && (var9 != BiomeGenBase.extremeHillsEdge.biomeID))
/*  59:    */         {
/*  60: 79 */           if ((var10 != null) && (var10.func_150559_j()))
/*  61:    */           {
/*  62: 81 */             func_151632_a(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150577_O.biomeID);
/*  63:    */           }
/*  64: 83 */           else if ((var9 != BiomeGenBase.field_150589_Z.biomeID) && (var9 != BiomeGenBase.field_150607_aa.biomeID))
/*  65:    */           {
/*  66: 85 */             if ((var9 != BiomeGenBase.ocean.biomeID) && (var9 != BiomeGenBase.field_150575_M.biomeID) && (var9 != BiomeGenBase.river.biomeID) && (var9 != BiomeGenBase.swampland.biomeID))
/*  67:    */             {
/*  68: 87 */               int var11 = var5[(var8 + 1 + (var7 + 1 - 1) * (par3 + 2))];
/*  69: 88 */               int var12 = var5[(var8 + 1 + 1 + (var7 + 1) * (par3 + 2))];
/*  70: 89 */               int var13 = var5[(var8 + 1 - 1 + (var7 + 1) * (par3 + 2))];
/*  71: 90 */               int var14 = var5[(var8 + 1 + (var7 + 1 + 1) * (par3 + 2))];
/*  72: 92 */               if ((!func_151618_b(var11)) && (!func_151618_b(var12)) && (!func_151618_b(var13)) && (!func_151618_b(var14))) {
/*  73: 94 */                 var6[(var8 + var7 * par3)] = var9;
/*  74:    */               } else {
/*  75: 98 */                 var6[(var8 + var7 * par3)] = BiomeGenBase.beach.biomeID;
/*  76:    */               }
/*  77:    */             }
/*  78:    */             else
/*  79:    */             {
/*  80:103 */               var6[(var8 + var7 * par3)] = var9;
/*  81:    */             }
/*  82:    */           }
/*  83:    */           else
/*  84:    */           {
/*  85:108 */             int var11 = var5[(var8 + 1 + (var7 + 1 - 1) * (par3 + 2))];
/*  86:109 */             int var12 = var5[(var8 + 1 + 1 + (var7 + 1) * (par3 + 2))];
/*  87:110 */             int var13 = var5[(var8 + 1 - 1 + (var7 + 1) * (par3 + 2))];
/*  88:111 */             int var14 = var5[(var8 + 1 + (var7 + 1 + 1) * (par3 + 2))];
/*  89:113 */             if ((!func_151618_b(var11)) && (!func_151618_b(var12)) && (!func_151618_b(var13)) && (!func_151618_b(var14)))
/*  90:    */             {
/*  91:115 */               if ((func_151633_d(var11)) && (func_151633_d(var12)) && (func_151633_d(var13)) && (func_151633_d(var14))) {
/*  92:117 */                 var6[(var8 + var7 * par3)] = var9;
/*  93:    */               } else {
/*  94:121 */                 var6[(var8 + var7 * par3)] = BiomeGenBase.desert.biomeID;
/*  95:    */               }
/*  96:    */             }
/*  97:    */             else {
/*  98:126 */               var6[(var8 + var7 * par3)] = var9;
/*  99:    */             }
/* 100:    */           }
/* 101:    */         }
/* 102:    */         else
/* 103:    */         {
/* 104:132 */           func_151632_a(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150576_N.biomeID);
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:137 */     return var6;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void func_151632_a(int[] p_151632_1_, int[] p_151632_2_, int p_151632_3_, int p_151632_4_, int p_151632_5_, int p_151632_6_, int p_151632_7_)
/* 112:    */   {
/* 113:142 */     if (func_151618_b(p_151632_6_))
/* 114:    */     {
/* 115:144 */       p_151632_2_[(p_151632_3_ + p_151632_4_ * p_151632_5_)] = p_151632_6_;
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:148 */       int var8 = p_151632_1_[(p_151632_3_ + 1 + (p_151632_4_ + 1 - 1) * (p_151632_5_ + 2))];
/* 120:149 */       int var9 = p_151632_1_[(p_151632_3_ + 1 + 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2))];
/* 121:150 */       int var10 = p_151632_1_[(p_151632_3_ + 1 - 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2))];
/* 122:151 */       int var11 = p_151632_1_[(p_151632_3_ + 1 + (p_151632_4_ + 1 + 1) * (p_151632_5_ + 2))];
/* 123:153 */       if ((!func_151618_b(var8)) && (!func_151618_b(var9)) && (!func_151618_b(var10)) && (!func_151618_b(var11))) {
/* 124:155 */         p_151632_2_[(p_151632_3_ + p_151632_4_ * p_151632_5_)] = p_151632_6_;
/* 125:    */       } else {
/* 126:159 */         p_151632_2_[(p_151632_3_ + p_151632_4_ * p_151632_5_)] = p_151632_7_;
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   private boolean func_151631_c(int p_151631_1_)
/* 132:    */   {
/* 133:166 */     return (BiomeGenBase.func_150568_d(p_151631_1_) != null) && (BiomeGenBase.func_150568_d(p_151631_1_).func_150562_l() == BiomeGenJungle.class);
/* 134:    */   }
/* 135:    */   
/* 136:    */   private boolean func_151633_d(int p_151633_1_)
/* 137:    */   {
/* 138:171 */     return (BiomeGenBase.func_150568_d(p_151633_1_) != null) && ((BiomeGenBase.func_150568_d(p_151633_1_) instanceof BiomeGenMesa));
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerShore
 * JD-Core Version:    0.7.0.1
 */