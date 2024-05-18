package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class AutoJump extends Module
{
  public AutoJump()
  {
    super("AutoJump", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.MISC);
  }
  
  public void onTick()
  {
    mc.gameSettings.keyBindJump.pressed = true;
  }
  
  public void onDisable()
  {
    mc.gameSettings.keyBindJump.pressed = false;
  }
}
