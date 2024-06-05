package me.superskidder.lune.modules.player;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventPacketSend;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class SpeedMine extends Mod {
	private boolean ready = false;
	private float blockHardness = 0.0f;
	public BlockPos blockPos;
	public EnumFacing facing;

	public SpeedMine() {
		super("SpeedMine", ModCategory.Player,"Fast Dig");
	}

	@EventTarget
	public void onPacket(EventPacketSend event) {
		if (event.getPacket() instanceof C07PacketPlayerDigging && !Minecraft.playerController.extendedReach()
				&& Minecraft.playerController != null) {
			C07PacketPlayerDigging packet = (C07PacketPlayerDigging) event.getPacket();
			if (packet.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
				this.ready = true;
				this.blockPos = packet.getPosition();
				this.facing = packet.getFacing();
				this.blockHardness = 0.0f;
			} else if (packet.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK
					|| packet.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
				this.ready = false;
				this.blockPos = null;
				this.facing = null;
			}
		}
	}

	@EventTarget
	private void onUpdate(EventUpdate e) {
		if(Minecraft.playerController.isInCreativeMode() && !mc.thePlayer.isServerWorld()){
			return;
		}
		if (Minecraft.playerController.extendedReach()) {
			Minecraft.playerController.blockHitDelay = 0;
		} else if (this.ready) {
			Block block = mc.theWorld.getBlockState(this.blockPos).getBlock();
			this.blockHardness += (float) ((double) block.getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld,
					this.blockPos) * 1.4);
			if (this.blockHardness >= 1.0f) {
				mc.theWorld.setBlockState(this.blockPos, Blocks.air.getDefaultState(), 11);
				mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C07PacketPlayerDigging(
						C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockPos, this.facing));
				this.blockHardness = 0.0f;
				this.ready = false;
			}
		}
	}
}
