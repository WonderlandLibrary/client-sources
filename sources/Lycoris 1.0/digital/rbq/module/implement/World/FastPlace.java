package digital.rbq.module.implement.World;

import com.darkmagician6.eventapi.EventTarget;
import digital.rbq.event.TickEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;

public class FastPlace extends Module {
	public FastPlace() {
		super("FastPlace", Category.World, false);
	}

	private boolean shit;

	@EventTarget
	public void onTick(TickEvent event) {
		if (this.mc.getRightClickDelayTimer() < 4 || (shit && this.mc.getRightClickDelayTimer() < 5)) {
			this.mc.setRightClickDelayTimer(0);
			shit = !shit;
		}
	}
}
