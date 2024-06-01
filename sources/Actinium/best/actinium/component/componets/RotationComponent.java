package best.actinium.component.componets;

import best.actinium.Actinium;
import best.actinium.component.Component;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.move.JumpCorrectionEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.move.MoveInputEvent;
import best.actinium.event.impl.move.StrafeEvent;
import best.actinium.module.impl.manager.RotationManager;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.player.MoveUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import org.lwjglx.util.vector.Vector2f;
/*half d.. i mean what*/
@Getter
@Setter
public class RotationComponent extends Component {
    private static boolean smoothed;
    public static Vector2f rotations, lastRotations, targetRotations, lastServerRotations;
    private static double rotationSpeed;
    private boolean shouldMoveFix;

    /*
     * run on update to work
     */
    public static void setRotations(final Vector2f rotations) {
        RotationComponent.targetRotations = rotations;
        setActive(true);

        smooth();
    }

    @Callback
    public void onUpdate(UpdateEvent event) {
        if (event.getType() == EventType.POST) {
            return;
        }

        shouldMoveFix = Actinium.INSTANCE.getModuleManager().get(RotationManager.class).moveFix.isEnabled();
        RotationComponent.rotationSpeed = RandomUtil.getAdvancedRandom(Actinium.INSTANCE.getModuleManager().get(RotationManager.class).minRotationSpeed.getValue().floatValue(),
                Actinium.INSTANCE.getModuleManager().get(RotationManager.class).maxRotationSpeed.getValue().floatValue()) * 18;

        if (!isActive() || rotations == null || lastRotations == null || targetRotations == null || lastServerRotations == null) {
            rotations = lastRotations = targetRotations = lastServerRotations = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        }

        if (isActive()) {
            smooth();
        }
    }


    @Callback
    public void onInput(MoveInputEvent event) {
        if (isActive() && shouldMoveFix && rotations != null) {
            final float yaw = rotations.x;
            MoveUtil.fixMovement(event, yaw);
        }
    }

    @Callback
    public void onNut(StrafeEvent event) {
        if (isActive() && (shouldMoveFix) && rotations != null) {
            event.setYaw(rotations.x);
        }
    }

    @Callback
    public void onNut2(JumpCorrectionEvent event) {
        if (isActive() && (shouldMoveFix) && rotations != null) {
            event.setYaw(rotations.x);
        }
    }

    @Callback
    public void onMotion(MotionEvent event) {
        if(event.getType() == EventType.POST) {
            return;
        }

        if (isActive() && rotations != null) {
            final float yaw = rotations.x;
            final float pitch = rotations.y;

            event.setYaw(yaw);
            event.setPitch(pitch);

            float partialTicks = mc.timer.renderPartialTicks;
            mc.thePlayer.rotationYawHead = this.interpolateRotation(lastRotations.x, rotations.x, partialTicks);
            mc.thePlayer.renderYawOffset = this.interpolateRotation(lastRotations.x, rotations.x, partialTicks) + 60.0f;
            mc.thePlayer.rotationPitchHead = lastRotations.y + (rotations.y - lastRotations.y) * partialTicks;

            lastServerRotations = new Vector2f(yaw, pitch);

            if (Math.abs((rotations.x - mc.thePlayer.rotationYaw) % 360) < 1 && Math.abs((rotations.y - mc.thePlayer.rotationPitch)) < 1) {
                setActive(false);

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
        final Vector2f fixedRotations = resetRotation(applySensitivityPatch(rotations, lastRotations));

        mc.thePlayer.rotationYaw = fixedRotations.x;
        mc.thePlayer.rotationPitch = fixedRotations.y;
    }

    protected float interpolateRotation(float par1, float par2, float par3) {
        float f;
        for (f = par2 - par1; f < -180.0f; f += 360.0f) {
        }

        while (f >= 180.0f) {
            f -= 360.0f;
        }

        return par1 + par3 * f;
    }

    public static Vector2f smooth(final Vector2f lastRotation, final Vector2f targetRotation, final double speed) {
        float yaw = targetRotation.x;
        float pitch = targetRotation.y;
        final float lastYaw = lastRotation.x;
        final float lastPitch = lastRotation.y;

        if (speed != 0) {
            final float rotationSpeed = (float) speed;

            final double deltaYaw = MathHelper.wrapAngleTo180_float(targetRotation.x - lastRotation.x);
            final double deltaPitch = pitch - lastPitch;

            final double distance = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);
            final double distributionYaw = Math.abs(deltaYaw / distance);
            final double distributionPitch = Math.abs(deltaPitch / distance);

            final double maxYaw = rotationSpeed * distributionYaw;
            final double maxPitch = rotationSpeed * distributionPitch;

            final float moveYaw = (float) Math.max(Math.min(deltaYaw, maxYaw), -maxYaw);
            final float movePitch = (float) Math.max(Math.min(deltaPitch, maxPitch), -maxPitch);

            yaw = lastYaw + moveYaw;
            pitch = lastPitch + movePitch;

            for (int i = 1; i <= (int) (Minecraft.getDebugFPS() / 20f + Math.random() * 10); ++i) {

                if (Math.abs(moveYaw) + Math.abs(movePitch) > 1) {
                    yaw += (Math.random() - 0.5) / 1000;
                    pitch -= Math.random() / 200;
                }

                /*
                 * Fixing GCD
                 */
                final Vector2f rotations = new Vector2f(yaw, pitch);
                final Vector2f fixedRotations = applySensitivityPatch(rotations);

                /*
                 * Setting rotations
                 */
                yaw = fixedRotations.x;
                pitch = Math.max(-90, Math.min(90, fixedRotations.y));
            }
        }

        return new Vector2f(yaw, pitch);
    }

    public Vector2f resetRotation(final Vector2f rotation) {
        if (rotation == null) {
            return null;
        }

        final float yaw = rotation.x + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - rotation.x);
        final float pitch = mc.thePlayer.rotationPitch;
        return new Vector2f(yaw, pitch);
    }

    public Vector2f applySensitivityPatch(final Vector2f rotation, final Vector2f previousRotation) {
        final float mouseSensitivity = (float) (mc.gameSettings.mouseSensitivity * (1 + Math.random() / 10000000) * 0.6F + 0.2F);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F * 0.15D;
        final float yaw = previousRotation.x + (float) (Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
        final float pitch = previousRotation.y + (float) (Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
        return new Vector2f(yaw, MathHelper.clamp_float(pitch, -90, 90));
    }

    public static Vector2f applySensitivityPatch(final Vector2f rotation) {
        final Vector2f previousRotation = mc.thePlayer.getPreviousRotation();
        final float mouseSensitivity = (float) (mc.gameSettings.mouseSensitivity * (1 + Math.random() / 10000000) * 0.6F + 0.2F);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F * 0.15D;
        final float yaw = previousRotation.x + (float) (Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
        final float pitch = previousRotation.y + (float) (Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
        return new Vector2f(yaw, MathHelper.clamp_float(pitch, -90, 90));
    }

    public static void smooth() {
        if(lastRotations == null || targetRotations == null) {
            return;
        }

        final float lastYaw = lastRotations.x;
        final float lastPitch = lastRotations.y;
        final float targetYaw = targetRotations.x;
        final float targetPitch = targetRotations.y;

        rotations = smooth(new Vector2f(lastYaw, lastPitch), new Vector2f(targetYaw, targetPitch),
                rotationSpeed + Math.random());

        smoothed = true;

        /*
         * Updating MouseOver
         */
        mc.entityRenderer.getMouseOver(1);
    }
}
