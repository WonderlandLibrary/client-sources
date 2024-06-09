package me.swezedcode.client.module.modules.Fight;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.math.MathUtils;
import me.swezedcode.client.utils.timer.Timer;
import me.swezedcode.client.utils.values.BooleanValue;
import me.swezedcode.client.utils.values.NumberValue;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class AutoClicker extends Module {

	public AutoClicker() {
		super("AutoClicker", Keyboard.KEY_NONE, 0xFFE85858, ModCategory.Fight);
	}

	private final BooleanValue sword_required = new BooleanValue(this, "Sword required", "sword_required",
			Boolean.valueOf(false));
	private int random;
	public static int AveregeCPS = 9;

	private final Timer timer = new Timer();
	private final Random rand = new Random();

	@EventListener
	public void onUpdate(EventPreMotionUpdates event) {
		if (mc.theWorld == null) {
			return;
		}
		if (mc.thePlayer == null) {
			return;
		}
		if (Mouse.isButtonDown(0)) {
			this.random = MathUtils.getRandomInRange(1, 50);
			if (timer.hasReached((1000 / (AveregeCPS + 10) + this.random))) {
				if (!sword_required.getValue()) {
					mc.thePlayer.swingItem();
					if (mc.objectMouseOver.entityHit != null) {
						mc.thePlayer.sendQueue.addToSendQueue(
								new C02PacketUseEntity(mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
					}
					mc.playerController.isHittingBlock = false;
					timer.reset();
				}else{
					if(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)
					mc.thePlayer.swingItem();
					if (mc.objectMouseOver.entityHit != null) {
						mc.thePlayer.sendQueue.addToSendQueue(
								new C02PacketUseEntity(mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
					}
					mc.playerController.isHittingBlock = false;
					timer.reset();
				}
			}

		}
	}
}
