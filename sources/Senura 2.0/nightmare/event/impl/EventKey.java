/*    */ package nightmare.event.impl;
/*    */ 
/*    */ import nightmare.event.Event;
/*    */ 
/*    */ public class EventKey
/*    */   extends Event {
/*    */   public int getKey() {
/*  8 */     return this.key;
/*    */   }
/*    */   private int key;
/*    */   public void setKey(int key) {
/* 12 */     this.key = key;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EventKey(int key) {
/* 18 */     this.key = key;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\event\impl\EventKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */