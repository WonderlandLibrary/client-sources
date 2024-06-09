/*     */ package org.newdawn.slick.font.effects;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.newdawn.slick.UnicodeFont;
/*     */ import org.newdawn.slick.font.Glyph;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OutlineEffect
/*     */   implements ConfigurableEffect
/*     */ {
/*  23 */   private float width = 2.0F;
/*     */   
/*  25 */   private Color color = Color.black;
/*     */   
/*  27 */   private int join = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Stroke stroke;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutlineEffect() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutlineEffect(int width, Color color) {
/*  44 */     this.width = width;
/*  45 */     this.color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph) {
/*  52 */     g = (Graphics2D)g.create();
/*  53 */     if (this.stroke != null) {
/*  54 */       g.setStroke(this.stroke);
/*     */     } else {
/*  56 */       g.setStroke(getStroke());
/*  57 */     }  g.setColor(this.color);
/*  58 */     g.draw(glyph.getShape());
/*  59 */     g.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/*  68 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/*  78 */     this.width = width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/*  87 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/*  96 */     this.color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getJoin() {
/* 105 */     return this.join;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getStroke() {
/* 114 */     if (this.stroke == null) {
/* 115 */       return new BasicStroke(this.width, 2, this.join);
/*     */     }
/*     */     
/* 118 */     return this.stroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStroke(Stroke stroke) {
/* 128 */     this.stroke = stroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJoin(int join) {
/* 138 */     this.join = join;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 145 */     return "Outline";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getValues() {
/* 152 */     List<ConfigurableEffect.Value> values = new ArrayList();
/* 153 */     values.add(EffectUtil.colorValue("Color", this.color));
/* 154 */     values.add(EffectUtil.floatValue("Width", this.width, 0.1F, 999.0F, "This setting controls the width of the outline. The glyphs will need padding so the outline doesn't get clipped."));
/*     */     
/* 156 */     values.add(EffectUtil.optionValue("Join", String.valueOf(this.join), new String[][] { { "Bevel", "2" }, , { "Miter", "0" }, , { "Round", "1" },  }, "This setting defines how the corners of the outline are drawn. This is usually only noticeable at large outline widths."));
/*     */ 
/*     */ 
/*     */     
/* 160 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValues(List values) {
/* 167 */     for (Iterator<ConfigurableEffect.Value> iter = values.iterator(); iter.hasNext(); ) {
/* 168 */       ConfigurableEffect.Value value = iter.next();
/* 169 */       if (value.getName().equals("Color")) {
/* 170 */         this.color = (Color)value.getObject(); continue;
/* 171 */       }  if (value.getName().equals("Width")) {
/* 172 */         this.width = ((Float)value.getObject()).floatValue(); continue;
/* 173 */       }  if (value.getName().equals("Join"))
/* 174 */         this.join = Integer.parseInt((String)value.getObject()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\effects\OutlineEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */