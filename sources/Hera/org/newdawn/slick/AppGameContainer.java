/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.Sys;
/*     */ import org.lwjgl.input.Cursor;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.openal.AL;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.DisplayMode;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ import org.newdawn.slick.openal.SoundStore;
/*     */ import org.newdawn.slick.opengl.CursorLoader;
/*     */ import org.newdawn.slick.opengl.ImageData;
/*     */ import org.newdawn.slick.opengl.ImageIOImageData;
/*     */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*     */ import org.newdawn.slick.opengl.TGAImageData;
/*     */ import org.newdawn.slick.util.Log;
/*     */ import org.newdawn.slick.util.ResourceLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppGameContainer
/*     */   extends GameContainer
/*     */ {
/*     */   protected DisplayMode originalDisplayMode;
/*     */   protected DisplayMode targetDisplayMode;
/*     */   
/*     */   static {
/*  36 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/*     */             try {
/*  39 */               Display.getDisplayMode();
/*  40 */             } catch (Exception e) {
/*  41 */               Log.error(e);
/*     */             } 
/*  43 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean updateOnlyOnVisible = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean alphaSupport = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppGameContainer(Game game) throws SlickException {
/*  63 */     this(game, 640, 480, false);
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
/*     */   public AppGameContainer(Game game, int width, int height, boolean fullscreen) throws SlickException {
/*  76 */     super(game);
/*     */     
/*  78 */     this.originalDisplayMode = Display.getDisplayMode();
/*     */     
/*  80 */     setDisplayMode(width, height, fullscreen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsAlphaInBackBuffer() {
/*  89 */     return this.alphaSupport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String title) {
/*  98 */     Display.setTitle(title);
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
/*     */   public void setDisplayMode(int width, int height, boolean fullscreen) throws SlickException {
/* 110 */     if (this.width == width && this.height == height && isFullscreen() == fullscreen) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 115 */       this.targetDisplayMode = null;
/* 116 */       if (fullscreen) {
/* 117 */         DisplayMode[] modes = Display.getAvailableDisplayModes();
/* 118 */         int freq = 0;
/*     */         
/* 120 */         for (int i = 0; i < modes.length; i++) {
/* 121 */           DisplayMode current = modes[i];
/*     */           
/* 123 */           if (current.getWidth() == width && current.getHeight() == height) {
/* 124 */             if ((this.targetDisplayMode == null || current.getFrequency() >= freq) && (
/* 125 */               this.targetDisplayMode == null || current.getBitsPerPixel() > this.targetDisplayMode.getBitsPerPixel())) {
/* 126 */               this.targetDisplayMode = current;
/* 127 */               freq = this.targetDisplayMode.getFrequency();
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 134 */             if (current.getBitsPerPixel() == this.originalDisplayMode.getBitsPerPixel() && current.getFrequency() == this.originalDisplayMode.getFrequency()) {
/*     */               
/* 136 */               this.targetDisplayMode = current;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 142 */         this.targetDisplayMode = new DisplayMode(width, height);
/*     */       } 
/*     */       
/* 145 */       if (this.targetDisplayMode == null) {
/* 146 */         throw new SlickException("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
/*     */       }
/*     */       
/* 149 */       this.width = width;
/* 150 */       this.height = height;
/*     */       
/* 152 */       Display.setDisplayMode(this.targetDisplayMode);
/* 153 */       Display.setFullscreen(fullscreen);
/*     */       
/* 155 */       if (Display.isCreated()) {
/* 156 */         initGL();
/* 157 */         enterOrtho();
/*     */       } 
/*     */       
/* 160 */       if (this.targetDisplayMode.getBitsPerPixel() == 16) {
/* 161 */         InternalTextureLoader.get().set16BitMode();
/*     */       }
/* 163 */     } catch (LWJGLException e) {
/* 164 */       throw new SlickException("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen, e);
/*     */     } 
/*     */     
/* 167 */     getDelta();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullscreen() {
/* 176 */     return Display.isFullscreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFullscreen(boolean fullscreen) throws SlickException {
/* 187 */     if (isFullscreen() == fullscreen) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     if (!fullscreen) {
/*     */       try {
/* 193 */         Display.setFullscreen(fullscreen);
/* 194 */       } catch (LWJGLException e) {
/* 195 */         throw new SlickException("Unable to set fullscreen=" + fullscreen, e);
/*     */       } 
/*     */     } else {
/* 198 */       setDisplayMode(this.width, this.height, fullscreen);
/*     */     } 
/* 200 */     getDelta();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseCursor(String ref, int hotSpotX, int hotSpotY) throws SlickException {
/*     */     try {
/* 208 */       Cursor cursor = CursorLoader.get().getCursor(ref, hotSpotX, hotSpotY);
/* 209 */       Mouse.setNativeCursor(cursor);
/* 210 */     } catch (Throwable e) {
/* 211 */       Log.error("Failed to load and apply cursor.", e);
/* 212 */       throw new SlickException("Failed to set mouse cursor", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseCursor(ImageData data, int hotSpotX, int hotSpotY) throws SlickException {
/*     */     try {
/* 221 */       Cursor cursor = CursorLoader.get().getCursor(data, hotSpotX, hotSpotY);
/* 222 */       Mouse.setNativeCursor(cursor);
/* 223 */     } catch (Throwable e) {
/* 224 */       Log.error("Failed to load and apply cursor.", e);
/* 225 */       throw new SlickException("Failed to set mouse cursor", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseCursor(Cursor cursor, int hotSpotX, int hotSpotY) throws SlickException {
/*     */     try {
/* 234 */       Mouse.setNativeCursor(cursor);
/* 235 */     } catch (Throwable e) {
/* 236 */       Log.error("Failed to load and apply cursor.", e);
/* 237 */       throw new SlickException("Failed to set mouse cursor", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int get2Fold(int fold) {
/* 248 */     int ret = 2;
/* 249 */     while (ret < fold) {
/* 250 */       ret *= 2;
/*     */     }
/* 252 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseCursor(Image image, int hotSpotX, int hotSpotY) throws SlickException {
/*     */     try {
/* 260 */       Image temp = new Image(get2Fold(image.getWidth()), get2Fold(image.getHeight()));
/* 261 */       Graphics g = temp.getGraphics();
/*     */       
/* 263 */       ByteBuffer buffer = BufferUtils.createByteBuffer(temp.getWidth() * temp.getHeight() * 4);
/* 264 */       g.drawImage(image.getFlippedCopy(false, true), 0.0F, 0.0F);
/* 265 */       g.flush();
/* 266 */       g.getArea(0, 0, temp.getWidth(), temp.getHeight(), buffer);
/*     */       
/* 268 */       Cursor cursor = CursorLoader.get().getCursor(buffer, hotSpotX, hotSpotY, temp.getWidth(), image.getHeight());
/* 269 */       Mouse.setNativeCursor(cursor);
/* 270 */     } catch (Throwable e) {
/* 271 */       Log.error("Failed to load and apply cursor.", e);
/* 272 */       throw new SlickException("Failed to set mouse cursor", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reinit() throws SlickException {
/* 280 */     InternalTextureLoader.get().clear();
/* 281 */     SoundStore.get().clear();
/* 282 */     initSystem();
/* 283 */     enterOrtho();
/*     */     
/*     */     try {
/* 286 */       this.game.init(this);
/* 287 */     } catch (SlickException e) {
/* 288 */       Log.error(e);
/* 289 */       this.running = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tryCreateDisplay(PixelFormat format) throws LWJGLException {
/* 300 */     if (SHARED_DRAWABLE == null) {
/*     */       
/* 302 */       Display.create(format);
/*     */     }
/*     */     else {
/*     */       
/* 306 */       Display.create(format, SHARED_DRAWABLE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() throws SlickException {
/*     */     try {
/* 317 */       setup();
/*     */       
/* 319 */       getDelta();
/* 320 */       while (running()) {
/* 321 */         gameLoop();
/*     */       }
/*     */     } finally {
/* 324 */       destroy();
/*     */     } 
/*     */     
/* 327 */     if (this.forceExit) {
/* 328 */       System.exit(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setup() throws SlickException {
/* 338 */     if (this.targetDisplayMode == null) {
/* 339 */       setDisplayMode(640, 480, false);
/*     */     }
/*     */     
/* 342 */     Display.setTitle(this.game.getTitle());
/*     */     
/* 344 */     Log.info("LWJGL Version: " + Sys.getVersion());
/* 345 */     Log.info("OriginalDisplayMode: " + this.originalDisplayMode);
/* 346 */     Log.info("TargetDisplayMode: " + this.targetDisplayMode);
/*     */     
/* 348 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/*     */             try {
/* 351 */               PixelFormat format = new PixelFormat(8, 8, GameContainer.stencil ? 8 : 0, AppGameContainer.this.samples);
/*     */               
/* 353 */               AppGameContainer.this.tryCreateDisplay(format);
/* 354 */               AppGameContainer.this.supportsMultiSample = true;
/* 355 */             } catch (Exception e) {
/* 356 */               Display.destroy();
/*     */               
/*     */               try {
/* 359 */                 PixelFormat format = new PixelFormat(8, 8, GameContainer.stencil ? 8 : 0);
/*     */                 
/* 361 */                 AppGameContainer.this.tryCreateDisplay(format);
/* 362 */                 AppGameContainer.this.alphaSupport = false;
/* 363 */               } catch (Exception e2) {
/* 364 */                 Display.destroy();
/*     */                 
/*     */                 try {
/* 367 */                   AppGameContainer.this.tryCreateDisplay(new PixelFormat());
/* 368 */                 } catch (Exception e3) {
/* 369 */                   Log.error(e3);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 374 */             return null;
/*     */           }
/*     */         });
/* 377 */     if (!Display.isCreated()) {
/* 378 */       throw new SlickException("Failed to initialise the LWJGL display");
/*     */     }
/*     */     
/* 381 */     initSystem();
/* 382 */     enterOrtho();
/*     */     
/*     */     try {
/* 385 */       getInput().initControllers();
/* 386 */     } catch (SlickException e) {
/* 387 */       Log.info("Controllers not available");
/* 388 */     } catch (Throwable e) {
/* 389 */       Log.info("Controllers not available");
/*     */     } 
/*     */     
/*     */     try {
/* 393 */       this.game.init(this);
/* 394 */     } catch (SlickException e) {
/* 395 */       Log.error(e);
/* 396 */       this.running = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void gameLoop() throws SlickException {
/* 406 */     int delta = getDelta();
/* 407 */     if (!Display.isVisible() && this.updateOnlyOnVisible) { 
/* 408 */       try { Thread.sleep(100L); } catch (Exception e) {} }
/*     */     else
/*     */     { try {
/* 411 */         updateAndRender(delta);
/* 412 */       } catch (SlickException e) {
/* 413 */         Log.error(e);
/* 414 */         this.running = false;
/*     */         
/*     */         return;
/*     */       }  }
/*     */     
/* 419 */     updateFPS();
/*     */     
/* 421 */     Display.update();
/*     */     
/* 423 */     if (Display.isCloseRequested() && 
/* 424 */       this.game.closeRequested()) {
/* 425 */       this.running = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUpdateOnlyWhenVisible(boolean updateOnlyWhenVisible) {
/* 434 */     this.updateOnlyOnVisible = updateOnlyWhenVisible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUpdatingOnlyWhenVisible() {
/* 441 */     return this.updateOnlyOnVisible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIcon(String ref) throws SlickException {
/* 448 */     setIcons(new String[] { ref });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMouseGrabbed(boolean grabbed) {
/* 455 */     Mouse.setGrabbed(grabbed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMouseGrabbed() {
/* 462 */     return Mouse.isGrabbed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFocus() {
/* 470 */     return Display.isActive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScreenHeight() {
/* 477 */     return this.originalDisplayMode.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScreenWidth() {
/* 484 */     return this.originalDisplayMode.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 491 */     Display.destroy();
/* 492 */     AL.destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class NullOutputStream
/*     */     extends OutputStream
/*     */   {
/*     */     public void write(int b) throws IOException {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIcons(String[] refs) throws SlickException {
/* 514 */     ByteBuffer[] bufs = new ByteBuffer[refs.length];
/* 515 */     for (int i = 0; i < refs.length; i++) {
/*     */       ImageIOImageData imageIOImageData;
/* 517 */       boolean flip = true;
/*     */       
/* 519 */       if (refs[i].endsWith(".tga")) {
/* 520 */         TGAImageData tGAImageData = new TGAImageData();
/*     */       } else {
/* 522 */         flip = false;
/* 523 */         imageIOImageData = new ImageIOImageData();
/*     */       } 
/*     */       
/*     */       try {
/* 527 */         bufs[i] = imageIOImageData.loadImage(ResourceLoader.getResourceAsStream(refs[i]), flip, false, null);
/* 528 */       } catch (Exception e) {
/* 529 */         Log.error(e);
/* 530 */         throw new SlickException("Failed to set the icon");
/*     */       } 
/*     */     } 
/*     */     
/* 534 */     Display.setIcon(bufs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultMouseCursor() {
/*     */     try {
/* 542 */       Mouse.setNativeCursor(null);
/* 543 */     } catch (LWJGLException e) {
/* 544 */       Log.error("Failed to reset mouse cursor", (Throwable)e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\AppGameContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */