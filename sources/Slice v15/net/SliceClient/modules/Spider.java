package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;

public class Spider extends Module
{
  public Spider()
  {
    super("Spider", net.SliceClient.module.Category.MOVEMENT, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    if (thePlayerisCollidedHorizontally) {
      thePlayermotionY = 0.2D;
    }
  }
}
