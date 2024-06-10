/*   1:    */ package org.newdawn.slick.font;
/*   2:    */ 
/*   3:    */ import java.awt.Font;
/*   4:    */ import java.awt.Rectangle;
/*   5:    */ import java.awt.Shape;
/*   6:    */ import java.awt.font.GlyphMetrics;
/*   7:    */ import java.awt.font.GlyphVector;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.UnicodeFont;
/*  10:    */ 
/*  11:    */ public class Glyph
/*  12:    */ {
/*  13:    */   private int codePoint;
/*  14:    */   private short width;
/*  15:    */   private short height;
/*  16:    */   private short yOffset;
/*  17:    */   private boolean isMissing;
/*  18:    */   private Shape shape;
/*  19:    */   private Image image;
/*  20:    */   
/*  21:    */   public Glyph(int codePoint, Rectangle bounds, GlyphVector vector, int index, UnicodeFont unicodeFont)
/*  22:    */   {
/*  23: 43 */     this.codePoint = codePoint;
/*  24:    */     
/*  25: 45 */     GlyphMetrics metrics = vector.getGlyphMetrics(index);
/*  26: 46 */     int lsb = (int)metrics.getLSB();
/*  27: 47 */     if (lsb > 0) {
/*  28: 47 */       lsb = 0;
/*  29:    */     }
/*  30: 48 */     int rsb = (int)metrics.getRSB();
/*  31: 49 */     if (rsb > 0) {
/*  32: 49 */       rsb = 0;
/*  33:    */     }
/*  34: 51 */     int glyphWidth = bounds.width - lsb - rsb;
/*  35: 52 */     int glyphHeight = bounds.height;
/*  36: 53 */     if ((glyphWidth > 0) && (glyphHeight > 0))
/*  37:    */     {
/*  38: 54 */       int padTop = unicodeFont.getPaddingTop();
/*  39: 55 */       int padRight = unicodeFont.getPaddingRight();
/*  40: 56 */       int padBottom = unicodeFont.getPaddingBottom();
/*  41: 57 */       int padLeft = unicodeFont.getPaddingLeft();
/*  42: 58 */       int glyphSpacing = 1;
/*  43: 59 */       this.width = ((short)(glyphWidth + padLeft + padRight + glyphSpacing));
/*  44: 60 */       this.height = ((short)(glyphHeight + padTop + padBottom + glyphSpacing));
/*  45: 61 */       this.yOffset = ((short)(unicodeFont.getAscent() + bounds.y - padTop));
/*  46:    */     }
/*  47: 64 */     this.shape = vector.getGlyphOutline(index, -bounds.x + unicodeFont.getPaddingLeft(), -bounds.y + unicodeFont.getPaddingTop());
/*  48:    */     
/*  49: 66 */     this.isMissing = (!unicodeFont.getFont().canDisplay((char)codePoint));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getCodePoint()
/*  53:    */   {
/*  54: 75 */     return this.codePoint;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isMissing()
/*  58:    */   {
/*  59: 84 */     return this.isMissing;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getWidth()
/*  63:    */   {
/*  64: 93 */     return this.width;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getHeight()
/*  68:    */   {
/*  69:102 */     return this.height;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Shape getShape()
/*  73:    */   {
/*  74:112 */     return this.shape;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setShape(Shape shape)
/*  78:    */   {
/*  79:121 */     this.shape = shape;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Image getImage()
/*  83:    */   {
/*  84:131 */     return this.image;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setImage(Image image)
/*  88:    */   {
/*  89:140 */     this.image = image;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getYOffset()
/*  93:    */   {
/*  94:150 */     return this.yOffset;
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.Glyph
 * JD-Core Version:    0.7.0.1
 */