package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Headless
  extends Module
{
  public Headless()
  {
    super("Headless", 0, true, ModuleCategory.PLAYER);
  }
  
  public void onPacketSend(Packet packet)
  {
    if ((packet instanceof C03PacketPlayer)) {
      C03PacketPlayer p = (C03PacketPlayer)packet;
      if ((pitch > 80.0F) || (pitch < -80.0F)) {
        pitch = 180.0F;
      } else {
        pitch = (180.0F - pitch);
        yaw += 180.0F;
      }
    }
  }
}
