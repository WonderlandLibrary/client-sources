package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class SlimeFly extends Module
{
  public SlimeFly()
  {
    super("SlimeFly", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.MOVEMENT);
  }
  


  public void onPreUpdate()
  {
    if (mc.thePlayer.fallDistance > 50.0F) {
      getValuesSlimeFly = 2.7D;
      return;
    }
    if (mc.thePlayer.fallDistance > 19.0F) {
      getValuesSlimeFly = 2.4D;
      return;
    }
    getValuesSlimeFly = 1.5D;
  }
}
