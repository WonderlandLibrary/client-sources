/*    */ package org.newdawn.slick.font.effects;
/*    */ 
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.awt.image.BufferedImageOp;
/*    */ import org.newdawn.slick.UnicodeFont;
/*    */ import org.newdawn.slick.font.Glyph;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FilterEffect
/*    */   implements Effect
/*    */ {
/*    */   private BufferedImageOp filter;
/*    */   
/*    */   public FilterEffect() {}
/*    */   
/*    */   public FilterEffect(BufferedImageOp filter) {
/* 33 */     this.filter = filter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph) {
/* 40 */     BufferedImage scratchImage = EffectUtil.getScratchImage();
/* 41 */     this.filter.filter(image, scratchImage);
/* 42 */     image.getGraphics().drawImage(scratchImage, 0, 0, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BufferedImageOp getFilter() {
/* 51 */     return this.filter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFilter(BufferedImageOp filter) {
/* 60 */     this.filter = filter;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\effects\FilterEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */