package net.SliceClient.modules;

import net.SliceClient.Utils.TimerUtil;
import net.SliceClient.Utils.Val;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;



public class Criticals
  extends Module
{
  public Criticals()
  {
    super("Criticals", Category.COMBAT, 16376546);
  }
  
  public static TimerUtil delayTimer = new TimerUtil();
  @Val(max=10000.0D, min=1.0D)
  public static long delay = 675L;
  
  public void onEnable()
  {
    super.onEnable();
    delayTimer.setLastMS();
  }
}
