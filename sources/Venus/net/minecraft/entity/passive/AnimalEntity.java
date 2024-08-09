/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AnimalEntity
extends AgeableEntity {
    private int inLove;
    private UUID playerInLove;

    protected AnimalEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super((EntityType<? extends AgeableEntity>)entityType, world);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0f);
    }

    @Override
    protected void updateAITasks() {
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        super.updateAITasks();
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        if (this.inLove > 0) {
            --this.inLove;
            if (this.inLove % 10 == 0) {
                double d = this.rand.nextGaussian() * 0.02;
                double d2 = this.rand.nextGaussian() * 0.02;
                double d3 = this.rand.nextGaussian() * 0.02;
                this.world.addParticle(ParticleTypes.HEART, this.getPosXRandom(1.0), this.getPosYRandom() + 0.5, this.getPosZRandom(1.0), d, d2, d3);
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        this.inLove = 0;
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        return iWorldReader.getBlockState(blockPos.down()).isIn(Blocks.GRASS_BLOCK) ? 10.0f : iWorldReader.getBrightness(blockPos) - 0.5f;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("InLove", this.inLove);
        if (this.playerInLove != null) {
            compoundNBT.putUniqueId("LoveCause", this.playerInLove);
        }
    }

    @Override
    public double getYOffset() {
        return 0.14;
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.inLove = compoundNBT.getInt("InLove");
        this.playerInLove = compoundNBT.hasUniqueId("LoveCause") ? compoundNBT.getUniqueId("LoveCause") : null;
    }

    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return iWorld.getBlockState(blockPos.down()).isIn(Blocks.GRASS_BLOCK) && iWorld.getLightSubtracted(blockPos, 0) > 8;
    }

    @Override
    public int getTalkInterval() {
        return 1;
    }

    @Override
    public boolean canDespawn(double d) {
        return true;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity playerEntity) {
        return 1 + this.world.rand.nextInt(3);
    }

    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack.getItem() == Items.WHEAT;
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (this.isBreedingItem(itemStack)) {
            int n = this.getGrowingAge();
            if (!this.world.isRemote && n == 0 && this.canFallInLove()) {
                this.consumeItemFromStack(playerEntity, itemStack);
                this.setInLove(playerEntity);
                return ActionResultType.SUCCESS;
            }
            if (this.isChild()) {
                this.consumeItemFromStack(playerEntity, itemStack);
                this.ageUp((int)((float)(-n / 20) * 0.1f), false);
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            if (this.world.isRemote) {
                return ActionResultType.CONSUME;
            }
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    protected void consumeItemFromStack(PlayerEntity playerEntity, ItemStack itemStack) {
        if (!playerEntity.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }
    }

    public boolean canFallInLove() {
        return this.inLove <= 0;
    }

    public void setInLove(@Nullable PlayerEntity playerEntity) {
        this.inLove = 600;
        if (playerEntity != null) {
            this.playerInLove = playerEntity.getUniqueID();
        }
        this.world.setEntityState(this, (byte)18);
    }

    public void setInLove(int n) {
        this.inLove = n;
    }

    public int func_234178_eO_() {
        return this.inLove;
    }

    @Nullable
    public ServerPlayerEntity getLoveCause() {
        if (this.playerInLove == null) {
            return null;
        }
        PlayerEntity playerEntity = this.world.getPlayerByUuid(this.playerInLove);
        return playerEntity instanceof ServerPlayerEntity ? (ServerPlayerEntity)playerEntity : null;
    }

    public boolean isInLove() {
        return this.inLove > 0;
    }

    public void resetInLove() {
        this.inLove = 0;
    }

    public boolean canMateWith(AnimalEntity animalEntity) {
        if (animalEntity == this) {
            return true;
        }
        if (animalEntity.getClass() != this.getClass()) {
            return true;
        }
        return this.isInLove() && animalEntity.isInLove();
    }

    public void func_234177_a_(ServerWorld serverWorld, AnimalEntity animalEntity) {
        AgeableEntity ageableEntity = this.func_241840_a(serverWorld, animalEntity);
        if (ageableEntity != null) {
            ServerPlayerEntity serverPlayerEntity = this.getLoveCause();
            if (serverPlayerEntity == null && animalEntity.getLoveCause() != null) {
                serverPlayerEntity = animalEntity.getLoveCause();
            }
            if (serverPlayerEntity != null) {
                serverPlayerEntity.addStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayerEntity, this, animalEntity, ageableEntity);
            }
            this.setGrowingAge(6000);
            animalEntity.setGrowingAge(6000);
            this.resetInLove();
            animalEntity.resetInLove();
            ageableEntity.setChild(false);
            ageableEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0f, 0.0f);
            serverWorld.func_242417_l(ageableEntity);
            serverWorld.setEntityState(this, (byte)18);
            if (serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                serverWorld.addEntity(new ExperienceOrbEntity(serverWorld, this.getPosX(), this.getPosY(), this.getPosZ(), this.getRNG().nextInt(7) + 1));
            }
        }
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 18) {
            for (int i = 0; i < 7; ++i) {
                double d = this.rand.nextGaussian() * 0.02;
                double d2 = this.rand.nextGaussian() * 0.02;
                double d3 = this.rand.nextGaussian() * 0.02;
                this.world.addParticle(ParticleTypes.HEART, this.getPosXRandom(1.0), this.getPosYRandom() + 0.5, this.getPosZRandom(1.0), d, d2, d3);
            }
        } else {
            super.handleStatusUpdate(by);
        }
    }
}

