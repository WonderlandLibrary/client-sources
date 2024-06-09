package Squad.Modules.Movement;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.Utils.TimeHelper;
import Squad.base.Module;

public class WaterSpeed extends Module{

	TimeHelper Vaginal = new TimeHelper();
	private int motionDelay;
	
	public WaterSpeed() {
		super("WaterSpeed", 0, 0xffffff, Category.Movement);
		// TODO Auto-generated constructor stub
	}
	@EventTarget
	public void onUpdate(EventUpdate e){
		   if (mc.thePlayer.isInWater()) {
			      if ((mc.gameSettings.keyBindSneak.pressed) || (mc.gameSettings.keyBindJump.pressed)) {
			        return;
			      }
			      mc.thePlayer.motionX *= 1.190000057220459D;
			      mc.thePlayer.motionZ *= 1.190000057220459D;
			      if ((mc.thePlayer.isInWater()) && (Vaginal.hasReached(1000L))) {
			        motionDelay += 1;
			        motionDelay %= 2;
			        if (motionDelay == 0) {
			          mc.thePlayer.motionY = 0.012000000104308128D;
			        }
			      }
			    }
			    else
			    {
			      Vaginal.setLastMS();
			    }

	}
}
