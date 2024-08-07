/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ public class S40PacketDisconnect
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private IChatComponent reason;
/*    */   
/*    */   public S40PacketDisconnect() {}
/*    */   
/*    */   public S40PacketDisconnect(IChatComponent reasonIn) {
/* 19 */     this.reason = reasonIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.reason = buf.readChatComponent();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 35 */     buf.writeChatComponent(this.reason);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 43 */     handler.handleDisconnect(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatComponent getReason() {
/* 48 */     return this.reason;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\play\server\S40PacketDisconnect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */