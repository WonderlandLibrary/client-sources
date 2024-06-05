package de.tired.module.impl.list.misc;

import de.tired.api.util.math.TimerUtil;
import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

@ModuleAnnotation(name = "ChestStealer", category = ModuleCategory.MISC, clickG = "Looting chests for you.")
public class ChestStealer extends Module {

	public NumberSetting delay = new NumberSetting("Delay", this, 12, 1, 1000, 1);

	public BooleanSetting close = new BooleanSetting("Close", this, true);

	private final TimerUtil timerUtil = new TimerUtil();

	@EventTarget
	public void onUpdate(UpdateEvent e) {

		if (MC.currentScreen instanceof GuiChest) {
			final ContainerChest chest  = (ContainerChest) MC.thePlayer.openContainer;
			for (int i = 0;  i < chest.getLowerChestInventory().getSizeInventory(); ++i) {
				final ItemStack itemStack = chest.getLowerChestInventory().getStackInSlot(i);

				MC.inGameHasFocus = true;
				MC.mouseHelper.grabMouseCursor();
				if (itemStack != null) {
					if (timerUtil.reachedTime(delay.getValueInt())) {
						MC.playerController.windowClick(chest.windowId, i, 0, 1, MC.thePlayer);
						timerUtil.doReset();
					}
				}
			}
			if (containerEmpty(chest) && close.getValue()) {
				MC.thePlayer.closeScreen();
			}
		}

	}

	private boolean containerEmpty(Container container) {
		boolean empty = true;
		int i = 0;
		for(int slot = container.inventorySlots.size() == 90 ? 54 : 27; i < slot; ++i) {
			if (container.getSlot(i).getHasStack()) {
				empty = false;
			}
		}

		return empty;
	}

	@Override
	public void onState() {

	}

	@Override
	public void onUndo() {

	}
}
