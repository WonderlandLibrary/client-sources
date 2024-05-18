package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;



public class SlimeFly
  extends Module
{
  public SlimeFly()
  {
    super("SlimeFly", Category.MOVEMENT, 16376546);
  }
  
  public void onEnable()
  {
    thePlayermotionY = 3.5D;
  }
}
