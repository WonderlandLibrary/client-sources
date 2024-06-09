/*     */ package org.newdawn.slick.font;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.font.GlyphMetrics;
/*     */ import java.awt.font.GlyphVector;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.UnicodeFont;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Glyph
/*     */ {
/*     */   private int codePoint;
/*     */   private short width;
/*     */   private short height;
/*     */   private short yOffset;
/*     */   private boolean isMissing;
/*     */   private Shape shape;
/*     */   private Image image;
/*     */   
/*     */   public Glyph(int codePoint, Rectangle bounds, GlyphVector vector, int index, UnicodeFont unicodeFont) {
/*  43 */     this.codePoint = codePoint;
/*     */     
/*  45 */     GlyphMetrics metrics = vector.getGlyphMetrics(index);
/*  46 */     int lsb = (int)metrics.getLSB();
/*  47 */     if (lsb > 0) lsb = 0; 
/*  48 */     int rsb = (int)metrics.getRSB();
/*  49 */     if (rsb > 0) rsb = 0;
/*     */     
/*  51 */     int glyphWidth = bounds.width - lsb - rsb;
/*  52 */     int glyphHeight = bounds.height;
/*  53 */     if (glyphWidth > 0 && glyphHeight > 0) {
/*  54 */       int padTop = unicodeFont.getPaddingTop();
/*  55 */       int padRight = unicodeFont.getPaddingRight();
/*  56 */       int padBottom = unicodeFont.getPaddingBottom();
/*  57 */       int padLeft = unicodeFont.getPaddingLeft();
/*  58 */       int glyphSpacing = 1;
/*  59 */       this.width = (short)(glyphWidth + padLeft + padRight + glyphSpacing);
/*  60 */       this.height = (short)(glyphHeight + padTop + padBottom + glyphSpacing);
/*  61 */       this.yOffset = (short)(unicodeFont.getAscent() + bounds.y - padTop);
/*     */     } 
/*     */     
/*  64 */     this.shape = vector.getGlyphOutline(index, (-bounds.x + unicodeFont.getPaddingLeft()), (-bounds.y + unicodeFont.getPaddingTop()));
/*     */     
/*  66 */     this.isMissing = !unicodeFont.getFont().canDisplay((char)codePoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCodePoint() {
/*  75 */     return this.codePoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMissing() {
/*  84 */     return this.isMissing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  93 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 102 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape getShape() {
/* 112 */     return this.shape;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShape(Shape shape) {
/* 121 */     this.shape = shape;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage() {
/* 131 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImage(Image image) {
/* 140 */     this.image = image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYOffset() {
/* 150 */     return this.yOffset;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\Glyph.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */