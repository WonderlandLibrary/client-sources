package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.MoveEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.NumberValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.Arrays;

public class WatchdogSpeed extends Mode<Speed> {

    private final ModeValue mode = new ModeValue("Type", this)
            .add(new SubMode("Ground Strafe"))
            .add(new SubMode("Autism"))
            .setDefault("Ground Strafe");
    public final BooleanValue yPort = new BooleanValue("Fast Fall", this, false);
    public final NumberValue ticksToGlide = new NumberValue("Ticks to Glide", this, 29, 1, 29, 1);

    private float speed = 0f;

    private boolean disable;
    private boolean enable;
    private static float lastInterpolatedYaw = 0.0f;
    private static final float YAW_STEP = 8.0f;

    public WatchdogSpeed(String name, Speed parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }

        disable = true;
    }

    @Override
    public void onDisable() {
        mc.thePlayer.omniSprint = false;
    }

    @EventLink
    public final Listener<MoveInputEvent> onInput = inputEvent -> inputEvent.setJump(false);
    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air) {
            disable = false;
        }

        if (mc.thePlayer.ticksSinceStep <= 10 || PlayerUtil.isBlockOver(2, true)) {
            disable = true;
        }

        if (yPort.getValue() && !disable) {
            event.setPosY(event.getPosY() + 0.000000000005);
        }
        // event.setYaw(lastInterpolatedYaw);
    };

    @EventLink
    public final Listener<MoveEvent> moveEventListener = moveEvent -> {
        //if (mc.thePlayer.motionY < 0) {
        //     rawSetSpeed(moveEvent, 5.75 / 20, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing, mc.thePlayer.rotationYaw);
        // }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        switch (mode.getValue().getName()) {
            case "Ground Strafe":

                mc.thePlayer.omniSprint = true;

                if (mc.thePlayer.onGround) {
                    MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() - 0.01);
                    mc.thePlayer.jump();
                }

//                ArrayList<Double> multipliers = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.008,
//                        1.003, 1.003, 1.0, 1.003, 1.01, 1.05, 1.1));
//
//                if (mc.thePlayer.offGroundTicks < multipliers.size()) {
//                    double multiplier = multipliers.get(mc.thePlayer.offGroundTicks);
//                    boolean potion = mc.thePlayer.isPotionActive(Potion.moveSpeed);
//
//                    multiplier = potion ? 1 + (multiplier % 1) * 1.1 : 1 + (multiplier % 1) * 0;
//
//                    if (mc.thePlayer.ticksSinceVelocity > mc.thePlayer.offGroundTicks + 2) {
//                        mc.thePlayer.motionX *= multiplier;
//                        mc.thePlayer.motionZ *= multiplier;
//                    }
//                }

//                if (mc.thePlayer.offGroundTicks == 7 && mc.thePlayer.ticksSinceVelocity > mc.thePlayer.offGroundTicks) {
//                    mc.thePlayer.motionY = mc.thePlayer.lastMotionY;
//                }

                if (mc.thePlayer.offGroundTicks == 1/* || (mc.thePlayer.offGroundTicks >= 7 &&
                        mc.thePlayer.offGroundTicks <= 9 && mc.thePlayer.ticksSinceVelocity > mc.thePlayer.offGroundTicks)*/ || (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0)
                        != Blocks.air && mc.thePlayer.offGroundTicks > 2)) {
                    MoveUtil.strafe();
                }

                break;

            case "Autism":
                if (MoveUtil.isMoving() && mc.thePlayer.onGround) {
                    MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());

                    mc.thePlayer.jump();
                }

                if (mc.thePlayer.onGround) {
                    speed = 1F;
                }

                final int[] allowedAirTicks = new int[]{10, 11, 13, 14, 16, 17, 19, 20, 22, 23, 25, 26, 28, 29};

                if (!(mc.theWorld.getBlockState(mc.thePlayer.getPosition().add(0, -0.25, 0)).getBlock() instanceof BlockAir)) {
                    for (final int allowedAirTick : allowedAirTicks) {
                        if (mc.thePlayer.offGroundTicks == allowedAirTick && allowedAirTick <= 9 + ticksToGlide.getValue().intValue()) {
                            mc.thePlayer.motionY = 0;
                            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * speed);

                            speed *= 0.98F;

                        }
                    }
                }

                if (this.mc.thePlayer.hurtTime == 7) {
                    //event.setSpeed(Math.hypot(this.mc.thePlayer.motionX, this.mc.thePlayer.motionZ));
                }

                break;
        }

        if (yPort.getValue() && !disable && mc.thePlayer.hurtTime == 0) {
            ArrayList<Double> values = new ArrayList<>(Arrays.asList(
                    0.33310120140062277, 0.24796918219826297, 0.14960980209333172,
                    0.05321760771444281, -0.02624674495067964, -0.3191218156544406,
                    -0.3161693874618279, -0.3882460072689227, -0.4588810960546281));

            if (mc.thePlayer.offGroundTicks < values.size() - 1 && mc.thePlayer.offGroundTicks > 1 &&
                    mc.thePlayer.ticksSinceVelocity > mc.thePlayer.offGroundTicks && mc.thePlayer.ticksSinceTeleport > 10) {
//                mc.thePlayer.motionY = values.get(mc.thePlayer.offGroundTicks - 1);
            }

            if (mc.thePlayer.offGroundTicks == 1) {
                MoveUtil.strafe();
            }

            if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air && mc.thePlayer.offGroundTicks > 2) {
                MoveUtil.strafe();
            }
        }
    };

    public static void rawSetSpeed(MoveEvent e, double speed, float forward, float strafing, float currentYaw) {
        if (forward == 0.0F && strafing == 0.0F) {
            return;
        }

        float targetYaw = currentYaw;
        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f * (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            targetYaw += 180.0f;
        if (strafing > 0.0f)
            targetYaw -= strafingYaw;
        else if (strafing < 0.0f)
            targetYaw += strafingYaw;

        targetYaw = (targetYaw + 360) % 360;

        float yawDifference = targetYaw - lastInterpolatedYaw;
        yawDifference = (yawDifference + 180) % 360 - 180;

        if (Math.abs(yawDifference) < YAW_STEP) {
            lastInterpolatedYaw = targetYaw; //snap
        } else {
            lastInterpolatedYaw += Math.signum(yawDifference) * YAW_STEP;
        }
        lastInterpolatedYaw = (lastInterpolatedYaw + 360) % 360;

        double x = StrictMath.cos(Math.toRadians(lastInterpolatedYaw + 90.0));
        double z = StrictMath.cos(Math.toRadians(lastInterpolatedYaw));

        e.setPosX(x * speed);
        e.setPosZ(z * speed);
    }
}