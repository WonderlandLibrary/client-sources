package Hydro.module.modules.movement;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;

public class Sprint extends Module {

	public Sprint() {
		super("Sprint", 0, true, Category.MOVEMENT, "Automatically sprints");
		Client.instance.settingsManager.rSetting(new Setting("SprintOmni", "OmniSprint", this, false));
	}
	
	@Override
	public void onUpdate() {
		if(!Client.instance.settingsManager.getSettingByName("SprintOmni").getValBoolean()) {
			if(mc.thePlayer.moveForward > 0 ){
				if(!mc.thePlayer.isUsingItem()) {
					if(!mc.thePlayer.isSneaking()) {
						if(!mc.thePlayer.isCollidedHorizontally) {
							if(!(mc.thePlayer.getFoodStats().getFoodLevel() < 3)) {
								mc.thePlayer.setSprinting(true);
							}
						}
					}
				}
			} 
		}else {
			if(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()){
				if(!mc.thePlayer.isUsingItem()) {
					if(!mc.thePlayer.isSneaking()) {
						if(!mc.thePlayer.isCollidedHorizontally) {
							if(!(mc.thePlayer.getFoodStats().getFoodLevel() < 3)) {
								mc.thePlayer.setSprinting(true);
							}
						}
					}
				}
			} 
		}
	}

}
