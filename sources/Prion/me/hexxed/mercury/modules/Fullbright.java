package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.client.Minecraft;




public class Fullbright
  extends Module
{
  public Fullbright()
  {
    super("Fullbright", 48, true, ModuleCategory.RENDER);
  }
  
  public void onTick()
  {
    mc.gameSettings.gammaSetting = 10.0F;
  }
  
  public void onDisable()
  {
    mc.gameSettings.gammaSetting = 0.0F;
  }
}
