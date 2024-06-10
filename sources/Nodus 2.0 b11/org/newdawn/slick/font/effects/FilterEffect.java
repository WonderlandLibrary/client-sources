/*  1:   */ package org.newdawn.slick.font.effects;
/*  2:   */ 
/*  3:   */ import java.awt.Graphics;
/*  4:   */ import java.awt.Graphics2D;
/*  5:   */ import java.awt.image.BufferedImage;
/*  6:   */ import java.awt.image.BufferedImageOp;
/*  7:   */ import org.newdawn.slick.UnicodeFont;
/*  8:   */ import org.newdawn.slick.font.Glyph;
/*  9:   */ 
/* 10:   */ public class FilterEffect
/* 11:   */   implements Effect
/* 12:   */ {
/* 13:   */   private BufferedImageOp filter;
/* 14:   */   
/* 15:   */   public FilterEffect() {}
/* 16:   */   
/* 17:   */   public FilterEffect(BufferedImageOp filter)
/* 18:   */   {
/* 19:33 */     this.filter = filter;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph)
/* 23:   */   {
/* 24:40 */     BufferedImage scratchImage = EffectUtil.getScratchImage();
/* 25:41 */     this.filter.filter(image, scratchImage);
/* 26:42 */     image.getGraphics().drawImage(scratchImage, 0, 0, null);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public BufferedImageOp getFilter()
/* 30:   */   {
/* 31:51 */     return this.filter;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setFilter(BufferedImageOp filter)
/* 35:   */   {
/* 36:60 */     this.filter = filter;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.effects.FilterEffect
 * JD-Core Version:    0.7.0.1
 */