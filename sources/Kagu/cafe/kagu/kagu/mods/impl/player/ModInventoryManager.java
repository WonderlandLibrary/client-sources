/**
 * 
 */
package cafe.kagu.kagu.mods.impl.player;

import java.util.Arrays;
import java.util.List;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.InventoryUtils;
import cafe.kagu.kagu.utils.MiscUtils;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.world.WorldSettings.GameType;

/**
 * @author lavaflowglow
 *
 */
public class ModInventoryManager extends Module {
	
	public ModInventoryManager() {
		super("Inventory Manager", Category.PLAYER);
		setSettings(c0ePosition, mode, instant, delay, inventorySpoof, weaponChoice, dropItems, dropNonScaffoldCompatibleBlocks, adventureCheck, autoArmor, armorSwapMode);
	}
	
	private ModeSetting c0ePosition = new ModeSetting("C0E Position", "PRE", "PRE", "POST");
	private ModeSetting mode = new ModeSetting("Mode", "Silent", "Silent", "Inventory Open", "No Movement");
	private ModeSetting inventorySpoof = new ModeSetting("Inventory Spoof", "None", "None", "Open & Close on Action", "Open on Start, Close on Stop").setDependency(() -> mode.is("Silent") || mode.is("No Movement"));
	private ModeSetting weaponChoice = new ModeSetting("Weapon Choice", "Sword", "Sword", "Axe");
	private BooleanSetting instant = new BooleanSetting("Instant", false);
	private IntegerSetting delay = new IntegerSetting("Millis Delay", 0, 0, 2500, 50).setDependency(instant::isDisabled);
	private BooleanSetting dropItems = new BooleanSetting("Drop Items", true);
	private BooleanSetting dropNonScaffoldCompatibleBlocks = new BooleanSetting("Drop Non Scaffold Compatible Blocks", true).setDependency(dropItems::isEnabled);
	private BooleanSetting adventureCheck = new BooleanSetting("Adventure Mode Check", true);
	private BooleanSetting autoArmor = new BooleanSetting("Auto Armor", true);
	private ModeSetting armorSwapMode = new ModeSetting("Armor Swap Mode", "Instant", "Instant", "Separate actions").setDependency(() -> instant.isDisabled() && autoArmor.isEnabled());
	
	private TimerUtil delayTimer = new TimerUtil();
	private boolean dropOrSort = false;
	private boolean inventoryOpen = false;
	
	private final int weaponSlot = 0 + 36;
	private final int bowSlot = 1 + 36;
	private final int pickaxeSlot = 2 + 36;
	private final int axeSlot = 3 + 36;
	private final int spadeSlot = 4 + 36;
	private final int shearsSlot = 5 + 36;
	private final int gappleSlot = 6 + 36;
	private final int blockSlot = 7 + 36;
	private final int helmetSlot = 5;
	private final int chestplateSlot = 6;
	private final int leggingsSlot = 7;
	private final int bootsSlot = 8;
	
	private Slot bestWeapon = null;
	private Slot bestBow = null;
	private Slot bestPickaxe = null;
	private Slot bestAxe = null;
	private Slot bestSpade = null;
	private Slot bestShears = null;
	private Slot mostGapples = null;
	private Slot mostBlocks = null;
	private Slot bestHelmet = null;
	private Slot bestChestplate = null;
	private Slot bestLeggings = null;
	private Slot bestBoots = null;
	
	@Override
	public void onEnable() {
		inventoryOpen = false;
	}
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (c0ePosition.is("PRE") ? e.isPost() : e.isPre())
			return;
		
		if (mode.is("Inventory Open") && !(mc.currentScreen instanceof GuiInventory)) {
			return;
		}
		else if (!mode.is("Inventory Open") && mc.currentScreen != null) {
			return;
		}
		
		if (mode.is("No Movement") && MovementUtils.isPlayerMoving()) {
			closeInventory();
			return;
		}
		
		if (instant.isDisabled() && !delayTimer.hasTimeElapsed(delay.getValue(), false))
			return;
		
		if (adventureCheck.isEnabled() && mc.playerController.getCurrentGameType() == GameType.ADVENTURE) {
			closeInventory();
			return;
		}
		
		Container container = mc.thePlayer.inventoryContainer;
		List<Slot> slots = container.getInventorySlots();
		setBestGear(slots);
		
		if (dropItems.isEnabled() && dropOrSort) {
			if (drop()) {
				if (inventorySpoof.is("Open & Close on Action"))
					closeInventory();
				dropOrSort = !dropOrSort;
				delayTimer.reset();
				if (instant.isDisabled())
					return;
			}
		}
		
		if (instant.isEnabled()) {
			setBestGear(slots);
		}
		
		if (sort()) {
			if (inventorySpoof.is("Open & Close on Action"))
				closeInventory();
			dropOrSort = !dropOrSort;
			delayTimer.reset();
			if (instant.isDisabled())
				return;
		}
		
		if (instant.isEnabled()) {
			setBestGear(slots);
		}
		
		if (dropItems.isEnabled()) {
			if (drop()) {
				if (inventorySpoof.is("Open & Close on Action"))
					closeInventory();
				dropOrSort = false;
				delayTimer.reset();
				if (instant.isDisabled())
					return;
			}
		}
		
		dropOrSort = !dropOrSort;
		closeInventory();
	};
	
	/**
	 * Used to fix a bug where it would double send inventory open
	 */
	@EventHandler
	private Handler<EventPacketSend> onPacketSend = e -> {
		if (e.isPost())
			return;
		if (mode.is("Inventory Open") || inventorySpoof.is("None"))
			return;
		if (e.getPacket() instanceof C0DPacketCloseWindow) {
			C0DPacketCloseWindow c0d = (C0DPacketCloseWindow)e.getPacket();
			if (c0d.getWindowId() != mc.thePlayer.inventoryContainer.windowId)
				return;
			if (!inventoryOpen || mc.currentScreen instanceof GuiInventory)
				e.cancel();
			else
				inventoryOpen = false;
		}
	};
	
	/**
	 * Opens inventory
	 */
	private void openInventory() {
		if (inventoryOpen || mode.is("Inventory Open") || inventorySpoof.is("None"))
			return;
		mc.thePlayer.triggerAchievement(AchievementList.openInventory);
		inventoryOpen = true;
	}
	
	/**
	 * Closes inventory
	 */
	private void closeInventory() {
		if (!inventoryOpen || mode.is("Inventory Open") || inventorySpoof.is("None"))
			return;
		InventoryUtils.sendCloseContainer(mc.thePlayer.inventoryContainer);
	}
	
	/**
	 * Drops items
	 * @return true if it did an action, otherwise false
	 */
	private boolean drop() {
		EntityPlayerSP thePlayer = mc.thePlayer;
		Container container = thePlayer.inventoryContainer;
		List<Slot> slots = container.getInventorySlots();
		
		Slot bestWeapon = this.bestWeapon;
		Slot bestBow = this.bestBow;
		Slot bestPickaxe = this.bestPickaxe;
		Slot bestAxe = this.bestAxe;
		Slot bestSpade = this.bestSpade;
		Slot bestShears = this.bestShears;
		Slot bestHelmet = this.bestHelmet;
		Slot bestChestplate = this.bestChestplate;
		Slot bestLeggings = this.bestLeggings;
		Slot bestBoots = this.bestBoots;
		Slot[] ignoreSlots = new Slot[] {bestWeapon, bestBow, bestPickaxe, bestAxe, bestSpade, bestShears, bestHelmet, bestChestplate, bestLeggings, bestBoots};
		
		for (int i = 9; i < slots.size(); i++) {
			Slot slot = slots.get(i);
			if (!slot.getHasStack() || slot == null || Arrays.stream(ignoreSlots).anyMatch(s -> s == slot))
				continue;
			ItemStack stack = slot.getStack();
			Item item = stack.getItem();
			
			if (item instanceof ItemTool || item instanceof ItemSword || (item instanceof ItemBow
					&& !MiscUtils.removeFormatting(slot.getStack().getDisplayName()).toLowerCase().contains("kit"))
					|| (item instanceof ItemBlock && dropNonScaffoldCompatibleBlocks.isEnabled() && ((ItemBlock) item).getBlock().doesBlockActivate())
					|| item instanceof ItemArmor) {
				openInventory();
				InventoryUtils.dropItem(container, slot.getSlotNumber());
				if (instant.isDisabled())
					return true;
			}
			
		}
		
		return false;
	}
	
	/**
	 * Sorts items
	 * @return true if it did an action, otherwise false
	 */
	private boolean sort() {
		EntityPlayerSP thePlayer = mc.thePlayer;
		Container container = thePlayer.inventoryContainer;
		
		Slot bestWeapon = this.bestWeapon;
		Slot bestBow = this.bestBow;
		Slot bestPickaxe = this.bestPickaxe;
		Slot bestAxe = this.bestAxe;
		Slot bestSpade = this.bestSpade;
		Slot bestShears = this.bestShears;
		Slot mostGapples = this.mostGapples;
		Slot mostBlocks = this.mostBlocks;
		Slot bestHelmet = this.bestHelmet;
		Slot bestChestplate = this.bestChestplate;
		Slot bestLeggings = this.bestLeggings;
		Slot bestBoots = this.bestBoots;
		
		// Weapon
		if (bestWeapon != null && bestWeapon.getSlotNumber() != weaponSlot) {
			openInventory();
			InventoryUtils.swapHotbarItems(container, weaponSlot, bestWeapon.getSlotNumber());
			if (instant.isDisabled())
				return true;
		}
		
		// Bow
		if (bestBow != null && bestBow.getSlotNumber() != bowSlot) {
			openInventory();
			InventoryUtils.swapHotbarItems(container, bowSlot, bestBow.getSlotNumber());
			if (instant.isDisabled())
				return true;
		}
		
		// Pickaxe
		if (bestPickaxe != null && bestPickaxe.getSlotNumber() != pickaxeSlot) {
			openInventory();
			InventoryUtils.swapHotbarItems(container, pickaxeSlot, bestPickaxe.getSlotNumber());
			if (instant.isDisabled())
				return true;
		}
		
		// Axe
		if (bestAxe != null && bestAxe.getSlotNumber() != axeSlot && bestAxe.getSlotNumber() != weaponSlot) {
			openInventory();
			InventoryUtils.swapHotbarItems(container, axeSlot, bestAxe.getSlotNumber());
			if (instant.isDisabled())
				return true;
		}
		
		// Spade
		if (bestSpade != null && bestSpade.getSlotNumber() != spadeSlot) {
			openInventory();
			InventoryUtils.swapHotbarItems(container, spadeSlot, bestSpade.getSlotNumber());
			if (instant.isDisabled())
				return true;
		}
		
		// Shears
		if (bestShears != null && bestShears.getSlotNumber() != shearsSlot) {
			openInventory();
			InventoryUtils.swapHotbarItems(container, shearsSlot, bestShears.getSlotNumber());
			if (instant.isDisabled())
				return true;
		}
		
		// Gapples
		if (mostGapples != null && mostGapples.getSlotNumber() != gappleSlot) {
			openInventory();
			InventoryUtils.swapHotbarItems(container, gappleSlot, mostGapples.getSlotNumber());
			if (instant.isDisabled())
				return true;
		}
		
		// Blocks
		if (mostBlocks != null && mostBlocks.getSlotNumber() != blockSlot) {
			openInventory();
			InventoryUtils.swapHotbarItems(container, blockSlot, mostBlocks.getSlotNumber());
			if (instant.isDisabled())
				return true;
		}
		
		// Auto armor
		if (autoArmor.isEnabled()) {
			if (bestChestplate != null && bestChestplate.getSlotNumber() != chestplateSlot) {
				openInventory();
				if (container.getSlot(chestplateSlot).getHasStack()) {
					InventoryUtils.dropItem(container, chestplateSlot);
					if (instant.isDisabled() && armorSwapMode.is("Separate actions")) {
						return true;
					}
				}
				InventoryUtils.shiftLeftClick(container, bestChestplate.getSlotNumber());
				if (instant.isDisabled())
					return true;
			}
			if (bestLeggings != null && bestLeggings.getSlotNumber() != leggingsSlot) {
				openInventory();
				if (container.getSlot(leggingsSlot).getHasStack()) {
					InventoryUtils.dropItem(container, leggingsSlot);
					if (instant.isDisabled() && armorSwapMode.is("Separate actions")) {
						return true;
					}
				}
				InventoryUtils.shiftLeftClick(container, bestLeggings.getSlotNumber());
				if (instant.isDisabled())
					return true;
			}
			if (bestHelmet != null && bestHelmet.getSlotNumber() != helmetSlot) {
				openInventory();
				if (container.getSlot(helmetSlot).getHasStack()) {
					InventoryUtils.dropItem(container, helmetSlot);
					if (instant.isDisabled() && armorSwapMode.is("Separate actions")) {
						return true;
					}
				}
				InventoryUtils.shiftLeftClick(container, bestHelmet.getSlotNumber());
				if (instant.isDisabled())
					return true;
			}
			if (bestBoots != null && bestBoots.getSlotNumber() != bootsSlot) {
				openInventory();
				if (container.getSlot(bootsSlot).getHasStack()) {
					InventoryUtils.dropItem(container, bootsSlot);
					if (instant.isDisabled() && armorSwapMode.is("Separate actions")) {
						return true;
					}
				}
				InventoryUtils.shiftLeftClick(container, bestBoots.getSlotNumber());
				if (instant.isDisabled())
					return true;
			}
		}
		
		return false;
	}

	private void setBestGear(List<Slot> slots) {
		Slot[] bestGearSet = InventoryUtils.getBestGearSetInInventory(slots, weaponChoice.is("Sword"), weaponSlot, bowSlot, pickaxeSlot, axeSlot, spadeSlot, shearsSlot, blockSlot, gappleSlot);
		bestWeapon = bestGearSet[0];
		bestBow = bestGearSet[1];
		bestPickaxe = bestGearSet[2];
		bestAxe = bestGearSet[3];
		bestSpade = bestGearSet[4];
		bestShears = bestGearSet[5];
		mostGapples = bestGearSet[6];
		mostBlocks = bestGearSet[7];
		bestHelmet = bestGearSet[8];
		bestChestplate = bestGearSet[9];
		bestLeggings = bestGearSet[10];
		bestBoots = bestGearSet[11];
	}
	
}
