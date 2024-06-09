/**
 * 
 */
package cafe.kagu.kagu.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vector3d;

/**
 * @author lavaflowglow
 *
 */
public class WorldUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Called at the start of the client
	 */
	public static void start() {
		
	}
	
	/**
	 * @param amount The amount to extend
	 * @param posX The x starting pos
	 * @param posX The y starting pos
	 * @param posX The y starting pos
	 * @return The extended block pos, uses strafe motion for the extend yaw
	 */
	public static double[] extendPosition(double amount, double posX, double posY, double posZ) {
		if (!MovementUtils.isPlayerMoving())
			return new double[] {posX, posY, posZ};
		double yaw = RotationUtils.getStrafeYaw() + 90;
		posX += Math.cos(Math.toRadians(yaw)) * amount;
		posZ += Math.sin(Math.toRadians(yaw)) * amount;
		return new double[] {posX, posY, posZ};
	}
	
	/**
	 * @param pos The pos to check
	 * @return true if the block in that position is solid, otherwise false
	 */
	public static boolean isBlockSolid(BlockPos pos) {
		IBlockState blockState = mc.theWorld.getBlockState(pos);
		return blockState.getBlock().canCollideCheck(blockState, false);
	}
	
	/**
	 * @param pos1 The first position
	 * @param pos2 The second position
	 * @return The distance between the two blocks
	 */
	public static double getDistance(BlockPos pos1, BlockPos pos2) {
		double distX = pos1.getX() - pos2.getX();
		double distY = pos1.getY() - pos2.getY();
		double distZ = pos1.getZ() - pos2.getZ();
		return Math.sqrt(distX * distX + distY * distY + distZ * distZ);
	}
	
	/**
	 * Calculates the block that the client should use in order to place a block in a specific position
	 * @param placePos The target position, where you're trying to place
	 * @param maxDistance The max distance the placeon block can be from the target block
	 * @return null if the place pos cannot be found, otherwise returns a <code>PlaceOnBlock</code> object that contains a blockpos and a enumfacing
	 */
	public static PlaceOnInfo getPlaceOn(BlockPos placePos, double maxDistance) {
		
		// Vars
		EntityPlayerSP thePlayer = mc.thePlayer;
		WorldClient theWorld = mc.theWorld;
		ItemBlock dummyBlock = new ItemBlock(Blocks.dirt);
		ItemStack dummyStack = new ItemStack(Blocks.dirt);
		
		// First do a simple check, if this comes back with a placeon position then advanced calculation isn't needed and won't be done
		EnumFacing[] checkOrder = new EnumFacing[] {EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.UP};
		for (EnumFacing facing : checkOrder) {
			BlockPos result = placePos.offset(facing);
			Block block = theWorld.getBlockState(result).getBlock();
			// The block is valid if it's solid, nothing happened when you right click it, and that specific side can be place on
			if (isBlockSolid(result) && !theWorld.getBlockState(result).getBlock().doesBlockActivate()
					&& dummyBlock.canPlaceBlockOnSide(theWorld, result, getFacingForTargetBlock(result, placePos), thePlayer, dummyStack)
					&& additionalPlaceOnBlockCheck(block)) {
				return new PlaceOnInfo(result, facing.getOpposite());
			}
		}
		
		// Get all solid blocks
		List<BlockPos> validBlockPositions = new ArrayList<>();
		int maxDistanceCeil = (int)Math.ceil(maxDistance);
		for (int x = -maxDistanceCeil; x < maxDistanceCeil; x++) {
			for (int z = -maxDistanceCeil; z < maxDistanceCeil; z++) {
				for (int y = -maxDistanceCeil; y < maxDistanceCeil; y++) {
					if (x == 0 && y == 0 && z == 0)
						continue;
					BlockPos pos = placePos.add(x, y, z);
					Block block = theWorld.getBlockState(pos).getBlock();
					
					// The block is valid if it's solid, nothing happened when you right click it, and that specific side can be place on
					if (isBlockSolid(pos) && !theWorld.getBlockState(pos).getBlock().doesBlockActivate()
							&& dummyBlock.canPlaceBlockOnSide(theWorld, pos, getFacingForTargetBlock(pos, placePos), thePlayer, dummyStack)
							&& additionalPlaceOnBlockCheck(block)) {
						validBlockPositions.add(pos);
					}
				}
			}
		}
		
		// If there are no valid block positions then just return null
		if (validBlockPositions.isEmpty())
			return null;
		
		// Order the list from closest blocks to farthest
		validBlockPositions = validBlockPositions.stream().sorted(Comparator.comparingDouble(pos -> getDistance(placePos, (BlockPos) pos))).collect(Collectors.toList());
		
		// Get the closest valid block
		BlockPos placeOn = validBlockPositions.get(0);
		
		// Return null if the closest valid block is too far away
		if (getDistance(placePos, placeOn) > maxDistance)
			return null;
		
		EnumFacing placeOnFacing = getFacingForTargetBlock(placeOn, placePos);
		
		if (placeOnFacing == null)
			return null;
		
		return new PlaceOnInfo(placeOn, placeOnFacing);
		
	}
	
	/**
	 * @param placeOn The block placed on
	 * @param target The block you want to reach
	 * @return The best facing to achieve this
	 */
	public static EnumFacing getFacingForTargetBlock(BlockPos placeOn, BlockPos target) {
		double xDist = Math.abs(placeOn.getX() - target.getX());
		double yDist = Math.abs(placeOn.getY() - target.getY());
		double zDist = Math.abs(placeOn.getZ() - target.getZ());
		
		if (yDist >= xDist && yDist >= zDist) {
			double realDist = placeOn.getY() - target.getY();
			if (realDist > 0) {
				return EnumFacing.DOWN;
			}
			else if (realDist < 0) {
				return EnumFacing.UP;
			}
		}
		else if (xDist >= yDist && xDist >= zDist) {
			double realDist = placeOn.getX() - target.getX();
			if (realDist > 0) {
				return EnumFacing.WEST;
			}
			else if (realDist < 0) {
				return EnumFacing.EAST;
			}
		}
		else if (zDist >= xDist && zDist >= yDist) {
			double realDist = placeOn.getZ() - target.getZ();
			if (realDist > 0) {
				return EnumFacing.NORTH;
			}
			else if (realDist < 0) {
				return EnumFacing.SOUTH;
			}
		}
		return null;
	}
	
	/**
	 * An additional block check meant to fix bugs in the placeOn method
	 * @param block The block to check
	 * @return true if it's valid, otherwise false
	 */
	public static boolean additionalPlaceOnBlockCheck(Block block) {
		return !(block instanceof BlockBush);
	}
	
	public static class PlaceOnInfo {
		
		/**
		 * @param placeOn
		 * @param placeFacing
		 */
		public PlaceOnInfo(BlockPos placeOn, EnumFacing placeFacing) {
			this.placeOn = placeOn;
			this.placeFacing = placeFacing;
		}
		
		private BlockPos placeOn;
		private EnumFacing placeFacing;

		/**
		 * @return the placeOn
		 */
		public BlockPos getPlaceOn() {
			return placeOn;
		}

		/**
		 * @param placeOn the placeOn to set
		 */
		public void setPlaceOn(BlockPos placeOn) {
			this.placeOn = placeOn;
		}

		/**
		 * @return the placeFacing
		 */
		public EnumFacing getPlaceFacing() {
			return placeFacing;
		}

		/**
		 * @param placeFacing the placeFacing to set
		 */
		public void setPlaceFacing(EnumFacing placeFacing) {
			this.placeFacing = placeFacing;
		}


		@Override
		public boolean equals(Object object) {
			if (this == object) return true;
			if (object == null || getClass() != object.getClass()) return false;
			PlaceOnInfo that = (PlaceOnInfo) object;
			return Objects.equals(placeOn, that.placeOn) && placeFacing == that.placeFacing;
		}

		@Override
		public int hashCode() {
			return Objects.hash(placeOn, placeFacing);
		}

	}
	
}
