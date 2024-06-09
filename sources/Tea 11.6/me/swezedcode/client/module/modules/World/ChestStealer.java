package me.swezedcode.client.module.modules.World;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPostMotionUpdates;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.timer.TimerUtils;
import me.swezedcode.client.utils.values.BooleanValue;
import me.swezedcode.client.utils.values.NumberValue;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;

public class ChestStealer extends Module {

	public ChestStealer() {
		super("ChestStealer", Keyboard.KEY_NONE, 0xFFFFFFFF, ModCategory.World);
		setDisplayName("Chest Stealer");
		timer = new TimerUtils();
	}

	private final BooleanValue offWhenDead = new BooleanValue(this, "Toggle_Of_When_Dead", "toggleOfWhenDead", false);
	private long delay = 20;

	private final TimerUtils timer;

	public boolean isContainerEmpty(Container container) {
		boolean temp = true;
		for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
			if (container.getSlot(i).getHasStack()) {
				temp = false;
			}
		}
		return temp;
	}

	@EventListener
	public void onUpdate(final EventPreMotionUpdates e) {
		if (this.offWhenDead.getValue()) {
			if (this.mc.thePlayer.isDead) {
				this.setToggled(false);
			}
		}
		final ContainerChest container = (ContainerChest) this.mc.thePlayer.openContainer;
		if ((this.mc.thePlayer.openContainer != null) && ((this.mc.thePlayer.openContainer instanceof ContainerChest))) {
			if (!isContainerEmpty(mc.thePlayer.openContainer)) {
				for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); i++) {
					if ((container.getLowerChestInventory().getStackInSlot(i) != null)
							&& (timer.hasReached(delay))) {
						this.mc.playerController.windowClick(container.windowId, i, 0, 1, this.mc.thePlayer);
						timer.rt();
					}
				}
			}else{
				mc.thePlayer.closeScreen();
			}
		}
	}

}
