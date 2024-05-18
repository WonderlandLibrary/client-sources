package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.client.Minecraft;

public class God extends Module
{
  public God()
  {
    super("God", 0, true, ModuleCategory.EXPLOITS);
  }
  
  public void onTick()
  {
    mc.thePlayer.isDead = true;
  }
  
  public void onDisable()
  {
    mc.thePlayer.isDead = false;
  }
}
