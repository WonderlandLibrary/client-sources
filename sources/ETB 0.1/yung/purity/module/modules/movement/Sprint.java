package yung.purity.module.modules.movement;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.FoodStats;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventUpdate;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;



public class Sprint
  extends Module
{
  public Sprint()
  {
    super("Sprint", new String[] { "run" }, ModuleType.Movement);
    
    setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
  }
  
  @EventHandler
  private void onUpdate(EventUpdate event) {
    if ((mc.thePlayer.getFoodStats().getFoodLevel() > 6) && (mc.thePlayer.moving())) {
      mc.thePlayer.setSprinting(true);
    }
  }
}
