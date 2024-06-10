/*  1:   */ package org.newdawn.slick.command;
/*  2:   */ 
/*  3:   */ public class KeyControl
/*  4:   */   implements Control
/*  5:   */ {
/*  6:   */   private int keycode;
/*  7:   */   
/*  8:   */   public KeyControl(int keycode)
/*  9:   */   {
/* 10:19 */     this.keycode = keycode;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean equals(Object o)
/* 14:   */   {
/* 15:26 */     if ((o instanceof KeyControl)) {
/* 16:27 */       return ((KeyControl)o).keycode == this.keycode;
/* 17:   */     }
/* 18:30 */     return false;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int hashCode()
/* 22:   */   {
/* 23:37 */     return this.keycode;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.command.KeyControl
 * JD-Core Version:    0.7.0.1
 */