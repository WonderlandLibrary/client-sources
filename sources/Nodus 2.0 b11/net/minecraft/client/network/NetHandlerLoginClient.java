/*   1:    */ package net.minecraft.client.network;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.exceptions.AuthenticationException;
/*   4:    */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*   5:    */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*   6:    */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*   7:    */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*   8:    */ import io.netty.util.concurrent.Future;
/*   9:    */ import io.netty.util.concurrent.GenericFutureListener;
/*  10:    */ import java.math.BigInteger;
/*  11:    */ import java.security.PublicKey;
/*  12:    */ import java.util.UUID;
/*  13:    */ import javax.crypto.SecretKey;
/*  14:    */ import net.minecraft.client.Minecraft;
/*  15:    */ import net.minecraft.client.gui.GuiDisconnected;
/*  16:    */ import net.minecraft.client.gui.GuiScreen;
/*  17:    */ import net.minecraft.network.EnumConnectionState;
/*  18:    */ import net.minecraft.network.NetworkManager;
/*  19:    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*  20:    */ import net.minecraft.network.login.client.C01PacketEncryptionResponse;
/*  21:    */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*  22:    */ import net.minecraft.network.login.server.S01PacketEncryptionRequest;
/*  23:    */ import net.minecraft.network.login.server.S02PacketLoginSuccess;
/*  24:    */ import net.minecraft.util.ChatComponentTranslation;
/*  25:    */ import net.minecraft.util.CryptManager;
/*  26:    */ import net.minecraft.util.IChatComponent;
/*  27:    */ import net.minecraft.util.Session;
/*  28:    */ import org.apache.logging.log4j.LogManager;
/*  29:    */ import org.apache.logging.log4j.Logger;
/*  30:    */ 
/*  31:    */ public class NetHandlerLoginClient
/*  32:    */   implements INetHandlerLoginClient
/*  33:    */ {
/*  34: 32 */   private static final Logger logger = ;
/*  35:    */   private final Minecraft field_147394_b;
/*  36:    */   private final GuiScreen field_147395_c;
/*  37:    */   private final NetworkManager field_147393_d;
/*  38:    */   private static final String __OBFID = "CL_00000876";
/*  39:    */   
/*  40:    */   public NetHandlerLoginClient(NetworkManager p_i45059_1_, Minecraft p_i45059_2_, GuiScreen p_i45059_3_)
/*  41:    */   {
/*  42: 40 */     this.field_147393_d = p_i45059_1_;
/*  43: 41 */     this.field_147394_b = p_i45059_2_;
/*  44: 42 */     this.field_147395_c = p_i45059_3_;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void handleEncryptionRequest(S01PacketEncryptionRequest p_147389_1_)
/*  48:    */   {
/*  49: 47 */     final SecretKey var2 = CryptManager.createNewSharedKey();
/*  50: 48 */     String var3 = p_147389_1_.func_149609_c();
/*  51: 49 */     PublicKey var4 = p_147389_1_.func_149608_d();
/*  52: 50 */     String var5 = new BigInteger(CryptManager.getServerIdHash(var3, var4, var2)).toString(16);
/*  53:    */     try
/*  54:    */     {
/*  55: 54 */       func_147391_c().joinServer(this.field_147394_b.getSession().func_148256_e(), this.field_147394_b.getSession().getToken(), var5);
/*  56:    */     }
/*  57:    */     catch (AuthenticationUnavailableException var7)
/*  58:    */     {
/*  59: 58 */       this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0]) }));
/*  60: 59 */       return;
/*  61:    */     }
/*  62:    */     catch (InvalidCredentialsException var8)
/*  63:    */     {
/*  64: 63 */       this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0]) }));
/*  65: 64 */       return;
/*  66:    */     }
/*  67:    */     catch (AuthenticationException var9)
/*  68:    */     {
/*  69: 68 */       this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { var9.getMessage() }));
/*  70: 69 */       return;
/*  71:    */     }
/*  72: 72 */     this.field_147393_d.scheduleOutboundPacket(new C01PacketEncryptionResponse(var2, var4, p_147389_1_.func_149607_e()), new GenericFutureListener[] { new GenericFutureListener()
/*  73:    */     {
/*  74:    */       private static final String __OBFID = "CL_00000877";
/*  75:    */       
/*  76:    */       public void operationComplete(Future p_operationComplete_1_)
/*  77:    */       {
/*  78: 77 */         NetHandlerLoginClient.this.field_147393_d.enableEncryption(var2);
/*  79:    */       }
/*  80:    */     } });
/*  81:    */   }
/*  82:    */   
/*  83:    */   private MinecraftSessionService func_147391_c()
/*  84:    */   {
/*  85: 85 */     return new YggdrasilAuthenticationService(this.field_147394_b.getProxy(), UUID.randomUUID().toString()).createMinecraftSessionService();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void handleLoginSuccess(S02PacketLoginSuccess p_147390_1_)
/*  89:    */   {
/*  90: 90 */     this.field_147393_d.setConnectionState(EnumConnectionState.PLAY);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void onDisconnect(IChatComponent p_147231_1_)
/*  94:    */   {
/*  95: 98 */     this.field_147394_b.displayGuiScreen(new GuiDisconnected(this.field_147395_c, "connect.failed", p_147231_1_));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
/*  99:    */   {
/* 100:107 */     logger.debug("Switching protocol from " + p_147232_1_ + " to " + p_147232_2_);
/* 101:109 */     if (p_147232_2_ == EnumConnectionState.PLAY) {
/* 102:111 */       this.field_147393_d.setNetHandler(new NetHandlerPlayClient(this.field_147394_b, this.field_147395_c, this.field_147393_d));
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void onNetworkTick() {}
/* 107:    */   
/* 108:    */   public void handleDisconnect(S00PacketDisconnect p_147388_1_)
/* 109:    */   {
/* 110:123 */     this.field_147393_d.closeChannel(p_147388_1_.func_149603_c());
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.network.NetHandlerLoginClient
 * JD-Core Version:    0.7.0.1
 */