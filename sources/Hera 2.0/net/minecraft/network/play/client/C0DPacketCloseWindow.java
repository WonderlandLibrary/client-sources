/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class C0DPacketCloseWindow
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int windowId;
/*    */   
/*    */   public C0DPacketCloseWindow() {}
/*    */   
/*    */   public C0DPacketCloseWindow(int windowId) {
/* 18 */     this.windowId = windowId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 26 */     handler.processCloseWindow(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.windowId = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeByte(this.windowId);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\play\client\C0DPacketCloseWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */