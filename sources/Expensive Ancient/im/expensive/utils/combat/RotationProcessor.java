package im.expensive.utils.combat;

import com.google.common.eventbus.Subscribe;
import im.expensive.Expensive;
import im.expensive.events.EventInput;
import im.expensive.events.EventLook;
import im.expensive.events.EventMotion;
import im.expensive.events.EventUpdate;
import im.expensive.utils.player.MouseUtil;
import im.expensive.utils.player.MoveUtils;
import im.expensive.utils.rotation.RotationUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RotationProcessor {
    boolean active, rotated;
    double rotationSpeed;
    MovementFix correctMovement;
    RotationType rotationType;
    int timeout = 0, ticks = 0;
    Rotation rotation, targetRotation, lastRotation, lastServerRotation;

    final Minecraft minecraft = Minecraft.getInstance();
    LivingEntity target;

    public RotationProcessor() {
        Expensive.getInstance().getEventBus().register(this);
    }

    public Rotation getRotation() {
        return rotation != null ? rotation : new Rotation(minecraft.player.rotationYaw, minecraft.player.rotationPitch);
    }

    public void setRotation(LivingEntity target, Rotation rotation, double rotationSpeed, MovementFix correctMovement, RotationType rotationType) {
        this.target = target;
        this.targetRotation = rotation;
        this.rotationSpeed = rotationSpeed;
        this.correctMovement = correctMovement;
        this.rotated = false;
        this.timeout = 4;
        this.rotationType = rotationType;
        if (rotationType != RotationType.SNAP) {
            this.active = true;
        }
        rotate();
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (!active && rotationType != RotationType.SNAP || rotation == null || lastRotation == null || targetRotation == null || lastServerRotation == null) {
            minecraft.player.rotationYawOffset = Integer.MIN_VALUE;
            rotation = lastRotation = targetRotation = lastServerRotation = new Rotation(minecraft.player.rotationYaw, minecraft.player.rotationPitch);
        }

        if (active && !rotation.equals(targetRotation)) {
            rotate();
        }


        if (timeout > 0 && rotationType == RotationType.SNAP) {
            if (!active && Expensive.getInstance().getFunctionRegistry().getKillAura().shouldPlayerFalling()) {
                active = true;
                ticks = 0;
            }

            if (MouseUtil.getMouseOver(target, lastRotation.getYaw(), lastRotation.getPitch(), 3.0f) == target) {
                ticks++;
            }

            if (ticks >= timeout) {
                rotationType = RotationType.NONE;
                rotation = lastRotation = targetRotation = lastServerRotation = new Rotation(minecraft.player.rotationYaw, minecraft.player.rotationPitch);
                active = false;
            }
        }

    }

    @Subscribe
    public void onInput(EventInput eventInput) {
        if (active && correctMovement.equals(MovementFix.SILENT) && rotation != null) {
            MoveUtils.fixMovement(eventInput, rotation.getYaw());
        }
    }

    @Subscribe
    public void onMotion(EventMotion event) {
        if (active && rotation != null) {
            float yaw = rotation.getYaw();
            float pitch = rotation.getPitch();
            event.setYaw(yaw);
            event.setPitch(pitch);
            minecraft.player.rotationYawHead = yaw;
            minecraft.player.renderYawOffset = yaw;
            minecraft.player.rotationPitchHead = pitch;
            lastRotation = new Rotation(yaw, pitch);
            if (Math.abs((rotation.getYaw() - minecraft.player.rotationYaw) % 360.0F) < 1.0F && Math.abs(rotation.getPitch() - minecraft.player.rotationPitch) < 1.0F) {
                active = false;
                this.correctDisabledRotations();
            }

            lastRotation = rotation;
        } else {
            lastRotation = new Rotation(minecraft.player.rotationYaw, minecraft.player.rotationPitch);
        }

        targetRotation = new Rotation(minecraft.player.rotationYaw, minecraft.player.rotationPitch);
        rotated = false;
    }

    private void correctDisabledRotations() {
        Rotation rotations = new Rotation(minecraft.player.rotationYaw, minecraft.player.rotationPitch);
        Rotation fixedRotations = RotationUtils.resetRotation(rotations);
        minecraft.player.rotationYaw = fixedRotations.getYaw();
        minecraft.player.rotationPitch = fixedRotations.getPitch();
    }

    public void rotate() {
        if (!rotated) {
            if (!active || rotation == null || lastRotation == null || targetRotation == null || lastServerRotation == null) {
                rotation = lastRotation = targetRotation = lastServerRotation = new Rotation(minecraft.player.rotationYaw, minecraft.player.rotationPitch);
            }
            final float lastYaw = lastRotation.getYaw();
            final float lastPitch = lastRotation.getPitch();
            final float targetYaw = targetRotation.getYaw();
            final float targetPitch = targetRotation.getPitch();

            rotation = RotationUtils.getRotation(new Rotation(lastYaw, lastPitch), new Rotation(targetYaw, targetPitch),
                    rotationSpeed);
        }

        rotated = true;
    }


    public enum RotationType {
        SMOOTH, SNAP, NONE
    }
}
