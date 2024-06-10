/* 1:  */ package org.darkstorm.minecraft.gui.component;
/* 2:  */ 
/* 3:  */ public abstract interface Label
/* 4:  */   extends TextComponent
/* 5:  */ {
/* 6:  */   public abstract TextAlignment getHorizontalAlignment();
/* 7:  */   
/* 8:  */   public abstract TextAlignment getVerticalAlignment();
/* 9:  */   
/* ::  */   public abstract void setHorizontalAlignment(TextAlignment paramTextAlignment);
/* ;:  */   
/* <:  */   public abstract void setVerticalAlignment(TextAlignment paramTextAlignment);
/* =:  */   
/* >:  */   public static enum TextAlignment
/* ?:  */   {
/* @:5 */     CENTER,  LEFT,  RIGHT,  TOP,  BOTTOM;
/* A:  */   }
/* B:  */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.Label
 * JD-Core Version:    0.7.0.1
 */