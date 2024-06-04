package yung.purity.module.modules.player;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventUpdate;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;



public class NoFall
  extends Module
{
  public NoFall()
  {
    super("NoFall", new String[] { "Nofalldamage" }, ModuleType.Player);
    setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
  }
  
  @EventHandler
  private void onUpdate(EventUpdate e)
  {
    if ((e.getType() == 0) && (mc.thePlayer.fallDistance > 3.0D) && (!mc.thePlayer.onGround))
    {
      e.setOnGround(true);
    }
  }
}
