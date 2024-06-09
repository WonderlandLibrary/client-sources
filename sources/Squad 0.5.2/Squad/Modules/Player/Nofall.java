package Squad.Modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;

public class Nofall extends Module {

	public Nofall() {
		super("Nofall", Keyboard.KEY_NONE, 0xfffffff, Category.Player);

	}

	@EventTarget
	public void onUpdate(EventUpdate event){
		setDisplayname("NoFall");
	if(mc.thePlayer.onGround)
	{		
		mc.thePlayer.capabilities.isFlying = false;
mc.gameSettings.keyBindSneak.pressed = false;
	}else{
		
		mc.gameSettings.keyBindSneak.pressed = true;

		mc.thePlayer.capabilities.isFlying = true;

		
	}
	}
	@Override
	public void onDisable(){
		mc.gameSettings.keyBindSneak.pressed = false;
		mc.thePlayer.capabilities.isFlying = false;

	}
	
}