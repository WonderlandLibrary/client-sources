/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.newdawn.slick.opengl.ImageData;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageBuffer
/*     */   implements ImageData
/*     */ {
/*     */   private int width;
/*     */   private int height;
/*     */   private int texWidth;
/*     */   private int texHeight;
/*     */   private byte[] rawData;
/*     */   
/*     */   public ImageBuffer(int width, int height) {
/*  40 */     this.width = width;
/*  41 */     this.height = height;
/*     */     
/*  43 */     this.texWidth = get2Fold(width);
/*  44 */     this.texHeight = get2Fold(height);
/*     */     
/*  46 */     this.rawData = new byte[this.texWidth * this.texHeight * 4];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getRGBA() {
/*  55 */     return this.rawData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  62 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  69 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/*  76 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexWidth() {
/*  83 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  90 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/*  97 */     ByteBuffer scratch = BufferUtils.createByteBuffer(this.rawData.length);
/*  98 */     scratch.put(this.rawData);
/*  99 */     scratch.flip();
/*     */     
/* 101 */     return scratch;
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
/*     */   public void setRGBA(int x, int y, int r, int g, int b, int a) {
/* 115 */     if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
/* 116 */       throw new RuntimeException("Specified location: " + x + "," + y + " outside of image");
/*     */     }
/*     */     
/* 119 */     int ofs = (x + y * this.texWidth) * 4;
/*     */     
/* 121 */     if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
/* 122 */       this.rawData[ofs] = (byte)b;
/* 123 */       this.rawData[ofs + 1] = (byte)g;
/* 124 */       this.rawData[ofs + 2] = (byte)r;
/* 125 */       this.rawData[ofs + 3] = (byte)a;
/*     */     } else {
/* 127 */       this.rawData[ofs] = (byte)r;
/* 128 */       this.rawData[ofs + 1] = (byte)g;
/* 129 */       this.rawData[ofs + 2] = (byte)b;
/* 130 */       this.rawData[ofs + 3] = (byte)a;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage() {
/* 140 */     return new Image(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage(int filter) {
/* 150 */     return new Image(this, filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int get2Fold(int fold) {
/* 160 */     int ret = 2;
/* 161 */     while (ret < fold) {
/* 162 */       ret *= 2;
/*     */     }
/* 164 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\ImageBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */