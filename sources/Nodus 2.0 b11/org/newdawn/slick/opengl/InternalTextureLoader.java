/*   1:    */ package org.newdawn.slick.opengl;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.lang.ref.SoftReference;
/*   9:    */ import java.nio.ByteBuffer;
/*  10:    */ import java.nio.ByteOrder;
/*  11:    */ import java.nio.IntBuffer;
/*  12:    */ import java.util.Collection;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import org.lwjgl.BufferUtils;
/*  16:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  17:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  18:    */ import org.newdawn.slick.util.ResourceLoader;
/*  19:    */ 
/*  20:    */ public class InternalTextureLoader
/*  21:    */ {
/*  22: 30 */   protected static SGL GL = ;
/*  23: 32 */   private static final InternalTextureLoader loader = new InternalTextureLoader();
/*  24:    */   
/*  25:    */   public static InternalTextureLoader get()
/*  26:    */   {
/*  27: 40 */     return loader;
/*  28:    */   }
/*  29:    */   
/*  30: 44 */   private HashMap texturesLinear = new HashMap();
/*  31: 46 */   private HashMap texturesNearest = new HashMap();
/*  32: 48 */   private int dstPixelFormat = 6408;
/*  33:    */   private boolean deferred;
/*  34:    */   private boolean holdTextureData;
/*  35:    */   
/*  36:    */   public void setHoldTextureData(boolean holdTextureData)
/*  37:    */   {
/*  38: 67 */     this.holdTextureData = holdTextureData;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setDeferredLoading(boolean deferred)
/*  42:    */   {
/*  43: 77 */     this.deferred = deferred;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isDeferredLoading()
/*  47:    */   {
/*  48: 86 */     return this.deferred;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void clear(String name)
/*  52:    */   {
/*  53: 95 */     this.texturesLinear.remove(name);
/*  54: 96 */     this.texturesNearest.remove(name);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void clear()
/*  58:    */   {
/*  59:103 */     this.texturesLinear.clear();
/*  60:104 */     this.texturesNearest.clear();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void set16BitMode()
/*  64:    */   {
/*  65:111 */     this.dstPixelFormat = 32859;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static int createTextureID()
/*  69:    */   {
/*  70:121 */     IntBuffer tmp = createIntBuffer(1);
/*  71:122 */     GL.glGenTextures(tmp);
/*  72:123 */     return tmp.get(0);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Texture getTexture(File source, boolean flipped, int filter)
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:136 */     String resourceName = source.getAbsolutePath();
/*  79:137 */     InputStream in = new FileInputStream(source);
/*  80:    */     
/*  81:139 */     return getTexture(in, resourceName, flipped, filter, null);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Texture getTexture(File source, boolean flipped, int filter, int[] transparent)
/*  85:    */     throws IOException
/*  86:    */   {
/*  87:153 */     String resourceName = source.getAbsolutePath();
/*  88:154 */     InputStream in = new FileInputStream(source);
/*  89:    */     
/*  90:156 */     return getTexture(in, resourceName, flipped, filter, transparent);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Texture getTexture(String resourceName, boolean flipped, int filter)
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:169 */     InputStream in = ResourceLoader.getResourceAsStream(resourceName);
/*  97:    */     
/*  98:171 */     return getTexture(in, resourceName, flipped, filter, null);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Texture getTexture(String resourceName, boolean flipped, int filter, int[] transparent)
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:185 */     InputStream in = ResourceLoader.getResourceAsStream(resourceName);
/* 105:    */     
/* 106:187 */     return getTexture(in, resourceName, flipped, filter, transparent);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Texture getTexture(InputStream in, String resourceName, boolean flipped, int filter)
/* 110:    */     throws IOException
/* 111:    */   {
/* 112:200 */     return getTexture(in, resourceName, flipped, filter, null);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public TextureImpl getTexture(InputStream in, String resourceName, boolean flipped, int filter, int[] transparent)
/* 116:    */     throws IOException
/* 117:    */   {
/* 118:215 */     if (this.deferred) {
/* 119:216 */       return new DeferredTexture(in, resourceName, flipped, filter, transparent);
/* 120:    */     }
/* 121:219 */     HashMap hash = this.texturesLinear;
/* 122:220 */     if (filter == 9728) {
/* 123:221 */       hash = this.texturesNearest;
/* 124:    */     }
/* 125:224 */     String resName = resourceName;
/* 126:225 */     if (transparent != null) {
/* 127:226 */       resName = resName + ":" + transparent[0] + ":" + transparent[1] + ":" + transparent[2];
/* 128:    */     }
/* 129:228 */     resName = resName + ":" + flipped;
/* 130:230 */     if (this.holdTextureData)
/* 131:    */     {
/* 132:231 */       TextureImpl tex = (TextureImpl)hash.get(resName);
/* 133:232 */       if (tex != null) {
/* 134:233 */         return tex;
/* 135:    */       }
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:236 */       SoftReference ref = (SoftReference)hash.get(resName);
/* 140:237 */       if (ref != null)
/* 141:    */       {
/* 142:238 */         TextureImpl tex = (TextureImpl)ref.get();
/* 143:239 */         if (tex != null) {
/* 144:240 */           return tex;
/* 145:    */         }
/* 146:242 */         hash.remove(resName);
/* 147:    */       }
/* 148:    */     }
/* 149:    */     try
/* 150:    */     {
/* 151:249 */       GL.glGetError();
/* 152:    */     }
/* 153:    */     catch (NullPointerException e)
/* 154:    */     {
/* 155:251 */       throw new RuntimeException("Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
/* 156:    */     }
/* 157:254 */     TextureImpl tex = getTexture(in, resourceName, 
/* 158:255 */       3553, 
/* 159:256 */       filter, 
/* 160:257 */       filter, flipped, transparent);
/* 161:    */     
/* 162:259 */     tex.setCacheName(resName);
/* 163:260 */     if (this.holdTextureData) {
/* 164:261 */       hash.put(resName, tex);
/* 165:    */     } else {
/* 166:263 */       hash.put(resName, new SoftReference(tex));
/* 167:    */     }
/* 168:266 */     return tex;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private TextureImpl getTexture(InputStream in, String resourceName, int target, int magFilter, int minFilter, boolean flipped, int[] transparent)
/* 172:    */     throws IOException
/* 173:    */   {
/* 174:291 */     LoadableImageData imageData = ImageDataFactory.getImageDataFor(resourceName);
/* 175:292 */     ByteBuffer textureBuffer = imageData.loadImage(new BufferedInputStream(in), flipped, transparent);
/* 176:    */     
/* 177:294 */     int textureID = createTextureID();
/* 178:295 */     TextureImpl texture = new TextureImpl(resourceName, target, textureID);
/* 179:    */     
/* 180:297 */     GL.glBindTexture(target, textureID);
/* 181:    */     
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:306 */     int width = imageData.getWidth();
/* 190:307 */     int height = imageData.getHeight();
/* 191:308 */     boolean hasAlpha = imageData.getDepth() == 32;
/* 192:    */     
/* 193:310 */     texture.setTextureWidth(imageData.getTexWidth());
/* 194:311 */     texture.setTextureHeight(imageData.getTexHeight());
/* 195:    */     
/* 196:313 */     int texWidth = texture.getTextureWidth();
/* 197:314 */     int texHeight = texture.getTextureHeight();
/* 198:    */     
/* 199:316 */     IntBuffer temp = BufferUtils.createIntBuffer(16);
/* 200:317 */     GL.glGetInteger(3379, temp);
/* 201:318 */     int max = temp.get(0);
/* 202:319 */     if ((texWidth > max) || (texHeight > max)) {
/* 203:320 */       throw new IOException("Attempt to allocate a texture to big for the current hardware");
/* 204:    */     }
/* 205:323 */     int srcPixelFormat = hasAlpha ? 6408 : 6407;
/* 206:324 */     int componentCount = hasAlpha ? 4 : 3;
/* 207:    */     
/* 208:326 */     texture.setWidth(width);
/* 209:327 */     texture.setHeight(height);
/* 210:328 */     texture.setAlpha(hasAlpha);
/* 211:330 */     if (this.holdTextureData) {
/* 212:331 */       texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
/* 213:    */     }
/* 214:334 */     GL.glTexParameteri(target, 10241, minFilter);
/* 215:335 */     GL.glTexParameteri(target, 10240, magFilter);
/* 216:    */     
/* 217:    */ 
/* 218:338 */     GL.glTexImage2D(target, 
/* 219:339 */       0, 
/* 220:340 */       this.dstPixelFormat, 
/* 221:341 */       get2Fold(width), 
/* 222:342 */       get2Fold(height), 
/* 223:343 */       0, 
/* 224:344 */       srcPixelFormat, 
/* 225:345 */       5121, 
/* 226:346 */       textureBuffer);
/* 227:    */     
/* 228:348 */     return texture;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Texture createTexture(int width, int height)
/* 232:    */     throws IOException
/* 233:    */   {
/* 234:360 */     return createTexture(width, height, 9728);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Texture createTexture(int width, int height, int filter)
/* 238:    */     throws IOException
/* 239:    */   {
/* 240:372 */     ImageData ds = new EmptyImageData(width, height);
/* 241:    */     
/* 242:374 */     return getTexture(ds, filter);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Texture getTexture(ImageData dataSource, int filter)
/* 246:    */     throws IOException
/* 247:    */   {
/* 248:387 */     int target = 3553;
/* 249:    */     
/* 250:    */ 
/* 251:390 */     ByteBuffer textureBuffer = dataSource.getImageBufferData();
/* 252:    */     
/* 253:    */ 
/* 254:393 */     int textureID = createTextureID();
/* 255:394 */     TextureImpl texture = new TextureImpl("generated:" + dataSource, target, textureID);
/* 256:    */     
/* 257:396 */     int minFilter = filter;
/* 258:397 */     int magFilter = filter;
/* 259:398 */     boolean flipped = false;
/* 260:    */     
/* 261:    */ 
/* 262:401 */     GL.glBindTexture(target, textureID);
/* 263:    */     
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:410 */     int width = dataSource.getWidth();
/* 272:411 */     int height = dataSource.getHeight();
/* 273:412 */     boolean hasAlpha = dataSource.getDepth() == 32;
/* 274:    */     
/* 275:414 */     texture.setTextureWidth(dataSource.getTexWidth());
/* 276:415 */     texture.setTextureHeight(dataSource.getTexHeight());
/* 277:    */     
/* 278:417 */     int texWidth = texture.getTextureWidth();
/* 279:418 */     int texHeight = texture.getTextureHeight();
/* 280:    */     
/* 281:420 */     int srcPixelFormat = hasAlpha ? 6408 : 6407;
/* 282:421 */     int componentCount = hasAlpha ? 4 : 3;
/* 283:    */     
/* 284:423 */     texture.setWidth(width);
/* 285:424 */     texture.setHeight(height);
/* 286:425 */     texture.setAlpha(hasAlpha);
/* 287:    */     
/* 288:427 */     IntBuffer temp = BufferUtils.createIntBuffer(16);
/* 289:428 */     GL.glGetInteger(3379, temp);
/* 290:429 */     int max = temp.get(0);
/* 291:430 */     if ((texWidth > max) || (texHeight > max)) {
/* 292:431 */       throw new IOException("Attempt to allocate a texture to big for the current hardware");
/* 293:    */     }
/* 294:434 */     if (this.holdTextureData) {
/* 295:435 */       texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
/* 296:    */     }
/* 297:438 */     GL.glTexParameteri(target, 10241, minFilter);
/* 298:439 */     GL.glTexParameteri(target, 10240, magFilter);
/* 299:    */     
/* 300:    */ 
/* 301:442 */     GL.glTexImage2D(target, 
/* 302:443 */       0, 
/* 303:444 */       this.dstPixelFormat, 
/* 304:445 */       get2Fold(width), 
/* 305:446 */       get2Fold(height), 
/* 306:447 */       0, 
/* 307:448 */       srcPixelFormat, 
/* 308:449 */       5121, 
/* 309:450 */       textureBuffer);
/* 310:    */     
/* 311:452 */     return texture;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public static int get2Fold(int fold)
/* 315:    */   {
/* 316:462 */     int ret = 2;
/* 317:463 */     while (ret < fold) {
/* 318:464 */       ret *= 2;
/* 319:    */     }
/* 320:466 */     return ret;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public static IntBuffer createIntBuffer(int size)
/* 324:    */   {
/* 325:477 */     ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
/* 326:478 */     temp.order(ByteOrder.nativeOrder());
/* 327:    */     
/* 328:480 */     return temp.asIntBuffer();
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void reload()
/* 332:    */   {
/* 333:487 */     Iterator texs = this.texturesLinear.values().iterator();
/* 334:488 */     while (texs.hasNext()) {
/* 335:489 */       ((TextureImpl)texs.next()).reload();
/* 336:    */     }
/* 337:491 */     texs = this.texturesNearest.values().iterator();
/* 338:492 */     while (texs.hasNext()) {
/* 339:493 */       ((TextureImpl)texs.next()).reload();
/* 340:    */     }
/* 341:    */   }
/* 342:    */   
/* 343:    */   public int reload(TextureImpl texture, int srcPixelFormat, int componentCount, int minFilter, int magFilter, ByteBuffer textureBuffer)
/* 344:    */   {
/* 345:510 */     int target = 3553;
/* 346:511 */     int textureID = createTextureID();
/* 347:512 */     GL.glBindTexture(target, textureID);
/* 348:    */     
/* 349:514 */     GL.glTexParameteri(target, 10241, minFilter);
/* 350:515 */     GL.glTexParameteri(target, 10240, magFilter);
/* 351:    */     
/* 352:    */ 
/* 353:518 */     GL.glTexImage2D(target, 
/* 354:519 */       0, 
/* 355:520 */       this.dstPixelFormat, 
/* 356:521 */       texture.getTextureWidth(), 
/* 357:522 */       texture.getTextureHeight(), 
/* 358:523 */       0, 
/* 359:524 */       srcPixelFormat, 
/* 360:525 */       5121, 
/* 361:526 */       textureBuffer);
/* 362:    */     
/* 363:528 */     return textureID;
/* 364:    */   }
/* 365:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.InternalTextureLoader
 * JD-Core Version:    0.7.0.1
 */