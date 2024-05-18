package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.init.Blocks;

public class IceSpeed
  extends Module
{
  public IceSpeed()
  {
    super("IceSpeed", Category.MOVEMENT, 16376546);
  }
  
  public void onEnable()
  {
    iceslipperiness = 0.4F;
    packed_iceslipperiness = 0.4F;
  }
  
  public void onDisable()
  {
    iceslipperiness = 0.85F;
    packed_iceslipperiness = 0.85F;
  }
}
