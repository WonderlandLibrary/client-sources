/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.engine.rotation;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.*;
import de.dietrichpaul.clientbase.event.rotate.RotationGetListener;
import de.dietrichpaul.clientbase.event.rotate.RotationSetListener;
import de.dietrichpaul.clientbase.event.rotate.SendRotationListener;
import de.dietrichpaul.clientbase.feature.engine.rotation.strafe.StrafeMode;
import de.dietrichpaul.clientbase.util.minecraft.rtx.Raytrace;
import de.dietrichpaul.clientbase.util.minecraft.rtx.RaytraceUtil;
import de.dietrichpaul.clientbase.util.render.OpenGL;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RotationEngine implements SendRotationListener, RaytraceListener, StrafeListener, RotationGetListener, RotationSetListener, JumpListener, StrafeInputListener, MoveCameraListener, PreTickRaytraceListener {

    private final Set<RotationSpoof> spoofs = new HashSet<>();

    private final float[] prevRotations = new float[2];
    private final float[] reportedRotations = new float[2];
    private final float[] rotations = new float[2];

    private boolean rotating;
    private boolean hasTarget;
    private boolean confirmedClientRotation = true;

    private boolean raytrace;
    private boolean rotateBack;
    private boolean lockView;
    private SensitivityFix sensitivityFix;
    private StrafeMode strafeMode;

    private float yawSpeed = Float.NaN;
    private float pitchSpeed = Float.NaN;
    private float prevYawSpeed;
    private float prevPitchSpeed;

    private double partialIterations;

    private RotationSpoof prevSpoof;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public RotationEngine() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(SendRotationListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(RaytraceListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(StrafeListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(RotationGetListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(RotationSetListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(JumpListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(StrafeInputListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(MoveCameraListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(PreTickRaytraceListener.class, this);
    }

    public void add(RotationSpoof spoof) {
        this.spoofs.add(spoof);
    }

    private void rotate(boolean tick, float partialTicks) {
        RotationSpoof spoof = prevSpoof;
        if (tick) {
            raytrace = spoof.raytrace();
            strafeMode = spoof.getStrafeMode();
            sensitivityFix = spoof.getSensitivityFix();
            lockView = spoof.lockView();
            rotateBack = spoof.rotateBack();
            prevYawSpeed = yawSpeed;
            prevPitchSpeed = pitchSpeed;
            yawSpeed = spoof.getYawSpeed();
            pitchSpeed = spoof.getPitchSpeed();

            if (Float.isNaN(prevYawSpeed))
                prevYawSpeed = yawSpeed;
            if (Float.isNaN(prevPitchSpeed))
                prevPitchSpeed = pitchSpeed;
        }

        if (hasTarget) {
            float[] targetRotations = new float[2];
            if (tick) spoof.tick();
            spoof.rotate(targetRotations, reportedRotations, tick, partialTicks);
            limitDeltaRotation(reportedRotations, targetRotations,
                    MathHelper.lerp(partialTicks, prevYawSpeed, yawSpeed),
                    MathHelper.lerp(partialTicks, prevPitchSpeed, pitchSpeed),
                    partialTicks
            );
            mouseSensitivity(reportedRotations, targetRotations, rotations, partialTicks);
            if (lockView) {
                MinecraftClient.getInstance().player.setYaw(rotations[0]);
                MinecraftClient.getInstance().player.setPitch(rotations[1]);
            }
            confirmedClientRotation = false;
        } else if (!confirmedClientRotation) {
            if (lockView) {
                if (tick) {
                    confirmClientRotation();
                }
            } else {
                float yawDiff = MathHelper.angleBetween(mc.player.getYaw(), reportedRotations[0]);
                float pitchDiff = MathHelper.angleBetween(mc.player.getPitch(), reportedRotations[1]);
                if (!rotateBack || (tick && yawDiff <= yawSpeed && pitchDiff < pitchSpeed)) {
                    confirmClientRotation();
                } else {
                    float[] targetRotations = new float[2];
                    targetRotations[0] = mc.player.getYaw();
                    targetRotations[1] = mc.player.getPitch();
                    limitDeltaRotation(reportedRotations, targetRotations,
                            MathHelper.lerp(partialTicks, prevYawSpeed, yawSpeed),
                            MathHelper.lerp(partialTicks, prevPitchSpeed, pitchSpeed),
                            partialTicks
                    );
                    mouseSensitivity(reportedRotations, targetRotations, rotations, partialTicks);
                }
            }
        }
    }

    private void confirmClientRotation() {
        float[] targetRotations = new float[]{
                mc.player.getYaw(),
                mc.player.getPitch()
        };
        mouseSensitivity(reportedRotations, targetRotations, rotations, 1.0F);
        mc.player.setYaw(rotations[0]);
        mc.player.getPitch(rotations[1]);
        confirmedClientRotation = true;
    }

    @Override
    public void onMoveCamera(float tickDelta) {
        if (mc.player == null || mc.world == null || mc.cameraEntity == null)
            return;
        if (rotating) {
            if (lockView) rotate(false, tickDelta);
        } else {
            rotations[0] = mc.player.getYaw();
            rotations[1] = mc.player.getPitch();
        }
    }

    @Override
    public void onPreTickRaytrace() {
        if (mc.player == null)
            return;
        prevRotations[0] = rotations[0];
        prevRotations[1] = rotations[1];
        spoofs.forEach(spoof -> spoof.hasTarget = false);
        Optional<RotationSpoof> spoofOpt = spoofs.stream()
                .filter(RotationSpoof::isToggled)
                .filter(RotationSpoof::pickTarget)
                .max(Comparator.comparingInt(RotationSpoof::getPriority));

        hasTarget = spoofOpt.isPresent();
        if (hasTarget) {
            spoofOpt.get().hasTarget = true;
        }
        RotationSpoof spoof = hasTarget ? spoofOpt.get() : prevSpoof;
        rotating = spoof != null && (hasTarget || !confirmedClientRotation);

        if (rotating) {
            prevSpoof = spoof;
            rotate(true, 1.0F);
        } else {
            yawSpeed = Float.NaN;
            pitchSpeed = Float.NaN;
            rotations[0] = mc.player.getYaw();
            rotations[1] = mc.player.getPitch();
        }
        reportedRotations[0] = rotations[0];
        reportedRotations[1] = rotations[1];
    }

    @Override
    public void onRaytrace(RaytraceEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (rotating) {
            event.cancel();
            Raytrace result;
            if (!raytrace && hasTarget) {
                result = prevSpoof.getTarget();
            } else {
                result = RaytraceUtil.raytrace(mc, rotations, prevRotations, prevSpoof.getRange(), event.tickDelta);
            }
            mc.targetedEntity = result.target();
            mc.crosshairTarget = result.hitResult();
        }
    }

    @Override
    public void onGetRotation(RotationGetEvent event) {
        event.yaw = rotations[0];
        event.pitch = rotations[1];
    }

    @Override
    public void onSetRotation(float yaw, float pitch, boolean isYaw, boolean isPitch) {
        if (isYaw) rotations[0] = yaw;
        if (isPitch) rotations[1] = pitch;
    }

    @Override
    public void onSendYaw(SendRotationEvent event) {
        if (rotating) event.value = rotations[0];
    }

    @Override
    public void onSendPitch(SendRotationEvent event) {
        if (rotating) event.value = rotations[1];
    }

    @Override
    public void onStrafeInput(StrafeInputEvent event) {
        if (strafeMode == null)
            return;

        if (strafeMode.getCorrectMovement() != null) {
            if (rotating) strafeMode.getCorrectMovement().edit(rotations[0], event);
            else strafeMode.getCorrectMovement().reset();
        }
    }

    @Override
    public void onJump(JumpEvent event) {
        if (rotating) event.yaw = rotations[0];
    }

    @Override
    public void onStrafe(StrafeEvent event) {
        if (rotating && strafeMode.isFixYaw()) {
            event.yaw = rotations[0];
        }
    }

    public void limitDeltaRotation(float[] from, float[] to, float limitYaw, float limitPitch, float partialTicks) {
        to[0] = from[0] + MathHelper.clamp(MathHelper.subtractAngles(from[0], to[0]), -limitYaw * partialTicks, limitYaw * partialTicks);
        to[1] = from[1] + MathHelper.clamp(MathHelper.subtractAngles(from[1], to[1]), -limitPitch * partialTicks, limitPitch * partialTicks);
    }

    public void mouseSensitivity(float[] from, float[] to, float[] server, float partialTicks) {
        double sensitivity = mc.options.getMouseSensitivity().getValue() * 0.6F + 0.2F;
        double sensitivityCb = sensitivity * sensitivity * sensitivity;
        double gcd = sensitivityCb * 8.0;

        float deltaYaw = MathHelper.subtractAngles(from[0], to[0]);
        float deltaPitch = MathHelper.subtractAngles(from[1], to[1]);

        switch (sensitivityFix) {
            case NONE -> {
                server[0] = from[0] + deltaYaw;
                server[1] = from[1] + deltaPitch;
                server[1] = MathHelper.clamp(server[1], -90F, 90F);
            }
            case TICK_BASED -> {
                server[0] = from[0];
                server[1] = from[1];
                float gcdCursorDeltaX = deltaYaw / 0.15F;
                float gcdCursorDeltaY = deltaPitch / 0.15F;

                double cursorDeltaX = gcdCursorDeltaX / gcd;
                double cursorDeltaY = gcdCursorDeltaY / gcd;

                int roundedCursorDeltaX = (int) Math.round(cursorDeltaX);
                int roundedCursorDeltaY = (int) Math.round(cursorDeltaY);

                cursorDeltaX = roundedCursorDeltaX * gcd;
                cursorDeltaY = roundedCursorDeltaY * gcd;

                gcdCursorDeltaX = (float) cursorDeltaX * 0.15F;
                gcdCursorDeltaY = (float) cursorDeltaY * 0.15F;

                server[0] += gcdCursorDeltaX;
                server[1] += gcdCursorDeltaY;
            }
            case APPROXIMATE, REAL -> {
                double iterationsNeeded = (sensitivityFix == SensitivityFix.APPROXIMATE ?
                        ThreadLocalRandom.current().nextDouble(20, 60) : OpenGL.getFps())
                        / 20.0;
                iterationsNeeded *= partialTicks;
                int iterations = MathHelper.floor(iterationsNeeded + partialIterations);
                partialIterations += iterationsNeeded - iterations;

                server[0] = from[0];
                server[1] = from[1];

                float gcdCursorDeltaX = deltaYaw / 0.15F;
                float gcdCursorDeltaY = deltaPitch / 0.15F;

                double cursorDeltaX = gcdCursorDeltaX / gcd;
                double cursorDeltaY = gcdCursorDeltaY / gcd;

                double partialDeltaX = 0;
                double partialDeltaY = 0;

                for (int i = 0; i < iterations; i++) {
                    double sollDeltaX = cursorDeltaX / iterations;
                    double sollDeltaY = cursorDeltaY / iterations;

                    int istDeltaX = (int) Math.round(sollDeltaX + partialDeltaX);
                    int istDeltaY = (int) Math.round(sollDeltaY + partialDeltaY);

                    partialDeltaX += sollDeltaX - istDeltaX;
                    partialDeltaY += sollDeltaY - istDeltaY;

                    double newCursorDeltaX = istDeltaX * gcd;
                    double newCursorDeltaY = istDeltaY * gcd;

                    server[0] += (float) newCursorDeltaX * 0.15F;
                    server[1] += (float) newCursorDeltaY * 0.15F;
                }
            }
        }
    }

    public float getPrevYaw() {
        return prevRotations[0];
    }

    public float getPrevPitch() {
        return prevRotations[1];
    }

    public float getYaw() {
        return rotations[0];
    }

    public float getPitch() {
        return rotations[1];
    }

    public boolean isRotating() {
        return rotating;
    }
}
