/*    */ package net.minecraft.network.login.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*    */ 
/*    */ 
/*    */ public class S03PacketEnableCompression
/*    */   implements Packet<INetHandlerLoginClient>
/*    */ {
/*    */   private int compressionTreshold;
/*    */   
/*    */   public S03PacketEnableCompression() {}
/*    */   
/*    */   public S03PacketEnableCompression(int compressionTresholdIn) {
/* 18 */     this.compressionTreshold = compressionTresholdIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.compressionTreshold = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeVarIntToBuffer(this.compressionTreshold);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginClient handler) {
/* 42 */     handler.handleEnableCompression(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCompressionTreshold() {
/* 47 */     return this.compressionTreshold;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\login\server\S03PacketEnableCompression.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */