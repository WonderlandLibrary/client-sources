/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.newdawn.slick.opengl.ImageData;
/*     */ import org.newdawn.slick.opengl.ImageDataFactory;
/*     */ import org.newdawn.slick.opengl.LoadableImageData;
/*     */ import org.newdawn.slick.opengl.Texture;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ import org.newdawn.slick.util.OperationNotSupportedException;
/*     */ import org.newdawn.slick.util.ResourceLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BigImage
/*     */   extends Image
/*     */ {
/*  34 */   protected static SGL GL = Renderer.get();
/*     */   
/*     */   private static Image lastBind;
/*     */   private Image[][] images;
/*     */   private int xcount;
/*     */   private int ycount;
/*     */   private int realWidth;
/*     */   private int realHeight;
/*     */   
/*     */   public static final int getMaxSingleImageSize() {
/*  44 */     IntBuffer buffer = BufferUtils.createIntBuffer(16);
/*  45 */     GL.glGetInteger(3379, buffer);
/*     */     
/*  47 */     return buffer.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BigImage() {
/*  68 */     this.inited = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigImage(String ref) throws SlickException {
/*  78 */     this(ref, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigImage(String ref, int filter) throws SlickException {
/*  90 */     build(ref, filter, getMaxSingleImageSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigImage(String ref, int filter, int tileSize) throws SlickException {
/* 102 */     build(ref, filter, tileSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigImage(LoadableImageData data, ByteBuffer imageBuffer, int filter) {
/* 113 */     build(data, imageBuffer, filter, getMaxSingleImageSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigImage(LoadableImageData data, ByteBuffer imageBuffer, int filter, int tileSize) {
/* 125 */     build(data, imageBuffer, filter, tileSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getTile(int x, int y) {
/* 136 */     return this.images[x][y];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void build(String ref, int filter, int tileSize) throws SlickException {
/*     */     try {
/* 149 */       LoadableImageData data = ImageDataFactory.getImageDataFor(ref);
/* 150 */       ByteBuffer imageBuffer = data.loadImage(ResourceLoader.getResourceAsStream(ref), false, null);
/* 151 */       build(data, imageBuffer, filter, tileSize);
/* 152 */     } catch (IOException e) {
/* 153 */       throw new SlickException("Failed to load: " + ref, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void build(final LoadableImageData data, final ByteBuffer imageBuffer, int filter, int tileSize) {
/* 166 */     final int dataWidth = data.getTexWidth();
/* 167 */     final int dataHeight = data.getTexHeight();
/*     */     
/* 169 */     this.realWidth = this.width = data.getWidth();
/* 170 */     this.realHeight = this.height = data.getHeight();
/*     */     
/* 172 */     if (dataWidth <= tileSize && dataHeight <= tileSize) {
/* 173 */       this.images = new Image[1][1];
/* 174 */       ImageData tempData = new ImageData() {
/*     */           public int getDepth() {
/* 176 */             return data.getDepth();
/*     */           }
/*     */           
/*     */           public int getHeight() {
/* 180 */             return dataHeight;
/*     */           }
/*     */           
/*     */           public ByteBuffer getImageBufferData() {
/* 184 */             return imageBuffer;
/*     */           }
/*     */           
/*     */           public int getTexHeight() {
/* 188 */             return dataHeight;
/*     */           }
/*     */           
/*     */           public int getTexWidth() {
/* 192 */             return dataWidth;
/*     */           }
/*     */           
/*     */           public int getWidth() {
/* 196 */             return dataWidth;
/*     */           }
/*     */         };
/* 199 */       this.images[0][0] = new Image(tempData, filter);
/* 200 */       this.xcount = 1;
/* 201 */       this.ycount = 1;
/* 202 */       this.inited = true;
/*     */       
/*     */       return;
/*     */     } 
/* 206 */     this.xcount = (this.realWidth - 1) / tileSize + 1;
/* 207 */     this.ycount = (this.realHeight - 1) / tileSize + 1;
/*     */     
/* 209 */     this.images = new Image[this.xcount][this.ycount];
/* 210 */     int components = data.getDepth() / 8;
/*     */     
/* 212 */     for (int x = 0; x < this.xcount; x++) {
/* 213 */       for (int y = 0; y < this.ycount; y++) {
/* 214 */         int finalX = (x + 1) * tileSize;
/* 215 */         int finalY = (y + 1) * tileSize;
/* 216 */         final int imageWidth = Math.min(this.realWidth - x * tileSize, tileSize);
/* 217 */         final int imageHeight = Math.min(this.realHeight - y * tileSize, tileSize);
/*     */         
/* 219 */         final int xSize = tileSize;
/* 220 */         final int ySize = tileSize;
/*     */         
/* 222 */         final ByteBuffer subBuffer = BufferUtils.createByteBuffer(tileSize * tileSize * components);
/* 223 */         int xo = x * tileSize * components;
/*     */         
/* 225 */         byte[] byteData = new byte[xSize * components];
/* 226 */         for (int i = 0; i < ySize; i++) {
/* 227 */           int yo = (y * tileSize + i) * dataWidth * components;
/* 228 */           imageBuffer.position(yo + xo);
/*     */           
/* 230 */           imageBuffer.get(byteData, 0, xSize * components);
/* 231 */           subBuffer.put(byteData);
/*     */         } 
/*     */         
/* 234 */         subBuffer.flip();
/* 235 */         ImageData imgData = new ImageData() {
/*     */             public int getDepth() {
/* 237 */               return data.getDepth();
/*     */             }
/*     */             
/*     */             public int getHeight() {
/* 241 */               return imageHeight;
/*     */             }
/*     */             
/*     */             public int getWidth() {
/* 245 */               return imageWidth;
/*     */             }
/*     */             
/*     */             public ByteBuffer getImageBufferData() {
/* 249 */               return subBuffer;
/*     */             }
/*     */             
/*     */             public int getTexHeight() {
/* 253 */               return ySize;
/*     */             }
/*     */             
/*     */             public int getTexWidth() {
/* 257 */               return xSize;
/*     */             }
/*     */           };
/* 260 */         this.images[x][y] = new Image(imgData, filter);
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     this.inited = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() {
/* 273 */     throw new OperationNotSupportedException("Can't bind big images yet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image copy() {
/* 282 */     throw new OperationNotSupportedException("Can't copy big images yet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw() {
/* 289 */     draw(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, Color filter) {
/* 296 */     draw(x, y, this.width, this.height, filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float scale, Color filter) {
/* 303 */     draw(x, y, this.width * scale, this.height * scale, filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float width, float height, Color filter) {
/* 310 */     float sx = width / this.realWidth;
/* 311 */     float sy = height / this.realHeight;
/*     */     
/* 313 */     GL.glTranslatef(x, y, 0.0F);
/* 314 */     GL.glScalef(sx, sy, 1.0F);
/*     */     
/* 316 */     float xp = 0.0F;
/* 317 */     float yp = 0.0F;
/*     */     
/* 319 */     for (int tx = 0; tx < this.xcount; tx++) {
/* 320 */       yp = 0.0F;
/* 321 */       for (int ty = 0; ty < this.ycount; ty++) {
/* 322 */         Image image = this.images[tx][ty];
/*     */         
/* 324 */         image.draw(xp, yp, image.getWidth(), image.getHeight(), filter);
/*     */         
/* 326 */         yp += image.getHeight();
/* 327 */         if (ty == this.ycount - 1) {
/* 328 */           xp += image.getWidth();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 334 */     GL.glScalef(1.0F / sx, 1.0F / sy, 1.0F);
/* 335 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) {
/* 342 */     int srcwidth = (int)(srcx2 - srcx);
/* 343 */     int srcheight = (int)(srcy2 - srcy);
/*     */     
/* 345 */     Image subImage = getSubImage((int)srcx, (int)srcy, srcwidth, srcheight);
/*     */     
/* 347 */     int width = (int)(x2 - x);
/* 348 */     int height = (int)(y2 - y);
/*     */     
/* 350 */     subImage.draw(x, y, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float srcx, float srcy, float srcx2, float srcy2) {
/* 357 */     int srcwidth = (int)(srcx2 - srcx);
/* 358 */     int srcheight = (int)(srcy2 - srcy);
/*     */     
/* 360 */     draw(x, y, srcwidth, srcheight, srcx, srcy, srcx2, srcy2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float width, float height) {
/* 367 */     draw(x, y, width, height, Color.white);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float scale) {
/* 374 */     draw(x, y, scale, Color.white);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y) {
/* 381 */     draw(x, y, Color.white);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEmbedded(float x, float y, float width, float height) {
/* 388 */     float sx = width / this.realWidth;
/* 389 */     float sy = height / this.realHeight;
/*     */     
/* 391 */     float xp = 0.0F;
/* 392 */     float yp = 0.0F;
/*     */     
/* 394 */     for (int tx = 0; tx < this.xcount; tx++) {
/* 395 */       yp = 0.0F;
/* 396 */       for (int ty = 0; ty < this.ycount; ty++) {
/* 397 */         Image image = this.images[tx][ty];
/*     */         
/* 399 */         if (lastBind == null || image.getTexture() != lastBind.getTexture()) {
/* 400 */           if (lastBind != null) {
/* 401 */             lastBind.endUse();
/*     */           }
/* 403 */           lastBind = image;
/* 404 */           lastBind.startUse();
/*     */         } 
/* 406 */         image.drawEmbedded(xp + x, yp + y, image.getWidth(), image.getHeight());
/*     */         
/* 408 */         yp += image.getHeight();
/* 409 */         if (ty == this.ycount - 1) {
/* 410 */           xp += image.getWidth();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawFlash(float x, float y, float width, float height) {
/* 421 */     float sx = width / this.realWidth;
/* 422 */     float sy = height / this.realHeight;
/*     */     
/* 424 */     GL.glTranslatef(x, y, 0.0F);
/* 425 */     GL.glScalef(sx, sy, 1.0F);
/*     */     
/* 427 */     float xp = 0.0F;
/* 428 */     float yp = 0.0F;
/*     */     
/* 430 */     for (int tx = 0; tx < this.xcount; tx++) {
/* 431 */       yp = 0.0F;
/* 432 */       for (int ty = 0; ty < this.ycount; ty++) {
/* 433 */         Image image = this.images[tx][ty];
/*     */         
/* 435 */         image.drawFlash(xp, yp, image.getWidth(), image.getHeight());
/*     */         
/* 437 */         yp += image.getHeight();
/* 438 */         if (ty == this.ycount - 1) {
/* 439 */           xp += image.getWidth();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 445 */     GL.glScalef(1.0F / sx, 1.0F / sy, 1.0F);
/* 446 */     GL.glTranslatef(-x, -y, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawFlash(float x, float y) {
/* 453 */     drawFlash(x, y, this.width, this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endUse() {
/* 462 */     if (lastBind != null) {
/* 463 */       lastBind.endUse();
/*     */     }
/* 465 */     lastBind = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startUse() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureInverted() {
/* 482 */     throw new OperationNotSupportedException("Doesn't make sense for tiled operations");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor(int x, int y) {
/* 491 */     throw new OperationNotSupportedException("Can't use big images as buffers");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getFlippedCopy(boolean flipHorizontal, boolean flipVertical) {
/* 498 */     BigImage image = new BigImage();
/*     */     
/* 500 */     image.images = this.images;
/* 501 */     image.xcount = this.xcount;
/* 502 */     image.ycount = this.ycount;
/* 503 */     image.width = this.width;
/* 504 */     image.height = this.height;
/* 505 */     image.realWidth = this.realWidth;
/* 506 */     image.realHeight = this.realHeight;
/*     */     
/* 508 */     if (flipHorizontal) {
/* 509 */       Image[][] images = image.images;
/* 510 */       image.images = new Image[this.xcount][this.ycount];
/*     */       
/* 512 */       for (int x = 0; x < this.xcount; x++) {
/* 513 */         for (int y = 0; y < this.ycount; y++) {
/* 514 */           image.images[x][y] = images[this.xcount - 1 - x][y].getFlippedCopy(true, false);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 519 */     if (flipVertical) {
/* 520 */       Image[][] images = image.images;
/* 521 */       image.images = new Image[this.xcount][this.ycount];
/*     */       
/* 523 */       for (int x = 0; x < this.xcount; x++) {
/* 524 */         for (int y = 0; y < this.ycount; y++) {
/* 525 */           image.images[x][y] = images[x][this.ycount - 1 - y].getFlippedCopy(false, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 530 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Graphics getGraphics() throws SlickException {
/* 539 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getScaledCopy(float scale) {
/* 546 */     return getScaledCopy((int)(scale * this.width), (int)(scale * this.height));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getScaledCopy(int width, int height) {
/* 553 */     BigImage image = new BigImage();
/*     */     
/* 555 */     image.images = this.images;
/* 556 */     image.xcount = this.xcount;
/* 557 */     image.ycount = this.ycount;
/* 558 */     image.width = width;
/* 559 */     image.height = height;
/* 560 */     image.realWidth = this.realWidth;
/* 561 */     image.realHeight = this.realHeight;
/*     */     
/* 563 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getSubImage(int x, int y, int width, int height) {
/* 570 */     BigImage image = new BigImage();
/*     */     
/* 572 */     image.width = width;
/* 573 */     image.height = height;
/* 574 */     image.realWidth = width;
/* 575 */     image.realHeight = height;
/* 576 */     image.images = new Image[this.xcount][this.ycount];
/*     */     
/* 578 */     float xp = 0.0F;
/* 579 */     float yp = 0.0F;
/* 580 */     int x2 = x + width;
/* 581 */     int y2 = y + height;
/*     */     
/* 583 */     int startx = 0;
/* 584 */     int starty = 0;
/* 585 */     boolean foundStart = false;
/*     */     
/* 587 */     for (int xt = 0; xt < this.xcount; xt++) {
/* 588 */       yp = 0.0F;
/* 589 */       starty = 0;
/* 590 */       foundStart = false;
/* 591 */       for (int yt = 0; yt < this.ycount; yt++) {
/* 592 */         Image current = this.images[xt][yt];
/*     */         
/* 594 */         int xp2 = (int)(xp + current.getWidth());
/* 595 */         int yp2 = (int)(yp + current.getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 602 */         int targetX1 = (int)Math.max(x, xp);
/* 603 */         int targetY1 = (int)Math.max(y, yp);
/* 604 */         int targetX2 = Math.min(x2, xp2);
/* 605 */         int targetY2 = Math.min(y2, yp2);
/*     */         
/* 607 */         int targetWidth = targetX2 - targetX1;
/* 608 */         int targetHeight = targetY2 - targetY1;
/*     */         
/* 610 */         if (targetWidth > 0 && targetHeight > 0) {
/* 611 */           Image subImage = current.getSubImage((int)(targetX1 - xp), (int)(targetY1 - yp), targetX2 - targetX1, targetY2 - targetY1);
/*     */ 
/*     */           
/* 614 */           foundStart = true;
/* 615 */           image.images[startx][starty] = subImage;
/* 616 */           starty++;
/* 617 */           image.ycount = Math.max(image.ycount, starty);
/*     */         } 
/*     */         
/* 620 */         yp += current.getHeight();
/* 621 */         if (yt == this.ycount - 1) {
/* 622 */           xp += current.getWidth();
/*     */         }
/*     */       } 
/* 625 */       if (foundStart) {
/* 626 */         startx++;
/* 627 */         image.xcount++;
/*     */       } 
/*     */     } 
/*     */     
/* 631 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Texture getTexture() {
/* 640 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initImpl() {
/* 647 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void reinit() {
/* 654 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTexture(Texture texture) {
/* 663 */     throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getSubImage(int offsetX, int offsetY) {
/* 675 */     return this.images[offsetX][offsetY];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHorizontalImageCount() {
/* 684 */     return this.xcount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalImageCount() {
/* 693 */     return this.ycount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 700 */     return "[BIG IMAGE]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() throws SlickException {
/* 708 */     for (int tx = 0; tx < this.xcount; tx++) {
/* 709 */       for (int ty = 0; ty < this.ycount; ty++) {
/* 710 */         Image image = this.images[tx][ty];
/* 711 */         image.destroy();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter) {
/* 721 */     int srcwidth = (int)(srcx2 - srcx);
/* 722 */     int srcheight = (int)(srcy2 - srcy);
/*     */     
/* 724 */     Image subImage = getSubImage((int)srcx, (int)srcy, srcwidth, srcheight);
/*     */     
/* 726 */     int width = (int)(x2 - x);
/* 727 */     int height = (int)(y2 - y);
/*     */     
/* 729 */     subImage.draw(x, y, width, height, filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCentered(float x, float y) {
/* 736 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter) {
/* 744 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) {
/* 752 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawFlash(float x, float y, float width, float height, Color col) {
/* 759 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawSheared(float x, float y, float hshear, float vshear) {
/* 766 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\BigImage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */