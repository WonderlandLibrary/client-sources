package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class Jetpack extends Module
{
  public Jetpack()
  {
    super("Jetpack", Category.MOVEMENT, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState()) {
      if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
        return;
      return;
    }
    if ((Minecraft.thePlayer != null) && 
      (gameSettingskeyBindJump.pressed)) {
      Minecraft.getMinecraft();Minecraft.thePlayer.jump();
      thePlayermoveForward = 0.9F;
      thePlayermoveForward = 0.8F;
    }
  }
}
