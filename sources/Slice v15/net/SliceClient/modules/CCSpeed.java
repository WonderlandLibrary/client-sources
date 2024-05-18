package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;

public class CCSpeed extends Module
{
  public CCSpeed()
  {
    super("CCSpeed", net.SliceClient.module.Category.SPEED, 16376546);
  }
  
  public static float tSpeed = 2.2F;
  
  public void onUpdate() {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    


    net.minecraft.util.Timer.timerSpeed = tSpeed;
    thePlayermotionX *= 1.009D;
    thePlayermotionZ *= 1.009D;
  }
  
  public void onDisable() {
    net.minecraft.util.Timer.timerSpeed = 1.0F;
  }
}
