package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;

public class NoWeb extends Module
{
  public NoWeb()
  {
    super("NoWeb", net.SliceClient.module.Category.EXPLOITS, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    



    thePlayerisInWeb = false;
  }
}
