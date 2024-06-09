package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.init.Blocks;



public class IceSpeed
  extends Module
{
  public IceSpeed()
  {
    super("IceSpeed", 0, true, ModuleCategory.MOVEMENT);
  }
  
  public void onPreUpdate()
  {
    iceslipperiness = 0.4F;
    packed_iceslipperiness = 0.4F;
  }
  
  public void onDisable()
  {
    iceslipperiness = 0.89F;
    packed_iceslipperiness = 0.89F;
  }
}
