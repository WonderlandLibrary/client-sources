package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Fly
  extends Module
{
  public Fly()
  {
    super("Fly", Category.MOVEMENT, 16376546);
  }
  


  public void onUpdate()
  {
    if (!getState()) {
      if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
        return;
      return;
    }
    thePlayercapabilities.isFlying = true;
    String yPos = String.format("%.0f", new Object[] { Double.valueOf(thePlayerposY) });
  }
  
  public void onDisable() {
    thePlayercapabilities.isFlying = false;
  }
}
