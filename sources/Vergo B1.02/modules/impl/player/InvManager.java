package xyz.vergoclient.modules.impl.player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.*;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.util.main.InventoryUtils;
import xyz.vergoclient.util.main.MovementUtils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;

import static xyz.vergoclient.util.main.InventoryUtils.drop;
import static xyz.vergoclient.util.main.InventoryUtils.isBadPotion;

public class InvManager extends Module implements OnEventInterface {

	public InvManager() {
		super("InventoryManager", Category.PLAYER);
	}

	public static transient boolean hasMovedItem = false;

	public ModeSetting mode = new ModeSetting("Mode", "Inv Only", "Silent", "Inv Only");
	public NumberSetting tickDelay = new NumberSetting("Delay", 1, 1, 10, 1);

	public static BooleanSetting dropBowAndArrows = new BooleanSetting("Drop BowAndArrows?", false);
	public static BooleanSetting setTools = new BooleanSetting("Auto-Set Tools", false), dropTools = new BooleanSetting("Drop Tools", false);

	@Override
	public void loadSettings() {
		mode.modes.clear();
		mode.modes.addAll(Arrays.asList("Silent", "Inv Only"));
		addSettings(dropBowAndArrows, setTools, dropTools, tickDelay, mode);
	}

	@Override
	public void onEnable() {
		if(setTools.isEnabled() && dropTools.isEnabled()) {
			dropTools.toggle();
		}
		hasMovedItem = false;
	}

	@Override
	public void onDisable() {
		hasMovedItem = false;
	}

	@Override
	public void onEvent(Event e) {

		if (e instanceof EventTick && e.isPost()) {
			setInfo("Hypixel");

			if (mc.thePlayer.ticksExisted % 60 == 0) {
//				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
			}
		} else if (e instanceof EventTick && e.isPre() && mc.thePlayer.ticksExisted % tickDelay.getValueAsInt() == 0) {

			if (!mode.is("Silent") || !MovementUtils.isMoving()) {
				moveOrDropLoop(e);
			} else if (mode.is("Silent") && hasMovedItem) {
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
				hasMovedItem = false;
			}

		}

	}

	private void moveOrDropLoop(Event e) {
		if (e instanceof EventTick && e.isPre()) {

//			if ((mc.currentScreen == null || (!(mc.currentScreen instanceof GuiChest)) && !(mc.currentScreen instanceof GuiContainerCreative)) && mc.thePlayer.ticksExisted % 4 == 0) {
			if (mode.is("Silent") ? ((mc.currentScreen == null || (!(mc.currentScreen instanceof GuiChest)) && !(mc.currentScreen instanceof GuiContainerCreative))) : (mc.currentScreen != null && mc.currentScreen instanceof GuiInventory)) {

				List<Slot> inventorySlots = Lists.<Slot>newArrayList();
				inventorySlots.addAll(mc.thePlayer.inventoryContainer.inventorySlots);
				Collections.shuffle(inventorySlots);
				for (Slot slot : inventorySlots) {
					ItemStack item = slot.getStack();
					if (item != null) {
						int windowId = mc.thePlayer.inventoryContainer.windowId;
						if (moveOrDropItem(slot, windowId)) {
							if (mc.currentScreen == null) {
//								mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
							}
							if (mode.is("Silent")) {
								hasMovedItem = true;
							}
							return;
						}

					}

				}

				if (mode.is("Silent") && hasMovedItem) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
					hasMovedItem = false;
				}

			} else if (mode.is("Silent") && hasMovedItem && MovementUtils.isMoving()) {
//				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
				hasMovedItem = false;
			}
		}
	}

	private static int swordSlot = 0, pickaxeSlot = 1, axeSlot = 2;

	private static boolean moveOrDropItem(Slot slot, int windowId) {

		if(setTools.isEnabled()) {
			swordSlot = 0;
			pickaxeSlot = 1;
			axeSlot = 2;
		}

		ItemStack stack = slot.getStack();

		if (stack == null || stack.getItem() == null)
			return false;

		if (stack.getItem() instanceof ItemSword) {
			if (InventoryUtils.getBestSwordSlot() != null && InventoryUtils.getBestSwordSlot() == slot) {
				if (slot.slotNumber == swordSlot)
					return false;
				if (!hasMovedItem && Vergo.config.modInventoryManager.mode.is("Silent")) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
				}
				InventoryUtils.swap(slot.slotNumber, swordSlot, windowId);
			} else {
				if (!hasMovedItem && Vergo.config.modInventoryManager.mode.is("Silent")) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
				}
				InventoryUtils.drop(slot.slotNumber, windowId);
			}
			return true;
		} else if (stack.getItem() instanceof ItemPickaxe) {
			if (InventoryUtils.getBestPickaxeSlot() != null && InventoryUtils.getBestPickaxeSlot() == slot) {
				if (slot.slotNumber == pickaxeSlot)
					return false;
				if (!hasMovedItem && Vergo.config.modInventoryManager.mode.is("Silent")) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
				}
				if(setTools.isEnabled()) {
					InventoryUtils.swap(slot.slotNumber, pickaxeSlot, windowId);
				} else {
					if(dropTools.isEnabled()) {
						InventoryUtils.drop(slot.slotNumber, windowId);
					}
				}
			} else {
				if (!hasMovedItem && Vergo.config.modInventoryManager.mode.is("Silent")) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
				}
				if(setTools.isEnabled()) {
					InventoryUtils.swap(slot.slotNumber, pickaxeSlot, windowId);
				} else {
					if(dropTools.isEnabled()) {
						InventoryUtils.drop(slot.slotNumber, windowId);
					}
				}
			}
			return true;
		} else if (stack.getItem() instanceof ItemAxe) {
			if (InventoryUtils.getBestAxeSlot() != null && InventoryUtils.getBestAxeSlot() == slot) {
				if (slot.slotNumber == axeSlot)
					return false;
				if (!hasMovedItem && Vergo.config.modInventoryManager.mode.is("Silent")) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
				}
				if(setTools.isEnabled()) {
					InventoryUtils.swap(slot.slotNumber, axeSlot, windowId);
				} else {
					if(dropTools.isEnabled()) {
						InventoryUtils.drop(slot.slotNumber, windowId);
					}
				}
			} else {
				if (!hasMovedItem && Vergo.config.modInventoryManager.mode.is("Silent")) {
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
				}
				if(setTools.isEnabled()) {
					InventoryUtils.swap(slot.slotNumber, axeSlot, windowId);
				} else {
					if(dropTools.isEnabled()) {
						InventoryUtils.drop(slot.slotNumber, windowId);
					}
				}
			}
			return true;
		} else if (stack.getItem() instanceof ItemSpade || stack.getItem() instanceof ItemHoe) {
			if (!hasMovedItem && Vergo.config.modInventoryManager.mode.is("Silent")) {
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
			}
			InventoryUtils.drop(slot.slotNumber, windowId);
			return true;
		} else if (stack.getItem() instanceof ItemArmor) {

			// Those slot numbers are for the currently equipped armor
			if (slot.slotNumber >= 5 && slot.slotNumber <= 8)
				return false;

			for (int type = 1; type < 5; type++) {
				ItemStack currentArmor = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
				if (AutoArmor.isBestArmor(stack, type) && (currentArmor == null || !AutoArmor.isBestArmor(currentArmor, type))) {
					return false;
				}
			}

			if (!hasMovedItem && Vergo.config.modInventoryManager.mode.is("Silent")) {
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY));
			}
			InventoryUtils.drop(slot.slotNumber, windowId);
			return true;
		} else {
			if (isTrash(stack)) {
				InventoryUtils.drop(slot.slotNumber, windowId);
			}
		}

		return false;

	}

	public static boolean isTrash(ItemStack item) {

		if (dropBowAndArrows.isDisabled()) {
			if (item.getItem().getUnlocalizedName().contains("bow") || (item.getItem().getUnlocalizedName().contains("arrow"))) {
				return ((item.getItem().getUnlocalizedName().equals("tnt")) || item.getDisplayName().contains("Frog") ||
						(item.getItem().getUnlocalizedName().contains("stick")) || (item.getItem().getUnlocalizedName().contains("ore")) ||
						(item.getItem().getUnlocalizedName().contains("string")) || (item.getItem().getUnlocalizedName().contains("flint")) ||
						(item.getItem().getUnlocalizedName().contains("feather")) || (item.getItem().getUnlocalizedName().contains("bucket")) ||
						(item.getItem().getUnlocalizedName().contains("snow")) || (item.getItem().getUnlocalizedName().contains("enchant")) ||
						(item.getItem().getUnlocalizedName().contains("exp")) || (item.getItem().getUnlocalizedName().contains("shears")) || (item.getItem().getUnlocalizedName().contains("anvil")) ||
						(item.getItem().getUnlocalizedName().contains("chest")) ||
						(item.getItem().getUnlocalizedName().contains("note")) || (item.getItem().getUnlocalizedName().contains("slab")) ||
						(item.getItem().getUnlocalizedName().contains("jukebox")) || (item.getItem().getUnlocalizedName().contains("coal")) ||
						(item.getItem().getUnlocalizedName().contains("torch")) || (item.getItem().getUnlocalizedName().contains("seeds")) ||
						(item.getItem().getUnlocalizedName().contains("leather")) || (item.getItem().getUnlocalizedName().contains("boat")) ||
						(item.getItem().getUnlocalizedName().contains("fishing")) || (item.getItem().getUnlocalizedName().contains("wheat")) ||
						(item.getItem().getUnlocalizedName().contains("flower")) || (item.getItem().getUnlocalizedName().contains("record")) ||
						(item.getItem().getUnlocalizedName().contains("note")) || (item.getItem().getUnlocalizedName().contains("sugar")) ||
						(item.getItem().getUnlocalizedName().contains("redstone")) || (item.getItem().getUnlocalizedName().contains("gunpowder")) ||
						(item.getItem().getUnlocalizedName().contains("lever")) || (item.getItem().getUnlocalizedName().contains("sand")) ||
						(item.getItem().getUnlocalizedName().contains("wire")) || (item.getItem().getUnlocalizedName().contains("trip")) ||
						(item.getItem().getUnlocalizedName().contains("slime")) || (item.getItem().getUnlocalizedName().contains("web")) ||
						((item.getItem() instanceof ItemGlassBottle)) || (item.getItem().getUnlocalizedName().contains("piston")) ||
						(item.getItem().getUnlocalizedName().contains("potion") && (isBadPotion(item))) ||
						//   ((item.getItem() instanceof ItemArmor) && isBestArmor(item)) ||
						(item.getItem() instanceof ItemEgg && !item.getDisplayName().contains("Kit")) ||
						//   ((item.getItem() instanceof ItemSword) && !isBestSword(item)) ||
						(item.getItem().getUnlocalizedName().contains("Raw")));
			}

		}

			return ((item.getItem().getUnlocalizedName().contains("tnt")) || item.getDisplayName().contains("Frog") ||
					(item.getItem().getUnlocalizedName().contains("stick")) || (item.getItem().getUnlocalizedName().contains("ore")) ||
					(item.getItem().getUnlocalizedName().contains("string")) || (item.getItem().getUnlocalizedName().contains("flint")) ||
					(item.getItem().getUnlocalizedName().contains("feather")) || (item.getItem().getUnlocalizedName().contains("bucket")) ||
					(item.getItem().getUnlocalizedName().contains("snow")) || (item.getItem().getUnlocalizedName().contains("enchant")) ||
					(item.getItem().getUnlocalizedName().contains("exp")) || (item.getItem().getUnlocalizedName().contains("shears")) || (item.getItem().getUnlocalizedName().contains("anvil")) ||
					(item.getItem().getUnlocalizedName().contains("chest")) ||
					(item.getItem().getUnlocalizedName().contains("note")) || (item.getItem().getUnlocalizedName().contains("slab")) ||
					(item.getItem().getUnlocalizedName().contains("jukebox")) || (item.getItem().getUnlocalizedName().contains("coal")) ||
					(item.getItem().getUnlocalizedName().contains("torch")) || (item.getItem().getUnlocalizedName().contains("seeds")) ||
					(item.getItem().getUnlocalizedName().contains("leather")) || (item.getItem().getUnlocalizedName().contains("boat")) ||
					(item.getItem().getUnlocalizedName().contains("fishing")) || (item.getItem().getUnlocalizedName().contains("wheat")) ||
					(item.getItem().getUnlocalizedName().contains("flower")) || (item.getItem().getUnlocalizedName().contains("record")) ||
					(item.getItem().getUnlocalizedName().contains("note block")) || (item.getItem().getUnlocalizedName().contains("sugar")) ||
					(item.getItem().getUnlocalizedName().contains("redstone")) || (item.getItem().getUnlocalizedName().contains("gunpowder")) ||
					(item.getItem().getUnlocalizedName().contains("lever")) || (item.getItem().getUnlocalizedName().contains("sand")) ||
					(item.getItem().getUnlocalizedName().contains("wire")) || (item.getItem().getUnlocalizedName().contains("trip")) ||
					(item.getItem().getUnlocalizedName().contains("slime")) || (item.getItem().getUnlocalizedName().contains("web")) ||
					((item.getItem() instanceof ItemGlassBottle)) || (item.getItem().getUnlocalizedName().contains("piston")) ||
					(item.getItem().getUnlocalizedName().contains("potion") && (isBadPotion(item))) || (item.getItem().getUnlocalizedName().contains("arrow")) ||
					//   ((item.getItem() instanceof ItemArmor) && isBestArmor(item)) ||
					(item.getItem() instanceof ItemEgg  || item.getItem().getUnlocalizedName().contains("bow") && !item.getDisplayName().contains("Kit")) ||
					//   ((item.getItem() instanceof ItemSword) && !isBestSword(item)) ||
					(item.getItem().getUnlocalizedName().contains("Raw")));
	}
	
}
