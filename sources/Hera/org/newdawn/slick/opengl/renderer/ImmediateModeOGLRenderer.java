/*     */ package org.newdawn.slick.opengl.renderer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.opengl.EXTSecondaryColor;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImmediateModeOGLRenderer
/*     */   implements SGL
/*     */ {
/*     */   private int width;
/*     */   private int height;
/*  23 */   private float[] current = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/*     */   
/*  25 */   protected float alphaScale = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDisplay(int width, int height) {
/*  31 */     this.width = width;
/*  32 */     this.height = height;
/*     */     
/*  34 */     String extensions = GL11.glGetString(7939);
/*     */     
/*  36 */     GL11.glEnable(3553);
/*  37 */     GL11.glShadeModel(7425);
/*  38 */     GL11.glDisable(2929);
/*  39 */     GL11.glDisable(2896);
/*     */     
/*  41 */     GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  42 */     GL11.glClearDepth(1.0D);
/*     */     
/*  44 */     GL11.glEnable(3042);
/*  45 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  47 */     GL11.glViewport(0, 0, width, height);
/*  48 */     GL11.glMatrixMode(5888);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enterOrtho(int xsize, int ysize) {
/*  55 */     GL11.glMatrixMode(5889);
/*  56 */     GL11.glLoadIdentity();
/*  57 */     GL11.glOrtho(0.0D, this.width, this.height, 0.0D, 1.0D, -1.0D);
/*  58 */     GL11.glMatrixMode(5888);
/*     */     
/*  60 */     GL11.glTranslatef(((this.width - xsize) / 2), ((this.height - ysize) / 2), 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glBegin(int geomType) {
/*  68 */     GL11.glBegin(geomType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glBindTexture(int target, int id) {
/*  75 */     GL11.glBindTexture(target, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glBlendFunc(int src, int dest) {
/*  82 */     GL11.glBlendFunc(src, dest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glCallList(int id) {
/*  89 */     GL11.glCallList(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glClear(int value) {
/*  96 */     GL11.glClear(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glClearColor(float red, float green, float blue, float alpha) {
/* 103 */     GL11.glClearColor(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glClipPlane(int plane, DoubleBuffer buffer) {
/* 110 */     GL11.glClipPlane(plane, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glColor4f(float r, float g, float b, float a) {
/* 117 */     a *= this.alphaScale;
/*     */     
/* 119 */     this.current[0] = r;
/* 120 */     this.current[1] = g;
/* 121 */     this.current[2] = b;
/* 122 */     this.current[3] = a;
/*     */     
/* 124 */     GL11.glColor4f(r, g, b, a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
/* 131 */     GL11.glColorMask(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border) {
/* 138 */     GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glDeleteTextures(IntBuffer buffer) {
/* 145 */     GL11.glDeleteTextures(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glDisable(int item) {
/* 152 */     GL11.glDisable(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glEnable(int item) {
/* 159 */     GL11.glEnable(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glEnd() {
/* 166 */     GL11.glEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glEndList() {
/* 173 */     GL11.glEndList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int glGenLists(int count) {
/* 180 */     return GL11.glGenLists(count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glGetFloat(int id, FloatBuffer ret) {
/* 187 */     GL11.glGetFloat(id, ret);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glGetInteger(int id, IntBuffer ret) {
/* 194 */     GL11.glGetInteger(id, ret);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixels) {
/* 201 */     GL11.glGetTexImage(target, level, format, type, pixels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glLineWidth(float width) {
/* 208 */     GL11.glLineWidth(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glLoadIdentity() {
/* 215 */     GL11.glLoadIdentity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glNewList(int id, int option) {
/* 222 */     GL11.glNewList(id, option);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glPointSize(float size) {
/* 229 */     GL11.glPointSize(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glPopMatrix() {
/* 236 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glPushMatrix() {
/* 243 */     GL11.glPushMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) {
/* 250 */     GL11.glReadPixels(x, y, width, height, format, type, pixels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glRotatef(float angle, float x, float y, float z) {
/* 257 */     GL11.glRotatef(angle, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glScalef(float x, float y, float z) {
/* 264 */     GL11.glScalef(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glScissor(int x, int y, int width, int height) {
/* 271 */     GL11.glScissor(x, y, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glTexCoord2f(float u, float v) {
/* 278 */     GL11.glTexCoord2f(u, v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glTexEnvi(int target, int mode, int value) {
/* 285 */     GL11.glTexEnvi(target, mode, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glTranslatef(float x, float y, float z) {
/* 292 */     GL11.glTranslatef(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glVertex2f(float x, float y) {
/* 299 */     GL11.glVertex2f(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glVertex3f(float x, float y, float z) {
/* 306 */     GL11.glVertex3f(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glTexParameteri(int target, int param, int value) {
/* 319 */     GL11.glTexParameteri(target, param, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getCurrentColor() {
/* 326 */     return this.current;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glDeleteLists(int list, int count) {
/* 333 */     GL11.glDeleteLists(list, count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glClearDepth(float value) {
/* 340 */     GL11.glClearDepth(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glDepthFunc(int func) {
/* 347 */     GL11.glDepthFunc(func);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glDepthMask(boolean mask) {
/* 354 */     GL11.glDepthMask(mask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlobalAlphaScale(float alphaScale) {
/* 361 */     this.alphaScale = alphaScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glLoadMatrix(FloatBuffer buffer) {
/* 368 */     GL11.glLoadMatrix(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glGenTextures(IntBuffer ids) {
/* 376 */     GL11.glGenTextures(ids);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glGetError() {
/* 384 */     GL11.glGetError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glTexImage2D(int target, int i, int dstPixelFormat, int width, int height, int j, int srcPixelFormat, int glUnsignedByte, ByteBuffer textureBuffer) {
/* 394 */     GL11.glTexImage2D(target, i, dstPixelFormat, width, height, j, srcPixelFormat, glUnsignedByte, textureBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void glTexSubImage2D(int glTexture2d, int i, int pageX, int pageY, int width, int height, int glBgra, int glUnsignedByte, ByteBuffer scratchByteBuffer) {
/* 400 */     GL11.glTexSubImage2D(glTexture2d, i, pageX, pageY, width, height, glBgra, glUnsignedByte, scratchByteBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTextureMirrorClamp() {
/* 408 */     return (GLContext.getCapabilities()).GL_EXT_texture_mirror_clamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSecondaryColor() {
/* 416 */     return (GLContext.getCapabilities()).GL_EXT_secondary_color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glSecondaryColor3ubEXT(byte b, byte c, byte d) {
/* 424 */     EXTSecondaryColor.glSecondaryColor3ubEXT(b, c, d);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\renderer\ImmediateModeOGLRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */