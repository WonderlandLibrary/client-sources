package me.swezedcode.client.module.modules.Motion;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.utils.block.BlockHelper;
import me.swezedcode.client.utils.events.EventEntityCollision;
import me.swezedcode.client.utils.events.EventPostMotionUpdates;
import me.swezedcode.client.utils.events.EventReadPacket;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class Jesus extends Module {

	public Jesus() {
		super("LiquidWalk", Keyboard.KEY_NONE, 0xFF24ADE3, ModCategory.Motion);
		setDisplayName("Liquid Walk §7Solid");
	}

	private BooleanValue dolphin = new BooleanValue(this, "Dolphin", "dolphin", Boolean.valueOf(false));

	@EventListener
	public void onPre(EventPostMotionUpdates e) {
		if (!dolphin.getValue()) {
			if (!MemeNames.enabled) {
				setDisplayName("Liquid Walk §7Solid");
			} else {
				setDisplayName("BeliveInJesusOrDrown §7Solid");
			}
			if (BlockHelper.isInLiquid() && !this.mc.thePlayer.isSneaking()
					&& !Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode())) {
				this.mc.thePlayer.motionY = 0.019999999552965164;
				mc.thePlayer.onGround = true;
			}
		} else {
			if (!MemeNames.enabled) {
				setDisplayName("Liquid Walk §7Bounce");
			} else {
				setDisplayName("BeliveInJesusOrNoDankMemeJumps §7Bounce");
			}
			if (BlockHelper.isInLiquid() && !mc.thePlayer.onGround && !this.mc.thePlayer.isSneaking()
					&& !Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode())) {
				this.mc.thePlayer.motionY = 0.09999999552965164;
			}
			if (!mc.thePlayer.capabilities.isFlying && !mc.thePlayer.isInWater() && !mc.thePlayer.onGround) {
				if (mc.thePlayer.motionY < -0.3 || mc.thePlayer.onGround || mc.thePlayer.isOnLadder()) {
					return;
				}
				mc.thePlayer.motionY = mc.thePlayer.motionY / 0.9800000190734863 + 0.08;
				EntityPlayerSP entityPlayerSP = mc.thePlayer;
				entityPlayerSP.motionY -= 0.03120000000005;
				if (BlockHelper.isInLiquid() && !mc.thePlayer.onGround && mc.thePlayer.motionY < 0.2) {
					mc.thePlayer.motionY = 0.5;
				}
			}
		}
	}

	@EventListener
	public void onPacket(EventReadPacket e) {
		if (!dolphin.getValue()) {
			if (e.getPacket() instanceof C03PacketPlayer && this.isGoodToJesusMyDudes() && BlockHelper.isOnLiquid()) {
				final C03PacketPlayer packet = (C03PacketPlayer) e.getPacket();
				packet.y = ((this.mc.thePlayer.ticksExisted % 2 == 0) ? (packet.y + 0.01) : (packet.y - 0.01));
			}
		} else {
			if (e.getPacket() instanceof C03PacketPlayer && this.isGoodToJesusMyDudes() && BlockHelper.isOnLiquid()) {
				final C03PacketPlayer packet = (C03PacketPlayer) e.getPacket();
				packet.y = ((this.mc.thePlayer.ticksExisted % 2 == 0) ? (packet.y + 0.01) : (packet.y - 0.01));
			}
		}
	}

	@EventListener
	public void onBB(final EventEntityCollision e) {
		if (!dolphin.getValue()) {
			if (e.getBlock() instanceof BlockLiquid && e.getEntity() == this.mc.thePlayer
					&& this.isGoodToJesusMyDudes()) {
				e.setBoundingBox(new AxisAlignedBB(e.getLocation().getX(), e.getLocation().getY(),
						e.getLocation().getZ(), e.getLocation().getX() + 1.0, e.getLocation().getY() + 1.0,
						e.getLocation().getZ() + 1.0));
			}

		} else {
			if (e.getBlock() instanceof BlockLiquid && e.getEntity() == this.mc.thePlayer
					&& this.isGoodToJesusMyDudes()) {
				e.setBoundingBox(new AxisAlignedBB(e.getLocation().getX(), e.getLocation().getY(),
						e.getLocation().getZ(), e.getLocation().getX() + 1.0, e.getLocation().getY() + 1.000001,
						e.getLocation().getZ() + 1.0));
			}
		}
	}

	private boolean isGoodToJesusMyDudes() {
		return this.mc.thePlayer.fallDistance < 3.0f && !this.mc.gameSettings.keyBindJump.isPressed()
				&& !BlockHelper.isInLiquid() && !this.mc.thePlayer.isSneaking();
	}

}
