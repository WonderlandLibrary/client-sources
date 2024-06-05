package net;

import net.minecraft.network.INetHandler;
import net.minecraft.util.IChatComponent;

public class bJ {
   public static void a(INetHandler var0, IChatComponent var1) {
      var0.onDisconnect(var1);
   }
}
