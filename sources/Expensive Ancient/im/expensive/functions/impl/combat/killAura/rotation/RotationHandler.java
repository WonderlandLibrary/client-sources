package im.expensive.functions.impl.combat.killAura.rotation;

import com.google.common.eventbus.Subscribe;
import im.expensive.Expensive;
import im.expensive.events.EventMotion;
import im.expensive.events.EventRotate;
import im.expensive.events.EventUpdate;
import im.expensive.events.LookEvent;
import im.expensive.utils.client.IMinecraft;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.math.MathHelper;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RotationHandler implements IMinecraft {
    @Getter
    boolean active, reset;
    VecRotation currentRotation, targetRotation;
    AimPlan aimPlan;
    @Setter
    float rotateTicks;
    float freeYaw, freePitch;

    public RotationHandler() {
        Expensive.getInstance().getEventBus().register(this);
    }


    public void setRotation(VecRotation rotation, AimPlan aimPlan) {
        this.targetRotation = rotation;
        this.aimPlan = aimPlan;
        this.active = true;
        this.reset = false;
    }

    public void resetRotation() {
        this.reset = true;
        mc.player.rotationYaw = freeYaw;
        mc.player.rotationPitch = freePitch;
        currentRotation = null;
    }

    public VecRotation getRotation() {
        return currentRotation != null ? currentRotation : new VecRotation(mc.player.rotationYaw, mc.player.rotationPitch);
    }

    @Subscribe
    public void onLook(LookEvent e) {
        if (active && currentRotation != null) {
            rotateTowards(e.yaw, e.pitch);
            e.cancel();
        }
    }

    public void rotateTowards(double yaw, double pitch) {
        double d0 = pitch * 0.15D;
        double d1 = yaw * 0.15D;
        this.freePitch = (float) ((double) this.freePitch + d0);
        this.freeYaw = (float) ((double) this.freeYaw + d1);
        this.freePitch = MathHelper.clamp(this.freePitch, -90.0F, 90.0F);
    }

    @Subscribe
    public void onActive(EventRotate e) {
        if (active && currentRotation != null) {
            e.yaw = freeYaw;
            e.pitch = freePitch;
            e.cancel();
        } else {
//            freeYaw = e.yaw;
//            freePitch = e.pitch;
        }
    }

    public void reset() {
        freeYaw = mc.player.rotationYaw;
        freePitch = mc.player.rotationPitch;
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        VecRotation realRotation = new VecRotation(freeYaw,freePitch);

        if (currentRotation == null) {
            currentRotation = realRotation;
        }

        if (!active) {
            currentRotation = realRotation;
            return;
        }

        if (reset) {
            targetRotation = realRotation;
            active = false;
            return;
        }

        if (aimPlan.getName().equalsIgnoreCase("Grim")) {
            if (rotateTicks > 0) {
                currentRotation = aimPlan.getRotation(targetRotation, new VecRotation(mc.player.rotationYaw, mc.player.rotationPitch));
                mc.player.rotationYaw = currentRotation.getYaw();
                mc.player.rotationPitch = currentRotation.getPitch();
                rotateTicks--;
            } else {
                resetRotation();
            }
        } else {
            currentRotation = aimPlan.getRotation(targetRotation, new VecRotation(mc.player.rotationYaw, mc.player.rotationPitch));
            mc.player.rotationYaw = currentRotation.getYaw();
            mc.player.rotationPitch = currentRotation.getPitch();
        }
    }
}