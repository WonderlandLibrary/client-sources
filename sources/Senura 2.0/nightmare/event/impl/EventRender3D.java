/*    */ package nightmare.event.impl;
/*    */ 
/*    */ import nightmare.event.Event;
/*    */ 
/*    */ public class EventRender3D
/*    */   extends Event
/*    */ {
/*    */   public float partialTicks;
/*    */   
/*    */   public EventRender3D(float partialTicks) {
/* 11 */     this.partialTicks = partialTicks;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPartialTicks() {
/* 16 */     return this.partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\event\impl\EventRender3D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */