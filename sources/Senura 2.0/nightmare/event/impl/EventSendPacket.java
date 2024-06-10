/*    */ package nightmare.event.impl;
/*    */ 
/*    */ import net.minecraft.network.Packet;
/*    */ import nightmare.event.Event;
/*    */ 
/*    */ public class EventSendPacket
/*    */   extends Event {
/*    */   private Packet<?> packet;
/*    */   
/*    */   public EventSendPacket(Packet<?> packet) {
/* 11 */     this.packet = packet;
/*    */   }
/*    */   
/*    */   public Packet<?> getPacket() {
/* 15 */     return this.packet;
/*    */   }
/*    */   
/*    */   public void setPacket(Packet<?> packet) {
/* 19 */     this.packet = packet;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\event\impl\EventSendPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */