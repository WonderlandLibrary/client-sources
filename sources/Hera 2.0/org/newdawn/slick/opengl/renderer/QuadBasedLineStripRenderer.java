/*     */ package org.newdawn.slick.opengl.renderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuadBasedLineStripRenderer
/*     */   implements LineStripRenderer
/*     */ {
/*  10 */   private SGL GL = Renderer.get();
/*     */ 
/*     */   
/*  13 */   public static int MAX_POINTS = 10000;
/*     */   
/*     */   private boolean antialias;
/*     */   
/*  17 */   private float width = 1.0F;
/*     */ 
/*     */   
/*     */   private float[] points;
/*     */   
/*     */   private float[] colours;
/*     */   
/*     */   private int pts;
/*     */   
/*     */   private int cpt;
/*     */   
/*  28 */   private DefaultLineStripRenderer def = new DefaultLineStripRenderer();
/*     */ 
/*     */   
/*     */   private boolean renderHalf;
/*     */ 
/*     */   
/*     */   private boolean lineCaps = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public QuadBasedLineStripRenderer() {
/*  39 */     this.points = new float[MAX_POINTS * 2];
/*  40 */     this.colours = new float[MAX_POINTS * 4];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineCaps(boolean caps) {
/*  49 */     this.lineCaps = caps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  56 */     if (this.width == 1.0F) {
/*  57 */       this.def.start();
/*     */       
/*     */       return;
/*     */     } 
/*  61 */     this.pts = 0;
/*  62 */     this.cpt = 0;
/*  63 */     this.GL.flush();
/*     */     
/*  65 */     float[] col = this.GL.getCurrentColor();
/*  66 */     color(col[0], col[1], col[2], col[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void end() {
/*  73 */     if (this.width == 1.0F) {
/*  74 */       this.def.end();
/*     */       
/*     */       return;
/*     */     } 
/*  78 */     renderLines(this.points, this.pts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void vertex(float x, float y) {
/*  85 */     if (this.width == 1.0F) {
/*  86 */       this.def.vertex(x, y);
/*     */       
/*     */       return;
/*     */     } 
/*  90 */     this.points[this.pts * 2] = x;
/*  91 */     this.points[this.pts * 2 + 1] = y;
/*  92 */     this.pts++;
/*     */     
/*  94 */     int index = this.pts - 1;
/*  95 */     color(this.colours[index * 4], this.colours[index * 4 + 1], this.colours[index * 4 + 2], this.colours[index * 4 + 3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/* 102 */     this.width = width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAntiAlias(boolean antialias) {
/* 109 */     this.def.setAntiAlias(antialias);
/* 110 */     this.antialias = antialias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLines(float[] points, int count) {
/* 120 */     if (this.antialias) {
/* 121 */       this.GL.glEnable(2881);
/* 122 */       renderLinesImpl(points, count, this.width + 1.0F);
/*     */     } 
/*     */     
/* 125 */     this.GL.glDisable(2881);
/* 126 */     renderLinesImpl(points, count, this.width);
/*     */     
/* 128 */     if (this.antialias) {
/* 129 */       this.GL.glEnable(2881);
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
/*     */   public void renderLinesImpl(float[] points, int count, float w) {
/* 141 */     float width = w / 2.0F;
/*     */     
/* 143 */     float lastx1 = 0.0F;
/* 144 */     float lasty1 = 0.0F;
/* 145 */     float lastx2 = 0.0F;
/* 146 */     float lasty2 = 0.0F;
/*     */     
/* 148 */     this.GL.glBegin(7);
/* 149 */     for (int i = 0; i < count + 1; i++) {
/* 150 */       int current = i;
/* 151 */       int next = i + 1;
/* 152 */       int prev = i - 1;
/* 153 */       if (prev < 0) {
/* 154 */         prev += count;
/*     */       }
/* 156 */       if (next >= count) {
/* 157 */         next -= count;
/*     */       }
/* 159 */       if (current >= count) {
/* 160 */         current -= count;
/*     */       }
/*     */       
/* 163 */       float x1 = points[current * 2];
/* 164 */       float y1 = points[current * 2 + 1];
/* 165 */       float x2 = points[next * 2];
/* 166 */       float y2 = points[next * 2 + 1];
/*     */ 
/*     */       
/* 169 */       float dx = x2 - x1;
/* 170 */       float dy = y2 - y1;
/*     */       
/* 172 */       if (dx != 0.0F || dy != 0.0F) {
/*     */ 
/*     */ 
/*     */         
/* 176 */         float d2 = dx * dx + dy * dy;
/* 177 */         float d = (float)Math.sqrt(d2);
/* 178 */         dx *= width;
/* 179 */         dy *= width;
/* 180 */         dx /= d;
/* 181 */         dy /= d;
/*     */         
/* 183 */         float tx = dy;
/* 184 */         float ty = -dx;
/*     */         
/* 186 */         if (i != 0) {
/* 187 */           bindColor(prev);
/* 188 */           this.GL.glVertex3f(lastx1, lasty1, 0.0F);
/* 189 */           this.GL.glVertex3f(lastx2, lasty2, 0.0F);
/* 190 */           bindColor(current);
/* 191 */           this.GL.glVertex3f(x1 + tx, y1 + ty, 0.0F);
/* 192 */           this.GL.glVertex3f(x1 - tx, y1 - ty, 0.0F);
/*     */         } 
/*     */         
/* 195 */         lastx1 = x2 - tx;
/* 196 */         lasty1 = y2 - ty;
/* 197 */         lastx2 = x2 + tx;
/* 198 */         lasty2 = y2 + ty;
/*     */         
/* 200 */         if (i < count - 1) {
/* 201 */           bindColor(current);
/* 202 */           this.GL.glVertex3f(x1 + tx, y1 + ty, 0.0F);
/* 203 */           this.GL.glVertex3f(x1 - tx, y1 - ty, 0.0F);
/* 204 */           bindColor(next);
/* 205 */           this.GL.glVertex3f(x2 - tx, y2 - ty, 0.0F);
/* 206 */           this.GL.glVertex3f(x2 + tx, y2 + ty, 0.0F);
/*     */         } 
/*     */       } 
/*     */     } 
/* 210 */     this.GL.glEnd();
/*     */     
/* 212 */     float step = (width <= 12.5F) ? 5.0F : (180.0F / (float)Math.ceil(width / 2.5D));
/*     */ 
/*     */     
/* 215 */     if (this.lineCaps) {
/* 216 */       float dx = points[2] - points[0];
/* 217 */       float dy = points[3] - points[1];
/* 218 */       float fang = (float)Math.toDegrees(Math.atan2(dy, dx)) + 90.0F;
/*     */       
/* 220 */       if (dx != 0.0F || dy != 0.0F) {
/* 221 */         this.GL.glBegin(6);
/* 222 */         bindColor(0);
/* 223 */         this.GL.glVertex2f(points[0], points[1]); int j;
/* 224 */         for (j = 0; j < 180.0F + step; j = (int)(j + step)) {
/* 225 */           float ang = (float)Math.toRadians((fang + j));
/* 226 */           this.GL.glVertex2f(points[0] + (float)(Math.cos(ang) * width), points[1] + (float)(Math.sin(ang) * width));
/*     */         } 
/*     */         
/* 229 */         this.GL.glEnd();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 234 */     if (this.lineCaps) {
/* 235 */       float dx = points[count * 2 - 2] - points[count * 2 - 4];
/* 236 */       float dy = points[count * 2 - 1] - points[count * 2 - 3];
/* 237 */       float fang = (float)Math.toDegrees(Math.atan2(dy, dx)) - 90.0F;
/*     */       
/* 239 */       if (dx != 0.0F || dy != 0.0F) {
/* 240 */         this.GL.glBegin(6);
/* 241 */         bindColor(count - 1);
/* 242 */         this.GL.glVertex2f(points[count * 2 - 2], points[count * 2 - 1]); int j;
/* 243 */         for (j = 0; j < 180.0F + step; j = (int)(j + step)) {
/* 244 */           float ang = (float)Math.toRadians((fang + j));
/* 245 */           this.GL.glVertex2f(points[count * 2 - 2] + (float)(Math.cos(ang) * width), points[count * 2 - 1] + (float)(Math.sin(ang) * width));
/*     */         } 
/*     */         
/* 248 */         this.GL.glEnd();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void bindColor(int index) {
/* 259 */     if (index < this.cpt) {
/* 260 */       if (this.renderHalf) {
/* 261 */         this.GL.glColor4f(this.colours[index * 4] * 0.5F, this.colours[index * 4 + 1] * 0.5F, this.colours[index * 4 + 2] * 0.5F, this.colours[index * 4 + 3] * 0.5F);
/*     */       } else {
/*     */         
/* 264 */         this.GL.glColor4f(this.colours[index * 4], this.colours[index * 4 + 1], this.colours[index * 4 + 2], this.colours[index * 4 + 3]);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void color(float r, float g, float b, float a) {
/* 274 */     if (this.width == 1.0F) {
/* 275 */       this.def.color(r, g, b, a);
/*     */       
/*     */       return;
/*     */     } 
/* 279 */     this.colours[this.pts * 4] = r;
/* 280 */     this.colours[this.pts * 4 + 1] = g;
/* 281 */     this.colours[this.pts * 4 + 2] = b;
/* 282 */     this.colours[this.pts * 4 + 3] = a;
/* 283 */     this.cpt++;
/*     */   }
/*     */   
/*     */   public boolean applyGLLineFixes() {
/* 287 */     if (this.width == 1.0F) {
/* 288 */       return this.def.applyGLLineFixes();
/*     */     }
/*     */     
/* 291 */     return this.def.applyGLLineFixes();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\renderer\QuadBasedLineStripRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */