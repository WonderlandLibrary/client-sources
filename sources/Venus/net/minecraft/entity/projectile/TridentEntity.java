/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TridentEntity
extends AbstractArrowEntity {
    private static final DataParameter<Byte> LOYALTY_LEVEL = EntityDataManager.createKey(TridentEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> field_226571_aq_ = EntityDataManager.createKey(TridentEntity.class, DataSerializers.BOOLEAN);
    private ItemStack thrownStack = new ItemStack(Items.TRIDENT);
    private boolean dealtDamage;
    public int returningTicks;

    public TridentEntity(EntityType<? extends TridentEntity> entityType, World world) {
        super((EntityType<? extends AbstractArrowEntity>)entityType, world);
    }

    public TridentEntity(World world, LivingEntity livingEntity, ItemStack itemStack) {
        super(EntityType.TRIDENT, livingEntity, world);
        this.thrownStack = itemStack.copy();
        this.dataManager.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyaltyModifier(itemStack));
        this.dataManager.set(field_226571_aq_, itemStack.hasEffect());
    }

    public TridentEntity(World world, double d, double d2, double d3) {
        super(EntityType.TRIDENT, d, d2, d3, world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LOYALTY_LEVEL, (byte)0);
        this.dataManager.register(field_226571_aq_, false);
    }

    @Override
    public void tick() {
        if (this.timeInGround > 4) {
            this.dealtDamage = true;
        }
        Entity entity2 = this.func_234616_v_();
        if ((this.dealtDamage || this.getNoClip()) && entity2 != null) {
            byte by = this.dataManager.get(LOYALTY_LEVEL);
            if (by > 0 && !this.shouldReturnToThrower()) {
                if (!this.world.isRemote && this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    this.entityDropItem(this.getArrowStack(), 0.1f);
                }
                this.remove();
            } else if (by > 0) {
                this.setNoClip(false);
                Vector3d vector3d = new Vector3d(entity2.getPosX() - this.getPosX(), entity2.getPosYEye() - this.getPosY(), entity2.getPosZ() - this.getPosZ());
                this.setRawPosition(this.getPosX(), this.getPosY() + vector3d.y * 0.015 * (double)by, this.getPosZ());
                if (this.world.isRemote) {
                    this.lastTickPosY = this.getPosY();
                }
                double d = 0.05 * (double)by;
                this.setMotion(this.getMotion().scale(0.95).add(vector3d.normalize().scale(d)));
                if (this.returningTicks == 0) {
                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0f, 1.0f);
                }
                ++this.returningTicks;
            }
        }
        super.tick();
    }

    private boolean shouldReturnToThrower() {
        Entity entity2 = this.func_234616_v_();
        if (entity2 != null && entity2.isAlive()) {
            return !(entity2 instanceof ServerPlayerEntity) || !entity2.isSpectator();
        }
        return true;
    }

    @Override
    protected ItemStack getArrowStack() {
        return this.thrownStack.copy();
    }

    public boolean func_226572_w_() {
        return this.dataManager.get(field_226571_aq_);
    }

    @Override
    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d vector3d, Vector3d vector3d2) {
        return this.dealtDamage ? null : super.rayTraceEntities(vector3d, vector3d2);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        BlockPos blockPos;
        Entity entity2;
        Entity entity3 = entityRayTraceResult.getEntity();
        float f = 8.0f;
        if (entity3 instanceof LivingEntity) {
            entity2 = (LivingEntity)entity3;
            f += EnchantmentHelper.getModifierForCreature(this.thrownStack, ((LivingEntity)entity2).getCreatureAttribute());
        }
        DamageSource damageSource = DamageSource.causeTridentDamage(this, (entity2 = this.func_234616_v_()) == null ? this : entity2);
        this.dealtDamage = true;
        SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;
        if (entity3.attackEntityFrom(damageSource, f)) {
            if (entity3.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (entity3 instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity3;
                if (entity2 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingEntity, entity2);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity2, livingEntity);
                }
                this.arrowHit(livingEntity);
            }
        }
        this.setMotion(this.getMotion().mul(-0.01, -0.1, -0.01));
        float f2 = 1.0f;
        if (this.world instanceof ServerWorld && this.world.isThundering() && EnchantmentHelper.hasChanneling(this.thrownStack) && this.world.canSeeSky(blockPos = entity3.getPosition())) {
            LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(this.world);
            lightningBoltEntity.moveForced(Vector3d.copyCenteredHorizontally(blockPos));
            lightningBoltEntity.setCaster(entity2 instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity2 : null);
            this.world.addEntity(lightningBoltEntity);
            soundEvent = SoundEvents.ITEM_TRIDENT_THUNDER;
            f2 = 5.0f;
        }
        this.playSound(soundEvent, f2, 1.0f);
    }

    @Override
    protected SoundEvent getHitEntitySound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        Entity entity2 = this.func_234616_v_();
        if (entity2 == null || entity2.getUniqueID() == playerEntity.getUniqueID()) {
            super.onCollideWithPlayer(playerEntity);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("Trident", 1)) {
            this.thrownStack = ItemStack.read(compoundNBT.getCompound("Trident"));
        }
        this.dealtDamage = compoundNBT.getBoolean("DealtDamage");
        this.dataManager.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyaltyModifier(this.thrownStack));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.put("Trident", this.thrownStack.write(new CompoundNBT()));
        compoundNBT.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void func_225516_i_() {
        byte by = this.dataManager.get(LOYALTY_LEVEL);
        if (this.pickupStatus != AbstractArrowEntity.PickupStatus.ALLOWED || by <= 0) {
            super.func_225516_i_();
        }
    }

    @Override
    protected float getWaterDrag() {
        return 0.99f;
    }

    @Override
    public boolean isInRangeToRender3d(double d, double d2, double d3) {
        return false;
    }
}

