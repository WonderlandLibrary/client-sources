package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Old extends Module
{
  public Old()
  {
    super("Old", net.SliceClient.module.Category.SPEED, 16376546);
  }
  
  public void onDisable()
  {
    net.minecraft.util.Timer.timerSpeed = 1.0F;
  }
  
  private boolean air = false;
  private int timerDelay;
  private int groundTimer;
  int speed = 0;
  private int motionDelay;
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if (thePlayeronGround)
    {
      motionDelay += 1;
      motionDelay %= 2;
      if (motionDelay == 1)
      {
        thePlayermotionX *= 2.58D;
        thePlayermotionZ *= 2.58D;
      }
      else
      {
        thePlayermotionX /= 1.5D;
        thePlayermotionZ /= 1.5D;
      }
      thePlayermoveStrafing *= 0.0F;
      
      thePlayermotionY = 0.001D;
    }
    thePlayerisAirBorne = false;
  }
}
