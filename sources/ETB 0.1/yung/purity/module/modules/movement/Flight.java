package yung.purity.module.modules.movement;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventUpdate;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;

public class Flight extends Module
{
  public Flight()
  {
    super("Flight", new String[] { "fly", "angel" }, ModuleType.Movement);
    setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
  }
  

  public void onDisable()
  {
    mc.timer.timerSpeed = 1.0F;
  }
  

  @EventHandler
  private void onUpdate(EventUpdate e)
  {
    mc.timer.timerSpeed = 1.7F;
    

    if ((!mc.thePlayer.onGround) && (mc.thePlayer.ticksExisted % 2 == 0)) {
      mc.thePlayer.motionY = 0.04D;
    }
    

    if (mc.gameSettings.keyBindJump.getIsKeyPressed()) {
      mc.thePlayer.motionY += 1.0D;
    }
    

    if (mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
      mc.thePlayer.motionY -= 1.0D;
    }
  }
}
