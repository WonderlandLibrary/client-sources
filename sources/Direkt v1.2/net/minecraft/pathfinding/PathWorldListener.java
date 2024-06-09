package net.minecraft.pathfinding;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;

public class PathWorldListener implements IWorldEventListener {
	private final List<PathNavigate> field_189519_a = Lists.<PathNavigate> newArrayList();

	@Override
	public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
		if (this.didBlockChange(worldIn, pos, oldState, newState)) {
			int i = 0;

			for (int j = this.field_189519_a.size(); i < j; ++i) {
				PathNavigate pathnavigate = this.field_189519_a.get(i);

				if ((pathnavigate != null) && !pathnavigate.canUpdatePathOnTimeout()) {
					Path path = pathnavigate.getPath();

					if ((path != null) && !path.isFinished() && (path.getCurrentPathLength() != 0)) {
						PathPoint pathpoint = pathnavigate.currentPath.getFinalPathPoint();
						double d0 = pos.distanceSq((pathpoint.xCoord + pathnavigate.theEntity.posX) / 2.0D, (pathpoint.yCoord + pathnavigate.theEntity.posY) / 2.0D,
								(pathpoint.zCoord + pathnavigate.theEntity.posZ) / 2.0D);
						int k = (path.getCurrentPathLength() - path.getCurrentPathIndex()) * (path.getCurrentPathLength() - path.getCurrentPathIndex());

						if (d0 < k) {
							pathnavigate.updatePath();
						}
					}
				}
			}
		}
	}

	protected boolean didBlockChange(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState) {
		AxisAlignedBB axisalignedbb = oldState.getCollisionBoundingBox(worldIn, pos);
		AxisAlignedBB axisalignedbb1 = newState.getCollisionBoundingBox(worldIn, pos);
		return (axisalignedbb != axisalignedbb1) && ((axisalignedbb == null) || !axisalignedbb.equals(axisalignedbb1));
	}

	@Override
	public void notifyLightSet(BlockPos pos) {
	}

	/**
	 * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing.
	 */
	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
	}

	@Override
	public void playSoundToAllNearExcept(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {
	}

	@Override
	public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
	}

	/**
	 * Called on all IWorldAccesses when an entity is created or loaded. On client worlds, starts downloading any necessary textures. On server worlds, adds the entity to the entity tracker.
	 */
	@Override
	public void onEntityAdded(Entity entityIn) {
		if (entityIn instanceof EntityLiving) {
			this.field_189519_a.add(((EntityLiving) entityIn).getNavigator());
		}
	}

	/**
	 * Called on all IWorldAccesses when an entity is unloaded or destroyed. On client worlds, releases any downloaded textures. On server worlds, removes the entity from the entity tracker.
	 */
	@Override
	public void onEntityRemoved(Entity entityIn) {
		if (entityIn instanceof EntityLiving) {
			this.field_189519_a.remove(((EntityLiving) entityIn).getNavigator());
		}
	}

	@Override
	public void playRecord(SoundEvent soundIn, BlockPos pos) {
	}

	@Override
	public void broadcastSound(int soundID, BlockPos pos, int data) {
	}

	@Override
	public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {
	}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
	}
}
