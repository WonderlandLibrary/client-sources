package net;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerHandshakeTCP$1;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.server.network.NetHandlerStatusServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class a2k implements INetHandlerHandshakeServer {
   private final MinecraftServer b;
   private final NetworkManager a;

   public a2k(MinecraftServer var1, NetworkManager var2) {
      this.b = var1;
      this.a = var2;
   }

   public void processHandshake(C00Handshake var1) {
      switch(NetHandlerHandshakeTCP$1.$SwitchMap$net$minecraft$network$EnumConnectionState[var1.getRequestedState().ordinal()]) {
      case 1:
         this.a.setConnectionState(EnumConnectionState.LOGIN);
         if(var1.getProtocolVersion() > 47) {
            ChatComponentText var2 = new ChatComponentText("Outdated server! I\'m still on 1.8.8");
            this.a.sendPacket(new S00PacketDisconnect(var2));
            this.a.closeChannel(var2);
         } else if(var1.getProtocolVersion() < 47) {
            ChatComponentText var3 = new ChatComponentText("Outdated client! Please use 1.8.8");
            this.a.sendPacket(new S00PacketDisconnect(var3));
            this.a.closeChannel(var3);
         } else {
            this.a.setNetHandler(new NetHandlerLoginServer(this.b, this.a));
         }
         break;
      case 2:
         this.a.setConnectionState(EnumConnectionState.STATUS);
         this.a.setNetHandler(new NetHandlerStatusServer(this.b, this.a));
         break;
      default:
         throw new UnsupportedOperationException("Invalid intention " + var1.getRequestedState());
      }

   }

   public void onDisconnect(IChatComponent var1) {
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
