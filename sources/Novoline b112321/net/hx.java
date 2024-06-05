package net;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.util.IThreadListener;

public class hx {
   public static void a(Packet var0, INetHandler var1, IThreadListener var2) {
      PacketThreadUtil.checkThreadAndEnqueue(var0, var1, var2);
   }
}
