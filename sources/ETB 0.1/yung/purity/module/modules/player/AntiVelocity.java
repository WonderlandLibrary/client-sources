package yung.purity.module.modules.player;

import java.awt.Color;
import java.util.Random;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventPacket;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;




public class AntiVelocity
  extends Module
{
  public AntiVelocity()
  {
    super("AntiKB", new String[] { "antivelocity", "antiknockback", "velocity" }, ModuleType.Player);
    setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
  }
  

  @EventHandler
  private void onPacket(EventPacket e)
  {
    if (((e.getPacket() instanceof S12PacketEntityVelocity)) || ((e.getPacket() instanceof S27PacketExplosion)))
    {
      e.setCancelled(true);
    }
  }
}
