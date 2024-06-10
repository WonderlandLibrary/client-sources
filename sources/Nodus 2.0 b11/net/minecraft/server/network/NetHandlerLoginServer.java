/*   1:    */ package net.minecraft.server.network;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import com.mojang.authlib.GameProfile;
/*   5:    */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*   6:    */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*   7:    */ import io.netty.util.concurrent.GenericFutureListener;
/*   8:    */ import java.math.BigInteger;
/*   9:    */ import java.security.KeyPair;
/*  10:    */ import java.security.PrivateKey;
/*  11:    */ import java.util.Arrays;
/*  12:    */ import java.util.Random;
/*  13:    */ import java.util.UUID;
/*  14:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  15:    */ import javax.crypto.SecretKey;
/*  16:    */ import net.minecraft.network.EnumConnectionState;
/*  17:    */ import net.minecraft.network.NetworkManager;
/*  18:    */ import net.minecraft.network.login.INetHandlerLoginServer;
/*  19:    */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*  20:    */ import net.minecraft.network.login.client.C01PacketEncryptionResponse;
/*  21:    */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*  22:    */ import net.minecraft.network.login.server.S01PacketEncryptionRequest;
/*  23:    */ import net.minecraft.network.login.server.S02PacketLoginSuccess;
/*  24:    */ import net.minecraft.server.MinecraftServer;
/*  25:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  26:    */ import net.minecraft.util.ChatComponentText;
/*  27:    */ import net.minecraft.util.CryptManager;
/*  28:    */ import net.minecraft.util.IChatComponent;
/*  29:    */ import org.apache.commons.lang3.Validate;
/*  30:    */ import org.apache.logging.log4j.LogManager;
/*  31:    */ import org.apache.logging.log4j.Logger;
/*  32:    */ 
/*  33:    */ public class NetHandlerLoginServer
/*  34:    */   implements INetHandlerLoginServer
/*  35:    */ {
/*  36: 32 */   private static final AtomicInteger field_147331_b = new AtomicInteger(0);
/*  37: 33 */   private static final Logger logger = LogManager.getLogger();
/*  38: 34 */   private static final Random field_147329_d = new Random();
/*  39: 35 */   private final byte[] field_147330_e = new byte[4];
/*  40:    */   private final MinecraftServer field_147327_f;
/*  41:    */   public final NetworkManager field_147333_a;
/*  42:    */   private LoginState field_147328_g;
/*  43:    */   private int field_147336_h;
/*  44:    */   private GameProfile field_147337_i;
/*  45:    */   private String field_147334_j;
/*  46:    */   private SecretKey field_147335_k;
/*  47:    */   private static final String __OBFID = "CL_00001458";
/*  48:    */   
/*  49:    */   public NetHandlerLoginServer(MinecraftServer p_i45298_1_, NetworkManager p_i45298_2_)
/*  50:    */   {
/*  51: 47 */     this.field_147328_g = LoginState.HELLO;
/*  52: 48 */     this.field_147334_j = "";
/*  53: 49 */     this.field_147327_f = p_i45298_1_;
/*  54: 50 */     this.field_147333_a = p_i45298_2_;
/*  55: 51 */     field_147329_d.nextBytes(this.field_147330_e);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void onNetworkTick()
/*  59:    */   {
/*  60: 60 */     if (this.field_147328_g == LoginState.READY_TO_ACCEPT) {
/*  61: 62 */       func_147326_c();
/*  62:    */     }
/*  63: 65 */     if (this.field_147336_h++ == 600) {
/*  64: 67 */       func_147322_a("Took too long to log in");
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void func_147322_a(String p_147322_1_)
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 75 */       logger.info("Disconnecting " + func_147317_d() + ": " + p_147322_1_);
/*  73: 76 */       ChatComponentText var2 = new ChatComponentText(p_147322_1_);
/*  74: 77 */       this.field_147333_a.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
/*  75: 78 */       this.field_147333_a.closeChannel(var2);
/*  76:    */     }
/*  77:    */     catch (Exception var3)
/*  78:    */     {
/*  79: 82 */       logger.error("Error whilst disconnecting player", var3);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void func_147326_c()
/*  84:    */   {
/*  85: 88 */     if (!this.field_147337_i.isComplete())
/*  86:    */     {
/*  87: 90 */       UUID var1 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.field_147337_i.getName()).getBytes(Charsets.UTF_8));
/*  88: 91 */       this.field_147337_i = new GameProfile(var1.toString().replaceAll("-", ""), this.field_147337_i.getName());
/*  89:    */     }
/*  90: 94 */     String var2 = this.field_147327_f.getConfigurationManager().func_148542_a(this.field_147333_a.getSocketAddress(), this.field_147337_i);
/*  91: 96 */     if (var2 != null)
/*  92:    */     {
/*  93: 98 */       func_147322_a(var2);
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:102 */       this.field_147328_g = LoginState.ACCEPTED;
/*  98:103 */       this.field_147333_a.scheduleOutboundPacket(new S02PacketLoginSuccess(this.field_147337_i), new GenericFutureListener[0]);
/*  99:104 */       this.field_147327_f.getConfigurationManager().initializeConnectionToPlayer(this.field_147333_a, this.field_147327_f.getConfigurationManager().func_148545_a(this.field_147337_i));
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void onDisconnect(IChatComponent p_147231_1_)
/* 104:    */   {
/* 105:113 */     logger.info(func_147317_d() + " lost connection: " + p_147231_1_.getUnformattedText());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String func_147317_d()
/* 109:    */   {
/* 110:118 */     return this.field_147337_i != null ? this.field_147337_i.toString() + " (" + this.field_147333_a.getSocketAddress().toString() + ")" : String.valueOf(this.field_147333_a.getSocketAddress());
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
/* 114:    */   {
/* 115:127 */     Validate.validState((this.field_147328_g == LoginState.ACCEPTED) || (this.field_147328_g == LoginState.HELLO), "Unexpected change in protocol", new Object[0]);
/* 116:128 */     Validate.validState((p_147232_2_ == EnumConnectionState.PLAY) || (p_147232_2_ == EnumConnectionState.LOGIN), "Unexpected protocol " + p_147232_2_, new Object[0]);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void processLoginStart(C00PacketLoginStart p_147316_1_)
/* 120:    */   {
/* 121:133 */     Validate.validState(this.field_147328_g == LoginState.HELLO, "Unexpected hello packet", new Object[0]);
/* 122:134 */     this.field_147337_i = p_147316_1_.func_149304_c();
/* 123:136 */     if ((this.field_147327_f.isServerInOnlineMode()) && (!this.field_147333_a.isLocalChannel()))
/* 124:    */     {
/* 125:138 */       this.field_147328_g = LoginState.KEY;
/* 126:139 */       this.field_147333_a.scheduleOutboundPacket(new S01PacketEncryptionRequest(this.field_147334_j, this.field_147327_f.getKeyPair().getPublic(), this.field_147330_e), new GenericFutureListener[0]);
/* 127:    */     }
/* 128:    */     else
/* 129:    */     {
/* 130:143 */       this.field_147328_g = LoginState.READY_TO_ACCEPT;
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void processEncryptionResponse(C01PacketEncryptionResponse p_147315_1_)
/* 135:    */   {
/* 136:149 */     Validate.validState(this.field_147328_g == LoginState.KEY, "Unexpected key packet", new Object[0]);
/* 137:150 */     PrivateKey var2 = this.field_147327_f.getKeyPair().getPrivate();
/* 138:152 */     if (!Arrays.equals(this.field_147330_e, p_147315_1_.func_149299_b(var2))) {
/* 139:154 */       throw new IllegalStateException("Invalid nonce!");
/* 140:    */     }
/* 141:158 */     this.field_147335_k = p_147315_1_.func_149300_a(var2);
/* 142:159 */     this.field_147328_g = LoginState.AUTHENTICATING;
/* 143:160 */     this.field_147333_a.enableEncryption(this.field_147335_k);
/* 144:161 */     new Thread("User Authenticator #" + field_147331_b.incrementAndGet())
/* 145:    */     {
/* 146:    */       private static final String __OBFID = "CL_00001459";
/* 147:    */       
/* 148:    */       public void run()
/* 149:    */       {
/* 150:    */         try
/* 151:    */         {
/* 152:168 */           String var1 = new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.this.field_147334_j, NetHandlerLoginServer.this.field_147327_f.getKeyPair().getPublic(), NetHandlerLoginServer.this.field_147335_k)).toString(16);
/* 153:169 */           NetHandlerLoginServer.this.field_147337_i = NetHandlerLoginServer.this.field_147327_f.func_147130_as().hasJoinedServer(new GameProfile(null, NetHandlerLoginServer.this.field_147337_i.getName()), var1);
/* 154:171 */           if (NetHandlerLoginServer.this.field_147337_i != null)
/* 155:    */           {
/* 156:173 */             NetHandlerLoginServer.logger.info("UUID of player " + NetHandlerLoginServer.this.field_147337_i.getName() + " is " + NetHandlerLoginServer.this.field_147337_i.getId());
/* 157:174 */             NetHandlerLoginServer.this.field_147328_g = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/* 158:    */           }
/* 159:    */           else
/* 160:    */           {
/* 161:178 */             NetHandlerLoginServer.this.func_147322_a("Failed to verify username!");
/* 162:179 */             NetHandlerLoginServer.logger.error("Username '" + NetHandlerLoginServer.this.field_147337_i.getName() + "' tried to join with an invalid session");
/* 163:    */           }
/* 164:    */         }
/* 165:    */         catch (AuthenticationUnavailableException var2)
/* 166:    */         {
/* 167:184 */           NetHandlerLoginServer.this.func_147322_a("Authentication servers are down. Please try again later, sorry!");
/* 168:185 */           NetHandlerLoginServer.logger.error("Couldn't verify username because servers are unavailable");
/* 169:    */         }
/* 170:    */       }
/* 171:    */     }.start();
/* 172:    */   }
/* 173:    */   
/* 174:    */   static enum LoginState
/* 175:    */   {
/* 176:194 */     HELLO("HELLO", 0),  KEY("KEY", 1),  AUTHENTICATING("AUTHENTICATING", 2),  READY_TO_ACCEPT("READY_TO_ACCEPT", 3),  ACCEPTED("ACCEPTED", 4);
/* 177:    */     
/* 178:200 */     private static final LoginState[] $VALUES = { HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, ACCEPTED };
/* 179:    */     private static final String __OBFID = "CL_00001463";
/* 180:    */     
/* 181:    */     private LoginState(String p_i45297_1_, int p_i45297_2_) {}
/* 182:    */   }
/* 183:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.network.NetHandlerLoginServer
 * JD-Core Version:    0.7.0.1
 */