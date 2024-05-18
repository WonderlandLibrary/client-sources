package me.valk.agway.modules.combat;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.valk.event.EventListener;
import me.valk.event.events.player.EventMotion;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.utils.ArmorUtils;
import net.minecraft.item.ItemStack;

public class AutoArmorMod extends me.valk.module.Module {
	private int[] chestplate;
	private int[] leggings;
	private int[] boots;
	private int[] helmet;
	int delay;
	public boolean bestarmor;

	public AutoArmorMod() {
		super(new ModData("AutoArmor", Keyboard.KEY_NONE, new Color(138, 8, 26)), ModType.COMBAT);
		chestplate = new int[] { 311, 307, 315, 303, 299 };
		leggings = new int[] { 312, 308, 316, 304, 300 };
		boots = new int[] { 313, 309, 317, 305, 301 };
		helmet = new int[] { 310, 306, 314, 302, 298 };		
		delay = 0;
		bestarmor = true;
	}

	public void AutoArmor() {
		if (this.bestarmor) {
			return;
		}
		int item = -1;
		++this.delay;
		if (this.delay >= 10) {
			if (this.mc.thePlayer.inventory.armorInventory[0] == null) {
				int[] boots;
				for (int length = (boots = this.boots).length, i = 0; i < length; ++i) {
					int id = boots[i];
					if (ArmorUtils.getItem(id) != -1) {
						item = ArmorUtils.getItem(id);
						break;
					}
				}
			}
			if (this.mc.thePlayer.inventory.armorInventory[1] == null) {
				int[] leggings;
				for (int length2 = (leggings = this.leggings).length, j = 0; j < length2; ++j) {
					int id = leggings[j];
					if (ArmorUtils.getItem(id) != -1) {
						item = ArmorUtils.getItem(id);
						break;
					}
				}
			}
			if (this.mc.thePlayer.inventory.armorInventory[2] == null) {
				int[] chestplate;
				for (int length3 = (chestplate = this.chestplate).length, k = 0; k < length3; ++k) {
					int id = chestplate[k];
					if (ArmorUtils.getItem(id) != -1) {
						item = ArmorUtils.getItem(id);
						break;
					}
				}
			}
			if (this.mc.thePlayer.inventory.armorInventory[3] == null) {
				int[] helmet;
				for (int length4 = (helmet = this.helmet).length, l = 0; l < length4; ++l) {
					int id = helmet[l];
					if (ArmorUtils.getItem(id) != -1) {
						item = ArmorUtils.getItem(id);
						break;
					}
				}
			}
			if (item != -1) {
				this.mc.playerController.windowClick(0, item, 0, 1, this.mc.thePlayer);
				this.delay = 0;
			}
		}
	}

	public void SwitchToBetterArmor() {
		if (!this.bestarmor) {
			return;
		}
		++this.delay;
		if (this.delay >= 10
				&& (this.mc.thePlayer.openContainer == null || this.mc.thePlayer.openContainer.windowId == 0)) {
			boolean switcharmor = false;
			int item = -1;
			if (this.mc.thePlayer.inventory.armorInventory[0] == null) {
				for (int id : this.boots) {
					if (ArmorUtils.getItem(id) != -1) {
						item = ArmorUtils.getItem(id);
						break;
					}
				}
			}
			if (ArmorUtils.IsBetterArmor(0, this.boots)) {
				item = 8;
				switcharmor = true;
			}
			if (this.mc.thePlayer.inventory.armorInventory[3] == null) {
				for (int id : this.helmet) {
					if (ArmorUtils.getItem(id) != -1) {
						item = ArmorUtils.getItem(id);
						break;
					}
				}
			}
			if (ArmorUtils.IsBetterArmor(3, this.helmet)) {
				item = 5;
				switcharmor = true;
			}
			if (this.mc.thePlayer.inventory.armorInventory[1] == null) {
				for (int id : this.leggings) {
					if (ArmorUtils.getItem(id) != -1) {
						item = ArmorUtils.getItem(id);
						break;
					}
				}
			}
			if (ArmorUtils.IsBetterArmor(1, this.leggings)) {
				item = 7;
				switcharmor = true;
			}
			if (this.mc.thePlayer.inventory.armorInventory[2] == null) {
				for (int id : this.chestplate) {
					if (ArmorUtils.getItem(id) != -1) {
						item = ArmorUtils.getItem(id);
						break;
					}
				}
			}
			if (ArmorUtils.IsBetterArmor(2, this.chestplate)) {
				item = 6;
				switcharmor = true;
			}
			boolean var7 = false;
			for (ItemStack stack : this.mc.thePlayer.inventory.mainInventory) {
				if (stack == null) {
					var7 = true;
					break;
				}
			}
			switcharmor = (switcharmor && !var7);
			if (item != -1) {
				this.mc.playerController.windowClick(0, item, 0, switcharmor ? 4 : 1, this.mc.thePlayer);
				this.delay = 0;
			}
		}
	}

	@EventListener
	public void onPre(EventMotion e) {
		this.AutoArmor();
		this.SwitchToBetterArmor();
	}

}