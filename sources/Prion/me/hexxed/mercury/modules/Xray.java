package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;

public class Xray extends Module
{
  public Xray()
  {
    super("Xray", 35, true, me.hexxed.mercury.modulebase.ModuleCategory.RENDER);
  }
  
  public void onEnable()
  {
    getValuesxray = true;
    mc.renderGlobal.loadRenderers();
  }
  
  public void onDisable()
  {
    getValuesxray = false;
    mc.renderGlobal.loadRenderers();
  }
}
