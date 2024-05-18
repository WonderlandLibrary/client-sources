package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;

public class FastPlace extends Module
{
  public FastPlace()
  {
    super("FastPlace", Category.WORLD, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    Minecraft.rightClickDelayTimer = 1;
  }
  
  public void onDisable()
  {
    Minecraft.rightClickDelayTimer = 6;
  }
}
