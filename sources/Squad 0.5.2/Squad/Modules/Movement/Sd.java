package Squad.Modules.Movement;

import Squad.base.Module;

public class Sd extends Module {

	public Sd() {
		super("Spider2", 0, 0, Category.Player);
		// TODO Auto-generated constructor stub
	}
	public void onTick(){
			if(mc.thePlayer.isCollidedHorizontally) {
				mc.thePlayer.stepHeight = 10;
		}
	}

}
