package me.valk.agway.modes.phase;

import me.valk.agway.modules.world.Phase;
import me.valk.event.EventListener;
import me.valk.event.events.entity.EventEntityCollision;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.module.ModMode;
import me.valk.utils.TimerUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class SkipMode extends ModMode<Phase> {

	private int resetNext;
	private boolean vanilla = true;
	private TimerUtils timer = new TimerUtils();

	public SkipMode(Phase parent) {
		super(parent, "Skip");
	}

	public void onEnable() {
		timer.reset();
	}

	public void onDisable() {
		if (mc.timer != null)
			mc.timer.timerSpeed = 1.0F;
		if (p != null)
			p.noClip = false;
		timer.reset();
		super.onEnable();
	}

	@EventListener
	public void onUpdate(EventPlayerUpdate event) {
		if (vanilla) {
			this.resetNext -= 1;
			double xOff = 0.0D;
			double zOff = 0.0D;
			double multiplier = 2.6D;
			double mx = Math.cos(Math.toRadians(p.rotationYaw + 90.0F));
			double mz = Math.sin(Math.toRadians(p.rotationYaw + 90.0F));
			xOff = p.moveForward * 2.6D * mx + p.moveStrafing * 2.6D * mz;
			zOff = p.moveForward * 2.6D * mz - p.moveStrafing * 2.6D * mx;
			if (parent.isInsideBlock() && p.isSneaking()) {
				this.resetNext = 1;
			}
			if (resetNext > 0) {
				p.boundingBox.offsetAndUpdate(xOff, 0.0D, zOff);
			}
		} else if ((timer.hasReached(150)) && (p.isCollidedHorizontally)) {
			float yaw = p.rotationYaw;
			if (p.moveForward < 0.0F) {
				yaw += 180.0F;
			}
			if (p.moveStrafing > 0.0F) {
				yaw -= 90.0F * (p.moveForward < 0.0F ? -0.5F : p.moveForward > 0.0F ? 0.5F : 1.0F);
			}
			if (p.moveStrafing < 0.0F) {
				yaw += 90.0F * (p.moveForward < 0.0F ? -0.5F : p.moveForward > 0.0F ? 0.5F : 1.0F);
			}
			double horizontalMultiplier = 0.3D;
			double xOffset = (float) Math.cos((yaw + 90.0F) * 3.141592653589793D / 180.0D) * 0.3D;
			double zOffset = (float) Math.sin((yaw + 90.0F) * 3.141592653589793D / 180.0D) * 0.3D;
			double yOffset = 0.0D;
			for (int i = 0; i < 3; i++) {
				yOffset += 0.01D;
				this.mc.getNetHandler().addToSendQueue(
						new C03PacketPlayer.C04PacketPlayerPosition(p.posX, p.posY - yOffset, p.posZ, p.onGround));
				this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(p.posX + xOffset * i,
						p.posY, p.posZ + zOffset * i, p.onGround));
			}
		} else if (!p.isCollidedHorizontally) {
			this.timer.reset();
		}
	}

	@EventListener
	public boolean onSetBoundingbox(EventEntityCollision event) {
		{
			if (((parent.isInsideBlock()) && (mc.gameSettings.keyBindJump.pressed))
					|| ((!parent.isInsideBlock()) && (event.getBoundingBox() != null)
							&& (event.getBoundingBox().maxY > p.boundingBox.minY) && (vanilla) && (p.isSneaking()))) {
				event.setBoundingBox(null);
			}
		}
		return true;
	}
}
