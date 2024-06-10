/*  1:   */ package org.darkstorm.minecraft.gui.component.basic;
/*  2:   */ 
/*  3:   */ import org.darkstorm.minecraft.gui.component.Button;
/*  4:   */ import org.darkstorm.minecraft.gui.component.ButtonGroup;
/*  5:   */ import org.darkstorm.minecraft.gui.component.RadioButton;
/*  6:   */ import org.darkstorm.minecraft.gui.listener.ComponentListener;
/*  7:   */ import org.darkstorm.minecraft.gui.listener.SelectableComponentListener;
/*  8:   */ 
/*  9:   */ public class BasicRadioButton
/* 10:   */   extends BasicButton
/* 11:   */   implements RadioButton
/* 12:   */ {
/* 13: 7 */   private boolean selected = false;
/* 14:   */   
/* 15:   */   public BasicRadioButton() {}
/* 16:   */   
/* 17:   */   public BasicRadioButton(String text)
/* 18:   */   {
/* 19:13 */     this.text = text;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void press()
/* 23:   */   {
/* 24:18 */     this.selected = true;
/* 25:19 */     for (Button button : getGroup().getButtons()) {
/* 26:20 */       if ((!equals(button)) && ((button instanceof RadioButton))) {
/* 27:22 */         ((RadioButton)button).setSelected(false);
/* 28:   */       }
/* 29:   */     }
/* 30:24 */     super.press();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean isSelected()
/* 34:   */   {
/* 35:29 */     return this.selected;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setSelected(boolean selected)
/* 39:   */   {
/* 40:34 */     this.selected = selected;
/* 41:35 */     for (ComponentListener listener : getListeners()) {
/* 42:36 */       if ((listener instanceof SelectableComponentListener)) {
/* 43:   */         try
/* 44:   */         {
/* 45:39 */           ((SelectableComponentListener)listener).onSelectedStateChanged(this);
/* 46:   */         }
/* 47:   */         catch (Exception exception)
/* 48:   */         {
/* 49:41 */           exception.printStackTrace();
/* 50:   */         }
/* 51:   */       }
/* 52:   */     }
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void addSelectableComponentListener(SelectableComponentListener listener)
/* 56:   */   {
/* 57:50 */     addListener(listener);
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void removeSelectableComponentListener(SelectableComponentListener listener)
/* 61:   */   {
/* 62:56 */     removeListener(listener);
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.basic.BasicRadioButton
 * JD-Core Version:    0.7.0.1
 */