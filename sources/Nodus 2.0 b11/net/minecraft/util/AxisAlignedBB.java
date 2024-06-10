/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ public class AxisAlignedBB
/*   4:    */ {
/*   5:  6 */   private static final ThreadLocal theAABBLocalPool = new ThreadLocal()
/*   6:    */   {
/*   7:    */     private static final String __OBFID = "CL_00000608";
/*   8:    */     
/*   9:    */     protected AABBPool initialValue()
/*  10:    */     {
/*  11: 11 */       return new AABBPool(300, 2000);
/*  12:    */     }
/*  13:    */   };
/*  14:    */   public double minX;
/*  15:    */   public double minY;
/*  16:    */   public double minZ;
/*  17:    */   public double maxX;
/*  18:    */   public double maxY;
/*  19:    */   public double maxZ;
/*  20:    */   private static final String __OBFID = "CL_00000607";
/*  21:    */   
/*  22:    */   public static AxisAlignedBB getBoundingBox(double par0, double par2, double par4, double par6, double par8, double par10)
/*  23:    */   {
/*  24: 27 */     return new AxisAlignedBB(par0, par2, par4, par6, par8, par10);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static AABBPool getAABBPool()
/*  28:    */   {
/*  29: 35 */     return (AABBPool)theAABBLocalPool.get();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public AxisAlignedBB(double par1, double par3, double par5, double par7, double par9, double par11)
/*  33:    */   {
/*  34: 40 */     this.minX = par1;
/*  35: 41 */     this.minY = par3;
/*  36: 42 */     this.minZ = par5;
/*  37: 43 */     this.maxX = par7;
/*  38: 44 */     this.maxY = par9;
/*  39: 45 */     this.maxZ = par11;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public AxisAlignedBB setBounds(double par1, double par3, double par5, double par7, double par9, double par11)
/*  43:    */   {
/*  44: 53 */     this.minX = par1;
/*  45: 54 */     this.minY = par3;
/*  46: 55 */     this.minZ = par5;
/*  47: 56 */     this.maxX = par7;
/*  48: 57 */     this.maxY = par9;
/*  49: 58 */     this.maxZ = par11;
/*  50: 59 */     return this;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public AxisAlignedBB addCoord(double par1, double par3, double par5)
/*  54:    */   {
/*  55: 67 */     double var7 = this.minX;
/*  56: 68 */     double var9 = this.minY;
/*  57: 69 */     double var11 = this.minZ;
/*  58: 70 */     double var13 = this.maxX;
/*  59: 71 */     double var15 = this.maxY;
/*  60: 72 */     double var17 = this.maxZ;
/*  61: 74 */     if (par1 < 0.0D) {
/*  62: 76 */       var7 += par1;
/*  63:    */     }
/*  64: 79 */     if (par1 > 0.0D) {
/*  65: 81 */       var13 += par1;
/*  66:    */     }
/*  67: 84 */     if (par3 < 0.0D) {
/*  68: 86 */       var9 += par3;
/*  69:    */     }
/*  70: 89 */     if (par3 > 0.0D) {
/*  71: 91 */       var15 += par3;
/*  72:    */     }
/*  73: 94 */     if (par5 < 0.0D) {
/*  74: 96 */       var11 += par5;
/*  75:    */     }
/*  76: 99 */     if (par5 > 0.0D) {
/*  77:101 */       var17 += par5;
/*  78:    */     }
/*  79:104 */     return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public AxisAlignedBB expand(double par1, double par3, double par5)
/*  83:    */   {
/*  84:113 */     double var7 = this.minX - par1;
/*  85:114 */     double var9 = this.minY - par3;
/*  86:115 */     double var11 = this.minZ - par5;
/*  87:116 */     double var13 = this.maxX + par1;
/*  88:117 */     double var15 = this.maxY + par3;
/*  89:118 */     double var17 = this.maxZ + par5;
/*  90:119 */     return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public AxisAlignedBB func_111270_a(AxisAlignedBB par1AxisAlignedBB)
/*  94:    */   {
/*  95:124 */     double var2 = Math.min(this.minX, par1AxisAlignedBB.minX);
/*  96:125 */     double var4 = Math.min(this.minY, par1AxisAlignedBB.minY);
/*  97:126 */     double var6 = Math.min(this.minZ, par1AxisAlignedBB.minZ);
/*  98:127 */     double var8 = Math.max(this.maxX, par1AxisAlignedBB.maxX);
/*  99:128 */     double var10 = Math.max(this.maxY, par1AxisAlignedBB.maxY);
/* 100:129 */     double var12 = Math.max(this.maxZ, par1AxisAlignedBB.maxZ);
/* 101:130 */     return getAABBPool().getAABB(var2, var4, var6, var8, var10, var12);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public AxisAlignedBB getOffsetBoundingBox(double par1, double par3, double par5)
/* 105:    */   {
/* 106:139 */     return getAABBPool().getAABB(this.minX + par1, this.minY + par3, this.minZ + par5, this.maxX + par1, this.maxY + par3, this.maxZ + par5);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public double calculateXOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
/* 110:    */   {
/* 111:149 */     if ((par1AxisAlignedBB.maxY > this.minY) && (par1AxisAlignedBB.minY < this.maxY))
/* 112:    */     {
/* 113:151 */       if ((par1AxisAlignedBB.maxZ > this.minZ) && (par1AxisAlignedBB.minZ < this.maxZ))
/* 114:    */       {
/* 115:155 */         if ((par2 > 0.0D) && (par1AxisAlignedBB.maxX <= this.minX))
/* 116:    */         {
/* 117:157 */           double var4 = this.minX - par1AxisAlignedBB.maxX;
/* 118:159 */           if (var4 < par2) {
/* 119:161 */             par2 = var4;
/* 120:    */           }
/* 121:    */         }
/* 122:165 */         if ((par2 < 0.0D) && (par1AxisAlignedBB.minX >= this.maxX))
/* 123:    */         {
/* 124:167 */           double var4 = this.maxX - par1AxisAlignedBB.minX;
/* 125:169 */           if (var4 > par2) {
/* 126:171 */             par2 = var4;
/* 127:    */           }
/* 128:    */         }
/* 129:175 */         return par2;
/* 130:    */       }
/* 131:179 */       return par2;
/* 132:    */     }
/* 133:184 */     return par2;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public double calculateYOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
/* 137:    */   {
/* 138:195 */     if ((par1AxisAlignedBB.maxX > this.minX) && (par1AxisAlignedBB.minX < this.maxX))
/* 139:    */     {
/* 140:197 */       if ((par1AxisAlignedBB.maxZ > this.minZ) && (par1AxisAlignedBB.minZ < this.maxZ))
/* 141:    */       {
/* 142:201 */         if ((par2 > 0.0D) && (par1AxisAlignedBB.maxY <= this.minY))
/* 143:    */         {
/* 144:203 */           double var4 = this.minY - par1AxisAlignedBB.maxY;
/* 145:205 */           if (var4 < par2) {
/* 146:207 */             par2 = var4;
/* 147:    */           }
/* 148:    */         }
/* 149:211 */         if ((par2 < 0.0D) && (par1AxisAlignedBB.minY >= this.maxY))
/* 150:    */         {
/* 151:213 */           double var4 = this.maxY - par1AxisAlignedBB.minY;
/* 152:215 */           if (var4 > par2) {
/* 153:217 */             par2 = var4;
/* 154:    */           }
/* 155:    */         }
/* 156:221 */         return par2;
/* 157:    */       }
/* 158:225 */       return par2;
/* 159:    */     }
/* 160:230 */     return par2;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public double calculateZOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
/* 164:    */   {
/* 165:241 */     if ((par1AxisAlignedBB.maxX > this.minX) && (par1AxisAlignedBB.minX < this.maxX))
/* 166:    */     {
/* 167:243 */       if ((par1AxisAlignedBB.maxY > this.minY) && (par1AxisAlignedBB.minY < this.maxY))
/* 168:    */       {
/* 169:247 */         if ((par2 > 0.0D) && (par1AxisAlignedBB.maxZ <= this.minZ))
/* 170:    */         {
/* 171:249 */           double var4 = this.minZ - par1AxisAlignedBB.maxZ;
/* 172:251 */           if (var4 < par2) {
/* 173:253 */             par2 = var4;
/* 174:    */           }
/* 175:    */         }
/* 176:257 */         if ((par2 < 0.0D) && (par1AxisAlignedBB.minZ >= this.maxZ))
/* 177:    */         {
/* 178:259 */           double var4 = this.maxZ - par1AxisAlignedBB.minZ;
/* 179:261 */           if (var4 > par2) {
/* 180:263 */             par2 = var4;
/* 181:    */           }
/* 182:    */         }
/* 183:267 */         return par2;
/* 184:    */       }
/* 185:271 */       return par2;
/* 186:    */     }
/* 187:276 */     return par2;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean intersectsWith(AxisAlignedBB par1AxisAlignedBB)
/* 191:    */   {
/* 192:285 */     return (par1AxisAlignedBB.maxZ > this.minZ) && (par1AxisAlignedBB.minZ < this.maxZ);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public AxisAlignedBB offset(double par1, double par3, double par5)
/* 196:    */   {
/* 197:293 */     this.minX += par1;
/* 198:294 */     this.minY += par3;
/* 199:295 */     this.minZ += par5;
/* 200:296 */     this.maxX += par1;
/* 201:297 */     this.maxY += par3;
/* 202:298 */     this.maxZ += par5;
/* 203:299 */     return this;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean isVecInside(Vec3 par1Vec3)
/* 207:    */   {
/* 208:307 */     return (par1Vec3.zCoord > this.minZ) && (par1Vec3.zCoord < this.maxZ);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public double getAverageEdgeLength()
/* 212:    */   {
/* 213:315 */     double var1 = this.maxX - this.minX;
/* 214:316 */     double var3 = this.maxY - this.minY;
/* 215:317 */     double var5 = this.maxZ - this.minZ;
/* 216:318 */     return (var1 + var3 + var5) / 3.0D;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public AxisAlignedBB contract(double par1, double par3, double par5)
/* 220:    */   {
/* 221:326 */     double var7 = this.minX + par1;
/* 222:327 */     double var9 = this.minY + par3;
/* 223:328 */     double var11 = this.minZ + par5;
/* 224:329 */     double var13 = this.maxX - par1;
/* 225:330 */     double var15 = this.maxY - par3;
/* 226:331 */     double var17 = this.maxZ - par5;
/* 227:332 */     return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public AxisAlignedBB copy()
/* 231:    */   {
/* 232:340 */     return getAABBPool().getAABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public MovingObjectPosition calculateIntercept(Vec3 par1Vec3, Vec3 par2Vec3)
/* 236:    */   {
/* 237:345 */     Vec3 var3 = par1Vec3.getIntermediateWithXValue(par2Vec3, this.minX);
/* 238:346 */     Vec3 var4 = par1Vec3.getIntermediateWithXValue(par2Vec3, this.maxX);
/* 239:347 */     Vec3 var5 = par1Vec3.getIntermediateWithYValue(par2Vec3, this.minY);
/* 240:348 */     Vec3 var6 = par1Vec3.getIntermediateWithYValue(par2Vec3, this.maxY);
/* 241:349 */     Vec3 var7 = par1Vec3.getIntermediateWithZValue(par2Vec3, this.minZ);
/* 242:350 */     Vec3 var8 = par1Vec3.getIntermediateWithZValue(par2Vec3, this.maxZ);
/* 243:352 */     if (!isVecInYZ(var3)) {
/* 244:354 */       var3 = null;
/* 245:    */     }
/* 246:357 */     if (!isVecInYZ(var4)) {
/* 247:359 */       var4 = null;
/* 248:    */     }
/* 249:362 */     if (!isVecInXZ(var5)) {
/* 250:364 */       var5 = null;
/* 251:    */     }
/* 252:367 */     if (!isVecInXZ(var6)) {
/* 253:369 */       var6 = null;
/* 254:    */     }
/* 255:372 */     if (!isVecInXY(var7)) {
/* 256:374 */       var7 = null;
/* 257:    */     }
/* 258:377 */     if (!isVecInXY(var8)) {
/* 259:379 */       var8 = null;
/* 260:    */     }
/* 261:382 */     Vec3 var9 = null;
/* 262:384 */     if ((var3 != null) && ((var9 == null) || (par1Vec3.squareDistanceTo(var3) < par1Vec3.squareDistanceTo(var9)))) {
/* 263:386 */       var9 = var3;
/* 264:    */     }
/* 265:389 */     if ((var4 != null) && ((var9 == null) || (par1Vec3.squareDistanceTo(var4) < par1Vec3.squareDistanceTo(var9)))) {
/* 266:391 */       var9 = var4;
/* 267:    */     }
/* 268:394 */     if ((var5 != null) && ((var9 == null) || (par1Vec3.squareDistanceTo(var5) < par1Vec3.squareDistanceTo(var9)))) {
/* 269:396 */       var9 = var5;
/* 270:    */     }
/* 271:399 */     if ((var6 != null) && ((var9 == null) || (par1Vec3.squareDistanceTo(var6) < par1Vec3.squareDistanceTo(var9)))) {
/* 272:401 */       var9 = var6;
/* 273:    */     }
/* 274:404 */     if ((var7 != null) && ((var9 == null) || (par1Vec3.squareDistanceTo(var7) < par1Vec3.squareDistanceTo(var9)))) {
/* 275:406 */       var9 = var7;
/* 276:    */     }
/* 277:409 */     if ((var8 != null) && ((var9 == null) || (par1Vec3.squareDistanceTo(var8) < par1Vec3.squareDistanceTo(var9)))) {
/* 278:411 */       var9 = var8;
/* 279:    */     }
/* 280:414 */     if (var9 == null) {
/* 281:416 */       return null;
/* 282:    */     }
/* 283:420 */     byte var10 = -1;
/* 284:422 */     if (var9 == var3) {
/* 285:424 */       var10 = 4;
/* 286:    */     }
/* 287:427 */     if (var9 == var4) {
/* 288:429 */       var10 = 5;
/* 289:    */     }
/* 290:432 */     if (var9 == var5) {
/* 291:434 */       var10 = 0;
/* 292:    */     }
/* 293:437 */     if (var9 == var6) {
/* 294:439 */       var10 = 1;
/* 295:    */     }
/* 296:442 */     if (var9 == var7) {
/* 297:444 */       var10 = 2;
/* 298:    */     }
/* 299:447 */     if (var9 == var8) {
/* 300:449 */       var10 = 3;
/* 301:    */     }
/* 302:452 */     return new MovingObjectPosition(0, 0, 0, var10, var9);
/* 303:    */   }
/* 304:    */   
/* 305:    */   private boolean isVecInYZ(Vec3 par1Vec3)
/* 306:    */   {
/* 307:461 */     return par1Vec3 != null;
/* 308:    */   }
/* 309:    */   
/* 310:    */   private boolean isVecInXZ(Vec3 par1Vec3)
/* 311:    */   {
/* 312:469 */     return par1Vec3 != null;
/* 313:    */   }
/* 314:    */   
/* 315:    */   private boolean isVecInXY(Vec3 par1Vec3)
/* 316:    */   {
/* 317:477 */     return par1Vec3 != null;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setBB(AxisAlignedBB par1AxisAlignedBB)
/* 321:    */   {
/* 322:485 */     this.minX = par1AxisAlignedBB.minX;
/* 323:486 */     this.minY = par1AxisAlignedBB.minY;
/* 324:487 */     this.minZ = par1AxisAlignedBB.minZ;
/* 325:488 */     this.maxX = par1AxisAlignedBB.maxX;
/* 326:489 */     this.maxY = par1AxisAlignedBB.maxY;
/* 327:490 */     this.maxZ = par1AxisAlignedBB.maxZ;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public String toString()
/* 331:    */   {
/* 332:495 */     return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
/* 333:    */   }
/* 334:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.AxisAlignedBB
 * JD-Core Version:    0.7.0.1
 */