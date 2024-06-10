/*  1:   */ package org.darkstorm.minecraft.gui.component.basic;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.darkstorm.minecraft.gui.component.Button;
/*  6:   */ import org.darkstorm.minecraft.gui.component.ButtonGroup;
/*  7:   */ 
/*  8:   */ public class BasicButtonGroup
/*  9:   */   implements ButtonGroup
/* 10:   */ {
/* 11: 8 */   private List<Button> buttons = new ArrayList();
/* 12:   */   
/* 13:   */   public void addButton(Button button)
/* 14:   */   {
/* 15:12 */     if (button == null) {
/* 16:13 */       throw new NullPointerException();
/* 17:   */     }
/* 18:14 */     synchronized (this.buttons)
/* 19:   */     {
/* 20:15 */       this.buttons.add(button);
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void removeButton(Button button)
/* 25:   */   {
/* 26:21 */     if (button == null) {
/* 27:22 */       throw new NullPointerException();
/* 28:   */     }
/* 29:23 */     synchronized (this.buttons)
/* 30:   */     {
/* 31:24 */       this.buttons.remove(button);
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Button[] getButtons()
/* 36:   */   {
/* 37:30 */     synchronized (this.buttons)
/* 38:   */     {
/* 39:31 */       return (Button[])this.buttons.toArray(new Button[this.buttons.size()]);
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.basic.BasicButtonGroup
 * JD-Core Version:    0.7.0.1
 */