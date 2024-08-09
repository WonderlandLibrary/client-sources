/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class SpectralArrowEntity
extends AbstractArrowEntity {
    private int duration = 200;

    public SpectralArrowEntity(EntityType<? extends SpectralArrowEntity> entityType, World world) {
        super((EntityType<? extends AbstractArrowEntity>)entityType, world);
    }

    public SpectralArrowEntity(World world, LivingEntity livingEntity) {
        super(EntityType.SPECTRAL_ARROW, livingEntity, world);
    }

    public SpectralArrowEntity(World world, double d, double d2, double d3) {
        super(EntityType.SPECTRAL_ARROW, d, d2, d3, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isRemote && !this.inGround) {
            this.world.addParticle(ParticleTypes.INSTANT_EFFECT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.SPECTRAL_ARROW);
    }

    @Override
    protected void arrowHit(LivingEntity livingEntity) {
        super.arrowHit(livingEntity);
        EffectInstance effectInstance = new EffectInstance(Effects.GLOWING, this.duration, 0);
        livingEntity.addPotionEffect(effectInstance);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("Duration")) {
            this.duration = compoundNBT.getInt("Duration");
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Duration", this.duration);
    }
}

