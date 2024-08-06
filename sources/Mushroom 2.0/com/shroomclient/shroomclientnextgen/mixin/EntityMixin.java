package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.MovementFix;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Sprint;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Step;
import com.shroomclient.shroomclientnextgen.modules.impl.render.ESP;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = 9999)
public abstract class EntityMixin {

    @Shadow
    @Final
    private static int SPRINTING_FLAG_INDEX;

    @Shadow
    protected final DataTracker dataTracker;

    @Shadow
    private final EntityType<?> type;

    @Shadow
    public boolean horizontalCollision;

    @Shadow
    private boolean glowing;

    protected EntityMixin(DataTracker dataTracker, EntityType<?> type) {
        this.dataTracker = dataTracker;
        this.type = type;
    }

    @Shadow
    private static Vec3d movementInputToVelocity(
        Vec3d movementInput,
        float speed,
        float yaw
    ) {
        return null;
    }

    /**
     * @author scoliosis
     * @reason movement fix
     */
    @Inject(method = "updateVelocity", at = @At("HEAD"), cancellable = true)
    public void updateVelocity(
        float speed,
        Vec3d movementInput,
        CallbackInfo ci
    ) {
        Vec3d vec3d = movementInputToVelocity(
            movementInput,
            speed,
            this.getYaw()
        );

        // TODO: serverside yaw is delayed by a tick. we are cooked.
        if (MovementFix.shouldMoveFix()) {
            if (MovementUtil.ServersideYaw - C.p().getYaw() != 0) vec3d =
                movementInputToVelocity(
                    movementInput,
                    speed,
                    MovementUtil.ServersideYaw
                );
        }

        this.setVelocity(this.getVelocity().add(vec3d));

        ci.cancel();
    }

    @Shadow
    public abstract float getYaw();

    @Shadow
    public abstract Vec3d getVelocity();

    @Shadow
    public abstract void setVelocity(Vec3d velocity);

    @Shadow
    public abstract double getY();

    @Shadow
    protected abstract boolean getFlag(int index);

    /**
     * @author 112batman
     * @reason Sprint
     */
    @Overwrite
    public boolean isSprinting() {
        if (
            C.p() != null &&
            Sprint.shouldOverrideSprint() &&
            C.p().getId() == this.getId()
        ) {
            boolean flag = Sprint.shouldSprint();
            setFlag(SPRINTING_FLAG_INDEX, flag);
            setSprinting(flag);
            return flag;
        }

        return this.getFlag(SPRINTING_FLAG_INDEX);
    }

    @Shadow
    public abstract void setSprinting(boolean sprinting);

    @Shadow
    public abstract int getId();

    @Shadow
    protected abstract void setFlag(int index, boolean value);

    @Shadow
    public abstract World getWorld();

    @Shadow
    public abstract boolean isOnGround();

    @Shadow
    public abstract void setVelocity(double x, double y, double z);

    @Shadow
    public abstract float getPitch();

    @Shadow
    public abstract Box getBoundingBox();

    @Shadow
    public static Vec3d adjustMovementForCollisions(
        @Nullable Entity entity,
        Vec3d movement,
        Box entityBoundingBox,
        World world,
        List<VoxelShape> collisions
    ) {
        return null;
    }

    @Shadow
    protected abstract void fall(
        double heightDifference,
        boolean onGround,
        BlockState state,
        BlockPos landedPosition
    );

    @Shadow
    protected abstract void refreshPosition();

    @Shadow
    public abstract boolean hasNoGravity();

    @Shadow
    protected abstract BlockPos getVelocityAffectingPos();

    @Shadow
    public abstract DamageSources getDamageSources();

    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow
    public abstract void move(MovementType movementType, Vec3d movement);

    @Shadow
    public abstract void limitFallDistance();

    @Shadow
    public abstract Vec3d getRotationVector();

    @Shadow
    public abstract boolean doesNotCollide(
        double offsetX,
        double offsetY,
        double offsetZ
    );

    @Shadow
    public abstract double getSwimHeight();

    @Shadow
    public abstract double getFluidHeight(TagKey<Fluid> fluid);

    @Shadow
    public abstract boolean isInLava();

    @Shadow
    public abstract void updateVelocity(float speed, Vec3d movementInput);

    @Shadow
    public abstract boolean isLogicalSideForUpdatingMovement();

    @Shadow
    public abstract BlockPos getBlockPos();

    @Shadow
    public abstract boolean isTouchingWater();

    /**
     * @author scale!!!
     * @reason esp!!
     */
    @Overwrite
    public boolean isGlowing() {
        if (ModuleManager.isEnabled(ESP.class)) {
            if (type == EntityType.ITEM && ESP.droppedItems) return true;
            Entity t = (Entity) (Object) this;
            if (t instanceof PlayerEntity && ESP.minecraftGlow) {
                if (!TargetUtil.isBot(t)) return true;
            } else return false;
        }

        return glowing;
    }

    /**
     * @author swig
     * @reason step
     */
    @Overwrite
    public float getStepHeight() {
        if (ModuleManager.isEnabled(Step.class)) {
            if (Step.mode == Step.Mode.Vanilla) return Step.Height;
        }
        return 0.6F;
    }
}
