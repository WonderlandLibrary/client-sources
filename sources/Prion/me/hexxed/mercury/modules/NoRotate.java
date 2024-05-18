package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module
{
  public NoRotate()
  {
    super("NoRotate", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.PLAYER);
  }
  
  public void onPacketRecieving(Packet packet)
  {
    if ((packet instanceof S08PacketPlayerPosLook)) {
      S08PacketPlayerPosLook p = (S08PacketPlayerPosLook)packet;
      field_148936_d = mc.thePlayer.rotationYaw;
      field_148937_e = mc.thePlayer.rotationPitch;
    }
  }
}
