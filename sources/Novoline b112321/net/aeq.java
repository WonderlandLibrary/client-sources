package net;

import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.util.IChatComponent;

public class aeq {
   public static IChatComponent a(S00PacketDisconnect var0) {
      return var0.getReason();
   }
}
