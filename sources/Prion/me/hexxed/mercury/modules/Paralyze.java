package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;

public class Paralyze extends Module
{
  public Paralyze()
  {
    super("Paralyze", 0, true, ModuleCategory.EXPLOITS);
  }
  
  public void onPreMotionUpdate()
  {
    if (!mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.thePlayer, mc.thePlayer.boundingBox).isEmpty())
    {
      for (int index = 0; index < 1337; index++) {
        mc.getNetHandler().addToSendQueue(
          new net.minecraft.network.play.client.C03PacketPlayer(mc.thePlayer.onGround));
      }
    }
  }
}
