package zamorozka.modules.PLAYER;

import org.lwjgl.input.Keyboard;

import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoClimb extends Module {

	public AutoClimb() {
		super("AutoClimb", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	public void onUpdate()
	  {
	    if (getState()) {
	    	if(Keyboard.isKeyDown(17)) {
				mc.gameSettings.keyBindForward.pressed = true;
				}else {
				mc.gameSettings.keyBindForward.pressed = false;
			
			if (mc.player.isOnLadder()) {
	            mc.gameSettings.keyBindForward.pressed = true;
			}else {
				mc.gameSettings.keyBindForward.pressed = false;
			}
				}
	    }
	  }
}