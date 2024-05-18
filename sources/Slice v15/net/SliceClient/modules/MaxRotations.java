package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;

public class MaxRotations
  extends Module
{
  public MaxRotations()
  {
    super("MaxRotations", Category.EXPLOITS, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {}
  }
}
