package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.*;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.KillAura;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.rotation.RotationUtil;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3d;

import java.util.function.Supplier;

@ModuleInfo(name = "Target Strafe", description = "Позволяет вам маневрировать вокруг таргета.", category = Category.MOVEMENT)
public class TargetStrafe extends Module {
    public static Singleton<TargetStrafe> singleton = Singleton.create(() -> Module.link(TargetStrafe.class));
    public final ModeValue mode = new ModeValue("Режим", this)
            .add(
                    new SubMode("Грим"),
                    new SubMode("FunTime")
            ).setDefault("FunTime");
    private final Animation boost = new Animation(Easing.LINEAR, 250);
    private float yaw;
    private LivingEntity target;
    private boolean left, colliding;
    public final Supplier<Boolean> allow = this::allowStrafe;

    @Override
    protected void onDisable() {
        super.onDisable();
        boost.setValue(0);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        boost.setValue(0);
    }

    public final Listener<MoveInputEvent> onMove = event -> {
        if (!mode.is("Грим")) return;
        KillAura aura = KillAura.singleton.get();
        if (toReturn(aura)) return;
        if (target != null && allowStrafe()) {
            event.setJump(false);
            event.setSneaking(false);
            boolean intersect = mc.player.getBoundingBox().intersects(target.getBoundingBox());
            event.setForward(intersect ? -1 : 1);
            event.setStrafe(left ? 1 : -1);
        }
    };
    public final Listener<JumpEvent> onJump = event -> {
        if (!mode.is("Грим")) return;
        KillAura aura = KillAura.singleton.get();
        if (toReturn(aura)) return;
        if (target != null && allowStrafe()) {
            setRotation();
            event.setYaw(yaw);
        }
    };
    public final Listener<MotionEvent> onMotion = event -> {
        if (mode.is("FunTime")) {
            AxisAlignedBB aabb = mc.player.getBoundingBox().grow(0.1);
            int armorstans = mc.world.getEntitiesWithinAABB(ArmorStandEntity.class, aabb).size();
            boolean canBoost = armorstans > 1
                    || mc.world.getEntitiesWithinAABB(LivingEntity.class, aabb).size() > 1;
            if (canBoost) {
                if (!mc.player.isOnGround()) {
                    mc.player.jumpMovementFactor = armorstans > 1 ? 1F / armorstans : 0.16F;
                }
            }
        }

        if (!mode.is("Грим")) return;
        KillAura aura = KillAura.singleton.get();
        if (toReturn(aura)) return;
        if (target != null && allowStrafe()) {
            event.setSprinting(false);
        }
    };

    private boolean toReturn(KillAura aura) {
        return !aura.isEnabled() || mc.player.isInLiquid() || mc.player.isSwimming();
    }

    public final Listener<StrafeEvent> onStrafe = event -> {
        if (!mode.is("Грим")) return;
        KillAura aura = KillAura.singleton.get();
        if (toReturn(aura)) return;

        if (target != null && allowStrafe()) {
            setRotation();
            event.setYaw(yaw);
            if (mc.player.isOnGround()) {
                boolean prevSprint = mc.player.isSprinting();
                mc.player.setSprinting(false);
                mc.player.jump();
                mc.player.setSprinting(prevSprint);
            }
            event.setFriction((float) (boost.getValue() * (mc.player.isOnGround() ? 0.2F : 0.1F)));
        }
    };

    private void setRotation() {
        if (!mode.is("Грим")) return;
        KillAura aura = KillAura.singleton.get();
        if (!aura.isEnabled() || aura.getTarget() == null) {
            this.yaw = mc.player.rotationYaw;
            return;
        }
        if (target == null) return;

        float yaw = RotationUtil.calculate(target).x();
        final int mul = left ? 1 : -1;
        yaw += (25) * mul;
        double range = 4;
        final double posX = -MathHelper.sin((float) Math.toRadians(yaw)) * range + target.getPosX();
        final double posZ = MathHelper.cos((float) Math.toRadians(yaw)) * range + target.getPosZ();
        yaw = RotationUtil.calculate(new Vector3d(posX, target.getPosY() + target.getEyeHeight(), posZ)).x();
        this.yaw = yaw;
    }

    public final Listener<UpdateEvent> onUpdate = event -> {
        if (!mode.is("Грим")) return;
        updateTarget();
        KillAura aura = KillAura.singleton.get();
        if (toReturn(aura)) return;
        if (target == null) return;

        if (mc.gameSettings.keyBindLeft.isKeyDown()) {
            left = true;
        }
        if (mc.gameSettings.keyBindRight.isKeyDown()) {
            left = false;
        }

        if (mc.player.collidedHorizontally || !PlayerUtil.isBlockUnder(5)) {
            if (!colliding) {
                left = !left;
            }
            colliding = true;
        } else {
            colliding = false;
        }
    };

    private double distanceToTarget() {
        return mc.player.getDistance(target);
    }

    private boolean allowStrafe() {
        if (target != null && mc.player.getBoundingBox().grow(1.5F).intersects(target.getBoundingBox()) && distanceToTarget() <= 2F) {
            return true;
        } else {
            boost.run(0);
            return false;
        }
    }

    private void updateTarget() {
        target = getTarget();
        if (target == null || mc.player.isOnGround() || !allowStrafe()) {
            boost.run(0);
        } else {
            boost.run(1);
        }
    }

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    private LivingEntity getTarget() {
        KillAura aura = KillAura.singleton.get();
        if (aura.isEnabled() && aura.getTarget() != null) {
            return aura.getTarget();
        }
        return null;
    }
}

