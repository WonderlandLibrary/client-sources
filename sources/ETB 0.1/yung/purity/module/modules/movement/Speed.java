package yung.purity.module.modules.movement;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventUpdate;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;


public class Speed
  extends Module
{
  public Speed()
  {
    super("Speed", new String[] { "spood" }, ModuleType.Movement);
    setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
  }
  
  @EventHandler
  private void onUpdate(EventUpdate e)
  {
    if ((mc.thePlayer.moving()) && (mc.thePlayer.onGround))
    {
      mc.thePlayer.motionY = 0.4D;
      
      mc.thePlayer.setSpeed(1.0D);
    }
    else {
      mc.thePlayer.setSpeed(Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ));
    }
  }
}
