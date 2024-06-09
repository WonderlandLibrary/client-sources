/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C17PacketCustomPayload
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private String channel;
/*    */   private PacketBuffer data;
/*    */   
/*    */   public C17PacketCustomPayload() {}
/*    */   
/*    */   public C17PacketCustomPayload(String channelIn, PacketBuffer dataIn) {
/* 20 */     this.channel = channelIn;
/* 21 */     this.data = dataIn;
/*    */     
/* 23 */     if (dataIn.writerIndex() > 32767)
/*    */     {
/* 25 */       throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.channel = buf.readStringFromBuffer(20);
/* 35 */     int i = buf.readableBytes();
/*    */     
/* 37 */     if (i >= 0 && i <= 32767) {
/*    */       
/* 39 */       this.data = new PacketBuffer(buf.readBytes(i));
/*    */     }
/*    */     else {
/*    */       
/* 43 */       throw new IOException("Payload may not be larger than 32767 bytes");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 52 */     buf.writeString(this.channel);
/* 53 */     buf.writeBytes((ByteBuf)this.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 61 */     handler.processVanilla250Packet(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getChannelName() {
/* 66 */     return this.channel;
/*    */   }
/*    */ 
/*    */   
/*    */   public PacketBuffer getBufferData() {
/* 71 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\play\client\C17PacketCustomPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */