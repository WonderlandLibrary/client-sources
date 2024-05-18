package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;

public class Fastbreak extends Module
{
  public Fastbreak()
  {
    super("Fastbreak", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.WORLD);
  }
  
  public void onPreUpdate()
  {
    mc.playerController.blockHitDelay = 0;
    
    if (mc.playerController.curBlockDamageMP > 0.7D)
    {
      mc.playerController.curBlockDamageMP = 1.0F;
    }
  }
}
