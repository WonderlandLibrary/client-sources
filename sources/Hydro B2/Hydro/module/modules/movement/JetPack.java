package Hydro.module.modules.movement;

import Hydro.module.Category;
import Hydro.module.Module;

public class JetPack extends Module {

	public JetPack() {
		super("JetPack", 0, true, Category.MOVEMENT, "Makes you propel upwards like a jetpack");
	}
	
	@Override
	public void onUpdate() {
		if(mc.gameSettings.keyBindJump.isKeyDown()) {
			mc.thePlayer.motionY = 0.7;
		}
	}

}
