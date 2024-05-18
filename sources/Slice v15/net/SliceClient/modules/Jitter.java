package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;

public class Jitter extends Module
{
  public Jitter()
  {
    super("Jitter", net.SliceClient.module.Category.SPEED, 16376546);
  }
  
  public static float modifier = 4.3F;
  private int movement;
  
  public void onEnable()
  {
    EventManager.register(this);
    super.onEnable();
  }
  
  public void onDisable()
  {
    EventManager.unregister(this);
    super.onDisable();
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
      return;
    movement += 1;
    if (((gameSettingskeyBindForward.pressed) || 
      (gameSettingskeyBindBack.pressed)) && 
      (thePlayerisCollidedVertically))
    {
      if (Minecraft.thePlayer.getHealth() >= 20.0F) {
        net.minecraft.util.Timer.timerSpeed = 1.05F;
      }
      if (movement > 3)
      {
        thePlayermotionX *= modifier;
        thePlayermotionZ *= modifier;
        movement = 0;
      }
      else
      {
        thePlayermotionX /= 1.5499999523162842D;
        thePlayermotionZ /= 1.5499999523162842D;
      }
    }
  }
}
