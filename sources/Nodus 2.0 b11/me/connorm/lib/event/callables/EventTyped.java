/*  1:   */ package me.connorm.lib.event.callables;
/*  2:   */ 
/*  3:   */ import me.connorm.lib.event.Event;
/*  4:   */ import me.connorm.lib.event.Typed;
/*  5:   */ 
/*  6:   */ public abstract class EventTyped
/*  7:   */   implements Event, Typed
/*  8:   */ {
/*  9:   */   private final byte type;
/* 10:   */   
/* 11:   */   protected EventTyped(byte eventType)
/* 12:   */   {
/* 13:25 */     this.type = eventType;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public byte getType()
/* 17:   */   {
/* 18:34 */     return this.type;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.lib.event.callables.EventTyped
 * JD-Core Version:    0.7.0.1
 */