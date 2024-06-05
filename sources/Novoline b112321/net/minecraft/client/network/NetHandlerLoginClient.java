package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginClient implements INetHandlerLoginClient {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft mc;
   private final GuiScreen previousGuiScreen;
   private final NetworkManager networkManager;
   private GameProfile gameProfile;

   public NetHandlerLoginClient(NetworkManager var1, Minecraft var2, GuiScreen var3) {
      this.networkManager = var1;
      this.mc = var2;
      this.previousGuiScreen = var3;
   }

   public void handleEncryptionRequest(S01PacketEncryptionRequest var1) {
      SecretKey var2 = CryptManager.createNewSharedKey();
      String var3 = var1.getServerId();
      PublicKey var4 = var1.getPublicKey();
      String var5 = (new BigInteger(CryptManager.getServerIdHash(var3, var4, var2))).toString(16);
      if(this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().func_181041_d()) {
         try {
            this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), var5);
         } catch (AuthenticationException var10) {
            LOGGER.warn("Couldn\'t connect to auth servers but will continue to join LAN");
         }
      } else {
         try {
            this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), var5);
         } catch (AuthenticationUnavailableException var7) {
            this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[]{new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0])}));
            return;
         } catch (InvalidCredentialsException var8) {
            this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[]{new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0])}));
            return;
         } catch (AuthenticationException var9) {
            this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[]{var9.getMessage()}));
            return;
         }
      }

      this.networkManager.sendPacket(new C01PacketEncryptionResponse(var2, var4, var1.getVerifyToken()), this::lambda$handleEncryptionRequest$0, new GenericFutureListener[0]);
   }

   private MinecraftSessionService getSessionService() {
      return this.mc.getSessionService();
   }

   public void handleLoginSuccess(S02PacketLoginSuccess var1) {
      this.gameProfile = var1.getProfile();
      this.networkManager.setConnectionState(EnumConnectionState.PLAY);
      this.networkManager.setNetHandler(new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
   }

   public void onDisconnect(IChatComponent var1) {
      this.mc.displayGuiScreen(new GuiDisconnected(this.previousGuiScreen, "connect.failed", var1));
   }

   public void handleDisconnect(S00PacketDisconnect var1) {
      this.networkManager.closeChannel(var1.getReason());
   }

   public void handleEnableCompression(S03PacketEnableCompression var1) {
      if(!this.networkManager.isLocalChannel()) {
         this.networkManager.setCompressionTreshold(var1.getCompressionTreshold());
      }

   }

   private void lambda$handleEncryptionRequest$0(SecretKey var1, Future var2) throws Exception {
      this.networkManager.enableEncryption(var1);
   }

   private static AuthenticationException a(AuthenticationException var0) {
      return var0;
   }
}
