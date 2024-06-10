/*   1:    */ package net.minecraft.world.gen.layer;
/*   2:    */ 
/*   3:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   4:    */ import org.apache.logging.log4j.LogManager;
/*   5:    */ import org.apache.logging.log4j.Logger;
/*   6:    */ 
/*   7:    */ public class GenLayerHills
/*   8:    */   extends GenLayer
/*   9:    */ {
/*  10:  9 */   private static final Logger logger = ;
/*  11:    */   private GenLayer field_151628_d;
/*  12:    */   private static final String __OBFID = "CL_00000563";
/*  13:    */   
/*  14:    */   public GenLayerHills(long p_i45479_1_, GenLayer p_i45479_3_, GenLayer p_i45479_4_)
/*  15:    */   {
/*  16: 15 */     super(p_i45479_1_);
/*  17: 16 */     this.parent = p_i45479_3_;
/*  18: 17 */     this.field_151628_d = p_i45479_4_;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int[] getInts(int par1, int par2, int par3, int par4)
/*  22:    */   {
/*  23: 26 */     int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
/*  24: 27 */     int[] var6 = this.field_151628_d.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
/*  25: 28 */     int[] var7 = IntCache.getIntCache(par3 * par4);
/*  26: 30 */     for (int var8 = 0; var8 < par4; var8++) {
/*  27: 32 */       for (int var9 = 0; var9 < par3; var9++)
/*  28:    */       {
/*  29: 34 */         initChunkSeed(var9 + par1, var8 + par2);
/*  30: 35 */         int var10 = var5[(var9 + 1 + (var8 + 1) * (par3 + 2))];
/*  31: 36 */         int var11 = var6[(var9 + 1 + (var8 + 1) * (par3 + 2))];
/*  32: 37 */         boolean var12 = (var11 - 2) % 29 == 0;
/*  33: 39 */         if (var10 > 255) {
/*  34: 41 */           logger.debug("old! " + var10);
/*  35:    */         }
/*  36: 44 */         if ((var10 != 0) && (var11 >= 2) && ((var11 - 2) % 29 == 1) && (var10 < 128))
/*  37:    */         {
/*  38: 46 */           if (BiomeGenBase.func_150568_d(var10 + 128) != null) {
/*  39: 48 */             var7[(var9 + var8 * par3)] = (var10 + 128);
/*  40:    */           } else {
/*  41: 52 */             var7[(var9 + var8 * par3)] = var10;
/*  42:    */           }
/*  43:    */         }
/*  44: 55 */         else if ((nextInt(3) != 0) && (!var12))
/*  45:    */         {
/*  46: 57 */           var7[(var9 + var8 * par3)] = var10;
/*  47:    */         }
/*  48:    */         else
/*  49:    */         {
/*  50: 61 */           int var13 = var10;
/*  51: 64 */           if (var10 == BiomeGenBase.desert.biomeID)
/*  52:    */           {
/*  53: 66 */             var13 = BiomeGenBase.desertHills.biomeID;
/*  54:    */           }
/*  55: 68 */           else if (var10 == BiomeGenBase.forest.biomeID)
/*  56:    */           {
/*  57: 70 */             var13 = BiomeGenBase.forestHills.biomeID;
/*  58:    */           }
/*  59: 72 */           else if (var10 == BiomeGenBase.field_150583_P.biomeID)
/*  60:    */           {
/*  61: 74 */             var13 = BiomeGenBase.field_150582_Q.biomeID;
/*  62:    */           }
/*  63: 76 */           else if (var10 == BiomeGenBase.field_150585_R.biomeID)
/*  64:    */           {
/*  65: 78 */             var13 = BiomeGenBase.plains.biomeID;
/*  66:    */           }
/*  67: 80 */           else if (var10 == BiomeGenBase.taiga.biomeID)
/*  68:    */           {
/*  69: 82 */             var13 = BiomeGenBase.taigaHills.biomeID;
/*  70:    */           }
/*  71: 84 */           else if (var10 == BiomeGenBase.field_150578_U.biomeID)
/*  72:    */           {
/*  73: 86 */             var13 = BiomeGenBase.field_150581_V.biomeID;
/*  74:    */           }
/*  75: 88 */           else if (var10 == BiomeGenBase.field_150584_S.biomeID)
/*  76:    */           {
/*  77: 90 */             var13 = BiomeGenBase.field_150579_T.biomeID;
/*  78:    */           }
/*  79: 92 */           else if (var10 == BiomeGenBase.plains.biomeID)
/*  80:    */           {
/*  81: 94 */             if (nextInt(3) == 0) {
/*  82: 96 */               var13 = BiomeGenBase.forestHills.biomeID;
/*  83:    */             } else {
/*  84:100 */               var13 = BiomeGenBase.forest.biomeID;
/*  85:    */             }
/*  86:    */           }
/*  87:103 */           else if (var10 == BiomeGenBase.icePlains.biomeID)
/*  88:    */           {
/*  89:105 */             var13 = BiomeGenBase.iceMountains.biomeID;
/*  90:    */           }
/*  91:107 */           else if (var10 == BiomeGenBase.jungle.biomeID)
/*  92:    */           {
/*  93:109 */             var13 = BiomeGenBase.jungleHills.biomeID;
/*  94:    */           }
/*  95:111 */           else if (var10 == BiomeGenBase.ocean.biomeID)
/*  96:    */           {
/*  97:113 */             var13 = BiomeGenBase.field_150575_M.biomeID;
/*  98:    */           }
/*  99:115 */           else if (var10 == BiomeGenBase.extremeHills.biomeID)
/* 100:    */           {
/* 101:117 */             var13 = BiomeGenBase.field_150580_W.biomeID;
/* 102:    */           }
/* 103:119 */           else if (var10 == BiomeGenBase.field_150588_X.biomeID)
/* 104:    */           {
/* 105:121 */             var13 = BiomeGenBase.field_150587_Y.biomeID;
/* 106:    */           }
/* 107:123 */           else if (func_151616_a(var10, BiomeGenBase.field_150607_aa.biomeID))
/* 108:    */           {
/* 109:125 */             var13 = BiomeGenBase.field_150589_Z.biomeID;
/* 110:    */           }
/* 111:127 */           else if ((var10 == BiomeGenBase.field_150575_M.biomeID) && (nextInt(3) == 0))
/* 112:    */           {
/* 113:129 */             int var14 = nextInt(2);
/* 114:131 */             if (var14 == 0) {
/* 115:133 */               var13 = BiomeGenBase.plains.biomeID;
/* 116:    */             } else {
/* 117:137 */               var13 = BiomeGenBase.forest.biomeID;
/* 118:    */             }
/* 119:    */           }
/* 120:141 */           if ((var12) && (var13 != var10)) {
/* 121:143 */             if (BiomeGenBase.func_150568_d(var13 + 128) != null) {
/* 122:145 */               var13 += 128;
/* 123:    */             } else {
/* 124:149 */               var13 = var10;
/* 125:    */             }
/* 126:    */           }
/* 127:153 */           if (var13 == var10)
/* 128:    */           {
/* 129:155 */             var7[(var9 + var8 * par3)] = var10;
/* 130:    */           }
/* 131:    */           else
/* 132:    */           {
/* 133:159 */             int var14 = var5[(var9 + 1 + (var8 + 1 - 1) * (par3 + 2))];
/* 134:160 */             int var15 = var5[(var9 + 1 + 1 + (var8 + 1) * (par3 + 2))];
/* 135:161 */             int var16 = var5[(var9 + 1 - 1 + (var8 + 1) * (par3 + 2))];
/* 136:162 */             int var17 = var5[(var9 + 1 + (var8 + 1 + 1) * (par3 + 2))];
/* 137:163 */             int var18 = 0;
/* 138:165 */             if (func_151616_a(var14, var10)) {
/* 139:167 */               var18++;
/* 140:    */             }
/* 141:170 */             if (func_151616_a(var15, var10)) {
/* 142:172 */               var18++;
/* 143:    */             }
/* 144:175 */             if (func_151616_a(var16, var10)) {
/* 145:177 */               var18++;
/* 146:    */             }
/* 147:180 */             if (func_151616_a(var17, var10)) {
/* 148:182 */               var18++;
/* 149:    */             }
/* 150:185 */             if (var18 >= 3) {
/* 151:187 */               var7[(var9 + var8 * par3)] = var13;
/* 152:    */             } else {
/* 153:191 */               var7[(var9 + var8 * par3)] = var10;
/* 154:    */             }
/* 155:    */           }
/* 156:    */         }
/* 157:    */       }
/* 158:    */     }
/* 159:198 */     return var7;
/* 160:    */   }
/* 161:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerHills
 * JD-Core Version:    0.7.0.1
 */