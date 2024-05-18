/*    */ package me.eagler.event.events;
/*    */ 
/*    */ import me.eagler.event.stuff.EventCancellable;
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ public class PacketEvent
/*    */   extends EventCancellable {
/*    */   private boolean cancel;
/*    */   public static Packet packet;
/*    */   
/*    */   public PacketEvent(Packet packet) {
/* 12 */     PacketEvent.packet = packet;
/*    */   }
/*    */   
/*    */   public Packet getPacket() {
/* 16 */     return packet;
/*    */   }
/*    */   
/*    */   public static Packet getPacket2() {
/* 20 */     return packet;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 24 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 28 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */   public void setPacket(Packet packet) {
/* 32 */     PacketEvent.packet = packet;
/*    */   }
/*    */   
/*    */   public static Packet getPacket1() {
/* 36 */     return packet;
/*    */   }
/*    */   
/*    */   public static void setPacket1(Packet packet) {
/* 40 */     PacketEvent.packet = packet;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\event\events\PacketEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */