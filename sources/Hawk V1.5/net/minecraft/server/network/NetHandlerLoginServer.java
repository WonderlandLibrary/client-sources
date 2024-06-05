package net.minecraft.server.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.Future;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginServer implements INetHandlerLoginServer, IUpdatePlayerListBox {
   private int connectionTimer;
   private static final Random RANDOM = new Random();
   private static final AtomicInteger AUTHENTICATOR_THREAD_ID = new AtomicInteger(0);
   private SecretKey secretKey;
   private static final Logger logger = LogManager.getLogger();
   private final MinecraftServer server;
   private GameProfile loginGameProfile;
   private final byte[] field_147330_e = new byte[4];
   private String serverId;
   private NetHandlerLoginServer.LoginState currentLoginState;
   public final NetworkManager networkManager;
   private static final String __OBFID = "CL_00001458";

   public void processLoginStart(C00PacketLoginStart var1) {
      Validate.validState(this.currentLoginState == NetHandlerLoginServer.LoginState.HELLO, "Unexpected hello packet", new Object[0]);
      this.loginGameProfile = var1.getProfile();
      if (this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
         this.currentLoginState = NetHandlerLoginServer.LoginState.KEY;
         this.networkManager.sendPacket(new S01PacketEncryptionRequest(this.serverId, this.server.getKeyPair().getPublic(), this.field_147330_e));
      } else {
         this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
      }

   }

   public NetHandlerLoginServer(MinecraftServer var1, NetworkManager var2) {
      this.currentLoginState = NetHandlerLoginServer.LoginState.HELLO;
      this.serverId = "";
      this.server = var1;
      this.networkManager = var2;
      RANDOM.nextBytes(this.field_147330_e);
   }

   static SecretKey access$3(NetHandlerLoginServer var0) {
      return var0.secretKey;
   }

   static Logger access$5() {
      return logger;
   }

   static MinecraftServer access$0(NetHandlerLoginServer var0) {
      return var0.server;
   }

   static void access$6(NetHandlerLoginServer var0, NetHandlerLoginServer.LoginState var1) {
      var0.currentLoginState = var1;
   }

   public void closeConnection(String var1) {
      try {
         logger.info(String.valueOf((new StringBuilder("Disconnecting ")).append(this.func_147317_d()).append(": ").append(var1)));
         ChatComponentText var2 = new ChatComponentText(var1);
         this.networkManager.sendPacket(new S00PacketDisconnect(var2));
         this.networkManager.closeChannel(var2);
      } catch (Exception var3) {
         logger.error("Error whilst disconnecting player", var3);
      }

   }

   static void access$4(NetHandlerLoginServer var0, GameProfile var1) {
      var0.loginGameProfile = var1;
   }

   static GameProfile access$1(NetHandlerLoginServer var0) {
      return var0.loginGameProfile;
   }

   public void func_147326_c() {
      if (!this.loginGameProfile.isComplete()) {
         this.loginGameProfile = this.getOfflineProfile(this.loginGameProfile);
      }

      String var1 = this.server.getConfigurationManager().allowUserToConnect(this.networkManager.getRemoteAddress(), this.loginGameProfile);
      if (var1 != null) {
         this.closeConnection(var1);
      } else {
         this.currentLoginState = NetHandlerLoginServer.LoginState.ACCEPTED;
         if (this.server.getNetworkCompressionTreshold() >= 0 && !this.networkManager.isLocalChannel()) {
            this.networkManager.sendPacket(new S03PacketEnableCompression(this.server.getNetworkCompressionTreshold()), new ChannelFutureListener(this) {
               private static final String __OBFID = "CL_00001459";
               final NetHandlerLoginServer this$0;

               public void operationComplete(ChannelFuture var1) {
                  this.this$0.networkManager.setCompressionTreshold(NetHandlerLoginServer.access$0(this.this$0).getNetworkCompressionTreshold());
               }

               {
                  this.this$0 = var1;
               }

               public void operationComplete(Future var1) throws Exception {
                  this.operationComplete((ChannelFuture)var1);
               }
            });
         }

         this.networkManager.sendPacket(new S02PacketLoginSuccess(this.loginGameProfile));
         this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile));
      }

   }

   static String access$2(NetHandlerLoginServer var0) {
      return var0.serverId;
   }

   public void processEncryptionResponse(C01PacketEncryptionResponse var1) {
      Validate.validState(this.currentLoginState == NetHandlerLoginServer.LoginState.KEY, "Unexpected key packet", new Object[0]);
      PrivateKey var2 = this.server.getKeyPair().getPrivate();
      if (!Arrays.equals(this.field_147330_e, var1.func_149299_b(var2))) {
         throw new IllegalStateException("Invalid nonce!");
      } else {
         this.secretKey = var1.func_149300_a(var2);
         this.currentLoginState = NetHandlerLoginServer.LoginState.AUTHENTICATING;
         this.networkManager.enableEncryption(this.secretKey);
         (new Thread(this, String.valueOf((new StringBuilder("User Authenticator #")).append(AUTHENTICATOR_THREAD_ID.incrementAndGet()))) {
            private static final String __OBFID = "CL_00002268";
            final NetHandlerLoginServer this$0;

            public void run() {
               GameProfile var1 = NetHandlerLoginServer.access$1(this.this$0);

               try {
                  String var2 = (new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.access$2(this.this$0), NetHandlerLoginServer.access$0(this.this$0).getKeyPair().getPublic(), NetHandlerLoginServer.access$3(this.this$0)))).toString(16);
                  NetHandlerLoginServer.access$4(this.this$0, NetHandlerLoginServer.access$0(this.this$0).getMinecraftSessionService().hasJoinedServer(new GameProfile((UUID)null, var1.getName()), var2));
                  if (NetHandlerLoginServer.access$1(this.this$0) != null) {
                     NetHandlerLoginServer.access$5().info(String.valueOf((new StringBuilder("UUID of player ")).append(NetHandlerLoginServer.access$1(this.this$0).getName()).append(" is ").append(NetHandlerLoginServer.access$1(this.this$0).getId())));
                     NetHandlerLoginServer.access$6(this.this$0, NetHandlerLoginServer.LoginState.READY_TO_ACCEPT);
                  } else if (NetHandlerLoginServer.access$0(this.this$0).isSinglePlayer()) {
                     NetHandlerLoginServer.access$5().warn("Failed to verify username but will let them in anyway!");
                     NetHandlerLoginServer.access$4(this.this$0, this.this$0.getOfflineProfile(var1));
                     NetHandlerLoginServer.access$6(this.this$0, NetHandlerLoginServer.LoginState.READY_TO_ACCEPT);
                  } else {
                     this.this$0.closeConnection("Failed to verify username!");
                     NetHandlerLoginServer.access$5().error(String.valueOf((new StringBuilder("Username '")).append(NetHandlerLoginServer.access$1(this.this$0).getName()).append("' tried to join with an invalid session")));
                  }
               } catch (AuthenticationUnavailableException var3) {
                  if (NetHandlerLoginServer.access$0(this.this$0).isSinglePlayer()) {
                     NetHandlerLoginServer.access$5().warn("Authentication servers are down but will let them in anyway!");
                     NetHandlerLoginServer.access$4(this.this$0, this.this$0.getOfflineProfile(var1));
                     NetHandlerLoginServer.access$6(this.this$0, NetHandlerLoginServer.LoginState.READY_TO_ACCEPT);
                  } else {
                     this.this$0.closeConnection("Authentication servers are down. Please try again later, sorry!");
                     NetHandlerLoginServer.access$5().error("Couldn't verify username because servers are unavailable");
                  }
               }

            }

            {
               this.this$0 = var1;
            }
         }).start();
      }
   }

   public String func_147317_d() {
      return this.loginGameProfile != null ? String.valueOf((new StringBuilder(String.valueOf(this.loginGameProfile.toString()))).append(" (").append(this.networkManager.getRemoteAddress().toString()).append(")")) : String.valueOf(this.networkManager.getRemoteAddress());
   }

   protected GameProfile getOfflineProfile(GameProfile var1) {
      UUID var2 = UUID.nameUUIDFromBytes(String.valueOf((new StringBuilder("OfflinePlayer:")).append(var1.getName())).getBytes(Charsets.UTF_8));
      return new GameProfile(var2, var1.getName());
   }

   public void onDisconnect(IChatComponent var1) {
      logger.info(String.valueOf((new StringBuilder(String.valueOf(this.func_147317_d()))).append(" lost connection: ").append(var1.getUnformattedText())));
   }

   public void update() {
      if (this.currentLoginState == NetHandlerLoginServer.LoginState.READY_TO_ACCEPT) {
         this.func_147326_c();
      }

      if (this.connectionTimer++ == 600) {
         this.closeConnection("Took too long to log in");
      }

   }

   static enum LoginState {
      private static final NetHandlerLoginServer.LoginState[] $VALUES = new NetHandlerLoginServer.LoginState[]{HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, ACCEPTED};
      private static final NetHandlerLoginServer.LoginState[] ENUM$VALUES = new NetHandlerLoginServer.LoginState[]{HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, ACCEPTED};
      KEY("KEY", 1);

      private static final String __OBFID = "CL_00001463";
      READY_TO_ACCEPT("READY_TO_ACCEPT", 3),
      AUTHENTICATING("AUTHENTICATING", 2),
      HELLO("HELLO", 0),
      ACCEPTED("ACCEPTED", 4);

      private LoginState(String var3, int var4) {
      }
   }
}
