package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class Vanilla extends Module
{
  public Vanilla()
  {
    super("Vanilla", 25, false, me.hexxed.mercury.modulebase.ModuleCategory.MODES);
  }
  
  public void onEnable()
  {
    getValuesshouldbypass = true;
  }
  
  public void onTick()
  {
    if (mc.theWorld == null) return;
    setModuleDisplayName(mc.getCurrentServerData().serverIP.contains("mineplex") ? "MAC" : "Vanilla");
  }
  
  public void onDisable()
  {
    getValuesshouldbypass = false;
  }
}
