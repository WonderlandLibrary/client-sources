package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class Sprint extends Module
{
  public Sprint()
  {
    super("Sprint", 21, true, me.hexxed.mercury.modulebase.ModuleCategory.MOVEMENT);
  }
  
  public void onPreUpdate()
  {
    if (((mc.gameSettings.keyBindForward.isPressed()) || (mc.gameSettings.keyBindBack.isPressed()) || (mc.gameSettings.keyBindRight.isPressed()) || (mc.gameSettings.keyBindLeft.isPressed())) && 
      (!mc.thePlayer.isOnLadder()) && (!mc.thePlayer.isCollidedHorizontally)) {
      mc.thePlayer.setSprinting(true);
    }
  }
  
  public void onDisable()
  {
    mc.thePlayer.setSprinting(false);
  }
}
