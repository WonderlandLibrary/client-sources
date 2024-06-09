/*     */ package net.minecraft.client.multiplayer;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiDisconnected;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.network.NetHandlerLoginClient;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.handshake.client.C00Handshake;
/*     */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiConnecting extends GuiScreen {
/*  24 */   private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
/*  25 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private NetworkManager networkManager;
/*     */   private boolean cancel;
/*     */   private final GuiScreen previousGuiScreen;
/*     */   
/*     */   public GuiConnecting(GuiScreen p_i1181_1_, Minecraft mcIn, ServerData p_i1181_3_) {
/*  32 */     this.mc = mcIn;
/*  33 */     this.previousGuiScreen = p_i1181_1_;
/*  34 */     ServerAddress serveraddress = ServerAddress.func_78860_a(p_i1181_3_.serverIP);
/*  35 */     mcIn.loadWorld(null);
/*  36 */     mcIn.setServerData(p_i1181_3_);
/*  37 */     connect(serveraddress.getIP(), serveraddress.getPort());
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiConnecting(GuiScreen p_i1182_1_, Minecraft mcIn, String hostName, int port) {
/*  42 */     this.mc = mcIn;
/*  43 */     this.previousGuiScreen = p_i1182_1_;
/*  44 */     mcIn.loadWorld(null);
/*  45 */     connect(hostName, port);
/*     */   }
/*     */ 
/*     */   
/*     */   private void connect(final String ip, final int port) {
/*  50 */     logger.info("Connecting to " + ip + ", " + port);
/*  51 */     (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
/*     */       {
/*     */         public void run()
/*     */         {
/*  55 */           InetAddress inetaddress = null;
/*     */ 
/*     */           
/*     */           try {
/*  59 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  64 */             inetaddress = InetAddress.getByName(ip);
/*  65 */             GuiConnecting.this.networkManager = NetworkManager.func_181124_a(inetaddress, port, GuiConnecting.this.mc.gameSettings.func_181148_f());
/*  66 */             GuiConnecting.this.networkManager.setNetHandler((INetHandler)new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
/*  67 */             GuiConnecting.this.networkManager.sendPacket((Packet)new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
/*  68 */             GuiConnecting.this.networkManager.sendPacket((Packet)new C00PacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
/*     */           }
/*  70 */           catch (UnknownHostException unknownhostexception) {
/*     */             
/*  72 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  77 */             GuiConnecting.logger.error("Couldn't connect to server", unknownhostexception);
/*  78 */             GuiConnecting.this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" })));
/*     */           }
/*  80 */           catch (Exception exception) {
/*     */             
/*  82 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  87 */             GuiConnecting.logger.error("Couldn't connect to server", exception);
/*  88 */             String s = exception.toString();
/*     */             
/*  90 */             if (inetaddress != null) {
/*     */               
/*  92 */               String s1 = String.valueOf(inetaddress.toString()) + ":" + port;
/*  93 */               s = s.replaceAll(s1, "");
/*     */             } 
/*     */             
/*  96 */             GuiConnecting.this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { s })));
/*     */           } 
/*     */         }
/*  99 */       }).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 107 */     if (this.networkManager != null)
/*     */     {
/* 109 */       if (this.networkManager.isChannelOpen()) {
/*     */         
/* 111 */         this.networkManager.processReceivedPackets();
/*     */       }
/*     */       else {
/*     */         
/* 115 */         this.networkManager.checkDisconnected();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 134 */     this.buttonList.clear();
/* 135 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 143 */     if (button.id == 0) {
/*     */       
/* 145 */       this.cancel = true;
/*     */       
/* 147 */       if (this.networkManager != null)
/*     */       {
/* 149 */         this.networkManager.closeChannel((IChatComponent)new ChatComponentText("Aborted"));
/*     */       }
/*     */       
/* 152 */       this.mc.displayGuiScreen(this.previousGuiScreen);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 161 */     drawDefaultBackground();
/*     */     
/* 163 */     if (this.networkManager == null) {
/*     */       
/* 165 */       drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
/*     */     }
/*     */     else {
/*     */       
/* 169 */       drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
/*     */     } 
/*     */     
/* 172 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\multiplayer\GuiConnecting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */