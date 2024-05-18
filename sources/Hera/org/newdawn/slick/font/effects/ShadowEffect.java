/*     */ package org.newdawn.slick.font.effects;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ConvolveOp;
/*     */ import java.awt.image.Kernel;
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
/*     */ 
/*     */ public class ShadowEffect
/*     */   implements ConfigurableEffect
/*     */ {
/*     */   public static final int NUM_KERNELS = 16;
/*  28 */   public static final float[][] GAUSSIAN_BLUR_KERNELS = generateGaussianBlurKernels(16);
/*     */ 
/*     */   
/*  31 */   private Color color = Color.black;
/*     */   
/*  33 */   private float opacity = 0.6F;
/*     */   
/*  35 */   private float xDistance = 2.0F;
/*     */   
/*  37 */   private float yDistance = 2.0F;
/*     */   
/*  39 */   private int blurKernelSize = 0;
/*     */   
/*  41 */   private int blurPasses = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShadowEffect() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShadowEffect(Color color, int xDistance, int yDistance, float opacity) {
/*  58 */     this.color = color;
/*  59 */     this.xDistance = xDistance;
/*  60 */     this.yDistance = yDistance;
/*  61 */     this.opacity = opacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph) {
/*  68 */     g = (Graphics2D)g.create();
/*  69 */     g.translate(this.xDistance, this.yDistance);
/*  70 */     g.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), Math.round(this.opacity * 255.0F)));
/*  71 */     g.fill(glyph.getShape());
/*     */ 
/*     */     
/*  74 */     for (Iterator<Effect> iter = unicodeFont.getEffects().iterator(); iter.hasNext(); ) {
/*  75 */       Effect effect = iter.next();
/*  76 */       if (effect instanceof OutlineEffect) {
/*  77 */         Composite composite = g.getComposite();
/*  78 */         g.setComposite(AlphaComposite.Src);
/*     */         
/*  80 */         g.setStroke(((OutlineEffect)effect).getStroke());
/*  81 */         g.draw(glyph.getShape());
/*     */         
/*  83 */         g.setComposite(composite);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  88 */     g.dispose();
/*  89 */     if (this.blurKernelSize > 1 && this.blurKernelSize < 16 && this.blurPasses > 0) blur(image);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void blur(BufferedImage image) {
/*  98 */     float[] matrix = GAUSSIAN_BLUR_KERNELS[this.blurKernelSize - 1];
/*  99 */     Kernel gaussianBlur1 = new Kernel(matrix.length, 1, matrix);
/* 100 */     Kernel gaussianBlur2 = new Kernel(1, matrix.length, matrix);
/* 101 */     RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
/* 102 */     ConvolveOp gaussianOp1 = new ConvolveOp(gaussianBlur1, 1, hints);
/* 103 */     ConvolveOp gaussianOp2 = new ConvolveOp(gaussianBlur2, 1, hints);
/* 104 */     BufferedImage scratchImage = EffectUtil.getScratchImage();
/* 105 */     for (int i = 0; i < this.blurPasses; i++) {
/* 106 */       gaussianOp1.filter(image, scratchImage);
/* 107 */       gaussianOp2.filter(scratchImage, image);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 117 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 126 */     this.color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getXDistance() {
/* 136 */     return this.xDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXDistance(float distance) {
/* 146 */     this.xDistance = distance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getYDistance() {
/* 156 */     return this.yDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYDistance(float distance) {
/* 166 */     this.yDistance = distance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlurKernelSize() {
/* 175 */     return this.blurKernelSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlurKernelSize(int blurKernelSize) {
/* 184 */     this.blurKernelSize = blurKernelSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlurPasses() {
/* 193 */     return this.blurPasses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlurPasses(int blurPasses) {
/* 202 */     this.blurPasses = blurPasses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getOpacity() {
/* 211 */     return this.opacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpacity(float opacity) {
/* 220 */     this.opacity = opacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 227 */     return "Shadow";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getValues() {
/* 234 */     List<ConfigurableEffect.Value> values = new ArrayList();
/* 235 */     values.add(EffectUtil.colorValue("Color", this.color));
/* 236 */     values.add(EffectUtil.floatValue("Opacity", this.opacity, 0.0F, 1.0F, "This setting sets the translucency of the shadow."));
/* 237 */     values.add(EffectUtil.floatValue("X distance", this.xDistance, Float.MIN_VALUE, Float.MAX_VALUE, "This setting is the amount of pixels to offset the shadow on the x axis. The glyphs will need padding so the shadow doesn't get clipped."));
/*     */     
/* 239 */     values.add(EffectUtil.floatValue("Y distance", this.yDistance, Float.MIN_VALUE, Float.MAX_VALUE, "This setting is the amount of pixels to offset the shadow on the y axis. The glyphs will need padding so the shadow doesn't get clipped."));
/*     */ 
/*     */     
/* 242 */     List<String[]> options = new ArrayList();
/* 243 */     options.add(new String[] { "None", "0" });
/* 244 */     for (int i = 2; i < 16; i++) {
/* 245 */       options.add(new String[] { String.valueOf(i) });
/* 246 */     }  String[][] optionsArray = options.<String[]>toArray(new String[options.size()][]);
/* 247 */     values.add(EffectUtil.optionValue("Blur kernel size", String.valueOf(this.blurKernelSize), optionsArray, "This setting controls how many neighboring pixels are used to blur the shadow. Set to \"None\" for no blur."));
/*     */ 
/*     */     
/* 250 */     values.add(EffectUtil.intValue("Blur passes", this.blurPasses, "The setting is the number of times to apply a blur to the shadow. Set to \"0\" for no blur."));
/*     */     
/* 252 */     return values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValues(List values) {
/* 259 */     for (Iterator<ConfigurableEffect.Value> iter = values.iterator(); iter.hasNext(); ) {
/* 260 */       ConfigurableEffect.Value value = iter.next();
/* 261 */       if (value.getName().equals("Color")) {
/* 262 */         this.color = (Color)value.getObject(); continue;
/* 263 */       }  if (value.getName().equals("Opacity")) {
/* 264 */         this.opacity = ((Float)value.getObject()).floatValue(); continue;
/* 265 */       }  if (value.getName().equals("X distance")) {
/* 266 */         this.xDistance = ((Float)value.getObject()).floatValue(); continue;
/* 267 */       }  if (value.getName().equals("Y distance")) {
/* 268 */         this.yDistance = ((Float)value.getObject()).floatValue(); continue;
/* 269 */       }  if (value.getName().equals("Blur kernel size")) {
/* 270 */         this.blurKernelSize = Integer.parseInt((String)value.getObject()); continue;
/* 271 */       }  if (value.getName().equals("Blur passes")) {
/* 272 */         this.blurPasses = ((Integer)value.getObject()).intValue();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[][] generateGaussianBlurKernels(int level) {
/* 284 */     float[][] pascalsTriangle = generatePascalsTriangle(level);
/* 285 */     float[][] gaussianTriangle = new float[pascalsTriangle.length][];
/* 286 */     for (int i = 0; i < gaussianTriangle.length; i++) {
/* 287 */       float total = 0.0F;
/* 288 */       gaussianTriangle[i] = new float[(pascalsTriangle[i]).length];
/* 289 */       for (int j = 0; j < (pascalsTriangle[i]).length; j++)
/* 290 */         total += pascalsTriangle[i][j]; 
/* 291 */       float coefficient = 1.0F / total;
/* 292 */       for (int k = 0; k < (pascalsTriangle[i]).length; k++)
/* 293 */         gaussianTriangle[i][k] = coefficient * pascalsTriangle[i][k]; 
/*     */     } 
/* 295 */     return gaussianTriangle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[][] generatePascalsTriangle(int level) {
/* 305 */     if (level < 2) level = 2; 
/* 306 */     float[][] triangle = new float[level][];
/* 307 */     triangle[0] = new float[1];
/* 308 */     triangle[1] = new float[2];
/* 309 */     triangle[0][0] = 1.0F;
/* 310 */     triangle[1][0] = 1.0F;
/* 311 */     triangle[1][1] = 1.0F;
/* 312 */     for (int i = 2; i < level; i++) {
/* 313 */       triangle[i] = new float[i + 1];
/* 314 */       triangle[i][0] = 1.0F;
/* 315 */       triangle[i][i] = 1.0F;
/* 316 */       for (int j = 1; j < (triangle[i]).length - 1; j++)
/* 317 */         triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j]; 
/*     */     } 
/* 319 */     return triangle;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\effects\ShadowEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */