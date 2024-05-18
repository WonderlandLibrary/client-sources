package net.SliceClient.modules;

import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.init.Blocks;

public class SlimeSpeed
  extends Module
{
  public SlimeSpeed()
  {
    super("SlimeSpeed", Category.MOVEMENT, 16376546);
  }
  
  public void onEnable()
  {
    slime_blockslipperiness = 0.47F;
  }
  


  public void onDisable()
  {
    slime_blockslipperiness = 0.85F;
  }
}
