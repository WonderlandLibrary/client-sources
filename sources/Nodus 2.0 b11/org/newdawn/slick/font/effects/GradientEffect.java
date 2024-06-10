/*   1:    */ package org.newdawn.slick.font.effects;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.GradientPaint;
/*   5:    */ import java.awt.Graphics2D;
/*   6:    */ import java.awt.image.BufferedImage;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import org.newdawn.slick.UnicodeFont;
/*  11:    */ import org.newdawn.slick.font.Glyph;
/*  12:    */ 
/*  13:    */ public class GradientEffect
/*  14:    */   implements ConfigurableEffect
/*  15:    */ {
/*  16: 22 */   private Color topColor = Color.cyan;
/*  17: 24 */   private Color bottomColor = Color.blue;
/*  18: 26 */   private int offset = 0;
/*  19: 28 */   private float scale = 1.0F;
/*  20:    */   private boolean cyclic;
/*  21:    */   
/*  22:    */   public GradientEffect() {}
/*  23:    */   
/*  24:    */   public GradientEffect(Color topColor, Color bottomColor, float scale)
/*  25:    */   {
/*  26: 46 */     this.topColor = topColor;
/*  27: 47 */     this.bottomColor = bottomColor;
/*  28: 48 */     this.scale = scale;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph)
/*  32:    */   {
/*  33: 55 */     int ascent = unicodeFont.getAscent();
/*  34: 56 */     float height = ascent * this.scale;
/*  35: 57 */     float top = -glyph.getYOffset() + unicodeFont.getDescent() + this.offset + ascent / 2 - height / 2.0F;
/*  36: 58 */     g.setPaint(new GradientPaint(0.0F, top, this.topColor, 0.0F, top + height, this.bottomColor, this.cyclic));
/*  37: 59 */     g.fill(glyph.getShape());
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Color getTopColor()
/*  41:    */   {
/*  42: 68 */     return this.topColor;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setTopColor(Color topColor)
/*  46:    */   {
/*  47: 77 */     this.topColor = topColor;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Color getBottomColor()
/*  51:    */   {
/*  52: 86 */     return this.bottomColor;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setBottomColor(Color bottomColor)
/*  56:    */   {
/*  57: 95 */     this.bottomColor = bottomColor;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getOffset()
/*  61:    */   {
/*  62:104 */     return this.offset;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setOffset(int offset)
/*  66:    */   {
/*  67:114 */     this.offset = offset;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public float getScale()
/*  71:    */   {
/*  72:123 */     return this.scale;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setScale(float scale)
/*  76:    */   {
/*  77:133 */     this.scale = scale;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isCyclic()
/*  81:    */   {
/*  82:142 */     return this.cyclic;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setCyclic(boolean cyclic)
/*  86:    */   {
/*  87:151 */     this.cyclic = cyclic;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String toString()
/*  91:    */   {
/*  92:158 */     return "Gradient";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List getValues()
/*  96:    */   {
/*  97:165 */     List values = new ArrayList();
/*  98:166 */     values.add(EffectUtil.colorValue("Top color", this.topColor));
/*  99:167 */     values.add(EffectUtil.colorValue("Bottom color", this.bottomColor));
/* 100:168 */     values.add(EffectUtil.intValue("Offset", this.offset, 
/* 101:169 */       "This setting allows you to move the gradient up or down. The gradient is normally centered on the glyph."));
/* 102:170 */     values.add(EffectUtil.floatValue("Scale", this.scale, 0.0F, 1.0F, "This setting allows you to change the height of the gradient by apercentage. The gradient is normally the height of most glyphs in the font."));
/* 103:    */     
/* 104:172 */     values.add(EffectUtil.booleanValue("Cyclic", this.cyclic, "If this setting is checked, the gradient will repeat."));
/* 105:173 */     return values;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setValues(List values)
/* 109:    */   {
/* 110:180 */     for (Iterator iter = values.iterator(); iter.hasNext();)
/* 111:    */     {
/* 112:181 */       ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
/* 113:182 */       if (value.getName().equals("Top color")) {
/* 114:183 */         this.topColor = ((Color)value.getObject());
/* 115:184 */       } else if (value.getName().equals("Bottom color")) {
/* 116:185 */         this.bottomColor = ((Color)value.getObject());
/* 117:186 */       } else if (value.getName().equals("Offset")) {
/* 118:187 */         this.offset = ((Integer)value.getObject()).intValue();
/* 119:188 */       } else if (value.getName().equals("Scale")) {
/* 120:189 */         this.scale = ((Float)value.getObject()).floatValue();
/* 121:190 */       } else if (value.getName().equals("Cyclic")) {
/* 122:191 */         this.cyclic = ((Boolean)value.getObject()).booleanValue();
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.effects.GradientEffect
 * JD-Core Version:    0.7.0.1
 */