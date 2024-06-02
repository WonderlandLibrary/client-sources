/**
 * 
 */
package cafe.kagu.kagu.utils;

import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import optifine.Reflector;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;

/**
 * @author lavaflowglow
 * C0E documentation on wiki.vg >>> https://yiffing.zone/7834e997cd
 * Inventory slots numbered >>> https://wiki.vg/images/1/13/Inventory-slots.png
 */
public class InventoryUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Called at the start of the client
	 */
	public static void start() {
		
	}
	
	/**
	 * @return true if the players inventory is full, otherwise false
	 */
	public static boolean isPlayerInventoryFull() {
		EntityPlayerSP thePlayer = mc.thePlayer;
		List<Slot> slots = thePlayer.inventoryContainer.getInventorySlots();
		
		for (int i = 9; i < 45; i++) {
			try {
				if (!slots.get(i).getHasStack())
					return false;
			} catch (Exception e) {
				
			}
		}
		
		return true;
	}
	
	/**
	 * Sends a close packet for a container
	 * @param container The container to close
	 */
	public static void sendCloseContainer(Container container) {
		mc.getNetHandler().getNetworkManager().sendPacket(new C0DPacketCloseWindow(container.windowId));
	}
	
	/**
	 * Simulates a shift left click inside of a container
	 * @param container The container, used to get transaction id and window id
	 * @param slot The slot to click
	 */
	public static void shiftLeftClick(Container container, int slot) {
		mc.playerController.windowClick(container.windowId, slot, 0, 1, mc.thePlayer);
	}
	
	/**
	 * Swaps two items between the inventory and the hotbar
	 * @param container The container
	 * @param hotbarSlot The first slot
	 * @param slot The inventory slot
	 */
	public static void swapHotbarItems(Container container, int hotbarSlot, int slot) {
		if (hotbarSlot > 35)
			hotbarSlot -= 36;
		mc.playerController.windowClick(container.windowId, slot, hotbarSlot, 2, mc.thePlayer);
	}
	
	/**
	 * Drops all the items inside a specific slot
	 * @param container The container
	 * @param slot The slot to drop
	 */
	public static void dropItem(Container container, int slot) {
		mc.playerController.windowClick(container.windowId, slot, 1, 4, mc.thePlayer);
	}
	
	/**
	 * Calculates the best gear set in the inventory in one pass, each first check should be the slot number that the current item is placed in
	 * @param slots The slots to check
	 * @param sword Set to true if it should only use swords, if it's false it'll search for axe
	 * @param firstWeaponCheck The slot of the first weapon to check
	 * @param firstBowCheck The slot of the first bow to check
	 * @param firstPickaxeCheck The slot of the first pickaxe to check
	 * @param firstAxeCheck The slot of the first axe to check
	 * @param firstSpadeCheck The slot of the first spade to check
	 * @param firstShearsCheck The slot of the first shears to check
	 * @param firstGapplesCheck The slot of the first gapples to check
	 * @param firstBlocksCheck The slot of the first blocks to check
	 * @return An array of slots, the values in the array are in the same order as the first checks with the last four values being the armors listed from helmet to boots
	 */
	public static Slot[] getBestGearSetInInventory(List<Slot> slots, boolean sword, int firstWeaponCheck, int firstBowCheck,
			int firstPickaxeCheck, int firstAxeCheck, int firstSpadeCheck, int firstShearsCheck, int firstGapplesCheck, int firstBlocksCheck) {
//		Slot[] bestItems = new Slot[] { slots.get(firstWeaponCheck), slots.get(firstBowCheck),
//				slots.get(firstPickaxeCheck), slots.get(firstAxeCheck), slots.get(firstSpadeCheck),
//				slots.get(firstShearsCheck), slots.get(firstGapplesCheck), slots.get(firstBlocksCheck), slots.get(5), slots.get(6), slots.get(7), slots.get(8) };
		Slot[] bestItems = new Slot[12];
		double[] bestScores = new double[] { Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE,
				Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE,
				Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE };
		
		// Do first checks
		{ // Weapon
			Slot slot = slots.get(firstWeaponCheck);
			if (slot.getHasStack() && (sword
					? slot.getStack().getItem() instanceof ItemSword
					: slot.getStack().getItem() instanceof ItemAxe)) {
				bestScores[0] = getWeaponScore(slot.getStack(), sword);
				bestItems[0] = slot;
			}
		}
		{ // Bow
			Slot slot = slots.get(firstBowCheck);
			if (slot.getHasStack() && slot.getStack().getItem() instanceof ItemBow && !MiscUtils.removeFormatting(slot.getStack().getDisplayName()).toLowerCase().contains("kit")) {
				bestScores[1] = getBowScore(slot.getStack());
				bestItems[1] = slot;
			}
		}
		{ // Pickaxe
			Slot slot = slots.get(firstPickaxeCheck);
			if (slot.getHasStack() && slot.getStack().getItem() instanceof ItemPickaxe) {
				ItemStack stack = slot.getStack();
				bestScores[2] = getToolScore(stack, (ItemTool)stack.getItem(), Blocks.stone);
				bestItems[2] = slot;
			}
		}
		{ // Axe
			Slot slot = slots.get(firstAxeCheck);
			if (slot.getHasStack() && slot.getStack().getItem() instanceof ItemAxe) {
				ItemStack stack = slot.getStack();
				bestScores[3] = getToolScore(stack, (ItemTool)stack.getItem(), Blocks.planks);
				bestItems[3] = slot;
			}
		}
		{ // Spade
			Slot slot = slots.get(firstSpadeCheck);
			if (slot.getHasStack() && slot.getStack().getItem() instanceof ItemSpade) {
				ItemStack stack = slot.getStack();
				bestScores[4] = getToolScore(stack, (ItemTool)stack.getItem(), Blocks.dirt);
				bestItems[4] = slot;
			}
		}
		{ // Shears
			Slot slot = slots.get(firstShearsCheck);
			if (slot.getHasStack() && slot.getStack().getItem() instanceof ItemShears) {
				ItemStack stack = slot.getStack();
				bestScores[5] = getShearsScore(stack, (ItemShears)stack.getItem());
				bestItems[5] = slot;
			}
		}
		{ // Blocks
			Slot slot = slots.get(firstBlocksCheck);
			if (slot.getHasStack() && slot.getStack().getItem() instanceof ItemBlock && !((ItemBlock)slot.getStack().getItem()).getBlock().doesBlockActivate()) {
				ItemStack stack = slot.getStack();
				bestScores[6] = stack.getStackSize();
				bestItems[6] = slot;
			}
		}
		{ // Gapples
			Slot slot = slots.get(firstGapplesCheck);
			if (slot.getHasStack() && slot.getStack().getItem() instanceof ItemAppleGold) {
				ItemStack stack = slot.getStack();
				bestScores[7] = stack.getStackSize();
				bestItems[7] = slot;
			}
		}
		{ // Armor
			for (int armor = 0; armor < 4; armor++) {
				Slot slot = slots.get(5 + armor);
				if (slot.getHasStack() && slot.getStack().getItem() instanceof ItemArmor && ((ItemArmor)slot.getStack().getItem()).getArmorType() == armor) {
					bestScores[8 + armor] = getArmorScore(slot);
					bestItems[8 + armor] = slot;
				}
			}
		}
		
		for (Slot slot : slots) {
			if (!slot.getHasStack())
				continue;
			ItemStack stack = slot.getStack();
			
			// Weapon
			if (sword ? stack.getItem() instanceof ItemSword : stack.getItem() instanceof ItemAxe) {
				double score = getWeaponScore(stack, sword);
				if (score > bestScores[0]) {
					bestScores[0] = score;
					bestItems[0] = slot;
				}
			}
			
			// Bow
			if (stack.getItem() instanceof ItemBow && !MiscUtils.removeFormatting(stack.getDisplayName()).toLowerCase().contains("kit")) {
				double score = getBowScore(stack);
				if (score > bestScores[1]) {
					bestScores[1] = score;
					bestItems[1] = slot;
				}
			}
			
			// Pickaxe
			if (stack.getItem() instanceof ItemPickaxe) {
				double score = getToolScore(stack, (ItemTool)stack.getItem(), Blocks.stone);
				if (score > bestScores[2]) {
					bestScores[2] = score;
					bestItems[2] = slot;
				}
			}
			
			// Axe
			if (stack.getItem() instanceof ItemAxe) {
				double score = getToolScore(stack, (ItemTool)stack.getItem(), Blocks.planks);
				if (score > bestScores[3]) {
					bestScores[3] = score;
					bestItems[3] = slot;
				}
			}
			
			// Spade
			if (stack.getItem() instanceof ItemSpade) {
				double score = getToolScore(stack, (ItemTool)stack.getItem(), Blocks.dirt);
				if (score > bestScores[4]) {
					bestScores[4] = score;
					bestItems[4] = slot;
				}
			}
			
			// Shears
			if (stack.getItem() instanceof ItemShears) {
				double score = getShearsScore(stack, (ItemShears)stack.getItem());
				if (score > bestScores[5]) {
					bestScores[5] = score;
					bestItems[5] = slot;
				}
			}
			
			// Blocks
			if (stack.getItem() instanceof ItemBlock && !((ItemBlock)stack.getItem()).getBlock().doesBlockActivate()) {
				double score = stack.getStackSize();
				if (score > bestScores[6]) {
					bestScores[6] = score;
					bestItems[6] = slot;
				}
			}
			
			// Gapples
			if (stack.getItem() instanceof ItemAppleGold) {
				double score = stack.getStackSize();
				if (score > bestScores[7]) {
					bestScores[7] = score;
					bestItems[7] = slot;
				}
			}
			
			// Armor
			for (int armor = 0; armor < 4; armor++) {
				if (slot.getHasStack() && stack.getItem() instanceof ItemArmor && ((ItemArmor)stack.getItem()).getArmorType() == armor && getArmorScore(slot) > bestScores[8 + armor]) {
					bestScores[8 + armor] = getArmorScore(slot);
					bestItems[8 + armor] = slot;
				}
			}
			
		}
		
		return bestItems;
	}
	
	/**
	 * @param stack The stack, should contain a weapon
	 * @param sword Set to true if it should only use swords, if it's false it'll search for axes
	 * @return The weapons score
	 */
	public static double getWeaponScore(ItemStack stack, boolean sword) {
		return (sword ? ((ItemSword) stack.getItem()).getAttackDamage()
				: ((ItemTool) stack.getItem()).getDamageVsEntity())
				+ EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.getEffectId(), stack) * 1.25
				+ EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.getEffectId(), stack) * 0.75
				+ EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.getEffectId(), stack) * 0.65
				- (Reflector.ForgeItem_getDurabilityForDisplay.exists() ? Reflector.callDouble(stack.getItem(),
						Reflector.ForgeItem_getDurabilityForDisplay, new Object[] { stack }) < 25 ? 10 : 0 : 0);
	}
	
	/**
	 * @param stack The stack, should contain a bow
	 * @return The bows score
	 */
	public static double getBowScore(ItemStack stack) {
		return (EnchantmentHelper.getEnchantmentLevel(Enchantment.power.getEffectId(), stack) * 0.5D + 0.5D)
				+ EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.getEffectId(), stack) * 0.75
				- (Reflector.ForgeItem_getDurabilityForDisplay.exists() ? Reflector.callDouble(stack.getItem(),
						Reflector.ForgeItem_getDurabilityForDisplay, new Object[] { stack }) < 25 ? 10 : 0 : 0);
	}
	
	/**
	 * @param stack The stack, should contain a tool
	 * @param tool The tool
	 * @param testBlock The block to test the break speed on
	 * @return The tools score
	 */
	public static double getToolScore(ItemStack stack, ItemTool tool, Block testBlock) {
		return tool.getStrVsBlock(stack, testBlock) + scoreToolEnchantments(stack)
				- (Reflector.ForgeItem_getDurabilityForDisplay.exists() ? Reflector.callDouble(stack.getItem(),
						Reflector.ForgeItem_getDurabilityForDisplay, new Object[] { stack }) < 25 ? 10 : 0 : 0)
				- (tool.getToolMaterial() == ToolMaterial.GOLD ? 5 : 0);
	}
	
	/**
	 * @param stack The stack, should contain some shears
	 * @param shears The shears
	 * @return The shears score
	 */
	public static double getShearsScore(ItemStack stack, ItemShears shears) {
		return shears.getStrVsBlock(stack, Blocks.wool) + scoreToolEnchantments(stack)
				- (Reflector.ForgeItem_getDurabilityForDisplay.exists() ? Reflector.callDouble(stack.getItem(),
						Reflector.ForgeItem_getDurabilityForDisplay, new Object[] { stack }) < 25 ? 10 : 0 : 0);
	}
	
	/**
	 * Calculates the armor score
	 * @param slot The slot to check
	 * @return The armor score
	 */
	public static double getArmorScore(Slot slot) {
		double score = Double.MIN_VALUE;
		if (!slot.getHasStack() || !(slot.getStack().getItem() instanceof ItemArmor))
			return score;
		ItemStack stack = slot.getStack();
		ItemArmor itemArmor = (ItemArmor)stack.getItem();
		score += itemArmor.getArmorMaterial().getDamageReductionAmount(itemArmor.getArmorType())
				+ EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.getEffectId(), stack)
				+ EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.getEffectId(), stack) * 0.3
				+ EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.getEffectId(), stack) * 0.6;
		return score;
	}
	
	/**
	 * @param stack The stack to test
	 * @return The score that the enchantments add to the tool
	 */
	public static double scoreToolEnchantments(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.getEffectId(), stack)
				+ EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.getEffectId(), stack) * 0.3
				- EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.getEffectId(), stack) * 0.25;
	}
	
}
