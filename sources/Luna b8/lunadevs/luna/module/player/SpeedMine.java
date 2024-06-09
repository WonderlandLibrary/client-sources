package lunadevs.luna.module.player;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventPlayerUpdate;
import lunadevs.luna.module.Module;

public class SpeedMine extends Module{

	public SpeedMine() {
		super("SpeedMine", 0, Category.PLAYER, false);
	}
	
	 @Override
	  public void onUpdate()
	  {
		  if(!this.isEnabled) return;
	    if (mc.playerController.curBlockDamageMP > 0.8F) {
	      mc.playerController.curBlockDamageMP = 1.0F;
	    }
	    mc.playerController.blockHitDelay = 0;
	    super.onUpdate();
	  }

	@Override
	public String getValue() {
		return null;
	}

}
