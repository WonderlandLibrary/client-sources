package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;

public class NoBob extends Module
{
  public NoBob()
  {
    super("NoBob", net.SliceClient.module.Category.RENDER, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    


    thePlayerdistanceWalkedModified = 2.00232909E9F;
  }
}
