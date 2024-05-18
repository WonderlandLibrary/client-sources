package me.swezedcode.client.module.modules.Fight;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;
import com.darkmagician6.eventapi.types.EventType;
import com.darkmagician6.eventapi.types.Priority;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.events.EventMove;
import me.swezedcode.client.utils.timer.TimerUtils;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class AutoHeal extends Module {

	public AutoHeal() {
		super("AutoHeal", Keyboard.KEY_NONE, 0xFFFC5DAD, ModCategory.Fight);
		setDisplayName("Auto Heal");
	}

	private BooleanValue autosoup = new BooleanValue(this, "Soup", "sop", Boolean.valueOf(true));
	private BooleanValue autostop = new BooleanValue(this, "Auto Stop", "jPot", Boolean.valueOf(true));
	private BooleanValue jumppot = new BooleanValue(this, "JumpPot", "jPot", Boolean.valueOf(true));
	private BooleanValue brackets = new BooleanValue(this, "Brackets", "brackets", Boolean.valueOf(true));

	public static int ticks = 0;
	private ItemThing potSlot;

	@EventListener
	public void onRawMove(EventMove e) {
		if (jumppot.getValue() && autostop.getValue() && ticks > 0 && mc.thePlayer.isEntityAlive() && potSlot != null
				&& !potSlot.isSoup()) {
			e.x = e.z = 0;
			mc.thePlayer.setSpeed(0);
		} else {
			return;
		}
	}

	@EventListener(Priority.HIGHEST)
	public void onMotion(EventMotion event) {
		if (brackets.getValue()) {
			setDisplayName(getName() + "§7 [" + getCount() + "§7]");
		} else {
			setDisplayName(getName() + "§7 " + getCount());
		}
		if (ticks > 0) {
			ticks--;
			return;
		}
		for (int i = 0; i < 1; i++) {
			if (event.getType() == EventType.PRE) {
				potSlot = null;
				ItemThing temp = getHealingItemFromInventory();
				event.breakEvent();
				if (ticks == 0 && mc.thePlayer.getHealth() <= 10 && temp.getSlot() != -1) {
					event.breakEvent();
					if (jumppot.getValue() && mc.thePlayer.onGround && !temp.isSoup()) {
						mc.thePlayer.jump();
						event.getLocation().setPitch(-90);
					} else {
						event.getLocation().setPitch(90);
					}
					potSlot = temp;
				}
			} else {
				if (potSlot != null) {
					if (potSlot.getSlot() < 9) {
						mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(potSlot.getSlot()));
						mc.thePlayer.sendQueue.addToSendQueue(
								new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
						mc.thePlayer.sendQueue
								.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
					} else {
						swap(potSlot.getSlot(), 5);
						mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(5));
						mc.thePlayer.sendQueue.addToSendQueue(
								new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
						if (potSlot.isSoup() && this.autosoup.getValue())
							mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
									C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
						mc.thePlayer.sendQueue
								.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
					}
					ticks = jumppot.getValue() ? 40 : 10;
					event.breakEvent();
				}
			}
		}
	}

	public int getCount() {
		int pot = -1;
		int counter = 0;
		for (int i = 0; i < 36; ++i) {
			if (mc.thePlayer.inventory.mainInventory[i] != null) {
				ItemStack is = mc.thePlayer.inventory.mainInventory[i];
				Item item = is.getItem();
				if (item instanceof ItemPotion) {
					ItemPotion potion = (ItemPotion) item;
					if (potion.getEffects(is) != null) {
						for (Object o : potion.getEffects(is)) {
							PotionEffect effect = (PotionEffect) o;
							if (effect.getPotionID() == Potion.heal.id && ItemPotion.isSplash(is.getItemDamage())) {
								++counter;
							}
						}
					}
				}

				if (item instanceof ItemSoup) {
					counter++;
				}
			}
		}

		return counter;
	}

	private ItemThing getHealingItemFromInventory() {
		int itemSlot = -1;
		int counter = 0;
		boolean soup = false;
		for (int i = 0; i < 36; ++i) {
			if (mc.thePlayer.inventory.mainInventory[i] != null) {
				ItemStack is = mc.thePlayer.inventory.mainInventory[i];
				Item item = is.getItem();
				if (item instanceof ItemPotion) {
					ItemPotion potion = (ItemPotion) item;
					if (potion.getEffects(is) != null) {
						for (Object o : potion.getEffects(is)) {
							PotionEffect effect = (PotionEffect) o;
							if (effect.getPotionID() == Potion.heal.id && ItemPotion.isSplash(is.getItemDamage())) {
								++counter;
								itemSlot = i;
								soup = false;
							}
						}
					}
				}

				if (item instanceof ItemSoup) {
					counter++;
					itemSlot = i;
					soup = true;
				}
			}
		}

		return new ItemThing(itemSlot, soup);
	}

	private void swap(int slot, int hotbarSlot) {
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarSlot, 2, mc.thePlayer);
	}

	public class ItemThing {

		private boolean soup;
		private int slot;

		public ItemThing(int slot, boolean soup) {
			this.slot = slot;
			this.soup = soup;
		}

		public int getSlot() {
			return slot;
		}

		public boolean isSoup() {
			return soup;
		}

	}

}
