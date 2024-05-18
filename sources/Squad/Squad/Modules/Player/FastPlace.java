package Squad.Modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.Utils.Wrapper;
import Squad.base.Module;
import net.minecraft.init.Blocks;

public class FastPlace extends Module{

	public FastPlace() {
		super("FastPlace", Keyboard.KEY_NONE, 0x888888, Category.Player);
		
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e){
	   
		if(this.getState()){
		Wrapper.getMC().rightClickDelayTimer = 0;
	}
	}
	
	public void onEnable(){
		EventManager.register(this);
	}
	
	
	public void onDisable(){
		EventManager.register(this);
		Wrapper.getMC().rightClickDelayTimer = 6;
	}

}
