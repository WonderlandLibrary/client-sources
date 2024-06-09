/*     */ package org.newdawn.slick.opengl.pbuffer;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import org.lwjgl.opengl.Pbuffer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GraphicsFactory
/*     */ {
/*  20 */   private static HashMap graphics = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   private static boolean pbuffer = true;
/*     */ 
/*     */   
/*     */   private static boolean pbufferRT = true;
/*     */ 
/*     */   
/*     */   private static boolean fbo = true;
/*     */ 
/*     */   
/*     */   private static boolean init = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private static void init() throws SlickException {
/*  37 */     init = true;
/*     */     
/*  39 */     if (fbo) {
/*  40 */       fbo = (GLContext.getCapabilities()).GL_EXT_framebuffer_object;
/*     */     }
/*  42 */     pbuffer = ((Pbuffer.getCapabilities() & 0x1) != 0);
/*  43 */     pbufferRT = ((Pbuffer.getCapabilities() & 0x2) != 0);
/*     */     
/*  45 */     if (!fbo && !pbuffer && !pbufferRT) {
/*  46 */       throw new SlickException("Your OpenGL card does not support offscreen buffers and hence can't handle the dynamic images required for this application.");
/*     */     }
/*     */     
/*  49 */     Log.info("Offscreen Buffers FBO=" + fbo + " PBUFFER=" + pbuffer + " PBUFFERRT=" + pbufferRT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setUseFBO(boolean useFBO) {
/*  58 */     fbo = useFBO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean usingFBO() {
/*  67 */     return fbo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean usingPBuffer() {
/*  76 */     return (!fbo && pbuffer);
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
/*     */   public static Graphics getGraphicsForImage(Image image) throws SlickException {
/*  88 */     Graphics g = (Graphics)graphics.get(image.getTexture());
/*     */     
/*  90 */     if (g == null) {
/*  91 */       g = createGraphics(image);
/*  92 */       graphics.put(image.getTexture(), g);
/*     */     } 
/*     */     
/*  95 */     return g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void releaseGraphicsForImage(Image image) throws SlickException {
/* 105 */     Graphics g = (Graphics)graphics.remove(image.getTexture());
/*     */     
/* 107 */     if (g != null) {
/* 108 */       g.destroy();
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
/*     */   private static Graphics createGraphics(Image image) throws SlickException {
/* 120 */     init();
/*     */     
/* 122 */     if (fbo) {
/*     */       try {
/* 124 */         return new FBOGraphics(image);
/* 125 */       } catch (Exception e) {
/* 126 */         fbo = false;
/* 127 */         Log.warn("FBO failed in use, falling back to PBuffer");
/*     */       } 
/*     */     }
/*     */     
/* 131 */     if (pbuffer) {
/* 132 */       if (pbufferRT) {
/* 133 */         return new PBufferGraphics(image);
/*     */       }
/* 135 */       return new PBufferUniqueGraphics(image);
/*     */     } 
/*     */ 
/*     */     
/* 139 */     throw new SlickException("Failed to create offscreen buffer even though the card reports it's possible");
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\pbuffer\GraphicsFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */