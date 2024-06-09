/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompositeImageData
/*     */   implements LoadableImageData
/*     */ {
/*  19 */   private ArrayList sources = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   private LoadableImageData picked;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(LoadableImageData data) {
/*  29 */     this.sources.add(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis) throws IOException {
/*  36 */     return loadImage(fis, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
/*  43 */     return loadImage(fis, flipped, false, transparent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer loadImage(InputStream is, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
/*  50 */     CompositeIOException exception = new CompositeIOException();
/*  51 */     ByteBuffer buffer = null;
/*     */     
/*  53 */     BufferedInputStream in = new BufferedInputStream(is, is.available());
/*  54 */     in.mark(is.available());
/*     */ 
/*     */     
/*  57 */     for (int i = 0; i < this.sources.size(); i++) {
/*  58 */       in.reset();
/*     */       try {
/*  60 */         LoadableImageData data = this.sources.get(i);
/*     */         
/*  62 */         buffer = data.loadImage(in, flipped, forceAlpha, transparent);
/*  63 */         this.picked = data;
/*     */         break;
/*  65 */       } catch (Exception e) {
/*  66 */         Log.warn(this.sources.get(i).getClass() + " failed to read the data", e);
/*  67 */         exception.addException(e);
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     if (this.picked == null) {
/*  72 */       throw exception;
/*     */     }
/*     */     
/*  75 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkPicked() {
/*  83 */     if (this.picked == null) {
/*  84 */       throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  92 */     checkPicked();
/*     */     
/*  94 */     return this.picked.getDepth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 101 */     checkPicked();
/*     */     
/* 103 */     return this.picked.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getImageBufferData() {
/* 110 */     checkPicked();
/*     */     
/* 112 */     return this.picked.getImageBufferData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexHeight() {
/* 119 */     checkPicked();
/*     */     
/* 121 */     return this.picked.getTexHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTexWidth() {
/* 128 */     checkPicked();
/*     */     
/* 130 */     return this.picked.getTexWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 137 */     checkPicked();
/*     */     
/* 139 */     return this.picked.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureEdging(boolean edging) {
/* 146 */     for (int i = 0; i < this.sources.size(); i++)
/* 147 */       ((LoadableImageData)this.sources.get(i)).configureEdging(edging); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\CompositeImageData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */