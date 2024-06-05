package zamorozka.modules.COMBAT;

import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.InventoryUtil;
import zamorozka.ui.MoveUtil;

public class AutoPot extends Module {

	private int slot;
	private int item;
	private int oldSlot;

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Packet");
		options.add("Client");
		Zamorozka.settingsManager.rSetting(new Setting("AutoPot Mode", this, "Packet", options));
	}

	public AutoPot() {
		super("AutoPot", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@Override
	public void onEnable() {
		this.slot = mc.player.inventory.currentItem;
		this.oldSlot = mc.player.inventory.currentItem;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		mc.player.inventory.currentItem = this.slot;
		super.onDisable();
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		String mode = Zamorozka.settingsManager.getSettingByName("AutoPot Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("AutoPot §f§" + " " + modeput);
		if (!doesNextSlotHavePot()) {
			return;
		}
		this.item = InventoryUtil.getSlotWithPot();
		if (this.slot != -1) {
			if (!(mc.player.getHeldItemOffhand().getItem() == Items.field_190929_cY)) {
				if (!(mc.player.isPotionActive(MobEffects.SPEED))) {
					if (mode.equalsIgnoreCase("Packet")) {
						sendPotPacket(event);
					} else if (mode.equalsIgnoreCase("Client")) {
						mc.player.rotationPitch = 90.0F;
					}
					sendPacket(new CPacketHeldItemChange(this.slot = this.item));
					sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
					sendPacket(new CPacketHeldItemChange(this.oldSlot));
				}
				if (mc.player.isPotionActive(MobEffects.SPEED)) {
					if (!(mc.player.isPotionActive(MobEffects.STRENGTH))) {
						if (mode.equalsIgnoreCase("Packet")) {
							sendPotPacket(event);
						} else if (mode.equalsIgnoreCase("Client")) {
							mc.player.rotationPitch = 90.0F;
						}
						sendPacket(new CPacketHeldItemChange(this.slot = this.item + 1));
						sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
						sendPacket(new CPacketHeldItemChange(this.oldSlot));
					}
					if (mc.player.isPotionActive(MobEffects.STRENGTH)) {
						if (!(mc.player.isPotionActive(MobEffects.FIRE_RESISTANCE))) {
							if (mode.equalsIgnoreCase("Packet")) {
								sendPotPacket(event);
							} else if (mode.equalsIgnoreCase("Client")) {
								mc.player.rotationPitch = 90.0F;
							}
							sendPacket(new CPacketHeldItemChange(this.slot = this.item + 2));
							sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
							sendPacket(new CPacketHeldItemChange(this.oldSlot));

						}
					}
				}
			} else {
				if (mc.player.getHeldItemOffhand().getItem() == Items.field_190929_cY) {
					if (!(mc.player.isPotionActive(MobEffects.STRENGTH))) {
						if (mode.equalsIgnoreCase("Packet")) {
							sendPotPacket(event);
						} else if (mode.equalsIgnoreCase("Client")) {
							mc.player.rotationPitch = 90.0F;
						}
						sendPacket(new CPacketHeldItemChange(this.slot = this.item));
						sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
						sendPacket(new CPacketHeldItemChange(this.oldSlot));
					}
					if (mc.player.isPotionActive(MobEffects.STRENGTH)) {
						if (!(mc.player.isPotionActive(MobEffects.SPEED))) {
							if (mode.equalsIgnoreCase("Packet")) {
								sendPotPacket(event);
							} else if (mode.equalsIgnoreCase("Client")) {
								mc.player.rotationPitch = 90.0F;
							}
							sendPacket(new CPacketHeldItemChange(this.slot = this.item + 1));
							sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
							sendPacket(new CPacketHeldItemChange(this.oldSlot));
						}
						if (mc.player.isPotionActive(MobEffects.SPEED)) {
							if (!(mc.player.isPotionActive(MobEffects.FIRE_RESISTANCE))) {
								if (mode.equalsIgnoreCase("Packet")) {
									sendPotPacket(event);
								} else if (mode.equalsIgnoreCase("Client")) {
									mc.player.rotationPitch = 90.0F;
								}
								sendPacket(new CPacketHeldItemChange(this.slot = this.item + 2));
								sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
								sendPacket(new CPacketHeldItemChange(this.oldSlot));

							}
						}
					}
				}
			}
		}
	}

	private void sendPotPacket(EventPreMotionUpdates event) {
		double xDist = mc.player.posX - mc.player.lastTickPosX;
		double zDist = mc.player.posZ - mc.player.lastTickPosZ;

		double speed = Math.sqrt(xDist * xDist + zDist * zDist);

		boolean shouldPredict = speed > 0.38D;
		boolean shouldJump = speed < MoveUtil.WALK_SPEED;
		boolean onGround = MoveUtil.isOnGround();

		if (shouldJump && onGround && !MoveUtil.isBlockAbove() && MoveUtil.getJumpBoostModifier() == 0) {
			mc.player.motionX *= 0;
			mc.player.motionZ *= 0;
			event.setPitch(90.0F);
			mc.player.rotationPitchHead = 90;
		} else if (shouldPredict || onGround) {
			event.setYaw(MoveUtil.getMovementDirection());
			event.setPitch(shouldPredict ? 0.0F : 45.0F);
		} else
			return;
	}

	private boolean doesNextSlotHavePot() {
		for (int i = 0; i < 9; ++i) {
			if (mc.player.inventory.getStackInSlot(i) != null && mc.player.inventory.getStackInSlot(i).getItem() == Items.SPLASH_POTION) {
				return true;
			}
		}
		return false;
	}
}