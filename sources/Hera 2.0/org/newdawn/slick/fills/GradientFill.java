/*     */ package org.newdawn.slick.fills;
/*     */ 
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.ShapeFill;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ import org.newdawn.slick.geom.Vector2f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GradientFill
/*     */   implements ShapeFill
/*     */ {
/*  19 */   private Vector2f none = new Vector2f(0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector2f start;
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector2f end;
/*     */ 
/*     */ 
/*     */   
/*     */   private Color startCol;
/*     */ 
/*     */ 
/*     */   
/*     */   private Color endCol;
/*     */ 
/*     */   
/*     */   private boolean local = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientFill(float sx, float sy, Color startCol, float ex, float ey, Color endCol) {
/*  43 */     this(sx, sy, startCol, ex, ey, endCol, false);
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
/*     */   
/*     */   public GradientFill(float sx, float sy, Color startCol, float ex, float ey, Color endCol, boolean local) {
/*  59 */     this(new Vector2f(sx, sy), startCol, new Vector2f(ex, ey), endCol, local);
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
/*     */   public GradientFill(Vector2f start, Color startCol, Vector2f end, Color endCol, boolean local) {
/*  72 */     this.start = new Vector2f(start);
/*  73 */     this.end = new Vector2f(end);
/*  74 */     this.startCol = new Color(startCol);
/*  75 */     this.endCol = new Color(endCol);
/*  76 */     this.local = local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientFill getInvertedCopy() {
/*  85 */     return new GradientFill(this.start, this.endCol, this.end, this.startCol, this.local);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocal(boolean local) {
/*  94 */     this.local = local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f getStart() {
/* 103 */     return this.start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f getEnd() {
/* 112 */     return this.end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getStartColor() {
/* 121 */     return this.startCol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getEndColor() {
/* 130 */     return this.endCol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStart(float x, float y) {
/* 140 */     setStart(new Vector2f(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStart(Vector2f start) {
/* 149 */     this.start = new Vector2f(start);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnd(float x, float y) {
/* 159 */     setEnd(new Vector2f(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnd(Vector2f end) {
/* 168 */     this.end = new Vector2f(end);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStartColor(Color color) {
/* 177 */     this.startCol = new Color(color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEndColor(Color color) {
/* 186 */     this.endCol = new Color(color);
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
/*     */   public Color colorAt(Shape shape, float x, float y) {
/* 198 */     if (this.local) {
/* 199 */       return colorAt(x - shape.getCenterX(), y - shape.getCenterY());
/*     */     }
/* 201 */     return colorAt(x, y);
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
/*     */   public Color colorAt(float x, float y) {
/* 213 */     float dx1 = this.end.getX() - this.start.getX();
/* 214 */     float dy1 = this.end.getY() - this.start.getY();
/*     */     
/* 216 */     float dx2 = -dy1;
/* 217 */     float dy2 = dx1;
/* 218 */     float denom = dy2 * dx1 - dx2 * dy1;
/*     */     
/* 220 */     if (denom == 0.0F) {
/* 221 */       return Color.black;
/*     */     }
/*     */     
/* 224 */     float ua = dx2 * (this.start.getY() - y) - dy2 * (this.start.getX() - x);
/* 225 */     ua /= denom;
/* 226 */     float ub = dx1 * (this.start.getY() - y) - dy1 * (this.start.getX() - x);
/* 227 */     ub /= denom;
/* 228 */     float u = ua;
/* 229 */     if (u < 0.0F) {
/* 230 */       u = 0.0F;
/*     */     }
/* 232 */     if (u > 1.0F) {
/* 233 */       u = 1.0F;
/*     */     }
/* 235 */     float v = 1.0F - u;
/*     */ 
/*     */     
/* 238 */     Color col = new Color(1, 1, 1, 1);
/* 239 */     col.r = u * this.endCol.r + v * this.startCol.r;
/* 240 */     col.b = u * this.endCol.b + v * this.startCol.b;
/* 241 */     col.g = u * this.endCol.g + v * this.startCol.g;
/* 242 */     col.a = u * this.endCol.a + v * this.startCol.a;
/*     */     
/* 244 */     return col;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f getOffsetAt(Shape shape, float x, float y) {
/* 251 */     return this.none;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\fills\GradientFill.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */