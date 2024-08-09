/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item.minecart;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class TNTMinecartEntity
extends AbstractMinecartEntity {
    private int minecartTNTFuse = -1;

    public TNTMinecartEntity(EntityType<? extends TNTMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

    public TNTMinecartEntity(World world, double d, double d2, double d3) {
        super(EntityType.TNT_MINECART, world, d, d2, d3);
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.TNT;
    }

    @Override
    public BlockState getDefaultDisplayTile() {
        return Blocks.TNT.getDefaultState();
    }

    @Override
    public void tick() {
        double d;
        super.tick();
        if (this.minecartTNTFuse > 0) {
            --this.minecartTNTFuse;
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5, this.getPosZ(), 0.0, 0.0, 0.0);
        } else if (this.minecartTNTFuse == 0) {
            this.explodeCart(TNTMinecartEntity.horizontalMag(this.getMotion()));
        }
        if (this.collidedHorizontally && (d = TNTMinecartEntity.horizontalMag(this.getMotion())) >= (double)0.01f) {
            this.explodeCart(d);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        AbstractArrowEntity abstractArrowEntity;
        Entity entity2 = damageSource.getImmediateSource();
        if (entity2 instanceof AbstractArrowEntity && (abstractArrowEntity = (AbstractArrowEntity)entity2).isBurning()) {
            this.explodeCart(abstractArrowEntity.getMotion().lengthSquared());
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public void killMinecart(DamageSource damageSource) {
        double d = TNTMinecartEntity.horizontalMag(this.getMotion());
        if (!(damageSource.isFireDamage() || damageSource.isExplosion() || d >= (double)0.01f)) {
            super.killMinecart(damageSource);
            if (!damageSource.isExplosion() && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                this.entityDropItem(Blocks.TNT);
            }
        } else if (this.minecartTNTFuse < 0) {
            this.ignite();
            this.minecartTNTFuse = this.rand.nextInt(20) + this.rand.nextInt(20);
        }
    }

    protected void explodeCart(double d) {
        if (!this.world.isRemote) {
            double d2 = Math.sqrt(d);
            if (d2 > 5.0) {
                d2 = 5.0;
            }
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float)(4.0 + this.rand.nextDouble() * 1.5 * d2), Explosion.Mode.BREAK);
            this.remove();
        }
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        if (f >= 3.0f) {
            float f3 = f / 10.0f;
            this.explodeCart(f3 * f3);
        }
        return super.onLivingFall(f, f2);
    }

    @Override
    public void onActivatorRailPass(int n, int n2, int n3, boolean bl) {
        if (bl && this.minecartTNTFuse < 0) {
            this.ignite();
        }
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 10) {
            this.ignite();
        } else {
            super.handleStatusUpdate(by);
        }
    }

    public void ignite() {
        this.minecartTNTFuse = 80;
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)10);
            if (!this.isSilent()) {
                this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }
    }

    public int getFuseTicks() {
        return this.minecartTNTFuse;
    }

    public boolean isIgnited() {
        return this.minecartTNTFuse > -1;
    }

    @Override
    public float getExplosionResistance(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, FluidState fluidState, float f) {
        return !this.isIgnited() || !blockState.isIn(BlockTags.RAILS) && !iBlockReader.getBlockState(blockPos.up()).isIn(BlockTags.RAILS) ? super.getExplosionResistance(explosion, iBlockReader, blockPos, blockState, fluidState, f) : 0.0f;
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, float f) {
        return !this.isIgnited() || !blockState.isIn(BlockTags.RAILS) && !iBlockReader.getBlockState(blockPos.up()).isIn(BlockTags.RAILS) ? super.canExplosionDestroyBlock(explosion, iBlockReader, blockPos, blockState, f) : false;
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("TNTFuse", 0)) {
            this.minecartTNTFuse = compoundNBT.getInt("TNTFuse");
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("TNTFuse", this.minecartTNTFuse);
    }
}

