package net.minecraft.pathfinding;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class SwimNodeProcessor extends NodeProcessor {
	@Override
	public PathPoint getStart() {
		return this.openPoint(MathHelper.floor_double(this.entity.getEntityBoundingBox().minX), MathHelper.floor_double(this.entity.getEntityBoundingBox().minY + 0.5D),
				MathHelper.floor_double(this.entity.getEntityBoundingBox().minZ));
	}

	/**
	 * Returns PathPoint for given coordinates
	 */
	@Override
	public PathPoint getPathPointToCoords(double x, double y, double z) {
		return this.openPoint(MathHelper.floor_double(x - this.entity.width / 2.0F), MathHelper.floor_double(y + 0.5D), MathHelper.floor_double(z - this.entity.width / 2.0F));
	}

	@Override
	public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
		int i = 0;

		for (EnumFacing enumfacing : EnumFacing.values()) {
			PathPoint pathpoint = this.getWaterNode(currentPoint.xCoord + enumfacing.getFrontOffsetX(), currentPoint.yCoord + enumfacing.getFrontOffsetY(),
					currentPoint.zCoord + enumfacing.getFrontOffsetZ());

			if ((pathpoint != null) && !pathpoint.visited && (pathpoint.distanceTo(targetPoint) < maxDistance)) {
				pathOptions[i++] = pathpoint;
			}
		}

		return i;
	}

	@Override
	public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, EntityLiving entitylivingIn, int xSize, int ySize, int zSize, boolean canBreakDoorsIn,
			boolean canEnterDoorsIn) {
		return PathNodeType.WATER;
	}

	@Override
	public PathNodeType getPathNodeType(IBlockAccess x, int y, int z, int p_186330_4_) {
		return PathNodeType.WATER;
	}

	@Nullable
	private PathPoint getWaterNode(int p_186328_1_, int p_186328_2_, int p_186328_3_) {
		PathNodeType pathnodetype = this.isFree(p_186328_1_, p_186328_2_, p_186328_3_);
		return pathnodetype == PathNodeType.WATER ? this.openPoint(p_186328_1_, p_186328_2_, p_186328_3_) : null;
	}

	private PathNodeType isFree(int p_186327_1_, int p_186327_2_, int p_186327_3_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int i = p_186327_1_; i < (p_186327_1_ + this.entitySizeX); ++i) {
			for (int j = p_186327_2_; j < (p_186327_2_ + this.entitySizeY); ++j) {
				for (int k = p_186327_3_; k < (p_186327_3_ + this.entitySizeZ); ++k) {
					IBlockState iblockstate = this.blockaccess.getBlockState(blockpos$mutableblockpos.set(i, j, k));

					if (iblockstate.getMaterial() != Material.WATER) { return PathNodeType.BLOCKED; }
				}
			}
		}

		return PathNodeType.WATER;
	}
}
