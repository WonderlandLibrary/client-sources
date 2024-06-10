/*   1:    */ package org.newdawn.slick.font.effects;
/*   2:    */ 
/*   3:    */ import java.awt.BasicStroke;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Shape;
/*   6:    */ import java.awt.Stroke;
/*   7:    */ import java.awt.geom.FlatteningPathIterator;
/*   8:    */ import java.awt.geom.GeneralPath;
/*   9:    */ import java.awt.geom.PathIterator;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ 
/*  13:    */ public class OutlineZigzagEffect
/*  14:    */   extends OutlineEffect
/*  15:    */ {
/*  16: 35 */   private float amplitude = 1.0F;
/*  17: 37 */   private float wavelength = 3.0F;
/*  18:    */   
/*  19:    */   public OutlineZigzagEffect()
/*  20:    */   {
/*  21: 43 */     setStroke(new ZigzagStroke(null));
/*  22:    */   }
/*  23:    */   
/*  24:    */   public float getWavelength()
/*  25:    */   {
/*  26: 52 */     return this.wavelength;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setWavelength(float wavelength)
/*  30:    */   {
/*  31: 61 */     this.wavelength = wavelength;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public float getAmplitude()
/*  35:    */   {
/*  36: 70 */     return this.amplitude;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setAmplitude(float amplitude)
/*  40:    */   {
/*  41: 79 */     this.amplitude = amplitude;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public OutlineZigzagEffect(int width, Color color)
/*  45:    */   {
/*  46: 89 */     super(width, color);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String toString()
/*  50:    */   {
/*  51: 96 */     return "Outline (Zigzag)";
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List getValues()
/*  55:    */   {
/*  56:103 */     List values = super.getValues();
/*  57:104 */     values.add(EffectUtil.floatValue("Wavelength", this.wavelength, 1.0F, 100.0F, "This setting controls the wavelength of the outline. The smaller the value, the more segments will be used to draw the outline."));
/*  58:    */     
/*  59:106 */     values.add(EffectUtil.floatValue("Amplitude", this.amplitude, 0.5F, 50.0F, "This setting controls the amplitude of the outline. The bigger the value, the more the zigzags will vary."));
/*  60:    */     
/*  61:108 */     return values;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setValues(List values)
/*  65:    */   {
/*  66:115 */     super.setValues(values);
/*  67:116 */     for (Iterator iter = values.iterator(); iter.hasNext();)
/*  68:    */     {
/*  69:117 */       ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
/*  70:118 */       if (value.getName().equals("Wavelength")) {
/*  71:119 */         this.wavelength = ((Float)value.getObject()).floatValue();
/*  72:120 */       } else if (value.getName().equals("Amplitude")) {
/*  73:121 */         this.amplitude = ((Float)value.getObject()).floatValue();
/*  74:    */       }
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   private class ZigzagStroke
/*  79:    */     implements Stroke
/*  80:    */   {
/*  81:    */     private static final float FLATNESS = 1.0F;
/*  82:    */     
/*  83:    */     private ZigzagStroke() {}
/*  84:    */     
/*  85:    */     public Shape createStrokedShape(Shape shape)
/*  86:    */     {
/*  87:140 */       GeneralPath result = new GeneralPath();
/*  88:141 */       PathIterator it = new FlatteningPathIterator(shape.getPathIterator(null), 1.0D);
/*  89:142 */       float[] points = new float[6];
/*  90:143 */       float moveX = 0.0F;float moveY = 0.0F;
/*  91:144 */       float lastX = 0.0F;float lastY = 0.0F;
/*  92:145 */       float thisX = 0.0F;float thisY = 0.0F;
/*  93:146 */       int type = 0;
/*  94:147 */       float next = 0.0F;
/*  95:148 */       int phase = 0;
/*  96:149 */       while (!it.isDone())
/*  97:    */       {
/*  98:150 */         type = it.currentSegment(points);
/*  99:151 */         switch (type)
/* 100:    */         {
/* 101:    */         case 0: 
/* 102:153 */           moveX = lastX = points[0];
/* 103:154 */           moveY = lastY = points[1];
/* 104:155 */           result.moveTo(moveX, moveY);
/* 105:156 */           next = OutlineZigzagEffect.this.wavelength / 2.0F;
/* 106:157 */           break;
/* 107:    */         case 4: 
/* 108:160 */           points[0] = moveX;
/* 109:161 */           points[1] = moveY;
/* 110:    */         case 1: 
/* 111:165 */           thisX = points[0];
/* 112:166 */           thisY = points[1];
/* 113:167 */           float dx = thisX - lastX;
/* 114:168 */           float dy = thisY - lastY;
/* 115:169 */           float distance = (float)Math.sqrt(dx * dx + dy * dy);
/* 116:170 */           if (distance >= next)
/* 117:    */           {
/* 118:171 */             float r = 1.0F / distance;
/* 119:172 */             while (distance >= next)
/* 120:    */             {
/* 121:173 */               float x = lastX + next * dx * r;
/* 122:174 */               float y = lastY + next * dy * r;
/* 123:175 */               if ((phase & 0x1) == 0) {
/* 124:176 */                 result.lineTo(x + OutlineZigzagEffect.this.amplitude * dy * r, y - OutlineZigzagEffect.this.amplitude * dx * r);
/* 125:    */               } else {
/* 126:178 */                 result.lineTo(x - OutlineZigzagEffect.this.amplitude * dy * r, y + OutlineZigzagEffect.this.amplitude * dx * r);
/* 127:    */               }
/* 128:179 */               next += OutlineZigzagEffect.this.wavelength;
/* 129:180 */               phase++;
/* 130:    */             }
/* 131:    */           }
/* 132:183 */           next -= distance;
/* 133:184 */           lastX = thisX;
/* 134:185 */           lastY = thisY;
/* 135:186 */           if (type == 4) {
/* 136:186 */             result.closePath();
/* 137:    */           }
/* 138:    */           break;
/* 139:    */         }
/* 140:189 */         it.next();
/* 141:    */       }
/* 142:191 */       return new BasicStroke(OutlineZigzagEffect.this.getWidth(), 2, OutlineZigzagEffect.this.getJoin()).createStrokedShape(result);
/* 143:    */     }
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.effects.OutlineZigzagEffect
 * JD-Core Version:    0.7.0.1
 */