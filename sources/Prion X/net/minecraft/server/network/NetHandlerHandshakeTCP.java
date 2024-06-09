package net.minecraft.server.network;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer
{
  private final MinecraftServer server;
  private final NetworkManager networkManager;
  private static final String __OBFID = "CL_00001456";
  
  public NetHandlerHandshakeTCP(MinecraftServer serverIn, NetworkManager netManager)
  {
    server = serverIn;
    networkManager = netManager;
  }
  





  public void processHandshake(C00Handshake packetIn)
  {
    switch (SwitchEnumConnectionState.VALUES[packetIn.getRequestedState().ordinal()])
    {
    case 1: 
      networkManager.setConnectionState(EnumConnectionState.LOGIN);
      

      if (packetIn.getProtocolVersion() > 47)
      {
        ChatComponentText var2 = new ChatComponentText("Outdated server! I'm still on 1.8");
        networkManager.sendPacket(new S00PacketDisconnect(var2));
        networkManager.closeChannel(var2);
      }
      else if (packetIn.getProtocolVersion() < 47)
      {
        ChatComponentText var2 = new ChatComponentText("Outdated client! Please use 1.8");
        networkManager.sendPacket(new S00PacketDisconnect(var2));
        networkManager.closeChannel(var2);
      }
      else
      {
        networkManager.setNetHandler(new NetHandlerLoginServer(server, networkManager));
      }
      
      break;
    
    case 2: 
      networkManager.setConnectionState(EnumConnectionState.STATUS);
      networkManager.setNetHandler(new NetHandlerStatusServer(server, networkManager));
      break;
    
    default: 
      throw new UnsupportedOperationException("Invalid intention " + packetIn.getRequestedState());
    }
    
  }
  

  public void onDisconnect(IChatComponent reason) {}
  

  static final class SwitchEnumConnectionState
  {
    static final int[] VALUES = new int[EnumConnectionState.values().length];
    private static final String __OBFID = "CL_00001457";
    
    static
    {
      try
      {
        VALUES[EnumConnectionState.LOGIN.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        VALUES[EnumConnectionState.STATUS.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
    }
    
    SwitchEnumConnectionState() {}
  }
}
