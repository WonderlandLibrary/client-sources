package lunadevs.luna.module.movement;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
/**
 * 
 * @author SLiZ_D_2017
 *
 */
public class AirHop extends Module{

	public AirHop() {
		super("AirHop", 0, Category.MOVEMENT, false);
	}
	
	@Override
	public void onUpdate() {
		if(!this.isEnabled) return;
        if(mc.gameSettings.keyBindJump.isPressed()){
        	mc.thePlayer.motionX *= 1.5;
        	mc.thePlayer.motionY = 0.4;
        	mc.thePlayer.motionZ *= 1.5;
        	mc.thePlayer.onGround =  true;
        }   
		super.onUpdate();
	}

	@Override
	public String getValue() {
		return null;
	}

}
