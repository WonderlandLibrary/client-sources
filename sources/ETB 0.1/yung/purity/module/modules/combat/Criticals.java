package yung.purity.module.modules.combat;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C02PacketUseEntity;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventPacket;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;




public class Criticals
  extends Module
{
  public Criticals()
  {
    super("Criticals", new String[] { "crits", "crit" }, ModuleType.Combat);
    setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
  }
  
  private boolean canCrit()
  {
    return (mc.thePlayer.onGround) && (!mc.thePlayer.isInWater());
  }
  
  @EventHandler
  private void onPacket(EventPacket e)
  {
    if (((e.getPacket() instanceof C02PacketUseEntity)) && (canCrit())) {
      mc.thePlayer.motionY = 0.2D;
    }
  }
}
