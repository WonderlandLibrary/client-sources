package net.minecraft.client.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.util.IChatComponent;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer {
   private static final String __OBFID = "CL_00001445";
   private final MinecraftServer field_147385_a;
   private final NetworkManager field_147384_b;

   public NetHandlerHandshakeMemory(MinecraftServer var1, NetworkManager var2) {
      this.field_147385_a = var1;
      this.field_147384_b = var2;
   }

   public void onDisconnect(IChatComponent var1) {
   }

   public void processHandshake(C00Handshake var1) {
      this.field_147384_b.setConnectionState(var1.getRequestedState());
      this.field_147384_b.setNetHandler(new NetHandlerLoginServer(this.field_147385_a, this.field_147384_b));
   }
}
