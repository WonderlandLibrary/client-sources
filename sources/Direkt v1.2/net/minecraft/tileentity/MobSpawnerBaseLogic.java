package net.minecraft.tileentity;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StringUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

public abstract class MobSpawnerBaseLogic {
	/** The delay to spawn. */
	private int spawnDelay = 20;
	private final List<WeightedSpawnerEntity> minecartToSpawn = Lists.<WeightedSpawnerEntity> newArrayList();
	private WeightedSpawnerEntity randomEntity = new WeightedSpawnerEntity();

	/** The rotation of the mob inside the mob spawner */
	private double mobRotation;

	/** the previous rotation of the mob inside the mob spawner */
	private double prevMobRotation;
	private int minSpawnDelay = 200;
	private int maxSpawnDelay = 800;
	private int spawnCount = 4;

	/** Cached instance of the entity to render inside the spawner. */
	private Entity cachedEntity;
	private int maxNearbyEntities = 6;

	/** The distance from which a player activates the spawner. */
	private int activatingRangeFromPlayer = 16;

	/** The range coefficient for spawning entities around. */
	private int spawnRange = 4;

	/**
	 * Gets the entity name that should be spawned.
	 */
	private String getEntityNameToSpawn() {
		return this.randomEntity.getNbt().getString("id");
	}

	public void setEntityName(String name) {
		this.randomEntity.getNbt().setString("id", name);
	}

	/**
	 * Returns true if there's a player close enough to this mob spawner to activate it.
	 */
	private boolean isActivated() {
		BlockPos blockpos = this.getSpawnerPosition();
		return this.getSpawnerWorld().isAnyPlayerWithinRangeAt(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D, this.activatingRangeFromPlayer);
	}

	public void updateSpawner() {
		if (!this.isActivated()) {
			this.prevMobRotation = this.mobRotation;
		} else {
			BlockPos blockpos = this.getSpawnerPosition();

			if (this.getSpawnerWorld().isRemote) {
				double d3 = blockpos.getX() + this.getSpawnerWorld().rand.nextFloat();
				double d4 = blockpos.getY() + this.getSpawnerWorld().rand.nextFloat();
				double d5 = blockpos.getZ() + this.getSpawnerWorld().rand.nextFloat();
				this.getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);
				this.getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);

				if (this.spawnDelay > 0) {
					--this.spawnDelay;
				}

				this.prevMobRotation = this.mobRotation;
				this.mobRotation = (this.mobRotation + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0D;
			} else {
				if (this.spawnDelay == -1) {
					this.resetTimer();
				}

				if (this.spawnDelay > 0) {
					--this.spawnDelay;
					return;
				}

				boolean flag = false;

				for (int i = 0; i < this.spawnCount; ++i) {
					NBTTagCompound nbttagcompound = this.randomEntity.getNbt();
					NBTTagList nbttaglist = nbttagcompound.getTagList("Pos", 6);
					World world = this.getSpawnerWorld();
					int j = nbttaglist.tagCount();
					double d0 = j >= 1 ? nbttaglist.getDoubleAt(0) : blockpos.getX() + ((world.rand.nextDouble() - world.rand.nextDouble()) * this.spawnRange) + 0.5D;
					double d1 = j >= 2 ? nbttaglist.getDoubleAt(1) : (double) ((blockpos.getY() + world.rand.nextInt(3)) - 1);
					double d2 = j >= 3 ? nbttaglist.getDoubleAt(2) : blockpos.getZ() + ((world.rand.nextDouble() - world.rand.nextDouble()) * this.spawnRange) + 0.5D;
					Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, d0, d1, d2, false);

					if (entity == null) { return; }

					int k = world
							.getEntitiesWithinAABB(entity.getClass(),
									(new AxisAlignedBB(blockpos.getX(), blockpos.getY(), blockpos.getZ(), blockpos.getX() + 1, blockpos.getY() + 1, blockpos.getZ() + 1)).expandXyz(this.spawnRange))
							.size();

					if (k >= this.maxNearbyEntities) {
						this.resetTimer();
						return;
					}

					EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving) entity : null;
					entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, world.rand.nextFloat() * 360.0F, 0.0F);

					if ((entityliving == null) || (entityliving.getCanSpawnHere() && entityliving.isNotColliding())) {
						if ((this.randomEntity.getNbt().getSize() == 1) && this.randomEntity.getNbt().hasKey("id", 8) && (entity instanceof EntityLiving)) {
							((EntityLiving) entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData) null);
						}

						AnvilChunkLoader.spawnEntity(entity, world);
						world.playEvent(2004, blockpos, 0);

						if (entityliving != null) {
							entityliving.spawnExplosionParticle();
						}

						flag = true;
					}
				}

				if (flag) {
					this.resetTimer();
				}
			}
		}
	}

	private void resetTimer() {
		if (this.maxSpawnDelay <= this.minSpawnDelay) {
			this.spawnDelay = this.minSpawnDelay;
		} else {
			int i = this.maxSpawnDelay - this.minSpawnDelay;
			this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(i);
		}

		if (!this.minecartToSpawn.isEmpty()) {
			this.setNextSpawnData(WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.minecartToSpawn));
		}

		this.broadcastEvent(1);
	}

	public void readFromNBT(NBTTagCompound nbt) {
		this.spawnDelay = nbt.getShort("Delay");
		this.minecartToSpawn.clear();

		if (nbt.hasKey("SpawnPotentials", 9)) {
			NBTTagList nbttaglist = nbt.getTagList("SpawnPotentials", 10);

			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				this.minecartToSpawn.add(new WeightedSpawnerEntity(nbttaglist.getCompoundTagAt(i)));
			}
		}

		NBTTagCompound nbttagcompound = nbt.getCompoundTag("SpawnData");

		if (!nbttagcompound.hasKey("id", 8)) {
			nbttagcompound.setString("id", "Pig");
		}

		this.setNextSpawnData(new WeightedSpawnerEntity(1, nbttagcompound));

		if (nbt.hasKey("MinSpawnDelay", 99)) {
			this.minSpawnDelay = nbt.getShort("MinSpawnDelay");
			this.maxSpawnDelay = nbt.getShort("MaxSpawnDelay");
			this.spawnCount = nbt.getShort("SpawnCount");
		}

		if (nbt.hasKey("MaxNearbyEntities", 99)) {
			this.maxNearbyEntities = nbt.getShort("MaxNearbyEntities");
			this.activatingRangeFromPlayer = nbt.getShort("RequiredPlayerRange");
		}

		if (nbt.hasKey("SpawnRange", 99)) {
			this.spawnRange = nbt.getShort("SpawnRange");
		}

		if (this.getSpawnerWorld() != null) {
			this.cachedEntity = null;
		}
	}

	public NBTTagCompound func_189530_b(NBTTagCompound p_189530_1_) {
		String s = this.getEntityNameToSpawn();

		if (StringUtils.isNullOrEmpty(s)) {
			return p_189530_1_;
		} else {
			p_189530_1_.setShort("Delay", (short) this.spawnDelay);
			p_189530_1_.setShort("MinSpawnDelay", (short) this.minSpawnDelay);
			p_189530_1_.setShort("MaxSpawnDelay", (short) this.maxSpawnDelay);
			p_189530_1_.setShort("SpawnCount", (short) this.spawnCount);
			p_189530_1_.setShort("MaxNearbyEntities", (short) this.maxNearbyEntities);
			p_189530_1_.setShort("RequiredPlayerRange", (short) this.activatingRangeFromPlayer);
			p_189530_1_.setShort("SpawnRange", (short) this.spawnRange);
			p_189530_1_.setTag("SpawnData", this.randomEntity.getNbt().copy());
			NBTTagList nbttaglist = new NBTTagList();

			if (this.minecartToSpawn.isEmpty()) {
				nbttaglist.appendTag(this.randomEntity.toCompoundTag());
			} else {
				for (WeightedSpawnerEntity weightedspawnerentity : this.minecartToSpawn) {
					nbttaglist.appendTag(weightedspawnerentity.toCompoundTag());
				}
			}

			p_189530_1_.setTag("SpawnPotentials", nbttaglist);
			return p_189530_1_;
		}
	}

	public Entity getCachedEntity() {
		if (this.cachedEntity == null) {
			this.cachedEntity = AnvilChunkLoader.readWorldEntity(this.randomEntity.getNbt(), this.getSpawnerWorld(), false);

			if ((this.randomEntity.getNbt().getSize() == 1) && this.randomEntity.getNbt().hasKey("id", 8) && (this.cachedEntity instanceof EntityLiving)) {
				((EntityLiving) this.cachedEntity).onInitialSpawn(this.getSpawnerWorld().getDifficultyForLocation(new BlockPos(this.cachedEntity)), (IEntityLivingData) null);
			}
		}

		return this.cachedEntity;
	}

	/**
	 * Sets the delay to minDelay if parameter given is 1, else return false.
	 */
	public boolean setDelayToMin(int delay) {
		if ((delay == 1) && this.getSpawnerWorld().isRemote) {
			this.spawnDelay = this.minSpawnDelay;
			return true;
		} else {
			return false;
		}
	}

	public void setNextSpawnData(WeightedSpawnerEntity p_184993_1_) {
		this.randomEntity = p_184993_1_;
	}

	public abstract void broadcastEvent(int id);

	public abstract World getSpawnerWorld();

	public abstract BlockPos getSpawnerPosition();

	public double getMobRotation() {
		return this.mobRotation;
	}

	public double getPrevMobRotation() {
		return this.prevMobRotation;
	}
}
