package Squad.Modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;

public class FastLadder extends Module{

	public FastLadder() {
		super("FastLadder", Keyboard.KEY_NONE, 0x88, Category.Player);
		// TODO Auto-generated constructor stub
	}
	@EventTarget
	public void onUpdate(EventUpdate e){
		if(mc.thePlayer.isOnLadder()){
			mc.thePlayer.motionY *= 1.4;
		}
	}

}
