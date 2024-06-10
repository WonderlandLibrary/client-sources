/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import java.io.EOFException;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.nio.ByteBuffer;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.zip.CRC32;
/*   9:    */ import java.util.zip.DataFormatException;
/*  10:    */ import java.util.zip.Inflater;
/*  11:    */ 
/*  12:    */ public class PNGDecoder
/*  13:    */ {
/*  14: 47 */   public static Format ALPHA = new Format(1, true, null);
/*  15: 48 */   public static Format LUMINANCE = new Format(1, false, null);
/*  16: 49 */   public static Format LUMINANCE_ALPHA = new Format(2, true, null);
/*  17: 50 */   public static Format RGB = new Format(3, false, null);
/*  18: 51 */   public static Format RGBA = new Format(4, true, null);
/*  19: 52 */   public static Format BGRA = new Format(4, true, null);
/*  20: 53 */   public static Format ABGR = new Format(4, true, null);
/*  21:    */   
/*  22:    */   public static class Format
/*  23:    */   {
/*  24:    */     final int numComponents;
/*  25:    */     final boolean hasAlpha;
/*  26:    */     
/*  27:    */     private Format(int numComponents, boolean hasAlpha)
/*  28:    */     {
/*  29: 61 */       this.numComponents = numComponents;
/*  30: 62 */       this.hasAlpha = hasAlpha;
/*  31:    */     }
/*  32:    */     
/*  33:    */     public int getNumComponents()
/*  34:    */     {
/*  35: 66 */       return this.numComponents;
/*  36:    */     }
/*  37:    */     
/*  38:    */     public boolean isHasAlpha()
/*  39:    */     {
/*  40: 70 */       return this.hasAlpha;
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44: 74 */   private static final byte[] SIGNATURE = { -119, 80, 78, 71, 13, 10, 26, 10 };
/*  45:    */   private static final int IHDR = 1229472850;
/*  46:    */   private static final int PLTE = 1347179589;
/*  47:    */   private static final int tRNS = 1951551059;
/*  48:    */   private static final int IDAT = 1229209940;
/*  49:    */   private static final int IEND = 1229278788;
/*  50:    */   private static final byte COLOR_GREYSCALE = 0;
/*  51:    */   private static final byte COLOR_TRUECOLOR = 2;
/*  52:    */   private static final byte COLOR_INDEXED = 3;
/*  53:    */   private static final byte COLOR_GREYALPHA = 4;
/*  54:    */   private static final byte COLOR_TRUEALPHA = 6;
/*  55:    */   private final InputStream input;
/*  56:    */   private final CRC32 crc;
/*  57:    */   private final byte[] buffer;
/*  58:    */   private int chunkLength;
/*  59:    */   private int chunkType;
/*  60:    */   private int chunkRemaining;
/*  61:    */   private int width;
/*  62:    */   private int height;
/*  63:    */   private int bitdepth;
/*  64:    */   private int colorType;
/*  65:    */   private int bytesPerPixel;
/*  66:    */   private byte[] palette;
/*  67:    */   private byte[] paletteA;
/*  68:    */   private byte[] transPixel;
/*  69:    */   
/*  70:    */   public PNGDecoder(InputStream input)
/*  71:    */     throws IOException
/*  72:    */   {
/*  73:106 */     this.input = input;
/*  74:107 */     this.crc = new CRC32();
/*  75:108 */     this.buffer = new byte[4096];
/*  76:    */     
/*  77:110 */     readFully(this.buffer, 0, SIGNATURE.length);
/*  78:111 */     if (!checkSignature(this.buffer)) {
/*  79:112 */       throw new IOException("Not a valid PNG file");
/*  80:    */     }
/*  81:115 */     openChunk(1229472850);
/*  82:116 */     readIHDR();
/*  83:117 */     closeChunk();
/*  84:    */     for (;;)
/*  85:    */     {
/*  86:120 */       openChunk();
/*  87:121 */       switch (this.chunkType)
/*  88:    */       {
/*  89:    */       case 1229209940: 
/*  90:    */         break;
/*  91:    */       case 1347179589: 
/*  92:125 */         readPLTE();
/*  93:126 */         break;
/*  94:    */       case 1951551059: 
/*  95:128 */         readtRNS();
/*  96:    */       }
/*  97:131 */       closeChunk();
/*  98:    */     }
/*  99:134 */     if ((this.colorType == 3) && (this.palette == null)) {
/* 100:135 */       throw new IOException("Missing PLTE chunk");
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getHeight()
/* 105:    */   {
/* 106:140 */     return this.height;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getWidth()
/* 110:    */   {
/* 111:144 */     return this.width;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean hasAlpha()
/* 115:    */   {
/* 116:148 */     return (this.colorType == 6) || 
/* 117:149 */       (this.paletteA != null) || (this.transPixel != null);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isRGB()
/* 121:    */   {
/* 122:153 */     return (this.colorType == 6) || 
/* 123:154 */       (this.colorType == 2) || 
/* 124:155 */       (this.colorType == 3);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Format decideTextureFormat(Format fmt)
/* 128:    */   {
/* 129:166 */     switch (this.colorType)
/* 130:    */     {
/* 131:    */     case 2: 
/* 132:168 */       if ((fmt == ABGR) || (fmt == RGBA) || (fmt == BGRA) || (fmt == RGB)) {
/* 133:169 */         return fmt;
/* 134:    */       }
/* 135:172 */       return RGB;
/* 136:    */     case 6: 
/* 137:174 */       if ((fmt == ABGR) || (fmt == RGBA) || (fmt == BGRA) || (fmt == RGB)) {
/* 138:175 */         return fmt;
/* 139:    */       }
/* 140:178 */       return RGBA;
/* 141:    */     case 0: 
/* 142:180 */       if ((fmt == LUMINANCE) || (fmt == ALPHA)) {
/* 143:181 */         return fmt;
/* 144:    */       }
/* 145:184 */       return LUMINANCE;
/* 146:    */     case 4: 
/* 147:186 */       return LUMINANCE_ALPHA;
/* 148:    */     case 3: 
/* 149:188 */       if ((fmt == ABGR) || (fmt == RGBA) || (fmt == BGRA)) {
/* 150:189 */         return fmt;
/* 151:    */       }
/* 152:192 */       return RGBA;
/* 153:    */     }
/* 154:194 */     throw new UnsupportedOperationException("Not yet implemented");
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void decode(ByteBuffer buffer, int stride, Format fmt)
/* 158:    */     throws IOException
/* 159:    */   {
/* 160:199 */     int offset = buffer.position();
/* 161:200 */     int lineSize = (this.width * this.bitdepth + 7) / 8 * this.bytesPerPixel;
/* 162:201 */     byte[] curLine = new byte[lineSize + 1];
/* 163:202 */     byte[] prevLine = new byte[lineSize + 1];
/* 164:203 */     byte[] palLine = this.bitdepth < 8 ? new byte[this.width + 1] : null;
/* 165:    */     
/* 166:205 */     Inflater inflater = new Inflater();
/* 167:    */     try
/* 168:    */     {
/* 169:207 */       for (int y = 0; y < this.height; y++)
/* 170:    */       {
/* 171:208 */         readChunkUnzip(inflater, curLine, 0, curLine.length);
/* 172:209 */         unfilter(curLine, prevLine);
/* 173:    */         
/* 174:211 */         buffer.position(offset + y * stride);
/* 175:213 */         switch (this.colorType)
/* 176:    */         {
/* 177:    */         case 2: 
/* 178:215 */           if (fmt == ABGR) {
/* 179:216 */             copyRGBtoABGR(buffer, curLine);
/* 180:218 */           } else if (fmt == RGBA) {
/* 181:219 */             copyRGBtoRGBA(buffer, curLine);
/* 182:221 */           } else if (fmt == BGRA) {
/* 183:222 */             copyRGBtoBGRA(buffer, curLine);
/* 184:224 */           } else if (fmt == RGB) {
/* 185:225 */             copy(buffer, curLine);
/* 186:    */           } else {
/* 187:227 */             throw new UnsupportedOperationException("Unsupported format for this image");
/* 188:    */           }
/* 189:    */           break;
/* 190:    */         case 6: 
/* 191:231 */           if (fmt == ABGR) {
/* 192:232 */             copyRGBAtoABGR(buffer, curLine);
/* 193:233 */           } else if (fmt == RGBA) {
/* 194:234 */             copy(buffer, curLine);
/* 195:235 */           } else if (fmt == BGRA) {
/* 196:236 */             copyRGBAtoBGRA(buffer, curLine);
/* 197:237 */           } else if (fmt == RGB) {
/* 198:238 */             copyRGBAtoRGB(buffer, curLine);
/* 199:    */           } else {
/* 200:240 */             throw new UnsupportedOperationException("Unsupported format for this image");
/* 201:    */           }
/* 202:    */           break;
/* 203:    */         case 0: 
/* 204:244 */           if ((fmt == LUMINANCE) || (fmt == ALPHA)) {
/* 205:245 */             copy(buffer, curLine);
/* 206:    */           } else {
/* 207:247 */             throw new UnsupportedOperationException("Unsupported format for this image");
/* 208:    */           }
/* 209:    */           break;
/* 210:    */         case 4: 
/* 211:251 */           if (fmt == LUMINANCE_ALPHA) {
/* 212:252 */             copy(buffer, curLine);
/* 213:    */           } else {
/* 214:254 */             throw new UnsupportedOperationException("Unsupported format for this image");
/* 215:    */           }
/* 216:    */           break;
/* 217:    */         case 3: 
/* 218:258 */           switch (this.bitdepth)
/* 219:    */           {
/* 220:    */           case 8: 
/* 221:259 */             palLine = curLine; break;
/* 222:    */           case 4: 
/* 223:260 */             expand4(curLine, palLine); break;
/* 224:    */           case 2: 
/* 225:261 */             expand2(curLine, palLine); break;
/* 226:    */           case 1: 
/* 227:262 */             expand1(curLine, palLine); break;
/* 228:    */           case 3: 
/* 229:    */           case 5: 
/* 230:    */           case 6: 
/* 231:    */           case 7: 
/* 232:    */           default: 
/* 233:263 */             throw new UnsupportedOperationException("Unsupported bitdepth for this image");
/* 234:    */           }
/* 235:265 */           if (fmt == ABGR) {
/* 236:266 */             copyPALtoABGR(buffer, palLine);
/* 237:267 */           } else if (fmt == RGBA) {
/* 238:268 */             copyPALtoRGBA(buffer, palLine);
/* 239:269 */           } else if (fmt == BGRA) {
/* 240:270 */             copyPALtoBGRA(buffer, palLine);
/* 241:    */           } else {
/* 242:272 */             throw new UnsupportedOperationException("Unsupported format for this image");
/* 243:    */           }
/* 244:    */           break;
/* 245:    */         case 1: 
/* 246:    */         case 5: 
/* 247:    */         default: 
/* 248:276 */           throw new UnsupportedOperationException("Not yet implemented");
/* 249:    */         }
/* 250:279 */         byte[] tmp = curLine;
/* 251:280 */         curLine = prevLine;
/* 252:281 */         prevLine = tmp;
/* 253:    */       }
/* 254:    */     }
/* 255:    */     finally
/* 256:    */     {
/* 257:284 */       inflater.end();
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   private void copy(ByteBuffer buffer, byte[] curLine)
/* 262:    */   {
/* 263:289 */     buffer.put(curLine, 1, curLine.length - 1);
/* 264:    */   }
/* 265:    */   
/* 266:    */   private void copyRGBtoABGR(ByteBuffer buffer, byte[] curLine)
/* 267:    */   {
/* 268:293 */     if (this.transPixel != null)
/* 269:    */     {
/* 270:294 */       byte tr = this.transPixel[1];
/* 271:295 */       byte tg = this.transPixel[3];
/* 272:296 */       byte tb = this.transPixel[5];
/* 273:297 */       int i = 1;
/* 274:297 */       for (int n = curLine.length; i < n; i += 3)
/* 275:    */       {
/* 276:298 */         byte r = curLine[i];
/* 277:299 */         byte g = curLine[(i + 1)];
/* 278:300 */         byte b = curLine[(i + 2)];
/* 279:301 */         byte a = -1;
/* 280:302 */         if ((r == tr) && (g == tg) && (b == tb)) {
/* 281:303 */           a = 0;
/* 282:    */         }
/* 283:305 */         buffer.put(a).put(b).put(g).put(r);
/* 284:    */       }
/* 285:    */     }
/* 286:    */     else
/* 287:    */     {
/* 288:308 */       int i = 1;
/* 289:308 */       for (int n = curLine.length; i < n; i += 3) {
/* 290:309 */         buffer.put((byte)-1).put(curLine[(i + 2)]).put(curLine[(i + 1)]).put(curLine[i]);
/* 291:    */       }
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   private void copyRGBtoRGBA(ByteBuffer buffer, byte[] curLine)
/* 296:    */   {
/* 297:315 */     if (this.transPixel != null)
/* 298:    */     {
/* 299:316 */       byte tr = this.transPixel[1];
/* 300:317 */       byte tg = this.transPixel[3];
/* 301:318 */       byte tb = this.transPixel[5];
/* 302:319 */       int i = 1;
/* 303:319 */       for (int n = curLine.length; i < n; i += 3)
/* 304:    */       {
/* 305:320 */         byte r = curLine[i];
/* 306:321 */         byte g = curLine[(i + 1)];
/* 307:322 */         byte b = curLine[(i + 2)];
/* 308:323 */         byte a = -1;
/* 309:324 */         if ((r == tr) && (g == tg) && (b == tb)) {
/* 310:325 */           a = 0;
/* 311:    */         }
/* 312:327 */         buffer.put(r).put(g).put(b).put(a);
/* 313:    */       }
/* 314:    */     }
/* 315:    */     else
/* 316:    */     {
/* 317:330 */       int i = 1;
/* 318:330 */       for (int n = curLine.length; i < n; i += 3) {
/* 319:331 */         buffer.put(curLine[i]).put(curLine[(i + 1)]).put(curLine[(i + 2)]).put((byte)-1);
/* 320:    */       }
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   private void copyRGBtoBGRA(ByteBuffer buffer, byte[] curLine)
/* 325:    */   {
/* 326:337 */     if (this.transPixel != null)
/* 327:    */     {
/* 328:338 */       byte tr = this.transPixel[1];
/* 329:339 */       byte tg = this.transPixel[3];
/* 330:340 */       byte tb = this.transPixel[5];
/* 331:341 */       int i = 1;
/* 332:341 */       for (int n = curLine.length; i < n; i += 3)
/* 333:    */       {
/* 334:342 */         byte r = curLine[i];
/* 335:343 */         byte g = curLine[(i + 1)];
/* 336:344 */         byte b = curLine[(i + 2)];
/* 337:345 */         byte a = -1;
/* 338:346 */         if ((r == tr) && (g == tg) && (b == tb)) {
/* 339:347 */           a = 0;
/* 340:    */         }
/* 341:349 */         buffer.put(b).put(g).put(r).put(a);
/* 342:    */       }
/* 343:    */     }
/* 344:    */     else
/* 345:    */     {
/* 346:352 */       int i = 1;
/* 347:352 */       for (int n = curLine.length; i < n; i += 3) {
/* 348:353 */         buffer.put(curLine[(i + 2)]).put(curLine[(i + 1)]).put(curLine[i]).put((byte)-1);
/* 349:    */       }
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   private void copyRGBAtoABGR(ByteBuffer buffer, byte[] curLine)
/* 354:    */   {
/* 355:359 */     int i = 1;
/* 356:359 */     for (int n = curLine.length; i < n; i += 4) {
/* 357:360 */       buffer.put(curLine[(i + 3)]).put(curLine[(i + 2)]).put(curLine[(i + 1)]).put(curLine[i]);
/* 358:    */     }
/* 359:    */   }
/* 360:    */   
/* 361:    */   private void copyRGBAtoBGRA(ByteBuffer buffer, byte[] curLine)
/* 362:    */   {
/* 363:365 */     int i = 1;
/* 364:365 */     for (int n = curLine.length; i < n; i += 4) {
/* 365:366 */       buffer.put(curLine[(i + 2)]).put(curLine[(i + 1)]).put(curLine[(i + 0)]).put(curLine[(i + 3)]);
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:    */   private void copyRGBAtoRGB(ByteBuffer buffer, byte[] curLine)
/* 370:    */   {
/* 371:371 */     int i = 1;
/* 372:371 */     for (int n = curLine.length; i < n; i += 4) {
/* 373:372 */       buffer.put(curLine[i]).put(curLine[(i + 1)]).put(curLine[(i + 2)]);
/* 374:    */     }
/* 375:    */   }
/* 376:    */   
/* 377:    */   private void copyPALtoABGR(ByteBuffer buffer, byte[] curLine)
/* 378:    */   {
/* 379:377 */     if (this.paletteA != null)
/* 380:    */     {
/* 381:378 */       int i = 1;
/* 382:378 */       for (int n = curLine.length; i < n; i++)
/* 383:    */       {
/* 384:379 */         int idx = curLine[i] & 0xFF;
/* 385:380 */         byte r = this.palette[(idx * 3 + 0)];
/* 386:381 */         byte g = this.palette[(idx * 3 + 1)];
/* 387:382 */         byte b = this.palette[(idx * 3 + 2)];
/* 388:383 */         byte a = this.paletteA[idx];
/* 389:384 */         buffer.put(a).put(b).put(g).put(r);
/* 390:    */       }
/* 391:    */     }
/* 392:    */     else
/* 393:    */     {
/* 394:387 */       int i = 1;
/* 395:387 */       for (int n = curLine.length; i < n; i++)
/* 396:    */       {
/* 397:388 */         int idx = curLine[i] & 0xFF;
/* 398:389 */         byte r = this.palette[(idx * 3 + 0)];
/* 399:390 */         byte g = this.palette[(idx * 3 + 1)];
/* 400:391 */         byte b = this.palette[(idx * 3 + 2)];
/* 401:392 */         byte a = -1;
/* 402:393 */         buffer.put(a).put(b).put(g).put(r);
/* 403:    */       }
/* 404:    */     }
/* 405:    */   }
/* 406:    */   
/* 407:    */   private void copyPALtoRGBA(ByteBuffer buffer, byte[] curLine)
/* 408:    */   {
/* 409:399 */     if (this.paletteA != null)
/* 410:    */     {
/* 411:400 */       int i = 1;
/* 412:400 */       for (int n = curLine.length; i < n; i++)
/* 413:    */       {
/* 414:401 */         int idx = curLine[i] & 0xFF;
/* 415:402 */         byte r = this.palette[(idx * 3 + 0)];
/* 416:403 */         byte g = this.palette[(idx * 3 + 1)];
/* 417:404 */         byte b = this.palette[(idx * 3 + 2)];
/* 418:405 */         byte a = this.paletteA[idx];
/* 419:406 */         buffer.put(r).put(g).put(b).put(a);
/* 420:    */       }
/* 421:    */     }
/* 422:    */     else
/* 423:    */     {
/* 424:409 */       int i = 1;
/* 425:409 */       for (int n = curLine.length; i < n; i++)
/* 426:    */       {
/* 427:410 */         int idx = curLine[i] & 0xFF;
/* 428:411 */         byte r = this.palette[(idx * 3 + 0)];
/* 429:412 */         byte g = this.palette[(idx * 3 + 1)];
/* 430:413 */         byte b = this.palette[(idx * 3 + 2)];
/* 431:414 */         byte a = -1;
/* 432:415 */         buffer.put(r).put(g).put(b).put(a);
/* 433:    */       }
/* 434:    */     }
/* 435:    */   }
/* 436:    */   
/* 437:    */   private void copyPALtoBGRA(ByteBuffer buffer, byte[] curLine)
/* 438:    */   {
/* 439:421 */     if (this.paletteA != null)
/* 440:    */     {
/* 441:422 */       int i = 1;
/* 442:422 */       for (int n = curLine.length; i < n; i++)
/* 443:    */       {
/* 444:423 */         int idx = curLine[i] & 0xFF;
/* 445:424 */         byte r = this.palette[(idx * 3 + 0)];
/* 446:425 */         byte g = this.palette[(idx * 3 + 1)];
/* 447:426 */         byte b = this.palette[(idx * 3 + 2)];
/* 448:427 */         byte a = this.paletteA[idx];
/* 449:428 */         buffer.put(b).put(g).put(r).put(a);
/* 450:    */       }
/* 451:    */     }
/* 452:    */     else
/* 453:    */     {
/* 454:431 */       int i = 1;
/* 455:431 */       for (int n = curLine.length; i < n; i++)
/* 456:    */       {
/* 457:432 */         int idx = curLine[i] & 0xFF;
/* 458:433 */         byte r = this.palette[(idx * 3 + 0)];
/* 459:434 */         byte g = this.palette[(idx * 3 + 1)];
/* 460:435 */         byte b = this.palette[(idx * 3 + 2)];
/* 461:436 */         byte a = -1;
/* 462:437 */         buffer.put(b).put(g).put(r).put(a);
/* 463:    */       }
/* 464:    */     }
/* 465:    */   }
/* 466:    */   
/* 467:    */   private void expand4(byte[] src, byte[] dst)
/* 468:    */   {
/* 469:443 */     int i = 1;
/* 470:443 */     for (int n = dst.length; i < n; i += 2)
/* 471:    */     {
/* 472:444 */       int val = src[(1 + (i >> 1))] & 0xFF;
/* 473:445 */       switch (n - i)
/* 474:    */       {
/* 475:    */       default: 
/* 476:446 */         dst[(i + 1)] = ((byte)(val & 0xF));
/* 477:    */       }
/* 478:447 */       dst[i] = ((byte)(val >> 4));
/* 479:    */     }
/* 480:    */   }
/* 481:    */   
/* 482:    */   private void expand2(byte[] src, byte[] dst)
/* 483:    */   {
/* 484:453 */     int i = 1;
/* 485:453 */     for (int n = dst.length; i < n; i += 4)
/* 486:    */     {
/* 487:454 */       int val = src[(1 + (i >> 2))] & 0xFF;
/* 488:455 */       switch (n - i)
/* 489:    */       {
/* 490:    */       default: 
/* 491:456 */         dst[(i + 3)] = ((byte)(val & 0x3));
/* 492:    */       case 3: 
/* 493:457 */         dst[(i + 2)] = ((byte)(val >> 2 & 0x3));
/* 494:    */       case 2: 
/* 495:458 */         dst[(i + 1)] = ((byte)(val >> 4 & 0x3));
/* 496:    */       }
/* 497:459 */       dst[i] = ((byte)(val >> 6));
/* 498:    */     }
/* 499:    */   }
/* 500:    */   
/* 501:    */   private void expand1(byte[] src, byte[] dst)
/* 502:    */   {
/* 503:465 */     int i = 1;
/* 504:465 */     for (int n = dst.length; i < n; i += 8)
/* 505:    */     {
/* 506:466 */       int val = src[(1 + (i >> 3))] & 0xFF;
/* 507:467 */       switch (n - i)
/* 508:    */       {
/* 509:    */       default: 
/* 510:468 */         dst[(i + 7)] = ((byte)(val & 0x1));
/* 511:    */       case 7: 
/* 512:469 */         dst[(i + 6)] = ((byte)(val >> 1 & 0x1));
/* 513:    */       case 6: 
/* 514:470 */         dst[(i + 5)] = ((byte)(val >> 2 & 0x1));
/* 515:    */       case 5: 
/* 516:471 */         dst[(i + 4)] = ((byte)(val >> 3 & 0x1));
/* 517:    */       case 4: 
/* 518:472 */         dst[(i + 3)] = ((byte)(val >> 4 & 0x1));
/* 519:    */       case 3: 
/* 520:473 */         dst[(i + 2)] = ((byte)(val >> 5 & 0x1));
/* 521:    */       case 2: 
/* 522:474 */         dst[(i + 1)] = ((byte)(val >> 6 & 0x1));
/* 523:    */       }
/* 524:475 */       dst[i] = ((byte)(val >> 7));
/* 525:    */     }
/* 526:    */   }
/* 527:    */   
/* 528:    */   private void unfilter(byte[] curLine, byte[] prevLine)
/* 529:    */     throws IOException
/* 530:    */   {
/* 531:481 */     switch (curLine[0])
/* 532:    */     {
/* 533:    */     case 0: 
/* 534:    */       break;
/* 535:    */     case 1: 
/* 536:485 */       unfilterSub(curLine);
/* 537:486 */       break;
/* 538:    */     case 2: 
/* 539:488 */       unfilterUp(curLine, prevLine);
/* 540:489 */       break;
/* 541:    */     case 3: 
/* 542:491 */       unfilterAverage(curLine, prevLine);
/* 543:492 */       break;
/* 544:    */     case 4: 
/* 545:494 */       unfilterPaeth(curLine, prevLine);
/* 546:495 */       break;
/* 547:    */     default: 
/* 548:497 */       throw new IOException("invalide filter type in scanline: " + curLine[0]);
/* 549:    */     }
/* 550:    */   }
/* 551:    */   
/* 552:    */   private void unfilterSub(byte[] curLine)
/* 553:    */   {
/* 554:502 */     int bpp = this.bytesPerPixel;
/* 555:503 */     int i = bpp + 1;
/* 556:503 */     for (int n = curLine.length; i < n; i++)
/* 557:    */     {
/* 558:504 */       int tmp18_17 = i; byte[] tmp18_16 = curLine;tmp18_16[tmp18_17] = ((byte)(tmp18_16[tmp18_17] + curLine[(i - bpp)]));
/* 559:    */     }
/* 560:    */   }
/* 561:    */   
/* 562:    */   private void unfilterUp(byte[] curLine, byte[] prevLine)
/* 563:    */   {
/* 564:509 */     int bpp = this.bytesPerPixel;
/* 565:510 */     int i = 1;
/* 566:510 */     for (int n = curLine.length; i < n; i++)
/* 567:    */     {
/* 568:511 */       int tmp18_16 = i; byte[] tmp18_15 = curLine;tmp18_15[tmp18_16] = ((byte)(tmp18_15[tmp18_16] + prevLine[i]));
/* 569:    */     }
/* 570:    */   }
/* 571:    */   
/* 572:    */   private void unfilterAverage(byte[] curLine, byte[] prevLine)
/* 573:    */   {
/* 574:516 */     int bpp = this.bytesPerPixel;
/* 575:519 */     for (int i = 1; i <= bpp; i++)
/* 576:    */     {
/* 577:520 */       int tmp14_12 = i; byte[] tmp14_11 = curLine;tmp14_11[tmp14_12] = ((byte)(tmp14_11[tmp14_12] + (byte)((prevLine[i] & 0xFF) >>> 1)));
/* 578:    */     }
/* 579:522 */     for (int n = curLine.length; i < n; i++)
/* 580:    */     {
/* 581:523 */       int tmp49_47 = i; byte[] tmp49_46 = curLine;tmp49_46[tmp49_47] = ((byte)(tmp49_46[tmp49_47] + (byte)((prevLine[i] & 0xFF) + (curLine[(i - bpp)] & 0xFF) >>> 1)));
/* 582:    */     }
/* 583:    */   }
/* 584:    */   
/* 585:    */   private void unfilterPaeth(byte[] curLine, byte[] prevLine)
/* 586:    */   {
/* 587:528 */     int bpp = this.bytesPerPixel;
/* 588:531 */     for (int i = 1; i <= bpp; i++)
/* 589:    */     {
/* 590:532 */       int tmp14_12 = i; byte[] tmp14_11 = curLine;tmp14_11[tmp14_12] = ((byte)(tmp14_11[tmp14_12] + prevLine[i]));
/* 591:    */     }
/* 592:534 */     for (int n = curLine.length; i < n; i++)
/* 593:    */     {
/* 594:535 */       int a = curLine[(i - bpp)] & 0xFF;
/* 595:536 */       int b = prevLine[i] & 0xFF;
/* 596:537 */       int c = prevLine[(i - bpp)] & 0xFF;
/* 597:538 */       int p = a + b - c;
/* 598:539 */       int pa = p - a;
/* 599:539 */       if (pa < 0) {
/* 600:539 */         pa = -pa;
/* 601:    */       }
/* 602:540 */       int pb = p - b;
/* 603:540 */       if (pb < 0) {
/* 604:540 */         pb = -pb;
/* 605:    */       }
/* 606:541 */       int pc = p - c;
/* 607:541 */       if (pc < 0) {
/* 608:541 */         pc = -pc;
/* 609:    */       }
/* 610:542 */       if ((pa <= pb) && (pa <= pc)) {
/* 611:543 */         c = a;
/* 612:544 */       } else if (pb <= pc) {
/* 613:545 */         c = b;
/* 614:    */       }
/* 615:546 */       int tmp169_167 = i; byte[] tmp169_166 = curLine;tmp169_166[tmp169_167] = ((byte)(tmp169_166[tmp169_167] + (byte)c));
/* 616:    */     }
/* 617:    */   }
/* 618:    */   
/* 619:    */   private void readIHDR()
/* 620:    */     throws IOException
/* 621:    */   {
/* 622:551 */     checkChunkLength(13);
/* 623:552 */     readChunk(this.buffer, 0, 13);
/* 624:553 */     this.width = readInt(this.buffer, 0);
/* 625:554 */     this.height = readInt(this.buffer, 4);
/* 626:555 */     this.bitdepth = (this.buffer[8] & 0xFF);
/* 627:556 */     this.colorType = (this.buffer[9] & 0xFF);
/* 628:558 */     switch (this.colorType)
/* 629:    */     {
/* 630:    */     case 0: 
/* 631:560 */       if (this.bitdepth != 8) {
/* 632:561 */         throw new IOException("Unsupported bit depth: " + this.bitdepth);
/* 633:    */       }
/* 634:563 */       this.bytesPerPixel = 1;
/* 635:564 */       break;
/* 636:    */     case 4: 
/* 637:566 */       if (this.bitdepth != 8) {
/* 638:567 */         throw new IOException("Unsupported bit depth: " + this.bitdepth);
/* 639:    */       }
/* 640:569 */       this.bytesPerPixel = 2;
/* 641:570 */       break;
/* 642:    */     case 2: 
/* 643:572 */       if (this.bitdepth != 8) {
/* 644:573 */         throw new IOException("Unsupported bit depth: " + this.bitdepth);
/* 645:    */       }
/* 646:575 */       this.bytesPerPixel = 3;
/* 647:576 */       break;
/* 648:    */     case 6: 
/* 649:578 */       if (this.bitdepth != 8) {
/* 650:579 */         throw new IOException("Unsupported bit depth: " + this.bitdepth);
/* 651:    */       }
/* 652:581 */       this.bytesPerPixel = 4;
/* 653:582 */       break;
/* 654:    */     case 3: 
/* 655:584 */       switch (this.bitdepth)
/* 656:    */       {
/* 657:    */       case 1: 
/* 658:    */       case 2: 
/* 659:    */       case 4: 
/* 660:    */       case 8: 
/* 661:589 */         this.bytesPerPixel = 1;
/* 662:590 */         break;
/* 663:    */       case 3: 
/* 664:    */       case 5: 
/* 665:    */       case 6: 
/* 666:    */       case 7: 
/* 667:    */       default: 
/* 668:592 */         throw new IOException("Unsupported bit depth: " + this.bitdepth);
/* 669:    */       }
/* 670:    */       break;
/* 671:    */     case 1: 
/* 672:    */     case 5: 
/* 673:    */     default: 
/* 674:596 */       throw new IOException("unsupported color format: " + this.colorType);
/* 675:    */     }
/* 676:599 */     if (this.buffer[10] != 0) {
/* 677:600 */       throw new IOException("unsupported compression method");
/* 678:    */     }
/* 679:602 */     if (this.buffer[11] != 0) {
/* 680:603 */       throw new IOException("unsupported filtering method");
/* 681:    */     }
/* 682:605 */     if (this.buffer[12] != 0) {
/* 683:606 */       throw new IOException("unsupported interlace method");
/* 684:    */     }
/* 685:    */   }
/* 686:    */   
/* 687:    */   private void readPLTE()
/* 688:    */     throws IOException
/* 689:    */   {
/* 690:611 */     int paletteEntries = this.chunkLength / 3;
/* 691:612 */     if ((paletteEntries < 1) || (paletteEntries > 256) || (this.chunkLength % 3 != 0)) {
/* 692:613 */       throw new IOException("PLTE chunk has wrong length");
/* 693:    */     }
/* 694:615 */     this.palette = new byte[paletteEntries * 3];
/* 695:616 */     readChunk(this.palette, 0, this.palette.length);
/* 696:    */   }
/* 697:    */   
/* 698:    */   private void readtRNS()
/* 699:    */     throws IOException
/* 700:    */   {
/* 701:620 */     switch (this.colorType)
/* 702:    */     {
/* 703:    */     case 0: 
/* 704:622 */       checkChunkLength(2);
/* 705:623 */       this.transPixel = new byte[2];
/* 706:624 */       readChunk(this.transPixel, 0, 2);
/* 707:625 */       break;
/* 708:    */     case 2: 
/* 709:627 */       checkChunkLength(6);
/* 710:628 */       this.transPixel = new byte[6];
/* 711:629 */       readChunk(this.transPixel, 0, 6);
/* 712:630 */       break;
/* 713:    */     case 3: 
/* 714:632 */       if (this.palette == null) {
/* 715:633 */         throw new IOException("tRNS chunk without PLTE chunk");
/* 716:    */       }
/* 717:635 */       this.paletteA = new byte[this.palette.length / 3];
/* 718:636 */       Arrays.fill(this.paletteA, (byte)-1);
/* 719:637 */       readChunk(this.paletteA, 0, this.paletteA.length);
/* 720:    */     }
/* 721:    */   }
/* 722:    */   
/* 723:    */   private void closeChunk()
/* 724:    */     throws IOException
/* 725:    */   {
/* 726:645 */     if (this.chunkRemaining > 0)
/* 727:    */     {
/* 728:647 */       skip(this.chunkRemaining + 4);
/* 729:    */     }
/* 730:    */     else
/* 731:    */     {
/* 732:649 */       readFully(this.buffer, 0, 4);
/* 733:650 */       int expectedCrc = readInt(this.buffer, 0);
/* 734:651 */       int computedCrc = (int)this.crc.getValue();
/* 735:652 */       if (computedCrc != expectedCrc) {
/* 736:653 */         throw new IOException("Invalid CRC");
/* 737:    */       }
/* 738:    */     }
/* 739:656 */     this.chunkRemaining = 0;
/* 740:657 */     this.chunkLength = 0;
/* 741:658 */     this.chunkType = 0;
/* 742:    */   }
/* 743:    */   
/* 744:    */   private void openChunk()
/* 745:    */     throws IOException
/* 746:    */   {
/* 747:662 */     readFully(this.buffer, 0, 8);
/* 748:663 */     this.chunkLength = readInt(this.buffer, 0);
/* 749:664 */     this.chunkType = readInt(this.buffer, 4);
/* 750:665 */     this.chunkRemaining = this.chunkLength;
/* 751:666 */     this.crc.reset();
/* 752:667 */     this.crc.update(this.buffer, 4, 4);
/* 753:    */   }
/* 754:    */   
/* 755:    */   private void openChunk(int expected)
/* 756:    */     throws IOException
/* 757:    */   {
/* 758:671 */     openChunk();
/* 759:672 */     if (this.chunkType != expected) {
/* 760:673 */       throw new IOException("Expected chunk: " + Integer.toHexString(expected));
/* 761:    */     }
/* 762:    */   }
/* 763:    */   
/* 764:    */   private void checkChunkLength(int expected)
/* 765:    */     throws IOException
/* 766:    */   {
/* 767:678 */     if (this.chunkLength != expected) {
/* 768:679 */       throw new IOException("Chunk has wrong size");
/* 769:    */     }
/* 770:    */   }
/* 771:    */   
/* 772:    */   private int readChunk(byte[] buffer, int offset, int length)
/* 773:    */     throws IOException
/* 774:    */   {
/* 775:684 */     if (length > this.chunkRemaining) {
/* 776:685 */       length = this.chunkRemaining;
/* 777:    */     }
/* 778:687 */     readFully(buffer, offset, length);
/* 779:688 */     this.crc.update(buffer, offset, length);
/* 780:689 */     this.chunkRemaining -= length;
/* 781:690 */     return length;
/* 782:    */   }
/* 783:    */   
/* 784:    */   private void refillInflater(Inflater inflater)
/* 785:    */     throws IOException
/* 786:    */   {
/* 787:694 */     while (this.chunkRemaining == 0)
/* 788:    */     {
/* 789:695 */       closeChunk();
/* 790:696 */       openChunk(1229209940);
/* 791:    */     }
/* 792:698 */     int read = readChunk(this.buffer, 0, this.buffer.length);
/* 793:699 */     inflater.setInput(this.buffer, 0, read);
/* 794:    */   }
/* 795:    */   
/* 796:    */   private void readChunkUnzip(Inflater inflater, byte[] buffer, int offset, int length)
/* 797:    */     throws IOException
/* 798:    */   {
/* 799:    */     try
/* 800:    */     {
/* 801:    */       do
/* 802:    */       {
/* 803:705 */         int read = inflater.inflate(buffer, offset, length);
/* 804:706 */         if (read <= 0)
/* 805:    */         {
/* 806:707 */           if (inflater.finished()) {
/* 807:708 */             throw new EOFException();
/* 808:    */           }
/* 809:710 */           if (inflater.needsInput()) {
/* 810:711 */             refillInflater(inflater);
/* 811:    */           } else {
/* 812:713 */             throw new IOException("Can't inflate " + length + " bytes");
/* 813:    */           }
/* 814:    */         }
/* 815:    */         else
/* 816:    */         {
/* 817:716 */           offset += read;
/* 818:717 */           length -= read;
/* 819:    */         }
/* 820:704 */       } while (
/* 821:    */       
/* 822:    */ 
/* 823:    */ 
/* 824:    */ 
/* 825:    */ 
/* 826:    */ 
/* 827:    */ 
/* 828:    */ 
/* 829:    */ 
/* 830:    */ 
/* 831:    */ 
/* 832:    */ 
/* 833:    */ 
/* 834:    */ 
/* 835:719 */         length > 0);
/* 836:    */     }
/* 837:    */     catch (DataFormatException ex)
/* 838:    */     {
/* 839:721 */       throw ((IOException)new IOException("inflate error").initCause(ex));
/* 840:    */     }
/* 841:    */   }
/* 842:    */   
/* 843:    */   private void readFully(byte[] buffer, int offset, int length)
/* 844:    */     throws IOException
/* 845:    */   {
/* 846:    */     do
/* 847:    */     {
/* 848:727 */       int read = this.input.read(buffer, offset, length);
/* 849:728 */       if (read < 0) {
/* 850:729 */         throw new EOFException();
/* 851:    */       }
/* 852:731 */       offset += read;
/* 853:732 */       length -= read;
/* 854:726 */     } while (
/* 855:    */     
/* 856:    */ 
/* 857:    */ 
/* 858:    */ 
/* 859:    */ 
/* 860:    */ 
/* 861:733 */       length > 0);
/* 862:    */   }
/* 863:    */   
/* 864:    */   private int readInt(byte[] buffer, int offset)
/* 865:    */   {
/* 866:737 */     return 
/* 867:738 */       buffer[offset] << 24 | 
/* 868:739 */       (buffer[(offset + 1)] & 0xFF) << 16 | 
/* 869:740 */       (buffer[(offset + 2)] & 0xFF) << 8 | 
/* 870:741 */       buffer[(offset + 3)] & 0xFF;
/* 871:    */   }
/* 872:    */   
/* 873:    */   private void skip(long amount)
/* 874:    */     throws IOException
/* 875:    */   {
/* 876:745 */     while (amount > 0L)
/* 877:    */     {
/* 878:746 */       long skipped = this.input.skip(amount);
/* 879:747 */       if (skipped < 0L) {
/* 880:748 */         throw new EOFException();
/* 881:    */       }
/* 882:750 */       amount -= skipped;
/* 883:    */     }
/* 884:    */   }
/* 885:    */   
/* 886:    */   private static boolean checkSignature(byte[] buffer)
/* 887:    */   {
/* 888:755 */     for (int i = 0; i < SIGNATURE.length; i++) {
/* 889:756 */       if (buffer[i] != SIGNATURE[i]) {
/* 890:757 */         return false;
/* 891:    */       }
/* 892:    */     }
/* 893:760 */     return true;
/* 894:    */   }
/* 895:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.PNGDecoder
 * JD-Core Version:    0.7.0.1
 */