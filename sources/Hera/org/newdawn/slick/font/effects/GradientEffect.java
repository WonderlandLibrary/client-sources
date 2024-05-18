/*     */ package org.newdawn.slick.font.effects;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
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
/*     */ public class GradientEffect
/*     */   implements ConfigurableEffect
/*     */ {
/*  22 */   private Color topColor = Color.cyan;
/*     */   
/*  24 */   private Color bottomColor = Color.blue;
/*     */   
/*  26 */   private int offset = 0;
/*     */   
/*  28 */   private float scale = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean cyclic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientEffect() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientEffect(Color topColor, Color bottomColor, float scale) {
/*  46 */     this.topColor = topColor;
/*  47 */     this.bottomColor = bottomColor;
/*  48 */     this.scale = scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph) {
/*  55 */     int ascent = unicodeFont.getAscent();
/*  56 */     float height = ascent * this.scale;
/*  57 */     float top = (-glyph.getYOffset() + unicodeFont.getDescent() + this.offset + ascent / 2) - height / 2.0F;
/*  58 */     g.setPaint(new GradientPaint(0.0F, top, this.topColor, 0.0F, top + height, this.bottomColor, this.cyclic));
/*  59 */     g.fill(glyph.getShape());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getTopColor() {
/*  68 */     return this.topColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTopColor(Color topColor) {
/*  77 */     this.topColor = topColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getBottomColor() {
/*  86 */     return this.bottomColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBottomColor(Color bottomColor) {
/*  95 */     this.bottomColor = bottomColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOffset() {
/* 104 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOffset(int offset) {
/* 114 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScale() {
/* 123 */     return this.scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScale(float scale) {
/* 133 */     this.scale = scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCyclic() {
/* 142 */     return this.cyclic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCyclic(boolean cyclic) {
/* 151 */     this.cyclic = cyclic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     return "Gradient";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getValues() {
/* 165 */     List<ConfigurableEffect.Value> values = new ArrayList();
/* 166 */     values.add(EffectUtil.colorValue("Top color", this.topColor));
/* 167 */     values.add(EffectUtil.colorValue("Bottom color", this.bottomColor));
/* 168 */     values.add(EffectUtil.intValue("Offset", this.offset, "This setting allows you to move the gradient up or down. The gradient is normally centered on the glyph."));
/*     */     
/* 170 */     values.add(EffectUtil.floatValue("Scale", this.scale, 0.0F, 1.0F, "This setting allows you to change the height of the gradient by apercentage. The gradient is normally the height of most glyphs in the font."));
/*     */     
/* 172 */     values.add(EffectUtil.booleanValue("Cyclic", this.cyclic, "If this setting is checked, the gradient will repeat."));
/* 173 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValues(List values) {
/* 180 */     for (Iterator<ConfigurableEffect.Value> iter = values.iterator(); iter.hasNext(); ) {
/* 181 */       ConfigurableEffect.Value value = iter.next();
/* 182 */       if (value.getName().equals("Top color")) {
/* 183 */         this.topColor = (Color)value.getObject(); continue;
/* 184 */       }  if (value.getName().equals("Bottom color")) {
/* 185 */         this.bottomColor = (Color)value.getObject(); continue;
/* 186 */       }  if (value.getName().equals("Offset")) {
/* 187 */         this.offset = ((Integer)value.getObject()).intValue(); continue;
/* 188 */       }  if (value.getName().equals("Scale")) {
/* 189 */         this.scale = ((Float)value.getObject()).floatValue(); continue;
/* 190 */       }  if (value.getName().equals("Cyclic"))
/* 191 */         this.cyclic = ((Boolean)value.getObject()).booleanValue(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\effects\GradientEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */