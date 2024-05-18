/*     */ package net.minecraft.client.network;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.math.BigInteger;
/*     */ import java.security.PublicKey;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiDisconnected;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.login.INetHandlerLoginClient;
/*     */ import net.minecraft.network.login.client.C01PacketEncryptionResponse;
/*     */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*     */ import net.minecraft.network.login.server.S01PacketEncryptionRequest;
/*     */ import net.minecraft.network.login.server.S02PacketLoginSuccess;
/*     */ import net.minecraft.network.login.server.S03PacketEnableCompression;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NetHandlerLoginClient implements INetHandlerLoginClient {
/*  32 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final Minecraft mc;
/*     */   private final GuiScreen previousGuiScreen;
/*     */   private final NetworkManager networkManager;
/*     */   private GameProfile gameProfile;
/*     */   
/*     */   public NetHandlerLoginClient(NetworkManager p_i45059_1_, Minecraft mcIn, GuiScreen p_i45059_3_) {
/*  40 */     this.networkManager = p_i45059_1_;
/*  41 */     this.mc = mcIn;
/*  42 */     this.previousGuiScreen = p_i45059_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEncryptionRequest(S01PacketEncryptionRequest packetIn) {
/*  47 */     final SecretKey secretkey = CryptManager.createNewSharedKey();
/*  48 */     String s = packetIn.getServerId();
/*  49 */     PublicKey publickey = packetIn.getPublicKey();
/*  50 */     String s1 = (new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey))).toString(16);
/*     */     
/*  52 */     if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().func_181041_d()) {
/*     */ 
/*     */       
/*     */       try {
/*  56 */         getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
/*     */       }
/*  58 */       catch (AuthenticationException var10) {
/*     */         
/*  60 */         logger.warn("Couldn't connect to auth servers but will continue to join LAN");
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/*  67 */         getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
/*     */       }
/*  69 */       catch (AuthenticationUnavailableException var7) {
/*     */         
/*  71 */         this.networkManager.closeChannel((IChatComponent)new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0]) }));
/*     */         
/*     */         return;
/*  74 */       } catch (InvalidCredentialsException var8) {
/*     */         
/*  76 */         this.networkManager.closeChannel((IChatComponent)new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0]) }));
/*     */         
/*     */         return;
/*  79 */       } catch (AuthenticationException authenticationexception) {
/*     */         
/*  81 */         this.networkManager.closeChannel((IChatComponent)new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { authenticationexception.getMessage() }));
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  86 */     this.networkManager.sendPacket((Packet)new C01PacketEncryptionResponse(secretkey, publickey, packetIn.getVerifyToken()), new GenericFutureListener<Future<? super Void>>()
/*     */         {
/*     */           public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception
/*     */           {
/*  90 */             NetHandlerLoginClient.this.networkManager.enableEncryption(secretkey);
/*     */           }
/*  92 */         },  new GenericFutureListener[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private MinecraftSessionService getSessionService() {
/*  97 */     return this.mc.getSessionService();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleLoginSuccess(S02PacketLoginSuccess packetIn) {
/* 102 */     this.gameProfile = packetIn.getProfile();
/* 103 */     this.networkManager.setConnectionState(EnumConnectionState.PLAY);
/* 104 */     this.networkManager.setNetHandler((INetHandler)new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisconnect(IChatComponent reason) {
/* 112 */     this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(this.previousGuiScreen, "connect.failed", reason));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleDisconnect(S00PacketDisconnect packetIn) {
/* 117 */     this.networkManager.closeChannel(packetIn.func_149603_c());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEnableCompression(S03PacketEnableCompression packetIn) {
/* 122 */     if (!this.networkManager.isLocalChannel())
/*     */     {
/* 124 */       this.networkManager.setCompressionTreshold(packetIn.getCompressionTreshold());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\network\NetHandlerLoginClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */