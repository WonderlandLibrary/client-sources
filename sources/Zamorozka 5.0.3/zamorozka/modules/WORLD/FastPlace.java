package zamorozka.modules.WORLD;

import zamorozka.module.Category;
import zamorozka.module.Module;

public class FastPlace extends Module{
	
	public FastPlace(){
		super("FastPlace", 0, Category.WORLD);
	}
	
	public void onUpdate(){
		if(this.getState()){
			mc.rightClickDelayTimer = 0;
		}
	}
	
	public void onDisable(){
		mc.rightClickDelayTimer = 6;
	}

}

