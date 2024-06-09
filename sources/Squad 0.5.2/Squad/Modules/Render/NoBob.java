package Squad.Modules.Render;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.Utils.Wrapper;
import Squad.base.Module;

public class NoBob extends Module{

	public NoBob() {
		super("NoBob", Keyboard.KEY_NONE, 0x888888, Category.Render);
	}
	
	@EventTarget
	public void onTicK(EventUpdate e){
		Wrapper.getMC().thePlayer.distanceWalkedModified = 88f;
	}
	
	public void onEnable(){
		EventManager.register(this);
	}
	
	public void onDisable(){
		EventManager.unregister(this);
		Wrapper.getMC().thePlayer.distanceWalkedModified = 0f;
	}
	
	

}
