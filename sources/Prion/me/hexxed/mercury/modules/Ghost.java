package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Ghost
  extends Module
{
  public Ghost()
  {
    super("Ghost", 0, true, ModuleCategory.MISC);
  }
  
  private boolean bypassdeath = true;
  

  public void onTick()
  {
    if (mc.theWorld == null) {
      return;
    }
    if (mc.thePlayer.getHealth() == 0.0F)
    {
      mc.thePlayer.setHealth(20.0F);
      mc.thePlayer.isDead = false;
      bypassdeath = true;
      mc.displayGuiScreen(null);
      mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }
  }
  
  public void onPacketSend(Packet packet)
  {
    if (bypassdeath)
    {
      if (((packet instanceof C03PacketPlayer)) && (!ModuleManager.getModByName("Vanilla").isEnabled())) {
        setOutboundPacketCancelled(true);
      }
    }
  }
  
  public void onDisable()
  {
    mc.thePlayer.respawnPlayer();
    bypassdeath = false;
  }
}
