/*    */ package net.minecraft.network.status.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.status.INetHandlerStatusClient;
/*    */ 
/*    */ 
/*    */ public class S01PacketPong
/*    */   implements Packet<INetHandlerStatusClient>
/*    */ {
/*    */   private long clientTime;
/*    */   
/*    */   public S01PacketPong() {}
/*    */   
/*    */   public S01PacketPong(long time) {
/* 18 */     this.clientTime = time;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.clientTime = buf.readLong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeLong(this.clientTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerStatusClient handler) {
/* 42 */     handler.handlePong(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\status\server\S01PacketPong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */