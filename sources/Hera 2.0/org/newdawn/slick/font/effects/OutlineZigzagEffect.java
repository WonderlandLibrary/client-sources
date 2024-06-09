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
/*     */ public class OutlineZigzagEffect
/*     */   extends OutlineEffect
/*     */ {
/*  35 */   private float amplitude = 1.0F;
/*     */   
/*  37 */   private float wavelength = 3.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutlineZigzagEffect() {
/*  43 */     setStroke(new ZigzagStroke());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWavelength() {
/*  52 */     return this.wavelength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWavelength(float wavelength) {
/*  61 */     this.wavelength = wavelength;
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
/*     */   public OutlineZigzagEffect(int width, Color color) {
/*  89 */     super(width, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  96 */     return "Outline (Zigzag)";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getValues() {
/* 103 */     List<ConfigurableEffect.Value> values = super.getValues();
/* 104 */     values.add(EffectUtil.floatValue("Wavelength", this.wavelength, 1.0F, 100.0F, "This setting controls the wavelength of the outline. The smaller the value, the more segments will be used to draw the outline."));
/*     */     
/* 106 */     values.add(EffectUtil.floatValue("Amplitude", this.amplitude, 0.5F, 50.0F, "This setting controls the amplitude of the outline. The bigger the value, the more the zigzags will vary."));
/*     */     
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
/* 118 */       if (value.getName().equals("Wavelength")) {
/* 119 */         this.wavelength = ((Float)value.getObject()).floatValue(); continue;
/* 120 */       }  if (value.getName().equals("Amplitude")) {
/* 121 */         this.amplitude = ((Float)value.getObject()).floatValue();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class ZigzagStroke
/*     */     implements Stroke
/*     */   {
/*     */     private static final float FLATNESS = 1.0F;
/*     */ 
/*     */ 
/*     */     
/*     */     private ZigzagStroke() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Shape createStrokedShape(Shape shape) {
/* 140 */       GeneralPath result = new GeneralPath();
/* 141 */       PathIterator it = new FlatteningPathIterator(shape.getPathIterator(null), 1.0D);
/* 142 */       float[] points = new float[6];
/* 143 */       float moveX = 0.0F, moveY = 0.0F;
/* 144 */       float lastX = 0.0F, lastY = 0.0F;
/* 145 */       float thisX = 0.0F, thisY = 0.0F;
/* 146 */       int type = 0;
/* 147 */       float next = 0.0F;
/* 148 */       int phase = 0;
/* 149 */       while (!it.isDone()) {
/* 150 */         float dx, dy, distance; type = it.currentSegment(points);
/* 151 */         switch (type) {
/*     */           case 0:
/* 153 */             moveX = lastX = points[0];
/* 154 */             moveY = lastY = points[1];
/* 155 */             result.moveTo(moveX, moveY);
/* 156 */             next = OutlineZigzagEffect.this.wavelength / 2.0F;
/*     */             break;
/*     */           
/*     */           case 4:
/* 160 */             points[0] = moveX;
/* 161 */             points[1] = moveY;
/*     */ 
/*     */           
/*     */           case 1:
/* 165 */             thisX = points[0];
/* 166 */             thisY = points[1];
/* 167 */             dx = thisX - lastX;
/* 168 */             dy = thisY - lastY;
/* 169 */             distance = (float)Math.sqrt((dx * dx + dy * dy));
/* 170 */             if (distance >= next) {
/* 171 */               float r = 1.0F / distance;
/* 172 */               while (distance >= next) {
/* 173 */                 float x = lastX + next * dx * r;
/* 174 */                 float y = lastY + next * dy * r;
/* 175 */                 if ((phase & 0x1) == 0) {
/* 176 */                   result.lineTo(x + OutlineZigzagEffect.this.amplitude * dy * r, y - OutlineZigzagEffect.this.amplitude * dx * r);
/*     */                 } else {
/* 178 */                   result.lineTo(x - OutlineZigzagEffect.this.amplitude * dy * r, y + OutlineZigzagEffect.this.amplitude * dx * r);
/* 179 */                 }  next += OutlineZigzagEffect.this.wavelength;
/* 180 */                 phase++;
/*     */               } 
/*     */             } 
/* 183 */             next -= distance;
/* 184 */             lastX = thisX;
/* 185 */             lastY = thisY;
/* 186 */             if (type == 4) result.closePath(); 
/*     */             break;
/*     */         } 
/* 189 */         it.next();
/*     */       } 
/* 191 */       return (new BasicStroke(OutlineZigzagEffect.this.getWidth(), 2, OutlineZigzagEffect.this.getJoin())).createStrokedShape(result);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\effects\OutlineZigzagEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */