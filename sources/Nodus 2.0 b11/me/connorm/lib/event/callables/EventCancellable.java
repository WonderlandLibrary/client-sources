/*  1:   */ package me.connorm.lib.event.callables;
/*  2:   */ 
/*  3:   */ import me.connorm.lib.event.Cancellable;
/*  4:   */ import me.connorm.lib.event.Event;
/*  5:   */ 
/*  6:   */ public abstract class EventCancellable
/*  7:   */   implements Event, Cancellable
/*  8:   */ {
/*  9:   */   private boolean cancelled;
/* 10:   */   
/* 11:   */   public boolean isCancelled()
/* 12:   */   {
/* 13:25 */     return this.cancelled;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setCancelled(boolean state)
/* 17:   */   {
/* 18:34 */     this.cancelled = state;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.lib.event.callables.EventCancellable
 * JD-Core Version:    0.7.0.1
 */