// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds.hypixel;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.combat.KillAura;
import cc.slack.features.modules.impl.movement.Speed;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.PlayerUtil;
import cc.slack.utils.rotations.RotationUtil;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class HypixelHopSpeed implements ISpeed {

    int jumpTick = 0;
    boolean wasSlow = false;

    float yaw = 0f;
    float speed = 0f;

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround) {
            if (MovementUtil.isMoving()) {
                wasSlow = false;
                if (jumpTick > 6) jumpTick = 5;
                mc.thePlayer.jump();
                MovementUtil.strafe(0.475f + jumpTick * 0.007f);
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    float amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                    MovementUtil.strafe(0.46f + jumpTick * 0.008f + 0.023f * (amplifier + 1));
                }
                yaw = MovementUtil.getDirection();
                mc.thePlayer.motionY = PlayerUtil.getJumpHeight();
            } else {
                jumpTick = 0;
            }
        } else {
            if (mc.thePlayer.offGroundTicks == 1) {
                MovementUtil.strafe(0.34125f, yaw);
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    MovementUtil.strafe(0.37f, yaw);
                }
                return;
            }
            mc.thePlayer.motionX *= 1.0005;
            mc.thePlayer.motionZ *= 1.0005;

            if (mc.thePlayer.offGroundTicks < 13) {
                if (mc.thePlayer.motionY < 0) {
                    if (jumpTick < 5 && !Slack.getInstance().getModuleManager().getInstance(Speed.class).enabledTime.hasReached(7000)) {
                        mc.timer.timerSpeed = 1.07f + (float) Math.random() * 0.07f;
                    } else {
                        mc.timer.timerSpeed = 1f;
                    }
                } else {
                    mc.timer.timerSpeed = 0.95f;
                }
            } else {
                mc.timer.timerSpeed = 1f;
            }

            if (Slack.getInstance().getModuleManager().getInstance(Speed.class).hypixelTest.getValue()) {
                if (!PlayerUtil.isOverAir(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.motionY + 1, mc.thePlayer.posZ) && mc.thePlayer.offGroundTicks > 2) {
                    MovementUtil.strafe();
                }
            }

            if (Slack.getInstance().getModuleManager().getInstance(Speed.class).hypixelSemiStrafe.getValue()) {
                if (mc.thePlayer.offGroundTicks == 6 && Slack.getInstance().getModuleManager().getInstance(KillAura.class).target == null) {
                    if (Math.abs(MathHelper.wrapAngleTo180_float(
                            MovementUtil.getBindsDirection(mc.thePlayer.rotationYaw) -
                                    RotationUtil.getRotations(new Vec3(0, 0, 0), new Vec3(mc.thePlayer.motionX, 0, mc.thePlayer.motionZ))[0]
                    )) > 30) {
                        MovementUtil.strafe(MovementUtil.getSpeed() * 0.90f);
                    }
                }

                if (wasSlow) {
                    MovementUtil.strafe(0.14f);
                    wasSlow = false;
                }
                if (Math.abs(MathHelper.wrapAngleTo180_float(
                        MovementUtil.getBindsDirection(mc.thePlayer.rotationYaw) -
                                RotationUtil.getRotations(new Vec3(0, 0, 0), new Vec3(mc.thePlayer.motionX, 0, mc.thePlayer.motionZ))[0]
                )) > 135) {
                    MovementUtil.resetMotion(false);
                    wasSlow = true;
                }
            }
        }

        if (Slack.getInstance().getModuleManager().getInstance(Speed.class).hypixelGlide.getValue()) {
            if (mc.thePlayer.onGround) {
                speed = 1F;
            }

            final int[] allowedAirTicks = new int[]{10, 11, 13, 14, 16, 17, 19, 20, 22, 23, 25, 26, 28, 29};

            if (!PlayerUtil.isOverAir(mc.thePlayer.posX, mc.thePlayer.posY - 0.4 + 1, mc.thePlayer.posZ)) {
                for (final int allowedAirTick : allowedAirTicks) {
                    if (mc.thePlayer.offGroundTicks == allowedAirTick && allowedAirTick <= 9 + 15 && MovementUtil.isMoving()) {
                        mc.thePlayer.motionY = 0;
                        MovementUtil.strafe(0.2873f * speed);

                        speed *= 0.98F;

                    }
                }
            }
        }

    }

    @Override
    public String toString() {
        return "Hypixel";
    }
}
