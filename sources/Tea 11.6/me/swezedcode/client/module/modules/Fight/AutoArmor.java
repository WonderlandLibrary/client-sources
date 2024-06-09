package me.swezedcode.client.module.modules.Fight;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.timer.TimerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Module {

	public AutoArmor() {
		super("AutoArmor", Keyboard.KEY_NONE, 0xFFF1F57A, ModCategory.Fight);
	}

	private TimerUtils time = new TimerUtils();
	private final int[] boots = { 313, 309, 317, 305, 301 };
	private final int[] chestplate = { 311, 307, 315, 303, 299 };
	private final int[] helmet = { 310, 306, 314, 302, 298 };
	private final int[] leggings = { 312, 308, 316, 304, 300 };

	public void onDisable() {

	}

	public void onEnable() {

	}

	@EventListener
	public void onTick(EventPreMotionUpdates event) {
		if (TimerUtils.hD(6)) {
			if (Minecraft.thePlayer.openContainer != null) {
				if (Minecraft.thePlayer.openContainer.windowId != 0) {
					return;
				}
			}
			int item = -1;
			if (Minecraft.thePlayer.inventory.armorInventory[0] == null) {
				int[] arrayOfInt;
				int j = (arrayOfInt = this.boots).length;
				for (int i = 0; i < j; i++) {
					int id = arrayOfInt[i];
					if (findItem(id) != -1) {
						item = findItem(id);
						break;
					}
				}
			}
			if (armourIsBetter(0, this.boots)) {
				item = 8;
			}
			if (Minecraft.thePlayer.inventory.armorInventory[3] == null) {
				int[] arrayOfInt;
				int j = (arrayOfInt = this.helmet).length;
				for (int i = 0; i < j; i++) {
					int id = arrayOfInt[i];
					if (findItem(id) != -1) {
						item = findItem(id);
						break;
					}
				}
			}
			if (armourIsBetter(3, this.helmet)) {
				item = 5;
			}
			if (Minecraft.thePlayer.inventory.armorInventory[1] == null) {
				int[] arrayOfInt;
				int j = (arrayOfInt = this.leggings).length;
				for (int i = 0; i < j; i++) {
					int id = arrayOfInt[i];
					if (findItem(id) != -1) {
						item = findItem(id);
						break;
					}
				}
			}
			if (armourIsBetter(1, this.leggings)) {
				item = 7;
			}
			if (Minecraft.thePlayer.inventory.armorInventory[2] == null) {
				int[] arrayOfInt;
				int j = (arrayOfInt = this.chestplate).length;
				for (int i = 0; i < j; i++) {
					int id = arrayOfInt[i];
					if (findItem(id) != -1) {
						item = findItem(id);
						break;
					}
				}
			}
			if (armourIsBetter(2, this.chestplate)) {
				item = 6;
			}
			if (item != -1) {
				Minecraft.playerController.windowClick(0, item, 0, 1, Minecraft.thePlayer);
				this.time.rt();
				return;
			}
		}
	}

	public boolean armourIsBetter(int slot, int[] armourtype) {
		if (Minecraft.thePlayer.inventory.armorInventory[slot] != null) {
			int currentIndex = 0;
			int finalCurrentIndex = -1;
			int invIndex = 0;
			int finalInvIndex = -1;
			int[] arrayOfInt;
			int j = (arrayOfInt = armourtype).length;
			for (int i = 0; i < j; i++) {
				int armour = arrayOfInt[i];
				if (Item.getIdFromItem(Minecraft.thePlayer.inventory.armorInventory[slot].getItem()) == armour) {
					finalCurrentIndex = currentIndex;
					break;
				}
				currentIndex++;
			}
			j = (arrayOfInt = armourtype).length;
			for (int i = 0; i < j; i++) {
				int armour = arrayOfInt[i];
				if (findItem(armour) != -1) {
					finalInvIndex = invIndex;
					break;
				}
				invIndex++;
			}
			if (finalInvIndex > -1) {
				return finalInvIndex < finalCurrentIndex;
			}
		}
		return false;
	}

	private int findItem(int id) {
		for (int index = 9; index < 45; index++) {
			ItemStack item = Minecraft.thePlayer.inventoryContainer.getSlot(index).getStack();
			if ((item != null) && (Item.getIdFromItem(item.getItem()) == id)) {
				return index;
			}
		}
		return -1;
	}
}