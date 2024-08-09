/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventDamageReceive;
import mpp.venusfr.venusfr;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class AbstractArrowEntity
extends ProjectileEntity {
    private static final DataParameter<Byte> CRITICAL = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> PIERCE_LEVEL = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BYTE);
    @Nullable
    private BlockState inBlockState;
    protected boolean inGround;
    protected int timeInGround;
    public PickupStatus pickupStatus = PickupStatus.DISALLOWED;
    public int arrowShake;
    private int ticksInGround;
    private double damage = 2.0;
    private int knockbackStrength;
    private SoundEvent hitSound = this.getHitEntitySound();
    private IntOpenHashSet piercedEntities;
    private List<Entity> hitEntities;

    protected AbstractArrowEntity(EntityType<? extends AbstractArrowEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
    }

    protected AbstractArrowEntity(EntityType<? extends AbstractArrowEntity> entityType, double d, double d2, double d3, World world) {
        this(entityType, world);
        this.setPosition(d, d2, d3);
    }

    protected AbstractArrowEntity(EntityType<? extends AbstractArrowEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getPosX(), livingEntity.getPosYEye() - (double)0.1f, livingEntity.getPosZ(), world);
        this.setShooter(livingEntity);
        if (livingEntity instanceof PlayerEntity) {
            this.pickupStatus = PickupStatus.ALLOWED;
        }
    }

    public void setHitSound(SoundEvent soundEvent) {
        this.hitSound = soundEvent;
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        double d2 = this.getBoundingBox().getAverageEdgeLength() * 10.0;
        if (Double.isNaN(d2)) {
            d2 = 1.0;
        }
        return d < (d2 = d2 * 64.0 * AbstractArrowEntity.getRenderDistanceWeight()) * d2;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(CRITICAL, (byte)0);
        this.dataManager.register(PIERCE_LEVEL, (byte)0);
    }

    @Override
    public void shoot(double d, double d2, double d3, float f, float f2) {
        super.shoot(d, d2, d3, f, f2);
        this.ticksInGround = 0;
    }

    @Override
    public void setPositionAndRotationDirect(double d, double d2, double d3, float f, float f2, int n, boolean bl) {
        this.setPosition(d, d2, d3);
        this.setRotation(f, f2);
    }

    @Override
    public void setVelocity(double d, double d2, double d3) {
        super.setVelocity(d, d2, d3);
        this.ticksInGround = 0;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void tick() {
        Vector3d vector3d;
        Object object;
        BlockPos blockPos;
        BlockState blockState;
        super.tick();
        boolean bl = this.getNoClip();
        Vector3d vector3d2 = this.getMotion();
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float f = MathHelper.sqrt(AbstractArrowEntity.horizontalMag(vector3d2));
            this.rotationYaw = (float)(MathHelper.atan2(vector3d2.x, vector3d2.z) * 57.2957763671875);
            this.rotationPitch = (float)(MathHelper.atan2(vector3d2.y, f) * 57.2957763671875);
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }
        if (!((blockState = this.world.getBlockState(blockPos = this.getPosition())).isAir() || bl || ((VoxelShape)(object = blockState.getCollisionShape(this.world, blockPos))).isEmpty())) {
            vector3d = this.getPositionVec();
            for (AxisAlignedBB object2 : ((VoxelShape)object).toBoundingBoxList()) {
                if (!object2.offset(blockPos).contains(vector3d)) continue;
                this.inGround = true;
                break;
            }
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.isWet()) {
            this.extinguish();
        }
        if (this.inGround && !bl) {
            if (this.inBlockState != blockState && this.func_234593_u_()) {
                this.func_234594_z_();
            } else if (!this.world.isRemote) {
                this.func_225516_i_();
            }
            ++this.timeInGround;
        } else {
            this.timeInGround = 0;
            object = this.getPositionVec();
            Object object3 = this.world.rayTraceBlocks(new RayTraceContext((Vector3d)object, vector3d = ((Vector3d)object).add(vector3d2), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (((RayTraceResult)object3).getType() != RayTraceResult.Type.MISS) {
                vector3d = ((RayTraceResult)object3).getHitVec();
            }
            while (!this.removed) {
                void var8_13;
                EntityRayTraceResult entityRayTraceResult = this.rayTraceEntities((Vector3d)object, vector3d);
                if (entityRayTraceResult != null) {
                    object3 = entityRayTraceResult;
                }
                if (object3 != null && ((RayTraceResult)object3).getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity2 = ((EntityRayTraceResult)object3).getEntity();
                    Entity entity3 = this.func_234616_v_();
                    if (entity2 instanceof PlayerEntity && entity3 instanceof PlayerEntity && !((PlayerEntity)entity3).canAttackPlayer((PlayerEntity)entity2)) {
                        object3 = null;
                        Object var8_12 = null;
                    }
                }
                if (object3 != null && !bl) {
                    if (var8_13 != null && ((RayTraceResult)object3).getType() == RayTraceResult.Type.ENTITY && var8_13.getEntity() instanceof ClientPlayerEntity) {
                        venusfr.getInstance().getEventBus().post(new EventDamageReceive(EventDamageReceive.DamageType.ARROW));
                    }
                    this.onImpact((RayTraceResult)object3);
                    this.isAirBorne = true;
                }
                if (var8_13 == null || this.getPierceLevel() <= 0) break;
                object3 = null;
            }
            vector3d2 = this.getMotion();
            double d = vector3d2.x;
            double d2 = vector3d2.y;
            double d3 = vector3d2.z;
            if (this.getIsCritical()) {
                for (int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.CRIT, this.getPosX() + d * (double)i / 4.0, this.getPosY() + d2 * (double)i / 4.0, this.getPosZ() + d3 * (double)i / 4.0, -d, -d2 + 0.2, -d3);
                }
            }
            double d4 = this.getPosX() + d;
            double d5 = this.getPosY() + d2;
            double d6 = this.getPosZ() + d3;
            float f = MathHelper.sqrt(AbstractArrowEntity.horizontalMag(vector3d2));
            this.rotationYaw = bl ? (float)(MathHelper.atan2(-d, -d3) * 57.2957763671875) : (float)(MathHelper.atan2(d, d3) * 57.2957763671875);
            this.rotationPitch = (float)(MathHelper.atan2(d2, f) * 57.2957763671875);
            this.rotationPitch = AbstractArrowEntity.func_234614_e_(this.prevRotationPitch, this.rotationPitch);
            this.rotationYaw = AbstractArrowEntity.func_234614_e_(this.prevRotationYaw, this.rotationYaw);
            float f2 = 0.99f;
            float f3 = 0.05f;
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    float f4 = 0.25f;
                    this.world.addParticle(ParticleTypes.BUBBLE, d4 - d * 0.25, d5 - d2 * 0.25, d6 - d3 * 0.25, d, d2, d3);
                }
                f2 = this.getWaterDrag();
            }
            this.setMotion(vector3d2.scale(f2));
            if (!this.hasNoGravity() && !bl) {
                Vector3d vector3d3 = this.getMotion();
                this.setMotion(vector3d3.x, vector3d3.y - (double)0.05f, vector3d3.z);
            }
            this.setPosition(d4, d5, d6);
            this.doBlockCollisions();
        }
    }

    private boolean func_234593_u_() {
        return this.inGround && this.world.hasNoCollisions(new AxisAlignedBB(this.getPositionVec(), this.getPositionVec()).grow(0.06));
    }

    private void func_234594_z_() {
        this.inGround = false;
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.mul(this.rand.nextFloat() * 0.2f, this.rand.nextFloat() * 0.2f, this.rand.nextFloat() * 0.2f));
        this.ticksInGround = 0;
    }

    @Override
    public void move(MoverType moverType, Vector3d vector3d) {
        super.move(moverType, vector3d);
        if (moverType != MoverType.SELF && this.func_234593_u_()) {
            this.func_234594_z_();
        }
    }

    protected void func_225516_i_() {
        ++this.ticksInGround;
        if (this.ticksInGround >= 1200) {
            this.remove();
        }
    }

    private void func_213870_w() {
        if (this.hitEntities != null) {
            this.hitEntities.clear();
        }
        if (this.piercedEntities != null) {
            this.piercedEntities.clear();
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        DamageSource damageSource;
        Entity entity2;
        super.onEntityHit(entityRayTraceResult);
        Entity entity3 = entityRayTraceResult.getEntity();
        float f = (float)this.getMotion().length();
        int n = MathHelper.ceil(MathHelper.clamp((double)f * this.damage, 0.0, 2.147483647E9));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }
            if (this.hitEntities == null) {
                this.hitEntities = Lists.newArrayListWithCapacity(5);
            }
            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }
            this.piercedEntities.add(entity3.getEntityId());
        }
        if (this.getIsCritical()) {
            long l = this.rand.nextInt(n / 2 + 2);
            n = (int)Math.min(l + (long)n, Integer.MAX_VALUE);
        }
        if ((entity2 = this.func_234616_v_()) == null) {
            damageSource = DamageSource.causeArrowDamage(this, this);
        } else {
            damageSource = DamageSource.causeArrowDamage(this, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).setLastAttackedEntity(entity3);
            }
        }
        boolean bl = entity3.getType() == EntityType.ENDERMAN;
        int n2 = entity3.getFireTimer();
        if (this.isBurning() && !bl) {
            entity3.setFire(5);
        }
        if (entity3.attackEntityFrom(damageSource, n)) {
            if (bl) {
                return;
            }
            if (entity3 instanceof LivingEntity) {
                Object object;
                LivingEntity livingEntity = (LivingEntity)entity3;
                if (!this.world.isRemote && this.getPierceLevel() <= 0) {
                    livingEntity.setArrowCountInEntity(livingEntity.getArrowCountInEntity() + 1);
                }
                if (this.knockbackStrength > 0 && ((Vector3d)(object = this.getMotion().mul(1.0, 0.0, 1.0).normalize().scale((double)this.knockbackStrength * 0.6))).lengthSquared() > 0.0) {
                    livingEntity.addVelocity(((Vector3d)object).x, 0.1, ((Vector3d)object).z);
                }
                if (!this.world.isRemote && entity2 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingEntity, entity2);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity2, livingEntity);
                }
                this.arrowHit(livingEntity);
                if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity2).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241770_g_, 0.0f));
                }
                if (!entity3.isAlive() && this.hitEntities != null) {
                    this.hitEntities.add(livingEntity);
                }
                if (!this.world.isRemote && entity2 instanceof ServerPlayerEntity) {
                    object = (ServerPlayerEntity)entity2;
                    if (this.hitEntities != null && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.test((ServerPlayerEntity)object, this.hitEntities);
                    } else if (!entity3.isAlive() && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.test((ServerPlayerEntity)object, Arrays.asList(entity3));
                    }
                }
            }
            this.playSound(this.hitSound, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity3.forceFireTicks(n2);
            this.setMotion(this.getMotion().scale(-0.1));
            this.rotationYaw += 180.0f;
            this.prevRotationYaw += 180.0f;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7) {
                if (this.pickupStatus == PickupStatus.ALLOWED) {
                    this.entityDropItem(this.getArrowStack(), 0.1f);
                }
                this.remove();
            }
        }
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        this.inBlockState = this.world.getBlockState(blockRayTraceResult.getPos());
        super.func_230299_a_(blockRayTraceResult);
        Vector3d vector3d = blockRayTraceResult.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
        this.setMotion(vector3d);
        Vector3d vector3d2 = vector3d.normalize().scale(0.05f);
        this.setRawPosition(this.getPosX() - vector3d2.x, this.getPosY() - vector3d2.y, this.getPosZ() - vector3d2.z);
        this.playSound(this.getHitGroundSound(), 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
        this.inGround = true;
        this.arrowShake = 7;
        this.setIsCritical(true);
        this.setPierceLevel((byte)0);
        this.setHitSound(SoundEvents.ENTITY_ARROW_HIT);
        this.setShotFromCrossbow(true);
        this.func_213870_w();
    }

    protected SoundEvent getHitEntitySound() {
        return SoundEvents.ENTITY_ARROW_HIT;
    }

    protected final SoundEvent getHitGroundSound() {
        return this.hitSound;
    }

    protected void arrowHit(LivingEntity livingEntity) {
    }

    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d vector3d, Vector3d vector3d2) {
        return ProjectileHelper.rayTraceEntities(this.world, this, vector3d, vector3d2, this.getBoundingBox().expand(this.getMotion()).grow(1.0), this::func_230298_a_);
    }

    @Override
    protected boolean func_230298_a_(Entity entity2) {
        return super.func_230298_a_(entity2) && (this.piercedEntities == null || !this.piercedEntities.contains(entity2.getEntityId()));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putShort("life", (short)this.ticksInGround);
        if (this.inBlockState != null) {
            compoundNBT.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
        }
        compoundNBT.putByte("shake", (byte)this.arrowShake);
        compoundNBT.putBoolean("inGround", this.inGround);
        compoundNBT.putByte("pickup", (byte)this.pickupStatus.ordinal());
        compoundNBT.putDouble("damage", this.damage);
        compoundNBT.putBoolean("crit", this.getIsCritical());
        compoundNBT.putByte("PierceLevel", this.getPierceLevel());
        compoundNBT.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.hitSound).toString());
        compoundNBT.putBoolean("ShotFromCrossbow", this.getShotFromCrossbow());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.ticksInGround = compoundNBT.getShort("life");
        if (compoundNBT.contains("inBlockState", 1)) {
            this.inBlockState = NBTUtil.readBlockState(compoundNBT.getCompound("inBlockState"));
        }
        this.arrowShake = compoundNBT.getByte("shake") & 0xFF;
        this.inGround = compoundNBT.getBoolean("inGround");
        if (compoundNBT.contains("damage", 0)) {
            this.damage = compoundNBT.getDouble("damage");
        }
        if (compoundNBT.contains("pickup", 0)) {
            this.pickupStatus = PickupStatus.getByOrdinal(compoundNBT.getByte("pickup"));
        } else if (compoundNBT.contains("player", 0)) {
            this.pickupStatus = compoundNBT.getBoolean("player") ? PickupStatus.ALLOWED : PickupStatus.DISALLOWED;
        }
        this.setIsCritical(compoundNBT.getBoolean("crit"));
        this.setPierceLevel(compoundNBT.getByte("PierceLevel"));
        if (compoundNBT.contains("SoundEvent", 1)) {
            this.hitSound = Registry.SOUND_EVENT.getOptional(new ResourceLocation(compoundNBT.getString("SoundEvent"))).orElse(this.getHitEntitySound());
        }
        this.setShotFromCrossbow(compoundNBT.getBoolean("ShotFromCrossbow"));
    }

    @Override
    public void setShooter(@Nullable Entity entity2) {
        super.setShooter(entity2);
        if (entity2 instanceof PlayerEntity) {
            this.pickupStatus = ((PlayerEntity)entity2).abilities.isCreativeMode ? PickupStatus.CREATIVE_ONLY : PickupStatus.ALLOWED;
        }
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        if (!this.world.isRemote && (this.inGround || this.getNoClip()) && this.arrowShake <= 0) {
            boolean bl;
            boolean bl2 = bl = this.pickupStatus == PickupStatus.ALLOWED || this.pickupStatus == PickupStatus.CREATIVE_ONLY && playerEntity.abilities.isCreativeMode || this.getNoClip() && this.func_234616_v_().getUniqueID() == playerEntity.getUniqueID();
            if (this.pickupStatus == PickupStatus.ALLOWED && !playerEntity.inventory.addItemStackToInventory(this.getArrowStack())) {
                bl = false;
            }
            if (bl) {
                playerEntity.onItemPickup(this, 1);
                this.remove();
            }
        }
    }

    protected abstract ItemStack getArrowStack();

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    public void setDamage(double d) {
        this.damage = d;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setKnockbackStrength(int n) {
        this.knockbackStrength = n;
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return true;
    }

    @Override
    protected float getEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.13f;
    }

    public void setIsCritical(boolean bl) {
        this.setArrowFlag(1, bl);
    }

    public void setPierceLevel(byte by) {
        this.dataManager.set(PIERCE_LEVEL, by);
    }

    private void setArrowFlag(int n, boolean bl) {
        byte by = this.dataManager.get(CRITICAL);
        if (bl) {
            this.dataManager.set(CRITICAL, (byte)(by | n));
        } else {
            this.dataManager.set(CRITICAL, (byte)(by & ~n));
        }
    }

    public boolean getIsCritical() {
        byte by = this.dataManager.get(CRITICAL);
        return (by & 1) != 0;
    }

    public boolean getShotFromCrossbow() {
        byte by = this.dataManager.get(CRITICAL);
        return (by & 4) != 0;
    }

    public byte getPierceLevel() {
        return this.dataManager.get(PIERCE_LEVEL);
    }

    public void setEnchantmentEffectsFromEntity(LivingEntity livingEntity, float f) {
        int n = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, livingEntity);
        int n2 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, livingEntity);
        this.setDamage((double)(f * 2.0f) + this.rand.nextGaussian() * 0.25 + (double)((float)this.world.getDifficulty().getId() * 0.11f));
        if (n > 0) {
            this.setDamage(this.getDamage() + (double)n * 0.5 + 0.5);
        }
        if (n2 > 0) {
            this.setKnockbackStrength(n2);
        }
        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, livingEntity) > 0) {
            this.setFire(100);
        }
    }

    protected float getWaterDrag() {
        return 0.6f;
    }

    public void setNoClip(boolean bl) {
        this.noClip = bl;
        this.setArrowFlag(2, bl);
    }

    public boolean getNoClip() {
        if (!this.world.isRemote) {
            return this.noClip;
        }
        return (this.dataManager.get(CRITICAL) & 2) != 0;
    }

    public void setShotFromCrossbow(boolean bl) {
        this.setArrowFlag(4, bl);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        Entity entity2 = this.func_234616_v_();
        return new SSpawnObjectPacket(this, entity2 == null ? 0 : entity2.getEntityId());
    }

    public static enum PickupStatus {
        DISALLOWED,
        ALLOWED,
        CREATIVE_ONLY;


        public static PickupStatus getByOrdinal(int n) {
            if (n < 0 || n > PickupStatus.values().length) {
                n = 0;
            }
            return PickupStatus.values()[n];
        }
    }
}

