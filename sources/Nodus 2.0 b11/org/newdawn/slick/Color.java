/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.nio.FloatBuffer;
/*   5:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*   6:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*   7:    */ 
/*   8:    */ public class Color
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 1393939L;
/*  12: 19 */   protected transient SGL GL = Renderer.get();
/*  13: 22 */   public static final Color transparent = new Color(0.0F, 0.0F, 0.0F, 0.0F);
/*  14: 24 */   public static final Color white = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/*  15: 26 */   public static final Color yellow = new Color(1.0F, 1.0F, 0.0F, 1.0F);
/*  16: 28 */   public static final Color red = new Color(1.0F, 0.0F, 0.0F, 1.0F);
/*  17: 30 */   public static final Color blue = new Color(0.0F, 0.0F, 1.0F, 1.0F);
/*  18: 32 */   public static final Color green = new Color(0.0F, 1.0F, 0.0F, 1.0F);
/*  19: 34 */   public static final Color black = new Color(0.0F, 0.0F, 0.0F, 1.0F);
/*  20: 36 */   public static final Color gray = new Color(0.5F, 0.5F, 0.5F, 1.0F);
/*  21: 38 */   public static final Color cyan = new Color(0.0F, 1.0F, 1.0F, 1.0F);
/*  22: 40 */   public static final Color darkGray = new Color(0.3F, 0.3F, 0.3F, 1.0F);
/*  23: 42 */   public static final Color lightGray = new Color(0.7F, 0.7F, 0.7F, 1.0F);
/*  24: 44 */   public static final Color pink = new Color(255, 175, 175, 255);
/*  25: 46 */   public static final Color orange = new Color(255, 200, 0, 255);
/*  26: 48 */   public static final Color magenta = new Color(255, 0, 255, 255);
/*  27:    */   public float r;
/*  28:    */   public float g;
/*  29:    */   public float b;
/*  30: 57 */   public float a = 1.0F;
/*  31:    */   
/*  32:    */   public Color(Color color)
/*  33:    */   {
/*  34: 65 */     this.r = color.r;
/*  35: 66 */     this.g = color.g;
/*  36: 67 */     this.b = color.b;
/*  37: 68 */     this.a = color.a;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Color(FloatBuffer buffer)
/*  41:    */   {
/*  42: 77 */     this.r = buffer.get();
/*  43: 78 */     this.g = buffer.get();
/*  44: 79 */     this.b = buffer.get();
/*  45: 80 */     this.a = buffer.get();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Color(float r, float g, float b)
/*  49:    */   {
/*  50: 91 */     this.r = r;
/*  51: 92 */     this.g = g;
/*  52: 93 */     this.b = b;
/*  53: 94 */     this.a = 1.0F;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Color(float r, float g, float b, float a)
/*  57:    */   {
/*  58:106 */     this.r = Math.min(r, 1.0F);
/*  59:107 */     this.g = Math.min(g, 1.0F);
/*  60:108 */     this.b = Math.min(b, 1.0F);
/*  61:109 */     this.a = Math.min(a, 1.0F);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Color(int r, int g, int b)
/*  65:    */   {
/*  66:120 */     this.r = (r / 255.0F);
/*  67:121 */     this.g = (g / 255.0F);
/*  68:122 */     this.b = (b / 255.0F);
/*  69:123 */     this.a = 1.0F;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Color(int r, int g, int b, int a)
/*  73:    */   {
/*  74:135 */     this.r = (r / 255.0F);
/*  75:136 */     this.g = (g / 255.0F);
/*  76:137 */     this.b = (b / 255.0F);
/*  77:138 */     this.a = (a / 255.0F);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Color(int value)
/*  81:    */   {
/*  82:149 */     int r = (value & 0xFF0000) >> 16;
/*  83:150 */     int g = (value & 0xFF00) >> 8;
/*  84:151 */     int b = value & 0xFF;
/*  85:152 */     int a = (value & 0xFF000000) >> 24;
/*  86:154 */     if (a < 0) {
/*  87:155 */       a += 256;
/*  88:    */     }
/*  89:157 */     if (a == 0) {
/*  90:158 */       a = 255;
/*  91:    */     }
/*  92:161 */     this.r = (r / 255.0F);
/*  93:162 */     this.g = (g / 255.0F);
/*  94:163 */     this.b = (b / 255.0F);
/*  95:164 */     this.a = (a / 255.0F);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static Color decode(String nm)
/*  99:    */   {
/* 100:175 */     return new Color(Integer.decode(nm).intValue());
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void bind()
/* 104:    */   {
/* 105:182 */     this.GL.glColor4f(this.r, this.g, this.b, this.a);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int hashCode()
/* 109:    */   {
/* 110:189 */     return (int)(this.r + this.g + this.b + this.a) * 255;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean equals(Object other)
/* 114:    */   {
/* 115:196 */     if ((other instanceof Color))
/* 116:    */     {
/* 117:197 */       Color o = (Color)other;
/* 118:198 */       return (o.r == this.r) && (o.g == this.g) && (o.b == this.b) && (o.a == this.a);
/* 119:    */     }
/* 120:201 */     return false;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String toString()
/* 124:    */   {
/* 125:208 */     return "Color (" + this.r + "," + this.g + "," + this.b + "," + this.a + ")";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Color darker()
/* 129:    */   {
/* 130:217 */     return darker(0.5F);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Color darker(float scale)
/* 134:    */   {
/* 135:227 */     scale = 1.0F - scale;
/* 136:228 */     Color temp = new Color(this.r * scale, this.g * scale, this.b * scale, this.a);
/* 137:    */     
/* 138:230 */     return temp;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Color brighter()
/* 142:    */   {
/* 143:239 */     return brighter(0.2F);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getRed()
/* 147:    */   {
/* 148:248 */     return (int)(this.r * 255.0F);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getGreen()
/* 152:    */   {
/* 153:257 */     return (int)(this.g * 255.0F);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getBlue()
/* 157:    */   {
/* 158:266 */     return (int)(this.b * 255.0F);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getAlpha()
/* 162:    */   {
/* 163:275 */     return (int)(this.a * 255.0F);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int getRedByte()
/* 167:    */   {
/* 168:284 */     return (int)(this.r * 255.0F);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getGreenByte()
/* 172:    */   {
/* 173:293 */     return (int)(this.g * 255.0F);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int getBlueByte()
/* 177:    */   {
/* 178:302 */     return (int)(this.b * 255.0F);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int getAlphaByte()
/* 182:    */   {
/* 183:311 */     return (int)(this.a * 255.0F);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Color brighter(float scale)
/* 187:    */   {
/* 188:321 */     scale += 1.0F;
/* 189:322 */     Color temp = new Color(this.r * scale, this.g * scale, this.b * scale, this.a);
/* 190:    */     
/* 191:324 */     return temp;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Color multiply(Color c)
/* 195:    */   {
/* 196:334 */     return new Color(this.r * c.r, this.g * c.g, this.b * c.b, this.a * c.a);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void add(Color c)
/* 200:    */   {
/* 201:343 */     this.r += c.r;
/* 202:344 */     this.g += c.g;
/* 203:345 */     this.b += c.b;
/* 204:346 */     this.a += c.a;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void scale(float value)
/* 208:    */   {
/* 209:355 */     this.r *= value;
/* 210:356 */     this.g *= value;
/* 211:357 */     this.b *= value;
/* 212:358 */     this.a *= value;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Color addToCopy(Color c)
/* 216:    */   {
/* 217:368 */     Color copy = new Color(this.r, this.g, this.b, this.a);
/* 218:369 */     copy.r += c.r;
/* 219:370 */     copy.g += c.g;
/* 220:371 */     copy.b += c.b;
/* 221:372 */     copy.a += c.a;
/* 222:    */     
/* 223:374 */     return copy;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Color scaleCopy(float value)
/* 227:    */   {
/* 228:384 */     Color copy = new Color(this.r, this.g, this.b, this.a);
/* 229:385 */     copy.r *= value;
/* 230:386 */     copy.g *= value;
/* 231:387 */     copy.b *= value;
/* 232:388 */     copy.a *= value;
/* 233:    */     
/* 234:390 */     return copy;
/* 235:    */   }
/* 236:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.Color
 * JD-Core Version:    0.7.0.1
 */