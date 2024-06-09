package net.minecraft.server.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginServer implements net.minecraft.network.login.INetHandlerLoginServer, IUpdatePlayerListBox
{
  private static final AtomicInteger AUTHENTICATOR_THREAD_ID = new AtomicInteger(0);
  private static final Logger logger = LogManager.getLogger();
  private static final Random RANDOM = new Random();
  private final byte[] field_147330_e = new byte[4];
  
  private final MinecraftServer server;
  
  public final NetworkManager networkManager;
  private LoginState currentLoginState;
  private int connectionTimer;
  private GameProfile loginGameProfile;
  private String serverId;
  private SecretKey secretKey;
  private static final String __OBFID = "CL_00001458";
  
  public NetHandlerLoginServer(MinecraftServer p_i45298_1_, NetworkManager p_i45298_2_)
  {
    currentLoginState = LoginState.HELLO;
    serverId = "";
    server = p_i45298_1_;
    networkManager = p_i45298_2_;
    RANDOM.nextBytes(field_147330_e);
  }
  



  public void update()
  {
    if (currentLoginState == LoginState.READY_TO_ACCEPT)
    {
      func_147326_c();
    }
    
    if (connectionTimer++ == 600)
    {
      closeConnection("Took too long to log in");
    }
  }
  
  public void closeConnection(String reason)
  {
    try
    {
      logger.info("Disconnecting " + func_147317_d() + ": " + reason);
      ChatComponentText var2 = new ChatComponentText(reason);
      networkManager.sendPacket(new S00PacketDisconnect(var2));
      networkManager.closeChannel(var2);
    }
    catch (Exception var3)
    {
      logger.error("Error whilst disconnecting player", var3);
    }
  }
  
  public void func_147326_c()
  {
    if (!loginGameProfile.isComplete())
    {
      loginGameProfile = getOfflineProfile(loginGameProfile);
    }
    
    String var1 = server.getConfigurationManager().allowUserToConnect(networkManager.getRemoteAddress(), loginGameProfile);
    
    if (var1 != null)
    {
      closeConnection(var1);
    }
    else
    {
      currentLoginState = LoginState.ACCEPTED;
      
      if ((server.getNetworkCompressionTreshold() >= 0) && (!networkManager.isLocalChannel()))
      {
        networkManager.sendPacket(new S03PacketEnableCompression(server.getNetworkCompressionTreshold()), new ChannelFutureListener()
        {
          private static final String __OBFID = "CL_00001459";
          
          public void operationComplete(ChannelFuture p_operationComplete_1_) {
            networkManager.setCompressionTreshold(server.getNetworkCompressionTreshold());
          }
        }, new GenericFutureListener[0]);
      }
      
      networkManager.sendPacket(new S02PacketLoginSuccess(loginGameProfile));
      server.getConfigurationManager().initializeConnectionToPlayer(networkManager, server.getConfigurationManager().createPlayerForUser(loginGameProfile));
    }
  }
  



  public void onDisconnect(IChatComponent reason)
  {
    logger.info(func_147317_d() + " lost connection: " + reason.getUnformattedText());
  }
  
  public String func_147317_d()
  {
    return loginGameProfile != null ? loginGameProfile.toString() + " (" + networkManager.getRemoteAddress().toString() + ")" : String.valueOf(networkManager.getRemoteAddress());
  }
  
  public void processLoginStart(C00PacketLoginStart packetIn)
  {
    Validate.validState(currentLoginState == LoginState.HELLO, "Unexpected hello packet", new Object[0]);
    loginGameProfile = packetIn.getProfile();
    
    if ((server.isServerInOnlineMode()) && (!networkManager.isLocalChannel()))
    {
      currentLoginState = LoginState.KEY;
      networkManager.sendPacket(new S01PacketEncryptionRequest(serverId, server.getKeyPair().getPublic(), field_147330_e));
    }
    else
    {
      currentLoginState = LoginState.READY_TO_ACCEPT;
    }
  }
  
  public void processEncryptionResponse(C01PacketEncryptionResponse packetIn)
  {
    Validate.validState(currentLoginState == LoginState.KEY, "Unexpected key packet", new Object[0]);
    PrivateKey var2 = server.getKeyPair().getPrivate();
    
    if (!Arrays.equals(field_147330_e, packetIn.func_149299_b(var2)))
    {
      throw new IllegalStateException("Invalid nonce!");
    }
    

    secretKey = packetIn.func_149300_a(var2);
    currentLoginState = LoginState.AUTHENTICATING;
    networkManager.enableEncryption(secretKey);
    new Thread("User Authenticator #" + AUTHENTICATOR_THREAD_ID.incrementAndGet())
    {
      private static final String __OBFID = "CL_00002268";
      
      public void run() {
        GameProfile var1 = loginGameProfile;
        
        try
        {
          String var2 = new BigInteger(CryptManager.getServerIdHash(serverId, server.getKeyPair().getPublic(), secretKey)).toString(16);
          loginGameProfile = server.getMinecraftSessionService().hasJoinedServer(new GameProfile(null, var1.getName()), var2);
          
          if (loginGameProfile != null)
          {
            NetHandlerLoginServer.logger.info("UUID of player " + loginGameProfile.getName() + " is " + loginGameProfile.getId());
            currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
          }
          else if (server.isSinglePlayer())
          {
            NetHandlerLoginServer.logger.warn("Failed to verify username but will let them in anyway!");
            loginGameProfile = getOfflineProfile(var1);
            currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
          }
          else
          {
            closeConnection("Failed to verify username!");
            NetHandlerLoginServer.logger.error("Username '" + loginGameProfile.getName() + "' tried to join with an invalid session");
          }
        }
        catch (AuthenticationUnavailableException var3)
        {
          if (server.isSinglePlayer())
          {
            NetHandlerLoginServer.logger.warn("Authentication servers are down but will let them in anyway!");
            loginGameProfile = getOfflineProfile(var1);
            currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
          }
          else
          {
            closeConnection("Authentication servers are down. Please try again later, sorry!");
            NetHandlerLoginServer.logger.error("Couldn't verify username because servers are unavailable");
          }
        }
      }
    }.start();
  }
  

  protected GameProfile getOfflineProfile(GameProfile original)
  {
    UUID var2 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + original.getName()).getBytes(com.google.common.base.Charsets.UTF_8));
    return new GameProfile(var2, original.getName());
  }
  
  static enum LoginState
  {
    HELLO("HELLO", 0), 
    KEY("KEY", 1), 
    AUTHENTICATING("AUTHENTICATING", 2), 
    READY_TO_ACCEPT("READY_TO_ACCEPT", 3), 
    ACCEPTED("ACCEPTED", 4);
    
    private static final LoginState[] $VALUES = { HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, ACCEPTED };
    private static final String __OBFID = "CL_00001463";
    
    private LoginState(String p_i45297_1_, int p_i45297_2_) {}
  }
}
