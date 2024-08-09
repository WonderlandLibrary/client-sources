/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EyeOfEnderEntity
extends Entity
implements IRendersAsItem {
    private static final DataParameter<ItemStack> field_213864_b = EntityDataManager.createKey(EyeOfEnderEntity.class, DataSerializers.ITEMSTACK);
    private double targetX;
    private double targetY;
    private double targetZ;
    private int despawnTimer;
    private boolean shatterOrDrop;

    public EyeOfEnderEntity(EntityType<? extends EyeOfEnderEntity> entityType, World world) {
        super(entityType, world);
    }

    public EyeOfEnderEntity(World world, double d, double d2, double d3) {
        this((EntityType<? extends EyeOfEnderEntity>)EntityType.EYE_OF_ENDER, world);
        this.despawnTimer = 0;
        this.setPosition(d, d2, d3);
    }

    public void func_213863_b(ItemStack itemStack) {
        if (itemStack.getItem() != Items.ENDER_EYE || itemStack.hasTag()) {
            this.getDataManager().set(field_213864_b, Util.make(itemStack.copy(), EyeOfEnderEntity::lambda$func_213863_b$0));
        }
    }

    private ItemStack func_213861_i() {
        return this.getDataManager().get(field_213864_b);
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.func_213861_i();
        return itemStack.isEmpty() ? new ItemStack(Items.ENDER_EYE) : itemStack;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(field_213864_b, ItemStack.EMPTY);
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        double d2 = this.getBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(d2)) {
            d2 = 4.0;
        }
        return d < (d2 *= 64.0) * d2;
    }

    public void moveTowards(BlockPos blockPos) {
        double d;
        double d2 = blockPos.getX();
        int n = blockPos.getY();
        double d3 = blockPos.getZ();
        double d4 = d2 - this.getPosX();
        float f = MathHelper.sqrt(d4 * d4 + (d = d3 - this.getPosZ()) * d);
        if (f > 12.0f) {
            this.targetX = this.getPosX() + d4 / (double)f * 12.0;
            this.targetZ = this.getPosZ() + d / (double)f * 12.0;
            this.targetY = this.getPosY() + 8.0;
        } else {
            this.targetX = d2;
            this.targetY = n;
            this.targetZ = d3;
        }
        this.despawnTimer = 0;
        this.shatterOrDrop = this.rand.nextInt(5) > 0;
    }

    @Override
    public void setVelocity(double d, double d2, double d3) {
        this.setMotion(d, d2, d3);
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float f = MathHelper.sqrt(d * d + d3 * d3);
            this.rotationYaw = (float)(MathHelper.atan2(d, d3) * 57.2957763671875);
            this.rotationPitch = (float)(MathHelper.atan2(d2, f) * 57.2957763671875);
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }
    }

    @Override
    public void tick() {
        super.tick();
        Vector3d vector3d = this.getMotion();
        double d = this.getPosX() + vector3d.x;
        double d2 = this.getPosY() + vector3d.y;
        double d3 = this.getPosZ() + vector3d.z;
        float f = MathHelper.sqrt(EyeOfEnderEntity.horizontalMag(vector3d));
        this.rotationPitch = ProjectileEntity.func_234614_e_(this.prevRotationPitch, (float)(MathHelper.atan2(vector3d.y, f) * 57.2957763671875));
        this.rotationYaw = ProjectileEntity.func_234614_e_(this.prevRotationYaw, (float)(MathHelper.atan2(vector3d.x, vector3d.z) * 57.2957763671875));
        if (!this.world.isRemote) {
            double d4 = this.targetX - d;
            double d5 = this.targetZ - d3;
            float f2 = (float)Math.sqrt(d4 * d4 + d5 * d5);
            float f3 = (float)MathHelper.atan2(d5, d4);
            double d6 = MathHelper.lerp(0.0025, (double)f, (double)f2);
            double d7 = vector3d.y;
            if (f2 < 1.0f) {
                d6 *= 0.8;
                d7 *= 0.8;
            }
            int n = this.getPosY() < this.targetY ? 1 : -1;
            vector3d = new Vector3d(Math.cos(f3) * d6, d7 + ((double)n - d7) * (double)0.015f, Math.sin(f3) * d6);
            this.setMotion(vector3d);
        }
        float f4 = 0.25f;
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                this.world.addParticle(ParticleTypes.BUBBLE, d - vector3d.x * 0.25, d2 - vector3d.y * 0.25, d3 - vector3d.z * 0.25, vector3d.x, vector3d.y, vector3d.z);
            }
        } else {
            this.world.addParticle(ParticleTypes.PORTAL, d - vector3d.x * 0.25 + this.rand.nextDouble() * 0.6 - 0.3, d2 - vector3d.y * 0.25 - 0.5, d3 - vector3d.z * 0.25 + this.rand.nextDouble() * 0.6 - 0.3, vector3d.x, vector3d.y, vector3d.z);
        }
        if (!this.world.isRemote) {
            this.setPosition(d, d2, d3);
            ++this.despawnTimer;
            if (this.despawnTimer > 80 && !this.world.isRemote) {
                this.playSound(SoundEvents.ENTITY_ENDER_EYE_DEATH, 1.0f, 1.0f);
                this.remove();
                if (this.shatterOrDrop) {
                    this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), this.getItem()));
                } else {
                    this.world.playEvent(2003, this.getPosition(), 0);
                }
            }
        } else {
            this.setRawPosition(d, d2, d3);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        ItemStack itemStack = this.func_213861_i();
        if (!itemStack.isEmpty()) {
            compoundNBT.put("Item", itemStack.write(new CompoundNBT()));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        ItemStack itemStack = ItemStack.read(compoundNBT.getCompound("Item"));
        this.func_213863_b(itemStack);
    }

    @Override
    public float getBrightness() {
        return 1.0f;
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    private static void lambda$func_213863_b$0(ItemStack itemStack) {
        itemStack.setCount(1);
    }
}

