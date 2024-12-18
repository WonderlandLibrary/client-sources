/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityEvokerFangs
extends Entity {
    private int field_190553_a;
    private boolean field_190554_b;
    private int field_190555_c = 22;
    private boolean field_190556_d;
    private EntityLivingBase field_190557_e;
    private UUID field_190558_f;

    public EntityEvokerFangs(World p_i47275_1_) {
        super(p_i47275_1_);
        this.setSize(0.5f, 0.8f);
    }

    public EntityEvokerFangs(World p_i47276_1_, double p_i47276_2_, double p_i47276_4_, double p_i47276_6_, float p_i47276_8_, int p_i47276_9_, EntityLivingBase p_i47276_10_) {
        this(p_i47276_1_);
        this.field_190553_a = p_i47276_9_;
        this.func_190549_a(p_i47276_10_);
        this.rotationYaw = p_i47276_8_ * 57.295776f;
        this.setPosition(p_i47276_2_, p_i47276_4_, p_i47276_6_);
    }

    @Override
    protected void entityInit() {
    }

    public void func_190549_a(@Nullable EntityLivingBase p_190549_1_) {
        this.field_190557_e = p_190549_1_;
        this.field_190558_f = p_190549_1_ == null ? null : p_190549_1_.getUniqueID();
    }

    @Nullable
    public EntityLivingBase func_190552_j() {
        Entity entity;
        if (this.field_190557_e == null && this.field_190558_f != null && this.world instanceof WorldServer && (entity = ((WorldServer)this.world).getEntityFromUuid(this.field_190558_f)) instanceof EntityLivingBase) {
            this.field_190557_e = (EntityLivingBase)entity;
        }
        return this.field_190557_e;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.field_190553_a = compound.getInteger("Warmup");
        this.field_190558_f = compound.getUniqueId("OwnerUUID");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("Warmup", this.field_190553_a);
        if (this.field_190558_f != null) {
            compound.setUniqueId("OwnerUUID", this.field_190558_f);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.world.isRemote) {
            if (this.field_190556_d) {
                --this.field_190555_c;
                if (this.field_190555_c == 14) {
                    for (int i = 0; i < 12; ++i) {
                        double d0 = this.posX + (this.rand.nextDouble() * 2.0 - 1.0) * (double)this.width * 0.5;
                        double d1 = this.posY + 0.05 + this.rand.nextDouble() * 1.0;
                        double d2 = this.posZ + (this.rand.nextDouble() * 2.0 - 1.0) * (double)this.width * 0.5;
                        double d3 = (this.rand.nextDouble() * 2.0 - 1.0) * 0.3;
                        double d4 = 0.3 + this.rand.nextDouble() * 0.3;
                        double d5 = (this.rand.nextDouble() * 2.0 - 1.0) * 0.3;
                        this.world.spawnParticle(EnumParticleTypes.CRIT, d0, d1 + 1.0, d2, d3, d4, d5, new int[0]);
                    }
                }
            }
        } else if (--this.field_190553_a < 0) {
            if (this.field_190553_a == -8) {
                for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(0.2, 0.0, 0.2))) {
                    this.func_190551_c(entitylivingbase);
                }
            }
            if (!this.field_190554_b) {
                this.world.setEntityState(this, (byte)4);
                this.field_190554_b = true;
            }
            if (--this.field_190555_c < 0) {
                this.setDead();
            }
        }
    }

    private void func_190551_c(EntityLivingBase p_190551_1_) {
        EntityLivingBase entitylivingbase = this.func_190552_j();
        if (p_190551_1_.isEntityAlive() && !p_190551_1_.func_190530_aW() && p_190551_1_ != entitylivingbase) {
            if (entitylivingbase == null) {
                p_190551_1_.attackEntityFrom(DamageSource.magic, 6.0f);
            } else {
                if (entitylivingbase.isOnSameTeam(p_190551_1_)) {
                    return;
                }
                p_190551_1_.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, entitylivingbase), 6.0f);
            }
        }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if (id == 4) {
            this.field_190556_d = true;
            if (!this.isSilent()) {
                this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.field_191242_bl, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.2f + 0.85f, false);
            }
        }
    }

    public float func_190550_a(float p_190550_1_) {
        if (!this.field_190556_d) {
            return 0.0f;
        }
        int i = this.field_190555_c - 2;
        return i <= 0 ? 1.0f : 1.0f - ((float)i - p_190550_1_) / 20.0f;
    }
}

