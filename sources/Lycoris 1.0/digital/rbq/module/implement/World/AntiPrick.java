package digital.rbq.module.implement.World;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.BlockCactus;
import net.minecraft.util.AxisAlignedBB;
import digital.rbq.event.BBSetEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;

public class AntiPrick extends Module {

	public AntiPrick() {
		super("AntiPrick", Category.World, false);
	}

	public void onEnable() {
		super.onEnable();
	}

	public void onDisable() {
		super.onDisable();
	}

	@EventTarget
	private void onBB(BBSetEvent event) {
		if (event.block instanceof BlockCactus) {
			event.boundingBox = new AxisAlignedBB(event.pos.getX(), event.pos.getY(), event.pos.getZ(), event.pos.getX() + 1, event.pos.getY() + 1, event.pos.getZ() + 1);
		}
	}

}
