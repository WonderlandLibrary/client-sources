package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoRespawn extends Module {

	public AutoRespawn() {
		super("AutoRespawn", Keyboard.KEY_NONE, Category.PLAYER);
		// TODO Auto-generated constructor stub
	}
	
	public void onUpdate()
	  {
	    if (getState()) {
	    	
	    }
	    if (mc.player.isEntityAlive()) {
	      mc.player.respawnPlayer();
	    }
	  }
	
	public String getValue() {
		return null;
	}
	
}
