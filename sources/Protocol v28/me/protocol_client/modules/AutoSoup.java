package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class AutoSoup extends Module {
	private int			delay;
	public static int	soups;

	public AutoSoup() {
		super("Auto Soup", "autosoup", 0, Category.COMBAT, new String[] { "autosoup", "soup" });
	}

	public final ClampedValue<Float>	health	= new ClampedValue<>("autosoup_health", 14f, 5f, 19f);
	public final static Value<Boolean>	yaymc	= new Value<>("autosoup_yaymc", false);

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		setName("Auto Soup");
		EventManager.unregister(this);
	}

	@EventTarget
	public void onPre(EventPreMotionUpdates pre) {
		setDisplayName("Auto Soup [" + this.getTotalSoups() + "]");
		getTotalSoups();
		if (shouldHeal()) {
			this.delay += 1;
		}
		if ((isSoupOnHotbar()) && (shouldHeal()) && (this.delay >= 5)) {
			drinkSoup();
			this.delay = 0;
		} else if ((!isSoupOnHotbar()) && (shouldHeal()) && (getTotalSoups() > 0)) {
			getSoup();
		}
	}
	private boolean isStackSoup(ItemStack stack) {
		if (stack == null) {
			return false;
		}
		if ((stack.getItem() instanceof ItemSoup)) {
			return true;
		}
		return false;
	}

	private boolean shouldHeal() {
		if (this.mc.thePlayer.getHealth() <= this.health.getValue()) {
			return true;
		}
		return false;
	}

	private boolean isSoupOnHotbar() {
		for (int index = 36; index < 45; index++) {
			ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
			if ((stack != null) && (isStackSoup(stack))) {
				return true;
			}
		}
		return false;
	}

	private void getSoup() {
		if (!(this.mc.currentScreen instanceof GuiChest)) {
			for (int index = 9; index < 36; index++) {
				ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
				if (!isStackSoup(stack)) {
					dropLastSlot();
				}
				if ((stack != null) && (isStackSoup(stack))) {
					this.mc.playerController.windowClick(0, index, 0, 1, this.mc.thePlayer);
					break;
				}
			}
		}
	}

	private void dropLastSlot() {
		for (int i1 = 0; i1 < 45; i1++) {
			ItemStack itemStack = this.mc.thePlayer.inventoryContainer.getSlot(i1).getStack();
			if ((itemStack != null) && (i1 > 43) && (i1 <= 44)) {
				this.mc.playerController.windowClick(0, i1, 0, 4, this.mc.thePlayer);
				this.mc.playerController.windowClick(0, 64537, 0, 4, this.mc.thePlayer);
				break;
			}
		}
	}

	private void drinkSoup() {
		for (int slot = 36; slot < 45; slot++) {
			ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
			if ((stack != null) && (isStackSoup(stack))) {
				int lastSlot = this.mc.thePlayer.inventory.currentItem;
				this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(slot - 36));
				this.mc.playerController.updateController();
				if (yaymc.getValue()) {
					Wrapper.mc().rightClickMouse();
				} else {
					this.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement());
				}
				this.mc.playerController.windowClick(0, slot, 0, 1, this.mc.thePlayer);
				this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(lastSlot));

				break;
			}
		}
	}

	public int getTotalSoups() {
		soups = 0;
		for (int index = 9; index < 45; index++) {
			ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
			if (isStackSoup(stack)) {
				soups += stack.stackSize;
			}
		}
		return soups;
	}

	public void runCmd(String args) {
		if (args.startsWith("health")) {
			String health = args.split(" ")[1];
			float healthn = Float.parseFloat(health);
			this.health.setValue(healthn);
			Wrapper.tellPlayer(Protocol.primColor + "Auto Soup" + "\2477" + " has had " + Protocol.primColor + "health" + "\2477" + " set to " + Protocol.primColor + this.health.getValue() + "\2477" + ".");
			return;
		}
		Wrapper.tellPlayer("\2477" + "Invalid arguments for" + Protocol.primColor + " Auto Soup\2477.");
		Wrapper.tellPlayer("\2477-" + Protocol.primColor + "autosoup\2477 health <health>");
	}
}
