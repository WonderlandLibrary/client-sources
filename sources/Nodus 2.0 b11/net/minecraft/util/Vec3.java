/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ public class Vec3
/*   4:    */ {
/*   5:  8 */   public static final Vec3Pool fakePool = new Vec3Pool(-1, -1);
/*   6:    */   public final Vec3Pool myVec3LocalPool;
/*   7:    */   public double xCoord;
/*   8:    */   public double yCoord;
/*   9:    */   public double zCoord;
/*  10:    */   private static final String __OBFID = "CL_00000612";
/*  11:    */   
/*  12:    */   public static Vec3 createVectorHelper(double par0, double par2, double par4)
/*  13:    */   {
/*  14: 27 */     return new Vec3(fakePool, par0, par2, par4);
/*  15:    */   }
/*  16:    */   
/*  17:    */   protected Vec3(Vec3Pool par1Vec3Pool, double par2, double par4, double par6)
/*  18:    */   {
/*  19: 32 */     if (par2 == -0.0D) {
/*  20: 34 */       par2 = 0.0D;
/*  21:    */     }
/*  22: 37 */     if (par4 == -0.0D) {
/*  23: 39 */       par4 = 0.0D;
/*  24:    */     }
/*  25: 42 */     if (par6 == -0.0D) {
/*  26: 44 */       par6 = 0.0D;
/*  27:    */     }
/*  28: 47 */     this.xCoord = par2;
/*  29: 48 */     this.yCoord = par4;
/*  30: 49 */     this.zCoord = par6;
/*  31: 50 */     this.myVec3LocalPool = par1Vec3Pool;
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected Vec3 setComponents(double par1, double par3, double par5)
/*  35:    */   {
/*  36: 58 */     this.xCoord = par1;
/*  37: 59 */     this.yCoord = par3;
/*  38: 60 */     this.zCoord = par5;
/*  39: 61 */     return this;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Vec3 subtract(Vec3 par1Vec3)
/*  43:    */   {
/*  44: 69 */     return this.myVec3LocalPool.getVecFromPool(par1Vec3.xCoord - this.xCoord, par1Vec3.yCoord - this.yCoord, par1Vec3.zCoord - this.zCoord);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Vec3 normalize()
/*  48:    */   {
/*  49: 77 */     double var1 = MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
/*  50: 78 */     return var1 < 0.0001D ? this.myVec3LocalPool.getVecFromPool(0.0D, 0.0D, 0.0D) : this.myVec3LocalPool.getVecFromPool(this.xCoord / var1, this.yCoord / var1, this.zCoord / var1);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public double dotProduct(Vec3 par1Vec3)
/*  54:    */   {
/*  55: 83 */     return this.xCoord * par1Vec3.xCoord + this.yCoord * par1Vec3.yCoord + this.zCoord * par1Vec3.zCoord;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Vec3 crossProduct(Vec3 par1Vec3)
/*  59:    */   {
/*  60: 91 */     return this.myVec3LocalPool.getVecFromPool(this.yCoord * par1Vec3.zCoord - this.zCoord * par1Vec3.yCoord, this.zCoord * par1Vec3.xCoord - this.xCoord * par1Vec3.zCoord, this.xCoord * par1Vec3.yCoord - this.yCoord * par1Vec3.xCoord);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Vec3 addVector(double par1, double par3, double par5)
/*  64:    */   {
/*  65:100 */     return this.myVec3LocalPool.getVecFromPool(this.xCoord + par1, this.yCoord + par3, this.zCoord + par5);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public double distanceTo(Vec3 par1Vec3)
/*  69:    */   {
/*  70:108 */     double var2 = par1Vec3.xCoord - this.xCoord;
/*  71:109 */     double var4 = par1Vec3.yCoord - this.yCoord;
/*  72:110 */     double var6 = par1Vec3.zCoord - this.zCoord;
/*  73:111 */     return MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public double squareDistanceTo(Vec3 par1Vec3)
/*  77:    */   {
/*  78:119 */     double var2 = par1Vec3.xCoord - this.xCoord;
/*  79:120 */     double var4 = par1Vec3.yCoord - this.yCoord;
/*  80:121 */     double var6 = par1Vec3.zCoord - this.zCoord;
/*  81:122 */     return var2 * var2 + var4 * var4 + var6 * var6;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public double squareDistanceTo(double par1, double par3, double par5)
/*  85:    */   {
/*  86:130 */     double var7 = par1 - this.xCoord;
/*  87:131 */     double var9 = par3 - this.yCoord;
/*  88:132 */     double var11 = par5 - this.zCoord;
/*  89:133 */     return var7 * var7 + var9 * var9 + var11 * var11;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public double lengthVector()
/*  93:    */   {
/*  94:141 */     return MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Vec3 getIntermediateWithXValue(Vec3 par1Vec3, double par2)
/*  98:    */   {
/*  99:150 */     double var4 = par1Vec3.xCoord - this.xCoord;
/* 100:151 */     double var6 = par1Vec3.yCoord - this.yCoord;
/* 101:152 */     double var8 = par1Vec3.zCoord - this.zCoord;
/* 102:154 */     if (var4 * var4 < 1.000000011686097E-007D) {
/* 103:156 */       return null;
/* 104:    */     }
/* 105:160 */     double var10 = (par2 - this.xCoord) / var4;
/* 106:161 */     return (var10 >= 0.0D) && (var10 <= 1.0D) ? this.myVec3LocalPool.getVecFromPool(this.xCoord + var4 * var10, this.yCoord + var6 * var10, this.zCoord + var8 * var10) : null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Vec3 getIntermediateWithYValue(Vec3 par1Vec3, double par2)
/* 110:    */   {
/* 111:171 */     double var4 = par1Vec3.xCoord - this.xCoord;
/* 112:172 */     double var6 = par1Vec3.yCoord - this.yCoord;
/* 113:173 */     double var8 = par1Vec3.zCoord - this.zCoord;
/* 114:175 */     if (var6 * var6 < 1.000000011686097E-007D) {
/* 115:177 */       return null;
/* 116:    */     }
/* 117:181 */     double var10 = (par2 - this.yCoord) / var6;
/* 118:182 */     return (var10 >= 0.0D) && (var10 <= 1.0D) ? this.myVec3LocalPool.getVecFromPool(this.xCoord + var4 * var10, this.yCoord + var6 * var10, this.zCoord + var8 * var10) : null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Vec3 getIntermediateWithZValue(Vec3 par1Vec3, double par2)
/* 122:    */   {
/* 123:192 */     double var4 = par1Vec3.xCoord - this.xCoord;
/* 124:193 */     double var6 = par1Vec3.yCoord - this.yCoord;
/* 125:194 */     double var8 = par1Vec3.zCoord - this.zCoord;
/* 126:196 */     if (var8 * var8 < 1.000000011686097E-007D) {
/* 127:198 */       return null;
/* 128:    */     }
/* 129:202 */     double var10 = (par2 - this.zCoord) / var8;
/* 130:203 */     return (var10 >= 0.0D) && (var10 <= 1.0D) ? this.myVec3LocalPool.getVecFromPool(this.xCoord + var4 * var10, this.yCoord + var6 * var10, this.zCoord + var8 * var10) : null;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String toString()
/* 134:    */   {
/* 135:209 */     return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void rotateAroundX(float par1)
/* 139:    */   {
/* 140:217 */     float var2 = MathHelper.cos(par1);
/* 141:218 */     float var3 = MathHelper.sin(par1);
/* 142:219 */     double var4 = this.xCoord;
/* 143:220 */     double var6 = this.yCoord * var2 + this.zCoord * var3;
/* 144:221 */     double var8 = this.zCoord * var2 - this.yCoord * var3;
/* 145:222 */     this.xCoord = var4;
/* 146:223 */     this.yCoord = var6;
/* 147:224 */     this.zCoord = var8;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void rotateAroundY(float par1)
/* 151:    */   {
/* 152:232 */     float var2 = MathHelper.cos(par1);
/* 153:233 */     float var3 = MathHelper.sin(par1);
/* 154:234 */     double var4 = this.xCoord * var2 + this.zCoord * var3;
/* 155:235 */     double var6 = this.yCoord;
/* 156:236 */     double var8 = this.zCoord * var2 - this.xCoord * var3;
/* 157:237 */     this.xCoord = var4;
/* 158:238 */     this.yCoord = var6;
/* 159:239 */     this.zCoord = var8;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void rotateAroundZ(float par1)
/* 163:    */   {
/* 164:247 */     float var2 = MathHelper.cos(par1);
/* 165:248 */     float var3 = MathHelper.sin(par1);
/* 166:249 */     double var4 = this.xCoord * var2 + this.yCoord * var3;
/* 167:250 */     double var6 = this.yCoord * var2 - this.xCoord * var3;
/* 168:251 */     double var8 = this.zCoord;
/* 169:252 */     this.xCoord = var4;
/* 170:253 */     this.yCoord = var6;
/* 171:254 */     this.zCoord = var8;
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.Vec3
 * JD-Core Version:    0.7.0.1
 */