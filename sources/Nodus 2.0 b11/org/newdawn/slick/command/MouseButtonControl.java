/*  1:   */ package org.newdawn.slick.command;
/*  2:   */ 
/*  3:   */ public class MouseButtonControl
/*  4:   */   implements Control
/*  5:   */ {
/*  6:   */   private int button;
/*  7:   */   
/*  8:   */   public MouseButtonControl(int button)
/*  9:   */   {
/* 10:18 */     this.button = button;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean equals(Object o)
/* 14:   */   {
/* 15:25 */     if ((o instanceof MouseButtonControl)) {
/* 16:27 */       return ((MouseButtonControl)o).button == this.button;
/* 17:   */     }
/* 18:30 */     return false;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int hashCode()
/* 22:   */   {
/* 23:37 */     return this.button;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.command.MouseButtonControl
 * JD-Core Version:    0.7.0.1
 */