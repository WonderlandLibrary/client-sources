package win.sightclient.module.player;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.utils.RotationUtils;

public class BedAura extends Module {

	private BlockPos breaking;
	private float blockDamage;
	
	public BedAura() {
		super("BedAura", Category.PLAYER);
	}

	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			if (eu.isPre()) {
				if (breaking == null || !this.isValid(breaking)) {
					if (breaking != null) {
						BlockPos old = new BlockPos(breaking.getX(), breaking.getY(), breaking.getZ());
						this.getBlockPos();
						if (breaking == null) {
							mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, old, EnumFacing.DOWN));
						}
					} else {
						this.getBlockPos();
					}
				}
				if (this.breaking == null) {
					this.blockDamage = 0F;
					return;
				}
				float[] rots = RotationUtils.faceBlock(this.breaking);
				eu.setYaw(rots[0]);
				eu.setPitch(rots[1]);
				if (this.blockDamage == 0F) {
					float oldYaw = mc.thePlayer.rotationYaw;
					float oldPitch = mc.thePlayer.rotationPitch;
					mc.thePlayer.rotationYaw = rots[0];
					mc.thePlayer.rotationPitch = rots[1];
					MovingObjectPosition ob = mc.thePlayer.rayTrace(mc.playerController.getBlockReachDistance(), 1F);
					if (ob.sideHit != null) {
						mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breaking, ob.sideHit));
					}
					mc.thePlayer.rotationYaw = oldYaw;
					mc.thePlayer.rotationPitch = oldPitch;
				}
				
				this.blockDamage += mc.theWorld.getBlockState(this.breaking).getBlock().getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, this.breaking);
				
				if (this.blockDamage >= 1F) {
					float oldYaw = mc.thePlayer.rotationYaw;
					float oldPitch = mc.thePlayer.rotationPitch;
					mc.thePlayer.rotationYaw = rots[0];
					mc.thePlayer.rotationPitch = rots[1];
					MovingObjectPosition ob = mc.thePlayer.rayTrace(mc.playerController.getBlockReachDistance(), 1F);
					if (ob.sideHit != null) {
						mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breaking, ob.sideHit));
						mc.playerController.onPlayerDestroyBlock(this.breaking, ob.sideHit);
						this.breaking = null;
						this.blockDamage = 0F;
					}
					mc.thePlayer.rotationYaw = oldYaw;
					mc.thePlayer.rotationPitch = oldPitch;
				}
			}
		}
	}
	
	private void getBlockPos() {
		this.breaking = null;
		for (double x = -mc.playerController.getBlockReachDistance(); x < mc.playerController.getBlockReachDistance(); x += 0.5) {
			for (double z = -mc.playerController.getBlockReachDistance(); z < mc.playerController.getBlockReachDistance(); z += 0.5) {
				for (double y = -mc.playerController.getBlockReachDistance(); y < mc.playerController.getBlockReachDistance(); y += 0.5) {
					BlockPos ps = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
					if (this.isValid(ps)) {
						this.breaking = ps;
					}
				}
			}
		}
	}
	
	private boolean isValid(BlockPos pos) {
		if (mc.objectMouseOver.getBlockPos() != null && mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() != Blocks.air && mc.gameSettings.keyBindAttack.isKeyDown()) {
			return false;
		}
		if (mc.theWorld.getBlockState(pos).getBlock() != Blocks.bed) {
			return false;
		}
		if (mc.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ()) >= mc.playerController.getBlockReachDistance()) {
			return false;
		}
		return true;
	}
}
