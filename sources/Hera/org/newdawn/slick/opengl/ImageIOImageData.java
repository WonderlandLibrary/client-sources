/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Hashtable;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageIOImageData
/*     */   implements LoadableImageData
/*     */ {
/*  30 */   private static final ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 8 }, true, false, 3, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 0 }, false, false, 1, 0);
/*     */ 
/*     */ 
/*     */   
/*     */   private int depth;
/*     */ 
/*     */ 
/*     */   
/*     */   private int height;
/*     */ 
/*     */   
/*     */   private int width;
/*     */ 
/*     */   
/*     */   private int texWidth;
/*     */ 
/*     */   
/*     */   private int texHeight;
/*     */ 
/*     */   
/*     */   private boolean edging = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  64 */     return this.depth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  71 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/*  78 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexWidth() {
/*  85 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  92 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis) throws IOException {
/*  99 */     return loadImage(fis, true, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
/* 106 */     return loadImage(fis, flipped, false, transparent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
/* 113 */     if (transparent != null) {
/* 114 */       forceAlpha = true;
/*     */     }
/*     */     
/* 117 */     BufferedImage bufferedImage = ImageIO.read(fis);
/* 118 */     return imageToByteBuffer(bufferedImage, flipped, forceAlpha, transparent);
/*     */   }
/*     */   public ByteBuffer imageToByteBuffer(BufferedImage image, boolean flipped, boolean forceAlpha, int[] transparent) {
/*     */     BufferedImage texImage;
/* 122 */     ByteBuffer imageBuffer = null;
/*     */ 
/*     */ 
/*     */     
/* 126 */     int texWidth = 2;
/* 127 */     int texHeight = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     while (texWidth < image.getWidth()) {
/* 133 */       texWidth *= 2;
/*     */     }
/* 135 */     while (texHeight < image.getHeight()) {
/* 136 */       texHeight *= 2;
/*     */     }
/*     */     
/* 139 */     this.width = image.getWidth();
/* 140 */     this.height = image.getHeight();
/* 141 */     this.texHeight = texHeight;
/* 142 */     this.texWidth = texWidth;
/*     */ 
/*     */ 
/*     */     
/* 146 */     boolean useAlpha = (image.getColorModel().hasAlpha() || forceAlpha);
/*     */     
/* 148 */     if (useAlpha) {
/* 149 */       this.depth = 32;
/* 150 */       WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 4, null);
/* 151 */       texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable<Object, Object>());
/*     */     } else {
/* 153 */       this.depth = 24;
/* 154 */       WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 3, null);
/* 155 */       texImage = new BufferedImage(glColorModel, raster, false, new Hashtable<Object, Object>());
/*     */     } 
/*     */ 
/*     */     
/* 159 */     Graphics2D g = (Graphics2D)texImage.getGraphics();
/*     */ 
/*     */     
/* 162 */     if (useAlpha) {
/* 163 */       g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.0F));
/* 164 */       g.fillRect(0, 0, texWidth, texHeight);
/*     */     } 
/*     */     
/* 167 */     if (flipped) {
/* 168 */       g.scale(1.0D, -1.0D);
/* 169 */       g.drawImage(image, 0, -this.height, (ImageObserver)null);
/*     */     } else {
/* 171 */       g.drawImage(image, 0, 0, (ImageObserver)null);
/*     */     } 
/*     */     
/* 174 */     if (this.edging) {
/* 175 */       if (this.height < texHeight - 1) {
/* 176 */         copyArea(texImage, 0, 0, this.width, 1, 0, texHeight - 1);
/* 177 */         copyArea(texImage, 0, this.height - 1, this.width, 1, 0, 1);
/*     */       } 
/* 179 */       if (this.width < texWidth - 1) {
/* 180 */         copyArea(texImage, 0, 0, 1, this.height, texWidth - 1, 0);
/* 181 */         copyArea(texImage, this.width - 1, 0, 1, this.height, 1, 0);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 187 */     byte[] data = ((DataBufferByte)texImage.getRaster().getDataBuffer()).getData();
/*     */     
/* 189 */     if (transparent != null) {
/* 190 */       for (int i = 0; i < data.length; i += 4) {
/* 191 */         boolean match = true;
/* 192 */         for (int c = 0; c < 3; c++) {
/* 193 */           int value = (data[i + c] < 0) ? (256 + data[i + c]) : data[i + c];
/* 194 */           if (value != transparent[c]) {
/* 195 */             match = false;
/*     */           }
/*     */         } 
/*     */         
/* 199 */         if (match) {
/* 200 */           data[i + 3] = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 205 */     imageBuffer = ByteBuffer.allocateDirect(data.length);
/* 206 */     imageBuffer.order(ByteOrder.nativeOrder());
/* 207 */     imageBuffer.put(data, 0, data.length);
/* 208 */     imageBuffer.flip();
/* 209 */     g.dispose();
/*     */     
/* 211 */     return imageBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/* 218 */     throw new RuntimeException("ImageIOImageData doesn't store it's image.");
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
/*     */   private void copyArea(BufferedImage image, int x, int y, int width, int height, int dx, int dy) {
/* 233 */     Graphics2D g = (Graphics2D)image.getGraphics();
/*     */     
/* 235 */     g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, (ImageObserver)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureEdging(boolean edging) {
/* 242 */     this.edging = edging;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\ImageIOImageData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */