/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.nio.FloatBuffer;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Color
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1393939L;
/*  19 */   protected transient SGL GL = Renderer.get();
/*     */ 
/*     */   
/*  22 */   public static final Color transparent = new Color(0.0F, 0.0F, 0.0F, 0.0F);
/*     */   
/*  24 */   public static final Color white = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   
/*  26 */   public static final Color yellow = new Color(1.0F, 1.0F, 0.0F, 1.0F);
/*     */   
/*  28 */   public static final Color red = new Color(1.0F, 0.0F, 0.0F, 1.0F);
/*     */   
/*  30 */   public static final Color blue = new Color(0.0F, 0.0F, 1.0F, 1.0F);
/*     */   
/*  32 */   public static final Color green = new Color(0.0F, 1.0F, 0.0F, 1.0F);
/*     */   
/*  34 */   public static final Color black = new Color(0.0F, 0.0F, 0.0F, 1.0F);
/*     */   
/*  36 */   public static final Color gray = new Color(0.5F, 0.5F, 0.5F, 1.0F);
/*     */   
/*  38 */   public static final Color cyan = new Color(0.0F, 1.0F, 1.0F, 1.0F);
/*     */   
/*  40 */   public static final Color darkGray = new Color(0.3F, 0.3F, 0.3F, 1.0F);
/*     */   
/*  42 */   public static final Color lightGray = new Color(0.7F, 0.7F, 0.7F, 1.0F);
/*     */   
/*  44 */   public static final Color pink = new Color(255, 175, 175, 255);
/*     */   
/*  46 */   public static final Color orange = new Color(255, 200, 0, 255);
/*     */   
/*  48 */   public static final Color magenta = new Color(255, 0, 255, 255);
/*     */ 
/*     */   
/*     */   public float r;
/*     */   
/*     */   public float g;
/*     */   
/*     */   public float b;
/*     */   
/*  57 */   public float a = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(Color color) {
/*  65 */     this.r = color.r;
/*  66 */     this.g = color.g;
/*  67 */     this.b = color.b;
/*  68 */     this.a = color.a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(FloatBuffer buffer) {
/*  77 */     this.r = buffer.get();
/*  78 */     this.g = buffer.get();
/*  79 */     this.b = buffer.get();
/*  80 */     this.a = buffer.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(float r, float g, float b) {
/*  91 */     this.r = r;
/*  92 */     this.g = g;
/*  93 */     this.b = b;
/*  94 */     this.a = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(float r, float g, float b, float a) {
/* 106 */     this.r = Math.min(r, 1.0F);
/* 107 */     this.g = Math.min(g, 1.0F);
/* 108 */     this.b = Math.min(b, 1.0F);
/* 109 */     this.a = Math.min(a, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(int r, int g, int b) {
/* 120 */     this.r = r / 255.0F;
/* 121 */     this.g = g / 255.0F;
/* 122 */     this.b = b / 255.0F;
/* 123 */     this.a = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(int r, int g, int b, int a) {
/* 135 */     this.r = r / 255.0F;
/* 136 */     this.g = g / 255.0F;
/* 137 */     this.b = b / 255.0F;
/* 138 */     this.a = a / 255.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(int value) {
/* 149 */     int r = (value & 0xFF0000) >> 16;
/* 150 */     int g = (value & 0xFF00) >> 8;
/* 151 */     int b = value & 0xFF;
/* 152 */     int a = (value & 0xFF000000) >> 24;
/*     */     
/* 154 */     if (a < 0) {
/* 155 */       a += 256;
/*     */     }
/* 157 */     if (a == 0) {
/* 158 */       a = 255;
/*     */     }
/*     */     
/* 161 */     this.r = r / 255.0F;
/* 162 */     this.g = g / 255.0F;
/* 163 */     this.b = b / 255.0F;
/* 164 */     this.a = a / 255.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color decode(String nm) {
/* 175 */     return new Color(Integer.decode(nm).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() {
/* 182 */     this.GL.glColor4f(this.r, this.g, this.b, this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 189 */     return (int)(this.r + this.g + this.b + this.a) * 255;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 196 */     if (other instanceof Color) {
/* 197 */       Color o = (Color)other;
/* 198 */       return (o.r == this.r && o.g == this.g && o.b == this.b && o.a == this.a);
/*     */     } 
/*     */     
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 208 */     return "Color (" + this.r + "," + this.g + "," + this.b + "," + this.a + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color darker() {
/* 217 */     return darker(0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color darker(float scale) {
/* 227 */     scale = 1.0F - scale;
/* 228 */     Color temp = new Color(this.r * scale, this.g * scale, this.b * scale, this.a);
/*     */     
/* 230 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color brighter() {
/* 239 */     return brighter(0.2F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRed() {
/* 248 */     return (int)(this.r * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGreen() {
/* 257 */     return (int)(this.g * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlue() {
/* 266 */     return (int)(this.b * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAlpha() {
/* 275 */     return (int)(this.a * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRedByte() {
/* 284 */     return (int)(this.r * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGreenByte() {
/* 293 */     return (int)(this.g * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlueByte() {
/* 302 */     return (int)(this.b * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAlphaByte() {
/* 311 */     return (int)(this.a * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color brighter(float scale) {
/* 321 */     scale++;
/* 322 */     Color temp = new Color(this.r * scale, this.g * scale, this.b * scale, this.a);
/*     */     
/* 324 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color multiply(Color c) {
/* 334 */     return new Color(this.r * c.r, this.g * c.g, this.b * c.b, this.a * c.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Color c) {
/* 343 */     this.r += c.r;
/* 344 */     this.g += c.g;
/* 345 */     this.b += c.b;
/* 346 */     this.a += c.a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scale(float value) {
/* 355 */     this.r *= value;
/* 356 */     this.g *= value;
/* 357 */     this.b *= value;
/* 358 */     this.a *= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color addToCopy(Color c) {
/* 368 */     Color copy = new Color(this.r, this.g, this.b, this.a);
/* 369 */     copy.r += c.r;
/* 370 */     copy.g += c.g;
/* 371 */     copy.b += c.b;
/* 372 */     copy.a += c.a;
/*     */     
/* 374 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color scaleCopy(float value) {
/* 384 */     Color copy = new Color(this.r, this.g, this.b, this.a);
/* 385 */     copy.r *= value;
/* 386 */     copy.g *= value;
/* 387 */     copy.b *= value;
/* 388 */     copy.a *= value;
/*     */     
/* 390 */     return copy;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\Color.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */