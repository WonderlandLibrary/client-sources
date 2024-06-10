/*   1:    */ package net.minecraft.potion;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public class PotionHelper
/*  10:    */ {
/*  11: 11 */   public static final String field_77924_a = null;
/*  12: 24 */   private static final HashMap potionRequirements = new HashMap();
/*  13: 27 */   private static final HashMap potionAmplifiers = new HashMap();
/*  14:    */   
/*  15:    */   public static boolean checkFlag(int par0, int par1)
/*  16:    */   {
/*  17: 39 */     return (par0 & 1 << par1) != 0;
/*  18:    */   }
/*  19:    */   
/*  20:    */   private static int isFlagSet(int par0, int par1)
/*  21:    */   {
/*  22: 47 */     return checkFlag(par0, par1) ? 1 : 0;
/*  23:    */   }
/*  24:    */   
/*  25:    */   private static int isFlagUnset(int par0, int par1)
/*  26:    */   {
/*  27: 55 */     return checkFlag(par0, par1) ? 0 : 1;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static int func_77909_a(int par0)
/*  31:    */   {
/*  32: 60 */     return func_77908_a(par0, 5, 4, 3, 2, 1);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static int calcPotionLiquidColor(Collection par0Collection)
/*  36:    */   {
/*  37: 68 */     int var1 = 3694022;
/*  38: 70 */     if ((par0Collection != null) && (!par0Collection.isEmpty()))
/*  39:    */     {
/*  40: 72 */       float var2 = 0.0F;
/*  41: 73 */       float var3 = 0.0F;
/*  42: 74 */       float var4 = 0.0F;
/*  43: 75 */       float var5 = 0.0F;
/*  44: 76 */       Iterator var6 = par0Collection.iterator();
/*  45:    */       PotionEffect var7;
/*  46:    */       int var9;
/*  47: 78 */       for (; var6.hasNext(); var9 <= var7.getAmplifier())
/*  48:    */       {
/*  49: 80 */         var7 = (PotionEffect)var6.next();
/*  50: 81 */         int var8 = Potion.potionTypes[var7.getPotionID()].getLiquidColor();
/*  51:    */         
/*  52: 83 */         var9 = 0; continue;
/*  53:    */         
/*  54: 85 */         var2 += (var8 >> 16 & 0xFF) / 255.0F;
/*  55: 86 */         var3 += (var8 >> 8 & 0xFF) / 255.0F;
/*  56: 87 */         var4 += (var8 >> 0 & 0xFF) / 255.0F;
/*  57: 88 */         var5 += 1.0F;var9++;
/*  58:    */       }
/*  59: 92 */       var2 = var2 / var5 * 255.0F;
/*  60: 93 */       var3 = var3 / var5 * 255.0F;
/*  61: 94 */       var4 = var4 / var5 * 255.0F;
/*  62: 95 */       return (int)var2 << 16 | (int)var3 << 8 | (int)var4;
/*  63:    */     }
/*  64: 99 */     return var1;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static boolean func_82817_b(Collection par0Collection)
/*  68:    */   {
/*  69:105 */     Iterator var1 = par0Collection.iterator();
/*  70:    */     PotionEffect var2;
/*  71:    */     do
/*  72:    */     {
/*  73:110 */       if (!var1.hasNext()) {
/*  74:112 */         return true;
/*  75:    */       }
/*  76:115 */       var2 = (PotionEffect)var1.next();
/*  77:117 */     } while (var2.getIsAmbient());
/*  78:119 */     return false;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static int func_77915_a(int par0, boolean par1)
/*  82:    */   {
/*  83:124 */     if (!par1)
/*  84:    */     {
/*  85:126 */       if (field_77925_n.containsKey(Integer.valueOf(par0))) {
/*  86:128 */         return ((Integer)field_77925_n.get(Integer.valueOf(par0))).intValue();
/*  87:    */       }
/*  88:132 */       int var2 = calcPotionLiquidColor(getPotionEffects(par0, false));
/*  89:133 */       field_77925_n.put(Integer.valueOf(par0), Integer.valueOf(var2));
/*  90:134 */       return var2;
/*  91:    */     }
/*  92:139 */     return calcPotionLiquidColor(getPotionEffects(par0, par1));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static String func_77905_c(int par0)
/*  96:    */   {
/*  97:145 */     int var1 = func_77909_a(par0);
/*  98:146 */     return potionPrefixes[var1];
/*  99:    */   }
/* 100:    */   
/* 101:    */   private static int func_77904_a(boolean par0, boolean par1, boolean par2, int par3, int par4, int par5, int par6)
/* 102:    */   {
/* 103:151 */     int var7 = 0;
/* 104:153 */     if (par0) {
/* 105:155 */       var7 = isFlagUnset(par6, par4);
/* 106:157 */     } else if (par3 != -1)
/* 107:    */     {
/* 108:159 */       if ((par3 == 0) && (countSetFlags(par6) == par4)) {
/* 109:161 */         var7 = 1;
/* 110:163 */       } else if ((par3 == 1) && (countSetFlags(par6) > par4)) {
/* 111:165 */         var7 = 1;
/* 112:167 */       } else if ((par3 == 2) && (countSetFlags(par6) < par4)) {
/* 113:169 */         var7 = 1;
/* 114:    */       }
/* 115:    */     }
/* 116:    */     else {
/* 117:174 */       var7 = isFlagSet(par6, par4);
/* 118:    */     }
/* 119:177 */     if (par1) {
/* 120:179 */       var7 *= par5;
/* 121:    */     }
/* 122:182 */     if (par2) {
/* 123:184 */       var7 *= -1;
/* 124:    */     }
/* 125:187 */     return var7;
/* 126:    */   }
/* 127:    */   
/* 128:    */   private static int countSetFlags(int par0)
/* 129:    */   {
/* 130:197 */     for (int var1 = 0; par0 > 0; var1++) {
/* 131:199 */       par0 &= par0 - 1;
/* 132:    */     }
/* 133:202 */     return var1;
/* 134:    */   }
/* 135:    */   
/* 136:    */   private static int parsePotionEffects(String par0Str, int par1, int par2, int par3)
/* 137:    */   {
/* 138:207 */     if ((par1 < par0Str.length()) && (par2 >= 0) && (par1 < par2))
/* 139:    */     {
/* 140:209 */       int var4 = par0Str.indexOf('|', par1);
/* 141:213 */       if ((var4 >= 0) && (var4 < par2))
/* 142:    */       {
/* 143:215 */         int var5 = parsePotionEffects(par0Str, par1, var4 - 1, par3);
/* 144:217 */         if (var5 > 0) {
/* 145:219 */           return var5;
/* 146:    */         }
/* 147:223 */         int var17 = parsePotionEffects(par0Str, var4 + 1, par2, par3);
/* 148:224 */         return var17 > 0 ? var17 : 0;
/* 149:    */       }
/* 150:229 */       int var5 = par0Str.indexOf('&', par1);
/* 151:231 */       if ((var5 >= 0) && (var5 < par2))
/* 152:    */       {
/* 153:233 */         int var17 = parsePotionEffects(par0Str, par1, var5 - 1, par3);
/* 154:235 */         if (var17 <= 0) {
/* 155:237 */           return 0;
/* 156:    */         }
/* 157:241 */         int var18 = parsePotionEffects(par0Str, var5 + 1, par2, par3);
/* 158:242 */         return var17 > var18 ? var17 : var18 <= 0 ? 0 : var18;
/* 159:    */       }
/* 160:247 */       boolean var6 = false;
/* 161:248 */       boolean var7 = false;
/* 162:249 */       boolean var8 = false;
/* 163:250 */       boolean var9 = false;
/* 164:251 */       boolean var10 = false;
/* 165:252 */       byte var11 = -1;
/* 166:253 */       int var12 = 0;
/* 167:254 */       int var13 = 0;
/* 168:255 */       int var14 = 0;
/* 169:257 */       for (int var15 = par1; var15 < par2; var15++)
/* 170:    */       {
/* 171:259 */         char var16 = par0Str.charAt(var15);
/* 172:261 */         if ((var16 >= '0') && (var16 <= '9'))
/* 173:    */         {
/* 174:263 */           if (var6)
/* 175:    */           {
/* 176:265 */             var13 = var16 - '0';
/* 177:266 */             var7 = true;
/* 178:    */           }
/* 179:    */           else
/* 180:    */           {
/* 181:270 */             var12 *= 10;
/* 182:271 */             var12 += var16 - '0';
/* 183:272 */             var8 = true;
/* 184:    */           }
/* 185:    */         }
/* 186:275 */         else if (var16 == '*')
/* 187:    */         {
/* 188:277 */           var6 = true;
/* 189:    */         }
/* 190:279 */         else if (var16 == '!')
/* 191:    */         {
/* 192:281 */           if (var8)
/* 193:    */           {
/* 194:283 */             var14 += func_77904_a(var9, var7, var10, var11, var12, var13, par3);
/* 195:284 */             var9 = false;
/* 196:285 */             var10 = false;
/* 197:286 */             var6 = false;
/* 198:287 */             var7 = false;
/* 199:288 */             var8 = false;
/* 200:289 */             var13 = 0;
/* 201:290 */             var12 = 0;
/* 202:291 */             var11 = -1;
/* 203:    */           }
/* 204:294 */           var9 = true;
/* 205:    */         }
/* 206:296 */         else if (var16 == '-')
/* 207:    */         {
/* 208:298 */           if (var8)
/* 209:    */           {
/* 210:300 */             var14 += func_77904_a(var9, var7, var10, var11, var12, var13, par3);
/* 211:301 */             var9 = false;
/* 212:302 */             var10 = false;
/* 213:303 */             var6 = false;
/* 214:304 */             var7 = false;
/* 215:305 */             var8 = false;
/* 216:306 */             var13 = 0;
/* 217:307 */             var12 = 0;
/* 218:308 */             var11 = -1;
/* 219:    */           }
/* 220:311 */           var10 = true;
/* 221:    */         }
/* 222:313 */         else if ((var16 != '=') && (var16 != '<') && (var16 != '>'))
/* 223:    */         {
/* 224:315 */           if ((var16 == '+') && (var8))
/* 225:    */           {
/* 226:317 */             var14 += func_77904_a(var9, var7, var10, var11, var12, var13, par3);
/* 227:318 */             var9 = false;
/* 228:319 */             var10 = false;
/* 229:320 */             var6 = false;
/* 230:321 */             var7 = false;
/* 231:322 */             var8 = false;
/* 232:323 */             var13 = 0;
/* 233:324 */             var12 = 0;
/* 234:325 */             var11 = -1;
/* 235:    */           }
/* 236:    */         }
/* 237:    */         else
/* 238:    */         {
/* 239:330 */           if (var8)
/* 240:    */           {
/* 241:332 */             var14 += func_77904_a(var9, var7, var10, var11, var12, var13, par3);
/* 242:333 */             var9 = false;
/* 243:334 */             var10 = false;
/* 244:335 */             var6 = false;
/* 245:336 */             var7 = false;
/* 246:337 */             var8 = false;
/* 247:338 */             var13 = 0;
/* 248:339 */             var12 = 0;
/* 249:340 */             var11 = -1;
/* 250:    */           }
/* 251:343 */           if (var16 == '=') {
/* 252:345 */             var11 = 0;
/* 253:347 */           } else if (var16 == '<') {
/* 254:349 */             var11 = 2;
/* 255:351 */           } else if (var16 == '>') {
/* 256:353 */             var11 = 1;
/* 257:    */           }
/* 258:    */         }
/* 259:    */       }
/* 260:358 */       if (var8) {
/* 261:360 */         var14 += func_77904_a(var9, var7, var10, var11, var12, var13, par3);
/* 262:    */       }
/* 263:363 */       return var14;
/* 264:    */     }
/* 265:369 */     return 0;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static List getPotionEffects(int par0, boolean par1)
/* 269:    */   {
/* 270:378 */     ArrayList var2 = null;
/* 271:379 */     Potion[] var3 = Potion.potionTypes;
/* 272:380 */     int var4 = var3.length;
/* 273:382 */     for (int var5 = 0; var5 < var4; var5++)
/* 274:    */     {
/* 275:384 */       Potion var6 = var3[var5];
/* 276:386 */       if ((var6 != null) && ((!var6.isUsable()) || (par1)))
/* 277:    */       {
/* 278:388 */         String var7 = (String)potionRequirements.get(Integer.valueOf(var6.getId()));
/* 279:390 */         if (var7 != null)
/* 280:    */         {
/* 281:392 */           int var8 = parsePotionEffects(var7, 0, var7.length(), par0);
/* 282:394 */           if (var8 > 0)
/* 283:    */           {
/* 284:396 */             int var9 = 0;
/* 285:397 */             String var10 = (String)potionAmplifiers.get(Integer.valueOf(var6.getId()));
/* 286:399 */             if (var10 != null)
/* 287:    */             {
/* 288:401 */               var9 = parsePotionEffects(var10, 0, var10.length(), par0);
/* 289:403 */               if (var9 < 0) {
/* 290:405 */                 var9 = 0;
/* 291:    */               }
/* 292:    */             }
/* 293:409 */             if (var6.isInstant())
/* 294:    */             {
/* 295:411 */               var8 = 1;
/* 296:    */             }
/* 297:    */             else
/* 298:    */             {
/* 299:415 */               var8 = 1200 * (var8 * 3 + (var8 - 1) * 2);
/* 300:416 */               var8 >>= var9;
/* 301:417 */               var8 = (int)Math.round(var8 * var6.getEffectiveness());
/* 302:419 */               if ((par0 & 0x4000) != 0) {
/* 303:421 */                 var8 = (int)Math.round(var8 * 0.75D + 0.5D);
/* 304:    */               }
/* 305:    */             }
/* 306:425 */             if (var2 == null) {
/* 307:427 */               var2 = new ArrayList();
/* 308:    */             }
/* 309:430 */             PotionEffect var11 = new PotionEffect(var6.getId(), var8, var9);
/* 310:432 */             if ((par0 & 0x4000) != 0) {
/* 311:434 */               var11.setSplashPotion(true);
/* 312:    */             }
/* 313:437 */             var2.add(var11);
/* 314:    */           }
/* 315:    */         }
/* 316:    */       }
/* 317:    */     }
/* 318:443 */     return var2;
/* 319:    */   }
/* 320:    */   
/* 321:    */   private static int brewBitOperations(int par0, int par1, boolean par2, boolean par3, boolean par4)
/* 322:    */   {
/* 323:453 */     if (par4)
/* 324:    */     {
/* 325:455 */       if (!checkFlag(par0, par1)) {
/* 326:457 */         return 0;
/* 327:    */       }
/* 328:    */     }
/* 329:460 */     else if (par2) {
/* 330:462 */       par0 &= (1 << par1 ^ 0xFFFFFFFF);
/* 331:464 */     } else if (par3)
/* 332:    */     {
/* 333:466 */       if ((par0 & 1 << par1) == 0) {
/* 334:468 */         par0 |= 1 << par1;
/* 335:    */       } else {
/* 336:472 */         par0 &= (1 << par1 ^ 0xFFFFFFFF);
/* 337:    */       }
/* 338:    */     }
/* 339:    */     else {
/* 340:477 */       par0 |= 1 << par1;
/* 341:    */     }
/* 342:480 */     return par0;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public static int applyIngredient(int par0, String par1Str)
/* 346:    */   {
/* 347:489 */     byte var2 = 0;
/* 348:490 */     int var3 = par1Str.length();
/* 349:491 */     boolean var4 = false;
/* 350:492 */     boolean var5 = false;
/* 351:493 */     boolean var6 = false;
/* 352:494 */     boolean var7 = false;
/* 353:495 */     int var8 = 0;
/* 354:497 */     for (int var9 = var2; var9 < var3; var9++)
/* 355:    */     {
/* 356:499 */       char var10 = par1Str.charAt(var9);
/* 357:501 */       if ((var10 >= '0') && (var10 <= '9'))
/* 358:    */       {
/* 359:503 */         var8 *= 10;
/* 360:504 */         var8 += var10 - '0';
/* 361:505 */         var4 = true;
/* 362:    */       }
/* 363:507 */       else if (var10 == '!')
/* 364:    */       {
/* 365:509 */         if (var4)
/* 366:    */         {
/* 367:511 */           par0 = brewBitOperations(par0, var8, var6, var5, var7);
/* 368:512 */           var7 = false;
/* 369:513 */           var5 = false;
/* 370:514 */           var6 = false;
/* 371:515 */           var4 = false;
/* 372:516 */           var8 = 0;
/* 373:    */         }
/* 374:519 */         var5 = true;
/* 375:    */       }
/* 376:521 */       else if (var10 == '-')
/* 377:    */       {
/* 378:523 */         if (var4)
/* 379:    */         {
/* 380:525 */           par0 = brewBitOperations(par0, var8, var6, var5, var7);
/* 381:526 */           var7 = false;
/* 382:527 */           var5 = false;
/* 383:528 */           var6 = false;
/* 384:529 */           var4 = false;
/* 385:530 */           var8 = 0;
/* 386:    */         }
/* 387:533 */         var6 = true;
/* 388:    */       }
/* 389:535 */       else if (var10 == '+')
/* 390:    */       {
/* 391:537 */         if (var4)
/* 392:    */         {
/* 393:539 */           par0 = brewBitOperations(par0, var8, var6, var5, var7);
/* 394:540 */           var7 = false;
/* 395:541 */           var5 = false;
/* 396:542 */           var6 = false;
/* 397:543 */           var4 = false;
/* 398:544 */           var8 = 0;
/* 399:    */         }
/* 400:    */       }
/* 401:547 */       else if (var10 == '&')
/* 402:    */       {
/* 403:549 */         if (var4)
/* 404:    */         {
/* 405:551 */           par0 = brewBitOperations(par0, var8, var6, var5, var7);
/* 406:552 */           var7 = false;
/* 407:553 */           var5 = false;
/* 408:554 */           var6 = false;
/* 409:555 */           var4 = false;
/* 410:556 */           var8 = 0;
/* 411:    */         }
/* 412:559 */         var7 = true;
/* 413:    */       }
/* 414:    */     }
/* 415:563 */     if (var4) {
/* 416:565 */       par0 = brewBitOperations(par0, var8, var6, var5, var7);
/* 417:    */     }
/* 418:568 */     return par0 & 0x7FFF;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public static int func_77908_a(int par0, int par1, int par2, int par3, int par4, int par5)
/* 422:    */   {
/* 423:573 */     return (checkFlag(par0, par1) ? 16 : 0) | (checkFlag(par0, par2) ? 8 : 0) | (checkFlag(par0, par3) ? 4 : 0) | (checkFlag(par0, par4) ? 2 : 0) | (checkFlag(par0, par5) ? 1 : 0);
/* 424:    */   }
/* 425:    */   
/* 426:    */   static
/* 427:    */   {
/* 428:578 */     potionRequirements.put(Integer.valueOf(Potion.regeneration.getId()), "0 & !1 & !2 & !3 & 0+6");
/* 429:579 */     sugarEffect = "-0+1-2-3&4-4+13";
/* 430:580 */     potionRequirements.put(Integer.valueOf(Potion.moveSpeed.getId()), "!0 & 1 & !2 & !3 & 1+6");
/* 431:581 */     magmaCreamEffect = "+0+1-2-3&4-4+13";
/* 432:582 */     potionRequirements.put(Integer.valueOf(Potion.fireResistance.getId()), "0 & 1 & !2 & !3 & 0+6");
/* 433:583 */     speckledMelonEffect = "+0-1+2-3&4-4+13";
/* 434:584 */     potionRequirements.put(Integer.valueOf(Potion.heal.getId()), "0 & !1 & 2 & !3");
/* 435:585 */     spiderEyeEffect = "-0-1+2-3&4-4+13";
/* 436:586 */     potionRequirements.put(Integer.valueOf(Potion.poison.getId()), "!0 & !1 & 2 & !3 & 2+6");
/* 437:587 */     fermentedSpiderEyeEffect = "-0+3-4+13";
/* 438:588 */     potionRequirements.put(Integer.valueOf(Potion.weakness.getId()), "!0 & !1 & !2 & 3 & 3+6");
/* 439:589 */     potionRequirements.put(Integer.valueOf(Potion.harm.getId()), "!0 & !1 & 2 & 3");
/* 440:590 */     potionRequirements.put(Integer.valueOf(Potion.moveSlowdown.getId()), "!0 & 1 & !2 & 3 & 3+6");
/* 441:591 */     blazePowderEffect = "+0-1-2+3&4-4+13";
/* 442:592 */     potionRequirements.put(Integer.valueOf(Potion.damageBoost.getId()), "0 & !1 & !2 & 3 & 3+6");
/* 443:593 */     goldenCarrotEffect = "-0+1+2-3+13&4-4";
/* 444:594 */     potionRequirements.put(Integer.valueOf(Potion.nightVision.getId()), "!0 & 1 & 2 & !3 & 2+6");
/* 445:595 */     potionRequirements.put(Integer.valueOf(Potion.invisibility.getId()), "!0 & 1 & 2 & 3 & 2+6");
/* 446:596 */     field_151423_m = "+0-1+2+3+13&4-4";
/* 447:597 */     potionRequirements.put(Integer.valueOf(Potion.waterBreathing.getId()), "0 & !1 & 2 & 3 & 2+6");
/* 448:598 */     glowstoneEffect = "+5-6-7";
/* 449:599 */     potionAmplifiers.put(Integer.valueOf(Potion.moveSpeed.getId()), "5");
/* 450:600 */     potionAmplifiers.put(Integer.valueOf(Potion.digSpeed.getId()), "5");
/* 451:601 */     potionAmplifiers.put(Integer.valueOf(Potion.damageBoost.getId()), "5");
/* 452:602 */     potionAmplifiers.put(Integer.valueOf(Potion.regeneration.getId()), "5");
/* 453:603 */     potionAmplifiers.put(Integer.valueOf(Potion.harm.getId()), "5");
/* 454:604 */     potionAmplifiers.put(Integer.valueOf(Potion.heal.getId()), "5");
/* 455:605 */     potionAmplifiers.put(Integer.valueOf(Potion.resistance.getId()), "5");
/* 456:606 */     potionAmplifiers.put(Integer.valueOf(Potion.poison.getId()), "5");
/* 457:    */   }
/* 458:    */   
/* 459:607 */   public static final String redstoneEffect = "-5+6-7";
/* 460:608 */   public static final String gunpowderEffect = "+14&13-13";
/* 461:609 */   private static final HashMap field_77925_n = new HashMap();
/* 462:610 */   private static final String[] potionPrefixes = { "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky" };
/* 463:    */   public static final String sugarEffect;
/* 464:    */   public static final String ghastTearEffect = "+0-1-2-3&4-4+13";
/* 465:    */   public static final String spiderEyeEffect;
/* 466:    */   public static final String fermentedSpiderEyeEffect;
/* 467:    */   public static final String speckledMelonEffect;
/* 468:    */   public static final String blazePowderEffect;
/* 469:    */   public static final String magmaCreamEffect;
/* 470:    */   public static final String glowstoneEffect;
/* 471:    */   public static final String goldenCarrotEffect;
/* 472:    */   public static final String field_151423_m;
/* 473:    */   private static final String __OBFID = "CL_00000078";
/* 474:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.potion.PotionHelper
 * JD-Core Version:    0.7.0.1
 */