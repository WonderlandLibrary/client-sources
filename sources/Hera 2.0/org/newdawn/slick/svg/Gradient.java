/*     */ package org.newdawn.slick.svg;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.ImageBuffer;
/*     */ import org.newdawn.slick.geom.Transform;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Gradient
/*     */ {
/*     */   private String name;
/*  19 */   private ArrayList steps = new ArrayList();
/*     */ 
/*     */   
/*     */   private float x1;
/*     */ 
/*     */   
/*     */   private float x2;
/*     */ 
/*     */   
/*     */   private float y1;
/*     */ 
/*     */   
/*     */   private float y2;
/*     */ 
/*     */   
/*     */   private float r;
/*     */   
/*     */   private Image image;
/*     */   
/*     */   private boolean radial;
/*     */   
/*     */   private Transform transform;
/*     */   
/*     */   private String ref;
/*     */ 
/*     */   
/*     */   public Gradient(String name, boolean radial) {
/*  46 */     this.name = name;
/*  47 */     this.radial = radial;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRadial() {
/*  56 */     return this.radial;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransform(Transform trans) {
/*  65 */     this.transform = trans;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform getTransform() {
/*  74 */     return this.transform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reference(String ref) {
/*  83 */     this.ref = ref;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resolve(Diagram diagram) {
/*  92 */     if (this.ref == null) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     Gradient other = diagram.getGradient(this.ref);
/*     */     
/*  98 */     for (int i = 0; i < other.steps.size(); i++) {
/*  99 */       this.steps.add(other.steps.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void genImage() {
/* 107 */     if (this.image == null) {
/* 108 */       ImageBuffer buffer = new ImageBuffer(128, 16);
/* 109 */       for (int i = 0; i < 128; i++) {
/* 110 */         Color col = getColorAt(i / 128.0F);
/* 111 */         for (int j = 0; j < 16; j++) {
/* 112 */           buffer.setRGBA(i, j, col.getRedByte(), col.getGreenByte(), col.getBlueByte(), col.getAlphaByte());
/*     */         }
/*     */       } 
/* 115 */       this.image = buffer.getImage();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage() {
/* 125 */     genImage();
/*     */     
/* 127 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setR(float r) {
/* 136 */     this.r = r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX1(float x1) {
/* 145 */     this.x1 = x1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX2(float x2) {
/* 154 */     this.x2 = x2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY1(float y1) {
/* 163 */     this.y1 = y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY2(float y2) {
/* 172 */     this.y2 = y2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getR() {
/* 181 */     return this.r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX1() {
/* 190 */     return this.x1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX2() {
/* 199 */     return this.x2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY1() {
/* 208 */     return this.y1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY2() {
/* 217 */     return this.y2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStep(float location, Color c) {
/* 227 */     this.steps.add(new Step(location, c));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColorAt(float p) {
/* 237 */     if (p <= 0.0F) {
/* 238 */       return ((Step)this.steps.get(0)).col;
/*     */     }
/* 240 */     if (p > 1.0F) {
/* 241 */       return ((Step)this.steps.get(this.steps.size() - 1)).col;
/*     */     }
/*     */     
/* 244 */     for (int i = 1; i < this.steps.size(); i++) {
/* 245 */       Step prev = this.steps.get(i - 1);
/* 246 */       Step current = this.steps.get(i);
/*     */       
/* 248 */       if (p <= current.location) {
/* 249 */         float dis = current.location - prev.location;
/* 250 */         p -= prev.location;
/* 251 */         float v = p / dis;
/*     */         
/* 253 */         Color c = new Color(1, 1, 1, 1);
/* 254 */         c.a = prev.col.a * (1.0F - v) + current.col.a * v;
/* 255 */         c.r = prev.col.r * (1.0F - v) + current.col.r * v;
/* 256 */         c.g = prev.col.g * (1.0F - v) + current.col.g * v;
/* 257 */         c.b = prev.col.b * (1.0F - v) + current.col.b * v;
/*     */         
/* 259 */         return c;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 264 */     return Color.black;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Step
/*     */   {
/*     */     float location;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Color col;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Step(float location, Color c) {
/* 285 */       this.location = location;
/* 286 */       this.col = c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\Gradient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */