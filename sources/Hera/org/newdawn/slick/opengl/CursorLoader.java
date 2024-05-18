/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.input.Cursor;
/*     */ import org.newdawn.slick.util.Log;
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
/*     */ public class CursorLoader
/*     */ {
/*  22 */   private static CursorLoader single = new CursorLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CursorLoader get() {
/*  30 */     return single;
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
/*     */   public Cursor getCursor(String ref, int x, int y) throws IOException, LWJGLException {
/*  50 */     LoadableImageData imageData = null;
/*     */     
/*  52 */     imageData = ImageDataFactory.getImageDataFor(ref);
/*  53 */     imageData.configureEdging(false);
/*     */     
/*  55 */     ByteBuffer buf = imageData.loadImage(ResourceLoader.getResourceAsStream(ref), true, true, (int[])null);
/*  56 */     for (int i = 0; i < buf.limit(); i += 4) {
/*  57 */       byte red = buf.get(i);
/*  58 */       byte green = buf.get(i + 1);
/*  59 */       byte blue = buf.get(i + 2);
/*  60 */       byte alpha = buf.get(i + 3);
/*     */       
/*  62 */       buf.put(i + 2, red);
/*  63 */       buf.put(i + 1, green);
/*  64 */       buf.put(i, blue);
/*  65 */       buf.put(i + 3, alpha);
/*     */     } 
/*     */     
/*     */     try {
/*  69 */       int yspot = imageData.getHeight() - y - 1;
/*  70 */       if (yspot < 0) {
/*  71 */         yspot = 0;
/*     */       }
/*     */       
/*  74 */       return new Cursor(imageData.getTexWidth(), imageData.getTexHeight(), x, yspot, 1, buf.asIntBuffer(), null);
/*  75 */     } catch (Throwable e) {
/*  76 */       Log.info("Chances are you cursor is too small for this platform");
/*  77 */       throw new LWJGLException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cursor getCursor(ByteBuffer buf, int x, int y, int width, int height) throws IOException, LWJGLException {
/*  95 */     for (int i = 0; i < buf.limit(); i += 4) {
/*  96 */       byte red = buf.get(i);
/*  97 */       byte green = buf.get(i + 1);
/*  98 */       byte blue = buf.get(i + 2);
/*  99 */       byte alpha = buf.get(i + 3);
/*     */       
/* 101 */       buf.put(i + 2, red);
/* 102 */       buf.put(i + 1, green);
/* 103 */       buf.put(i, blue);
/* 104 */       buf.put(i + 3, alpha);
/*     */     } 
/*     */     
/*     */     try {
/* 108 */       int yspot = height - y - 1;
/* 109 */       if (yspot < 0) {
/* 110 */         yspot = 0;
/*     */       }
/* 112 */       return new Cursor(width, height, x, yspot, 1, buf.asIntBuffer(), null);
/* 113 */     } catch (Throwable e) {
/* 114 */       Log.info("Chances are you cursor is too small for this platform");
/* 115 */       throw new LWJGLException(e);
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
/*     */ 
/*     */   
/*     */   public Cursor getCursor(ImageData imageData, int x, int y) throws IOException, LWJGLException {
/* 130 */     ByteBuffer buf = imageData.getImageBufferData();
/* 131 */     for (int i = 0; i < buf.limit(); i += 4) {
/* 132 */       byte red = buf.get(i);
/* 133 */       byte green = buf.get(i + 1);
/* 134 */       byte blue = buf.get(i + 2);
/* 135 */       byte alpha = buf.get(i + 3);
/*     */       
/* 137 */       buf.put(i + 2, red);
/* 138 */       buf.put(i + 1, green);
/* 139 */       buf.put(i, blue);
/* 140 */       buf.put(i + 3, alpha);
/*     */     } 
/*     */     
/*     */     try {
/* 144 */       int yspot = imageData.getHeight() - y - 1;
/* 145 */       if (yspot < 0) {
/* 146 */         yspot = 0;
/*     */       }
/* 148 */       return new Cursor(imageData.getTexWidth(), imageData.getTexHeight(), x, yspot, 1, buf.asIntBuffer(), null);
/* 149 */     } catch (Throwable e) {
/* 150 */       Log.info("Chances are you cursor is too small for this platform");
/* 151 */       throw new LWJGLException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cursor getAnimatedCursor(String ref, int x, int y, int width, int height, int[] cursorDelays) throws IOException, LWJGLException {
/* 172 */     IntBuffer cursorDelaysBuffer = ByteBuffer.allocateDirect(cursorDelays.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
/* 173 */     for (int i = 0; i < cursorDelays.length; i++) {
/* 174 */       cursorDelaysBuffer.put(cursorDelays[i]);
/*     */     }
/* 176 */     cursorDelaysBuffer.flip();
/*     */     
/* 178 */     LoadableImageData imageData = new TGAImageData();
/* 179 */     ByteBuffer buf = imageData.loadImage(ResourceLoader.getResourceAsStream(ref), false, (int[])null);
/*     */     
/* 181 */     return new Cursor(width, height, x, y, cursorDelays.length, buf.asIntBuffer(), cursorDelaysBuffer);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\CursorLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */