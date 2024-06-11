package Hydro.module.modules.movement;

import Hydro.module.Category;
import Hydro.module.Module;

public class AirJump extends Module {

	public AirJump() {
		super("AirJump", 0, true, Category.MOVEMENT, "Jump in the air");
	}
	
	@Override
	public void onUpdate() {
		mc.thePlayer.onGround = true;
	}

}
