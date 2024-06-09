/*     */ package org.newdawn.slick.opengl.renderer;
/*     */ 
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class VAOGLRenderer
/*     */   extends ImmediateModeOGLRenderer
/*     */ {
/*     */   private static final int TOLERANCE = 20;
/*     */   public static final int NONE = -1;
/*     */   public static final int MAX_VERTS = 5000;
/*  24 */   private int currentType = -1;
/*     */   
/*  26 */   private float[] color = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/*     */   
/*  28 */   private float[] tex = new float[] { 0.0F, 0.0F };
/*     */ 
/*     */   
/*     */   private int vertIndex;
/*     */   
/*  33 */   private float[] verts = new float[15000];
/*     */   
/*  35 */   private float[] cols = new float[20000];
/*     */   
/*  37 */   private float[] texs = new float[15000];
/*     */ 
/*     */   
/*  40 */   private FloatBuffer vertices = BufferUtils.createFloatBuffer(15000);
/*     */   
/*  42 */   private FloatBuffer colors = BufferUtils.createFloatBuffer(20000);
/*     */   
/*  44 */   private FloatBuffer textures = BufferUtils.createFloatBuffer(10000);
/*     */ 
/*     */   
/*  47 */   private int listMode = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDisplay(int width, int height) {
/*  53 */     super.initDisplay(width, height);
/*     */     
/*  55 */     startBuffer();
/*  56 */     GL11.glEnableClientState(32884);
/*  57 */     GL11.glEnableClientState(32888);
/*  58 */     GL11.glEnableClientState(32886);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startBuffer() {
/*  65 */     this.vertIndex = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushBuffer() {
/*  72 */     if (this.vertIndex == 0) {
/*     */       return;
/*     */     }
/*  75 */     if (this.currentType == -1) {
/*     */       return;
/*     */     }
/*     */     
/*  79 */     if (this.vertIndex < 20) {
/*  80 */       GL11.glBegin(this.currentType);
/*  81 */       for (int i = 0; i < this.vertIndex; i++) {
/*  82 */         GL11.glColor4f(this.cols[i * 4 + 0], this.cols[i * 4 + 1], this.cols[i * 4 + 2], this.cols[i * 4 + 3]);
/*  83 */         GL11.glTexCoord2f(this.texs[i * 2 + 0], this.texs[i * 2 + 1]);
/*  84 */         GL11.glVertex3f(this.verts[i * 3 + 0], this.verts[i * 3 + 1], this.verts[i * 3 + 2]);
/*     */       } 
/*  86 */       GL11.glEnd();
/*  87 */       this.currentType = -1;
/*     */       return;
/*     */     } 
/*  90 */     this.vertices.clear();
/*  91 */     this.colors.clear();
/*  92 */     this.textures.clear();
/*     */     
/*  94 */     this.vertices.put(this.verts, 0, this.vertIndex * 3);
/*  95 */     this.colors.put(this.cols, 0, this.vertIndex * 4);
/*  96 */     this.textures.put(this.texs, 0, this.vertIndex * 2);
/*     */     
/*  98 */     this.vertices.flip();
/*  99 */     this.colors.flip();
/* 100 */     this.textures.flip();
/*     */     
/* 102 */     GL11.glVertexPointer(3, 0, this.vertices);
/* 103 */     GL11.glColorPointer(4, 0, this.colors);
/* 104 */     GL11.glTexCoordPointer(2, 0, this.textures);
/*     */     
/* 106 */     GL11.glDrawArrays(this.currentType, 0, this.vertIndex);
/* 107 */     this.currentType = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyBuffer() {
/* 114 */     if (this.listMode > 0) {
/*     */       return;
/*     */     }
/*     */     
/* 118 */     if (this.vertIndex != 0) {
/* 119 */       flushBuffer();
/* 120 */       startBuffer();
/*     */     } 
/*     */     
/* 123 */     super.glColor4f(this.color[0], this.color[1], this.color[2], this.color[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 130 */     super.flush();
/*     */     
/* 132 */     applyBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glBegin(int geomType) {
/* 139 */     if (this.listMode > 0) {
/* 140 */       super.glBegin(geomType);
/*     */       
/*     */       return;
/*     */     } 
/* 144 */     if (this.currentType != geomType) {
/* 145 */       applyBuffer();
/* 146 */       this.currentType = geomType;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glColor4f(float r, float g, float b, float a) {
/* 154 */     a *= this.alphaScale;
/*     */     
/* 156 */     this.color[0] = r;
/* 157 */     this.color[1] = g;
/* 158 */     this.color[2] = b;
/* 159 */     this.color[3] = a;
/*     */     
/* 161 */     if (this.listMode > 0) {
/* 162 */       super.glColor4f(r, g, b, a);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glEnd() {
/* 171 */     if (this.listMode > 0) {
/* 172 */       super.glEnd();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glTexCoord2f(float u, float v) {
/* 181 */     if (this.listMode > 0) {
/* 182 */       super.glTexCoord2f(u, v);
/*     */       
/*     */       return;
/*     */     } 
/* 186 */     this.tex[0] = u;
/* 187 */     this.tex[1] = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glVertex2f(float x, float y) {
/* 194 */     if (this.listMode > 0) {
/* 195 */       super.glVertex2f(x, y);
/*     */       
/*     */       return;
/*     */     } 
/* 199 */     glVertex3f(x, y, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glVertex3f(float x, float y, float z) {
/* 206 */     if (this.listMode > 0) {
/* 207 */       super.glVertex3f(x, y, z);
/*     */       
/*     */       return;
/*     */     } 
/* 211 */     this.verts[this.vertIndex * 3 + 0] = x;
/* 212 */     this.verts[this.vertIndex * 3 + 1] = y;
/* 213 */     this.verts[this.vertIndex * 3 + 2] = z;
/* 214 */     this.cols[this.vertIndex * 4 + 0] = this.color[0];
/* 215 */     this.cols[this.vertIndex * 4 + 1] = this.color[1];
/* 216 */     this.cols[this.vertIndex * 4 + 2] = this.color[2];
/* 217 */     this.cols[this.vertIndex * 4 + 3] = this.color[3];
/* 218 */     this.texs[this.vertIndex * 2 + 0] = this.tex[0];
/* 219 */     this.texs[this.vertIndex * 2 + 1] = this.tex[1];
/* 220 */     this.vertIndex++;
/*     */     
/* 222 */     if (this.vertIndex > 4950 && 
/* 223 */       isSplittable(this.vertIndex, this.currentType)) {
/* 224 */       int type = this.currentType;
/* 225 */       applyBuffer();
/* 226 */       this.currentType = type;
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
/*     */   private boolean isSplittable(int count, int type) {
/* 239 */     switch (type) {
/*     */       case 7:
/* 241 */         return (count % 4 == 0);
/*     */       case 4:
/* 243 */         return (count % 3 == 0);
/*     */       case 6913:
/* 245 */         return (count % 2 == 0);
/*     */     } 
/*     */     
/* 248 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glBindTexture(int target, int id) {
/* 255 */     applyBuffer();
/* 256 */     super.glBindTexture(target, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glBlendFunc(int src, int dest) {
/* 263 */     applyBuffer();
/* 264 */     super.glBlendFunc(src, dest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glCallList(int id) {
/* 271 */     applyBuffer();
/* 272 */     super.glCallList(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glClear(int value) {
/* 279 */     applyBuffer();
/* 280 */     super.glClear(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glClipPlane(int plane, DoubleBuffer buffer) {
/* 287 */     applyBuffer();
/* 288 */     super.glClipPlane(plane, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
/* 295 */     applyBuffer();
/* 296 */     super.glColorMask(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glDisable(int item) {
/* 303 */     applyBuffer();
/* 304 */     super.glDisable(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glEnable(int item) {
/* 311 */     applyBuffer();
/* 312 */     super.glEnable(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glLineWidth(float width) {
/* 319 */     applyBuffer();
/* 320 */     super.glLineWidth(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glPointSize(float size) {
/* 327 */     applyBuffer();
/* 328 */     super.glPointSize(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glPopMatrix() {
/* 335 */     applyBuffer();
/* 336 */     super.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glPushMatrix() {
/* 343 */     applyBuffer();
/* 344 */     super.glPushMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glRotatef(float angle, float x, float y, float z) {
/* 351 */     applyBuffer();
/* 352 */     super.glRotatef(angle, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glScalef(float x, float y, float z) {
/* 359 */     applyBuffer();
/* 360 */     super.glScalef(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glScissor(int x, int y, int width, int height) {
/* 367 */     applyBuffer();
/* 368 */     super.glScissor(x, y, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glTexEnvi(int target, int mode, int value) {
/* 375 */     applyBuffer();
/* 376 */     super.glTexEnvi(target, mode, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glTranslatef(float x, float y, float z) {
/* 383 */     applyBuffer();
/* 384 */     super.glTranslatef(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glEndList() {
/* 391 */     this.listMode--;
/* 392 */     super.glEndList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glNewList(int id, int option) {
/* 399 */     this.listMode++;
/* 400 */     super.glNewList(id, option);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getCurrentColor() {
/* 407 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glLoadMatrix(FloatBuffer buffer) {
/* 414 */     flushBuffer();
/* 415 */     super.glLoadMatrix(buffer);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\renderer\VAOGLRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */