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
   private final NetworkManager field_147393_d;
   private static final String __OBFID = "CL_00000876";
   private static final Logger logger = LogManager.getLogger();
   private final Minecraft field_147394_b;
   private final GuiScreen field_147395_c;
   private GameProfile field_175091_e;

   public void handleLoginSuccess(S02PacketLoginSuccess var1) {
      this.field_175091_e = var1.func_179730_a();
      this.field_147393_d.setConnectionState(EnumConnectionState.PLAY);
      this.field_147393_d.setNetHandler(new NetHandlerPlayClient(this.field_147394_b, this.field_147395_c, this.field_147393_d, this.field_175091_e));
   }

   public void handleEncryptionRequest(S01PacketEncryptionRequest var1) {
      SecretKey var2 = CryptManager.createNewSharedKey();
      String var3 = var1.func_149609_c();
      PublicKey var4 = var1.func_149608_d();
      String var5 = (new BigInteger(CryptManager.getServerIdHash(var3, var4, var2))).toString(16);

      try {
         this.func_147391_c().joinServer(this.field_147394_b.getSession().getProfile(), this.field_147394_b.getSession().getToken(), var5);
      } catch (AuthenticationUnavailableException var7) {
         this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[]{new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0])}));
         return;
      } catch (InvalidCredentialsException var8) {
         this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[]{new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0])}));
         return;
      } catch (AuthenticationException var9) {
         this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[]{var9.getMessage()}));
         return;
      }

      this.field_147393_d.sendPacket(new C01PacketEncryptionResponse(var2, var4, var1.func_149607_e()), new GenericFutureListener(this, var2) {
         private static final String __OBFID = "CL_00000877";
         final NetHandlerLoginClient this$0;
         private final SecretKey val$var2;

         public void operationComplete(Future var1) {
            NetHandlerLoginClient.access$0(this.this$0).enableEncryption(this.val$var2);
         }

         {
            this.this$0 = var1;
            this.val$var2 = var2;
         }
      });
   }

   public void func_180464_a(S03PacketEnableCompression var1) {
      if (!this.field_147393_d.isLocalChannel()) {
         this.field_147393_d.setCompressionTreshold(var1.func_179731_a());
      }

   }

   public NetHandlerLoginClient(NetworkManager var1, Minecraft var2, GuiScreen var3) {
      this.field_147393_d = var1;
      this.field_147394_b = var2;
      this.field_147395_c = var3;
   }

   private MinecraftSessionService func_147391_c() {
      return this.field_147394_b.getSessionService();
   }

   public void handleDisconnect(S00PacketDisconnect var1) {
      this.field_147393_d.closeChannel(var1.func_149603_c());
   }

   static NetworkManager access$0(NetHandlerLoginClient var0) {
      return var0.field_147393_d;
   }

   public void onDisconnect(IChatComponent var1) {
      this.field_147394_b.displayGuiScreen(new GuiDisconnected(this.field_147395_c, "connect.failed", var1));
   }
}
