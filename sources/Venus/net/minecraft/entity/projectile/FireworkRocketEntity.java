/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FireworkRocketEntity
extends ProjectileEntity
implements IRendersAsItem {
    private static final DataParameter<ItemStack> FIREWORK_ITEM = EntityDataManager.createKey(FireworkRocketEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<OptionalInt> BOOSTED_ENTITY_ID = EntityDataManager.createKey(FireworkRocketEntity.class, DataSerializers.OPTIONAL_VARINT);
    private static final DataParameter<Boolean> field_213895_d = EntityDataManager.createKey(FireworkRocketEntity.class, DataSerializers.BOOLEAN);
    private int fireworkAge;
    private int lifetime;
    private LivingEntity boostedEntity;

    public FireworkRocketEntity(EntityType<? extends FireworkRocketEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
    }

    public FireworkRocketEntity(World world, double d, double d2, double d3, ItemStack itemStack) {
        super((EntityType<? extends ProjectileEntity>)EntityType.FIREWORK_ROCKET, world);
        this.fireworkAge = 0;
        this.setPosition(d, d2, d3);
        int n = 1;
        if (!itemStack.isEmpty() && itemStack.hasTag()) {
            this.dataManager.set(FIREWORK_ITEM, itemStack.copy());
            n += itemStack.getOrCreateChildTag("Fireworks").getByte("Flight");
        }
        this.setMotion(this.rand.nextGaussian() * 0.001, 0.05, this.rand.nextGaussian() * 0.001);
        this.lifetime = 10 * n + this.rand.nextInt(6) + this.rand.nextInt(7);
    }

    public FireworkRocketEntity(World world, @Nullable Entity entity2, double d, double d2, double d3, ItemStack itemStack) {
        this(world, d, d2, d3, itemStack);
        this.setShooter(entity2);
    }

    public FireworkRocketEntity(World world, ItemStack itemStack, LivingEntity livingEntity) {
        this(world, livingEntity, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), itemStack);
        this.dataManager.set(BOOSTED_ENTITY_ID, OptionalInt.of(livingEntity.getEntityId()));
        this.boostedEntity = livingEntity;
    }

    public FireworkRocketEntity(World world, ItemStack itemStack, double d, double d2, double d3, boolean bl) {
        this(world, d, d2, d3, itemStack);
        this.dataManager.set(field_213895_d, bl);
    }

    public FireworkRocketEntity(World world, ItemStack itemStack, Entity entity2, double d, double d2, double d3, boolean bl) {
        this(world, itemStack, d, d2, d3, bl);
        this.setShooter(entity2);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(FIREWORK_ITEM, ItemStack.EMPTY);
        this.dataManager.register(BOOSTED_ENTITY_ID, OptionalInt.empty());
        this.dataManager.register(field_213895_d, false);
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        return d < 4096.0 && !this.isAttachedToEntity();
    }

    @Override
    public boolean isInRangeToRender3d(double d, double d2, double d3) {
        return super.isInRangeToRender3d(d, d2, d3) && !this.isAttachedToEntity();
    }

    @Override
    public void tick() {
        Object object;
        super.tick();
        if (this.isAttachedToEntity()) {
            if (this.boostedEntity == null) {
                this.dataManager.get(BOOSTED_ENTITY_ID).ifPresent(this::lambda$tick$0);
            }
            if (this.boostedEntity != null) {
                if (this.boostedEntity.isElytraFlying()) {
                    object = this.boostedEntity.getLookVec();
                    double d = 1.5;
                    double d2 = 0.1;
                    Vector3d vector3d = this.boostedEntity.getMotion();
                    this.boostedEntity.setMotion(vector3d.add(((Vector3d)object).x * 0.1 + (((Vector3d)object).x * 1.5 - vector3d.x) * 0.5, ((Vector3d)object).y * 0.1 + (((Vector3d)object).y * 1.5 - vector3d.y) * 0.5, ((Vector3d)object).z * 0.1 + (((Vector3d)object).z * 1.5 - vector3d.z) * 0.5));
                }
                this.setPosition(this.boostedEntity.getPosX(), this.boostedEntity.getPosY(), this.boostedEntity.getPosZ());
                this.setMotion(this.boostedEntity.getMotion());
            }
        } else {
            if (!this.func_213889_i()) {
                double d = this.collidedHorizontally ? 1.0 : 1.15;
                this.setMotion(this.getMotion().mul(d, 1.0, d).add(0.0, 0.04, 0.0));
            }
            object = this.getMotion();
            this.move(MoverType.SELF, (Vector3d)object);
            this.setMotion((Vector3d)object);
        }
        object = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
        if (!this.noClip) {
            this.onImpact((RayTraceResult)object);
            this.isAirBorne = true;
        }
        this.func_234617_x_();
        if (this.fireworkAge == 0 && !this.isSilent()) {
            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.AMBIENT, 3.0f, 1.0f);
        }
        ++this.fireworkAge;
        if (this.world.isRemote && this.fireworkAge % 2 < 2) {
            this.world.addParticle(ParticleTypes.FIREWORK, this.getPosX(), this.getPosY() - 0.3, this.getPosZ(), this.rand.nextGaussian() * 0.05, -this.getMotion().y * 0.5, this.rand.nextGaussian() * 0.05);
        }
        if (!this.world.isRemote && this.fireworkAge > this.lifetime) {
            this.func_213893_k();
        }
    }

    private void func_213893_k() {
        this.world.setEntityState(this, (byte)17);
        this.dealExplosionDamage();
        this.remove();
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        if (!this.world.isRemote) {
            this.func_213893_k();
        }
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        BlockPos blockPos = new BlockPos(blockRayTraceResult.getPos());
        this.world.getBlockState(blockPos).onEntityCollision(this.world, blockPos, this);
        if (!this.world.isRemote() && this.func_213894_l()) {
            this.func_213893_k();
        }
        super.func_230299_a_(blockRayTraceResult);
    }

    private boolean func_213894_l() {
        ItemStack itemStack = this.dataManager.get(FIREWORK_ITEM);
        CompoundNBT compoundNBT = itemStack.isEmpty() ? null : itemStack.getChildTag("Fireworks");
        ListNBT listNBT = compoundNBT != null ? compoundNBT.getList("Explosions", 10) : null;
        return listNBT != null && !listNBT.isEmpty();
    }

    private void dealExplosionDamage() {
        ListNBT listNBT;
        float f = 0.0f;
        ItemStack itemStack = this.dataManager.get(FIREWORK_ITEM);
        CompoundNBT compoundNBT = itemStack.isEmpty() ? null : itemStack.getChildTag("Fireworks");
        ListNBT listNBT2 = listNBT = compoundNBT != null ? compoundNBT.getList("Explosions", 10) : null;
        if (listNBT != null && !listNBT.isEmpty()) {
            f = 5.0f + (float)(listNBT.size() * 2);
        }
        if (f > 0.0f) {
            if (this.boostedEntity != null) {
                this.boostedEntity.attackEntityFrom(DamageSource.func_233548_a_(this, this.func_234616_v_()), 5.0f + (float)(listNBT.size() * 2));
            }
            double d = 5.0;
            Vector3d vector3d = this.getPositionVec();
            for (LivingEntity livingEntity : this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(5.0))) {
                if (livingEntity == this.boostedEntity || this.getDistanceSq(livingEntity) > 25.0) continue;
                boolean bl = false;
                for (int i = 0; i < 2; ++i) {
                    Vector3d vector3d2 = new Vector3d(livingEntity.getPosX(), livingEntity.getPosYHeight(0.5 * (double)i), livingEntity.getPosZ());
                    BlockRayTraceResult blockRayTraceResult = this.world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
                    if (((RayTraceResult)blockRayTraceResult).getType() != RayTraceResult.Type.MISS) continue;
                    bl = true;
                    break;
                }
                if (!bl) continue;
                float f2 = f * (float)Math.sqrt((5.0 - (double)this.getDistance(livingEntity)) / 5.0);
                livingEntity.attackEntityFrom(DamageSource.func_233548_a_(this, this.func_234616_v_()), f2);
            }
        }
    }

    private boolean isAttachedToEntity() {
        return this.dataManager.get(BOOSTED_ENTITY_ID).isPresent();
    }

    public boolean func_213889_i() {
        return this.dataManager.get(field_213895_d);
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 17 && this.world.isRemote) {
            if (!this.func_213894_l()) {
                for (int i = 0; i < this.rand.nextInt(3) + 2; ++i) {
                    this.world.addParticle(ParticleTypes.POOF, this.getPosX(), this.getPosY(), this.getPosZ(), this.rand.nextGaussian() * 0.05, 0.005, this.rand.nextGaussian() * 0.05);
                }
            } else {
                ItemStack itemStack = this.dataManager.get(FIREWORK_ITEM);
                CompoundNBT compoundNBT = itemStack.isEmpty() ? null : itemStack.getChildTag("Fireworks");
                Vector3d vector3d = this.getMotion();
                this.world.makeFireworks(this.getPosX(), this.getPosY(), this.getPosZ(), vector3d.x, vector3d.y, vector3d.z, compoundNBT);
            }
        }
        super.handleStatusUpdate(by);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Life", this.fireworkAge);
        compoundNBT.putInt("LifeTime", this.lifetime);
        ItemStack itemStack = this.dataManager.get(FIREWORK_ITEM);
        if (!itemStack.isEmpty()) {
            compoundNBT.put("FireworksItem", itemStack.write(new CompoundNBT()));
        }
        compoundNBT.putBoolean("ShotAtAngle", this.dataManager.get(field_213895_d));
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.fireworkAge = compoundNBT.getInt("Life");
        this.lifetime = compoundNBT.getInt("LifeTime");
        ItemStack itemStack = ItemStack.read(compoundNBT.getCompound("FireworksItem"));
        if (!itemStack.isEmpty()) {
            this.dataManager.set(FIREWORK_ITEM, itemStack);
        }
        if (compoundNBT.contains("ShotAtAngle")) {
            this.dataManager.set(field_213895_d, compoundNBT.getBoolean("ShotAtAngle"));
        }
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.dataManager.get(FIREWORK_ITEM);
        return itemStack.isEmpty() ? new ItemStack(Items.FIREWORK_ROCKET) : itemStack;
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    private void lambda$tick$0(int n) {
        Entity entity2 = this.world.getEntityByID(n);
        if (entity2 instanceof LivingEntity) {
            this.boostedEntity = (LivingEntity)entity2;
        }
    }
}

