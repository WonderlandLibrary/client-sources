/*  1:   */ package org.darkstorm.minecraft.gui.component.basic;
/*  2:   */ 
/*  3:   */ import org.darkstorm.minecraft.gui.component.AbstractComponent;
/*  4:   */ import org.darkstorm.minecraft.gui.component.ComboBox;
/*  5:   */ import org.darkstorm.minecraft.gui.listener.ComboBoxListener;
/*  6:   */ import org.darkstorm.minecraft.gui.listener.ComponentListener;
/*  7:   */ import org.darkstorm.minecraft.gui.listener.SelectableComponentListener;
/*  8:   */ 
/*  9:   */ public class BasicComboBox
/* 10:   */   extends AbstractComponent
/* 11:   */   implements ComboBox
/* 12:   */ {
/* 13:   */   private String[] elements;
/* 14:   */   private int selectedIndex;
/* 15:   */   private boolean selected;
/* 16:   */   
/* 17:   */   public BasicComboBox()
/* 18:   */   {
/* 19:12 */     this.elements = new String[0];
/* 20:   */   }
/* 21:   */   
/* 22:   */   public BasicComboBox(String... elements)
/* 23:   */   {
/* 24:16 */     this.elements = elements;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String[] getElements()
/* 28:   */   {
/* 29:21 */     return this.elements;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setElements(String... elements)
/* 33:   */   {
/* 34:26 */     this.selectedIndex = 0;
/* 35:27 */     this.elements = elements;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getSelectedIndex()
/* 39:   */   {
/* 40:32 */     return this.selectedIndex;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setSelectedIndex(int selectedIndex)
/* 44:   */   {
/* 45:37 */     this.selectedIndex = selectedIndex;
/* 46:38 */     for (ComponentListener listener : getListeners()) {
/* 47:39 */       if ((listener instanceof ComboBoxListener)) {
/* 48:   */         try
/* 49:   */         {
/* 50:42 */           ((ComboBoxListener)listener).onComboBoxSelectionChanged(this);
/* 51:   */         }
/* 52:   */         catch (Exception exception)
/* 53:   */         {
/* 54:44 */           exception.printStackTrace();
/* 55:   */         }
/* 56:   */       }
/* 57:   */     }
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String getSelectedElement()
/* 61:   */   {
/* 62:52 */     return this.elements[this.selectedIndex];
/* 63:   */   }
/* 64:   */   
/* 65:   */   public boolean isSelected()
/* 66:   */   {
/* 67:57 */     return this.selected;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void setSelected(boolean selected)
/* 71:   */   {
/* 72:62 */     this.selected = selected;
/* 73:63 */     for (ComponentListener listener : getListeners()) {
/* 74:64 */       if ((listener instanceof SelectableComponentListener)) {
/* 75:   */         try
/* 76:   */         {
/* 77:66 */           ((SelectableComponentListener)listener).onSelectedStateChanged(this);
/* 78:   */         }
/* 79:   */         catch (Exception exception)
/* 80:   */         {
/* 81:68 */           exception.printStackTrace();
/* 82:   */         }
/* 83:   */       }
/* 84:   */     }
/* 85:   */   }
/* 86:   */   
/* 87:   */   public void addComboBoxListener(ComboBoxListener listener)
/* 88:   */   {
/* 89:76 */     addListener(listener);
/* 90:   */   }
/* 91:   */   
/* 92:   */   public void removeComboBoxListener(ComboBoxListener listener)
/* 93:   */   {
/* 94:81 */     removeListener(listener);
/* 95:   */   }
/* 96:   */   
/* 97:   */   public void addSelectableComponentListener(SelectableComponentListener listener)
/* 98:   */   {
/* 99:86 */     addListener(listener);
/* :0:   */   }
/* :1:   */   
/* :2:   */   public void removeSelectableComponentListener(SelectableComponentListener listener)
/* :3:   */   {
/* :4:91 */     removeListener(listener);
/* :5:   */   }
/* :6:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.basic.BasicComboBox
 * JD-Core Version:    0.7.0.1
 */