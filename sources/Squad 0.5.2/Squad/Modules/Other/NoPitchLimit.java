package Squad.Modules.Other;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;

import Squad.base.Module;
import Squad.base.ModuleManager;

public class NoPitchLimit extends Module{

	public NoPitchLimit() {
		super("NoPitchLimit", Keyboard.KEY_NONE, 0x88, Category.Other);
		// TODO Auto-generated constructor stub
	}

	public  void onEnable(){
		EventManager.register(this);
	}
	
	public void onDisable(){
		EventManager.unregister(this);
	}
	
}
