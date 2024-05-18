/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*    */ import net.minecraft.network.handshake.client.C00Handshake;
/*    */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer {
/*    */   private final MinecraftServer server;
/*    */   private final NetworkManager networkManager;
/*    */   
/*    */   public NetHandlerHandshakeTCP(MinecraftServer serverIn, NetworkManager netManager) {
/* 19 */     this.server = serverIn;
/* 20 */     this.networkManager = netManager;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processHandshake(C00Handshake packetIn) {
/* 30 */     switch (packetIn.getRequestedState()) {
/*    */       
/*    */       case LOGIN:
/* 33 */         this.networkManager.setConnectionState(EnumConnectionState.LOGIN);
/*    */         
/* 35 */         if (packetIn.getProtocolVersion() > 47) {
/*    */           
/* 37 */           ChatComponentText chatcomponenttext = new ChatComponentText("Outdated server! I'm still on 1.8.8");
/* 38 */           this.networkManager.sendPacket((Packet)new S00PacketDisconnect((IChatComponent)chatcomponenttext));
/* 39 */           this.networkManager.closeChannel((IChatComponent)chatcomponenttext);
/*    */         }
/* 41 */         else if (packetIn.getProtocolVersion() < 47) {
/*    */           
/* 43 */           ChatComponentText chatcomponenttext1 = new ChatComponentText("Outdated client! Please use 1.8.8");
/* 44 */           this.networkManager.sendPacket((Packet)new S00PacketDisconnect((IChatComponent)chatcomponenttext1));
/* 45 */           this.networkManager.closeChannel((IChatComponent)chatcomponenttext1);
/*    */         }
/*    */         else {
/*    */           
/* 49 */           this.networkManager.setNetHandler((INetHandler)new NetHandlerLoginServer(this.server, this.networkManager));
/*    */         } 
/*    */         return;
/*    */ 
/*    */       
/*    */       case STATUS:
/* 55 */         this.networkManager.setConnectionState(EnumConnectionState.STATUS);
/* 56 */         this.networkManager.setNetHandler((INetHandler)new NetHandlerStatusServer(this.server, this.networkManager));
/*    */         return;
/*    */     } 
/*    */     
/* 60 */     throw new UnsupportedOperationException("Invalid intention " + packetIn.getRequestedState());
/*    */   }
/*    */   
/*    */   public void onDisconnect(IChatComponent reason) {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\server\network\NetHandlerHandshakeTCP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */