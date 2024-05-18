/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.lwjgl.BufferUtils;
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
/*     */ public class PNGImageData
/*     */   implements LoadableImageData
/*     */ {
/*     */   private int width;
/*     */   private int height;
/*     */   private int texHeight;
/*     */   private int texWidth;
/*     */   private PNGDecoder decoder;
/*     */   private int bitDepth;
/*     */   private ByteBuffer scratch;
/*     */   
/*     */   public int getDepth() {
/*  34 */     return this.bitDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/*  41 */     return this.scratch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/*  48 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexWidth() {
/*  55 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis) throws IOException {
/*  62 */     return loadImage(fis, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
/*  69 */     return loadImage(fis, flipped, false, transparent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
/*  76 */     if (transparent != null) {
/*  77 */       forceAlpha = true;
/*  78 */       throw new IOException("Transparent color not support in custom PNG Decoder");
/*     */     } 
/*     */     
/*  81 */     PNGDecoder decoder = new PNGDecoder(fis);
/*     */     
/*  83 */     if (!decoder.isRGB()) {
/*  84 */       throw new IOException("Only RGB formatted images are supported by the PNGLoader");
/*     */     }
/*     */     
/*  87 */     this.width = decoder.getWidth();
/*  88 */     this.height = decoder.getHeight();
/*  89 */     this.texWidth = get2Fold(this.width);
/*  90 */     this.texHeight = get2Fold(this.height);
/*     */     
/*  92 */     int perPixel = decoder.hasAlpha() ? 4 : 3;
/*  93 */     this.bitDepth = decoder.hasAlpha() ? 32 : 24;
/*     */ 
/*     */     
/*  96 */     this.scratch = BufferUtils.createByteBuffer(this.texWidth * this.texHeight * perPixel);
/*  97 */     decoder.decode(this.scratch, this.texWidth * perPixel, (perPixel == 4) ? PNGDecoder.RGBA : PNGDecoder.RGB);
/*     */     
/*  99 */     if (this.height < this.texHeight - 1) {
/* 100 */       int topOffset = (this.texHeight - 1) * this.texWidth * perPixel;
/* 101 */       int bottomOffset = (this.height - 1) * this.texWidth * perPixel;
/* 102 */       for (int x = 0; x < this.texWidth; x++) {
/* 103 */         for (int i = 0; i < perPixel; i++) {
/* 104 */           this.scratch.put(topOffset + x + i, this.scratch.get(x + i));
/* 105 */           this.scratch.put(bottomOffset + this.texWidth * perPixel + x + i, this.scratch.get(bottomOffset + x + i));
/*     */         } 
/*     */       } 
/*     */     } 
/* 109 */     if (this.width < this.texWidth - 1) {
/* 110 */       for (int y = 0; y < this.texHeight; y++) {
/* 111 */         for (int i = 0; i < perPixel; i++) {
/* 112 */           this.scratch.put((y + 1) * this.texWidth * perPixel - perPixel + i, this.scratch.get(y * this.texWidth * perPixel + i));
/* 113 */           this.scratch.put(y * this.texWidth * perPixel + this.width * perPixel + i, this.scratch.get(y * this.texWidth * perPixel + (this.width - 1) * perPixel + i));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 118 */     if (!decoder.hasAlpha() && forceAlpha) {
/* 119 */       ByteBuffer temp = BufferUtils.createByteBuffer(this.texWidth * this.texHeight * 4);
/* 120 */       for (int x = 0; x < this.texWidth; x++) {
/* 121 */         for (int y = 0; y < this.texHeight; y++) {
/* 122 */           int srcOffset = y * 3 + x * this.texHeight * 3;
/* 123 */           int dstOffset = y * 4 + x * this.texHeight * 4;
/*     */           
/* 125 */           temp.put(dstOffset, this.scratch.get(srcOffset));
/* 126 */           temp.put(dstOffset + 1, this.scratch.get(srcOffset + 1));
/* 127 */           temp.put(dstOffset + 2, this.scratch.get(srcOffset + 2));
/* 128 */           if (x < getHeight() && y < getWidth()) {
/* 129 */             temp.put(dstOffset + 3, (byte)-1);
/*     */           } else {
/* 131 */             temp.put(dstOffset + 3, (byte)0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 136 */       this.bitDepth = 32;
/* 137 */       this.scratch = temp;
/*     */     } 
/*     */     
/* 140 */     if (transparent != null) {
/* 141 */       for (int i = 0; i < this.texWidth * this.texHeight * 4; i += 4) {
/* 142 */         boolean match = true;
/* 143 */         for (int c = 0; c < 3; c++) {
/* 144 */           if (toInt(this.scratch.get(i + c)) != transparent[c]) {
/* 145 */             match = false;
/*     */           }
/*     */         } 
/*     */         
/* 149 */         if (match) {
/* 150 */           this.scratch.put(i + 3, (byte)0);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 155 */     this.scratch.position(0);
/*     */     
/* 157 */     return this.scratch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int toInt(byte b) {
/* 167 */     if (b < 0) {
/* 168 */       return 256 + b;
/*     */     }
/*     */     
/* 171 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int get2Fold(int fold) {
/* 181 */     int ret = 2;
/* 182 */     while (ret < fold) {
/* 183 */       ret *= 2;
/*     */     }
/* 185 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureEdging(boolean edging) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 195 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 199 */     return this.height;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\PNGImageData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */