/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AgeableEntity
extends CreatureEntity {
    private static final DataParameter<Boolean> BABY = EntityDataManager.createKey(AgeableEntity.class, DataSerializers.BOOLEAN);
    protected int growingAge;
    protected int forcedAge;
    protected int forcedAgeTimer;

    protected AgeableEntity(EntityType<? extends AgeableEntity> entityType, World world) {
        super((EntityType<? extends CreatureEntity>)entityType, world);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        AgeableData ageableData;
        if (iLivingEntityData == null) {
            iLivingEntityData = new AgeableData(true);
        }
        if ((ageableData = (AgeableData)iLivingEntityData).canBabySpawn() && ageableData.getIndexInGroup() > 0 && this.rand.nextFloat() <= ageableData.getBabySpawnProbability()) {
            this.setGrowingAge(-24000);
        }
        ageableData.incrementIndexInGroup();
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Nullable
    public abstract AgeableEntity func_241840_a(ServerWorld var1, AgeableEntity var2);

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BABY, false);
    }

    public boolean canBreed() {
        return true;
    }

    public int getGrowingAge() {
        if (this.world.isRemote) {
            return this.dataManager.get(BABY) != false ? -1 : 1;
        }
        return this.growingAge;
    }

    public void ageUp(int n, boolean bl) {
        int n2 = this.getGrowingAge();
        if ((n2 += n * 20) > 0) {
            n2 = 0;
        }
        int n3 = n2 - n2;
        this.setGrowingAge(n2);
        if (bl) {
            this.forcedAge += n3;
            if (this.forcedAgeTimer == 0) {
                this.forcedAgeTimer = 40;
            }
        }
        if (this.getGrowingAge() == 0) {
            this.setGrowingAge(this.forcedAge);
        }
    }

    public void addGrowth(int n) {
        this.ageUp(n, true);
    }

    public void setGrowingAge(int n) {
        int n2 = this.growingAge;
        this.growingAge = n;
        if (n2 < 0 && n >= 0 || n2 >= 0 && n < 0) {
            this.dataManager.set(BABY, n < 0);
            this.onGrowingAdult();
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Age", this.getGrowingAge());
        compoundNBT.putInt("ForcedAge", this.forcedAge);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setGrowingAge(compoundNBT.getInt("Age"));
        this.forcedAge = compoundNBT.getInt("ForcedAge");
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (BABY.equals(dataParameter)) {
            this.recalculateSize();
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            if (this.forcedAgeTimer > 0) {
                if (this.forcedAgeTimer % 4 == 0) {
                    this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getPosXRandom(1.0), this.getPosYRandom() + 0.5, this.getPosZRandom(1.0), 0.0, 0.0, 0.0);
                }
                --this.forcedAgeTimer;
            }
        } else if (this.isAlive()) {
            int n = this.getGrowingAge();
            if (n < 0) {
                this.setGrowingAge(++n);
            } else if (n > 0) {
                this.setGrowingAge(--n);
            }
        }
    }

    protected void onGrowingAdult() {
    }

    @Override
    public boolean isChild() {
        return this.getGrowingAge() < 0;
    }

    @Override
    public void setChild(boolean bl) {
        this.setGrowingAge(bl ? -24000 : 0);
    }

    public static class AgeableData
    implements ILivingEntityData {
        private int indexInGroup;
        private final boolean canBabySpawn;
        private final float babySpawnProbability;

        private AgeableData(boolean bl, float f) {
            this.canBabySpawn = bl;
            this.babySpawnProbability = f;
        }

        public AgeableData(boolean bl) {
            this(bl, 0.05f);
        }

        public AgeableData(float f) {
            this(true, f);
        }

        public int getIndexInGroup() {
            return this.indexInGroup;
        }

        public void incrementIndexInGroup() {
            ++this.indexInGroup;
        }

        public boolean canBabySpawn() {
            return this.canBabySpawn;
        }

        public float getBabySpawnProbability() {
            return this.babySpawnProbability;
        }
    }
}

