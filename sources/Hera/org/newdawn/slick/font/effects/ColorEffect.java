/*    */ package org.newdawn.slick.font.effects;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.newdawn.slick.UnicodeFont;
/*    */ import org.newdawn.slick.font.Glyph;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorEffect
/*    */   implements ConfigurableEffect
/*    */ {
/* 21 */   private Color color = Color.white;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ColorEffect() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ColorEffect(Color color) {
/* 35 */     this.color = color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph) {
/* 42 */     g.setColor(this.color);
/* 43 */     g.fill(glyph.getShape());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Color getColor() {
/* 52 */     return this.color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setColor(Color color) {
/* 61 */     if (color == null) throw new IllegalArgumentException("color cannot be null."); 
/* 62 */     this.color = color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return "Color";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List getValues() {
/* 76 */     List<ConfigurableEffect.Value> values = new ArrayList();
/* 77 */     values.add(EffectUtil.colorValue("Color", this.color));
/* 78 */     return values;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValues(List values) {
/* 85 */     for (Iterator<ConfigurableEffect.Value> iter = values.iterator(); iter.hasNext(); ) {
/* 86 */       ConfigurableEffect.Value value = iter.next();
/* 87 */       if (value.getName().equals("Color"))
/* 88 */         setColor((Color)value.getObject()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\effects\ColorEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */