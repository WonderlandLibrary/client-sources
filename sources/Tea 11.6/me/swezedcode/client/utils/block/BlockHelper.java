package me.swezedcode.client.utils.block;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class BlockHelper {

	public static Minecraft mc = Minecraft.getMinecraft();

	private List<Block> invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water,
			Blocks.lava, Blocks.flowing_lava, Blocks.anvil, Blocks.chest, Blocks.bed, Blocks.ender_chest,
			Blocks.trapped_chest);

	public static boolean isUnderBlock() {
		if (mc.thePlayer == null) {
			return false;
		}
		int y = (int) mc.thePlayer.getLocation().add(0, 2, 0).getY();
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
				Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if ((block != null) && (!(block instanceof BlockAir)) && (block.isCollidable())) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isOnLiquid() {
		boolean onLiquid = false;
		final int y = (int) (BlockHelper.mc.thePlayer.boundingBox.minY - 0.01);
		for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
				final Block block = BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					onLiquid = true;
				}
			}
		}
		return onLiquid;
	}

	public static boolean isInLiquid() {
		boolean inLiquid = false;
		if (BlockHelper.mc.thePlayer == null || BlockHelper.mc.thePlayer.boundingBox == null) {
			return false;
		}
		final int y = (int) BlockHelper.mc.thePlayer.boundingBox.minY;
		for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
				final Block block = BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					inLiquid = true;
				}
			}
		}
		return inLiquid;
	}

	public static boolean isInLiquidNew() {
		boolean inLiquid = false;
		final int y = (int) BlockHelper.mc.thePlayer.boundingBox.minY;
		for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(BlockHelper.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
				final Block block = BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					inLiquid = true;
				}
			}
		}
		return inLiquid;
	}

	public BlockData getBlockData(BlockPos pos) {
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
			return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
			return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
			return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
			return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
			return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockPos add = pos.add(-1, 0, 0);
		if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock())) {
			return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock())) {
			return new BlockData(add.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock())) {
			return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock())) {
			return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockPos add2 = pos.add(1, 0, 0);
		if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock())) {
			return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock())) {
			return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock())) {
			return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock())) {
			return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockPos add3 = pos.add(0, 0, -1);
		if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock())) {
			return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock())) {
			return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock())) {
			return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock())) {
			return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockPos add4 = pos.add(0, 0, 1);
		if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock())) {
			return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock())) {
			return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock())) {
			return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock())) {
			return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH);
		}
		BlockData blockData = null;

		return blockData;
	}

	public static Block getBlock(int x, int y, int z) {
		return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
	}

	public static class BlockData {
		public BlockPos position;
		public EnumFacing face;

		public BlockData(BlockPos position, EnumFacing face) {
			this.position = position;
			this.face = face;
		}
	}
	
	public static boolean isInsideBlock() {
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
			for (int y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY); y < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxY) + 1; y++) {
				for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
						.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
					Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					if ((block != null) && (!(block instanceof BlockAir))) {
						AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z),
								mc.theWorld.getBlockState(new BlockPos(x, y, z)));
						if ((block instanceof BlockHopper)) {
							boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
						}
						if ((boundingBox != null) && (mc.thePlayer.boundingBox.intersectsWith(boundingBox))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
