/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Random;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.material.Material;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.util.ChunkCoordinates;
/*  12:    */ import net.minecraft.util.LongHashMap;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ 
/*  15:    */ public class Teleporter
/*  16:    */ {
/*  17:    */   private final WorldServer worldServerInstance;
/*  18:    */   private final Random random;
/*  19: 23 */   private final LongHashMap destinationCoordinateCache = new LongHashMap();
/*  20: 29 */   private final List destinationCoordinateKeys = new ArrayList();
/*  21:    */   private static final String __OBFID = "CL_00000153";
/*  22:    */   
/*  23:    */   public Teleporter(WorldServer par1WorldServer)
/*  24:    */   {
/*  25: 34 */     this.worldServerInstance = par1WorldServer;
/*  26: 35 */     this.random = new Random(par1WorldServer.getSeed());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
/*  30:    */   {
/*  31: 43 */     if (this.worldServerInstance.provider.dimensionId != 1)
/*  32:    */     {
/*  33: 45 */       if (!placeInExistingPortal(par1Entity, par2, par4, par6, par8))
/*  34:    */       {
/*  35: 47 */         makePortal(par1Entity);
/*  36: 48 */         placeInExistingPortal(par1Entity, par2, par4, par6, par8);
/*  37:    */       }
/*  38:    */     }
/*  39:    */     else
/*  40:    */     {
/*  41: 53 */       int var9 = MathHelper.floor_double(par1Entity.posX);
/*  42: 54 */       int var10 = MathHelper.floor_double(par1Entity.posY) - 1;
/*  43: 55 */       int var11 = MathHelper.floor_double(par1Entity.posZ);
/*  44: 56 */       byte var12 = 1;
/*  45: 57 */       byte var13 = 0;
/*  46: 59 */       for (int var14 = -2; var14 <= 2; var14++) {
/*  47: 61 */         for (int var15 = -2; var15 <= 2; var15++) {
/*  48: 63 */           for (int var16 = -1; var16 < 3; var16++)
/*  49:    */           {
/*  50: 65 */             int var17 = var9 + var15 * var12 + var14 * var13;
/*  51: 66 */             int var18 = var10 + var16;
/*  52: 67 */             int var19 = var11 + var15 * var13 - var14 * var12;
/*  53: 68 */             boolean var20 = var16 < 0;
/*  54: 69 */             this.worldServerInstance.setBlock(var17, var18, var19, var20 ? Blocks.obsidian : Blocks.air);
/*  55:    */           }
/*  56:    */         }
/*  57:    */       }
/*  58: 74 */       par1Entity.setLocationAndAngles(var9, var10, var11, par1Entity.rotationYaw, 0.0F);
/*  59: 75 */       par1Entity.motionX = (par1Entity.motionY = par1Entity.motionZ = 0.0D);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
/*  64:    */   {
/*  65: 84 */     short var9 = 128;
/*  66: 85 */     double var10 = -1.0D;
/*  67: 86 */     int var12 = 0;
/*  68: 87 */     int var13 = 0;
/*  69: 88 */     int var14 = 0;
/*  70: 89 */     int var15 = MathHelper.floor_double(par1Entity.posX);
/*  71: 90 */     int var16 = MathHelper.floor_double(par1Entity.posZ);
/*  72: 91 */     long var17 = ChunkCoordIntPair.chunkXZ2Int(var15, var16);
/*  73: 92 */     boolean var19 = true;
/*  74: 96 */     if (this.destinationCoordinateCache.containsItem(var17))
/*  75:    */     {
/*  76: 98 */       PortalPosition var20 = (PortalPosition)this.destinationCoordinateCache.getValueByKey(var17);
/*  77: 99 */       var10 = 0.0D;
/*  78:100 */       var12 = var20.posX;
/*  79:101 */       var13 = var20.posY;
/*  80:102 */       var14 = var20.posZ;
/*  81:103 */       var20.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
/*  82:104 */       var19 = false;
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:108 */       for (int var48 = var15 - var9; var48 <= var15 + var9; var48++)
/*  87:    */       {
/*  88:110 */         double var21 = var48 + 0.5D - par1Entity.posX;
/*  89:112 */         for (int var23 = var16 - var9; var23 <= var16 + var9; var23++)
/*  90:    */         {
/*  91:114 */           double var24 = var23 + 0.5D - par1Entity.posZ;
/*  92:116 */           for (int var26 = this.worldServerInstance.getActualHeight() - 1; var26 >= 0; var26--) {
/*  93:118 */             if (this.worldServerInstance.getBlock(var48, var26, var23) == Blocks.portal)
/*  94:    */             {
/*  95:120 */               while (this.worldServerInstance.getBlock(var48, var26 - 1, var23) == Blocks.portal) {
/*  96:122 */                 var26--;
/*  97:    */               }
/*  98:125 */               double var27 = var26 + 0.5D - par1Entity.posY;
/*  99:126 */               double var29 = var21 * var21 + var27 * var27 + var24 * var24;
/* 100:128 */               if ((var10 < 0.0D) || (var29 < var10))
/* 101:    */               {
/* 102:130 */                 var10 = var29;
/* 103:131 */                 var12 = var48;
/* 104:132 */                 var13 = var26;
/* 105:133 */                 var14 = var23;
/* 106:    */               }
/* 107:    */             }
/* 108:    */           }
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:141 */     if (var10 >= 0.0D)
/* 113:    */     {
/* 114:143 */       if (var19)
/* 115:    */       {
/* 116:145 */         this.destinationCoordinateCache.add(var17, new PortalPosition(var12, var13, var14, this.worldServerInstance.getTotalWorldTime()));
/* 117:146 */         this.destinationCoordinateKeys.add(Long.valueOf(var17));
/* 118:    */       }
/* 119:149 */       double var49 = var12 + 0.5D;
/* 120:150 */       double var25 = var13 + 0.5D;
/* 121:151 */       double var27 = var14 + 0.5D;
/* 122:152 */       int var50 = -1;
/* 123:154 */       if (this.worldServerInstance.getBlock(var12 - 1, var13, var14) == Blocks.portal) {
/* 124:156 */         var50 = 2;
/* 125:    */       }
/* 126:159 */       if (this.worldServerInstance.getBlock(var12 + 1, var13, var14) == Blocks.portal) {
/* 127:161 */         var50 = 0;
/* 128:    */       }
/* 129:164 */       if (this.worldServerInstance.getBlock(var12, var13, var14 - 1) == Blocks.portal) {
/* 130:166 */         var50 = 3;
/* 131:    */       }
/* 132:169 */       if (this.worldServerInstance.getBlock(var12, var13, var14 + 1) == Blocks.portal) {
/* 133:171 */         var50 = 1;
/* 134:    */       }
/* 135:174 */       int var30 = par1Entity.getTeleportDirection();
/* 136:176 */       if (var50 > -1)
/* 137:    */       {
/* 138:178 */         int var31 = net.minecraft.util.Direction.rotateLeft[var50];
/* 139:179 */         int var32 = net.minecraft.util.Direction.offsetX[var50];
/* 140:180 */         int var33 = net.minecraft.util.Direction.offsetZ[var50];
/* 141:181 */         int var34 = net.minecraft.util.Direction.offsetX[var31];
/* 142:182 */         int var35 = net.minecraft.util.Direction.offsetZ[var31];
/* 143:183 */         boolean var36 = (!this.worldServerInstance.isAirBlock(var12 + var32 + var34, var13, var14 + var33 + var35)) || (!this.worldServerInstance.isAirBlock(var12 + var32 + var34, var13 + 1, var14 + var33 + var35));
/* 144:184 */         boolean var37 = (!this.worldServerInstance.isAirBlock(var12 + var32, var13, var14 + var33)) || (!this.worldServerInstance.isAirBlock(var12 + var32, var13 + 1, var14 + var33));
/* 145:186 */         if ((var36) && (var37))
/* 146:    */         {
/* 147:188 */           var50 = net.minecraft.util.Direction.rotateOpposite[var50];
/* 148:189 */           var31 = net.minecraft.util.Direction.rotateOpposite[var31];
/* 149:190 */           var32 = net.minecraft.util.Direction.offsetX[var50];
/* 150:191 */           var33 = net.minecraft.util.Direction.offsetZ[var50];
/* 151:192 */           var34 = net.minecraft.util.Direction.offsetX[var31];
/* 152:193 */           var35 = net.minecraft.util.Direction.offsetZ[var31];
/* 153:194 */           int var48 = var12 - var34;
/* 154:195 */           var49 -= var34;
/* 155:196 */           int var22 = var14 - var35;
/* 156:197 */           var27 -= var35;
/* 157:198 */           var36 = (!this.worldServerInstance.isAirBlock(var48 + var32 + var34, var13, var22 + var33 + var35)) || (!this.worldServerInstance.isAirBlock(var48 + var32 + var34, var13 + 1, var22 + var33 + var35));
/* 158:199 */           var37 = (!this.worldServerInstance.isAirBlock(var48 + var32, var13, var22 + var33)) || (!this.worldServerInstance.isAirBlock(var48 + var32, var13 + 1, var22 + var33));
/* 159:    */         }
/* 160:202 */         float var38 = 0.5F;
/* 161:203 */         float var39 = 0.5F;
/* 162:205 */         if ((!var36) && (var37)) {
/* 163:207 */           var38 = 1.0F;
/* 164:209 */         } else if ((var36) && (!var37)) {
/* 165:211 */           var38 = 0.0F;
/* 166:213 */         } else if ((var36) && (var37)) {
/* 167:215 */           var39 = 0.0F;
/* 168:    */         }
/* 169:218 */         var49 += var34 * var38 + var39 * var32;
/* 170:219 */         var27 += var35 * var38 + var39 * var33;
/* 171:220 */         float var40 = 0.0F;
/* 172:221 */         float var41 = 0.0F;
/* 173:222 */         float var42 = 0.0F;
/* 174:223 */         float var43 = 0.0F;
/* 175:225 */         if (var50 == var30)
/* 176:    */         {
/* 177:227 */           var40 = 1.0F;
/* 178:228 */           var41 = 1.0F;
/* 179:    */         }
/* 180:230 */         else if (var50 == net.minecraft.util.Direction.rotateOpposite[var30])
/* 181:    */         {
/* 182:232 */           var40 = -1.0F;
/* 183:233 */           var41 = -1.0F;
/* 184:    */         }
/* 185:235 */         else if (var50 == net.minecraft.util.Direction.rotateRight[var30])
/* 186:    */         {
/* 187:237 */           var42 = 1.0F;
/* 188:238 */           var43 = -1.0F;
/* 189:    */         }
/* 190:    */         else
/* 191:    */         {
/* 192:242 */           var42 = -1.0F;
/* 193:243 */           var43 = 1.0F;
/* 194:    */         }
/* 195:246 */         double var44 = par1Entity.motionX;
/* 196:247 */         double var46 = par1Entity.motionZ;
/* 197:248 */         par1Entity.motionX = (var44 * var40 + var46 * var43);
/* 198:249 */         par1Entity.motionZ = (var44 * var42 + var46 * var41);
/* 199:250 */         par1Entity.rotationYaw = (par8 - var30 * 90 + var50 * 90);
/* 200:    */       }
/* 201:    */       else
/* 202:    */       {
/* 203:254 */         par1Entity.motionX = (par1Entity.motionY = par1Entity.motionZ = 0.0D);
/* 204:    */       }
/* 205:257 */       par1Entity.setLocationAndAngles(var49, var25, var27, par1Entity.rotationYaw, par1Entity.rotationPitch);
/* 206:258 */       return true;
/* 207:    */     }
/* 208:262 */     return false;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean makePortal(Entity par1Entity)
/* 212:    */   {
/* 213:268 */     byte var2 = 16;
/* 214:269 */     double var3 = -1.0D;
/* 215:270 */     int var5 = MathHelper.floor_double(par1Entity.posX);
/* 216:271 */     int var6 = MathHelper.floor_double(par1Entity.posY);
/* 217:272 */     int var7 = MathHelper.floor_double(par1Entity.posZ);
/* 218:273 */     int var8 = var5;
/* 219:274 */     int var9 = var6;
/* 220:275 */     int var10 = var7;
/* 221:276 */     int var11 = 0;
/* 222:277 */     int var12 = this.random.nextInt(4);
/* 223:294 */     for (int var13 = var5 - var2; var13 <= var5 + var2; var13++)
/* 224:    */     {
/* 225:296 */       double var14 = var13 + 0.5D - par1Entity.posX;
/* 226:298 */       for (int var16 = var7 - var2; var16 <= var7 + var2; var16++)
/* 227:    */       {
/* 228:300 */         double var17 = var16 + 0.5D - par1Entity.posZ;
/* 229:303 */         for (int var19 = this.worldServerInstance.getActualHeight() - 1; var19 >= 0; var19--) {
/* 230:305 */           if (this.worldServerInstance.isAirBlock(var13, var19, var16))
/* 231:    */           {
/* 232:307 */             while ((var19 > 0) && (this.worldServerInstance.isAirBlock(var13, var19 - 1, var16))) {
/* 233:309 */               var19--;
/* 234:    */             }
/* 235:312 */             for (int var20 = var12; var20 < var12 + 4; var20++)
/* 236:    */             {
/* 237:314 */               int var21 = var20 % 2;
/* 238:315 */               int var22 = 1 - var21;
/* 239:317 */               if (var20 % 4 >= 2)
/* 240:    */               {
/* 241:319 */                 var21 = -var21;
/* 242:320 */                 var22 = -var22;
/* 243:    */               }
/* 244:323 */               for (int var23 = 0; var23 < 3; var23++) {
/* 245:325 */                 for (int var24 = 0; var24 < 4; var24++) {
/* 246:327 */                   for (int var25 = -1; var25 < 4; var25++)
/* 247:    */                   {
/* 248:329 */                     int var26 = var13 + (var24 - 1) * var21 + var23 * var22;
/* 249:330 */                     int var27 = var19 + var25;
/* 250:331 */                     int var28 = var16 + (var24 - 1) * var22 - var23 * var21;
/* 251:333 */                     if (((var25 < 0) && (!this.worldServerInstance.getBlock(var26, var27, var28).getMaterial().isSolid())) || ((var25 >= 0) && (!this.worldServerInstance.isAirBlock(var26, var27, var28)))) {
/* 252:    */                       break;
/* 253:    */                     }
/* 254:    */                   }
/* 255:    */                 }
/* 256:    */               }
/* 257:341 */               double var32 = var19 + 0.5D - par1Entity.posY;
/* 258:342 */               double var31 = var14 * var14 + var32 * var32 + var17 * var17;
/* 259:344 */               if ((var3 < 0.0D) || (var31 < var3))
/* 260:    */               {
/* 261:346 */                 var3 = var31;
/* 262:347 */                 var8 = var13;
/* 263:348 */                 var9 = var19;
/* 264:349 */                 var10 = var16;
/* 265:350 */                 var11 = var20 % 4;
/* 266:    */               }
/* 267:    */             }
/* 268:    */           }
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:358 */     if (var3 < 0.0D) {
/* 273:360 */       for (var13 = var5 - var2; var13 <= var5 + var2; var13++)
/* 274:    */       {
/* 275:362 */         double var14 = var13 + 0.5D - par1Entity.posX;
/* 276:364 */         for (int var16 = var7 - var2; var16 <= var7 + var2; var16++)
/* 277:    */         {
/* 278:366 */           double var17 = var16 + 0.5D - par1Entity.posZ;
/* 279:369 */           for (int var19 = this.worldServerInstance.getActualHeight() - 1; var19 >= 0; var19--) {
/* 280:371 */             if (this.worldServerInstance.isAirBlock(var13, var19, var16))
/* 281:    */             {
/* 282:373 */               while ((var19 > 0) && (this.worldServerInstance.isAirBlock(var13, var19 - 1, var16))) {
/* 283:375 */                 var19--;
/* 284:    */               }
/* 285:378 */               for (int var20 = var12; var20 < var12 + 2; var20++)
/* 286:    */               {
/* 287:380 */                 int var21 = var20 % 2;
/* 288:381 */                 int var22 = 1 - var21;
/* 289:383 */                 for (int var23 = 0; var23 < 4; var23++) {
/* 290:385 */                   for (int var24 = -1; var24 < 4; var24++)
/* 291:    */                   {
/* 292:387 */                     int var25 = var13 + (var23 - 1) * var21;
/* 293:388 */                     int var26 = var19 + var24;
/* 294:389 */                     int var27 = var16 + (var23 - 1) * var22;
/* 295:391 */                     if (((var24 < 0) && (!this.worldServerInstance.getBlock(var25, var26, var27).getMaterial().isSolid())) || ((var24 >= 0) && (!this.worldServerInstance.isAirBlock(var25, var26, var27)))) {
/* 296:    */                       break;
/* 297:    */                     }
/* 298:    */                   }
/* 299:    */                 }
/* 300:398 */                 double var32 = var19 + 0.5D - par1Entity.posY;
/* 301:399 */                 double var31 = var14 * var14 + var32 * var32 + var17 * var17;
/* 302:401 */                 if ((var3 < 0.0D) || (var31 < var3))
/* 303:    */                 {
/* 304:403 */                   var3 = var31;
/* 305:404 */                   var8 = var13;
/* 306:405 */                   var9 = var19;
/* 307:406 */                   var10 = var16;
/* 308:407 */                   var11 = var20 % 2;
/* 309:    */                 }
/* 310:    */               }
/* 311:    */             }
/* 312:    */           }
/* 313:    */         }
/* 314:    */       }
/* 315:    */     }
/* 316:416 */     int var29 = var8;
/* 317:417 */     int var15 = var9;
/* 318:418 */     int var16 = var10;
/* 319:419 */     int var30 = var11 % 2;
/* 320:420 */     int var18 = 1 - var30;
/* 321:422 */     if (var11 % 4 >= 2)
/* 322:    */     {
/* 323:424 */       var30 = -var30;
/* 324:425 */       var18 = -var18;
/* 325:    */     }
/* 326:430 */     if (var3 < 0.0D)
/* 327:    */     {
/* 328:432 */       if (var9 < 70) {
/* 329:434 */         var9 = 70;
/* 330:    */       }
/* 331:437 */       if (var9 > this.worldServerInstance.getActualHeight() - 10) {
/* 332:439 */         var9 = this.worldServerInstance.getActualHeight() - 10;
/* 333:    */       }
/* 334:442 */       var15 = var9;
/* 335:444 */       for (int var19 = -1; var19 <= 1; var19++) {
/* 336:446 */         for (int var20 = 1; var20 < 3; var20++) {
/* 337:448 */           for (int var21 = -1; var21 < 3; var21++)
/* 338:    */           {
/* 339:450 */             int var22 = var29 + (var20 - 1) * var30 + var19 * var18;
/* 340:451 */             int var23 = var15 + var21;
/* 341:452 */             int var24 = var16 + (var20 - 1) * var18 - var19 * var30;
/* 342:453 */             boolean var33 = var21 < 0;
/* 343:454 */             this.worldServerInstance.setBlock(var22, var23, var24, var33 ? Blocks.obsidian : Blocks.air);
/* 344:    */           }
/* 345:    */         }
/* 346:    */       }
/* 347:    */     }
/* 348:460 */     for (int var19 = 0; var19 < 4; var19++)
/* 349:    */     {
/* 350:462 */       for (int var20 = 0; var20 < 4; var20++) {
/* 351:464 */         for (int var21 = -1; var21 < 4; var21++)
/* 352:    */         {
/* 353:466 */           int var22 = var29 + (var20 - 1) * var30;
/* 354:467 */           int var23 = var15 + var21;
/* 355:468 */           int var24 = var16 + (var20 - 1) * var18;
/* 356:469 */           boolean var33 = (var20 == 0) || (var20 == 3) || (var21 == -1) || (var21 == 3);
/* 357:470 */           this.worldServerInstance.setBlock(var22, var23, var24, var33 ? Blocks.obsidian : Blocks.portal, 0, 2);
/* 358:    */         }
/* 359:    */       }
/* 360:474 */       for (var20 = 0; var20 < 4; var20++) {
/* 361:476 */         for (int var21 = -1; var21 < 4; var21++)
/* 362:    */         {
/* 363:478 */           int var22 = var29 + (var20 - 1) * var30;
/* 364:479 */           int var23 = var15 + var21;
/* 365:480 */           int var24 = var16 + (var20 - 1) * var18;
/* 366:481 */           this.worldServerInstance.notifyBlocksOfNeighborChange(var22, var23, var24, this.worldServerInstance.getBlock(var22, var23, var24));
/* 367:    */         }
/* 368:    */       }
/* 369:    */     }
/* 370:486 */     return true;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void removeStalePortalLocations(long par1)
/* 374:    */   {
/* 375:495 */     if (par1 % 100L == 0L)
/* 376:    */     {
/* 377:497 */       Iterator var3 = this.destinationCoordinateKeys.iterator();
/* 378:498 */       long var4 = par1 - 600L;
/* 379:500 */       while (var3.hasNext())
/* 380:    */       {
/* 381:502 */         Long var6 = (Long)var3.next();
/* 382:503 */         PortalPosition var7 = (PortalPosition)this.destinationCoordinateCache.getValueByKey(var6.longValue());
/* 383:505 */         if ((var7 == null) || (var7.lastUpdateTime < var4))
/* 384:    */         {
/* 385:507 */           var3.remove();
/* 386:508 */           this.destinationCoordinateCache.remove(var6.longValue());
/* 387:    */         }
/* 388:    */       }
/* 389:    */     }
/* 390:    */   }
/* 391:    */   
/* 392:    */   public class PortalPosition
/* 393:    */     extends ChunkCoordinates
/* 394:    */   {
/* 395:    */     public long lastUpdateTime;
/* 396:    */     private static final String __OBFID = "CL_00000154";
/* 397:    */     
/* 398:    */     public PortalPosition(int par2, int par3, int par4, long par5)
/* 399:    */     {
/* 400:521 */       super(par3, par4);
/* 401:522 */       this.lastUpdateTime = par5;
/* 402:    */     }
/* 403:    */   }
/* 404:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.Teleporter
 * JD-Core Version:    0.7.0.1
 */