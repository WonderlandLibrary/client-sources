package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;



public class Drugs
  extends Module
{
  public Drugs()
  {
    super("Drugs", Category.RENDER, 16376546);
  }
  





  public static enum WeedMODE
  {
    High,  NoShake;
  }
  
  public static WeedMODE wm = WeedMODE.High;
  
  public void onDisable()
  {
    Minecraft.getMinecraft();gameSettingsviewBobbing = true;
  }
  



  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    if (wm == WeedMODE.NoShake)
    {
      Minecraft.getMinecraft();gameSettingsviewBobbing = true;
      thePlayerdistanceWalkedModified = 133.7F;
    }
    if (wm == WeedMODE.High) {
      Minecraft.getMinecraft();gameSettingsviewBobbing = true;
    }
  }
}
