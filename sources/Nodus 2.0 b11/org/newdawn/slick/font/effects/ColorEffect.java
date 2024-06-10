/*  1:   */ package org.newdawn.slick.font.effects;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Graphics2D;
/*  5:   */ import java.awt.image.BufferedImage;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Iterator;
/*  8:   */ import java.util.List;
/*  9:   */ import org.newdawn.slick.UnicodeFont;
/* 10:   */ import org.newdawn.slick.font.Glyph;
/* 11:   */ 
/* 12:   */ public class ColorEffect
/* 13:   */   implements ConfigurableEffect
/* 14:   */ {
/* 15:21 */   private Color color = Color.white;
/* 16:   */   
/* 17:   */   public ColorEffect() {}
/* 18:   */   
/* 19:   */   public ColorEffect(Color color)
/* 20:   */   {
/* 21:35 */     this.color = color;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph)
/* 25:   */   {
/* 26:42 */     g.setColor(this.color);
/* 27:43 */     g.fill(glyph.getShape());
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Color getColor()
/* 31:   */   {
/* 32:52 */     return this.color;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setColor(Color color)
/* 36:   */   {
/* 37:61 */     if (color == null) {
/* 38:61 */       throw new IllegalArgumentException("color cannot be null.");
/* 39:   */     }
/* 40:62 */     this.color = color;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String toString()
/* 44:   */   {
/* 45:69 */     return "Color";
/* 46:   */   }
/* 47:   */   
/* 48:   */   public List getValues()
/* 49:   */   {
/* 50:76 */     List values = new ArrayList();
/* 51:77 */     values.add(EffectUtil.colorValue("Color", this.color));
/* 52:78 */     return values;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setValues(List values)
/* 56:   */   {
/* 57:85 */     for (Iterator iter = values.iterator(); iter.hasNext();)
/* 58:   */     {
/* 59:86 */       ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
/* 60:87 */       if (value.getName().equals("Color")) {
/* 61:88 */         setColor((Color)value.getObject());
/* 62:   */       }
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.effects.ColorEffect
 * JD-Core Version:    0.7.0.1
 */