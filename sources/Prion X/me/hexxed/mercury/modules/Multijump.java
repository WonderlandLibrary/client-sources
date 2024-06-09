package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.client.Minecraft;


public class Multijump
  extends Module
{
  public Multijump()
  {
    super("Multijump", 0, false, ModuleCategory.MOVEMENT);
  }
  
  public void onPreMotionUpdate()
  {
    mc.thePlayer.onGround = true;
  }
}
