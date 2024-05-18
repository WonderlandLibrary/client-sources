package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class AutoArmour extends Module{

	public AutoArmour() {
		super("Auto Armor", "autoarmor", Keyboard.KEY_NONE, Category.COMBAT, new String[]{"help"});
	}
	private final ClampedValue<Float> delay = new ClampedValue<>("autoarmor_delay", 65F, 0F, 500F);
	private int[] bestArmor;
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	//Skeet Skeet nigga
	private final int[] boots = { 313, 309, 317, 305, 301 };
	private final int[] chestplate = { 311, 307, 315, 303, 299 };
	private final int[] helmet = { 310, 306, 314, 302, 298 };
	private final int[] leggings = { 312, 308, 316, 304, 300 };
	@EventTarget
	public void onEvent(EventPreMotionUpdates event)
	{
		this.setDisplayName("Auto Armor [" + (this.delay.getValue().longValue()) + "]");
		updateMS();
		if (!hasTimePassedM(this.delay.getValue().longValue())) {
			return;
		}
		if ((this.mc.thePlayer.openContainer != null) && (this.mc.thePlayer.openContainer.windowId != 0)) {
			return;
		}
		int item = -1;
		int[] arrayOfInt;
		int j;
		int i;
		if (this.mc.thePlayer.inventory.armorInventory[0] == null) {
			j = (arrayOfInt = this.boots).length;
			for (i = 0; i < j; i++) {
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
		if (this.mc.thePlayer.inventory.armorInventory[3] == null) {
			j = (arrayOfInt = this.helmet).length;
			for (i = 0; i < j; i++) {
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
		if (this.mc.thePlayer.inventory.armorInventory[1] == null) {
			j = (arrayOfInt = this.leggings).length;
			for (i = 0; i < j; i++) {
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
		if (this.mc.thePlayer.inventory.armorInventory[2] == null) {
			j = (arrayOfInt = this.chestplate).length;
			for (i = 0; i < j; i++) {
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
			this.mc.playerController.windowClick(0, item, 0, 1, this.mc.thePlayer);
			updateLastMS();
			return;
		}
	}

	public boolean armourIsBetter(int slot, int[] armourtype) {
		if (this.mc.thePlayer.inventory.armorInventory[slot] != null) {
			int currentIndex = 0;
			int finalCurrentIndex = -1;
			int invIndex = 0;
			int finalInvIndex = -1;
			int[] arrayOfInt;
			int j = (arrayOfInt = armourtype).length;
			for (int i = 0; i < j; i++) {
				int armour = arrayOfInt[i];
				if (Item.getIdFromItem(this.mc.thePlayer.inventory.armorInventory[slot].getItem()) == armour) {
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
			ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
			if ((item != null) && (Item.getIdFromItem(item.getItem()) == id)) {
				return index;
			}
		}
		return -1;
	}
	public void runCmd(String command) {
		String help = "\2477List of commands: ";
		for(Module mod : Protocol.getModules()){
			for(String s : mod.getCmds()){
				if(!s.equals("dsdfsdfsdfsdghgh") && !s.equals("bbfgdfgfdg") &&  !s.equals("")){
				help = help + Protocol.primColor + s + "\2477, ";
				}
			}
		}
		Wrapper.tellPlayer(help);
	}
}