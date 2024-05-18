/*    */ package me.eagler.event.stuff;
/*    */ 
/*    */ import me.eagler.event.Event;
/*    */ 
/*    */ public abstract class EventCancellable implements Event, Cancellable {
/*    */   private boolean cancelled;
/*    */   
/*    */   public boolean isCancelled() {
/*  9 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean state) {
/* 13 */     this.cancelled = state;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\event\stuff\EventCancellable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */