package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventSafewalk;
import lunadevs.luna.module.Module;

public class SafeWalk extends Module{

	public SafeWalk() {
		super("SafeWalk", Keyboard.KEY_NONE, Category.MOVEMENT, false);
	}
	
	 @EventTarget
	  public void eventSafeWalk(EventSafewalk e)
	  {
	    if (this.isEnabled) {
	      if ((mc.gameSettings.keyBindJump.pressed) || (!mc.thePlayer.onGround)) {
	        e.setCancelled(false);
	      } else {
	        e.setCancelled(true);
	      }
	    }
	  }
	
	public String getValue() {
		return null;
	}
	
}
