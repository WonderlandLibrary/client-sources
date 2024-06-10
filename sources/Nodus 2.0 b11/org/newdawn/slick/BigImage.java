/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.nio.ByteBuffer;
/*   5:    */ import java.nio.IntBuffer;
/*   6:    */ import org.lwjgl.BufferUtils;
/*   7:    */ import org.newdawn.slick.opengl.ImageData;
/*   8:    */ import org.newdawn.slick.opengl.ImageDataFactory;
/*   9:    */ import org.newdawn.slick.opengl.LoadableImageData;
/*  10:    */ import org.newdawn.slick.opengl.Texture;
/*  11:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  12:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  13:    */ import org.newdawn.slick.util.OperationNotSupportedException;
/*  14:    */ import org.newdawn.slick.util.ResourceLoader;
/*  15:    */ 
/*  16:    */ public class BigImage
/*  17:    */   extends Image
/*  18:    */ {
/*  19: 34 */   protected static SGL GL = ;
/*  20:    */   private static Image lastBind;
/*  21:    */   private Image[][] images;
/*  22:    */   private int xcount;
/*  23:    */   private int ycount;
/*  24:    */   private int realWidth;
/*  25:    */   private int realHeight;
/*  26:    */   
/*  27:    */   public static final int getMaxSingleImageSize()
/*  28:    */   {
/*  29: 44 */     IntBuffer buffer = BufferUtils.createIntBuffer(16);
/*  30: 45 */     GL.glGetInteger(3379, buffer);
/*  31:    */     
/*  32: 47 */     return buffer.get(0);
/*  33:    */   }
/*  34:    */   
/*  35:    */   private BigImage()
/*  36:    */   {
/*  37: 68 */     this.inited = true;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public BigImage(String ref)
/*  41:    */     throws SlickException
/*  42:    */   {
/*  43: 78 */     this(ref, 2);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public BigImage(String ref, int filter)
/*  47:    */     throws SlickException
/*  48:    */   {
/*  49: 90 */     build(ref, filter, getMaxSingleImageSize());
/*  50:    */   }
/*  51:    */   
/*  52:    */   public BigImage(String ref, int filter, int tileSize)
/*  53:    */     throws SlickException
/*  54:    */   {
/*  55:102 */     build(ref, filter, tileSize);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public BigImage(LoadableImageData data, ByteBuffer imageBuffer, int filter)
/*  59:    */   {
/*  60:113 */     build(data, imageBuffer, filter, getMaxSingleImageSize());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public BigImage(LoadableImageData data, ByteBuffer imageBuffer, int filter, int tileSize)
/*  64:    */   {
/*  65:125 */     build(data, imageBuffer, filter, tileSize);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Image getTile(int x, int y)
/*  69:    */   {
/*  70:136 */     return this.images[x][y];
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void build(String ref, int filter, int tileSize)
/*  74:    */     throws SlickException
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:149 */       LoadableImageData data = ImageDataFactory.getImageDataFor(ref);
/*  79:150 */       ByteBuffer imageBuffer = data.loadImage(ResourceLoader.getResourceAsStream(ref), false, null);
/*  80:151 */       build(data, imageBuffer, filter, tileSize);
/*  81:    */     }
/*  82:    */     catch (IOException e)
/*  83:    */     {
/*  84:153 */       throw new SlickException("Failed to load: " + ref, e);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void build(final LoadableImageData data, final ByteBuffer imageBuffer, int filter, int tileSize)
/*  89:    */   {
/*  90:166 */     final int dataWidth = data.getTexWidth();
/*  91:167 */     final int dataHeight = data.getTexHeight();
/*  92:    */     
/*  93:169 */     this.realWidth = (this.width = data.getWidth());
/*  94:170 */     this.realHeight = (this.height = data.getHeight());
/*  95:172 */     if ((dataWidth <= tileSize) && (dataHeight <= tileSize))
/*  96:    */     {
/*  97:173 */       this.images = new Image[1][1];
/*  98:174 */       ImageData tempData = new ImageData()
/*  99:    */       {
/* 100:    */         public int getDepth()
/* 101:    */         {
/* 102:176 */           return data.getDepth();
/* 103:    */         }
/* 104:    */         
/* 105:    */         public int getHeight()
/* 106:    */         {
/* 107:180 */           return dataHeight;
/* 108:    */         }
/* 109:    */         
/* 110:    */         public ByteBuffer getImageBufferData()
/* 111:    */         {
/* 112:184 */           return imageBuffer;
/* 113:    */         }
/* 114:    */         
/* 115:    */         public int getTexHeight()
/* 116:    */         {
/* 117:188 */           return dataHeight;
/* 118:    */         }
/* 119:    */         
/* 120:    */         public int getTexWidth()
/* 121:    */         {
/* 122:192 */           return dataWidth;
/* 123:    */         }
/* 124:    */         
/* 125:    */         public int getWidth()
/* 126:    */         {
/* 127:196 */           return dataWidth;
/* 128:    */         }
/* 129:198 */       };
/* 130:199 */       this.images[0][0] = new Image(tempData, filter);
/* 131:200 */       this.xcount = 1;
/* 132:201 */       this.ycount = 1;
/* 133:202 */       this.inited = true;
/* 134:203 */       return;
/* 135:    */     }
/* 136:206 */     this.xcount = ((this.realWidth - 1) / tileSize + 1);
/* 137:207 */     this.ycount = ((this.realHeight - 1) / tileSize + 1);
/* 138:    */     
/* 139:209 */     this.images = new Image[this.xcount][this.ycount];
/* 140:210 */     int components = data.getDepth() / 8;
/* 141:212 */     for (int x = 0; x < this.xcount; x++) {
/* 142:213 */       for (int y = 0; y < this.ycount; y++)
/* 143:    */       {
/* 144:214 */         int finalX = (x + 1) * tileSize;
/* 145:215 */         int finalY = (y + 1) * tileSize;
/* 146:216 */         final int imageWidth = Math.min(this.realWidth - x * tileSize, tileSize);
/* 147:217 */         final int imageHeight = Math.min(this.realHeight - y * tileSize, tileSize);
/* 148:    */         
/* 149:219 */         final int xSize = tileSize;
/* 150:220 */         final int ySize = tileSize;
/* 151:    */         
/* 152:222 */         final ByteBuffer subBuffer = BufferUtils.createByteBuffer(tileSize * tileSize * components);
/* 153:223 */         int xo = x * tileSize * components;
/* 154:    */         
/* 155:225 */         byte[] byteData = new byte[xSize * components];
/* 156:226 */         for (int i = 0; i < ySize; i++)
/* 157:    */         {
/* 158:227 */           int yo = (y * tileSize + i) * dataWidth * components;
/* 159:228 */           imageBuffer.position(yo + xo);
/* 160:    */           
/* 161:230 */           imageBuffer.get(byteData, 0, xSize * components);
/* 162:231 */           subBuffer.put(byteData);
/* 163:    */         }
/* 164:234 */         subBuffer.flip();
/* 165:235 */         ImageData imgData = new ImageData()
/* 166:    */         {
/* 167:    */           public int getDepth()
/* 168:    */           {
/* 169:237 */             return data.getDepth();
/* 170:    */           }
/* 171:    */           
/* 172:    */           public int getHeight()
/* 173:    */           {
/* 174:241 */             return imageHeight;
/* 175:    */           }
/* 176:    */           
/* 177:    */           public int getWidth()
/* 178:    */           {
/* 179:245 */             return imageWidth;
/* 180:    */           }
/* 181:    */           
/* 182:    */           public ByteBuffer getImageBufferData()
/* 183:    */           {
/* 184:249 */             return subBuffer;
/* 185:    */           }
/* 186:    */           
/* 187:    */           public int getTexHeight()
/* 188:    */           {
/* 189:253 */             return ySize;
/* 190:    */           }
/* 191:    */           
/* 192:    */           public int getTexWidth()
/* 193:    */           {
/* 194:257 */             return xSize;
/* 195:    */           }
/* 196:259 */         };
/* 197:260 */         this.images[x][y] = new Image(imgData, filter);
/* 198:    */       }
/* 199:    */     }
/* 200:264 */     this.inited = true;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void bind()
/* 204:    */   {
/* 205:273 */     throw new OperationNotSupportedException("Can't bind big images yet");
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Image copy()
/* 209:    */   {
/* 210:282 */     throw new OperationNotSupportedException("Can't copy big images yet");
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void draw()
/* 214:    */   {
/* 215:289 */     draw(0.0F, 0.0F);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void draw(float x, float y, Color filter)
/* 219:    */   {
/* 220:296 */     draw(x, y, this.width, this.height, filter);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void draw(float x, float y, float scale, Color filter)
/* 224:    */   {
/* 225:303 */     draw(x, y, this.width * scale, this.height * scale, filter);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void draw(float x, float y, float width, float height, Color filter)
/* 229:    */   {
/* 230:310 */     float sx = width / this.realWidth;
/* 231:311 */     float sy = height / this.realHeight;
/* 232:    */     
/* 233:313 */     GL.glTranslatef(x, y, 0.0F);
/* 234:314 */     GL.glScalef(sx, sy, 1.0F);
/* 235:    */     
/* 236:316 */     float xp = 0.0F;
/* 237:317 */     float yp = 0.0F;
/* 238:319 */     for (int tx = 0; tx < this.xcount; tx++)
/* 239:    */     {
/* 240:320 */       yp = 0.0F;
/* 241:321 */       for (int ty = 0; ty < this.ycount; ty++)
/* 242:    */       {
/* 243:322 */         Image image = this.images[tx][ty];
/* 244:    */         
/* 245:324 */         image.draw(xp, yp, image.getWidth(), image.getHeight(), filter);
/* 246:    */         
/* 247:326 */         yp += image.getHeight();
/* 248:327 */         if (ty == this.ycount - 1) {
/* 249:328 */           xp += image.getWidth();
/* 250:    */         }
/* 251:    */       }
/* 252:    */     }
/* 253:334 */     GL.glScalef(1.0F / sx, 1.0F / sy, 1.0F);
/* 254:335 */     GL.glTranslatef(-x, -y, 0.0F);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
/* 258:    */   {
/* 259:342 */     int srcwidth = (int)(srcx2 - srcx);
/* 260:343 */     int srcheight = (int)(srcy2 - srcy);
/* 261:    */     
/* 262:345 */     Image subImage = getSubImage((int)srcx, (int)srcy, srcwidth, srcheight);
/* 263:    */     
/* 264:347 */     int width = (int)(x2 - x);
/* 265:348 */     int height = (int)(y2 - y);
/* 266:    */     
/* 267:350 */     subImage.draw(x, y, width, height);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void draw(float x, float y, float srcx, float srcy, float srcx2, float srcy2)
/* 271:    */   {
/* 272:357 */     int srcwidth = (int)(srcx2 - srcx);
/* 273:358 */     int srcheight = (int)(srcy2 - srcy);
/* 274:    */     
/* 275:360 */     draw(x, y, srcwidth, srcheight, srcx, srcy, srcx2, srcy2);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void draw(float x, float y, float width, float height)
/* 279:    */   {
/* 280:367 */     draw(x, y, width, height, Color.white);
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void draw(float x, float y, float scale)
/* 284:    */   {
/* 285:374 */     draw(x, y, scale, Color.white);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void draw(float x, float y)
/* 289:    */   {
/* 290:381 */     draw(x, y, Color.white);
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void drawEmbedded(float x, float y, float width, float height)
/* 294:    */   {
/* 295:388 */     float sx = width / this.realWidth;
/* 296:389 */     float sy = height / this.realHeight;
/* 297:    */     
/* 298:391 */     float xp = 0.0F;
/* 299:392 */     float yp = 0.0F;
/* 300:394 */     for (int tx = 0; tx < this.xcount; tx++)
/* 301:    */     {
/* 302:395 */       yp = 0.0F;
/* 303:396 */       for (int ty = 0; ty < this.ycount; ty++)
/* 304:    */       {
/* 305:397 */         Image image = this.images[tx][ty];
/* 306:399 */         if ((lastBind == null) || (image.getTexture() != lastBind.getTexture()))
/* 307:    */         {
/* 308:400 */           if (lastBind != null) {
/* 309:401 */             lastBind.endUse();
/* 310:    */           }
/* 311:403 */           lastBind = image;
/* 312:404 */           lastBind.startUse();
/* 313:    */         }
/* 314:406 */         image.drawEmbedded(xp + x, yp + y, image.getWidth(), image.getHeight());
/* 315:    */         
/* 316:408 */         yp += image.getHeight();
/* 317:409 */         if (ty == this.ycount - 1) {
/* 318:410 */           xp += image.getWidth();
/* 319:    */         }
/* 320:    */       }
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void drawFlash(float x, float y, float width, float height)
/* 325:    */   {
/* 326:421 */     float sx = width / this.realWidth;
/* 327:422 */     float sy = height / this.realHeight;
/* 328:    */     
/* 329:424 */     GL.glTranslatef(x, y, 0.0F);
/* 330:425 */     GL.glScalef(sx, sy, 1.0F);
/* 331:    */     
/* 332:427 */     float xp = 0.0F;
/* 333:428 */     float yp = 0.0F;
/* 334:430 */     for (int tx = 0; tx < this.xcount; tx++)
/* 335:    */     {
/* 336:431 */       yp = 0.0F;
/* 337:432 */       for (int ty = 0; ty < this.ycount; ty++)
/* 338:    */       {
/* 339:433 */         Image image = this.images[tx][ty];
/* 340:    */         
/* 341:435 */         image.drawFlash(xp, yp, image.getWidth(), image.getHeight());
/* 342:    */         
/* 343:437 */         yp += image.getHeight();
/* 344:438 */         if (ty == this.ycount - 1) {
/* 345:439 */           xp += image.getWidth();
/* 346:    */         }
/* 347:    */       }
/* 348:    */     }
/* 349:445 */     GL.glScalef(1.0F / sx, 1.0F / sy, 1.0F);
/* 350:446 */     GL.glTranslatef(-x, -y, 0.0F);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void drawFlash(float x, float y)
/* 354:    */   {
/* 355:453 */     drawFlash(x, y, this.width, this.height);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void endUse()
/* 359:    */   {
/* 360:462 */     if (lastBind != null) {
/* 361:463 */       lastBind.endUse();
/* 362:    */     }
/* 363:465 */     lastBind = null;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void startUse() {}
/* 367:    */   
/* 368:    */   public void ensureInverted()
/* 369:    */   {
/* 370:482 */     throw new OperationNotSupportedException("Doesn't make sense for tiled operations");
/* 371:    */   }
/* 372:    */   
/* 373:    */   public Color getColor(int x, int y)
/* 374:    */   {
/* 375:491 */     throw new OperationNotSupportedException("Can't use big images as buffers");
/* 376:    */   }
/* 377:    */   
/* 378:    */   public Image getFlippedCopy(boolean flipHorizontal, boolean flipVertical)
/* 379:    */   {
/* 380:498 */     BigImage image = new BigImage();
/* 381:    */     
/* 382:500 */     image.images = this.images;
/* 383:501 */     image.xcount = this.xcount;
/* 384:502 */     image.ycount = this.ycount;
/* 385:503 */     image.width = this.width;
/* 386:504 */     image.height = this.height;
/* 387:505 */     image.realWidth = this.realWidth;
/* 388:506 */     image.realHeight = this.realHeight;
/* 389:508 */     if (flipHorizontal)
/* 390:    */     {
/* 391:509 */       Image[][] images = image.images;
/* 392:510 */       image.images = new Image[this.xcount][this.ycount];
/* 393:512 */       for (int x = 0; x < this.xcount; x++) {
/* 394:513 */         for (int y = 0; y < this.ycount; y++) {
/* 395:514 */           image.images[x][y] = images[(this.xcount - 1 - x)][y].getFlippedCopy(true, false);
/* 396:    */         }
/* 397:    */       }
/* 398:    */     }
/* 399:519 */     if (flipVertical)
/* 400:    */     {
/* 401:520 */       Image[][] images = image.images;
/* 402:521 */       image.images = new Image[this.xcount][this.ycount];
/* 403:523 */       for (int x = 0; x < this.xcount; x++) {
/* 404:524 */         for (int y = 0; y < this.ycount; y++) {
/* 405:525 */           image.images[x][y] = images[x][(this.ycount - 1 - y)].getFlippedCopy(false, true);
/* 406:    */         }
/* 407:    */       }
/* 408:    */     }
/* 409:530 */     return image;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public Graphics getGraphics()
/* 413:    */     throws SlickException
/* 414:    */   {
/* 415:539 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/* 416:    */   }
/* 417:    */   
/* 418:    */   public Image getScaledCopy(float scale)
/* 419:    */   {
/* 420:546 */     return getScaledCopy((int)(scale * this.width), (int)(scale * this.height));
/* 421:    */   }
/* 422:    */   
/* 423:    */   public Image getScaledCopy(int width, int height)
/* 424:    */   {
/* 425:553 */     BigImage image = new BigImage();
/* 426:    */     
/* 427:555 */     image.images = this.images;
/* 428:556 */     image.xcount = this.xcount;
/* 429:557 */     image.ycount = this.ycount;
/* 430:558 */     image.width = width;
/* 431:559 */     image.height = height;
/* 432:560 */     image.realWidth = this.realWidth;
/* 433:561 */     image.realHeight = this.realHeight;
/* 434:    */     
/* 435:563 */     return image;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public Image getSubImage(int x, int y, int width, int height)
/* 439:    */   {
/* 440:570 */     BigImage image = new BigImage();
/* 441:    */     
/* 442:572 */     image.width = width;
/* 443:573 */     image.height = height;
/* 444:574 */     image.realWidth = width;
/* 445:575 */     image.realHeight = height;
/* 446:576 */     image.images = new Image[this.xcount][this.ycount];
/* 447:    */     
/* 448:578 */     float xp = 0.0F;
/* 449:579 */     float yp = 0.0F;
/* 450:580 */     int x2 = x + width;
/* 451:581 */     int y2 = y + height;
/* 452:    */     
/* 453:583 */     int startx = 0;
/* 454:584 */     int starty = 0;
/* 455:585 */     boolean foundStart = false;
/* 456:587 */     for (int xt = 0; xt < this.xcount; xt++)
/* 457:    */     {
/* 458:588 */       yp = 0.0F;
/* 459:589 */       starty = 0;
/* 460:590 */       foundStart = false;
/* 461:591 */       for (int yt = 0; yt < this.ycount; yt++)
/* 462:    */       {
/* 463:592 */         Image current = this.images[xt][yt];
/* 464:    */         
/* 465:594 */         int xp2 = (int)(xp + current.getWidth());
/* 466:595 */         int yp2 = (int)(yp + current.getHeight());
/* 467:    */         
/* 468:    */ 
/* 469:    */ 
/* 470:    */ 
/* 471:    */ 
/* 472:    */ 
/* 473:602 */         int targetX1 = (int)Math.max(x, xp);
/* 474:603 */         int targetY1 = (int)Math.max(y, yp);
/* 475:604 */         int targetX2 = Math.min(x2, xp2);
/* 476:605 */         int targetY2 = Math.min(y2, yp2);
/* 477:    */         
/* 478:607 */         int targetWidth = targetX2 - targetX1;
/* 479:608 */         int targetHeight = targetY2 - targetY1;
/* 480:610 */         if ((targetWidth > 0) && (targetHeight > 0))
/* 481:    */         {
/* 482:611 */           Image subImage = current.getSubImage((int)(targetX1 - xp), (int)(targetY1 - yp), 
/* 483:612 */             targetX2 - targetX1, 
/* 484:613 */             targetY2 - targetY1);
/* 485:614 */           foundStart = true;
/* 486:615 */           image.images[startx][starty] = subImage;
/* 487:616 */           starty++;
/* 488:617 */           image.ycount = Math.max(image.ycount, starty);
/* 489:    */         }
/* 490:620 */         yp += current.getHeight();
/* 491:621 */         if (yt == this.ycount - 1) {
/* 492:622 */           xp += current.getWidth();
/* 493:    */         }
/* 494:    */       }
/* 495:625 */       if (foundStart)
/* 496:    */       {
/* 497:626 */         startx++;
/* 498:627 */         image.xcount += 1;
/* 499:    */       }
/* 500:    */     }
/* 501:631 */     return image;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public Texture getTexture()
/* 505:    */   {
/* 506:640 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/* 507:    */   }
/* 508:    */   
/* 509:    */   protected void initImpl()
/* 510:    */   {
/* 511:647 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/* 512:    */   }
/* 513:    */   
/* 514:    */   protected void reinit()
/* 515:    */   {
/* 516:654 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/* 517:    */   }
/* 518:    */   
/* 519:    */   public void setTexture(Texture texture)
/* 520:    */   {
/* 521:663 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/* 522:    */   }
/* 523:    */   
/* 524:    */   public Image getSubImage(int offsetX, int offsetY)
/* 525:    */   {
/* 526:675 */     return this.images[offsetX][offsetY];
/* 527:    */   }
/* 528:    */   
/* 529:    */   public int getHorizontalImageCount()
/* 530:    */   {
/* 531:684 */     return this.xcount;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public int getVerticalImageCount()
/* 535:    */   {
/* 536:693 */     return this.ycount;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public String toString()
/* 540:    */   {
/* 541:700 */     return "[BIG IMAGE]";
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void destroy()
/* 545:    */     throws SlickException
/* 546:    */   {
/* 547:708 */     for (int tx = 0; tx < this.xcount; tx++) {
/* 548:709 */       for (int ty = 0; ty < this.ycount; ty++)
/* 549:    */       {
/* 550:710 */         Image image = this.images[tx][ty];
/* 551:711 */         image.destroy();
/* 552:    */       }
/* 553:    */     }
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter)
/* 557:    */   {
/* 558:721 */     int srcwidth = (int)(srcx2 - srcx);
/* 559:722 */     int srcheight = (int)(srcy2 - srcy);
/* 560:    */     
/* 561:724 */     Image subImage = getSubImage((int)srcx, (int)srcy, srcwidth, srcheight);
/* 562:    */     
/* 563:726 */     int width = (int)(x2 - x);
/* 564:727 */     int height = (int)(y2 - y);
/* 565:    */     
/* 566:729 */     subImage.draw(x, y, width, height, filter);
/* 567:    */   }
/* 568:    */   
/* 569:    */   public void drawCentered(float x, float y)
/* 570:    */   {
/* 571:736 */     throw new UnsupportedOperationException();
/* 572:    */   }
/* 573:    */   
/* 574:    */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter)
/* 575:    */   {
/* 576:744 */     throw new UnsupportedOperationException();
/* 577:    */   }
/* 578:    */   
/* 579:    */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
/* 580:    */   {
/* 581:752 */     throw new UnsupportedOperationException();
/* 582:    */   }
/* 583:    */   
/* 584:    */   public void drawFlash(float x, float y, float width, float height, Color col)
/* 585:    */   {
/* 586:759 */     throw new UnsupportedOperationException();
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void drawSheared(float x, float y, float hshear, float vshear)
/* 590:    */   {
/* 591:766 */     throw new UnsupportedOperationException();
/* 592:    */   }
/* 593:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.BigImage
 * JD-Core Version:    0.7.0.1
 */