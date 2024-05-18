package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class AutoWalk extends Module
{
  public AutoWalk()
  {
    super("AutoWalk", Category.PLAYER, 16376546);
  }
  
  public void onEnable()
  {
    gameSettingskeyBindForward.pressed = true;
  }
  
  public void onDisable() {
    gameSettingskeyBindForward.pressed = false;
    thePlayermotionY = -9.999999747378752E-6D;
  }
}
