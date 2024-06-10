/*   1:    */ package org.newdawn.slick.font.effects;
/*   2:    */ 
/*   3:    */ import java.awt.BasicStroke;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Graphics2D;
/*   6:    */ import java.awt.Stroke;
/*   7:    */ import java.awt.image.BufferedImage;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import org.newdawn.slick.UnicodeFont;
/*  12:    */ import org.newdawn.slick.font.Glyph;
/*  13:    */ 
/*  14:    */ public class OutlineEffect
/*  15:    */   implements ConfigurableEffect
/*  16:    */ {
/*  17: 23 */   private float width = 2.0F;
/*  18: 25 */   private Color color = Color.black;
/*  19: 27 */   private int join = 2;
/*  20:    */   private Stroke stroke;
/*  21:    */   
/*  22:    */   public OutlineEffect() {}
/*  23:    */   
/*  24:    */   public OutlineEffect(int width, Color color)
/*  25:    */   {
/*  26: 44 */     this.width = width;
/*  27: 45 */     this.color = color;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph)
/*  31:    */   {
/*  32: 52 */     g = (Graphics2D)g.create();
/*  33: 53 */     if (this.stroke != null) {
/*  34: 54 */       g.setStroke(this.stroke);
/*  35:    */     } else {
/*  36: 56 */       g.setStroke(getStroke());
/*  37:    */     }
/*  38: 57 */     g.setColor(this.color);
/*  39: 58 */     g.draw(glyph.getShape());
/*  40: 59 */     g.dispose();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public float getWidth()
/*  44:    */   {
/*  45: 68 */     return this.width;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setWidth(int width)
/*  49:    */   {
/*  50: 78 */     this.width = width;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Color getColor()
/*  54:    */   {
/*  55: 87 */     return this.color;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setColor(Color color)
/*  59:    */   {
/*  60: 96 */     this.color = color;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getJoin()
/*  64:    */   {
/*  65:105 */     return this.join;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Stroke getStroke()
/*  69:    */   {
/*  70:114 */     if (this.stroke == null) {
/*  71:115 */       return new BasicStroke(this.width, 2, this.join);
/*  72:    */     }
/*  73:118 */     return this.stroke;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setStroke(Stroke stroke)
/*  77:    */   {
/*  78:128 */     this.stroke = stroke;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setJoin(int join)
/*  82:    */   {
/*  83:138 */     this.join = join;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String toString()
/*  87:    */   {
/*  88:145 */     return "Outline";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List getValues()
/*  92:    */   {
/*  93:152 */     List values = new ArrayList();
/*  94:153 */     values.add(EffectUtil.colorValue("Color", this.color));
/*  95:154 */     values.add(EffectUtil.floatValue("Width", this.width, 0.1F, 999.0F, "This setting controls the width of the outline. The glyphs will need padding so the outline doesn't get clipped."));
/*  96:    */     
/*  97:156 */     values.add(EffectUtil.optionValue("Join", String.valueOf(this.join), new String[][] { { "Bevel", "2" }, 
/*  98:157 */       { "Miter", "0" }, { "Round", "1" } }, 
/*  99:158 */       "This setting defines how the corners of the outline are drawn. This is usually only noticeable at large outline widths."));
/* 100:    */     
/* 101:160 */     return values;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setValues(List values)
/* 105:    */   {
/* 106:167 */     for (Iterator iter = values.iterator(); iter.hasNext();)
/* 107:    */     {
/* 108:168 */       ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
/* 109:169 */       if (value.getName().equals("Color")) {
/* 110:170 */         this.color = ((Color)value.getObject());
/* 111:171 */       } else if (value.getName().equals("Width")) {
/* 112:172 */         this.width = ((Float)value.getObject()).floatValue();
/* 113:173 */       } else if (value.getName().equals("Join")) {
/* 114:174 */         this.join = Integer.parseInt((String)value.getObject());
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.effects.OutlineEffect
 * JD-Core Version:    0.7.0.1
 */