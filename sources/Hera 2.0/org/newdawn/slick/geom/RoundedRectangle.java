/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.newdawn.slick.util.FastTrig;
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
/*     */ public class RoundedRectangle
/*     */   extends Rectangle
/*     */ {
/*     */   public static final int TOP_LEFT = 1;
/*     */   public static final int TOP_RIGHT = 2;
/*     */   public static final int BOTTOM_RIGHT = 4;
/*     */   public static final int BOTTOM_LEFT = 8;
/*     */   public static final int ALL = 15;
/*     */   private static final int DEFAULT_SEGMENT_COUNT = 25;
/*     */   private float cornerRadius;
/*     */   private int segmentCount;
/*     */   private int cornerFlags;
/*     */   
/*     */   public RoundedRectangle(float x, float y, float width, float height, float cornerRadius) {
/*  45 */     this(x, y, width, height, cornerRadius, 25);
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
/*     */   public RoundedRectangle(float x, float y, float width, float height, float cornerRadius, int segmentCount) {
/*  59 */     this(x, y, width, height, cornerRadius, segmentCount, 15);
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
/*     */   public RoundedRectangle(float x, float y, float width, float height, float cornerRadius, int segmentCount, int cornerFlags) {
/*  75 */     super(x, y, width, height);
/*     */     
/*  77 */     if (cornerRadius < 0.0F) {
/*  78 */       throw new IllegalArgumentException("corner radius must be >= 0");
/*     */     }
/*  80 */     this.x = x;
/*  81 */     this.y = y;
/*  82 */     this.width = width;
/*  83 */     this.height = height;
/*  84 */     this.cornerRadius = cornerRadius;
/*  85 */     this.segmentCount = segmentCount;
/*  86 */     this.pointsDirty = true;
/*  87 */     this.cornerFlags = cornerFlags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCornerRadius() {
/*  96 */     return this.cornerRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCornerRadius(float cornerRadius) {
/* 105 */     if (cornerRadius >= 0.0F && 
/* 106 */       cornerRadius != this.cornerRadius) {
/* 107 */       this.cornerRadius = cornerRadius;
/* 108 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 119 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(float height) {
/* 128 */     if (this.height != height) {
/* 129 */       this.height = height;
/* 130 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 140 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/* 149 */     if (width != this.width) {
/* 150 */       this.width = width;
/* 151 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void createPoints() {
/* 156 */     this.maxX = this.x + this.width;
/* 157 */     this.maxY = this.y + this.height;
/* 158 */     this.minX = this.x;
/* 159 */     this.minY = this.y;
/* 160 */     float useWidth = this.width - 1.0F;
/* 161 */     float useHeight = this.height - 1.0F;
/* 162 */     if (this.cornerRadius == 0.0F) {
/* 163 */       this.points = new float[8];
/*     */       
/* 165 */       this.points[0] = this.x;
/* 166 */       this.points[1] = this.y;
/*     */       
/* 168 */       this.points[2] = this.x + useWidth;
/* 169 */       this.points[3] = this.y;
/*     */       
/* 171 */       this.points[4] = this.x + useWidth;
/* 172 */       this.points[5] = this.y + useHeight;
/*     */       
/* 174 */       this.points[6] = this.x;
/* 175 */       this.points[7] = this.y + useHeight;
/*     */     } else {
/*     */       
/* 178 */       float doubleRadius = this.cornerRadius * 2.0F;
/* 179 */       if (doubleRadius > useWidth) {
/* 180 */         doubleRadius = useWidth;
/* 181 */         this.cornerRadius = doubleRadius / 2.0F;
/*     */       } 
/* 183 */       if (doubleRadius > useHeight) {
/* 184 */         doubleRadius = useHeight;
/* 185 */         this.cornerRadius = doubleRadius / 2.0F;
/*     */       } 
/*     */       
/* 188 */       ArrayList<Float> tempPoints = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       if ((this.cornerFlags & 0x1) != 0) {
/* 194 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + this.cornerRadius, 180.0F, 270.0F));
/*     */       } else {
/* 196 */         tempPoints.add(new Float(this.x));
/* 197 */         tempPoints.add(new Float(this.y));
/*     */       } 
/*     */ 
/*     */       
/* 201 */       if ((this.cornerFlags & 0x2) != 0) {
/* 202 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + useWidth - this.cornerRadius, this.y + this.cornerRadius, 270.0F, 360.0F));
/*     */       } else {
/* 204 */         tempPoints.add(new Float(this.x + useWidth));
/* 205 */         tempPoints.add(new Float(this.y));
/*     */       } 
/*     */ 
/*     */       
/* 209 */       if ((this.cornerFlags & 0x4) != 0) {
/* 210 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + useWidth - this.cornerRadius, this.y + useHeight - this.cornerRadius, 0.0F, 90.0F));
/*     */       } else {
/* 212 */         tempPoints.add(new Float(this.x + useWidth));
/* 213 */         tempPoints.add(new Float(this.y + useHeight));
/*     */       } 
/*     */ 
/*     */       
/* 217 */       if ((this.cornerFlags & 0x8) != 0) {
/* 218 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + useHeight - this.cornerRadius, 90.0F, 180.0F));
/*     */       } else {
/* 220 */         tempPoints.add(new Float(this.x));
/* 221 */         tempPoints.add(new Float(this.y + useHeight));
/*     */       } 
/*     */       
/* 224 */       this.points = new float[tempPoints.size()];
/* 225 */       for (int i = 0; i < tempPoints.size(); i++) {
/* 226 */         this.points[i] = ((Float)tempPoints.get(i)).floatValue();
/*     */       }
/*     */     } 
/*     */     
/* 230 */     findCenter();
/* 231 */     calculateRadius();
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
/*     */   private List createPoints(int numberOfSegments, float radius, float cx, float cy, float start, float end) {
/* 246 */     ArrayList<Float> tempPoints = new ArrayList();
/*     */     
/* 248 */     int step = 360 / numberOfSegments;
/*     */     float a;
/* 250 */     for (a = start; a <= end + step; a += step) {
/* 251 */       float ang = a;
/* 252 */       if (ang > end) {
/* 253 */         ang = end;
/*     */       }
/* 255 */       float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * radius);
/* 256 */       float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * radius);
/*     */       
/* 258 */       tempPoints.add(new Float(x));
/* 259 */       tempPoints.add(new Float(y));
/*     */     } 
/*     */     
/* 262 */     return tempPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 272 */     checkPoints();
/*     */     
/* 274 */     Polygon resultPolygon = new Polygon();
/*     */     
/* 276 */     float[] result = new float[this.points.length];
/* 277 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 278 */     resultPolygon.points = result;
/* 279 */     resultPolygon.findCenter();
/*     */     
/* 281 */     return resultPolygon;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\RoundedRectangle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */