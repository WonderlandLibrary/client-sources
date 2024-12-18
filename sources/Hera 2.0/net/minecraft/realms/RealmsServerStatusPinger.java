/*     */ package net.minecraft.realms;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.ServerStatusResponse;
/*     */ import net.minecraft.network.handshake.client.C00Handshake;
/*     */ import net.minecraft.network.status.INetHandlerStatusClient;
/*     */ import net.minecraft.network.status.client.C00PacketServerQuery;
/*     */ import net.minecraft.network.status.client.C01PacketPing;
/*     */ import net.minecraft.network.status.server.S00PacketServerInfo;
/*     */ import net.minecraft.network.status.server.S01PacketPong;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class RealmsServerStatusPinger {
/*  27 */   private static final Logger LOGGER = LogManager.getLogger();
/*  28 */   private final List<NetworkManager> connections = Collections.synchronizedList(Lists.newArrayList());
/*     */ 
/*     */   
/*     */   public void pingServer(final String p_pingServer_1_, final RealmsServerPing p_pingServer_2_) throws UnknownHostException {
/*  32 */     if (p_pingServer_1_ != null && !p_pingServer_1_.startsWith("0.0.0.0") && !p_pingServer_1_.isEmpty()) {
/*     */       
/*  34 */       RealmsServerAddress realmsserveraddress = RealmsServerAddress.parseString(p_pingServer_1_);
/*  35 */       final NetworkManager networkmanager = NetworkManager.func_181124_a(InetAddress.getByName(realmsserveraddress.getHost()), realmsserveraddress.getPort(), false);
/*  36 */       this.connections.add(networkmanager);
/*  37 */       networkmanager.setNetHandler((INetHandler)new INetHandlerStatusClient()
/*     */           {
/*     */             private boolean field_154345_e = false;
/*     */             
/*     */             public void handleServerInfo(S00PacketServerInfo packetIn) {
/*  42 */               ServerStatusResponse serverstatusresponse = packetIn.getResponse();
/*     */               
/*  44 */               if (serverstatusresponse.getPlayerCountData() != null) {
/*     */                 
/*  46 */                 p_pingServer_2_.nrOfPlayers = String.valueOf(serverstatusresponse.getPlayerCountData().getOnlinePlayerCount());
/*     */                 
/*  48 */                 if (ArrayUtils.isNotEmpty((Object[])serverstatusresponse.getPlayerCountData().getPlayers()))
/*     */                 {
/*  50 */                   StringBuilder stringbuilder = new StringBuilder(); byte b; int i;
/*     */                   GameProfile[] arrayOfGameProfile;
/*  52 */                   for (i = (arrayOfGameProfile = serverstatusresponse.getPlayerCountData().getPlayers()).length, b = 0; b < i; ) { GameProfile gameprofile = arrayOfGameProfile[b];
/*     */                     
/*  54 */                     if (stringbuilder.length() > 0)
/*     */                     {
/*  56 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/*  59 */                     stringbuilder.append(gameprofile.getName());
/*     */                     b++; }
/*     */                   
/*  62 */                   if ((serverstatusresponse.getPlayerCountData().getPlayers()).length < serverstatusresponse.getPlayerCountData().getOnlinePlayerCount()) {
/*     */                     
/*  64 */                     if (stringbuilder.length() > 0)
/*     */                     {
/*  66 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/*  69 */                     stringbuilder.append("... and ").append(serverstatusresponse.getPlayerCountData().getOnlinePlayerCount() - (serverstatusresponse.getPlayerCountData().getPlayers()).length).append(" more ...");
/*     */                   } 
/*     */                   
/*  72 */                   p_pingServer_2_.playerList = stringbuilder.toString();
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/*  77 */                 p_pingServer_2_.playerList = "";
/*     */               } 
/*     */               
/*  80 */               networkmanager.sendPacket((Packet)new C01PacketPing(Realms.currentTimeMillis()));
/*  81 */               this.field_154345_e = true;
/*     */             }
/*     */             
/*     */             public void handlePong(S01PacketPong packetIn) {
/*  85 */               networkmanager.closeChannel((IChatComponent)new ChatComponentText("Finished"));
/*     */             }
/*     */             
/*     */             public void onDisconnect(IChatComponent reason) {
/*  89 */               if (!this.field_154345_e)
/*     */               {
/*  91 */                 RealmsServerStatusPinger.LOGGER.error("Can't ping " + p_pingServer_1_ + ": " + reason.getUnformattedText());
/*     */               }
/*     */             }
/*     */           });
/*     */ 
/*     */       
/*     */       try {
/*  98 */         networkmanager.sendPacket((Packet)new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, realmsserveraddress.getHost(), realmsserveraddress.getPort(), EnumConnectionState.STATUS));
/*  99 */         networkmanager.sendPacket((Packet)new C00PacketServerQuery());
/*     */       }
/* 101 */       catch (Throwable throwable) {
/*     */         
/* 103 */         LOGGER.error(throwable);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 110 */     synchronized (this.connections) {
/*     */       
/* 112 */       Iterator<NetworkManager> iterator = this.connections.iterator();
/*     */       
/* 114 */       while (iterator.hasNext()) {
/*     */         
/* 116 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 118 */         if (networkmanager.isChannelOpen()) {
/*     */           
/* 120 */           networkmanager.processReceivedPackets();
/*     */           
/*     */           continue;
/*     */         } 
/* 124 */         iterator.remove();
/* 125 */         networkmanager.checkDisconnected();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 133 */     synchronized (this.connections) {
/*     */       
/* 135 */       Iterator<NetworkManager> iterator = this.connections.iterator();
/*     */       
/* 137 */       while (iterator.hasNext()) {
/*     */         
/* 139 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 141 */         if (networkmanager.isChannelOpen()) {
/*     */           
/* 143 */           iterator.remove();
/* 144 */           networkmanager.closeChannel((IChatComponent)new ChatComponentText("Cancelled"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\realms\RealmsServerStatusPinger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */