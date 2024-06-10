/*  1:   */ package org.darkstorm.minecraft.gui.component.basic;
/*  2:   */ 
/*  3:   */ import org.darkstorm.minecraft.gui.component.AbstractComponent;
/*  4:   */ import org.darkstorm.minecraft.gui.component.Label;
/*  5:   */ import org.darkstorm.minecraft.gui.component.Label.TextAlignment;
/*  6:   */ 
/*  7:   */ public class BasicLabel
/*  8:   */   extends AbstractComponent
/*  9:   */   implements Label
/* 10:   */ {
/* 11:   */   protected String text;
/* 12:   */   protected int index;
/* 13: 9 */   protected Label.TextAlignment horizontalAlignment = Label.TextAlignment.LEFT;
/* 14:10 */   protected Label.TextAlignment verticalAlignment = Label.TextAlignment.CENTER;
/* 15:   */   
/* 16:   */   public BasicLabel() {}
/* 17:   */   
/* 18:   */   public BasicLabel(String text)
/* 19:   */   {
/* 20:16 */     this.text = text;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getText()
/* 24:   */   {
/* 25:20 */     return this.text;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setText(String text)
/* 29:   */   {
/* 30:24 */     this.text = text;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Label.TextAlignment getHorizontalAlignment()
/* 34:   */   {
/* 35:28 */     return this.horizontalAlignment;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Label.TextAlignment getVerticalAlignment()
/* 39:   */   {
/* 40:32 */     return this.verticalAlignment;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setHorizontalAlignment(Label.TextAlignment alignment)
/* 44:   */   {
/* 45:36 */     this.horizontalAlignment = alignment;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setVerticalAlignment(Label.TextAlignment alignment)
/* 49:   */   {
/* 50:40 */     this.verticalAlignment = alignment;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.basic.BasicLabel
 * JD-Core Version:    0.7.0.1
 */