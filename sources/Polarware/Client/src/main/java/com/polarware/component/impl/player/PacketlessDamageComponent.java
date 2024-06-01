package com.polarware.component.impl.player;

import com.polarware.component.Component;
import com.polarware.component.impl.render.SmoothCameraComponent;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import lombok.Getter;

public final class PacketlessDamageComponent extends Component {

    @Getter
    private static boolean active;
    private static float timer;
    private static int jumps;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (active) {
            if (jumps < 3) {
                mc.timer.timerSpeed = timer;

                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    jumps++;
                }

                event.setOnGround(false);
            } else if (mc.thePlayer.onGround) {
                mc.timer.timerSpeed = 1.0F;
                active = false;
                timer = 1.0F;
                jumps = 0;
            }

            SmoothCameraComponent.setY();
        }
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        if (active) {
            event.setForward(0);
            event.setStrafe(0);
        }
    };

    public static void setActive(final float timer) {
        PacketlessDamageComponent.active = true;
        PacketlessDamageComponent.timer = timer;
        jumps = 0;
    }
}
