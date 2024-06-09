// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.combat;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.TickEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

@Mod(displayName = "Auto Armor")
public class AutoArmor extends Module {
	
	public AutoArmor(){
		this.setProperties("AutoArmor", "AutoArmor", Module.Category.Combat, 0, "", true);
	}
	
	@EventTarget
	private void onTick(final TickEvent event) {
		if (ClientUtils.player() != null
				&& (ClientUtils.mc().currentScreen == null || ClientUtils.mc().currentScreen instanceof GuiInventory
						|| !ClientUtils.mc().currentScreen.getClass().getName().contains("inventory"))) {
			int slotID = -1;
			double maxProt = -1.0;
			for (int i = 9; i < 45; ++i) {
				final ItemStack stack = ClientUtils.player().inventoryContainer.getSlot(i).getStack();
				if (stack != null) {
					if (this.canEquip(stack)) {
						final double protValue = this.getProtectionValue(stack);
						if (protValue >= maxProt) {
							slotID = i;
							maxProt = protValue;
						}
					}
				}
			}
			if (slotID != -1) {
				ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, slotID, 0,
						1, ClientUtils.player());
			}
		}
	}

	private boolean canEquip(final ItemStack stack) {
		return ((ClientUtils.player().getEquipmentInSlot(1) == null) && stack.getUnlocalizedName().contains("boots"))
				|| (ClientUtils.player().getEquipmentInSlot(2) == null
						&& stack.getUnlocalizedName().contains("leggings"))
				|| (ClientUtils.player().getEquipmentInSlot(3) == null
						&& stack.getUnlocalizedName().contains("chestplate"))
				|| (ClientUtils.player().getEquipmentInSlot(4) == null
						&& stack.getUnlocalizedName().contains("helmet"));
	}

	private double getProtectionValue(final ItemStack stack) {
		return ((ItemArmor) stack.getItem()).damageReduceAmount
				+ (100 - ((ItemArmor) stack.getItem()).damageReduceAmount * 4)
						* EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 4 * 0.0075;
	}
}
