/*     */ package net.minecraft.realms;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.network.NetHandlerLoginClient;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class RealmsConnect {
/*  17 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final RealmsScreen onlineScreen;
/*     */   private volatile boolean aborted = false;
/*     */   private NetworkManager connection;
/*     */   
/*     */   public RealmsConnect(RealmsScreen p_i1079_1_) {
/*  24 */     this.onlineScreen = p_i1079_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void connect(final String p_connect_1_, final int p_connect_2_) {
/*  29 */     Realms.setConnectedToRealms(true);
/*  30 */     (new Thread("Realms-connect-task")
/*     */       {
/*     */         public void run()
/*     */         {
/*  34 */           InetAddress inetaddress = null;
/*     */ 
/*     */           
/*     */           try {
/*  38 */             inetaddress = InetAddress.getByName(p_connect_1_);
/*     */             
/*  40 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  45 */             RealmsConnect.this.connection = NetworkManager.func_181124_a(inetaddress, p_connect_2_, (Minecraft.getMinecraft()).gameSettings.func_181148_f());
/*     */             
/*  47 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  52 */             RealmsConnect.this.connection.setNetHandler((INetHandler)new NetHandlerLoginClient(RealmsConnect.this.connection, Minecraft.getMinecraft(), (GuiScreen)RealmsConnect.this.onlineScreen.getProxy()));
/*     */             
/*  54 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  59 */             RealmsConnect.this.connection.sendPacket((Packet)new C00Handshake(47, p_connect_1_, p_connect_2_, EnumConnectionState.LOGIN));
/*     */             
/*  61 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  66 */             RealmsConnect.this.connection.sendPacket((Packet)new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
/*     */           }
/*  68 */           catch (UnknownHostException unknownhostexception) {
/*     */             
/*  70 */             Realms.clearResourcePack();
/*     */             
/*  72 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  77 */             RealmsConnect.LOGGER.error("Couldn't connect to world", unknownhostexception);
/*  78 */             Minecraft.getMinecraft().getResourcePackRepository().func_148529_f();
/*  79 */             Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host '" + this.val$p_connect_1_ + "'" })));
/*     */           }
/*  81 */           catch (Exception exception) {
/*     */             
/*  83 */             Realms.clearResourcePack();
/*     */             
/*  85 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  90 */             RealmsConnect.LOGGER.error("Couldn't connect to world", exception);
/*  91 */             String s = exception.toString();
/*     */             
/*  93 */             if (inetaddress != null) {
/*     */               
/*  95 */               String s1 = String.valueOf(inetaddress.toString()) + ":" + p_connect_2_;
/*  96 */               s = s.replaceAll(s1, "");
/*     */             } 
/*     */             
/*  99 */             Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { s })));
/*     */           } 
/*     */         }
/* 102 */       }).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void abort() {
/* 107 */     this.aborted = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 112 */     if (this.connection != null)
/*     */     {
/* 114 */       if (this.connection.isChannelOpen()) {
/*     */         
/* 116 */         this.connection.processReceivedPackets();
/*     */       }
/*     */       else {
/*     */         
/* 120 */         this.connection.checkDisconnected();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\realms\RealmsConnect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */