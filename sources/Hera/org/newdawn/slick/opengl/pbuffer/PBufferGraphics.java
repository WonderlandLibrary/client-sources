/*     */ package org.newdawn.slick.opengl.pbuffer;
/*     */ 
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.Pbuffer;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ import org.lwjgl.opengl.RenderTexture;
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
/*     */ public class PBufferGraphics
/*     */   extends Graphics
/*     */ {
/*     */   private Pbuffer pbuffer;
/*     */   private Image image;
/*     */   
/*     */   public PBufferGraphics(Image image) throws SlickException {
/*  37 */     super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
/*  38 */     this.image = image;
/*     */     
/*  40 */     Log.debug("Creating pbuffer(rtt) " + image.getWidth() + "x" + image.getHeight());
/*  41 */     if ((Pbuffer.getCapabilities() & 0x1) == 0) {
/*  42 */       throw new SlickException("Your OpenGL card does not support PBuffers and hence can't handle the dynamic images required for this application.");
/*     */     }
/*  44 */     if ((Pbuffer.getCapabilities() & 0x2) == 0) {
/*  45 */       throw new SlickException("Your OpenGL card does not support Render-To-Texture and hence can't handle the dynamic images required for this application.");
/*     */     }
/*     */     
/*  48 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() throws SlickException {
/*     */     try {
/*  58 */       Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
/*     */       
/*  60 */       RenderTexture rt = new RenderTexture(false, true, false, false, 8314, 0);
/*  61 */       this.pbuffer = new Pbuffer(this.screenWidth, this.screenHeight, new PixelFormat(8, 0, 0), rt, null);
/*     */ 
/*     */       
/*  64 */       this.pbuffer.makeCurrent();
/*     */       
/*  66 */       initGL();
/*  67 */       GL.glBindTexture(3553, tex.getTextureID());
/*  68 */       this.pbuffer.releaseTexImage(8323);
/*  69 */       this.image.draw(0.0F, 0.0F);
/*  70 */       this.image.setTexture(tex);
/*     */       
/*  72 */       Display.makeCurrent();
/*  73 */     } catch (Exception e) {
/*  74 */       Log.error(e);
/*  75 */       throw new SlickException("Failed to create PBuffer for dynamic image. OpenGL driver failure?");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disable() {
/*  83 */     GL.flush();
/*     */ 
/*     */     
/*  86 */     GL.glBindTexture(3553, this.image.getTexture().getTextureID());
/*  87 */     this.pbuffer.bindTexImage(8323);
/*     */     
/*     */     try {
/*  90 */       Display.makeCurrent();
/*  91 */     } catch (LWJGLException e) {
/*  92 */       Log.error((Throwable)e);
/*     */     } 
/*     */     
/*  95 */     SlickCallable.leaveSafeBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enable() {
/* 102 */     SlickCallable.enterSafeBlock();
/*     */     
/*     */     try {
/* 105 */       if (this.pbuffer.isBufferLost()) {
/* 106 */         this.pbuffer.destroy();
/* 107 */         init();
/*     */       } 
/*     */       
/* 110 */       this.pbuffer.makeCurrent();
/* 111 */     } catch (Exception e) {
/* 112 */       Log.error("Failed to recreate the PBuffer");
/* 113 */       throw new RuntimeException(e);
/*     */     } 
/*     */ 
/*     */     
/* 117 */     GL.glBindTexture(3553, this.image.getTexture().getTextureID());
/* 118 */     this.pbuffer.releaseTexImage(8323);
/* 119 */     TextureImpl.unbind();
/* 120 */     initGL();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initGL() {
/* 127 */     GL11.glEnable(3553);
/* 128 */     GL11.glShadeModel(7425);
/* 129 */     GL11.glDisable(2929);
/* 130 */     GL11.glDisable(2896);
/*     */     
/* 132 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 133 */     GL11.glClearDepth(1.0D);
/*     */     
/* 135 */     GL11.glEnable(3042);
/* 136 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 138 */     GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
/* 139 */     GL11.glMatrixMode(5888);
/* 140 */     GL11.glLoadIdentity();
/*     */     
/* 142 */     enterOrtho();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enterOrtho() {
/* 149 */     GL11.glMatrixMode(5889);
/* 150 */     GL11.glLoadIdentity();
/* 151 */     GL11.glOrtho(0.0D, this.screenWidth, 0.0D, this.screenHeight, 1.0D, -1.0D);
/* 152 */     GL11.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 159 */     super.destroy();
/*     */     
/* 161 */     this.pbuffer.destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 168 */     super.flush();
/*     */     
/* 170 */     this.image.flushPixelData();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\pbuffer\PBufferGraphics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */