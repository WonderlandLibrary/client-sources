package Squad.Modules.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;

public class AutoJump extends Module{

	public AutoJump() {
		super("AutoJump", Keyboard.KEY_NONE, 0x88, Category.Movement);
		// TODO Auto-generated constructor stub
	}

	@EventTarget
	public void onUpdate(EventUpdate e) {

		if (mc.thePlayer.isCollidedHorizontally){
			mc.gameSettings.keyBindJump.pressed = true;
		}
		}
	}


