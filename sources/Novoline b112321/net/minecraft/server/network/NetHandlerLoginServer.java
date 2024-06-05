package net.minecraft.server.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import net.Tq;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerLoginServer$LoginState;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginServer implements INetHandlerLoginServer, ITickable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final AtomicInteger AUTHENTICATOR_THREAD_ID = new AtomicInteger(0);
   private static final Random RANDOM = new Random();
   private final byte[] verifyToken = new byte[4];
   private final MinecraftServer server;
   public final NetworkManager networkManager;
   private NetHandlerLoginServer$LoginState currentLoginState = NetHandlerLoginServer$LoginState.HELLO;
   private int connectionTimer;
   private GameProfile loginGameProfile;
   private final String serverId = "";
   private SecretKey secretKey;
   private EntityPlayerMP b;

   public NetHandlerLoginServer(MinecraftServer var1, NetworkManager var2) {
      this.server = var1;
      this.networkManager = var2;
      RANDOM.nextBytes(this.verifyToken);
   }

   public void update() {
      if(this.currentLoginState == NetHandlerLoginServer$LoginState.READY_TO_ACCEPT) {
         this.tryAcceptPlayer();
      } else if(this.currentLoginState == NetHandlerLoginServer$LoginState.DELAY_ACCEPT) {
         EntityPlayerMP var1 = this.server.getConfigurationManager().getPlayerByUUID(this.loginGameProfile.getId());
         this.currentLoginState = NetHandlerLoginServer$LoginState.READY_TO_ACCEPT;
         this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.b);
         this.b = null;
      }

      if(this.connectionTimer++ == 600) {
         this.closeConnection("Took too long to log in");
      }

   }

   public void closeConnection(String var1) {
      try {
         LOGGER.info("Disconnecting " + this.getConnectionInfo() + ": " + var1);
         ChatComponentText var2 = new ChatComponentText(var1);
         this.networkManager.sendPacket(new S00PacketDisconnect(var2));
         this.networkManager.closeChannel(var2);
      } catch (Exception var3) {
         LOGGER.error("Error whilst disconnecting player", var3);
      }

   }

   public void tryAcceptPlayer() {
      if(!this.loginGameProfile.isComplete()) {
         this.loginGameProfile = this.getOfflineProfile(this.loginGameProfile);
      }

      String var1 = this.server.getConfigurationManager().allowUserToConnect(this.networkManager.getRemoteAddress(), this.loginGameProfile);
      this.closeConnection(var1);
   }

   public void onDisconnect(IChatComponent var1) {
      LOGGER.info(this.getConnectionInfo() + " lost connection: " + var1.getUnformattedText());
   }

   public String getConnectionInfo() {
      return this.loginGameProfile != null?this.loginGameProfile.toString() + " (" + this.networkManager.getRemoteAddress().toString() + ")":String.valueOf(this.networkManager.getRemoteAddress());
   }

   public void processLoginStart(C00PacketLoginStart var1) {
      Validate.validState(this.currentLoginState == NetHandlerLoginServer$LoginState.HELLO, "Unexpected hello packet", new Object[0]);
      this.loginGameProfile = var1.getProfile();
      if(this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
         this.currentLoginState = NetHandlerLoginServer$LoginState.KEY;
         NetworkManager var10000 = this.networkManager;
         this.getClass();
         var10000.sendPacket(new S01PacketEncryptionRequest("", this.server.getKeyPair().getPublic(), this.verifyToken));
      } else {
         this.currentLoginState = NetHandlerLoginServer$LoginState.READY_TO_ACCEPT;
      }

   }

   public void processEncryptionResponse(C01PacketEncryptionResponse var1) {
      Validate.validState(this.currentLoginState == NetHandlerLoginServer$LoginState.KEY, "Unexpected key packet", new Object[0]);
      PrivateKey var2 = this.server.getKeyPair().getPrivate();
      if(!Arrays.equals(this.verifyToken, var1.a(var2))) {
         throw new IllegalStateException("Invalid nonce!");
      } else {
         this.secretKey = var1.getSecretKey(var2);
         this.currentLoginState = NetHandlerLoginServer$LoginState.AUTHENTICATING;
         this.networkManager.enableEncryption(this.secretKey);
         (new Tq(this, "User Authenticator #" + AUTHENTICATOR_THREAD_ID.incrementAndGet())).start();
      }
   }

   protected GameProfile getOfflineProfile(GameProfile var1) {
      UUID var2 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + var1.getName()).getBytes(Charsets.UTF_8));
      return new GameProfile(var2, var1.getName());
   }

   static MinecraftServer access$000(NetHandlerLoginServer var0) {
      return var0.server;
   }

   static GameProfile access$100(NetHandlerLoginServer var0) {
      return var0.loginGameProfile;
   }

   static SecretKey access$200(NetHandlerLoginServer var0) {
      return var0.secretKey;
   }

   static GameProfile access$102(NetHandlerLoginServer var0, GameProfile var1) {
      return var0.loginGameProfile = var1;
   }

   static Logger access$300() {
      return LOGGER;
   }

   static NetHandlerLoginServer$LoginState access$402(NetHandlerLoginServer var0, NetHandlerLoginServer$LoginState var1) {
      return var0.currentLoginState = var1;
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
