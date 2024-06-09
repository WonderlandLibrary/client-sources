/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.Sys;
/*     */ import org.lwjgl.input.Cursor;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.Drawable;
/*     */ import org.lwjgl.opengl.Pbuffer;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ import org.newdawn.slick.gui.GUIContext;
/*     */ import org.newdawn.slick.openal.SoundStore;
/*     */ import org.newdawn.slick.opengl.CursorLoader;
/*     */ import org.newdawn.slick.opengl.ImageData;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ import org.newdawn.slick.util.Log;
/*     */ import org.newdawn.slick.util.ResourceLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GameContainer
/*     */   implements GUIContext
/*     */ {
/*  30 */   protected static SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   protected static Drawable SHARED_DRAWABLE;
/*     */ 
/*     */   
/*     */   protected long lastFrame;
/*     */ 
/*     */   
/*     */   protected long lastFPS;
/*     */ 
/*     */   
/*     */   protected int recordedFPS;
/*     */   
/*     */   protected int fps;
/*     */   
/*     */   protected boolean running = true;
/*     */   
/*     */   protected int width;
/*     */   
/*     */   protected int height;
/*     */   
/*     */   protected Game game;
/*     */   
/*     */   private Font defaultFont;
/*     */   
/*     */   private Graphics graphics;
/*     */   
/*     */   protected Input input;
/*     */   
/*  60 */   protected int targetFPS = -1;
/*     */   
/*     */   private boolean showFPS = true;
/*     */   
/*  64 */   protected long minimumLogicInterval = 1L;
/*     */   
/*     */   protected long storedDelta;
/*     */   
/*  68 */   protected long maximumLogicInterval = 0L;
/*     */ 
/*     */   
/*     */   protected Game lastGame;
/*     */ 
/*     */   
/*     */   protected boolean clearEachFrame = true;
/*     */ 
/*     */   
/*     */   protected boolean paused;
/*     */ 
/*     */   
/*     */   protected boolean forceExit = true;
/*     */ 
/*     */   
/*     */   protected boolean vsync;
/*     */ 
/*     */   
/*     */   protected boolean smoothDeltas;
/*     */ 
/*     */   
/*     */   protected int samples;
/*     */   
/*     */   protected boolean supportsMultiSample;
/*     */   
/*     */   protected boolean alwaysRender;
/*     */   
/*     */   protected static boolean stencil;
/*     */ 
/*     */   
/*     */   protected GameContainer(Game game) {
/*  99 */     this.game = game;
/* 100 */     this.lastFrame = getTime();
/*     */     
/* 102 */     getBuildVersion();
/* 103 */     Log.checkVerboseLogSetting();
/*     */   }
/*     */   
/*     */   public static void enableStencil() {
/* 107 */     stencil = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultFont(Font font) {
/* 116 */     if (font != null) {
/* 117 */       this.defaultFont = font;
/*     */     } else {
/* 119 */       Log.warn("Please provide a non null font");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiSample(int samples) {
/* 130 */     this.samples = samples;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsMultiSample() {
/* 139 */     return this.supportsMultiSample;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSamples() {
/* 149 */     return this.samples;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForceExit(boolean forceExit) {
/* 159 */     this.forceExit = forceExit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSmoothDeltas(boolean smoothDeltas) {
/* 170 */     this.smoothDeltas = smoothDeltas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullscreen() {
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAspectRatio() {
/* 188 */     return (getWidth() / getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFullscreen(boolean fullscreen) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void enableSharedContext() throws SlickException {
/*     */     try {
/* 209 */       SHARED_DRAWABLE = (Drawable)new Pbuffer(64, 64, new PixelFormat(8, 0, 0), null);
/* 210 */     } catch (LWJGLException e) {
/* 211 */       throw new SlickException("Unable to create the pbuffer used for shard context, buffers not supported", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Drawable getSharedContext() {
/* 221 */     return SHARED_DRAWABLE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClearEachFrame(boolean clear) {
/* 232 */     this.clearEachFrame = clear;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reinit() throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {
/* 248 */     setPaused(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resume() {
/* 256 */     setPaused(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPaused() {
/* 265 */     return this.paused;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaused(boolean paused) {
/* 276 */     this.paused = paused;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAlwaysRender() {
/* 285 */     return this.alwaysRender;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlwaysRender(boolean alwaysRender) {
/* 294 */     this.alwaysRender = alwaysRender;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getBuildVersion() {
/*     */     try {
/* 304 */       Properties props = new Properties();
/* 305 */       props.load(ResourceLoader.getResourceAsStream("version"));
/*     */       
/* 307 */       int build = Integer.parseInt(props.getProperty("build"));
/* 308 */       Log.info("Slick Build #" + build);
/*     */       
/* 310 */       return build;
/* 311 */     } catch (Exception e) {
/* 312 */       Log.error("Unable to determine Slick build number");
/* 313 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getDefaultFont() {
/* 323 */     return this.defaultFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSoundOn() {
/* 332 */     return SoundStore.get().soundsOn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMusicOn() {
/* 341 */     return SoundStore.get().musicOn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMusicOn(boolean on) {
/* 350 */     SoundStore.get().setMusicOn(on);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSoundOn(boolean on) {
/* 359 */     SoundStore.get().setSoundsOn(on);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMusicVolume() {
/* 367 */     return SoundStore.get().getMusicVolume();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSoundVolume() {
/* 375 */     return SoundStore.get().getSoundVolume();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSoundVolume(float volume) {
/* 383 */     SoundStore.get().setSoundVolume(volume);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMusicVolume(float volume) {
/* 391 */     SoundStore.get().setMusicVolume(volume);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getScreenWidth();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getScreenHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 414 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 423 */     return this.height;
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
/*     */   public abstract void setIcon(String paramString) throws SlickException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setIcons(String[] paramArrayOfString) throws SlickException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTime() {
/* 453 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sleep(int milliseconds) {
/* 462 */     long target = getTime() + milliseconds;
/* 463 */     while (getTime() < target) { 
/* 464 */       try { Thread.sleep(1L); } catch (Exception e) {} }
/*     */   
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
/*     */   public abstract void setMouseCursor(String paramString, int paramInt1, int paramInt2) throws SlickException;
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
/*     */   public abstract void setMouseCursor(ImageData paramImageData, int paramInt1, int paramInt2) throws SlickException;
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
/*     */   public abstract void setMouseCursor(Image paramImage, int paramInt1, int paramInt2) throws SlickException;
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
/*     */   public abstract void setMouseCursor(Cursor paramCursor, int paramInt1, int paramInt2) throws SlickException;
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
/*     */   public void setAnimatedMouseCursor(String ref, int x, int y, int width, int height, int[] cursorDelays) throws SlickException {
/*     */     try {
/* 533 */       Cursor cursor = CursorLoader.get().getAnimatedCursor(ref, x, y, width, height, cursorDelays);
/* 534 */       setMouseCursor(cursor, x, y);
/* 535 */     } catch (IOException e) {
/* 536 */       throw new SlickException("Failed to set mouse cursor", e);
/* 537 */     } catch (LWJGLException e) {
/* 538 */       throw new SlickException("Failed to set mouse cursor", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setDefaultMouseCursor();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Input getInput() {
/* 554 */     return this.input;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFPS() {
/* 563 */     return this.recordedFPS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setMouseGrabbed(boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isMouseGrabbed();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getDelta() {
/* 587 */     long time = getTime();
/* 588 */     int delta = (int)(time - this.lastFrame);
/* 589 */     this.lastFrame = time;
/*     */     
/* 591 */     return delta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateFPS() {
/* 598 */     if (getTime() - this.lastFPS > 1000L) {
/* 599 */       this.lastFPS = getTime();
/* 600 */       this.recordedFPS = this.fps;
/* 601 */       this.fps = 0;
/*     */     } 
/* 603 */     this.fps++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinimumLogicUpdateInterval(int interval) {
/* 614 */     this.minimumLogicInterval = interval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaximumLogicUpdateInterval(int interval) {
/* 625 */     this.maximumLogicInterval = interval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateAndRender(int delta) throws SlickException {
/* 635 */     if (this.smoothDeltas && 
/* 636 */       getFPS() != 0) {
/* 637 */       delta = 1000 / getFPS();
/*     */     }
/*     */ 
/*     */     
/* 641 */     this.input.poll(this.width, this.height);
/*     */     
/* 643 */     Music.poll(delta);
/* 644 */     if (!this.paused) {
/* 645 */       this.storedDelta += delta;
/*     */       
/* 647 */       if (this.storedDelta >= this.minimumLogicInterval) {
/*     */         try {
/* 649 */           if (this.maximumLogicInterval != 0L) {
/* 650 */             long cycles = this.storedDelta / this.maximumLogicInterval;
/* 651 */             for (int i = 0; i < cycles; i++) {
/* 652 */               this.game.update(this, (int)this.maximumLogicInterval);
/*     */             }
/*     */             
/* 655 */             int remainder = (int)(this.storedDelta % this.maximumLogicInterval);
/* 656 */             if (remainder > this.minimumLogicInterval) {
/* 657 */               this.game.update(this, (int)(remainder % this.maximumLogicInterval));
/* 658 */               this.storedDelta = 0L;
/*     */             } else {
/* 660 */               this.storedDelta = remainder;
/*     */             } 
/*     */           } else {
/* 663 */             this.game.update(this, (int)this.storedDelta);
/* 664 */             this.storedDelta = 0L;
/*     */           }
/*     */         
/* 667 */         } catch (Throwable e) {
/* 668 */           Log.error(e);
/* 669 */           throw new SlickException("Game.update() failure - check the game code.");
/*     */         } 
/*     */       }
/*     */     } else {
/* 673 */       this.game.update(this, 0);
/*     */     } 
/*     */     
/* 676 */     if (hasFocus() || getAlwaysRender()) {
/* 677 */       if (this.clearEachFrame) {
/* 678 */         GL.glClear(16640);
/*     */       }
/*     */       
/* 681 */       GL.glLoadIdentity();
/*     */       
/* 683 */       this.graphics.resetTransform();
/* 684 */       this.graphics.resetFont();
/* 685 */       this.graphics.resetLineWidth();
/* 686 */       this.graphics.setAntiAlias(false);
/*     */       try {
/* 688 */         this.game.render(this, this.graphics);
/* 689 */       } catch (Throwable e) {
/* 690 */         Log.error(e);
/* 691 */         throw new SlickException("Game.render() failure - check the game code.");
/*     */       } 
/* 693 */       this.graphics.resetTransform();
/*     */       
/* 695 */       if (this.showFPS) {
/* 696 */         this.defaultFont.drawString(10.0F, 10.0F, "FPS: " + this.recordedFPS);
/*     */       }
/*     */       
/* 699 */       GL.flush();
/*     */     } 
/*     */     
/* 702 */     if (this.targetFPS != -1) {
/* 703 */       Display.sync(this.targetFPS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUpdateOnlyWhenVisible(boolean updateOnlyWhenVisible) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUpdatingOnlyWhenVisible() {
/* 722 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initGL() {
/* 729 */     Log.info("Starting display " + this.width + "x" + this.height);
/* 730 */     GL.initDisplay(this.width, this.height);
/*     */     
/* 732 */     if (this.input == null) {
/* 733 */       this.input = new Input(this.height);
/*     */     }
/* 735 */     this.input.init(this.height);
/*     */ 
/*     */     
/* 738 */     if (this.game instanceof InputListener) {
/* 739 */       this.input.removeListener((InputListener)this.game);
/* 740 */       this.input.addListener((InputListener)this.game);
/*     */     } 
/*     */     
/* 743 */     if (this.graphics != null) {
/* 744 */       this.graphics.setDimensions(getWidth(), getHeight());
/*     */     }
/* 746 */     this.lastGame = this.game;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initSystem() throws SlickException {
/* 755 */     initGL();
/* 756 */     setMusicVolume(1.0F);
/* 757 */     setSoundVolume(1.0F);
/*     */     
/* 759 */     this.graphics = new Graphics(this.width, this.height);
/* 760 */     this.defaultFont = this.graphics.getFont();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enterOrtho() {
/* 767 */     enterOrtho(this.width, this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShowFPS(boolean show) {
/* 776 */     this.showFPS = show;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShowingFPS() {
/* 785 */     return this.showFPS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetFrameRate(int fps) {
/* 794 */     this.targetFPS = fps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVSync(boolean vsync) {
/* 804 */     this.vsync = vsync;
/* 805 */     Display.setVSyncEnabled(vsync);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVSyncRequested() {
/* 814 */     return this.vsync;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean running() {
/* 823 */     return this.running;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerbose(boolean verbose) {
/* 832 */     Log.setVerbose(verbose);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exit() {
/* 839 */     this.running = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean hasFocus();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Graphics getGraphics() {
/* 856 */     return this.graphics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enterOrtho(int xsize, int ysize) {
/* 866 */     GL.enterOrtho(xsize, ysize);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\GameContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */