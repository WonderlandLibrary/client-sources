package lunadevs.luna.module.player;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;

public class Parkour extends Module{

	public Parkour() {
		super("Parkour", 0, Category.PLAYER, false);
	}
	
	@Override
	public void onUpdate() {
		if(!this.isEnabled) return;
	    if ((mc.thePlayer.onGround) && (!mc.thePlayer.isSneaking()) && 
	    	      (!mc.gameSettings.keyBindSneak.pressed)) {
	    	      if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(-0.001D, 0.0D, -0.001D)).isEmpty()) {
	    	        mc.thePlayer.jump();
	    	      }
	    	    }
		super.onUpdate();
	}

	@Override
	public String getValue() {
		return null;
	}

}
