/*     */ package org.newdawn.slick.opengl.pbuffer;
/*     */ 
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.Pbuffer;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*     */ import org.newdawn.slick.opengl.SlickCallable;
/*     */ import org.newdawn.slick.opengl.Texture;
/*     */ import org.newdawn.slick.opengl.TextureImpl;
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
/*     */ public class PBufferUniqueGraphics
/*     */   extends Graphics
/*     */ {
/*     */   private Pbuffer pbuffer;
/*     */   private Image image;
/*     */   
/*     */   public PBufferUniqueGraphics(Image image) throws SlickException {
/*  36 */     super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
/*  37 */     this.image = image;
/*     */     
/*  39 */     Log.debug("Creating pbuffer(unique) " + image.getWidth() + "x" + image.getHeight());
/*  40 */     if ((Pbuffer.getCapabilities() & 0x1) == 0) {
/*  41 */       throw new SlickException("Your OpenGL card does not support PBuffers and hence can't handle the dynamic images required for this application.");
/*     */     }
/*     */     
/*  44 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() throws SlickException {
/*     */     try {
/*  54 */       Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
/*     */       
/*  56 */       this.pbuffer = new Pbuffer(this.screenWidth, this.screenHeight, new PixelFormat(8, 0, 0), null, null);
/*     */       
/*  58 */       this.pbuffer.makeCurrent();
/*     */       
/*  60 */       initGL();
/*  61 */       this.image.draw(0.0F, 0.0F);
/*  62 */       GL11.glBindTexture(3553, tex.getTextureID());
/*  63 */       GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, tex.getTextureWidth(), tex.getTextureHeight(), 0);
/*     */ 
/*     */       
/*  66 */       this.image.setTexture(tex);
/*     */       
/*  68 */       Display.makeCurrent();
/*  69 */     } catch (Exception e) {
/*  70 */       Log.error(e);
/*  71 */       throw new SlickException("Failed to create PBuffer for dynamic image. OpenGL driver failure?");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disable() {
/*  80 */     GL11.glBindTexture(3553, this.image.getTexture().getTextureID());
/*  81 */     GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, this.image.getTexture().getTextureWidth(), this.image.getTexture().getTextureHeight(), 0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  86 */       Display.makeCurrent();
/*  87 */     } catch (LWJGLException e) {
/*  88 */       Log.error((Throwable)e);
/*     */     } 
/*     */     
/*  91 */     SlickCallable.leaveSafeBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enable() {
/*  98 */     SlickCallable.enterSafeBlock();
/*     */     
/*     */     try {
/* 101 */       if (this.pbuffer.isBufferLost()) {
/* 102 */         this.pbuffer.destroy();
/* 103 */         init();
/*     */       } 
/*     */       
/* 106 */       this.pbuffer.makeCurrent();
/* 107 */     } catch (Exception e) {
/* 108 */       Log.error("Failed to recreate the PBuffer");
/* 109 */       Log.error(e);
/* 110 */       throw new RuntimeException(e);
/*     */     } 
/*     */ 
/*     */     
/* 114 */     TextureImpl.unbind();
/* 115 */     initGL();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initGL() {
/* 122 */     GL11.glEnable(3553);
/* 123 */     GL11.glShadeModel(7425);
/* 124 */     GL11.glDisable(2929);
/* 125 */     GL11.glDisable(2896);
/*     */     
/* 127 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 128 */     GL11.glClearDepth(1.0D);
/*     */     
/* 130 */     GL11.glEnable(3042);
/* 131 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 133 */     GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
/* 134 */     GL11.glMatrixMode(5888);
/* 135 */     GL11.glLoadIdentity();
/*     */     
/* 137 */     enterOrtho();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enterOrtho() {
/* 144 */     GL11.glMatrixMode(5889);
/* 145 */     GL11.glLoadIdentity();
/* 146 */     GL11.glOrtho(0.0D, this.screenWidth, 0.0D, this.screenHeight, 1.0D, -1.0D);
/* 147 */     GL11.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 154 */     super.destroy();
/*     */     
/* 156 */     this.pbuffer.destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 163 */     super.flush();
/*     */     
/* 165 */     this.image.flushPixelData();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\pbuffer\PBufferUniqueGraphics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */