package net.minecraft.pathfinding;

import java.util.EnumSet;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessor extends NodeProcessor {
	private float avoidsWater;

	@Override
	public void initProcessor(IBlockAccess sourceIn, EntityLiving mob) {
		super.initProcessor(sourceIn, mob);
		this.avoidsWater = mob.getPathPriority(PathNodeType.WATER);
	}

	/**
	 * This method is called when all nodes have been processed and PathEntity is created. {@link net.minecraft.world.pathfinder.WalkNodeProcessor WalkNodeProcessor} uses this to change its field {@link net.minecraft.world.pathfinder.WalkNodeProcessor#avoidsWater avoidsWater}
	 */
	@Override
	public void postProcess() {
		this.entity.setPathPriority(PathNodeType.WATER, this.avoidsWater);
		super.postProcess();
	}

	@Override
	public PathPoint getStart() {
		int i;

		if (this.getCanSwim() && this.entity.isInWater()) {
			i = (int) this.entity.getEntityBoundingBox().minY;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor_double(this.entity.posX), i, MathHelper.floor_double(this.entity.posZ));

			for (Block block = this.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock(); (block == Blocks.FLOWING_WATER)
					|| (block == Blocks.WATER); block = this.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock()) {
				++i;
				blockpos$mutableblockpos.set(MathHelper.floor_double(this.entity.posX), i, MathHelper.floor_double(this.entity.posZ));
			}
		} else if (this.entity.onGround) {
			i = MathHelper.floor_double(this.entity.getEntityBoundingBox().minY + 0.5D);
		} else {
			BlockPos blockpos;

			for (blockpos = new BlockPos(this.entity); ((this.blockaccess.getBlockState(blockpos).getMaterial() == Material.AIR)
					|| this.blockaccess.getBlockState(blockpos).getBlock().isPassable(this.blockaccess, blockpos)) && (blockpos.getY() > 0); blockpos = blockpos.down()) {
				;
			}

			i = blockpos.up().getY();
		}

		BlockPos blockpos2 = new BlockPos(this.entity);
		PathNodeType pathnodetype1 = this.getPathNodeType(this.entity, blockpos2.getX(), i, blockpos2.getZ());

		if (this.entity.getPathPriority(pathnodetype1) < 0.0F) {
			Set<BlockPos> set = Sets.<BlockPos> newHashSet();
			set.add(new BlockPos(this.entity.getEntityBoundingBox().minX, i, this.entity.getEntityBoundingBox().minZ));
			set.add(new BlockPos(this.entity.getEntityBoundingBox().minX, i, this.entity.getEntityBoundingBox().maxZ));
			set.add(new BlockPos(this.entity.getEntityBoundingBox().maxX, i, this.entity.getEntityBoundingBox().minZ));
			set.add(new BlockPos(this.entity.getEntityBoundingBox().maxX, i, this.entity.getEntityBoundingBox().maxZ));

			for (BlockPos blockpos1 : set) {
				PathNodeType pathnodetype = this.getPathNodeType(this.entity, blockpos1);

				if (this.entity.getPathPriority(pathnodetype) >= 0.0F) { return this.openPoint(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ()); }
			}
		}

		return this.openPoint(blockpos2.getX(), i, blockpos2.getZ());
	}

	/**
	 * Returns PathPoint for given coordinates
	 */
	@Override
	public PathPoint getPathPointToCoords(double x, double y, double z) {
		return this.openPoint(MathHelper.floor_double(x - this.entity.width / 2.0F), MathHelper.floor_double(y), MathHelper.floor_double(z - this.entity.width / 2.0F));
	}

	@Override
	public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
		int i = 0;
		int j = 0;
		PathNodeType pathnodetype = this.getPathNodeType(this.entity, currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord);

		if (this.entity.getPathPriority(pathnodetype) >= 0.0F) {
			j = MathHelper.floor_float(Math.max(1.0F, this.entity.stepHeight));
		}

		BlockPos blockpos = (new BlockPos(currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord)).down();
		double d0 = currentPoint.yCoord - (1.0D - this.blockaccess.getBlockState(blockpos).getBoundingBox(this.blockaccess, blockpos).maxY);
		PathPoint pathpoint = this.getSafePoint(currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord + 1, j, d0, EnumFacing.SOUTH);
		PathPoint pathpoint1 = this.getSafePoint(currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord, j, d0, EnumFacing.WEST);
		PathPoint pathpoint2 = this.getSafePoint(currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord, j, d0, EnumFacing.EAST);
		PathPoint pathpoint3 = this.getSafePoint(currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord - 1, j, d0, EnumFacing.NORTH);

		if ((pathpoint != null) && !pathpoint.visited && (pathpoint.distanceTo(targetPoint) < maxDistance)) {
			pathOptions[i++] = pathpoint;
		}

		if ((pathpoint1 != null) && !pathpoint1.visited && (pathpoint1.distanceTo(targetPoint) < maxDistance)) {
			pathOptions[i++] = pathpoint1;
		}

		if ((pathpoint2 != null) && !pathpoint2.visited && (pathpoint2.distanceTo(targetPoint) < maxDistance)) {
			pathOptions[i++] = pathpoint2;
		}

		if ((pathpoint3 != null) && !pathpoint3.visited && (pathpoint3.distanceTo(targetPoint) < maxDistance)) {
			pathOptions[i++] = pathpoint3;
		}

		boolean flag = (pathpoint3 == null) || (pathpoint3.nodeType == PathNodeType.OPEN) || (pathpoint3.costMalus != 0.0F);
		boolean flag1 = (pathpoint == null) || (pathpoint.nodeType == PathNodeType.OPEN) || (pathpoint.costMalus != 0.0F);
		boolean flag2 = (pathpoint2 == null) || (pathpoint2.nodeType == PathNodeType.OPEN) || (pathpoint2.costMalus != 0.0F);
		boolean flag3 = (pathpoint1 == null) || (pathpoint1.nodeType == PathNodeType.OPEN) || (pathpoint1.costMalus != 0.0F);

		if (flag && flag3) {
			PathPoint pathpoint4 = this.getSafePoint(currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord - 1, j, d0, EnumFacing.NORTH);

			if ((pathpoint4 != null) && !pathpoint4.visited && (pathpoint4.distanceTo(targetPoint) < maxDistance)) {
				pathOptions[i++] = pathpoint4;
			}
		}

		if (flag && flag2) {
			PathPoint pathpoint5 = this.getSafePoint(currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord - 1, j, d0, EnumFacing.NORTH);

			if ((pathpoint5 != null) && !pathpoint5.visited && (pathpoint5.distanceTo(targetPoint) < maxDistance)) {
				pathOptions[i++] = pathpoint5;
			}
		}

		if (flag1 && flag3) {
			PathPoint pathpoint6 = this.getSafePoint(currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord + 1, j, d0, EnumFacing.SOUTH);

			if ((pathpoint6 != null) && !pathpoint6.visited && (pathpoint6.distanceTo(targetPoint) < maxDistance)) {
				pathOptions[i++] = pathpoint6;
			}
		}

		if (flag1 && flag2) {
			PathPoint pathpoint7 = this.getSafePoint(currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord + 1, j, d0, EnumFacing.SOUTH);

			if ((pathpoint7 != null) && !pathpoint7.visited && (pathpoint7.distanceTo(targetPoint) < maxDistance)) {
				pathOptions[i++] = pathpoint7;
			}
		}

		return i;
	}

	@Nullable

	/**
	 * Returns a point that the entity can safely move to
	 */
	private PathPoint getSafePoint(int x, int y, int z, int p_186332_4_, double p_186332_5_, EnumFacing facing) {
		PathPoint pathpoint = null;
		BlockPos blockpos = new BlockPos(x, y, z);
		BlockPos blockpos1 = blockpos.down();
		double d0 = y - (1.0D - this.blockaccess.getBlockState(blockpos1).getBoundingBox(this.blockaccess, blockpos1).maxY);

		if ((d0 - p_186332_5_) > 1.125D) {
			return null;
		} else {
			PathNodeType pathnodetype = this.getPathNodeType(this.entity, x, y, z);
			float f = this.entity.getPathPriority(pathnodetype);
			double d1 = this.entity.width / 2.0D;

			if (f >= 0.0F) {
				pathpoint = this.openPoint(x, y, z);
				pathpoint.nodeType = pathnodetype;
				pathpoint.costMalus = Math.max(pathpoint.costMalus, f);
			}

			if (pathnodetype == PathNodeType.WALKABLE) {
				return pathpoint;
			} else {
				if ((pathpoint == null) && (p_186332_4_ > 0) && (pathnodetype != PathNodeType.FENCE) && (pathnodetype != PathNodeType.TRAPDOOR)) {
					pathpoint = this.getSafePoint(x, y + 1, z, p_186332_4_ - 1, p_186332_5_, facing);

					if ((pathpoint != null) && ((pathpoint.nodeType == PathNodeType.OPEN) || (pathpoint.nodeType == PathNodeType.WALKABLE)) && (this.entity.width < 1.0F)) {
						double d2 = x - facing.getFrontOffsetX() + 0.5D;
						double d3 = z - facing.getFrontOffsetZ() + 0.5D;
						AxisAlignedBB axisalignedbb = new AxisAlignedBB(d2 - d1, y + 0.001D, d3 - d1, d2 + d1, y + this.entity.height, d3 + d1);
						AxisAlignedBB axisalignedbb1 = this.blockaccess.getBlockState(blockpos).getBoundingBox(this.blockaccess, blockpos);
						AxisAlignedBB axisalignedbb2 = axisalignedbb.addCoord(0.0D, axisalignedbb1.maxY - 0.002D, 0.0D);

						if (this.entity.worldObj.collidesWithAnyBlock(axisalignedbb2)) {
							pathpoint = null;
						}
					}
				}

				if (pathnodetype == PathNodeType.OPEN) {
					AxisAlignedBB axisalignedbb3 = new AxisAlignedBB((x - d1) + 0.5D, y + 0.001D, (z - d1) + 0.5D, x + d1 + 0.5D, y + this.entity.height, z + d1 + 0.5D);

					if (this.entity.worldObj.collidesWithAnyBlock(axisalignedbb3)) { return null; }

					if (this.entity.width >= 1.0F) {
						PathNodeType pathnodetype1 = this.getPathNodeType(this.entity, x, y - 1, z);

						if (pathnodetype1 == PathNodeType.BLOCKED) {
							pathpoint = this.openPoint(x, y, z);
							pathpoint.nodeType = PathNodeType.WALKABLE;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, f);
							return pathpoint;
						}
					}

					int i = 0;

					while ((y > 0) && (pathnodetype == PathNodeType.OPEN)) {
						--y;

						if (i++ >= this.entity.getMaxFallHeight()) { return null; }

						pathnodetype = this.getPathNodeType(this.entity, x, y, z);
						f = this.entity.getPathPriority(pathnodetype);

						if ((pathnodetype != PathNodeType.OPEN) && (f >= 0.0F)) {
							pathpoint = this.openPoint(x, y, z);
							pathpoint.nodeType = pathnodetype;
							pathpoint.costMalus = Math.max(pathpoint.costMalus, f);
							break;
						}

						if (f < 0.0F) { return null; }
					}
				}

				return pathpoint;
			}
		}
	}

	@Override
	public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, EntityLiving entitylivingIn, int xSize, int ySize, int zSize, boolean canBreakDoorsIn,
			boolean canEnterDoorsIn) {
		EnumSet<PathNodeType> enumset = EnumSet.<PathNodeType> noneOf(PathNodeType.class);
		PathNodeType pathnodetype = PathNodeType.BLOCKED;
		double d0 = entitylivingIn.width / 2.0D;
		BlockPos blockpos = new BlockPos(entitylivingIn);

		for (int i = 0; i < xSize; ++i) {
			for (int j = 0; j < ySize; ++j) {
				for (int k = 0; k < zSize; ++k) {
					int l = i + x;
					int i1 = j + y;
					int j1 = k + z;
					PathNodeType pathnodetype1 = this.getPathNodeType(blockaccessIn, l, i1, j1);

					if ((pathnodetype1 == PathNodeType.DOOR_WOOD_CLOSED) && canBreakDoorsIn && canEnterDoorsIn) {
						pathnodetype1 = PathNodeType.WALKABLE;
					}

					if ((pathnodetype1 == PathNodeType.DOOR_OPEN) && !canEnterDoorsIn) {
						pathnodetype1 = PathNodeType.BLOCKED;
					}

					if ((pathnodetype1 == PathNodeType.RAIL) && !(blockaccessIn.getBlockState(blockpos).getBlock() instanceof BlockRailBase)
							&& !(blockaccessIn.getBlockState(blockpos.down()).getBlock() instanceof BlockRailBase)) {
						pathnodetype1 = PathNodeType.FENCE;
					}

					if ((i == 0) && (j == 0) && (k == 0)) {
						pathnodetype = pathnodetype1;
					}

					enumset.add(pathnodetype1);
				}
			}
		}

		if (enumset.contains(PathNodeType.FENCE)) {
			return PathNodeType.FENCE;
		} else {
			PathNodeType pathnodetype2 = PathNodeType.BLOCKED;

			for (PathNodeType pathnodetype3 : enumset) {
				if (entitylivingIn.getPathPriority(pathnodetype3) < 0.0F) { return pathnodetype3; }

				if (entitylivingIn.getPathPriority(pathnodetype3) >= entitylivingIn.getPathPriority(pathnodetype2)) {
					pathnodetype2 = pathnodetype3;
				}
			}

			if ((pathnodetype == PathNodeType.OPEN) && (entitylivingIn.getPathPriority(pathnodetype2) == 0.0F)) {
				return PathNodeType.OPEN;
			} else {
				return pathnodetype2;
			}
		}
	}

	private PathNodeType getPathNodeType(EntityLiving entitylivingIn, BlockPos pos) {
		return this.getPathNodeType(entitylivingIn, pos.getX(), pos.getY(), pos.getZ());
	}

	private PathNodeType getPathNodeType(EntityLiving entitylivingIn, int x, int y, int z) {
		return this.getPathNodeType(this.blockaccess, x, y, z, entitylivingIn, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.getCanBreakDoors(), this.getCanEnterDoors());
	}

	@Override
	public PathNodeType getPathNodeType(IBlockAccess x, int y, int z, int p_186330_4_) {
		PathNodeType pathnodetype = this.func_189553_b(x, y, z, p_186330_4_);

		if ((pathnodetype == PathNodeType.OPEN) && (z >= 1)) {
			Block block = x.getBlockState(new BlockPos(y, z - 1, p_186330_4_)).getBlock();
			PathNodeType pathnodetype1 = this.func_189553_b(x, y, z - 1, p_186330_4_);
			pathnodetype = (pathnodetype1 != PathNodeType.WALKABLE) && (pathnodetype1 != PathNodeType.OPEN) && (pathnodetype1 != PathNodeType.WATER) && (pathnodetype1 != PathNodeType.LAVA)
					? PathNodeType.WALKABLE : PathNodeType.OPEN;

			if ((pathnodetype1 == PathNodeType.DAMAGE_FIRE) || (block == Blocks.field_189877_df)) {
				pathnodetype = PathNodeType.DAMAGE_FIRE;
			}

			if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS) {
				pathnodetype = PathNodeType.DAMAGE_CACTUS;
			}
		}

		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		if (pathnodetype == PathNodeType.WALKABLE) {
			for (int j = -1; j <= 1; ++j) {
				for (int i = -1; i <= 1; ++i) {
					if ((j != 0) || (i != 0)) {
						Block block1 = x.getBlockState(blockpos$pooledmutableblockpos.set(j + y, z, i + p_186330_4_)).getBlock();

						if (block1 == Blocks.CACTUS) {
							pathnodetype = PathNodeType.DANGER_CACTUS;
						} else if (block1 == Blocks.FIRE) {
							pathnodetype = PathNodeType.DANGER_FIRE;
						}
					}
				}
			}
		}

		blockpos$pooledmutableblockpos.release();
		return pathnodetype;
	}

	private PathNodeType func_189553_b(IBlockAccess p_189553_1_, int p_189553_2_, int p_189553_3_, int p_189553_4_) {
		BlockPos blockpos = new BlockPos(p_189553_2_, p_189553_3_, p_189553_4_);
		IBlockState iblockstate = p_189553_1_.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		Material material = iblockstate.getMaterial();
		return material == Material.AIR ? PathNodeType.OPEN
				: ((block != Blocks.TRAPDOOR) && (block != Blocks.IRON_TRAPDOOR)
						&& (block != Blocks.WATERLILY)
								? (block == Blocks.FIRE ? PathNodeType.DAMAGE_FIRE
										: (block == Blocks.CACTUS ? PathNodeType.DAMAGE_CACTUS
												: ((block instanceof BlockDoor) && (material == Material.WOOD) && !iblockstate.getValue(BlockDoor.OPEN).booleanValue() ? PathNodeType.DOOR_WOOD_CLOSED
														: ((block instanceof BlockDoor) && (material == Material.IRON) && !iblockstate.getValue(BlockDoor.OPEN).booleanValue()
																? PathNodeType.DOOR_IRON_CLOSED
																: ((block instanceof BlockDoor) && iblockstate.getValue(BlockDoor.OPEN).booleanValue() ? PathNodeType.DOOR_OPEN
																		: (block instanceof BlockRailBase ? PathNodeType.RAIL
																				: (!(block instanceof BlockFence) && !(block instanceof BlockWall)
																						&& (!(block instanceof BlockFenceGate)
																								|| iblockstate.getValue(BlockFenceGate.OPEN).booleanValue())
																										? (material == Material.WATER ? PathNodeType.WATER
																												: (material == Material.LAVA ? PathNodeType.LAVA
																														: (block.isPassable(p_189553_1_, blockpos) ? PathNodeType.OPEN
																																: PathNodeType.BLOCKED)))
																										: PathNodeType.FENCE)))))))
								: PathNodeType.TRAPDOOR);
	}
}
