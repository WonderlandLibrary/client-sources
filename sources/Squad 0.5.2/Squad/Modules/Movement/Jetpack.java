package Squad.Modules.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;

public class Jetpack extends Module{

	public Jetpack() {
		super("JetPack", Keyboard.KEY_NONE, 0x88, Category.Movement);
		// TODO Auto-generated constructor stub
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e){
		if(this.isEnabled()){
			if(mc.gameSettings.keyBindJump.pressed){
				mc.thePlayer.motionY += 0.07;
			}
		}
	}

}
