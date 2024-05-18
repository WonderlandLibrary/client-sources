package net.SliceClient.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class NetUtils
{
  public NetUtils() {}
  
  public static void sendPacket(Packet packet)
  {
    Minecraft.getMinecraft();Minecraft.getNetHandler().getNetworkManager().dispatchPacket(packet, null);
  }
}
