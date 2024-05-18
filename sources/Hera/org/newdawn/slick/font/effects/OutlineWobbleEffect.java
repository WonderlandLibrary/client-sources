/*     */ package org.newdawn.slick.font.effects;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.FlatteningPathIterator;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class OutlineWobbleEffect
/*     */   extends OutlineEffect
/*     */ {
/*  35 */   private float detail = 1.0F;
/*     */   
/*  37 */   private float amplitude = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutlineWobbleEffect() {
/*  43 */     setStroke(new WobbleStroke());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDetail() {
/*  52 */     return this.detail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDetail(float detail) {
/*  61 */     this.detail = detail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAmplitude() {
/*  70 */     return this.amplitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAmplitude(float amplitude) {
/*  79 */     this.amplitude = amplitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutlineWobbleEffect(int width, Color color) {
/*  89 */     super(width, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  96 */     return "Outline (Wobble)";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getValues() {
/* 103 */     List<ConfigurableEffect.Value> values = super.getValues();
/* 104 */     values.remove(2);
/* 105 */     values.add(EffectUtil.floatValue("Detail", this.detail, 1.0F, 50.0F, "This setting controls how detailed the outline will be. Smaller numbers cause the outline to have more detail."));
/*     */     
/* 107 */     values.add(EffectUtil.floatValue("Amplitude", this.amplitude, 0.5F, 50.0F, "This setting controls the amplitude of the outline."));
/* 108 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValues(List values) {
/* 115 */     super.setValues(values);
/* 116 */     for (Iterator<ConfigurableEffect.Value> iter = values.iterator(); iter.hasNext(); ) {
/* 117 */       ConfigurableEffect.Value value = iter.next();
/* 118 */       if (value.getName().equals("Detail")) {
/* 119 */         this.detail = ((Float)value.getObject()).floatValue(); continue;
/* 120 */       }  if (value.getName().equals("Amplitude")) {
/* 121 */         this.amplitude = ((Float)value.getObject()).floatValue();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class WobbleStroke
/*     */     implements Stroke
/*     */   {
/*     */     private static final float FLATNESS = 1.0F;
/*     */ 
/*     */ 
/*     */     
/*     */     private WobbleStroke() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Shape createStrokedShape(Shape shape) {
/* 140 */       GeneralPath result = new GeneralPath();
/* 141 */       shape = (new BasicStroke(OutlineWobbleEffect.this.getWidth(), 2, OutlineWobbleEffect.this.getJoin())).createStrokedShape(shape);
/* 142 */       PathIterator it = new FlatteningPathIterator(shape.getPathIterator(null), 1.0D);
/* 143 */       float[] points = new float[6];
/* 144 */       float moveX = 0.0F, moveY = 0.0F;
/* 145 */       float lastX = 0.0F, lastY = 0.0F;
/* 146 */       float thisX = 0.0F, thisY = 0.0F;
/* 147 */       int type = 0;
/* 148 */       float next = 0.0F;
/* 149 */       while (!it.isDone()) {
/* 150 */         float dx, dy, distance; type = it.currentSegment(points);
/* 151 */         switch (type) {
/*     */           case 0:
/* 153 */             moveX = lastX = randomize(points[0]);
/* 154 */             moveY = lastY = randomize(points[1]);
/* 155 */             result.moveTo(moveX, moveY);
/* 156 */             next = 0.0F;
/*     */             break;
/*     */           
/*     */           case 4:
/* 160 */             points[0] = moveX;
/* 161 */             points[1] = moveY;
/*     */ 
/*     */           
/*     */           case 1:
/* 165 */             thisX = randomize(points[0]);
/* 166 */             thisY = randomize(points[1]);
/* 167 */             dx = thisX - lastX;
/* 168 */             dy = thisY - lastY;
/* 169 */             distance = (float)Math.sqrt((dx * dx + dy * dy));
/* 170 */             if (distance >= next) {
/* 171 */               float r = 1.0F / distance;
/* 172 */               while (distance >= next) {
/* 173 */                 float x = lastX + next * dx * r;
/* 174 */                 float y = lastY + next * dy * r;
/* 175 */                 result.lineTo(randomize(x), randomize(y));
/* 176 */                 next += OutlineWobbleEffect.this.detail;
/*     */               } 
/*     */             } 
/* 179 */             next -= distance;
/* 180 */             lastX = thisX;
/* 181 */             lastY = thisY;
/*     */             break;
/*     */         } 
/* 184 */         it.next();
/*     */       } 
/*     */       
/* 187 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float randomize(float x) {
/* 197 */       return x + (float)Math.random() * OutlineWobbleEffect.this.amplitude * 2.0F - 1.0F;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\effects\OutlineWobbleEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */