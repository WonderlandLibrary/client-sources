package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.AntiAim;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.fluid.FluidState;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = LivingEntity.class, priority = 1)
public abstract class LivingEntityMixin extends EntityMixin {

    @Shadow
    protected int roll;

    float num = 0;

    @Shadow
    private float leaningPitch;

    @Shadow
    private float lastLeaningPitch;

    /**
     * @author scoliosis
     * @reason movement fix
     */
    /*
    @Overwrite
    public void travel(Vec3d movementInput) {
        MovementUtil.movementInput = movementInput;

        if (this.isLogicalSideForUpdatingMovement()) {
            double d = 0.08;
            boolean bl = this.getVelocity().y <= 0.0;
            if (bl && this.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
                d = 0.01;
            }

            FluidState fluidState = this.getWorld().getFluidState(this.getBlockPos());
            float f;
            double e;
            if (this.isTouchingWater() && this.shouldSwimInFluids() && !this.canWalkOnFluid(fluidState)) {
                e = this.getY();
                f = this.isSprinting() ? 0.9F : this.getBaseMovementSpeedMultiplier();
                float g = 0.02F;
                float h = (float) EnchantmentHelper.getDepthStrider(C.p());
                if (h > 3.0F) {
                    h = 3.0F;
                }

                if (!this.isOnGround()) {
                    h *= 0.5F;
                }

                if (h > 0.0F) {
                    f += (0.54600006F - f) * h / 3.0F;
                    g += (this.getMovementSpeed() - g) * h / 3.0F;
                }

                if (this.hasStatusEffect(StatusEffects.DOLPHINS_GRACE)) {
                    f = 0.96F;
                }

                this.updateVelocity(g, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                Vec3d vec3d = this.getVelocity();
                if (this.horizontalCollision && this.isClimbing()) {
                    vec3d = new Vec3d(vec3d.x, 0.2, vec3d.z);
                }

                this.setVelocity(vec3d.multiply((double) f, 0.800000011920929, (double) f));
                Vec3d vec3d2 = this.applyFluidMovingSpeed(d, bl, this.getVelocity());
                this.setVelocity(vec3d2);
                if (this.horizontalCollision && this.doesNotCollide(vec3d2.x, vec3d2.y + 0.6000000238418579 - this.getY() + e, vec3d2.z)) {
                    this.setVelocity(vec3d2.x, 0.30000001192092896, vec3d2.z);
                }
            } else if (this.isInLava() && this.shouldSwimInFluids() && !this.canWalkOnFluid(fluidState)) {
                e = this.getY();
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                Vec3d vec3d3;
                if (this.getFluidHeight(FluidTags.LAVA) <= this.getSwimHeight()) {
                    this.setVelocity(this.getVelocity().multiply(0.5, 0.800000011920929, 0.5));
                    vec3d3 = this.applyFluidMovingSpeed(d, bl, this.getVelocity());
                    this.setVelocity(vec3d3);
                } else {
                    this.setVelocity(this.getVelocity().multiply(0.5));
                }

                if (!this.hasNoGravity()) {
                    this.setVelocity(this.getVelocity().add(0.0, -d / 4.0, 0.0));
                }

                vec3d3 = this.getVelocity();
                if (this.horizontalCollision && this.doesNotCollide(vec3d3.x, vec3d3.y + 0.6000000238418579 - this.getY() + e, vec3d3.z)) {
                    this.setVelocity(vec3d3.x, 0.30000001192092896, vec3d3.z);
                }
            } else if (this.isFallFlying()) {
                this.limitFallDistance();
                Vec3d vec3d4 = this.getVelocity();
                Vec3d vec3d5 = this.getRotationVector();
                f = this.getPitch() * 0.017453292F;
                double i = Math.sqrt(vec3d5.x * vec3d5.x + vec3d5.z * vec3d5.z);
                double j = vec3d4.horizontalLength();
                double k = vec3d5.length();
                double l = Math.cos((double) f);
                l = l * l * Math.min(1.0, k / 0.4);
                vec3d4 = this.getVelocity().add(0.0, d * (-1.0 + l * 0.75), 0.0);
                double m;
                if (vec3d4.y < 0.0 && i > 0.0) {
                    m = vec3d4.y * -0.1 * l;
                    vec3d4 = vec3d4.add(vec3d5.x * m / i, m, vec3d5.z * m / i);
                }

                if (f < 0.0F && i > 0.0) {
                    m = j * (double) (-MathHelper.sin(f)) * 0.04;
                    vec3d4 = vec3d4.add(-vec3d5.x * m / i, m * 3.2, -vec3d5.z * m / i);
                }

                if (i > 0.0) {
                    vec3d4 = vec3d4.add((vec3d5.x / i * j - vec3d4.x) * 0.1, 0.0, (vec3d5.z / i * j - vec3d4.z) * 0.1);
                }

                this.setVelocity(vec3d4.multiply(0.9900000095367432, 0.9800000190734863, 0.9900000095367432));
                this.move(MovementType.SELF, this.getVelocity());
                if (this.horizontalCollision && !this.getWorld().isClient) {
                    m = this.getVelocity().horizontalLength();
                    double n = j - m;
                    float o = (float) (n * 10.0 - 3.0);
                    if (o > 0.0F) {
                        this.playSound(this.getFallSound((int) o), 1.0F, 1.0F);
                        this.damage(this.getDamageSources().flyIntoWall(), o);
                    }
                }

                if (this.isOnGround() && !this.getWorld().isClient) {
                    this.setFlag(7, false);
                }
            } else {
                BlockPos blockPos = this.getVelocityAffectingPos();
                float p = this.getWorld().getBlockState(blockPos).getBlock().getSlipperiness();
                f = this.isOnGround() ? p * 0.91F : 0.91F;
                Vec3d vec3d6 = this.applyMovementInput(movementInput, p);
                double q = vec3d6.y;
                if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
                    q += (0.05 * (double) (this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1) - vec3d6.y) * 0.2;
                } else if (this.getWorld().isClient && !this.getWorld().isChunkLoaded(blockPos)) {
                    if (this.getY() > (double) this.getWorld().getBottomY()) {
                        q = -0.1;
                    } else {
                        q = 0.0;
                    }
                } else if (!this.hasNoGravity()) {
                    q -= d;
                }

                if (this.hasNoDrag()) {
                    this.setVelocity(vec3d6.x, q, vec3d6.z);
                } else {
                    this.setVelocity(vec3d6.x * (double) f, q * 0.9800000190734863, vec3d6.z * (double) f);
                }
            }
        }

        this.updateLimbs(this instanceof Flutterer);
    }
     */
    protected LivingEntityMixin(DataTracker dataTracker, EntityType<?> type) {
        super(dataTracker, type);
    }

    /**
     * @author scoliosis
     * @reason pitching
     */
    @Overwrite
    public float getLeaningPitch(float tickDelta) {
        if (
            ModuleManager.isEnabled(AntiAim.class) &&
            this.getId() == C.p().getId()
        ) {
            if (AntiAim.mode == AntiAim.Mode.Swimming) {
                return MathHelper.lerp(tickDelta, 5f, 5f);
            } else if (AntiAim.mode == AntiAim.Mode.Backflip) {
                num += AntiAim.foldingSpeed;
                if (num > 20) num = 0;

                return MathHelper.lerp(tickDelta, num, num);
            } else if (AntiAim.mode == AntiAim.Mode.Flail) {
                return MathHelper.lerp(tickDelta, 20f, 20f);
            }
        }
        return MathHelper.lerp(
            tickDelta,
            this.lastLeaningPitch,
            this.leaningPitch
        );
    }

    @Shadow
    public abstract float getJumpVelocity();

    @Shadow
    public abstract void jump();

    @Shadow
    public abstract boolean isUsingItem();

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    protected abstract boolean shouldSwimInFluids();

    @Shadow
    public abstract boolean canWalkOnFluid(FluidState state);

    @Shadow
    protected abstract float getBaseMovementSpeedMultiplier();

    @Shadow
    public abstract float getMovementSpeed();

    @Shadow
    public abstract boolean isClimbing();

    @Shadow
    public abstract Vec3d applyFluidMovingSpeed(
        double gravity,
        boolean falling,
        Vec3d motion
    );

    @Shadow
    public abstract boolean isFallFlying();

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow
    protected abstract SoundEvent getFallSound(int distance);

    @Shadow
    public abstract Vec3d applyMovementInput(
        Vec3d movementInput,
        float slipperiness
    );

    @Shadow
    @Nullable
    public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow
    public abstract boolean hasNoDrag();

    @Shadow
    public abstract void updateLimbs(boolean flutter);

    /**
     * @author hallo
     * @reason h
     */
    @Overwrite
    public int getRoll() {
        if (
            ModuleManager.isEnabled(AntiAim.class) &&
            (AntiAim.headRoll && AntiAim.mode != AntiAim.Mode.Jitter) &&
            this.getId() == C.p().getId()
        ) return (int) (System.currentTimeMillis() % 100);
        return this.roll;
    }
}
