package net.minecraft.world;

import javax.annotation.Nullable;

import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.ChunkProviderEnd;

public class WorldProviderEnd extends WorldProvider {
	private DragonFightManager dragonFightManager;

	/**
	 * creates a new world chunk manager for WorldProvider
	 */
	@Override
	public void createBiomeProvider() {
		this.biomeProvider = new BiomeProviderSingle(Biomes.SKY);
		this.hasNoSky = true;
		NBTTagCompound nbttagcompound = this.worldObj.getWorldInfo().getDimensionData(DimensionType.THE_END);
		this.dragonFightManager = this.worldObj instanceof WorldServer ? new DragonFightManager((WorldServer) this.worldObj, nbttagcompound.getCompoundTag("DragonFight")) : null;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkProviderEnd(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed());
	}

	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
	 */
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return 0.0F;
	}

	/**
	 * Returns array with sunrise/sunset colors
	 */
	@Override
	public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
		return null;
	}

	/**
	 * Return Vec3D with biome specific fog color
	 */
	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		int i = 10518688;
		float f = (MathHelper.cos(p_76562_1_ * ((float) Math.PI * 2F)) * 2.0F) + 0.5F;
		f = MathHelper.clamp_float(f, 0.0F, 1.0F);
		float f1 = 0.627451F;
		float f2 = 0.5019608F;
		float f3 = 0.627451F;
		f1 = f1 * ((f * 0.0F) + 0.15F);
		f2 = f2 * ((f * 0.0F) + 0.15F);
		f3 = f3 * ((f * 0.0F) + 0.15F);
		return new Vec3d(f1, f2, f3);
	}

	@Override
	public boolean isSkyColored() {
		return false;
	}

	/**
	 * True if the player can respawn in this dimension (true = overworld, false = nether).
	 */
	@Override
	public boolean canRespawnHere() {
		return false;
	}

	/**
	 * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
	 */
	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	/**
	 * the y level at which clouds are rendered.
	 */
	@Override
	public float getCloudHeight() {
		return 8.0F;
	}

	/**
	 * Will check if the x, z position specified is alright to be set as the map spawn point
	 */
	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		return this.worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)).getMaterial().blocksMovement();
	}

	@Override
	public BlockPos getSpawnCoordinate() {
		return new BlockPos(100, 50, 0);
	}

	@Override
	public int getAverageGroundLevel() {
		return 50;
	}

	/**
	 * Returns true if the given X,Z coordinate should show environmental fog.
	 */
	@Override
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}

	@Override
	public DimensionType getDimensionType() {
		return DimensionType.THE_END;
	}

	/**
	 * Called when the world is performing a save. Only used to save the state of the Dragon Boss fight in WorldProviderEnd in Vanilla.
	 */
	@Override
	public void onWorldSave() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();

		if (this.dragonFightManager != null) {
			nbttagcompound.setTag("DragonFight", this.dragonFightManager.getCompound());
		}

		this.worldObj.getWorldInfo().setDimensionData(DimensionType.THE_END, nbttagcompound);
	}

	/**
	 * Called when the world is updating entities. Only used in WorldProviderEnd to update the DragonFightManager in Vanilla.
	 */
	@Override
	public void onWorldUpdateEntities() {
		if (this.dragonFightManager != null) {
			this.dragonFightManager.tick();
		}
	}

	@Nullable
	public DragonFightManager getDragonFightManager() {
		return this.dragonFightManager;
	}
}
