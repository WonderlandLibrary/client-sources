/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.newdawn.slick.loading.DeferredResource;
/*     */ import org.newdawn.slick.loading.LoadingList;
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
/*     */ public class DeferredTexture
/*     */   extends TextureImpl
/*     */   implements DeferredResource
/*     */ {
/*     */   private InputStream in;
/*     */   private String resourceName;
/*     */   private boolean flipped;
/*     */   private int filter;
/*     */   private TextureImpl target;
/*     */   private int[] trans;
/*     */   
/*     */   public DeferredTexture(InputStream in, String resourceName, boolean flipped, int filter, int[] trans) {
/*  40 */     this.in = in;
/*  41 */     this.resourceName = resourceName;
/*  42 */     this.flipped = flipped;
/*  43 */     this.filter = filter;
/*  44 */     this.trans = trans;
/*     */     
/*  46 */     LoadingList.get().add(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() throws IOException {
/*  53 */     boolean before = InternalTextureLoader.get().isDeferredLoading();
/*  54 */     InternalTextureLoader.get().setDeferredLoading(false);
/*  55 */     this.target = InternalTextureLoader.get().getTexture(this.in, this.resourceName, this.flipped, this.filter, this.trans);
/*  56 */     InternalTextureLoader.get().setDeferredLoading(before);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTarget() {
/*  63 */     if (this.target == null) {
/*     */       try {
/*  65 */         load();
/*  66 */         LoadingList.get().remove(this);
/*     */         return;
/*  68 */       } catch (IOException e) {
/*  69 */         throw new RuntimeException("Attempt to use deferred texture before loading and resource not found: " + this.resourceName);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() {
/*  78 */     checkTarget();
/*     */     
/*  80 */     this.target.bind();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/*  87 */     checkTarget();
/*     */     
/*  89 */     return this.target.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageHeight() {
/*  96 */     checkTarget();
/*  97 */     return this.target.getImageHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageWidth() {
/* 104 */     checkTarget();
/* 105 */     return this.target.getImageWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureHeight() {
/* 112 */     checkTarget();
/* 113 */     return this.target.getTextureHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureID() {
/* 120 */     checkTarget();
/* 121 */     return this.target.getTextureID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureRef() {
/* 128 */     checkTarget();
/* 129 */     return this.target.getTextureRef();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureWidth() {
/* 136 */     checkTarget();
/* 137 */     return this.target.getTextureWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 144 */     checkTarget();
/* 145 */     return this.target.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 152 */     checkTarget();
/* 153 */     this.target.release();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlpha(boolean alpha) {
/* 160 */     checkTarget();
/* 161 */     this.target.setAlpha(alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/* 168 */     checkTarget();
/* 169 */     this.target.setHeight(height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureHeight(int texHeight) {
/* 176 */     checkTarget();
/* 177 */     this.target.setTextureHeight(texHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureID(int textureID) {
/* 184 */     checkTarget();
/* 185 */     this.target.setTextureID(textureID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureWidth(int texWidth) {
/* 192 */     checkTarget();
/* 193 */     this.target.setTextureWidth(texWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 200 */     checkTarget();
/* 201 */     this.target.setWidth(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getTextureData() {
/* 208 */     checkTarget();
/* 209 */     return this.target.getTextureData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 216 */     return this.resourceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAlpha() {
/* 223 */     checkTarget();
/* 224 */     return this.target.hasAlpha();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureFilter(int textureFilter) {
/* 231 */     checkTarget();
/* 232 */     this.target.setTextureFilter(textureFilter);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\DeferredTexture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */