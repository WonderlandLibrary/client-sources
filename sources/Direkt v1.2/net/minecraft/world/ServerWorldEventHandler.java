package net.minecraft.world;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class ServerWorldEventHandler implements IWorldEventListener {
	/** Reference to the MinecraftServer object. */
	private final MinecraftServer mcServer;

	/** The WorldServer object. */
	private final WorldServer theWorldServer;

	public ServerWorldEventHandler(MinecraftServer mcServerIn, WorldServer worldServerIn) {
		this.mcServer = mcServerIn;
		this.theWorldServer = worldServerIn;
	}

	@Override
	public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
	}

	/**
	 * Called on all IWorldAccesses when an entity is created or loaded. On client worlds, starts downloading any necessary textures. On server worlds, adds the entity to the entity tracker.
	 */
	@Override
	public void onEntityAdded(Entity entityIn) {
		this.theWorldServer.getEntityTracker().trackEntity(entityIn);

		if (entityIn instanceof EntityPlayerMP) {
			this.theWorldServer.provider.onPlayerAdded((EntityPlayerMP) entityIn);
		}
	}

	/**
	 * Called on all IWorldAccesses when an entity is unloaded or destroyed. On client worlds, releases any downloaded textures. On server worlds, removes the entity from the entity tracker.
	 */
	@Override
	public void onEntityRemoved(Entity entityIn) {
		this.theWorldServer.getEntityTracker().untrackEntity(entityIn);
		this.theWorldServer.getScoreboard().removeEntity(entityIn);

		if (entityIn instanceof EntityPlayerMP) {
			this.theWorldServer.provider.onPlayerRemoved((EntityPlayerMP) entityIn);
		}
	}

	@Override
	public void playSoundToAllNearExcept(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {
		this.mcServer.getPlayerList().sendToAllNearExcept(player, x, y, z, volume > 1.0F ? (double) (16.0F * volume) : 16.0D, this.theWorldServer.provider.getDimensionType().getId(),
				new SPacketSoundEffect(soundIn, category, x, y, z, volume, pitch));
	}

	/**
	 * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing.
	 */
	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
	}

	@Override
	public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
		this.theWorldServer.getPlayerChunkMap().markBlockForUpdate(pos);
	}

	@Override
	public void notifyLightSet(BlockPos pos) {
	}

	@Override
	public void playRecord(SoundEvent soundIn, BlockPos pos) {
	}

	@Override
	public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {
		this.mcServer.getPlayerList().sendToAllNearExcept(player, blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ(), 64.0D, this.theWorldServer.provider.getDimensionType().getId(),
				new SPacketEffect(type, blockPosIn, data, false));
	}

	@Override
	public void broadcastSound(int soundID, BlockPos pos, int data) {
		this.mcServer.getPlayerList().sendPacketToAllPlayers(new SPacketEffect(soundID, pos, data, true));
	}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
		for (EntityPlayerMP entityplayermp : this.mcServer.getPlayerList().getPlayerList()) {
			if ((entityplayermp != null) && (entityplayermp.worldObj == this.theWorldServer) && (entityplayermp.getEntityId() != breakerId)) {
				double d0 = pos.getX() - entityplayermp.posX;
				double d1 = pos.getY() - entityplayermp.posY;
				double d2 = pos.getZ() - entityplayermp.posZ;

				if (((d0 * d0) + (d1 * d1) + (d2 * d2)) < 1024.0D) {
					entityplayermp.connection.sendPacket(new SPacketBlockBreakAnim(breakerId, pos, progress));
				}
			}
		}
	}
}
