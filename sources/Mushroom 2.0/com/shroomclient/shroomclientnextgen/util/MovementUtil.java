package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.NoSlow;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Speed;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@RegisterListeners(registerStatic = true)
public class MovementUtil {

    // gets ticks that player has been in air for
    public static int airTicks = 0;
    public static int groundTicks = 0;
    public static int jumps = 0;
    public static int ticksSinceLastHurt = 0;
    public static boolean isUsingItem = false;
    public static boolean lastUsingItem = false;
    public static Vec3d lastJumpPos = new Vec3d(0, 0, 0);
    public static Vec3d lastJumpVelocity = new Vec3d(0, 0, 0);
    public static Vec3d lastLeaveGroundPos = new Vec3d(0, 0, 0);
    public static Vec3d lastNotOverVoidPos = new Vec3d(0, 0, 0);
    public static Vec3d lastNotOverVoidVelo = new Vec3d(0, 0, 0);
    // scaffold is evil
    public static Vec3d lastLeaveGroundPosNoScaffold = new Vec3d(0, 0, 0);
    public static Vec3d movementInput = new Vec3d(0, 0, 0);
    public static float PrevServersideYaw = 0;
    public static float ServersideYaw = 0;
    public static RotationUtil.Rotation ServersideRots =
        new RotationUtil.Rotation(0, 0);
    public static int ticks = 0;
    public static boolean headSnapped = false;
    public static boolean onGroundClient = false;
    public static boolean lastOnGround = false;

    public static boolean isMoving() {
        return (
            C.isInGame() &&
            (C.p().forwardSpeed >= 0.1 ||
                C.p().sidewaysSpeed >= 0.1 ||
                C.p().sidewaysSpeed <= -0.1 ||
                C.p().forwardSpeed <= -0.1)
        );
    }

    public static boolean isUserMoving(boolean y) {
        return (
            C.mc.options.forwardKey.isPressed() ||
            (y && C.mc.options.jumpKey.isPressed()) ||
            C.mc.options.leftKey.isPressed() ||
            C.mc.options.rightKey.isPressed() ||
            C.mc.options.backKey.isPressed()
        );
    }

    // gonna need this for nuker aswell
    public static boolean OverrideHeadRotations() {
        if (Speed.headSnapped) return true;
        if (headSnapped) return true;
        return false;
    }

    @SubscribeEvent(EventBus.Priority.LOWEST)
    public static void onMotionPost(MotionEvent.Post e) {
        headSnapped = false;
    }

    public static void setYmotion(double ymotion) {
        C.p()
            .setVelocity(C.p().getVelocity().x, ymotion, C.p().getVelocity().z);
    }

    public static void setXZvelocity(double motion) {
        C.p().setVelocity(motion, C.p().getVelocity().y, motion);
    }

    public static void setMotionBoostless(double speed) {
        double forward = C.p().forwardSpeed;
        double strafe = C.p().sidewaysSpeed;

        double yaw = C.p().getRotationClient().y;

        if (forward == 0.0 && strafe == 0.0) {
            C.p().setVelocity(0, C.p().getVelocity().y, 0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            double sin = Math.sin(Math.toRadians(yaw + 90.0f));

            C.p()
                .setVelocity(
                    forward * speed * cos + strafe * speed * sin,
                    C.p().getVelocity().y,
                    forward * speed * sin - strafe * speed * cos
                );
        }
    }

    public static void setMotion(double speed) {
        double forward = C.p().forwardSpeed;
        double strafe = C.p().sidewaysSpeed;

        double yaw = C.p().getRotationClient().y;

        speed *= C.p().getMovementSpeed() * 10;

        if (forward == 0.0 && strafe == 0.0) {
            C.p().setVelocity(0, C.p().getVelocity().y, 0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            double sin = Math.sin(Math.toRadians(yaw + 90.0f));

            C.p()
                .setVelocity(
                    forward * speed * cos + strafe * speed * sin,
                    C.p().getVelocity().y,
                    forward * speed * sin - strafe * speed * cos
                );
        }
    }

    public static void doMotionBoostless(double speed, double yaw) {
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));

        C.p().setVelocity(speed * cos, C.p().getVelocity().y, speed * sin);
    }

    public static void doMotion(double speed, double yaw) {
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));

        speed *= C.p().getMovementSpeed() * 10;

        C.p().setVelocity(speed * cos, C.p().getVelocity().y, speed * sin);
    }

    // stolen from minecrafts entity class
    public static float distanceTo(Vec3d firstPos, Vec3d seconPos) {
        float f = (float) (firstPos.x - seconPos.x);
        float g = (float) (firstPos.y - seconPos.y);
        float h = (float) (firstPos.z - seconPos.z);
        return MathHelper.sqrt(f * f + g * g + h * h);
    }

    public static float distanceToExcludeY(Vec3d firstPos, Vec3d seconPos) {
        float f = (float) (firstPos.x - seconPos.x);
        float h = (float) (firstPos.z - seconPos.z);
        return MathHelper.sqrt(f * f + h * h);
    }

    public static void jumpBoostless() {
        MovementUtil.lastJumpPos = C.p().getPos();
        Vec3d vec3d = C.p().getVelocity();

        C.p().setVelocity(vec3d.x, 0.42F, vec3d.z);
        if (
            C.p().isSprinting() &&
            MovementUtil.isUserMoving(false) &&
            (!C.p().isUsingItem() ||
                (ModuleManager.isEnabled(NoSlow.class) && NoSlow.doNoSlow))
        ) {
            float f = MovementUtil.getYaw() * 0.017453292F;
            C.p()
                .setVelocity(
                    C.p()
                        .getVelocity()
                        .add(
                            (-MathHelper.sin(f) * 0.2F),
                            0.0,
                            (MathHelper.cos(f) * 0.2F)
                        )
                );
        }

        C.p().velocityDirty = true;

        C.p().incrementStat(Stats.JUMP);
        if (C.p().isSprinting()) {
            C.p().addExhaustion(0.2F);
        } else {
            C.p().addExhaustion(0.05F);
        }
    }

    public static void jump(float height) {
        MovementUtil.lastJumpPos = C.p().getPos();
        Vec3d vec3d = C.p().getVelocity();

        C.p().setVelocity(vec3d.x, height, vec3d.z);
        if (
            C.p().isSprinting() &&
            MovementUtil.isUserMoving(false) &&
            (!C.p().isUsingItem() ||
                (ModuleManager.isEnabled(NoSlow.class) && NoSlow.doNoSlow))
        ) {
            float f = MovementUtil.getYaw() * 0.017453292F;
            C.p()
                .setVelocity(
                    C.p()
                        .getVelocity()
                        .add(
                            (-MathHelper.sin(f) * 0.2F),
                            0.0,
                            (MathHelper.cos(f) * 0.2F)
                        )
                );
        }

        C.p().velocityDirty = true;

        C.p().incrementStat(Stats.JUMP);
        if (C.p().isSprinting()) {
            C.p().addExhaustion(0.2F);
        } else {
            C.p().addExhaustion(0.05F);
        }
    }

    public static void jump() {
        MovementUtil.lastJumpPos = C.p().getPos();
        Vec3d vec3d = C.p().getVelocity();

        C.p().setVelocity(vec3d.x, Speed.jumpVelo(), vec3d.z);
        if (
            C.p().isSprinting() &&
            MovementUtil.isUserMoving(false) &&
            (!C.p().isUsingItem() ||
                (ModuleManager.isEnabled(NoSlow.class) && NoSlow.doNoSlow))
        ) {
            float f = MovementUtil.getYaw() * 0.017453292F;
            C.p()
                .setVelocity(
                    C.p()
                        .getVelocity()
                        .add(
                            (-MathHelper.sin(f) * 0.2F),
                            0.0,
                            (MathHelper.cos(f) * 0.2F)
                        )
                );
        }

        C.p().velocityDirty = true;

        C.p().incrementStat(Stats.JUMP);
        if (C.p().isSprinting()) {
            C.p().addExhaustion(0.2F);
        } else {
            C.p().addExhaustion(0.05F);
        }
    }

    public static float jumpVeloMulti() {
        return C.p().getJumpBoostVelocityModifier();
    }

    public static float getYaw() {
        float yaw = C.p().getYaw();
        if (C.p().forwardSpeed < 0.0f) yaw += 180.0f;
        float forward = 1.0f;
        if (C.p().forwardSpeed < 0.0f) forward = -0.5f;
        else if (C.p().forwardSpeed > 0.0f) forward = 0.5f;

        if (C.p().sidewaysSpeed > 0.0f) yaw -= 90.0f * forward;
        if (C.p().sidewaysSpeed < 0.0f) yaw += 90.0f * forward;

        return yaw;
    }
}
