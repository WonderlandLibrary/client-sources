/*   1:    */ package net.minecraft.world.gen.layer;
/*   2:    */ 
/*   3:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   4:    */ import net.minecraft.world.biome.BiomeGenBase.TempCategory;
/*   5:    */ 
/*   6:    */ public class GenLayerBiomeEdge
/*   7:    */   extends GenLayer
/*   8:    */ {
/*   9:    */   private static final String __OBFID = "CL_00000554";
/*  10:    */   
/*  11:    */   public GenLayerBiomeEdge(long p_i45475_1_, GenLayer p_i45475_3_)
/*  12:    */   {
/*  13: 11 */     super(p_i45475_1_);
/*  14: 12 */     this.parent = p_i45475_3_;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public int[] getInts(int par1, int par2, int par3, int par4)
/*  18:    */   {
/*  19: 21 */     int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
/*  20: 22 */     int[] var6 = IntCache.getIntCache(par3 * par4);
/*  21: 24 */     for (int var7 = 0; var7 < par4; var7++) {
/*  22: 26 */       for (int var8 = 0; var8 < par3; var8++)
/*  23:    */       {
/*  24: 28 */         initChunkSeed(var8 + par1, var7 + par2);
/*  25: 29 */         int var9 = var5[(var8 + 1 + (var7 + 1) * (par3 + 2))];
/*  26: 31 */         if ((!func_151636_a(var5, var6, var8, var7, par3, var9, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID)) && (!func_151635_b(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150607_aa.biomeID, BiomeGenBase.field_150589_Z.biomeID)) && (!func_151635_b(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150608_ab.biomeID, BiomeGenBase.field_150589_Z.biomeID)) && (!func_151635_b(var5, var6, var8, var7, par3, var9, BiomeGenBase.field_150578_U.biomeID, BiomeGenBase.taiga.biomeID))) {
/*  27: 38 */           if (var9 == BiomeGenBase.desert.biomeID)
/*  28:    */           {
/*  29: 40 */             int var10 = var5[(var8 + 1 + (var7 + 1 - 1) * (par3 + 2))];
/*  30: 41 */             int var11 = var5[(var8 + 1 + 1 + (var7 + 1) * (par3 + 2))];
/*  31: 42 */             int var12 = var5[(var8 + 1 - 1 + (var7 + 1) * (par3 + 2))];
/*  32: 43 */             int var13 = var5[(var8 + 1 + (var7 + 1 + 1) * (par3 + 2))];
/*  33: 45 */             if ((var10 != BiomeGenBase.icePlains.biomeID) && (var11 != BiomeGenBase.icePlains.biomeID) && (var12 != BiomeGenBase.icePlains.biomeID) && (var13 != BiomeGenBase.icePlains.biomeID)) {
/*  34: 47 */               var6[(var8 + var7 * par3)] = var9;
/*  35:    */             } else {
/*  36: 51 */               var6[(var8 + var7 * par3)] = BiomeGenBase.field_150580_W.biomeID;
/*  37:    */             }
/*  38:    */           }
/*  39: 54 */           else if (var9 == BiomeGenBase.swampland.biomeID)
/*  40:    */           {
/*  41: 56 */             int var10 = var5[(var8 + 1 + (var7 + 1 - 1) * (par3 + 2))];
/*  42: 57 */             int var11 = var5[(var8 + 1 + 1 + (var7 + 1) * (par3 + 2))];
/*  43: 58 */             int var12 = var5[(var8 + 1 - 1 + (var7 + 1) * (par3 + 2))];
/*  44: 59 */             int var13 = var5[(var8 + 1 + (var7 + 1 + 1) * (par3 + 2))];
/*  45: 61 */             if ((var10 != BiomeGenBase.desert.biomeID) && (var11 != BiomeGenBase.desert.biomeID) && (var12 != BiomeGenBase.desert.biomeID) && (var13 != BiomeGenBase.desert.biomeID) && (var10 != BiomeGenBase.field_150584_S.biomeID) && (var11 != BiomeGenBase.field_150584_S.biomeID) && (var12 != BiomeGenBase.field_150584_S.biomeID) && (var13 != BiomeGenBase.field_150584_S.biomeID) && (var10 != BiomeGenBase.icePlains.biomeID) && (var11 != BiomeGenBase.icePlains.biomeID) && (var12 != BiomeGenBase.icePlains.biomeID) && (var13 != BiomeGenBase.icePlains.biomeID))
/*  46:    */             {
/*  47: 63 */               if ((var10 != BiomeGenBase.jungle.biomeID) && (var13 != BiomeGenBase.jungle.biomeID) && (var11 != BiomeGenBase.jungle.biomeID) && (var12 != BiomeGenBase.jungle.biomeID)) {
/*  48: 65 */                 var6[(var8 + var7 * par3)] = var9;
/*  49:    */               } else {
/*  50: 69 */                 var6[(var8 + var7 * par3)] = BiomeGenBase.field_150574_L.biomeID;
/*  51:    */               }
/*  52:    */             }
/*  53:    */             else {
/*  54: 74 */               var6[(var8 + var7 * par3)] = BiomeGenBase.plains.biomeID;
/*  55:    */             }
/*  56:    */           }
/*  57:    */           else
/*  58:    */           {
/*  59: 79 */             var6[(var8 + var7 * par3)] = var9;
/*  60:    */           }
/*  61:    */         }
/*  62:    */       }
/*  63:    */     }
/*  64: 85 */     return var6;
/*  65:    */   }
/*  66:    */   
/*  67:    */   private boolean func_151636_a(int[] p_151636_1_, int[] p_151636_2_, int p_151636_3_, int p_151636_4_, int p_151636_5_, int p_151636_6_, int p_151636_7_, int p_151636_8_)
/*  68:    */   {
/*  69: 90 */     if (!func_151616_a(p_151636_6_, p_151636_7_)) {
/*  70: 92 */       return false;
/*  71:    */     }
/*  72: 96 */     int var9 = p_151636_1_[(p_151636_3_ + 1 + (p_151636_4_ + 1 - 1) * (p_151636_5_ + 2))];
/*  73: 97 */     int var10 = p_151636_1_[(p_151636_3_ + 1 + 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2))];
/*  74: 98 */     int var11 = p_151636_1_[(p_151636_3_ + 1 - 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2))];
/*  75: 99 */     int var12 = p_151636_1_[(p_151636_3_ + 1 + (p_151636_4_ + 1 + 1) * (p_151636_5_ + 2))];
/*  76:101 */     if ((func_151634_b(var9, p_151636_7_)) && (func_151634_b(var10, p_151636_7_)) && (func_151634_b(var11, p_151636_7_)) && (func_151634_b(var12, p_151636_7_))) {
/*  77:103 */       p_151636_2_[(p_151636_3_ + p_151636_4_ * p_151636_5_)] = p_151636_6_;
/*  78:    */     } else {
/*  79:107 */       p_151636_2_[(p_151636_3_ + p_151636_4_ * p_151636_5_)] = p_151636_8_;
/*  80:    */     }
/*  81:110 */     return true;
/*  82:    */   }
/*  83:    */   
/*  84:    */   private boolean func_151635_b(int[] p_151635_1_, int[] p_151635_2_, int p_151635_3_, int p_151635_4_, int p_151635_5_, int p_151635_6_, int p_151635_7_, int p_151635_8_)
/*  85:    */   {
/*  86:116 */     if (p_151635_6_ != p_151635_7_) {
/*  87:118 */       return false;
/*  88:    */     }
/*  89:122 */     int var9 = p_151635_1_[(p_151635_3_ + 1 + (p_151635_4_ + 1 - 1) * (p_151635_5_ + 2))];
/*  90:123 */     int var10 = p_151635_1_[(p_151635_3_ + 1 + 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2))];
/*  91:124 */     int var11 = p_151635_1_[(p_151635_3_ + 1 - 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2))];
/*  92:125 */     int var12 = p_151635_1_[(p_151635_3_ + 1 + (p_151635_4_ + 1 + 1) * (p_151635_5_ + 2))];
/*  93:127 */     if ((func_151616_a(var9, p_151635_7_)) && (func_151616_a(var10, p_151635_7_)) && (func_151616_a(var11, p_151635_7_)) && (func_151616_a(var12, p_151635_7_))) {
/*  94:129 */       p_151635_2_[(p_151635_3_ + p_151635_4_ * p_151635_5_)] = p_151635_6_;
/*  95:    */     } else {
/*  96:133 */       p_151635_2_[(p_151635_3_ + p_151635_4_ * p_151635_5_)] = p_151635_8_;
/*  97:    */     }
/*  98:136 */     return true;
/*  99:    */   }
/* 100:    */   
/* 101:    */   private boolean func_151634_b(int p_151634_1_, int p_151634_2_)
/* 102:    */   {
/* 103:142 */     if (func_151616_a(p_151634_1_, p_151634_2_)) {
/* 104:144 */       return true;
/* 105:    */     }
/* 106:146 */     if ((BiomeGenBase.func_150568_d(p_151634_1_) != null) && (BiomeGenBase.func_150568_d(p_151634_2_) != null))
/* 107:    */     {
/* 108:148 */       BiomeGenBase.TempCategory var3 = BiomeGenBase.func_150568_d(p_151634_1_).func_150561_m();
/* 109:149 */       BiomeGenBase.TempCategory var4 = BiomeGenBase.func_150568_d(p_151634_2_).func_150561_m();
/* 110:150 */       return (var3 == var4) || (var3 == BiomeGenBase.TempCategory.MEDIUM) || (var4 == BiomeGenBase.TempCategory.MEDIUM);
/* 111:    */     }
/* 112:154 */     return false;
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerBiomeEdge
 * JD-Core Version:    0.7.0.1
 */