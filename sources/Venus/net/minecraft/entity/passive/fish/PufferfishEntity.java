/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.fish;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class PufferfishEntity
extends AbstractFishEntity {
    private static final DataParameter<Integer> PUFF_STATE = EntityDataManager.createKey(PufferfishEntity.class, DataSerializers.VARINT);
    private int puffTimer;
    private int deflateTimer;
    private static final Predicate<LivingEntity> ENEMY_MATCHER = PufferfishEntity::lambda$static$0;

    public PufferfishEntity(EntityType<? extends PufferfishEntity> entityType, World world) {
        super((EntityType<? extends AbstractFishEntity>)entityType, world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(PUFF_STATE, 0);
    }

    public int getPuffState() {
        return this.dataManager.get(PUFF_STATE);
    }

    public void setPuffState(int n) {
        this.dataManager.set(PUFF_STATE, n);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (PUFF_STATE.equals(dataParameter)) {
            this.recalculateSize();
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("PuffState", this.getPuffState());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setPuffState(compoundNBT.getInt("PuffState"));
    }

    @Override
    protected ItemStack getFishBucket() {
        return new ItemStack(Items.PUFFERFISH_BUCKET);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PuffGoal(this));
    }

    @Override
    public void tick() {
        if (!this.world.isRemote && this.isAlive() && this.isServerWorld()) {
            if (this.puffTimer > 0) {
                if (this.getPuffState() == 0) {
                    this.playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_UP, this.getSoundVolume(), this.getSoundPitch());
                    this.setPuffState(1);
                } else if (this.puffTimer > 40 && this.getPuffState() == 1) {
                    this.playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_UP, this.getSoundVolume(), this.getSoundPitch());
                    this.setPuffState(2);
                }
                ++this.puffTimer;
            } else if (this.getPuffState() != 0) {
                if (this.deflateTimer > 60 && this.getPuffState() == 2) {
                    this.playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_OUT, this.getSoundVolume(), this.getSoundPitch());
                    this.setPuffState(1);
                } else if (this.deflateTimer > 100 && this.getPuffState() == 1) {
                    this.playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_OUT, this.getSoundVolume(), this.getSoundPitch());
                    this.setPuffState(0);
                }
                ++this.deflateTimer;
            }
        }
        super.tick();
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.isAlive() && this.getPuffState() > 0) {
            for (MobEntity mobEntity : this.world.getEntitiesWithinAABB(MobEntity.class, this.getBoundingBox().grow(0.3), ENEMY_MATCHER)) {
                if (!mobEntity.isAlive()) continue;
                this.attack(mobEntity);
            }
        }
    }

    private void attack(MobEntity mobEntity) {
        int n = this.getPuffState();
        if (mobEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 1 + n)) {
            mobEntity.addPotionEffect(new EffectInstance(Effects.POISON, 60 * n, 0));
            this.playSound(SoundEvents.ENTITY_PUFFER_FISH_STING, 1.0f, 1.0f);
        }
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        int n = this.getPuffState();
        if (playerEntity instanceof ServerPlayerEntity && n > 0 && playerEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 1 + n)) {
            if (!this.isSilent()) {
                ((ServerPlayerEntity)playerEntity).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241773_j_, 0.0f));
            }
            playerEntity.addPotionEffect(new EffectInstance(Effects.POISON, 60 * n, 0));
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PUFFER_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PUFFER_FISH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_PUFFER_FISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_PUFFER_FISH_FLOP;
    }

    @Override
    public EntitySize getSize(Pose pose) {
        return super.getSize(pose).scale(PufferfishEntity.getPuffSize(this.getPuffState()));
    }

    private static float getPuffSize(int n) {
        switch (n) {
            case 0: {
                return 0.5f;
            }
            case 1: {
                return 0.7f;
            }
        }
        return 1.0f;
    }

    private static boolean lambda$static$0(LivingEntity livingEntity) {
        if (livingEntity == null) {
            return true;
        }
        if (!(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative()) {
            return livingEntity.getCreatureAttribute() != CreatureAttribute.WATER;
        }
        return true;
    }

    static class PuffGoal
    extends Goal {
        private final PufferfishEntity fish;

        public PuffGoal(PufferfishEntity pufferfishEntity) {
            this.fish = pufferfishEntity;
        }

        @Override
        public boolean shouldExecute() {
            List<LivingEntity> list = this.fish.world.getEntitiesWithinAABB(LivingEntity.class, this.fish.getBoundingBox().grow(2.0), ENEMY_MATCHER);
            return !list.isEmpty();
        }

        @Override
        public void startExecuting() {
            this.fish.puffTimer = 1;
            this.fish.deflateTimer = 0;
        }

        @Override
        public void resetTask() {
            this.fish.puffTimer = 0;
        }

        @Override
        public boolean shouldContinueExecuting() {
            List<LivingEntity> list = this.fish.world.getEntitiesWithinAABB(LivingEntity.class, this.fish.getBoundingBox().grow(2.0), ENEMY_MATCHER);
            return !list.isEmpty();
        }
    }
}

