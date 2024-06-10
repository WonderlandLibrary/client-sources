/*  1:   */ package me.connorm.lib.event;
/*  2:   */ 
/*  3:   */ public abstract class EventStoppable
/*  4:   */   implements Event
/*  5:   */ {
/*  6:   */   private boolean stopped;
/*  7:   */   
/*  8:   */   public void stop()
/*  9:   */   {
/* 10:26 */     this.stopped = true;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean isStopped()
/* 14:   */   {
/* 15:37 */     return this.stopped;
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.lib.event.EventStoppable
 * JD-Core Version:    0.7.0.1
 */