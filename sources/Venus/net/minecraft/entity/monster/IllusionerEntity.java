/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpellcastingIllagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class IllusionerEntity
extends SpellcastingIllagerEntity
implements IRangedAttackMob {
    private int ghostTime;
    private final Vector3d[][] renderLocations;

    public IllusionerEntity(EntityType<? extends IllusionerEntity> entityType, World world) {
        super((EntityType<? extends SpellcastingIllagerEntity>)entityType, world);
        this.experienceValue = 5;
        this.renderLocations = new Vector3d[2][4];
        for (int i = 0; i < 4; ++i) {
            this.renderLocations[0][i] = Vector3d.ZERO;
            this.renderLocations[1][i] = Vector3d.ZERO;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SpellcastingIllagerEntity.CastingASpellGoal(this));
        this.goalSelector.addGoal(4, new MirrorSpellGoal(this));
        this.goalSelector.addGoal(5, new BlindnessSpellGoal(this));
        this.goalSelector.addGoal(6, new RangedBowAttackGoal<IllusionerEntity>(this, 0.5, 20, 15.0f));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractRaiderEntity.class).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillagerEntity>((MobEntity)this, AbstractVillagerEntity.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, false).setUnseenMemoryTicks(300));
    }

    public static AttributeModifierMap.MutableAttribute func_234293_eI_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5).createMutableAttribute(Attributes.FOLLOW_RANGE, 18.0).createMutableAttribute(Attributes.MAX_HEALTH, 32.0);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return this.getBoundingBox().grow(3.0, 0.0, 3.0);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote && this.isInvisible()) {
            --this.ghostTime;
            if (this.ghostTime < 0) {
                this.ghostTime = 0;
            }
            if (this.hurtTime != 1 && this.ticksExisted % 1200 != 0) {
                if (this.hurtTime == this.maxHurtTime - 1) {
                    this.ghostTime = 3;
                    for (int i = 0; i < 4; ++i) {
                        this.renderLocations[0][i] = this.renderLocations[1][i];
                        this.renderLocations[1][i] = new Vector3d(0.0, 0.0, 0.0);
                    }
                }
            } else {
                int n;
                this.ghostTime = 3;
                float f = -6.0f;
                int n2 = 13;
                for (n = 0; n < 4; ++n) {
                    this.renderLocations[0][n] = this.renderLocations[1][n];
                    this.renderLocations[1][n] = new Vector3d((double)(-6.0f + (float)this.rand.nextInt(13)) * 0.5, Math.max(0, this.rand.nextInt(6) - 4), (double)(-6.0f + (float)this.rand.nextInt(13)) * 0.5);
                }
                for (n = 0; n < 16; ++n) {
                    this.world.addParticle(ParticleTypes.CLOUD, this.getPosXRandom(0.5), this.getPosYRandom(), this.getPosZWidth(0.5), 0.0, 0.0, 0.0);
                }
                this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ILLUSIONER_MIRROR_MOVE, this.getSoundCategory(), 1.0f, 1.0f, true);
            }
        }
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    public Vector3d[] getRenderLocations(float f) {
        if (this.ghostTime <= 0) {
            return this.renderLocations[1];
        }
        double d = ((float)this.ghostTime - f) / 3.0f;
        d = Math.pow(d, 0.25);
        Vector3d[] vector3dArray = new Vector3d[4];
        for (int i = 0; i < 4; ++i) {
            vector3dArray[i] = this.renderLocations[1][i].scale(1.0 - d).add(this.renderLocations[0][i].scale(d));
        }
        return vector3dArray;
    }

    @Override
    public boolean isOnSameTeam(Entity entity2) {
        if (super.isOnSameTeam(entity2)) {
            return false;
        }
        if (entity2 instanceof LivingEntity && ((LivingEntity)entity2).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entity2.getTeam() == null;
        }
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ILLUSIONER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ILLUSIONER_HURT;
    }

    @Override
    protected SoundEvent getSpellSound() {
        return SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL;
    }

    @Override
    public void applyWaveBonus(int n, boolean bl) {
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f) {
        ItemStack itemStack = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, Items.BOW)));
        AbstractArrowEntity abstractArrowEntity = ProjectileHelper.fireArrow(this, itemStack, f);
        double d = livingEntity.getPosX() - this.getPosX();
        double d2 = livingEntity.getPosYHeight(0.3333333333333333) - abstractArrowEntity.getPosY();
        double d3 = livingEntity.getPosZ() - this.getPosZ();
        double d4 = MathHelper.sqrt(d * d + d3 * d3);
        abstractArrowEntity.shoot(d, d2 + d4 * (double)0.2f, d3, 1.6f, 14 - this.world.getDifficulty().getId() * 4);
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.world.addEntity(abstractArrowEntity);
    }

    @Override
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isSpellcasting()) {
            return AbstractIllagerEntity.ArmPose.SPELLCASTING;
        }
        return this.isAggressive() ? AbstractIllagerEntity.ArmPose.BOW_AND_ARROW : AbstractIllagerEntity.ArmPose.CROSSED;
    }

    class MirrorSpellGoal
    extends SpellcastingIllagerEntity.UseSpellGoal {
        final IllusionerEntity this$0;

        private MirrorSpellGoal(IllusionerEntity illusionerEntity) {
            this.this$0 = illusionerEntity;
            super(illusionerEntity);
        }

        @Override
        public boolean shouldExecute() {
            if (!super.shouldExecute()) {
                return true;
            }
            return !this.this$0.isPotionActive(Effects.INVISIBILITY);
        }

        @Override
        protected int getCastingTime() {
            return 1;
        }

        @Override
        protected int getCastingInterval() {
            return 1;
        }

        @Override
        protected void castSpell() {
            this.this$0.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 1200));
        }

        @Override
        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_ILLUSIONER_PREPARE_MIRROR;
        }

        @Override
        protected SpellcastingIllagerEntity.SpellType getSpellType() {
            return SpellcastingIllagerEntity.SpellType.DISAPPEAR;
        }
    }

    class BlindnessSpellGoal
    extends SpellcastingIllagerEntity.UseSpellGoal {
        private int lastTargetId;
        final IllusionerEntity this$0;

        private BlindnessSpellGoal(IllusionerEntity illusionerEntity) {
            this.this$0 = illusionerEntity;
            super(illusionerEntity);
        }

        @Override
        public boolean shouldExecute() {
            if (!super.shouldExecute()) {
                return true;
            }
            if (this.this$0.getAttackTarget() == null) {
                return true;
            }
            if (this.this$0.getAttackTarget().getEntityId() == this.lastTargetId) {
                return true;
            }
            return this.this$0.world.getDifficultyForLocation(this.this$0.getPosition()).isHarderThan(Difficulty.NORMAL.ordinal());
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.lastTargetId = this.this$0.getAttackTarget().getEntityId();
        }

        @Override
        protected int getCastingTime() {
            return 1;
        }

        @Override
        protected int getCastingInterval() {
            return 1;
        }

        @Override
        protected void castSpell() {
            this.this$0.getAttackTarget().addPotionEffect(new EffectInstance(Effects.BLINDNESS, 400));
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS;
        }

        @Override
        protected SpellcastingIllagerEntity.SpellType getSpellType() {
            return SpellcastingIllagerEntity.SpellType.BLINDNESS;
        }
    }
}

