package Squad.Modules.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;

public class LazyAir extends Module{

	public LazyAir() {
		super("LazyAir", Keyboard.KEY_NONE, 0x88, Category.Movement);
		// TODO Auto-generated constructor stub
	}
	@EventTarget
	public void onUpdate(EventUpdate e){
		if(!mc.thePlayer.onGround){
			mc.timer.timerSpeed = 0.3F;
		}else {
			mc.timer.timerSpeed = 1F;
		}
	}
	

}
