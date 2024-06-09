/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ import org.newdawn.slick.util.Log;
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
/*     */ public class TextureImpl
/*     */   implements Texture
/*     */ {
/*  27 */   protected static SGL GL = Renderer.get();
/*     */   
/*     */   static Texture lastBind;
/*     */   
/*     */   private int target;
/*     */   private int textureID;
/*     */   private int height;
/*     */   private int width;
/*     */   private int texWidth;
/*     */   
/*     */   public static Texture getLastBind() {
/*  38 */     return lastBind;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int texHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float widthRatio;
/*     */ 
/*     */ 
/*     */   
/*     */   private float heightRatio;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean alpha;
/*     */ 
/*     */ 
/*     */   
/*     */   private String ref;
/*     */ 
/*     */ 
/*     */   
/*     */   private String cacheName;
/*     */ 
/*     */ 
/*     */   
/*     */   private ReloadData reloadData;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TextureImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureImpl(String ref, int target, int textureID) {
/*  81 */     this.target = target;
/*  82 */     this.ref = ref;
/*  83 */     this.textureID = textureID;
/*  84 */     lastBind = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCacheName(String cacheName) {
/*  93 */     this.cacheName = cacheName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAlpha() {
/* 100 */     return this.alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureRef() {
/* 107 */     return this.ref;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlpha(boolean alpha) {
/* 116 */     this.alpha = alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindNone() {
/* 123 */     lastBind = null;
/* 124 */     GL.glDisable(3553);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unbind() {
/* 133 */     lastBind = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() {
/* 140 */     if (lastBind != this) {
/* 141 */       lastBind = this;
/* 142 */       GL.glEnable(3553);
/* 143 */       GL.glBindTexture(this.target, this.textureID);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/* 153 */     this.height = height;
/* 154 */     setHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 163 */     this.width = width;
/* 164 */     setWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageHeight() {
/* 171 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageWidth() {
/* 178 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 185 */     return this.heightRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 192 */     return this.widthRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureHeight() {
/* 199 */     return this.texHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureWidth() {
/* 206 */     return this.texWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureHeight(int texHeight) {
/* 215 */     this.texHeight = texHeight;
/* 216 */     setHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureWidth(int texWidth) {
/* 225 */     this.texWidth = texWidth;
/* 226 */     setWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setHeight() {
/* 234 */     if (this.texHeight != 0) {
/* 235 */       this.heightRatio = this.height / this.texHeight;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setWidth() {
/* 244 */     if (this.texWidth != 0) {
/* 245 */       this.widthRatio = this.width / this.texWidth;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 253 */     IntBuffer texBuf = createIntBuffer(1);
/* 254 */     texBuf.put(this.textureID);
/* 255 */     texBuf.flip();
/*     */     
/* 257 */     GL.glDeleteTextures(texBuf);
/*     */     
/* 259 */     if (lastBind == this) {
/* 260 */       bindNone();
/*     */     }
/*     */     
/* 263 */     if (this.cacheName != null) {
/* 264 */       InternalTextureLoader.get().clear(this.cacheName);
/*     */     } else {
/* 266 */       InternalTextureLoader.get().clear(this.ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureID() {
/* 274 */     return this.textureID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureID(int textureID) {
/* 283 */     this.textureID = textureID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IntBuffer createIntBuffer(int size) {
/* 294 */     ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
/* 295 */     temp.order(ByteOrder.nativeOrder());
/*     */     
/* 297 */     return temp.asIntBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getTextureData() {
/* 304 */     ByteBuffer buffer = BufferUtils.createByteBuffer((hasAlpha() ? 4 : 3) * this.texWidth * this.texHeight);
/* 305 */     bind();
/* 306 */     GL.glGetTexImage(3553, 0, hasAlpha() ? 6408 : 6407, 5121, buffer);
/*     */     
/* 308 */     byte[] data = new byte[buffer.limit()];
/* 309 */     buffer.get(data);
/* 310 */     buffer.clear();
/*     */     
/* 312 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextureFilter(int textureFilter) {
/* 319 */     bind();
/* 320 */     GL.glTexParameteri(this.target, 10241, textureFilter);
/* 321 */     GL.glTexParameteri(this.target, 10240, textureFilter);
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
/*     */   public void setTextureData(int srcPixelFormat, int componentCount, int minFilter, int magFilter, ByteBuffer textureBuffer) {
/* 335 */     this.reloadData = new ReloadData();
/* 336 */     this.reloadData.srcPixelFormat = srcPixelFormat;
/* 337 */     this.reloadData.componentCount = componentCount;
/* 338 */     this.reloadData.minFilter = minFilter;
/* 339 */     this.reloadData.magFilter = magFilter;
/* 340 */     this.reloadData.textureBuffer = textureBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reload() {
/* 347 */     if (this.reloadData != null) {
/* 348 */       this.textureID = this.reloadData.reload();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class ReloadData
/*     */   {
/*     */     private int srcPixelFormat;
/*     */ 
/*     */     
/*     */     private int componentCount;
/*     */ 
/*     */     
/*     */     private int minFilter;
/*     */     
/*     */     private int magFilter;
/*     */     
/*     */     private ByteBuffer textureBuffer;
/*     */ 
/*     */     
/*     */     private ReloadData() {}
/*     */ 
/*     */     
/*     */     public int reload() {
/* 373 */       Log.error("Reloading texture: " + TextureImpl.this.ref);
/* 374 */       return InternalTextureLoader.get().reload(TextureImpl.this, this.srcPixelFormat, this.componentCount, this.minFilter, this.magFilter, this.textureBuffer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\TextureImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */