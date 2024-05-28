package dev.vertic.module.impl.render;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.api.Priority;
import dev.vertic.event.impl.motion.PreMotionEvent;
import dev.vertic.event.impl.render.RotationsRenderEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;

public class Rotations extends Module {

    public float yaw, pitch;
    private float lastYaw, lastPitch;
    public boolean customRender;

    public Rotations() {
        super("Rotations", "Shows server side rotations in 3rd person.", Category.RENDER);
    }

    @EventLink(Priority.VERY_LOW)
    public void onMotion(PreMotionEvent event) {
        customRender = mc.thePlayer.rotationYaw != event.getYaw() || mc.thePlayer.rotationPitch != event.getPitch();
        lastYaw = yaw;
        lastPitch = pitch;
        yaw = event.getYaw();
        pitch = event.getPitch();
    }

    @EventLink(Priority.VERY_LOW)
    public void onRotationRender(RotationsRenderEvent event) {
        if(customRender) {
            event.setYaw(yaw);
            event.setBodyYaw(yaw);
            event.setPitch(pitch);
        }
    }

}
