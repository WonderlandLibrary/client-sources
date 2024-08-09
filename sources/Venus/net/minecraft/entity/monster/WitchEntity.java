/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetExpiringGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.ToggleableNearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class WitchEntity
extends AbstractRaiderEntity
implements IRangedAttackMob {
    private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier MODIFIER = new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25, AttributeModifier.Operation.ADDITION);
    private static final DataParameter<Boolean> IS_DRINKING = EntityDataManager.createKey(WitchEntity.class, DataSerializers.BOOLEAN);
    private int potionUseTimer;
    private NearestAttackableTargetExpiringGoal<AbstractRaiderEntity> field_213694_bC;
    private ToggleableNearestAttackableTargetGoal<PlayerEntity> field_213695_bD;

    public WitchEntity(EntityType<? extends WitchEntity> entityType, World world) {
        super((EntityType<? extends AbstractRaiderEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.field_213694_bC = new NearestAttackableTargetExpiringGoal<AbstractRaiderEntity>(this, AbstractRaiderEntity.class, true, this::lambda$registerGoals$0);
        this.field_213695_bD = new ToggleableNearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, (Predicate<LivingEntity>)null);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 60, 10.0f));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractRaiderEntity.class));
        this.targetSelector.addGoal(2, this.field_213694_bC);
        this.targetSelector.addGoal(3, this.field_213695_bD);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(IS_DRINKING, false);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITCH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_WITCH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITCH_DEATH;
    }

    public void setDrinkingPotion(boolean bl) {
        this.getDataManager().set(IS_DRINKING, bl);
    }

    public boolean isDrinkingPotion() {
        return this.getDataManager().get(IS_DRINKING);
    }

    public static AttributeModifierMap.MutableAttribute func_234323_eI_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 26.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote && this.isAlive()) {
            this.field_213694_bC.tickCooldown();
            if (this.field_213694_bC.getCooldown() <= 0) {
                this.field_213695_bD.func_220783_a(false);
            } else {
                this.field_213695_bD.func_220783_a(true);
            }
            if (this.isDrinkingPotion()) {
                if (this.potionUseTimer-- <= 0) {
                    List<EffectInstance> list;
                    this.setDrinkingPotion(true);
                    ItemStack itemStack = this.getHeldItemMainhand();
                    this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
                    if (itemStack.getItem() == Items.POTION && (list = PotionUtils.getEffectsFromStack(itemStack)) != null) {
                        for (EffectInstance effectInstance : list) {
                            this.addPotionEffect(new EffectInstance(effectInstance));
                        }
                    }
                    this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MODIFIER);
                }
            } else {
                Potion potion = null;
                if (this.rand.nextFloat() < 0.15f && this.areEyesInFluid(FluidTags.WATER) && !this.isPotionActive(Effects.WATER_BREATHING)) {
                    potion = Potions.WATER_BREATHING;
                } else if (this.rand.nextFloat() < 0.15f && (this.isBurning() || this.getLastDamageSource() != null && this.getLastDamageSource().isFireDamage()) && !this.isPotionActive(Effects.FIRE_RESISTANCE)) {
                    potion = Potions.FIRE_RESISTANCE;
                } else if (this.rand.nextFloat() < 0.05f && this.getHealth() < this.getMaxHealth()) {
                    potion = Potions.HEALING;
                } else if (this.rand.nextFloat() < 0.5f && this.getAttackTarget() != null && !this.isPotionActive(Effects.SPEED) && this.getAttackTarget().getDistanceSq(this) > 121.0) {
                    potion = Potions.SWIFTNESS;
                }
                if (potion != null) {
                    this.setItemStackToSlot(EquipmentSlotType.MAINHAND, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potion));
                    this.potionUseTimer = this.getHeldItemMainhand().getUseDuration();
                    this.setDrinkingPotion(false);
                    if (!this.isSilent()) {
                        this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_WITCH_DRINK, this.getSoundCategory(), 1.0f, 0.8f + this.rand.nextFloat() * 0.4f);
                    }
                    ModifiableAttributeInstance modifiableAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                    modifiableAttributeInstance.removeModifier(MODIFIER);
                    modifiableAttributeInstance.applyNonPersistentModifier(MODIFIER);
                }
            }
            if (this.rand.nextFloat() < 7.5E-4f) {
                this.world.setEntityState(this, (byte)15);
            }
        }
        super.livingTick();
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_WITCH_CELEBRATE;
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 15) {
            for (int i = 0; i < this.rand.nextInt(35) + 10; ++i) {
                this.world.addParticle(ParticleTypes.WITCH, this.getPosX() + this.rand.nextGaussian() * (double)0.13f, this.getBoundingBox().maxY + 0.5 + this.rand.nextGaussian() * (double)0.13f, this.getPosZ() + this.rand.nextGaussian() * (double)0.13f, 0.0, 0.0, 0.0);
            }
        } else {
            super.handleStatusUpdate(by);
        }
    }

    @Override
    protected float applyPotionDamageCalculations(DamageSource damageSource, float f) {
        f = super.applyPotionDamageCalculations(damageSource, f);
        if (damageSource.getTrueSource() == this) {
            f = 0.0f;
        }
        if (damageSource.isMagicDamage()) {
            f = (float)((double)f * 0.15);
        }
        return f;
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f) {
        if (!this.isDrinkingPotion()) {
            Vector3d vector3d = livingEntity.getMotion();
            double d = livingEntity.getPosX() + vector3d.x - this.getPosX();
            double d2 = livingEntity.getPosYEye() - (double)1.1f - this.getPosY();
            double d3 = livingEntity.getPosZ() + vector3d.z - this.getPosZ();
            float f2 = MathHelper.sqrt(d * d + d3 * d3);
            Potion potion = Potions.HARMING;
            if (livingEntity instanceof AbstractRaiderEntity) {
                potion = livingEntity.getHealth() <= 4.0f ? Potions.HEALING : Potions.REGENERATION;
                this.setAttackTarget(null);
            } else if (f2 >= 8.0f && !livingEntity.isPotionActive(Effects.SLOWNESS)) {
                potion = Potions.SLOWNESS;
            } else if (livingEntity.getHealth() >= 8.0f && !livingEntity.isPotionActive(Effects.POISON)) {
                potion = Potions.POISON;
            } else if (f2 <= 3.0f && !livingEntity.isPotionActive(Effects.WEAKNESS) && this.rand.nextFloat() < 0.25f) {
                potion = Potions.WEAKNESS;
            }
            PotionEntity potionEntity = new PotionEntity(this.world, this);
            potionEntity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potion));
            potionEntity.rotationPitch -= -20.0f;
            potionEntity.shoot(d, d2 + (double)(f2 * 0.2f), d3, 0.75f, 8.0f);
            if (!this.isSilent()) {
                this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0f, 0.8f + this.rand.nextFloat() * 0.4f);
            }
            this.world.addEntity(potionEntity);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 1.62f;
    }

    @Override
    public void applyWaveBonus(int n, boolean bl) {
    }

    @Override
    public boolean canBeLeader() {
        return true;
    }

    private boolean lambda$registerGoals$0(LivingEntity livingEntity) {
        return livingEntity != null && this.isRaidActive() && livingEntity.getType() != EntityType.WITCH;
    }
}

