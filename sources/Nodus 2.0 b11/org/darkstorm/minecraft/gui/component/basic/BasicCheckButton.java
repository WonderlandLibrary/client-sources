/*  1:   */ package org.darkstorm.minecraft.gui.component.basic;
/*  2:   */ 
/*  3:   */ import org.darkstorm.minecraft.gui.component.CheckButton;
/*  4:   */ import org.darkstorm.minecraft.gui.listener.ComponentListener;
/*  5:   */ import org.darkstorm.minecraft.gui.listener.SelectableComponentListener;
/*  6:   */ 
/*  7:   */ public class BasicCheckButton
/*  8:   */   extends BasicButton
/*  9:   */   implements CheckButton
/* 10:   */ {
/* 11: 7 */   private boolean selected = false;
/* 12:   */   protected int index;
/* 13:   */   private int color;
/* 14:   */   
/* 15:   */   public BasicCheckButton() {}
/* 16:   */   
/* 17:   */   public BasicCheckButton(String text, int index)
/* 18:   */   {
/* 19:17 */     this.index = index;
/* 20:18 */     this.text = text;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void press()
/* 24:   */   {
/* 25:23 */     this.selected = (!this.selected);
/* 26:24 */     super.press();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean isSelected()
/* 30:   */   {
/* 31:29 */     return this.selected;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setSelected(boolean selected)
/* 35:   */   {
/* 36:34 */     this.selected = selected;
/* 37:35 */     for (ComponentListener listener : getListeners()) {
/* 38:36 */       if ((listener instanceof SelectableComponentListener)) {
/* 39:   */         try
/* 40:   */         {
/* 41:38 */           ((SelectableComponentListener)listener).onSelectedStateChanged(this);
/* 42:   */         }
/* 43:   */         catch (Exception exception)
/* 44:   */         {
/* 45:40 */           exception.printStackTrace();
/* 46:   */         }
/* 47:   */       }
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void addSelectableComponentListener(SelectableComponentListener listener)
/* 52:   */   {
/* 53:48 */     addListener(listener);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void removeSelectableComponentListener(SelectableComponentListener listener)
/* 57:   */   {
/* 58:53 */     removeListener(listener);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int getIndex()
/* 62:   */   {
/* 63:58 */     return this.index;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.basic.BasicCheckButton
 * JD-Core Version:    0.7.0.1
 */