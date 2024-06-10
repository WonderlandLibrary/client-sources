/*  1:   */ package me.connorm.Nodus.event.other;
/*  2:   */ 
/*  3:   */ import me.connorm.lib.event.Event;
/*  4:   */ 
/*  5:   */ public class EventKeyPress
/*  6:   */   implements Event
/*  7:   */ {
/*  8:   */   private int pressedKey;
/*  9:   */   
/* 10:   */   public EventKeyPress(int pressedKey)
/* 11:   */   {
/* 12:11 */     this.pressedKey = pressedKey;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public int getKeyCode()
/* 16:   */   {
/* 17:16 */     return this.pressedKey;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.event.other.EventKeyPress
 * JD-Core Version:    0.7.0.1
 */