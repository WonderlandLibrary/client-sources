package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class Parkour extends Module
{
  public Parkour()
  {
    super("Parkour", 0, true, ModuleCategory.MISC);
  }
  
  public void onPreUpdate()
  {
    if ((mc.thePlayer.isSneaking()) && (!mc.thePlayer.capabilities.isFlying) && (!ModuleManager.getModByName("Fly").isEnabled()) && (!ModuleManager.getModByName("Glide").isEnabled()) && (!mc.thePlayer.onGround)) {
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1D, mc.thePlayer.posZ, false));
    }
  }
}
