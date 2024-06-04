package com.polarware.component.impl.player;

import com.polarware.Client;
import com.polarware.component.Component;
import com.polarware.component.impl.player.rotationcomponent.MovementFix;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.JumpEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.event.impl.render.LookEvent;
import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.rotation.RotationUtil;
import com.polarware.util.vector.Vector2f;
import net.minecraft.client.Minecraft;

public final class RotationComponent extends Component {
    private static boolean active, smoothed;
    public static Vector2f rotations, lastRotations, targetRotations, lastServerRotations;
    private static double rotationSpeed;
    private static MovementFix correctMovement;

    /*
     * This method must be called on Pre Update Event to work correctly
     */
    public static void setRotations(final Vector2f rotations, final double rotationSpeed, final MovementFix correctMovement) {
        RotationComponent.targetRotations = rotations;
        RotationComponent.rotationSpeed = rotationSpeed * 18;
        RotationComponent.correctMovement = correctMovement;
        active = true;

        smooth();
    }

    @EventLink(value = Priority.VERY_LOW)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        if (!active || rotations == null || lastRotations == null || targetRotations == null || lastServerRotations == null) {
            rotations = lastRotations = targetRotations = lastServerRotations = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        }

        if (active) {
            smooth();
        }

//        mc.thePlayer.rotationYaw = rotations.x;
//        mc.thePlayer.rotationPitch = rotations.y;

        if (correctMovement == MovementFix.BACKWARDS_SPRINT && active) {
            if (Math.abs(rotations.x - Math.toDegrees(MoveUtil.direction())) > 45) {
                mc.gameSettings.keyBindSprint.setPressed(false);
                mc.thePlayer.setSprinting(false);
            }
        }
    };


    @EventLink(value = Priority.VERY_LOW)
    public final Listener<MoveInputEvent> onMove = event -> {

        if (active && correctMovement == MovementFix.NORMAL && rotations != null) {
            /*
             * Calculating movement fix
             */
            final float yaw = rotations.x;
            MoveUtil.fixMovement(event, yaw);
        }
    };

    @EventLink(value = Priority.VERY_LOW)
    public final Listener<LookEvent> onLook = event -> {
        if (active && rotations != null) {
            event.setRotation(rotations);
        }
    };

    @EventLink(value = Priority.VERY_LOW)
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (active && (correctMovement == MovementFix.NORMAL || correctMovement == MovementFix.TRADITIONAL) && rotations != null) {
            event.setYaw(rotations.x);
        }
    };

    @EventLink(value = Priority.VERY_LOW)
    public final Listener<JumpEvent> onJump = event -> {
        if (active && (correctMovement == MovementFix.NORMAL || correctMovement == MovementFix.TRADITIONAL || correctMovement == MovementFix.BACKWARDS_SPRINT) && rotations != null) {
            event.setYaw(rotations.x);
        }
    };

    @EventLink(value = Priority.VERY_LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (active && rotations != null) {
            final float yaw = rotations.x;
            final float pitch = rotations.y;

            event.setYaw(yaw);
            event.setPitch(pitch);

//            mc.thePlayer.rotationYaw = yaw;
//            mc.thePlayer.rotationPitch = pitch;
            if(!(Client.INSTANCE.getModuleManager().get(ScaffoldModule.class).isEnabled() && Client.INSTANCE.getModuleManager().get(ScaffoldModule.class).mode.getValue().getName().equals("Derp"))) {
                if (mc.thePlayer.ticksExisted % 4 == 0) {
                    mc.thePlayer.renderYawOffset = lastRotations.x;
                } else {
                    mc.thePlayer.renderYawOffset = mc.thePlayer.prevRenderYawOffset;
                }
                mc.thePlayer.rotationYawHead = yaw;
                mc.thePlayer.renderPitchHead = pitch;
            }

            lastServerRotations = new Vector2f(yaw, pitch);

            if (Math.abs((rotations.x - mc.thePlayer.rotationYaw) % 360) < 1 && Math.abs((rotations.y - mc.thePlayer.rotationPitch)) < 1) {
                active = false;

                this.correctDisabledRotations();
            }

            lastRotations = rotations;
        } else {
            lastRotations = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        }

        targetRotations = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        smoothed = false;
    };

    private void correctDisabledRotations() {
        final Vector2f rotations = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        final Vector2f fixedRotations = RotationUtil.resetRotation(RotationUtil.applySensitivityPatch(rotations, lastRotations));

        mc.thePlayer.rotationYaw = fixedRotations.x;
        mc.thePlayer.rotationPitch = fixedRotations.y;
    }

    public static void smooth() {
      //  if (!smoothed) {
            final float lastYaw = lastRotations.x;
            final float lastPitch = lastRotations.y;
            final float targetYaw = targetRotations.x;
            final float targetPitch = targetRotations.y;

            rotations = RotationUtil.smooth(new Vector2f(lastYaw, lastPitch), new Vector2f(targetYaw, targetPitch),
                    rotationSpeed + Math.random());

            if (correctMovement == MovementFix.NORMAL || correctMovement == MovementFix.TRADITIONAL) {
                mc.thePlayer.movementYaw = rotations.x;
            }

            mc.thePlayer.velocityYaw = rotations.x;
      //  }

        smoothed = true;

        /*
         * Updating MouseOver
         */
        mc.entityRenderer.getMouseOver(1);
    }
}