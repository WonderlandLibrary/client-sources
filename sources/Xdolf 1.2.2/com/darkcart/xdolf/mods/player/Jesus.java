  package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.mods.world.Freecam;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

// FIXME: NCP flags when colliding with lilypads
public class Jesus extends Module {

	public Jesus() {
		super("Jesus", "Walk on water.", Keyboard.KEY_J, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled() && !Hacks.findMod(Freecam.class).isEnabled()) {
			if (isInWater(player) && !player.isSneaking()) {
				player.motionY = 0.1;
				if(player.ridingEntity != null) {
					player.ridingEntity.motionY = 0.2;
				}
			}
		}
	}

	@Override
	public Packet<?> onPacketSend(Packet<?> packet) {
		if (isEnabled() && packet instanceof CPacketPlayer) {
			if (isAboveWater(Wrapper.getPlayer()) && !isInWater(Wrapper.getPlayer()) && !isAboveLand(Wrapper.getPlayer())) {
				int ticks = Wrapper.getPlayer().ticksExisted % 2;

				if (ticks == 0) ((CPacketPlayer) packet).y += 0.02;
			}
		}
		return packet;
	}

	@Override
	public AxisAlignedBB onAddCollisionBox(Block block, BlockPos pos, AxisAlignedBB box) {
		if (!isEnabled() || Wrapper.getPlayer() == null || !(block instanceof BlockLiquid)) {
			return box;
		}

		if (isInWater(Wrapper.getPlayer()) || Wrapper.getPlayer().isSneaking() || Wrapper.getPlayer().fallDistance > 3) {
			return box;
		}
		
		if (Wrapper.getPlayer().ridingEntity != null) {
			return box;
		}

		return new AxisAlignedBB(0, 0, 0, 1, 0.99, 1);
	}

	private static boolean isAboveLand(Entity entity){
		if(entity == null) return false;

		double y = entity.posY - 0.01;

		for(int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
			for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
				BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);

				if (Wrapper.getWorld().getBlockState(pos).getBlock().isFullBlock(Wrapper.getWorld().getBlockState(pos))) return true;
			}

		return false;
	}

	private static boolean isAboveWater(Entity entity){
		double y = entity.posY - 0.03;

		for(int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
			for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
				BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);

				if (Wrapper.getWorld().getBlockState(pos).getBlock() instanceof BlockLiquid) return true;
			}

		return false;
	}

	private static boolean isInWater(Entity entity) {
		if(entity == null) return false;

		double y = entity.posY + 0.01;

		for(int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
			for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
				BlockPos pos = new BlockPos(x, (int) y, z);

				if (Wrapper.getWorld().getBlockState(pos).getBlock() instanceof BlockLiquid) return true;
			}

		return false;
	}
}
