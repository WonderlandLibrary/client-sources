package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {
	/** Current path navigation target */
	private BlockPos targetPosition;

	public PathNavigateClimber(EntityLiving entityLivingIn, World worldIn) {
		super(entityLivingIn, worldIn);
	}

	/**
	 * Returns path to given BlockPos
	 */
	@Override
	public Path getPathToPos(BlockPos pos) {
		this.targetPosition = pos;
		return super.getPathToPos(pos);
	}

	/**
	 * Returns the path to the given EntityLiving. Args : entity
	 */
	@Override
	public Path getPathToEntityLiving(Entity entityIn) {
		this.targetPosition = new BlockPos(entityIn);
		return super.getPathToEntityLiving(entityIn);
	}

	/**
	 * Try to find and set a path to EntityLiving. Returns true if successful. Args : entity, speed
	 */
	@Override
	public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
		Path path = this.getPathToEntityLiving(entityIn);

		if (path != null) {
			return this.setPath(path, speedIn);
		} else {
			this.targetPosition = new BlockPos(entityIn);
			this.speed = speedIn;
			return true;
		}
	}

	@Override
	public void onUpdateNavigation() {
		if (!this.noPath()) {
			super.onUpdateNavigation();
		} else {
			if (this.targetPosition != null) {
				double d0 = this.theEntity.width * this.theEntity.width;

				if ((this.theEntity.getDistanceSqToCenter(this.targetPosition) >= d0) && ((this.theEntity.posY <= this.targetPosition.getY())
						|| (this.theEntity.getDistanceSqToCenter(new BlockPos(this.targetPosition.getX(), MathHelper.floor_double(this.theEntity.posY), this.targetPosition.getZ())) >= d0))) {
					this.theEntity.getMoveHelper().setMoveTo(this.targetPosition.getX(), this.targetPosition.getY(), this.targetPosition.getZ(), this.speed);
				} else {
					this.targetPosition = null;
				}
			}
		}
	}
}
