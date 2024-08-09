package dev.excellent.client.rotation;


import dev.excellent.Excellent;
import dev.excellent.api.event.impl.player.LookEvent;
import dev.excellent.api.event.impl.player.RotationEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.api.interfaces.game.IMinecraft;
import lombok.Getter;
import net.minecraft.util.math.MathHelper;

public class FreeLookHandler implements IMinecraft {
    public FreeLookHandler() {
        Excellent.getInst().getEventBus().register(this);
    }

    @Getter
    private static boolean active;
    @Getter
    private static float freeYaw, freePitch;
    private final Listener<LookEvent> onLook = event -> {
        if (active) {
            rotateTowards(event.yaw, event.pitch);
            event.cancel();
        }
    };

    private final Listener<RotationEvent> onRotation = event -> {
        if (active) {
            event.yaw = freeYaw;
            event.pitch = freePitch;
        } else {
            freeYaw = event.yaw;
            freePitch = event.pitch;
        }
    };

    public static void setActive(boolean state) {
        if (active != state) {
            active = state;
            resetRotation();
        }
    }

    private void rotateTowards(double yaw, double pitch) {
        double d0 = pitch * 0.15D;
        double d1 = yaw * 0.15D;
        freePitch = (float) ((double) freePitch + d0);
        freeYaw = (float) ((double) freeYaw + d1);
        freePitch = MathHelper.clamp(freePitch, -90.0F, 90.0F);
    }

    private static void resetRotation() {
        mc.player.rotationYaw = freeYaw;
        mc.player.rotationPitch = freePitch;
    }
}
