/*     */ package org.newdawn.slick.opengl.pbuffer;
/*     */ 
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*     */ import org.newdawn.slick.opengl.SlickCallable;
/*     */ import org.newdawn.slick.opengl.Texture;
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
/*     */ public class FBOGraphics
/*     */   extends Graphics
/*     */ {
/*     */   private Image image;
/*     */   private int FBO;
/*     */   private boolean valid = true;
/*     */   
/*     */   public FBOGraphics(Image image) throws SlickException {
/*  37 */     super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
/*  38 */     this.image = image;
/*     */     
/*  40 */     Log.debug("Creating FBO " + image.getWidth() + "x" + image.getHeight());
/*     */     
/*  42 */     boolean FBOEnabled = (GLContext.getCapabilities()).GL_EXT_framebuffer_object;
/*  43 */     if (!FBOEnabled) {
/*  44 */       throw new SlickException("Your OpenGL card does not support FBO and hence can't handle the dynamic images required for this application.");
/*     */     }
/*     */     
/*  47 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void completeCheck() throws SlickException {
/*  56 */     int framebuffer = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*  57 */     switch (framebuffer) {
/*     */       case 36053:
/*     */         return;
/*     */       case 36054:
/*  61 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception");
/*     */       
/*     */       case 36055:
/*  64 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception");
/*     */       
/*     */       case 36057:
/*  67 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception");
/*     */       
/*     */       case 36059:
/*  70 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception");
/*     */       
/*     */       case 36058:
/*  73 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception");
/*     */       
/*     */       case 36060:
/*  76 */         throw new SlickException("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception");
/*     */     } 
/*     */     
/*  79 */     throw new SlickException("Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() throws SlickException {
/*  89 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/*  90 */     EXTFramebufferObject.glGenFramebuffersEXT(buffer);
/*  91 */     this.FBO = buffer.get();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  96 */       Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
/*     */       
/*  98 */       EXTFramebufferObject.glBindFramebufferEXT(36160, this.FBO);
/*  99 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, tex.getTextureID(), 0);
/*     */ 
/*     */ 
/*     */       
/* 103 */       completeCheck();
/* 104 */       unbind();
/*     */ 
/*     */       
/* 107 */       clear();
/* 108 */       flush();
/*     */ 
/*     */       
/* 111 */       drawImage(this.image, 0.0F, 0.0F);
/* 112 */       this.image.setTexture(tex);
/*     */     }
/* 114 */     catch (Exception e) {
/* 115 */       throw new SlickException("Failed to create new texture for FBO");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void bind() {
/* 123 */     EXTFramebufferObject.glBindFramebufferEXT(36160, this.FBO);
/* 124 */     GL11.glReadBuffer(36064);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void unbind() {
/* 131 */     EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
/* 132 */     GL11.glReadBuffer(1029);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disable() {
/* 139 */     GL.flush();
/*     */     
/* 141 */     unbind();
/* 142 */     GL11.glPopClientAttrib();
/* 143 */     GL11.glPopAttrib();
/* 144 */     GL11.glMatrixMode(5888);
/* 145 */     GL11.glPopMatrix();
/* 146 */     GL11.glMatrixMode(5889);
/* 147 */     GL11.glPopMatrix();
/* 148 */     GL11.glMatrixMode(5888);
/*     */     
/* 150 */     SlickCallable.leaveSafeBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enable() {
/* 157 */     if (!this.valid) {
/* 158 */       throw new RuntimeException("Attempt to use a destroy()ed offscreen graphics context.");
/*     */     }
/* 160 */     SlickCallable.enterSafeBlock();
/*     */     
/* 162 */     GL11.glPushAttrib(1048575);
/* 163 */     GL11.glPushClientAttrib(-1);
/* 164 */     GL11.glMatrixMode(5889);
/* 165 */     GL11.glPushMatrix();
/* 166 */     GL11.glMatrixMode(5888);
/* 167 */     GL11.glPushMatrix();
/*     */     
/* 169 */     bind();
/* 170 */     initGL();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initGL() {
/* 177 */     GL11.glEnable(3553);
/* 178 */     GL11.glShadeModel(7425);
/* 179 */     GL11.glDisable(2929);
/* 180 */     GL11.glDisable(2896);
/*     */     
/* 182 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 183 */     GL11.glClearDepth(1.0D);
/*     */     
/* 185 */     GL11.glEnable(3042);
/* 186 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 188 */     GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
/* 189 */     GL11.glMatrixMode(5888);
/* 190 */     GL11.glLoadIdentity();
/*     */     
/* 192 */     enterOrtho();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enterOrtho() {
/* 199 */     GL11.glMatrixMode(5889);
/* 200 */     GL11.glLoadIdentity();
/* 201 */     GL11.glOrtho(0.0D, this.screenWidth, 0.0D, this.screenHeight, 1.0D, -1.0D);
/* 202 */     GL11.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 209 */     super.destroy();
/*     */     
/* 211 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/* 212 */     buffer.put(this.FBO);
/* 213 */     buffer.flip();
/*     */     
/* 215 */     EXTFramebufferObject.glDeleteFramebuffersEXT(buffer);
/* 216 */     this.valid = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 223 */     super.flush();
/*     */     
/* 225 */     this.image.flushPixelData();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\pbuffer\FBOGraphics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */