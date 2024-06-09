package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;


public class Step
  extends Module
{
  public Step()
  {
    super("Step", 33, true, ModuleCategory.MOVEMENT);
  }
  
  TimeHelper time = new TimeHelper();
  
  public void onPostUpdate()
  {
    if (mc.theWorld == null) {
      return;
    }
    

    mc.thePlayer.stepHeight = Float.valueOf((float)getValuesStepHeight).floatValue();
  }
  


  public void onStep() {}
  

  public void onDisable()
  {
    mc.thePlayer.stepHeight = 0.5F;
  }
}
