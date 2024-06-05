package net.minecraft.server.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;

public class NetHandlerStatusServer implements INetHandlerStatusServer {
   private final NetworkManager networkManager;
   private final MinecraftServer server;
   private static final String __OBFID = "CL_00001464";

   public void onDisconnect(IChatComponent var1) {
   }

   public void processServerQuery(C00PacketServerQuery var1) {
      this.networkManager.sendPacket(new S00PacketServerInfo(this.server.getServerStatusResponse()));
   }

   public NetHandlerStatusServer(MinecraftServer var1, NetworkManager var2) {
      this.server = var1;
      this.networkManager = var2;
   }

   public void processPing(C01PacketPing var1) {
      this.networkManager.sendPacket(new S01PacketPong(var1.getClientTime()));
   }
}
