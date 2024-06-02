/**
 * 
 */
package cafe.kagu.kagu.mods.impl.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.InventoryUtils;
import cafe.kagu.kagu.utils.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author lavaflowglow
 *
 */
public class ModChestStealer extends Module {
	
	public ModChestStealer() {
		super("ChestStealer", Category.PLAYER);
		setSettings(c0ePosition, chestNameCheck, shuffleOrder, autoClose, instant, firstItemDelay, delay);
	}
	
	private ModeSetting c0ePosition = new ModeSetting("C0E Position", "PRE", "PRE", "POST");
	private BooleanSetting chestNameCheck = new BooleanSetting("Chest Name Check", true);
	private BooleanSetting shuffleOrder = new BooleanSetting("Shuffle Order", false);
	private BooleanSetting autoClose = new BooleanSetting("Auto close chest", true);
	private BooleanSetting instant = new BooleanSetting("Instant", false);
	private BooleanSetting firstItemDelay = new BooleanSetting("First Item Delay", true).setDependency(instant::isDisabled);
	private IntegerSetting delay = new IntegerSetting("Millis Delay", 0, 0, 2500, 50).setDependency(instant::isDisabled);
	
	private TimerUtil delayTimer = new TimerUtil();
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (c0ePosition.is("PRE") ? e.isPost() : e.isPre())
			return;
		if (!(mc.currentScreen instanceof GuiChest)) {
			if (instant.isDisabled() && firstItemDelay.isEnabled())
				delayTimer.reset();
			return;
		}
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		GuiChest guiChest = (GuiChest)mc.currentScreen;
		Container container = guiChest.getInventorySlots();
		
		// Close gui if the players inventory is full
		if (InventoryUtils.isPlayerInventoryFull()) {
			if (autoClose.isEnabled())
				closeChest(container, "your inventory is full");
			return;
		}
		
		// Name check
		if (chestNameCheck.isEnabled()
				&& !guiChest.getLowerChestInventory().getDisplayName().getFormattedText()
						.equalsIgnoreCase(I18n.format("container.chest"))
				&& !guiChest.getLowerChestInventory().getDisplayName().getFormattedText()
						.equalsIgnoreCase(I18n.format("container.chestDouble"))) {
			return;
		}
		
		if (instant.isDisabled() && !delayTimer.hasTimeElapsed(delay.getValue(), false)) {
			return;
		}
		
		List<Slot> slots = new ArrayList<Slot>(container.getInventorySlots());
		List<Slot> realSlots = container.getInventorySlots();
		List<ItemStack> stacksAlreadyInPlayerInventory = new ArrayList<ItemStack>(thePlayer.inventoryContainer.getInventory());
		if (shuffleOrder.isEnabled())
			Collections.shuffle(slots);
		
		// Shift click items
		for (Slot slot : slots) {
			if (slot.getHasStack() && slot.getStack() != null) {
				if (stacksAlreadyInPlayerInventory.contains(slot.getStack()))
					continue;
				InventoryUtils.shiftLeftClick(container, realSlots.indexOf(slot));
				delayTimer.reset();
				if (instant.isDisabled())
					return;
			}
		}
		if (autoClose.isEnabled())
			closeChest(container, "the chest is empty");
	};
	
	/**
	 * Closes the container and send a close packet
	 * @param container The container
	 * @param reason The reason displayed to the user
	 */
	private void closeChest(Container container, String reason) {
		mc.thePlayer.closeScreenAndDropStack();
		InventoryUtils.sendCloseContainer(container);
		ChatUtils.addChatMessage(getName() + ": Closed chest because " + reason);
	}
	
}
