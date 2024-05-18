/*    */ package me.eagler.event.stuff;
/*    */ 
/*    */ import me.eagler.event.Event;
/*    */ 
/*    */ public abstract class EventTyped implements Event, Typed {
/*    */   private final byte type;
/*    */   
/*    */   protected EventTyped(byte eventType) {
/*  9 */     this.type = eventType;
/*    */   }
/*    */   
/*    */   public byte getType() {
/* 13 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\event\stuff\EventTyped.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */