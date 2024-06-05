package zamorozka.modules.TRAFFIC;

import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.MouseUtilis;

public class PacketXP extends Module {

	public PacketXP() {
		super("PacketXP", 0, Category.TRAFFIC);
	}
	
	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		mc.rightClickDelayTimer = 0;
		event.setPitch(90);
			EnumHand hand = EnumHand.MAIN_HAND;
			if (this.mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) { hand = EnumHand.OFF_HAND; }
			 else if (this.mc.player.getHeldItemMainhand().getItem() != Items.EXPERIENCE_BOTTLE)
			{ for (int i = 0; i < 9; i++) {
			  if (this.mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
			 this.mc.player.inventory.currentItem = i;
				MouseUtilis.setMouse(1, true);
			  }
			}
			}
	
	}
	
	@Override
	public void onDisable() {
		MouseUtilis.setMouse(1, false);
		super.onDisable();
	}

}