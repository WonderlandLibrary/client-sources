/*   1:    */ package org.newdawn.slick.font.effects;
/*   2:    */ 
/*   3:    */ import java.awt.AlphaComposite;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Composite;
/*   6:    */ import java.awt.Graphics2D;
/*   7:    */ import java.awt.RenderingHints;
/*   8:    */ import java.awt.image.BufferedImage;
/*   9:    */ import java.awt.image.ConvolveOp;
/*  10:    */ import java.awt.image.Kernel;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import org.newdawn.slick.UnicodeFont;
/*  15:    */ import org.newdawn.slick.font.Glyph;
/*  16:    */ 
/*  17:    */ public class ShadowEffect
/*  18:    */   implements ConfigurableEffect
/*  19:    */ {
/*  20:    */   public static final int NUM_KERNELS = 16;
/*  21: 28 */   public static final float[][] GAUSSIAN_BLUR_KERNELS = generateGaussianBlurKernels(16);
/*  22: 31 */   private Color color = Color.black;
/*  23: 33 */   private float opacity = 0.6F;
/*  24: 35 */   private float xDistance = 2.0F;
/*  25: 37 */   private float yDistance = 2.0F;
/*  26: 39 */   private int blurKernelSize = 0;
/*  27: 41 */   private int blurPasses = 1;
/*  28:    */   
/*  29:    */   public ShadowEffect() {}
/*  30:    */   
/*  31:    */   public ShadowEffect(Color color, int xDistance, int yDistance, float opacity)
/*  32:    */   {
/*  33: 58 */     this.color = color;
/*  34: 59 */     this.xDistance = xDistance;
/*  35: 60 */     this.yDistance = yDistance;
/*  36: 61 */     this.opacity = opacity;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph)
/*  40:    */   {
/*  41: 68 */     g = (Graphics2D)g.create();
/*  42: 69 */     g.translate(this.xDistance, this.yDistance);
/*  43: 70 */     g.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), Math.round(this.opacity * 255.0F)));
/*  44: 71 */     g.fill(glyph.getShape());
/*  45: 74 */     for (Iterator iter = unicodeFont.getEffects().iterator(); iter.hasNext();)
/*  46:    */     {
/*  47: 75 */       Effect effect = (Effect)iter.next();
/*  48: 76 */       if ((effect instanceof OutlineEffect))
/*  49:    */       {
/*  50: 77 */         Composite composite = g.getComposite();
/*  51: 78 */         g.setComposite(AlphaComposite.Src);
/*  52:    */         
/*  53: 80 */         g.setStroke(((OutlineEffect)effect).getStroke());
/*  54: 81 */         g.draw(glyph.getShape());
/*  55:    */         
/*  56: 83 */         g.setComposite(composite);
/*  57: 84 */         break;
/*  58:    */       }
/*  59:    */     }
/*  60: 88 */     g.dispose();
/*  61: 89 */     if ((this.blurKernelSize > 1) && (this.blurKernelSize < 16) && (this.blurPasses > 0)) {
/*  62: 89 */       blur(image);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   private void blur(BufferedImage image)
/*  67:    */   {
/*  68: 98 */     float[] matrix = GAUSSIAN_BLUR_KERNELS[(this.blurKernelSize - 1)];
/*  69: 99 */     Kernel gaussianBlur1 = new Kernel(matrix.length, 1, matrix);
/*  70:100 */     Kernel gaussianBlur2 = new Kernel(1, matrix.length, matrix);
/*  71:101 */     RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
/*  72:102 */     ConvolveOp gaussianOp1 = new ConvolveOp(gaussianBlur1, 1, hints);
/*  73:103 */     ConvolveOp gaussianOp2 = new ConvolveOp(gaussianBlur2, 1, hints);
/*  74:104 */     BufferedImage scratchImage = EffectUtil.getScratchImage();
/*  75:105 */     for (int i = 0; i < this.blurPasses; i++)
/*  76:    */     {
/*  77:106 */       gaussianOp1.filter(image, scratchImage);
/*  78:107 */       gaussianOp2.filter(scratchImage, image);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Color getColor()
/*  83:    */   {
/*  84:117 */     return this.color;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setColor(Color color)
/*  88:    */   {
/*  89:126 */     this.color = color;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public float getXDistance()
/*  93:    */   {
/*  94:136 */     return this.xDistance;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setXDistance(float distance)
/*  98:    */   {
/*  99:146 */     this.xDistance = distance;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public float getYDistance()
/* 103:    */   {
/* 104:156 */     return this.yDistance;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setYDistance(float distance)
/* 108:    */   {
/* 109:166 */     this.yDistance = distance;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getBlurKernelSize()
/* 113:    */   {
/* 114:175 */     return this.blurKernelSize;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setBlurKernelSize(int blurKernelSize)
/* 118:    */   {
/* 119:184 */     this.blurKernelSize = blurKernelSize;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getBlurPasses()
/* 123:    */   {
/* 124:193 */     return this.blurPasses;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setBlurPasses(int blurPasses)
/* 128:    */   {
/* 129:202 */     this.blurPasses = blurPasses;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public float getOpacity()
/* 133:    */   {
/* 134:211 */     return this.opacity;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setOpacity(float opacity)
/* 138:    */   {
/* 139:220 */     this.opacity = opacity;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String toString()
/* 143:    */   {
/* 144:227 */     return "Shadow";
/* 145:    */   }
/* 146:    */   
/* 147:    */   public List getValues()
/* 148:    */   {
/* 149:234 */     List values = new ArrayList();
/* 150:235 */     values.add(EffectUtil.colorValue("Color", this.color));
/* 151:236 */     values.add(EffectUtil.floatValue("Opacity", this.opacity, 0.0F, 1.0F, "This setting sets the translucency of the shadow."));
/* 152:237 */     values.add(EffectUtil.floatValue("X distance", this.xDistance, 1.4E-45F, 3.4028235E+38F, "This setting is the amount of pixels to offset the shadow on the x axis. The glyphs will need padding so the shadow doesn't get clipped."));
/* 153:    */     
/* 154:239 */     values.add(EffectUtil.floatValue("Y distance", this.yDistance, 1.4E-45F, 3.4028235E+38F, "This setting is the amount of pixels to offset the shadow on the y axis. The glyphs will need padding so the shadow doesn't get clipped."));
/* 155:    */     
/* 156:    */ 
/* 157:242 */     List options = new ArrayList();
/* 158:243 */     options.add(new String[] { "None", "0" });
/* 159:244 */     for (int i = 2; i < 16; i++) {
/* 160:245 */       options.add(new String[] { String.valueOf(i) });
/* 161:    */     }
/* 162:246 */     String[][] optionsArray = (String[][])options.toArray(new String[options.size()][]);
/* 163:247 */     values.add(EffectUtil.optionValue("Blur kernel size", String.valueOf(this.blurKernelSize), optionsArray, 
/* 164:248 */       "This setting controls how many neighboring pixels are used to blur the shadow. Set to \"None\" for no blur."));
/* 165:    */     
/* 166:250 */     values.add(EffectUtil.intValue("Blur passes", this.blurPasses, 
/* 167:251 */       "The setting is the number of times to apply a blur to the shadow. Set to \"0\" for no blur."));
/* 168:252 */     return values;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setValues(List values)
/* 172:    */   {
/* 173:259 */     for (Iterator iter = values.iterator(); iter.hasNext();)
/* 174:    */     {
/* 175:260 */       ConfigurableEffect.Value value = (ConfigurableEffect.Value)iter.next();
/* 176:261 */       if (value.getName().equals("Color")) {
/* 177:262 */         this.color = ((Color)value.getObject());
/* 178:263 */       } else if (value.getName().equals("Opacity")) {
/* 179:264 */         this.opacity = ((Float)value.getObject()).floatValue();
/* 180:265 */       } else if (value.getName().equals("X distance")) {
/* 181:266 */         this.xDistance = ((Float)value.getObject()).floatValue();
/* 182:267 */       } else if (value.getName().equals("Y distance")) {
/* 183:268 */         this.yDistance = ((Float)value.getObject()).floatValue();
/* 184:269 */       } else if (value.getName().equals("Blur kernel size")) {
/* 185:270 */         this.blurKernelSize = Integer.parseInt((String)value.getObject());
/* 186:271 */       } else if (value.getName().equals("Blur passes")) {
/* 187:272 */         this.blurPasses = ((Integer)value.getObject()).intValue();
/* 188:    */       }
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   private static float[][] generateGaussianBlurKernels(int level)
/* 193:    */   {
/* 194:284 */     float[][] pascalsTriangle = generatePascalsTriangle(level);
/* 195:285 */     float[][] gaussianTriangle = new float[pascalsTriangle.length][];
/* 196:286 */     for (int i = 0; i < gaussianTriangle.length; i++)
/* 197:    */     {
/* 198:287 */       float total = 0.0F;
/* 199:288 */       gaussianTriangle[i] = new float[pascalsTriangle[i].length];
/* 200:289 */       for (int j = 0; j < pascalsTriangle[i].length; j++) {
/* 201:290 */         total += pascalsTriangle[i][j];
/* 202:    */       }
/* 203:291 */       float coefficient = 1.0F / total;
/* 204:292 */       for (int j = 0; j < pascalsTriangle[i].length; j++) {
/* 205:293 */         gaussianTriangle[i][j] = (coefficient * pascalsTriangle[i][j]);
/* 206:    */       }
/* 207:    */     }
/* 208:295 */     return gaussianTriangle;
/* 209:    */   }
/* 210:    */   
/* 211:    */   private static float[][] generatePascalsTriangle(int level)
/* 212:    */   {
/* 213:305 */     if (level < 2) {
/* 214:305 */       level = 2;
/* 215:    */     }
/* 216:306 */     float[][] triangle = new float[level][];
/* 217:307 */     triangle[0] = new float[1];
/* 218:308 */     triangle[1] = new float[2];
/* 219:309 */     triangle[0][0] = 1.0F;
/* 220:310 */     triangle[1][0] = 1.0F;
/* 221:311 */     triangle[1][1] = 1.0F;
/* 222:312 */     for (int i = 2; i < level; i++)
/* 223:    */     {
/* 224:313 */       triangle[i] = new float[i + 1];
/* 225:314 */       triangle[i][0] = 1.0F;
/* 226:315 */       triangle[i][i] = 1.0F;
/* 227:316 */       for (int j = 1; j < triangle[i].length - 1; j++) {
/* 228:317 */         triangle[i][j] = (triangle[(i - 1)][(j - 1)] + triangle[(i - 1)][j]);
/* 229:    */       }
/* 230:    */     }
/* 231:319 */     return triangle;
/* 232:    */   }
/* 233:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.effects.ShadowEffect
 * JD-Core Version:    0.7.0.1
 */