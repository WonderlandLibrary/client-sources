package net.SliceClient.modules;

import net.minecraft.client.Minecraft;

public class NoVelocity extends net.SliceClient.module.Module
{
  public NoVelocity()
  {
    super("NoVelocity", net.SliceClient.module.Category.MISC, 16376546);
  }
  


  public void onEnabled()
  {
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {}
  }
}
