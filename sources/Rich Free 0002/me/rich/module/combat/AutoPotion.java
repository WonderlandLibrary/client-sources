package me.rich.module.combat;

import java.util.Objects;

import org.lwjgl.input.Keyboard;

import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;

public class AutoPotion extends Feature {
	ItemStack held;

	public AutoPotion() {
		super("AutoPotion", Keyboard.KEY_NONE, Category.COMBAT);

	}

	@EventTarget
	public void onUpdate(final EventPreMotionUpdate event) {

		if (held == null && timerHelper.check(100))
			held = mc.player.getHeldItemMainhand();

		if (isPotionOnHotBar() && mc.player.onGround) {
			if (!mc.player.isPotionActive((Objects.requireNonNull(Potion.getPotionById(1))))) {
				event.setPitch(90);
				if (event.getPitch() == 90)
					throwPot(Potions.SKORKA);
				timerHelper.resetwatermark();
			}
			if (!mc.player.isPotionActive((Objects.requireNonNull(Potion.getPotionById(5))))) {
				event.setPitch(90);
				if (event.getPitch() == 90)
					throwPot(Potions.SILKA);
				timerHelper.resetwatermark();
			}
			if (!mc.player.isPotionActive((Objects.requireNonNull(Potion.getPotionById(12))))) {
				event.setPitch(90);
				if (event.getPitch() == 90)
					throwPot(Potions.RESIST);
				timerHelper.resetwatermark();
			}
		}
	}

	void throwPot(Potions potion) {
		int slot = getPotionSlot(potion);
		mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
		mc.playerController.updateController();
		mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
		mc.playerController.updateController();
		mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
	}

	int getPotionSlot(Potions potion) {
		for (int i = 0; i < 9; ++i) {
			if (this.isStackPotion(mc.player.inventory.getStackInSlot(i), potion))
				return i;
		}
		return -1;
	}

	boolean isPotionOnHotBar() {
		for (int i = 0; i < 9; ++i) {
			if (isStackPotion(mc.player.inventory.getStackInSlot(i), Potions.SILKA)
					|| isStackPotion(mc.player.inventory.getStackInSlot(i), Potions.SKORKA)
					|| isStackPotion(mc.player.inventory.getStackInSlot(i), Potions.RESIST)) {
				return true;
			}

		}
		return false;
	}

	boolean isStackPotion(ItemStack stack, Potions potion) {
		if (stack == null)
			return false;

		Item item = stack.getItem();

		if (item == Items.SPLASH_POTION) {
			int id = 5;

			switch (potion) {
			case SILKA:
				id = 5;
				break;

			case SKORKA:
				id = 1;
				break;

			case RESIST:
				id = 12;
				break;

			}

			for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack))
				if (effect.getPotion() == Potion.getPotionById(id))
					return true;

		}

		return false;
	}

	enum Potions {
		SILKA, SKORKA, RESIST
	}
}