package net.minecraft.server.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;

public class NetHandlerStatusServer implements INetHandlerStatusServer
{
  private final MinecraftServer server;
  private final NetworkManager networkManager;
  private static final String __OBFID = "CL_00001464";
  
  public NetHandlerStatusServer(MinecraftServer serverIn, NetworkManager netManager)
  {
    server = serverIn;
    networkManager = netManager;
  }
  


  public void onDisconnect(IChatComponent reason) {}
  

  public void processServerQuery(C00PacketServerQuery packetIn)
  {
    networkManager.sendPacket(new S00PacketServerInfo(server.getServerStatusResponse()));
  }
  
  public void processPing(C01PacketPing packetIn)
  {
    networkManager.sendPacket(new S01PacketPong(packetIn.getClientTime()));
  }
}
