package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;

public class Sprint extends Module
{
  public Sprint()
  {
    super("Sprint", net.SliceClient.module.Category.MOVEMENT, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState()) {
      return;
    }
    if ((!thePlayerisCollidedHorizontally) && (thePlayermoveForward > 0.0F)) {
      Minecraft.thePlayer.setSprinting(true);
    }
    super.onUpdate();
  }
  
  public void onDisable()
  {
    Minecraft.thePlayer.setSprinting(false);
    super.onDisable();
  }
}
