package Squad.Modules.Movement;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.Utils.TimeHelper;
import Squad.base.Category;
import Squad.base.Module;
import net.minecraft.client.Minecraft;

public class Step
  extends Module
{
  public Step()
  {
    super("Spider", 0, 136, Category.Player);
  }
  
  TimeHelper time = new TimeHelper();
  
  @EventTarget
  public void onUpdate(EventUpdate e)
  {
    setDisplayname("Spider §7AAC3");
    if (((mc.gameSettings.keyBindForward.pressed) || (mc.gameSettings.keyBindBack.pressed) || (mc.gameSettings.keyBindRight.pressed) || (mc.gameSettings.keyBindLeft.pressed)) && 
      (mc.thePlayer.isCollidedHorizontally) && (this.time.hasReached(500.0F)))
    {
    	mc.thePlayer.jump();
    	mc.thePlayer.motionY *= 1.399999976158142D;
      this.time.reset();
    }
  }
  
  public void onDisable()
  {
	  mc.thePlayer.stepHeight = 0.6F;
  }
}
