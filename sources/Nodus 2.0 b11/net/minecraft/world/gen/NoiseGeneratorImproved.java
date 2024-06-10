/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public class NoiseGeneratorImproved
/*   6:    */   extends NoiseGenerator
/*   7:    */ {
/*   8:    */   private int[] permutations;
/*   9:    */   public double xCoord;
/*  10:    */   public double yCoord;
/*  11:    */   public double zCoord;
/*  12:    */   private static final String __OBFID = "CL_00000534";
/*  13:    */   
/*  14:    */   public NoiseGeneratorImproved()
/*  15:    */   {
/*  16: 15 */     this(new Random());
/*  17:    */   }
/*  18:    */   
/*  19:    */   public NoiseGeneratorImproved(Random p_i45469_1_)
/*  20:    */   {
/*  21: 20 */     this.permutations = new int[512];
/*  22: 21 */     this.xCoord = (p_i45469_1_.nextDouble() * 256.0D);
/*  23: 22 */     this.yCoord = (p_i45469_1_.nextDouble() * 256.0D);
/*  24: 23 */     this.zCoord = (p_i45469_1_.nextDouble() * 256.0D);
/*  25: 26 */     for (int var2 = 0; var2 < 256; this.permutations[var2] = (var2++)) {}
/*  26: 31 */     for (var2 = 0; var2 < 256; var2++)
/*  27:    */     {
/*  28: 33 */       int var3 = p_i45469_1_.nextInt(256 - var2) + var2;
/*  29: 34 */       int var4 = this.permutations[var2];
/*  30: 35 */       this.permutations[var2] = this.permutations[var3];
/*  31: 36 */       this.permutations[var3] = var4;
/*  32: 37 */       this.permutations[(var2 + 256)] = this.permutations[var2];
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final double lerp(double par1, double par3, double par5)
/*  37:    */   {
/*  38: 43 */     return par3 + par1 * (par5 - par3);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final double func_76309_a(int par1, double par2, double par4)
/*  42:    */   {
/*  43: 48 */     int var6 = par1 & 0xF;
/*  44: 49 */     double var7 = (1 - ((var6 & 0x8) >> 3)) * par2;
/*  45: 50 */     double var9 = (var6 != 12) && (var6 != 14) ? par4 : var6 < 4 ? 0.0D : par2;
/*  46: 51 */     return ((var6 & 0x1) == 0 ? var7 : -var7) + ((var6 & 0x2) == 0 ? var9 : -var9);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final double grad(int par1, double par2, double par4, double par6)
/*  50:    */   {
/*  51: 56 */     int var8 = par1 & 0xF;
/*  52: 57 */     double var9 = var8 < 8 ? par2 : par4;
/*  53: 58 */     double var11 = (var8 != 12) && (var8 != 14) ? par6 : var8 < 4 ? par4 : par2;
/*  54: 59 */     return ((var8 & 0x1) == 0 ? var9 : -var9) + ((var8 & 0x2) == 0 ? var11 : -var11);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void populateNoiseArray(double[] par1ArrayOfDouble, double par2, double par4, double par6, int par8, int par9, int par10, double par11, double par13, double par15, double par17)
/*  58:    */   {
/*  59: 80 */     if (par9 == 1)
/*  60:    */     {
/*  61: 82 */       boolean var66 = false;
/*  62: 83 */       boolean var65 = false;
/*  63: 84 */       boolean var21 = false;
/*  64: 85 */       boolean var67 = false;
/*  65: 86 */       double var72 = 0.0D;
/*  66: 87 */       double var71 = 0.0D;
/*  67: 88 */       int var77 = 0;
/*  68: 89 */       double var74 = 1.0D / par17;
/*  69: 91 */       for (int var30 = 0; var30 < par8; var30++)
/*  70:    */       {
/*  71: 93 */         double var31 = par2 + var30 * par11 + this.xCoord;
/*  72: 94 */         int var78 = (int)var31;
/*  73: 96 */         if (var31 < var78) {
/*  74: 98 */           var78--;
/*  75:    */         }
/*  76:101 */         int var34 = var78 & 0xFF;
/*  77:102 */         var31 -= var78;
/*  78:103 */         double var35 = var31 * var31 * var31 * (var31 * (var31 * 6.0D - 15.0D) + 10.0D);
/*  79:105 */         for (int var37 = 0; var37 < par10; var37++)
/*  80:    */         {
/*  81:107 */           double var38 = par6 + var37 * par15 + this.zCoord;
/*  82:108 */           int var40 = (int)var38;
/*  83:110 */           if (var38 < var40) {
/*  84:112 */             var40--;
/*  85:    */           }
/*  86:115 */           int var41 = var40 & 0xFF;
/*  87:116 */           var38 -= var40;
/*  88:117 */           double var42 = var38 * var38 * var38 * (var38 * (var38 * 6.0D - 15.0D) + 10.0D);
/*  89:118 */           int var19 = this.permutations[var34] + 0;
/*  90:119 */           int var64 = this.permutations[var19] + var41;
/*  91:120 */           int var69 = this.permutations[(var34 + 1)] + 0;
/*  92:121 */           int var22 = this.permutations[var69] + var41;
/*  93:122 */           var72 = lerp(var35, func_76309_a(this.permutations[var64], var31, var38), grad(this.permutations[var22], var31 - 1.0D, 0.0D, var38));
/*  94:123 */           var71 = lerp(var35, grad(this.permutations[(var64 + 1)], var31, 0.0D, var38 - 1.0D), grad(this.permutations[(var22 + 1)], var31 - 1.0D, 0.0D, var38 - 1.0D));
/*  95:124 */           double var79 = lerp(var42, var72, var71);
/*  96:125 */           int var10001 = var77++;
/*  97:126 */           par1ArrayOfDouble[var10001] += var79 * var74;
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:132 */       int var19 = 0;
/* 104:133 */       double var20 = 1.0D / par17;
/* 105:134 */       int var22 = -1;
/* 106:135 */       boolean var23 = false;
/* 107:136 */       boolean var24 = false;
/* 108:137 */       boolean var25 = false;
/* 109:138 */       boolean var26 = false;
/* 110:139 */       boolean var27 = false;
/* 111:140 */       boolean var28 = false;
/* 112:141 */       double var29 = 0.0D;
/* 113:142 */       double var31 = 0.0D;
/* 114:143 */       double var33 = 0.0D;
/* 115:144 */       double var35 = 0.0D;
/* 116:146 */       for (int var37 = 0; var37 < par8; var37++)
/* 117:    */       {
/* 118:148 */         double var38 = par2 + var37 * par11 + this.xCoord;
/* 119:149 */         int var40 = (int)var38;
/* 120:151 */         if (var38 < var40) {
/* 121:153 */           var40--;
/* 122:    */         }
/* 123:156 */         int var41 = var40 & 0xFF;
/* 124:157 */         var38 -= var40;
/* 125:158 */         double var42 = var38 * var38 * var38 * (var38 * (var38 * 6.0D - 15.0D) + 10.0D);
/* 126:160 */         for (int var44 = 0; var44 < par10; var44++)
/* 127:    */         {
/* 128:162 */           double var45 = par6 + var44 * par15 + this.zCoord;
/* 129:163 */           int var47 = (int)var45;
/* 130:165 */           if (var45 < var47) {
/* 131:167 */             var47--;
/* 132:    */           }
/* 133:170 */           int var48 = var47 & 0xFF;
/* 134:171 */           var45 -= var47;
/* 135:172 */           double var49 = var45 * var45 * var45 * (var45 * (var45 * 6.0D - 15.0D) + 10.0D);
/* 136:174 */           for (int var51 = 0; var51 < par9; var51++)
/* 137:    */           {
/* 138:176 */             double var52 = par4 + var51 * par13 + this.yCoord;
/* 139:177 */             int var54 = (int)var52;
/* 140:179 */             if (var52 < var54) {
/* 141:181 */               var54--;
/* 142:    */             }
/* 143:184 */             int var55 = var54 & 0xFF;
/* 144:185 */             var52 -= var54;
/* 145:186 */             double var56 = var52 * var52 * var52 * (var52 * (var52 * 6.0D - 15.0D) + 10.0D);
/* 146:188 */             if ((var51 == 0) || (var55 != var22))
/* 147:    */             {
/* 148:190 */               var22 = var55;
/* 149:191 */               int var68 = this.permutations[var41] + var55;
/* 150:192 */               int var73 = this.permutations[var68] + var48;
/* 151:193 */               int var70 = this.permutations[(var68 + 1)] + var48;
/* 152:194 */               int var76 = this.permutations[(var41 + 1)] + var55;
/* 153:195 */               int var77 = this.permutations[var76] + var48;
/* 154:196 */               int var75 = this.permutations[(var76 + 1)] + var48;
/* 155:197 */               var29 = lerp(var42, grad(this.permutations[var73], var38, var52, var45), grad(this.permutations[var77], var38 - 1.0D, var52, var45));
/* 156:198 */               var31 = lerp(var42, grad(this.permutations[var70], var38, var52 - 1.0D, var45), grad(this.permutations[var75], var38 - 1.0D, var52 - 1.0D, var45));
/* 157:199 */               var33 = lerp(var42, grad(this.permutations[(var73 + 1)], var38, var52, var45 - 1.0D), grad(this.permutations[(var77 + 1)], var38 - 1.0D, var52, var45 - 1.0D));
/* 158:200 */               var35 = lerp(var42, grad(this.permutations[(var70 + 1)], var38, var52 - 1.0D, var45 - 1.0D), grad(this.permutations[(var75 + 1)], var38 - 1.0D, var52 - 1.0D, var45 - 1.0D));
/* 159:    */             }
/* 160:203 */             double var58 = lerp(var56, var29, var31);
/* 161:204 */             double var60 = lerp(var56, var33, var35);
/* 162:205 */             double var62 = lerp(var49, var58, var60);
/* 163:206 */             int var10001 = var19++;
/* 164:207 */             par1ArrayOfDouble[var10001] += var62 * var20;
/* 165:    */           }
/* 166:    */         }
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.NoiseGeneratorImproved
 * JD-Core Version:    0.7.0.1
 */