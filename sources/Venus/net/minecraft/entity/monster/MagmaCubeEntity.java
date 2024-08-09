/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.loot.LootTables;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class MagmaCubeEntity
extends SlimeEntity {
    public MagmaCubeEntity(EntityType<? extends MagmaCubeEntity> entityType, World world) {
        super((EntityType<? extends SlimeEntity>)entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute func_234294_m_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2f);
    }

    public static boolean func_223367_b(EntityType<MagmaCubeEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return iWorld.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public boolean isNotColliding(IWorldReader iWorldReader) {
        return iWorldReader.checkNoEntityCollision(this) && !iWorldReader.containsAnyLiquid(this.getBoundingBox());
    }

    @Override
    protected void setSlimeSize(int n, boolean bl) {
        super.setSlimeSize(n, bl);
        this.getAttribute(Attributes.ARMOR).setBaseValue(n * 3);
    }

    @Override
    public float getBrightness() {
        return 1.0f;
    }

    @Override
    protected IParticleData getSquishParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return this.isSmallSlime() ? LootTables.EMPTY : this.getType().getLootTable();
    }

    @Override
    public boolean isBurning() {
        return true;
    }

    @Override
    protected int getJumpDelay() {
        return super.getJumpDelay() * 4;
    }

    @Override
    protected void alterSquishAmount() {
        this.squishAmount *= 0.9f;
    }

    @Override
    protected void jump() {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x, this.getJumpUpwardsMotion() + (float)this.getSlimeSize() * 0.1f, vector3d.z);
        this.isAirBorne = true;
    }

    @Override
    protected void handleFluidJump(ITag<Fluid> iTag) {
        if (iTag == FluidTags.LAVA) {
            Vector3d vector3d = this.getMotion();
            this.setMotion(vector3d.x, 0.22f + (float)this.getSlimeSize() * 0.05f, vector3d.z);
            this.isAirBorne = true;
        } else {
            super.handleFluidJump(iTag);
        }
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        return true;
    }

    @Override
    protected boolean canDamagePlayer() {
        return this.isServerWorld();
    }

    @Override
    protected float func_225512_er_() {
        return super.func_225512_er_() + 2.0f;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_HURT_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_DEATH_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_DEATH;
    }

    @Override
    protected SoundEvent getSquishSound() {
        return this.isSmallSlime() ? SoundEvents.ENTITY_MAGMA_CUBE_SQUISH_SMALL : SoundEvents.ENTITY_MAGMA_CUBE_SQUISH;
    }

    @Override
    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_MAGMA_CUBE_JUMP;
    }
}

