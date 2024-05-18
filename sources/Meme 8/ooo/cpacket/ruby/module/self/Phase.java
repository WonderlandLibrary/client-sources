package ooo.cpacket.ruby.module.self;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.blocks.EventAABB;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.util.SpeedUtils;
import tv.twitch.Core;

public class Phase extends Module {

	public Phase(String name, int key, Category category) {
		super(name, key, category);
		this.addNumberOption("Speed", 1, 2, 6);
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
	}

	@EventImpl
	public void onBlockCollide(EventAABB event) {
		if (event.getBoundingBox() != null && event.getBoundingBox().maxY > mc.thePlayer.boundingBox.minY) {
			event.setBoundingBox(null);
		}
	}

	@EventImpl
	public void onPrePostMotionUpdate(EventMotionUpdate e) {
		// PRE
		if (e.getState() == State.PRE) {
			mc.thePlayer.noClip = true;
			if (mc.thePlayer.isMoving() && this.isInsideBlock()) {
				// mc.thePlayer.sendQueue.addToSendQueue(new
				// C04PacketPlayerPosition(mc.thePlayer.posX,
				// mc.thePlayer.posY + (mc.thePlayer.isSneaking() ? -0.825 : 0.0),
				// mc.thePlayer.posZ, true));
			}
		}
		// POST
		if (e.getState() == State.POST) {
			final float yaw = mc.thePlayer.rotationYaw;
			double speed = this.getDouble("Speed") * (this.isInsideBlock() ? 0.15 : 0.1);
			double x = speed * Math.cos(Math.toRadians(yaw + 90.0f));
			double z = speed * Math.sin(Math.toRadians(yaw + 90.0f));
			if (mc.thePlayer.isMoving()) {
				SpeedUtils.setMoveSpeed(0.32);
			}
			if (!isInsideBlock() || mc.thePlayer.isSneaking() || mc.thePlayer.ticksExisted % 2048 == 0) {
				mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY + (mc.thePlayer.isSneaking() ? -0.0625 : 0.0), mc.thePlayer.posZ, true));
				mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY + (mc.thePlayer.isSneaking() ? 11125 : 3330.0), mc.thePlayer.posZ, true));
			}
			mc.timer.timerSpeed = 1.0f;
			if (isInsideBlock()) {
				mc.timer.timerSpeed = 1.0f;
			}
		}
	}

	private boolean isInsideBlock() {
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY + 1); y < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxY) + 2; ++y) {
				for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
						.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
					final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					if (block != null && !(block instanceof BlockAir)) {
						AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z),
								mc.theWorld.getBlockState(new BlockPos(x, y, z)));
						if (block instanceof BlockHopper) {
							boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
						}
						if (boundingBox != null && mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
