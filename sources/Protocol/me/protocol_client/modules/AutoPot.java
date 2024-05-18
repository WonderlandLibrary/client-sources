package me.protocol_client.modules;

import java.util.List;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import net.minecraft.block.BlockSlime;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketSent;
import events.EventPreMotionUpdates;

public class AutoPot extends Module {

	public boolean	isPotting	= false;
	public float	pitch;

	public AutoPot() {
		super("Auto Potion", "autopotion", 0, Category.COMBAT, new String[] { "" });
	}
	public final ClampedValue<Float>	health	= new ClampedValue<>("autopotion_health", 10f, 5f, 19f);
	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		setName("Auto Potion");
		EventManager.unregister(this);
	}

	private int			delay;
	public static int	potions;

	@EventTarget
	public void onPre(EventPreMotionUpdates pre) {
		setDisplayName("Auto Potion [" + this.getTotalPots() + "]");
		getTotalPots();
		if (shouldHeal()) {
			this.delay += 1;
		}
		if ((isSoupOnHotbar()) && (shouldHeal()) && (this.delay >= 5)) {
			throwPotion();
			this.delay = 0;
		} else if ((!isSoupOnHotbar()) && (shouldHeal()) && (getTotalPots() > 0)) {
			getSoup();
		}
	}

	private boolean isStackSoup(ItemStack stack, int index) {
		if (stack == null) {
			return false;
		}
		ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
		Item item = is.getItem();
		if ((stack.getItem() instanceof ItemPotion)) {
			ItemPotion potion = (ItemPotion) item;
			if (potion.getEffects(is) != null) {
				for (Object o : potion.getEffects(is)) {
					PotionEffect effect = (PotionEffect) o;
					if ((effect.getPotionID() == Potion.heal.id) && (ItemPotion.isSplash(is.getItemDamage()))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean shouldHeal() {
		if (Wrapper.getPlayer().capabilities.isCreativeMode) {
			return false;
		}
		if (this.mc.thePlayer.getHealth() <= this.health.getValue()) {
			return true;
		}
		return false;
	}

	private boolean isSoupOnHotbar() {
		for (int index = 36; index < 45; index++) {
			ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
			if ((stack != null) && (isStackSoup(stack, index))) {
				return true;
			}
		}
		return false;
	}

	private void getSoup() {
		if (!(this.mc.currentScreen instanceof GuiChest)) {
			for (int index = 9; index < 36; index++) {
				ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
				if (!isStackSoup(stack, index)) {
					dropLastSlot();
				}
				if ((stack != null) && (isStackSoup(stack, index))) {
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
				this.mc.playerController.windowClick(0, i1, 0, 0, this.mc.thePlayer);
				this.mc.playerController.windowClick(0, 64537, 0, 0, this.mc.thePlayer);
				break;
			}
		}
	}

	private void throwPotion() {
		updateMS();
		Wrapper.getPlayer().rotationPitch += 9.0E-4f;
		for (int slot = 36; slot < 45; slot++) {
			ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
			if ((stack != null) && (isStackSoup(stack, slot)) && !isPotting) {
				isPotting = true;
				int lastSlot = this.mc.thePlayer.inventory.currentItem;
				Wrapper.getPlayer().rotationPitch += 9.0E-4f;
				this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(slot - 36));
				this.mc.playerController.updateController();
				this.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(stack));
				this.mc.playerController.windowClick(0, slot, 0, 1, this.mc.thePlayer);
				this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(lastSlot));
				break;
			}
		}
		if (hasTimePassedM(200)) {
			isPotting = false;
			updateLastMS();
		}
	}

	public int getTotalPots() {
		potions = 0;
		for (int index = 9; index < 45; index++) {
			ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
			if (isStackSoup(stack, index)) {
				potions += stack.stackSize;
			}
		}
		return potions;
	}

	@EventTarget
	public void onPacketSent(EventPacketSent event) {
		Wrapper.getPlayer().rotationPitch += 9.0E-4f;
		if (this.shouldHeal() && this.getTotalPots() > 0) {
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.pitch = 90f;
			}
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.pitch = 90f;
			}
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.pitch = 90f;
			}
		}
	}
}
