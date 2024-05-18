/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
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
/*     */ public class InternalTextureLoader
/*     */ {
/*  30 */   protected static SGL GL = Renderer.get();
/*     */   
/*  32 */   private static final InternalTextureLoader loader = new InternalTextureLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InternalTextureLoader get() {
/*  40 */     return loader;
/*     */   }
/*     */ 
/*     */   
/*  44 */   private HashMap texturesLinear = new HashMap<Object, Object>();
/*     */   
/*  46 */   private HashMap texturesNearest = new HashMap<Object, Object>();
/*     */   
/*  48 */   private int dstPixelFormat = 6408;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deferred;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean holdTextureData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHoldTextureData(boolean holdTextureData) {
/*  67 */     this.holdTextureData = holdTextureData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeferredLoading(boolean deferred) {
/*  77 */     this.deferred = deferred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeferredLoading() {
/*  86 */     return this.deferred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear(String name) {
/*  95 */     this.texturesLinear.remove(name);
/*  96 */     this.texturesNearest.remove(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 103 */     this.texturesLinear.clear();
/* 104 */     this.texturesNearest.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set16BitMode() {
/* 111 */     this.dstPixelFormat = 32859;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int createTextureID() {
/* 121 */     IntBuffer tmp = createIntBuffer(1);
/* 122 */     GL.glGenTextures(tmp);
/* 123 */     return tmp.get(0);
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
/*     */   public Texture getTexture(File source, boolean flipped, int filter) throws IOException {
/* 136 */     String resourceName = source.getAbsolutePath();
/* 137 */     InputStream in = new FileInputStream(source);
/*     */     
/* 139 */     return getTexture(in, resourceName, flipped, filter, null);
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
/*     */   public Texture getTexture(File source, boolean flipped, int filter, int[] transparent) throws IOException {
/* 153 */     String resourceName = source.getAbsolutePath();
/* 154 */     InputStream in = new FileInputStream(source);
/*     */     
/* 156 */     return getTexture(in, resourceName, flipped, filter, transparent);
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
/*     */   public Texture getTexture(String resourceName, boolean flipped, int filter) throws IOException {
/* 169 */     InputStream in = ResourceLoader.getResourceAsStream(resourceName);
/*     */     
/* 171 */     return getTexture(in, resourceName, flipped, filter, null);
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
/*     */   public Texture getTexture(String resourceName, boolean flipped, int filter, int[] transparent) throws IOException {
/* 185 */     InputStream in = ResourceLoader.getResourceAsStream(resourceName);
/*     */     
/* 187 */     return getTexture(in, resourceName, flipped, filter, transparent);
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
/*     */   public Texture getTexture(InputStream in, String resourceName, boolean flipped, int filter) throws IOException {
/* 200 */     return getTexture(in, resourceName, flipped, filter, null);
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
/*     */   public TextureImpl getTexture(InputStream in, String resourceName, boolean flipped, int filter, int[] transparent) throws IOException {
/* 215 */     if (this.deferred) {
/* 216 */       return new DeferredTexture(in, resourceName, flipped, filter, transparent);
/*     */     }
/*     */     
/* 219 */     HashMap<String, TextureImpl> hash = this.texturesLinear;
/* 220 */     if (filter == 9728) {
/* 221 */       hash = this.texturesNearest;
/*     */     }
/*     */     
/* 224 */     String resName = resourceName;
/* 225 */     if (transparent != null) {
/* 226 */       resName = resName + ":" + transparent[0] + ":" + transparent[1] + ":" + transparent[2];
/*     */     }
/* 228 */     resName = resName + ":" + flipped;
/*     */     
/* 230 */     if (this.holdTextureData) {
/* 231 */       TextureImpl textureImpl = (TextureImpl)hash.get(resName);
/* 232 */       if (textureImpl != null) {
/* 233 */         return textureImpl;
/*     */       }
/*     */     } else {
/* 236 */       SoftReference<TextureImpl> ref = (SoftReference)hash.get(resName);
/* 237 */       if (ref != null) {
/* 238 */         TextureImpl textureImpl = ref.get();
/* 239 */         if (textureImpl != null) {
/* 240 */           return textureImpl;
/*     */         }
/* 242 */         hash.remove(resName);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 249 */       GL.glGetError();
/* 250 */     } catch (NullPointerException e) {
/* 251 */       throw new RuntimeException("Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
/*     */     } 
/*     */     
/* 254 */     TextureImpl tex = getTexture(in, resourceName, 3553, filter, filter, flipped, transparent);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     tex.setCacheName(resName);
/* 260 */     if (this.holdTextureData) {
/* 261 */       hash.put(resName, tex);
/*     */     } else {
/* 263 */       hash.put(resName, (TextureImpl)new SoftReference<TextureImpl>(tex));
/*     */     } 
/*     */     
/* 266 */     return tex;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TextureImpl getTexture(InputStream in, String resourceName, int target, int magFilter, int minFilter, boolean flipped, int[] transparent) throws IOException {
/* 291 */     LoadableImageData imageData = ImageDataFactory.getImageDataFor(resourceName);
/* 292 */     ByteBuffer textureBuffer = imageData.loadImage(new BufferedInputStream(in), flipped, transparent);
/*     */     
/* 294 */     int textureID = createTextureID();
/* 295 */     TextureImpl texture = new TextureImpl(resourceName, target, textureID);
/*     */     
/* 297 */     GL.glBindTexture(target, textureID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 306 */     int width = imageData.getWidth();
/* 307 */     int height = imageData.getHeight();
/* 308 */     boolean hasAlpha = (imageData.getDepth() == 32);
/*     */     
/* 310 */     texture.setTextureWidth(imageData.getTexWidth());
/* 311 */     texture.setTextureHeight(imageData.getTexHeight());
/*     */     
/* 313 */     int texWidth = texture.getTextureWidth();
/* 314 */     int texHeight = texture.getTextureHeight();
/*     */     
/* 316 */     IntBuffer temp = BufferUtils.createIntBuffer(16);
/* 317 */     GL.glGetInteger(3379, temp);
/* 318 */     int max = temp.get(0);
/* 319 */     if (texWidth > max || texHeight > max) {
/* 320 */       throw new IOException("Attempt to allocate a texture to big for the current hardware");
/*     */     }
/*     */     
/* 323 */     int srcPixelFormat = hasAlpha ? 6408 : 6407;
/* 324 */     int componentCount = hasAlpha ? 4 : 3;
/*     */     
/* 326 */     texture.setWidth(width);
/* 327 */     texture.setHeight(height);
/* 328 */     texture.setAlpha(hasAlpha);
/*     */     
/* 330 */     if (this.holdTextureData) {
/* 331 */       texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
/*     */     }
/*     */     
/* 334 */     GL.glTexParameteri(target, 10241, minFilter);
/* 335 */     GL.glTexParameteri(target, 10240, magFilter);
/*     */ 
/*     */     
/* 338 */     GL.glTexImage2D(target, 0, this.dstPixelFormat, get2Fold(width), get2Fold(height), 0, srcPixelFormat, 5121, textureBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 348 */     return texture;
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
/*     */   public Texture createTexture(int width, int height) throws IOException {
/* 360 */     return createTexture(width, height, 9728);
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
/*     */   public Texture createTexture(int width, int height, int filter) throws IOException {
/* 372 */     ImageData ds = new EmptyImageData(width, height);
/*     */     
/* 374 */     return getTexture(ds, filter);
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
/*     */   public Texture getTexture(ImageData dataSource, int filter) throws IOException {
/* 387 */     int target = 3553;
/*     */ 
/*     */     
/* 390 */     ByteBuffer textureBuffer = dataSource.getImageBufferData();
/*     */ 
/*     */     
/* 393 */     int textureID = createTextureID();
/* 394 */     TextureImpl texture = new TextureImpl("generated:" + dataSource, target, textureID);
/*     */     
/* 396 */     int minFilter = filter;
/* 397 */     int magFilter = filter;
/* 398 */     boolean flipped = false;
/*     */ 
/*     */     
/* 401 */     GL.glBindTexture(target, textureID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     int width = dataSource.getWidth();
/* 411 */     int height = dataSource.getHeight();
/* 412 */     boolean hasAlpha = (dataSource.getDepth() == 32);
/*     */     
/* 414 */     texture.setTextureWidth(dataSource.getTexWidth());
/* 415 */     texture.setTextureHeight(dataSource.getTexHeight());
/*     */     
/* 417 */     int texWidth = texture.getTextureWidth();
/* 418 */     int texHeight = texture.getTextureHeight();
/*     */     
/* 420 */     int srcPixelFormat = hasAlpha ? 6408 : 6407;
/* 421 */     int componentCount = hasAlpha ? 4 : 3;
/*     */     
/* 423 */     texture.setWidth(width);
/* 424 */     texture.setHeight(height);
/* 425 */     texture.setAlpha(hasAlpha);
/*     */     
/* 427 */     IntBuffer temp = BufferUtils.createIntBuffer(16);
/* 428 */     GL.glGetInteger(3379, temp);
/* 429 */     int max = temp.get(0);
/* 430 */     if (texWidth > max || texHeight > max) {
/* 431 */       throw new IOException("Attempt to allocate a texture to big for the current hardware");
/*     */     }
/*     */     
/* 434 */     if (this.holdTextureData) {
/* 435 */       texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
/*     */     }
/*     */     
/* 438 */     GL.glTexParameteri(target, 10241, minFilter);
/* 439 */     GL.glTexParameteri(target, 10240, magFilter);
/*     */ 
/*     */     
/* 442 */     GL.glTexImage2D(target, 0, this.dstPixelFormat, get2Fold(width), get2Fold(height), 0, srcPixelFormat, 5121, textureBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 452 */     return texture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int get2Fold(int fold) {
/* 462 */     int ret = 2;
/* 463 */     while (ret < fold) {
/* 464 */       ret *= 2;
/*     */     }
/* 466 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntBuffer createIntBuffer(int size) {
/* 477 */     ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
/* 478 */     temp.order(ByteOrder.nativeOrder());
/*     */     
/* 480 */     return temp.asIntBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reload() {
/* 487 */     Iterator<TextureImpl> texs = this.texturesLinear.values().iterator();
/* 488 */     while (texs.hasNext()) {
/* 489 */       ((TextureImpl)texs.next()).reload();
/*     */     }
/* 491 */     texs = this.texturesNearest.values().iterator();
/* 492 */     while (texs.hasNext()) {
/* 493 */       ((TextureImpl)texs.next()).reload();
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
/*     */   public int reload(TextureImpl texture, int srcPixelFormat, int componentCount, int minFilter, int magFilter, ByteBuffer textureBuffer) {
/* 510 */     int target = 3553;
/* 511 */     int textureID = createTextureID();
/* 512 */     GL.glBindTexture(target, textureID);
/*     */     
/* 514 */     GL.glTexParameteri(target, 10241, minFilter);
/* 515 */     GL.glTexParameteri(target, 10240, magFilter);
/*     */ 
/*     */     
/* 518 */     GL.glTexImage2D(target, 0, this.dstPixelFormat, texture.getTextureWidth(), texture.getTextureHeight(), 0, srcPixelFormat, 5121, textureBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 528 */     return textureID;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\InternalTextureLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */