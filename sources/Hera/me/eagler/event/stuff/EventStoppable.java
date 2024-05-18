/*    */ package me.eagler.event.stuff;
/*    */ 
/*    */ import me.eagler.event.Event;
/*    */ 
/*    */ public abstract class EventStoppable implements Event {
/*    */   private boolean stopped;
/*    */   
/*    */   public void stop() {
/*  9 */     this.stopped = true;
/*    */   }
/*    */   
/*    */   public boolean isStopped() {
/* 13 */     return this.stopped;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\event\stuff\EventStoppable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */