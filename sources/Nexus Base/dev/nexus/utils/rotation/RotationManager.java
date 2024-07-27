package dev.nexus.utils.rotation;

import dev.nexus.Nexus;
import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.Priorities;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.*;
import dev.nexus.modules.impl.other.MoveFix;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class RotationManager {
    private final Minecraft mc = Minecraft.getMinecraft();
    public float yaw;
    private float prevYaw;
    public float pitch;
    private float prevPitch;
    private boolean modified;
    private float speed;

    @EventLink
    public final Listener<EventLiving> eventLivingListener = event -> {
        EventSilentRotation rotation = new EventSilentRotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, speed);
        Nexus.INSTANCE.getEventManager().post(rotation);
        prevYaw = yaw;
        prevPitch = pitch;

        if (!modified && !rotation.hasBeenModified()) {
            yaw = mc.thePlayer.rotationYaw;
            pitch = mc.thePlayer.rotationPitch;
            return;
        }

        speed = rotation.getSpeed();

        float[] patchedRots = RotationUtils.getPatchedAndCappedRots(
                new float[]{prevYaw, prevPitch},
                new float[]{rotation.getYaw(), rotation.getPitch()},
                speed
        );

        yaw = patchedRots[0];
        pitch = patchedRots[1];
        modified = rotation.hasBeenModified() || (Math.abs(RotationUtils.getYawDifference(mc.thePlayer.rotationYaw, yaw)) > speed);
    };

    @EventLink
    public final Listener<EventMotionPre> eventUpdateListener = event -> {
        if (!modified)
            return;
        event.setYaw(yaw);
        event.setPitch(pitch);
    };

    @EventLink
    public final Listener<EventLook> eventLookListener = event -> {
        if (!modified)
            return;
        event.setYaw(yaw);
        event.setPitch(pitch);
    };

    @EventLink
    public final Listener<EventRenderThirdPerson> eventRenderThirdPersonListener = event -> {
        if(!modified)
            return;
        event.setAccepted(true);
        event.setYaw(yaw);
        event.setPrevYaw(prevYaw);
        event.setPitch(pitch);
        event.setPrevPitch(prevPitch);
    };

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<EventMoveInput> onMove = event -> {
        if (!modified)
            return;
        if (Nexus.INSTANCE.getModuleManager().getModule(MoveFix.class).isEnabled())
            fixMovement(event, yaw);
    };

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<EventStrafe> eventStrafeListener = event -> {
        if (!modified)
            return;
        if (Nexus.INSTANCE.getModuleManager().getModule(MoveFix.class).isEnabled())
            event.setYaw(yaw);
    };

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<EventJump> eventJumpListener = event -> {
        if (!modified)
            return;
        if (Nexus.INSTANCE.getModuleManager().getModule(MoveFix.class).isEnabled())
            event.setYaw(yaw);
    };

    public void fixMovement(final EventMoveInput event, final float yaw) {
        final float forward = event.getForward();
        final float strafe = event.getStrafe();

        final double angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(direction(mc.thePlayer.rotationYaw, forward, strafe)));

        if (forward == 0 && strafe == 0) {
            return;
        }

        float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;

        for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
            for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
                if (predictedStrafe == 0 && predictedForward == 0) continue;

                final double predictedAngle = MathHelper.wrapAngleTo180_double(Math.toDegrees(direction(yaw, predictedForward, predictedStrafe)));
                final double difference = Math.abs(angle - predictedAngle);

                if (difference < closestDifference) {
                    closestDifference = (float) difference;
                    closestForward = predictedForward;
                    closestStrafe = predictedStrafe;
                }
            }
        }

        event.setForward(closestForward);
        event.setStrafe(closestStrafe);
    }

    public double direction(float rotationYaw, final double moveForward, final double moveStrafing) {
        if (moveForward < 0F) rotationYaw += 180F;

        float forward = 1F;

        if (moveForward < 0F) forward = -0.5F;
        else if (moveForward > 0F) forward = 0.5F;

        if (moveStrafing > 0F) rotationYaw -= 90F * forward;
        if (moveStrafing < 0F) rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }
}